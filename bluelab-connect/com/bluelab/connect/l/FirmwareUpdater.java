package bluelab.connect.l;

import bluelab.connect.c.Enum_DeviceVersion;
import bluelab.connect.c.BluelabRemoteXbeeDevice;
import bluelab.connect.d.WeirdEncoder;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FirmwareUpdater {
   private static Logger logger = LoggerFactory.getLogger(FirmwareUpdater.class);
   private Interface_FirmwareUpdateNotify firmwareUpdateCallback;

   public final void setCallback(Interface_FirmwareUpdateNotify firmwareUpdateCallback) {
      this.firmwareUpdateCallback = firmwareUpdateCallback;
   }

   public final void checkForUpdates(List<BluelabRemoteXbeeDevice> devices) {
      try {
         logger.info("Checking for firmware updates...");
         List deviceUpdateList = GetDeviceUpdateList();
         ArrayList updates = new ArrayList();
         Iterator updateListIterator = deviceUpdateList.iterator();

         while(updateListIterator.hasNext()) {
            DeviceVersion ver = (DeviceVersion)updateListIterator.next();
            if (devices.stream().anyMatch(CompareDeviceVersion(ver))) {
               updates.add(ver);
            }
         }

         if (this.firmwareUpdateCallback != null && !updates.isEmpty()) {
            this.firmwareUpdateCallback.notifyFirmwareUpdates(updates);
            return;
         }
      } catch (Throwable ex) {
         WeirdEncoder.ReportException(ex);
         logger.error("Error: {}", ex.toString());
      }

   }

   public static DeviceVersion GetPossibleUpdate(BluelabRemoteXbeeDevice dev) {
      Iterator iterator = GetDeviceUpdateList().iterator();

      while(iterator.hasNext()) {
         DeviceVersion version = (DeviceVersion)iterator.next();
         if (CompareDeviceVersion(version).test(dev)) {
            return version;
         }
      }

      return null;
   }

   public static String getPossibleUpdateFirmwareFile(BluelabRemoteXbeeDevice dev) {
      Iterator iterator = GetDeviceUpdateList().iterator();

      while(iterator.hasNext()) {
         DeviceVersion version = (DeviceVersion)iterator.next();
         if (CompareDeviceVersion(version).test(dev)) {
            return version.firmwareFile;
         }
      }

      return null;
   }

   private static List<DeviceVersion> GetDeviceUpdateList() {
      ArrayList ret = new ArrayList();
      ret.add(new DeviceVersion(EnumSet.of(Enum_DeviceVersion.PRO_CONTROLLER_V2, Enum_DeviceVersion.PRO_BOOTLOADER_V1), "5.00.0024", "/resources/updates/V5.0.24_procontroller_update.blb"));
      return ret;
   }

   private static Predicate<? super BluelabRemoteXbeeDevice> CompareDeviceVersion(DeviceVersion deviceVersion) {
      VersionComparer cmp = new VersionComparer(deviceVersion.versionString);
      return (var2) -> {
         return deviceVersion.supportedDeviceVersions.contains(var2.getDeviceVersion()) && cmp.compareTo(new VersionComparer(var2.getIdent2())) > 0;
      };
   }
}