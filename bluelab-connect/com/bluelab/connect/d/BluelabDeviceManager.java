package bluelab.connect.d;

import bluelab.connect.c.BluelabConnectStick;
import bluelab.connect.c.BluelabGuardianRemoteXbeeDevice;
import bluelab.connect.c.BluelabPHControllerRemoteXbeeDevice;
import bluelab.connect.c.BluelabProControllerRemoteXbeeDevice;
import bluelab.connect.c.BluelabExtenderDevice;
import bluelab.connect.c.BluelabRemoteXbeeDevice;
import bluelab.connect.c.b.InstantiateDeviceFromVersion;
import com.digi.xbee.api.RemoteXBeeDevice;
import com.digi.xbee.api.XBeeNetwork;
import com.digi.xbee.api.exceptions.TimeoutException;
import com.digi.xbee.api.exceptions.XBeeException;
import com.digi.xbee.api.listeners.IDataReceiveListener;
import com.digi.xbee.api.listeners.IDiscoveryListener;
import com.digi.xbee.api.listeners.IModemStatusReceiveListener;
import com.digi.xbee.api.listeners.IPacketReceiveListener;
import com.digi.xbee.api.models.ModemStatusEvent;
import com.digi.xbee.api.models.XBeeMessage;
import com.digi.xbee.api.packet.XBeePacket;
import com.digi.xbee.api.packet.common.RemoteATCommandPacket;
import com.digi.xbee.api.packet.common.RemoteATCommandResponsePacket;
import com.digi.xbee.api.utils.HexUtils;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Predicate;

final class BluelabDeviceManager implements IDataReceiveListener, IDiscoveryListener, IModemStatusReceiveListener, IPacketReceiveListener, Runnable {
   // $FF: synthetic field
   private BluelabDevice device;

   private BluelabDeviceManager(BluelabDevice dev) {
      super();
      this.device = dev;
   }

   private void notifyDeviceChange() {
      if (BluelabDevice.GetDeviceManager(this.device) != null && BluelabDevice.getCallbackInterface(this.device) != null) {
         BluelabDevice.getCallbackInterface(this.device).updateDevices((List)BluelabDevice.GetRemoteDeviceCOWList(this.device));
      }

   }

   private void c(BluelabRemoteXbeeDevice remoteDev) {
      if (BluelabDevice.GetDeviceManager(this.device) != null && BluelabDevice.getCallbackInterface(this.device) != null) {
         BluelabDevice.getCallbackInterface(this.device).updateDevice(remoteDev);
      }

   }

   public final void addDevice(BluelabRemoteXbeeDevice remoteDev) {
      if (!BluelabDevice.GetRemoteDeviceCOWList(this.device).contains(remoteDev)) {
         int devIdx;
         if ((devIdx = BluelabDevice.GetRemoteDeviceList(this.device).indexOf(remoteDev)) >= 0) {
            remoteDev = (BluelabRemoteXbeeDevice)BluelabDevice.GetRemoteDeviceList(this.device).remove(devIdx);
         }

         remoteDev.setDevicePresence(bluelab.connect.c.Enum_DevicePresence.INDISTINCT);
         BluelabDevice.GetRemoteDeviceCOWList(this.device).add(remoteDev);
         BluelabDevice.logger.info("Added device '{}'", remoteDev.getKeyCode());
         this.notifyDeviceChange();
      }

   }

   public final void b(BluelabRemoteXbeeDevice remoteDev) {
      if (BluelabDevice.GetRemoteDeviceCOWList(this.device).remove(remoteDev)) {
         remoteDev.setDevicePresence(bluelab.connect.c.Enum_DevicePresence.ALIEN);
         BluelabDevice.GetRemoteDeviceList(this.device).add(remoteDev);
         BluelabDevice.logger.info("Alienated device '{}'", remoteDev.getKeyCode());
         this.notifyDeviceChange();
         BluelabDevice.GetCSVWriter(this.device).prepareMessageList(WeirdEncoder.FilterCollect(BluelabDevice.GetRemoteDeviceCOWList(this.device), (var0) -> {
            return var0.isOnlineOffline();
         }));
         DevicesFileManager.SaveDevicesToFile(BluelabDevice.GetRemoteDeviceCOWList(this.device), BluelabDevice.GetDevicesJsonFile());
         BluelabDevice.GetDeviceManager(this.device).deregisterDevice(remoteDev);
      }

   }

