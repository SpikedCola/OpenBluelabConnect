package bluelab.connect.d;

import bluelab.connect.Connect;
import bluelab.connect.c.BluelabConnectStick;
import bluelab.connect.c.BluelabRemoteXbeeDevice;
import bluelab.connect.model.SettingFileModel;
import com.google.gson.JsonSyntaxException;
import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.function.BiConsumer;
import javax.swing.JOptionPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BluelabDevice implements bluelab.connect.Web.Interface_OnSuccessFailure, Interface_DeviceList, Runnable {
   static Logger logger = LoggerFactory.getLogger(BluelabDevice.class);
   private Interface_UpdateDevice callbackInterface;
   private SettingFileModel settingFileModel;
   private static final String devicesJsonFile;
   private static final String knownDevicesFile;
   private boolean running;
   private BluelabConnectStick localXbeeDevice;
   private bluelab.connect.f.CSVWriter csvWriter;
   private DeviceManager deviceManager;
   private final ScheduledExecutorService scheduledExecutor = Executors.newScheduledThreadPool(2);
   private long k = 0L;
   private long l = 0L;
   private long m = 0L;
   private long n = 0L;
   private long nextUpdateMillis = 0L;
   private CopyOnWriteArrayList<BluelabRemoteXbeeDevice> remoteDeviceCOWList;
   private ArrayList<BluelabRemoteXbeeDevice> remoteDeviceList;
   private BluelabDeviceManager bluelabDeviceManager;
   private Thread thread;
   private bluelab.connect.h.StateManager stateManager;
   private final ArrayList<Enum_Command> commandList;
   private static int counter;
   private bluelab.connect.l.FirmwareUpdater firmwareUpdater;

   static {
      devicesJsonFile = Connect.GetUserDataDirectory() + File.separator + "devices.json";
     
      // wonder why this is done with stringbuilder?
      String path = "";
      String os;
      if ((os = System.getProperty("os.name").toLowerCase()).contains("win")) {
         path = "C:/Bluelab Connect";
      } else if (os.contains("mac")) {
         path = System.getProperty("user.home") + File.separator + "Bluelab Connect";
      }

      StringBuilder pathSB = new StringBuilder(path);
      knownDevicesFile = pathSB.append(File.separator).append("known.devices").toString();
   }

   public BluelabDevice(SettingFileModel settingFileModel) {
      this.settingFileModel = settingFileModel;
      this.callbackInterface = null;
      this.running = true;
      this.csvWriter = new bluelab.connect.f.CSVWriter(settingFileModel);
      this.deviceManager = new DeviceManager(settingFileModel, this);
      this.stateManager = new bluelab.connect.h.StateManager();
      this.commandList = new ArrayList(Arrays.asList(bluelab.connect.d.Enum_Command.DATA_1, bluelab.connect.d.Enum_Command.DATA_2, bluelab.connect.d.Enum_Command.DATA_3));
      counter = 0;
      this.firmwareUpdater = new bluelab.connect.l.FirmwareUpdater();
   }

   public final BluelabConnectStick getXbeeDevice() {
      return this.localXbeeDevice;
   }

   public final bluelab.connect.h.StateManager getStateManager() {
      return this.stateManager;
   }

   public final bluelab.connect.f.CSVWriter getCSVWriter() {
      return this.csvWriter;
   }

   public final SettingFileModel getSettingFileModel() {
      return this.settingFileModel;
   }

   public final void setCallbackInterface(Interface_UpdateDevice var1) {
      this.callbackInterface = var1;
   }

   public final void a(bluelab.connect.l.Interface_FirmwareUpdateNotify var1) {
      this.firmwareUpdater.setCallback(var1);
   }

   public final void clearCallbackInterface() {
      this.callbackInterface = null;
   }

   public final List<BluelabRemoteXbeeDevice> getRemoteDeviceCOWList() {
      return this.remoteDeviceCOWList;
   }

   public void run() {
      try {
         this.running = true;
         logger.info("Started {} {}", Connect.GetTitle(), Connect.GetVersion());
         bluelab.connect.i.SettingsAndSystemProperties.GetUserInfo(this.settingFileModel, this.remoteDeviceCOWList).forEach((var0, var1x) -> {
            logger.info("{}: {}", var0, var1x);
         });
         this.csvWriter.scheduleWritingToCSV(this.settingFileModel.logDataInterval);
         BluelabDevice dev = this;
         this.remoteDeviceCOWList = new CopyOnWriteArrayList();
         this.remoteDeviceList = new ArrayList();

         String message;
         try {
            dev.remoteDeviceCOWList = bluelab.connect.d.DevicesFileManager.LoadDevicesFromFile(devicesJsonFile);
            if (dev.remoteDeviceCOWList.isEmpty()) {
               logger.info("Attempting to import devices from previous file format...");
               dev.remoteDeviceCOWList = bluelab.connect.d.DevicesFileManager.CreateDevicesWithKeycodesFromFile(knownDevicesFile);
            }
         } catch (JsonSyntaxException ex) {
            message = String.format("<html>Detected corrupted device file.<br>%s</html>", ex.toString());
            JOptionPane.showMessageDialog((Component)null, message, "Device file error", 0);
            bluelab.connect.d.WeirdEncoder.ReportException((Throwable)ex);
         } catch (IOException ex) {
            message = String.format("<html>Device file access error (%s).<br>%s</html>", devicesJsonFile, ex.toString());
            JOptionPane.showMessageDialog((Component)null, message, "Device file error", 0);
            bluelab.connect.d.WeirdEncoder.ReportException((Throwable)ex);
            System.exit(-1);
         }

         if (this.callbackInterface != null) {
            this.callbackInterface.updateDevices((List)this.remoteDeviceCOWList);
         }
      } catch (Throwable ex) {
         bluelab.connect.d.WeirdEncoder.ReportException(ex);
      }

      while(this.running) {
         try {
            if (this.callbackInterface != null) {
               this.callbackInterface.update();
            }

            this.localXbeeDevice = BluelabConnectStick.FindConnectStickAndCreateLocalXbeeDevice();
            if (this.localXbeeDevice != null) {
               this.localXbeeDevice.setStateManager(this.stateManager);
               this.stateManager.put("64-bit address", this.localXbeeDevice.get64BitAddress().toString().toLowerCase());
               this.stateManager.put("Node ID", this.localXbeeDevice.getNodeID());
               this.stateManager.put("Hardware version", this.localXbeeDevice.getHardwareVersion().toString());
               this.stateManager.put("Firmware version", this.localXbeeDevice.getFirmwareVersion());
               this.stateManager.put("Protocol", this.localXbeeDevice.getXBeeProtocol().toString());
               this.stateManager.put("Association indication", this.localXbeeDevice.getAssociationIndicationStatus().toString());
               long panId64Bit = ByteBuffer.wrap(this.localXbeeDevice.getPANID()).getLong();
               this.stateManager.put("64-bit PAN ID", Long.toHexString(panId64Bit));
               long panId16Bit = (long)ByteBuffer.wrap(this.localXbeeDevice.getParameter("OI")).getShort();
               this.stateManager.put("16-bit PAN ID", Integer.toHexString((int)panId16Bit));
               byte channel = ByteBuffer.wrap(this.localXbeeDevice.getParameter("CH")).get();
               this.stateManager.put("Channel", String.valueOf(channel));
               if (this.callbackInterface != null) {
                  this.callbackInterface.foundConnectStick();
               }

               if (this.running) {
                  this.bluelabDeviceManager = new BluelabDeviceManager(this, (byte)0);
                  this.thread = new Thread(this.bluelabDeviceManager);
                  this.thread.start();
                  this.thread.join();
               }

               this.localXbeeDevice.close();
               this.csvWriter.requestWaitShutdown();
            } else if (this.callbackInterface != null) {
               this.callbackInterface.shutdown();
            }

            this.localXbeeDevice = null;
         } catch (Throwable ex) {
            bluelab.connect.d.WeirdEncoder.ReportException(ex);
            logger.error("Error: {}", ex.toString());
         } finally {
            bluelab.connect.d.DevicesFileManager.SaveDevicesToFile(this.remoteDeviceCOWList, devicesJsonFile);
         }
      }

      this.scheduledExecutor.shutdown();
      logger.info("Done");
   }

   public final void i() {
      logger.info("Stop requested");
      this.running = false;
      if (this.bluelabDeviceManager != null) {
         this.bluelabDeviceManager.requestStop();
      }

   }

   public final void onSuccess() {
   }

   public final void onFailure() {
      if (this.bluelabDeviceManager != null) {
         this.bluelabDeviceManager.clearDeviceIdentifiers();
      }

   }

   public final boolean containsKeycode(String var1) {
      BluelabRemoteXbeeDevice dev = new BluelabRemoteXbeeDevice(var1);
      return this.remoteDeviceCOWList.contains(dev);
   }

   public final void b(String var1) {
      BluelabRemoteXbeeDevice dev = new BluelabRemoteXbeeDevice(var1);
      if (this.bluelabDeviceManager != null) {
         this.bluelabDeviceManager.addDevice(dev);
         bluelab.connect.GoogleAnalytics.GA_tracker_Sender.GetInstance().reportEvent("action", "DK_AddDevice");
      }

   }

   public final void c(String var1) {
      BluelabRemoteXbeeDevice dev = new BluelabRemoteXbeeDevice(var1);
      int devIdx;
      if ((devIdx = this.remoteDeviceCOWList.indexOf(dev)) >= 0) {
         dev = (BluelabRemoteXbeeDevice)this.remoteDeviceCOWList.get(devIdx);
         if (this.bluelabDeviceManager != null) {
            this.bluelabDeviceManager.b(dev);
         }
      }

   }

   public final void saveToFile() {
      bluelab.connect.d.DevicesFileManager.SaveDevicesToFile(this.remoteDeviceCOWList, devicesJsonFile);
   }

   public final void deviceRegistrationErrorList(boolean isError, String message, List<String> errorList) {
      if (this.bluelabDeviceManager != null && isError) {
         this.bluelabDeviceManager.clearDeviceIdentifiers();
      }

      if (this.callbackInterface != null) {
         this.callbackInterface.errorList(isError, message, errorList);
      }

   }

   public final void deviceUpdateErrorList(boolean isError, String message, List<String> errorList) {
      if (this.bluelabDeviceManager != null && isError) {
         this.bluelabDeviceManager.clearDeviceIdentifiers();
      }

      if (this.callbackInterface != null) {
         this.callbackInterface.errorList(isError, message, errorList);
      }

   }

   public final void deviceDeregistrationErrorList(boolean isError, String message, List<String> errorList) {
      if (this.bluelabDeviceManager != null && isError) {
         this.bluelabDeviceManager.clearDeviceIdentifiers();
      }

      if (this.callbackInterface != null) {
         this.callbackInterface.errorList(isError, message, errorList);
      }

   }

   // $FF: synthetic method
   static BluelabDeviceManager GetBluelabDeviceManager(BluelabDevice dev) {
      return dev.bluelabDeviceManager;
   }

   // $FF: synthetic method
   static Interface_UpdateDevice getCallbackInterface(BluelabDevice var0) {
      return var0.callbackInterface;
   }

   // $FF: synthetic method
   static CopyOnWriteArrayList GetRemoteDeviceCOWList(BluelabDevice var0) {
      return var0.remoteDeviceCOWList;
   }

   // $FF: synthetic method
   static ArrayList GetRemoteDeviceList(BluelabDevice dev) {
      return dev.remoteDeviceList;
   }

   // $FF: synthetic method
   static bluelab.connect.f.CSVWriter GetCSVWriter(BluelabDevice dev) {
      return dev.csvWriter;
   }

   // $FF: synthetic method
   static String GetDevicesJsonFile() {
      return devicesJsonFile;
   }

   // $FF: synthetic method
   static DeviceManager GetDeviceManager(BluelabDevice dev) {
      return dev.deviceManager;
   }

   // $FF: synthetic method
   static BluelabConnectStick GetXbeeDevice(BluelabDevice dev) {
      return dev.localXbeeDevice;
   }

   // $FF: synthetic method
   static void GetStoreNumberOfNetworkDevices(BluelabDevice dev) {
      int numDevices = dev.localXbeeDevice.getNetwork().getNumberOfDevices();
      dev.stateManager.put("Number of devices", String.valueOf(numDevices));
   }

   // $FF: synthetic method
   static ArrayList GetCommandList(BluelabDevice dev) {
      return dev.commandList;
   }

   // $FF: synthetic method
   static int GetCounter() {
      return counter;
   }

   // $FF: synthetic method
   static void SetCounter(int var0) {
      counter = var0;
   }

   // $FF: synthetic method
   static long GetDeviceNextUpdateMillis(BluelabDevice dev) {
      return dev.nextUpdateMillis;
   }

   // $FF: synthetic method
   static void SetDeviceNextUpdateMillis(BluelabDevice dev, long millis) {
      dev.nextUpdateMillis = millis;
   }

   // $FF: synthetic method
   static long k(BluelabDevice dev) {
      return dev.k;
   }

   // $FF: synthetic method
   static void b(BluelabDevice dev, long var1) {
      dev.k = var1;
   }

   // $FF: synthetic method
   static long l(BluelabDevice dev) {
      return dev.l;
   }

   // $FF: synthetic method
   static void c(BluelabDevice dev, long var1) {
      dev.l = var1;
   }

   // $FF: synthetic method
   static long m(BluelabDevice dev) {
      return dev.m;
   }

   // $FF: synthetic method
   static void d(BluelabDevice dev, long var1) {
      dev.m = var1;
   }

   // $FF: synthetic method
   static long n(BluelabDevice dev) {
      return dev.n;
   }

   // $FF: synthetic method
   static void e(BluelabDevice dev, long var1) {
      dev.n = var1;
   }

   // $FF: synthetic method
   static bluelab.connect.h.StateManager getStateManager(BluelabDevice dev) {
      return dev.stateManager;
   }

   // $FF: synthetic method
   static bluelab.connect.l.FirmwareUpdater getFirmwareUpdater(BluelabDevice var0) {
      return var0.firmwareUpdater;
   }

   // $FF: synthetic method
   static ScheduledExecutorService getScheduledExecutor(BluelabDevice var0) {
      return var0.scheduledExecutor;
   }
}