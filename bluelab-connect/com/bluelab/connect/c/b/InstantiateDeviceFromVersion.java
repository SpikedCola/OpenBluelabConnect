package bluelab.connect.c.b;

import bluelab.connect.c.BluelabRemoteXbeeDevice;

public final class InstantiateDeviceFromVersion {
   public static BluelabRemoteDevice InstantiateDeviceFromVersion(bluelab.connect.c.Enum_DeviceVersion version, BluelabRemoteXbeeDevice dev) {
      Object var2 = null;
      if (version.equals(bluelab.connect.c.Enum_DeviceVersion.PH_CONTROLLER_V1)) {
         var2 = new BluelabPhControllerV1Device(dev);
      } else if (version.equals(bluelab.connect.c.Enum_DeviceVersion.PRO_CONTROLLER_V1)) {
         var2 = new BluelabProControllerDevice(dev);
      } else if (version.equals(bluelab.connect.c.Enum_DeviceVersion.GUARDIAN_V1)) {
         var2 = new BluelabGuardianRemoteDevice(dev);
      } else if (version.equals(bluelab.connect.c.Enum_DeviceVersion.EXTENDER_V1)) {
         var2 = new BluelabExtenderDevice(dev);
      } else if (version.equals(bluelab.connect.c.Enum_DeviceVersion.PH_CONTROLLER_V2) || version.equals(bluelab.connect.c.Enum_DeviceVersion.PRO_CONTROLLER_V2) || version.equals(bluelab.connect.c.Enum_DeviceVersion.PRO_BOOTLOADER_V1)) {
         var2 = new BluelabPhControllerV2Device(dev);
      }

      return (BluelabRemoteDevice)var2;
   }
}