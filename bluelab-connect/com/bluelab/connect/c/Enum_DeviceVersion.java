package bluelab.connect.c;

public enum Enum_DeviceVersion {
   UNKNOWN("Unknown", Enum_DeviceType.UNKNOWN),
   UNSUPPORTED("Unsupported", Enum_DeviceType.UNKNOWN),
   EXTENDER_V1("EXT_V1", Enum_DeviceType.EXTENDER),
   GUARDIAN_V1("\t\t", Enum_DeviceType.GUARDIAN),
   PH_CONTROLLER_V1("PHC_V1", Enum_DeviceType.PH_CONTROLLER),
   PH_CONTROLLER_V2("PHC_V2", Enum_DeviceType.PH_CONTROLLER),
   PRO_CONTROLLER_V1("DTC2", Enum_DeviceType.PRO_CONTROLLER),
   PRO_CONTROLLER_V2("PRO_V2", Enum_DeviceType.PRO_CONTROLLER),
   PRO_BOOTLOADER_V1("PRO_BL_V1", Enum_DeviceType.PRO_CONTROLLER);

   private final String id;
   private final Enum_DeviceType deviceType;
   private static Enum_DeviceVersion[] cachedValues = null;

   private Enum_DeviceVersion(String id, Enum_DeviceType type) {
      this.id = id;
      this.deviceType = type;
   }

   public final String getId() {
      return this.id;
   }

   public final Enum_DeviceType getDeviceType() {
      return this.deviceType;
   }

   public static Enum_DeviceVersion GetByID(String id, boolean showAsUnsupported) {
      Enum_DeviceVersion ret = showAsUnsupported ? UNSUPPORTED : UNKNOWN;
      if (cachedValues == null) {
         cachedValues = values();
      }

      for(int idx = 0; idx < cachedValues.length; ++idx) {
         Enum_DeviceVersion val = cachedValues[idx];
         if (id.contains(val.id)) {
            ret = val;
            break;
         }
      }

      return ret;
   }
}