   public final void clearDeviceIdentifiers() {
      BluelabDevice.GetRemoteDeviceCOWList(this.device).forEach((var0) -> {
         ((BluelabRemoteXbeeDevice)var0).clearIdAndIdOnAllControlTypes();
      });
      DevicesFileManager.SaveDevicesToFile(BluelabDevice.GetRemoteDeviceCOWList(this.device), BluelabDevice.GetDevicesJsonFile());
      BluelabDevice.logger.info("Device identifiers cleared");
   }

   private void sendDeviceTypeAndDataZeroRequests() {
      Iterator remoteDeviceListIterator = BluelabDevice.GetRemoteDeviceCOWList(this.device).iterator();

      while(remoteDeviceListIterator.hasNext()) {
          // loop list of devices, for any that are 'indistinct' send a AT packet DD request
         BluelabRemoteXbeeDevice bluelabRemoteDev;
         RemoteXBeeDevice remoteDev = (bluelabRemoteDev = (BluelabRemoteXbeeDevice)remoteDeviceListIterator.next()).getRemoteXbeeDevice();
         if (bluelabRemoteDev.updateGetDevicePresence() == bluelab.connect.c.Enum_DevicePresence.INDISTINCT && remoteDev != null) {
            RemoteATCommandPacket ddPacket = new RemoteATCommandPacket(1, remoteDev.get64BitAddress(), remoteDev.get16BitAddress(), 0, "DD", "");
            BluelabDevice.GetXbeeDevice(this.device).sendPacket(ddPacket, this);
            BluelabDevice.logger.info("Device type (DD) request for '{}'", bluelabRemoteDev.getKeyCode());
         }
      }
      // broadcast 'who are you' message
      bluelab.connect.g.ObfuscatedDeviceMessage whoAreYouMsg = new bluelab.connect.g.ObfuscatedDeviceMessage((byte)bluelab.connect.d.Enum_Command.WHO_ARE_YOU.getValue(), (byte[])null);
      BluelabDevice.GetXbeeDevice(this.device).sendBroadcastData(whoAreYouMsg.ConstructPacket());
      BluelabDevice.logger.info("Data 0 (D0) broadcast request");
   }

   private void sendDeviceTypeRequest() {
      Iterator remoteDeviceListIterator = BluelabDevice.GetRemoteDeviceCOWList(this.device).iterator();

      while(remoteDeviceListIterator.hasNext()) {
         BluelabRemoteXbeeDevice remoteDev;
         if ((remoteDev = (BluelabRemoteXbeeDevice)remoteDeviceListIterator.next()).getDeviceType().equals(bluelab.connect.c.Enum_DeviceType.EXTENDER) && remoteDev.getRemoteXbeeDevice() != null) {
            RemoteATCommandPacket ddPacket = new RemoteATCommandPacket(1, remoteDev.getRemoteXbeeDevice().get64BitAddress(), remoteDev.getRemoteXbeeDevice().get16BitAddress(), 0, "DD", "");
            BluelabDevice.GetXbeeDevice(this.device).sendPacket(ddPacket, this);
            BluelabDevice.logger.info("Device type (DD) request for '{}'", remoteDev.getKeyCode());
         }
      }

   }

