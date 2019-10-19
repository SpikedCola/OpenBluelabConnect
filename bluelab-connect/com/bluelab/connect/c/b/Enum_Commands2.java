package bluelab.connect.c.b;

enum Enum_Commands2 {
   WHO_ARE_YOU((byte)0),
   GET_DATA_1((byte)1),
   GET_DATA_2((byte)2),
   GET_DATA_3((byte)3),
   SET_MODE((byte)10),
   SET_REQUIRED((byte)11),
   SET_ALARM_ENABLED((byte)12),
   SET_LOW_ALARM((byte)13),
   SET_HIGH_ALARM((byte)14),
   SET_ON_TIME((byte)15),
   SET_OFF_TIME((byte)16),
   APPLY_SETTINGS((byte)100),
   NONE((byte)-1);

   private final byte value;
   private static Enum_Commands2[] cachedValues = null;

   private Enum_Commands2(byte value) {
      this.value = value;
   }

   public final byte getValue() {
      return this.value;
   }

   public static Enum_Commands2 GetCommandByInt(int var0) {
      Enum_Commands2 command = NONE;
      if (cachedValues == null) {
         cachedValues = values();
      }

      Enum_Commands2[] cache = cachedValues;
      int length = cachedValues.length;

      for(int idx = 0; idx < length; ++idx) {
         if (cache[idx].value == var0) {
            command = cache[idx];
            break;
         }
      }

      return command;
   }
}