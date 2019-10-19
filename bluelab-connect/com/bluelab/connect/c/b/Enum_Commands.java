package bluelab.connect.c.b;

enum Enum_Commands {
   WHO_ARE_YOU((byte)0),
   GET_DEVICE_INFO((byte)1),
   GET_DEVICE_SETTINGS((byte)2),
   GET_DEVICE_CONTROL_ACTIVITY((byte)3),
   GET_DEVICE_POD_SETUP((byte)4),
   SET_DEVICE_SETTINGS((byte)10),
   RESTART_DEVICE_BOOTLOADER((byte)49),
   POD_WHO_ARE_YOU((byte)50),
   GET_POD_INFO((byte)51),
   GET_POD_SETTINGS((byte)52),
   DISPLAY_POD_ID((byte)53),
   GET_POD_PUMP_RATES((byte)54),
   SET_POD_NAME((byte)60),
   SET_POD_SETTINGS((byte)61),
   RESTART_POD_BOOTLOADER((byte)99),
   NONE((byte)-1);

   private final byte value;
   private static Enum_Commands[] cachedValues = null;

   private Enum_Commands(byte value) {
      this.value = value;
   }

   public final byte getValue() {
      return this.value;
   }

   public static Enum_Commands getCommandByInt(int cmdInt) {
      Enum_Commands ret = NONE;
      if (cachedValues == null) {
         cachedValues = values();
      }

      Enum_Commands[] commands = cachedValues;
      int len = cachedValues.length;

      for(int idx = 0; idx < len; ++idx) {
         Enum_Commands command;
         if ((command = commands[idx]).value == cmdInt) {
            ret = command;
            break;
         }
      }

      return ret;
   }
}