   private void getSendMessagesToDevice() {
      Iterator messagesIterator = BluelabDevice.GetRemoteDeviceCOWList(this.device).iterator();

      while(true) {
         BluelabRemoteXbeeDevice remoteDev;
         do {
            do {
               do {
                  if (!messagesIterator.hasNext()) {
                     return;
                  }
               } while(!(remoteDev = (BluelabRemoteXbeeDevice)messagesIterator.next()).isOnline());
            } while(!remoteDev.isProController());
         } while(remoteDev.M());

         Iterator messageListIterator = remoteDev.getBluelabRemoteDevice().buildMessageList().iterator();

         while(messageListIterator.hasNext()) {
            bluelab.connect.g.DeviceMessage msg = (bluelab.connect.g.DeviceMessage)messageListIterator.next();
            BluelabDevice.GetXbeeDevice(this.device).sendDataToRemoteDevice(remoteDev, msg.ConstructPacket(), 1);
            BluelabDevice.logger.info("Command sent to device '{}' (0x{})", remoteDev.getKeyCode(), Integer.toHexString(msg.getCommand()));
            Thread.sleep(1000L);
         }
      }
   }

   private void updateRegisteredDevices() {
      try {
         List onlineOfflineDevicesAndNotExtenders = WeirdEncoder.FilterCollect(BluelabDevice.GetRemoteDeviceCOWList(this.device), (remoteDev) -> {
            return remoteDev.isOnlineOffline() && !remoteDev.getDeviceType().equals(bluelab.connect.c.Enum_DeviceType.EXTENDER);
         });
         BluelabDevice.GetDeviceManager(this.device).registerDevices(onlineOfflineDevicesAndNotExtenders);
         if (System.currentTimeMillis() > BluelabDevice.GetDeviceNextUpdateMillis(this.device)) {
            BluelabDevice.GetDeviceManager(this.device).updateRegisteredDevices(onlineOfflineDevicesAndNotExtenders);
            BluelabDevice.SetDeviceNextUpdateMillis(this.device, System.currentTimeMillis() + 20000L);
            return;
         }
      } catch (Throwable ex) {
         WeirdEncoder.ReportException(ex);
         BluelabDevice.logger.error("Error: {}", ex.toString());
      }

   }

