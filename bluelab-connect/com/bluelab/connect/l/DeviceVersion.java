package bluelab.connect.l;

import bluelab.connect.c.Enum_DeviceVersion;
import java.util.EnumSet;

public final class DeviceVersion {
   public final EnumSet<Enum_DeviceVersion> supportedDeviceVersions;
   public final String versionString;
   public final String firmwareFile;

   public DeviceVersion(EnumSet<Enum_DeviceVersion> supportedDeviceVersions, String versionString, String firmwareFile) {
      this.supportedDeviceVersions = supportedDeviceVersions;
      this.versionString = versionString;
      this.firmwareFile = firmwareFile;
   }
}