package bluelab.connect.f;

import bluelab.connect.c.PeripodWithPumps;
import bluelab.connect.c.BluelabRemoteXbeeDevice;
import bluelab.connect.d.WeirdEncoder;
import bluelab.connect.model.SettingFileModel;
import java.io.File;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public final class CSVWriter {
   private final SettingFileModel settingFileModel;
   private List<BluelabRemoteXbeeDevice> bluelabRemoteXbeeDeviceList;
   private List<String> messageList;
   private String logDataDirectory;
   private String logFileName;
   private Writer writer;
   private boolean g;
   private boolean firstPass;
   private final DateTimeFormatter dateTimeFormatter;
   private final String csvColumnSeparatorCharacter;
   private final ScheduledExecutorService scheduledThreadPool;
   private ScheduledFuture<?> scheduledFutureTask;
   private Object mutex = new Object();

   public CSVWriter(SettingFileModel settingFileModel) {
      this.settingFileModel = settingFileModel;
      this.bluelabRemoteXbeeDeviceList = new ArrayList();
      this.messageList = new ArrayList();
      this.mkdirRecursive(settingFileModel.logDataDirectory);
      this.logFileName = "";
      this.wroteHeaders();
      this.dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
      this.csvColumnSeparatorCharacter = ",";
      this.scheduledThreadPool = Executors.newScheduledThreadPool(1);
   }

   private void wroteHeaders() {
      this.g = false;
      this.firstPass = false;
   }

   public final void prepareMessageList(List<BluelabRemoteXbeeDevice> bluelabRemoteXbeeDeviceList) {
      ArrayList msgList = new ArrayList();
      String tz = ZoneId.systemDefault().toString();
      msgList.add("Time (" + tz + ")");
      msgList.add("Conductivity unit");
      msgList.add("Temperature unit");
      Iterator remoteDevIterator = bluelabRemoteXbeeDeviceList.iterator();

      while(remoteDevIterator.hasNext()) {
         BluelabRemoteXbeeDevice remoteDev = (BluelabRemoteXbeeDevice)remoteDevIterator.next();
         remoteDev.getControlTypeControlTypeManagerMap().forEach((controlType, controlTypeManager) -> {
            msgList.add(remoteDev.r() + " " + controlTypeManager.b());
         });
         remoteDev.getPeripodWithPumpsList().forEach((idx, peripod) -> {
            ((PeripodWithPumps)peripod).getPumps().forEach((pump) -> {
               msgList.add(pump.getName() + " run time");
            });
         });
      }

      synchronized(this.mutex) {
         if (!this.firstPass) {
            this.firstPass = !this.messageList.equals(msgList);
         }

         this.messageList = msgList;
         this.bluelabRemoteXbeeDeviceList = bluelabRemoteXbeeDeviceList;
      }
   }

   public final void mkdirRecursive(String dir) {
      File file;
      if (!(file = new File(dir)).exists()) {
         file.mkdirs();
      }

      synchronized(this.mutex) {
         this.logDataDirectory = dir;
      }
   }

   public final LocalDateTime scheduleWritingToCSV(long var1) {
      if (this.scheduledFutureTask != null) {
         this.scheduledFutureTask.cancel(false);
      }

      LocalDateTime now2;
      LocalDateTime now = now2 = LocalDateTime.now();
      long minutes = var1 / 60L;
      LocalDateTime now3 = now;
      long var10 = now.withNano(0).until(now3.plusHours(1L).withMinute(0).withSecond(0).withNano(0), ChronoUnit.SECONDS);
      long seconds = minutes * 60L;
      now3 = now3.withNano(0);
      if (minutes > 0L) {
         now3 = now3.plusSeconds((var10 + seconds) % seconds);
      }

      long var5 = now2.until(now3, ChronoUnit.MILLIS) + 100L;
      this.scheduledFutureTask = this.scheduledThreadPool.scheduleAtFixedRate(this::writeToCsv, var5, var1 * 1000L, TimeUnit.MILLISECONDS);
      return now3;
   }

   public final void requestWaitShutdown() {
      try {
         this.scheduledThreadPool.shutdown();
         this.scheduledThreadPool.awaitTermination(100L, TimeUnit.MILLISECONDS);
      } catch (Throwable var4) {
         throw var4;
      } finally {
         if (this.writer != null) {
            this.writer.close();
         }

      }

   }

   protected final void writeToCsv() {
      if (!this.bluelabRemoteXbeeDeviceList.isEmpty()) {
         try {
            synchronized(this.mutex) {
               File logFile = new File(this.logDataDirectory + File.separator + this.logFileName);
               if (this.firstPass || this.logFileName.isEmpty() || !logFile.exists() || this.writer == null) {
                  if (this.writer != null) {
                     this.writer.close();
                  }

                  LocalDateTime now = LocalDateTime.now();
                  DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmmss");
                  this.logFileName = now.format(dtFormatter) + ".csv";
                  (logFile = new File(this.logDataDirectory + File.separator + this.logFileName)).getParentFile().mkdirs();
                  this.writer = Files.newBufferedWriter(logFile.toPath(), StandardCharsets.UTF_8, new OpenOption[]{StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.APPEND});
                  this.writer.write(String.join(this.csvColumnSeparatorCharacter, this.messageList));
                  this.writer.write("\n");
                  this.wroteHeaders();
               }

               List dataToWrite = this.getConductivityTemperatureReadings();
               this.writer.write(String.join(this.csvColumnSeparatorCharacter, dataToWrite));
               this.writer.write("\n");
            }

            this.writer.flush();
            return;
         } catch (Throwable ex) {
            bluelab.connect.d.WeirdEncoder.ReportException(ex);
         }
      }

   }

   private List<String> getConductivityTemperatureReadings() {
      ArrayList ret = new ArrayList();
      LocalDateTime now = LocalDateTime.now();
      ret.add(now.format(this.dateTimeFormatter));
      ret.add(this.settingFileModel.conductivityUnit);
      ret.add(this.settingFileModel.temperatureUnit.substring(1));
      Iterator remoteDeviceListIterator = this.bluelabRemoteXbeeDeviceList.iterator();

      while(remoteDeviceListIterator.hasNext()) {
         BluelabRemoteXbeeDevice remoteDev = (BluelabRemoteXbeeDevice)remoteDeviceListIterator.next();
         remoteDev.getControlTypeControlTypeManagerMap().forEach((controlType, controlTypeManager) -> {
            ret.add(remoteDev.getCurrentValueOfControlType(controlType, this.settingFileModel));
         });
         remoteDev.getPeripodWithPumpsList().forEach((idx, peripod) -> {
            ((PeripodWithPumps)peripod).getPumps().forEach((pump) -> {
               ret.add(pump.getCurrentValue());
            });
         });
      }

      return ret;
   }
}