   private void discoverDevices() {
      try {
         //Object var1 = BluelabConnectStick.readMutex;
         synchronized(BluelabConnectStick.readMutex) {
            if (!BluelabDevice.GetXbeeDevice(this.device).isOpen()) {
               this.requestStop();
            } else {
               boolean indistinct = WeirdEncoder.AnyMatch(BluelabDevice.GetRemoteDeviceCOWList(this.device), (dev) -> {
                  return dev.updateGetDevicePresence().equals(bluelab.connect.c.Enum_DevicePresence.INDISTINCT);
               });
               boolean offline = WeirdEncoder.AnyMatch(BluelabDevice.GetRemoteDeviceCOWList(this.device), (dev) -> {
                  return dev.updateGetDevicePresence().equals(bluelab.connect.c.Enum_DevicePresence.OFFLINE);
               });
               List<BluelabRemoteXbeeDevice> remoteDeviceList;
               if ((indistinct || offline) && !BluelabDevice.GetXbeeDevice(this.device).getNetwork().isDiscoveryRunning() && !(remoteDeviceList = WeirdEncoder.FilterCollect(BluelabDevice.GetRemoteDeviceCOWList(this.device), (dev) -> {
                  return !dev.isOnline();
               })).isEmpty()) {
                  ArrayList devicesToDiscover = new ArrayList();
                  remoteDeviceList.forEach((dev) -> {
                     devicesToDiscover.add(dev.getKeyCode());
                  });
                  BluelabDevice.logger.info("Devices to discover: {}", String.join(", ", devicesToDiscover));
                  if (BluelabDevice.GetXbeeDevice(this.device) != null) {
                     BluelabDevice.logger.info("Open network for joining");
                     BluelabDevice.GetXbeeDevice(this.device).setParameter("NJ", new byte[1]);
                     BluelabDevice.GetXbeeDevice(this.device).setParameter("NJ", new byte[]{30});
                  }

                  BluelabDevice.GetXbeeDevice(this.device).getNetwork().startDiscoveryProcess();
                  BluelabDevice.logger.info("Discovery started");
               }

               if (indistinct && System.currentTimeMillis() > BluelabDevice.k(this.device)) {
                  this.sendDeviceTypeAndDataZeroRequests();
                  BluelabDevice.b(this.device, System.currentTimeMillis() + 10000L);
               }

               if (!BluelabDevice.GetRemoteDeviceList(this.device).isEmpty() && !BluelabDevice.GetXbeeDevice(this.device).getNetwork().isDiscoveryRunning()) {
                  BluelabRemoteXbeeDevice remoteDev;
                  if ((remoteDev = (BluelabRemoteXbeeDevice)BluelabDevice.GetRemoteDeviceList(this.device).remove(BluelabDevice.GetRemoteDeviceList(this.device).size() - 1)).getRemoteXbeeDevice() != null) {
                     BluelabDevice.GetXbeeDevice(this.device).getNetwork().removeRemoteDevice(remoteDev.getRemoteXbeeDevice());
                    
                     try {
                        if (remoteDev != null) {
                           remoteDev.setRemoteXbeeDeviceParameter("NR", new byte[1]);
                        }
                     } catch (TimeoutException ex) {
                        ;
                     }
                  }

                  BluelabDevice.logger.info("Rejected alien device '{}'", remoteDev.getKeyCode());
                  BluelabDevice.GetStoreNumberOfNetworkDevices(this.device);
               }

               if (System.currentTimeMillis() > BluelabDevice.l(this.device)) {
                  Enum_Command cmd = (Enum_Command)BluelabDevice.GetCommandList(this.device).get(BluelabDevice.GetCounter());
                  BluelabDevice.SetCounter((BluelabDevice.GetCounter() + 1) % BluelabDevice.GetCommandList(this.device).size());
                  bluelab.connect.g.ObfuscatedDeviceMessage obfuscatedMessage = new bluelab.connect.g.ObfuscatedDeviceMessage((byte)cmd.getValue(), (byte[])null);
                  BluelabDevice.GetXbeeDevice(this.device).sendBroadcastData(obfuscatedMessage.ConstructPacket());
                  BluelabDevice.logger.info("Data requested from devices ({})", cmd.getText());
                  BluelabDevice.c(this.device, System.currentTimeMillis() + 7500L);
               }

               if (System.currentTimeMillis() > BluelabDevice.m(this.device)) {
                  this.getSendMessagesToDevice();
                  BluelabDevice.d(this.device, System.currentTimeMillis() + 15000L);
               }

               if (System.currentTimeMillis() > BluelabDevice.n(this.device)) {
                  this.sendDeviceTypeRequest();
                  BluelabDevice.e(this.device, System.currentTimeMillis() + 30000L);
               }
            }

            return;
         }
      } catch (TimeoutException ex) {
         WeirdEncoder.ReportException((Throwable)ex);
         BluelabDevice.logger.error("Exception: {}", ex.toString());
         if (BluelabDevice.getStateManager(this.device) != null) {
            BluelabDevice.getStateManager(this.device).incrementStateCounter("0000", BluelabDevice.GetXbeeDevice(this.device).get16BitAddress().toString(), bluelab.connect.h.Enum_TransmitReceiveState.TIMEOUT);
            return;
         }
      } catch (Throwable ex) {
         WeirdEncoder.ReportException(ex);
         BluelabDevice.logger.error("Error: {}", ex.toString());
      }

   }

   private void logJVMDetails() {
      Runtime var1 = Runtime.getRuntime();
      BluelabDevice.logger.info("[JVM] Free: {} MB, Total: {} MB, Max: {} MB", new Object[]{var1.freeMemory() >> 20, var1.totalMemory() >> 20, var1.maxMemory() >> 20});
   }

   private void j() {
      BluelabDevice.getFirmwareUpdater(this.device).checkForUpdates((List)BluelabDevice.GetRemoteDeviceCOWList(this.device));
   }

   public final void run() {
      BluelabDevice.logger.info("Started");

      try {
         BluelabDevice.GetXbeeDevice(this.device).addDataListener(this);
         BluelabDevice.GetXbeeDevice(this.device).addPacketListener(this);
         BluelabDevice.GetXbeeDevice(this.device).addModemStatusListener(this);
         XBeeNetwork var1;
         (var1 = BluelabDevice.GetXbeeDevice(this.device).getNetwork()).addDiscoveryListener(this);
         var1.setDiscoveryTimeout(15000L);
         BluelabDevice.getScheduledExecutor(this.device).scheduleWithFixedDelay(this::discoverDevices, 0L, 1000L, TimeUnit.MILLISECONDS);
         BluelabDevice.getScheduledExecutor(this.device).scheduleWithFixedDelay(this::updateRegisteredDevices, 20000L, 5000L, TimeUnit.MILLISECONDS);
         BluelabDevice.getScheduledExecutor(this.device).scheduleWithFixedDelay(this::logJVMDetails, 0L, 10000L, TimeUnit.MILLISECONDS);
         BluelabDevice.getScheduledExecutor(this.device).scheduleWithFixedDelay(this::j, 30L, 86400L, TimeUnit.SECONDS);
         BluelabDevice.getScheduledExecutor(this.device).awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
      } catch (Throwable ex) {
         WeirdEncoder.ReportException(ex);
         BluelabDevice.logger.error("Aborting: {}", ex.toString());
      } finally {
         if (BluelabDevice.getCallbackInterface(this.device) != null) {
            BluelabDevice.getCallbackInterface(this.device).update();
         }

         try {
            this.closeNetworkForJoining();
            BluelabDevice.GetXbeeDevice(this.device).getNetwork().removeDiscoveryListener(this);
            BluelabDevice.GetXbeeDevice(this.device).removeDataListener(this);
            if (!BluelabDevice.GetXbeeDevice(this.device).getNetwork().isDiscoveryRunning()) {
               BluelabDevice.GetXbeeDevice(this.device).getNetwork().stopDiscoveryProcess();
            }

            BluelabDevice.GetXbeeDevice(this.device).reset();
         } catch (XBeeException ex) {
            WeirdEncoder.ReportException((Throwable)ex);
            BluelabDevice.logger.error("Exception: {}", ex.toString());
         }

      }

      BluelabDevice.logger.info("Done");
   }

   public final void requestStop() {
      BluelabDevice.logger.info("Stop requested");
      BluelabDevice.getScheduledExecutor(this.device).shutdown();
   }

   private void closeNetworkForJoining() {
      if (BluelabDevice.GetXbeeDevice(this.device) != null) {
         BluelabDevice.logger.info("Close network for joining");
         BluelabDevice.GetXbeeDevice(this.device).setParameter("NJ", new byte[1]);
      }

   }

   public final void deviceDiscovered(RemoteXBeeDevice dev) {
      try {
         if (dev.getNodeID() == null) {
            dev.readDeviceInfo();
         }
      } catch (Throwable ex) {
         WeirdEncoder.ReportException(ex);
         BluelabDevice.logger.error("Error: {}", ex.toString());
      }

      BluelabRemoteXbeeDevice dev2 = this.createBluelabRemoteXbeeDevice(dev);
      BluelabDevice.logger.info("Discovered device '{}'", dev2.getKeyCode());
   }

   private BluelabRemoteXbeeDevice createBluelabRemoteXbeeDevice(RemoteXBeeDevice remoteXbeeDev) {
      if (remoteXbeeDev == null) {
         throw new NullPointerException("Invalid XBee object");
      } else {
         BluelabRemoteXbeeDevice blRemoteXbeeDev = new BluelabRemoteXbeeDevice(remoteXbeeDev);
         int blRemoteXbeeDevIdx;
         if ((blRemoteXbeeDevIdx = BluelabDevice.GetRemoteDeviceCOWList(this.device).indexOf(blRemoteXbeeDev)) >= 0) {
             // in cow list
            (blRemoteXbeeDev = (BluelabRemoteXbeeDevice)BluelabDevice.GetRemoteDeviceCOWList(this.device).get(blRemoteXbeeDevIdx)).setRemoteXbeeDevice(remoteXbeeDev);
            String id = remoteXbeeDev.getNodeID();
            if (id != null && !id.equals(blRemoteXbeeDev.getId())) {
                blRemoteXbeeDev.setName(id);
            }
         } else if ((blRemoteXbeeDevIdx = BluelabDevice.GetRemoteDeviceList(this.device).indexOf(blRemoteXbeeDev)) >= 0) {
            // in remote device list
             ((BluelabRemoteXbeeDevice)BluelabDevice.GetRemoteDeviceList(this.device).get(blRemoteXbeeDevIdx)).setRemoteXbeeDevice(remoteXbeeDev);
         } else {
             // not seen before, put in list
            BluelabDevice.GetRemoteDeviceList(this.device).add(blRemoteXbeeDev);
         }

         return blRemoteXbeeDev;
      }
   }

   public final void discoveryError(String var1) {
      BluelabDevice.logger.info("Discovery error: {}", var1);
   }

   public final void discoveryFinished(String var1) {
      try {
         this.closeNetworkForJoining();
      } catch (XBeeException ex) {
         BluelabDevice.logger.error("Could not close network for joining: {}", ex.toString());
      }

      if (var1 != null) {
         BluelabDevice.logger.info("Discovery finished: {}", var1);
      } else {
         BluelabDevice.logger.info("Discovery finished.");
      }

      BluelabDevice.GetStoreNumberOfNetworkDevices(this.device);
   }

   public final void dataReceived(XBeeMessage var1) {
      BluelabRemoteXbeeDevice dev = this.createBluelabRemoteXbeeDevice(var1.getDevice());
      BluelabDevice.logger.info("Data received from device '{}'", ((BluelabRemoteXbeeDevice)dev).g());
      byte[] msgData = var1.getData();
      if (BluelabDevice.GetRemoteDeviceCOWList(this.device).contains(dev)) {
         boolean processed = false;
         if (dev.updateGetDevicePresence().equals(bluelab.connect.c.Enum_DevicePresence.INDISTINCT)) {
            bluelab.connect.c.Enum_DeviceVersion deviceVersion = bluelab.connect.c.b.BluelabRemoteDevice.ParseDeviceVersion(msgData);
            dev.setDeviceVersion(deviceVersion);
            if (!deviceVersion.equals(bluelab.connect.c.Enum_DeviceVersion.UNKNOWN) && !deviceVersion.equals(bluelab.connect.c.Enum_DeviceVersion.UNSUPPORTED)) {
               bluelab.connect.c.Enum_DeviceType deviceType = deviceVersion.getDeviceType();
               BluelabRemoteXbeeDevice dev2 = dev;
               if (deviceType.equals(bluelab.connect.c.Enum_DeviceType.GUARDIAN)) {
                  dev2 = new BluelabGuardianRemoteXbeeDevice(dev2);
               } else if (deviceType.equals(bluelab.connect.c.Enum_DeviceType.PH_CONTROLLER)) {
                  dev2 = new BluelabPHControllerRemoteXbeeDevice(dev2);
               } else if (deviceType.equals(bluelab.connect.c.Enum_DeviceType.PRO_CONTROLLER)) {
                  dev2 = new BluelabProControllerRemoteXbeeDevice(dev2);
               }

               dev = dev2;
               dev2.setBluelabRemoteDevice(InstantiateDeviceFromVersion.InstantiateDeviceFromVersion(deviceVersion, dev2));
               if (dev2.isDeviceTypeKnown()) {
                  processed = true;
                  BluelabDevice.logger.info("Classified '{}' as a {}", dev2.getKeyCode(), dev2.getDeviceTypeUiText());
               }
            }
         }

         if (dev.updateGetDevicePresence().equals(bluelab.connect.c.Enum_DevicePresence.ONLINE)) {
            try {
               if (dev.processBytes(msgData) > 0) {
                  this.c(dev);
               }
            } catch (bluelab.connect.c.c.BluelabException ex) {
               BluelabDevice.logger.error("Data processing (setting) error: {}", ex.toString());
            }

            if (BluelabDevice.getStateManager(this.device) != null) {
               BluelabDevice.getStateManager(this.device).incrementStateCounter(((BluelabRemoteXbeeDevice)dev).getKeyCode(), ((BluelabRemoteXbeeDevice)dev).get16BitAddress(), bluelab.connect.h.Enum_TransmitReceiveState.RECEIVE);
            }
         }

         if (processed) {
            BluelabDevice.GetRemoteDeviceCOWList(this.device).set(BluelabDevice.GetRemoteDeviceCOWList(this.device).indexOf(dev), dev);
            DevicesFileManager.SaveDevicesToFile(BluelabDevice.GetRemoteDeviceCOWList(this.device), BluelabDevice.GetDevicesJsonFile());
            this.c((BluelabRemoteXbeeDevice)dev);
         }

         BluelabDevice.GetCSVWriter(this.device).prepareMessageList(WeirdEncoder.FilterCollect(BluelabDevice.GetRemoteDeviceCOWList(this.device), (var0) -> {
            return var0.isOnlineOffline();
         }));
      }

   }

    public final void packetReceived(XBeePacket xbeePacket) {
        RemoteATCommandResponsePacket atPacket = (RemoteATCommandResponsePacket) xbeePacket;
        String keyCode = WeirdEncoder.KeyCodeFrom64BitAddress(atPacket.get64bitSourceAddress().getValue());
        BluelabRemoteXbeeDevice dev = new BluelabRemoteXbeeDevice(keyCode);
        BluelabDevice.logger.info("Packet received from '{}' [{}]", keyCode, HexUtils.prettyHexString(HexUtils.byteArrayToHexString(atPacket.getPacketData())));
        if (atPacket.getCommand().equals("DD")) {
            int remoteDeviceIndex = BluelabDevice.GetRemoteDeviceCOWList(this.device).indexOf(dev);
            if (remoteDeviceIndex >= 0) {
                // created the object above from keycode. 
                // now we overwrite it with the device from the COW list?
                dev = (BluelabRemoteXbeeDevice)BluelabDevice.GetRemoteDeviceCOWList(this.device).get(remoteDeviceIndex);
                if (dev.updateGetDevicePresence().equals(bluelab.connect.c.Enum_DevicePresence.INDISTINCT)) {
                    if (ByteBuffer.wrap(atPacket.getCommandValue()).getInt() == 196620) {
                        // y is special somehow
                        BluelabExtenderDevice specialDev = new BluelabExtenderDevice(dev);
                        specialDev.setBluelabRemoteDevice(InstantiateDeviceFromVersion.InstantiateDeviceFromVersion(bluelab.connect.c.Enum_DeviceVersion.EXTENDER_V1, specialDev));
                        BluelabDevice.GetRemoteDeviceCOWList(this.device).set(remoteDeviceIndex, specialDev);
                        dev = (BluelabRemoteXbeeDevice) BluelabDevice.GetRemoteDeviceCOWList(this.device).get(remoteDeviceIndex);
                        DevicesFileManager.SaveDevicesToFile(BluelabDevice.GetRemoteDeviceCOWList(this.device), BluelabDevice.GetDevicesJsonFile());
                        this.c(dev);
                        BluelabDevice.logger.info("Classified '{}' as a {}", dev.getKeyCode(), dev.getDeviceTypeUiText());
                        return;
                    }
                } else {
                    dev.setIdent1(dev.getIdent1());
                    this.c(dev);
                }
            }
        }

    }

   public final void modemStatusEventReceived(ModemStatusEvent var1) {
      BluelabDevice.logger.info("Modem status event received: {}", var1.toString());
   }

   // $FF: synthetic method
   BluelabDeviceManager(BluelabDevice dev, byte var2) {
      this(dev);
   }
}