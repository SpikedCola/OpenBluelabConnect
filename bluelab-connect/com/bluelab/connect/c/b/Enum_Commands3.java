package bluelab.connect.c.b;

enum Enum_Commands3 {
   WHO_ARE_YOU((byte)0),
   GET_DATA_1((byte)1),
   GET_DATA_2((byte)2),
   GET_DATA_3((byte)3),
   SET_REQUIRED((byte)10),
   SET_MODE((byte)11),
   SET_EC_ON_OFF_TIME((byte)13),
   SET_TEMP_ON_OFF_TIME((byte)14),
   SET_PH_ON_OFF_TIME((byte)15),
   SET_EC_ALARM((byte)16),
   SET_TEMP_ALARM((byte)17),
   SET_PH_ALARM((byte)18),
   SET_LOW_EC_LOCKOUT((byte)19),
   SET_IC_LOCKOUT((byte)20),
   APPLY_SETTINGS((byte)100),
   NONE((byte)-1);

   private final byte value;
   private static Enum_Commands3[] cachedValues = null;

   private Enum_Commands3(byte var3) {
      this.value = var3;
   }

   public final byte getValue() {
      return this.value;
   }

   public static Enum_Commands3 GetCommandByInt(int var0) {
      Enum_Commands3 var1 = NONE;
      if (cachedValues == null) {
         cachedValues = values();
      }

      Enum_Commands3[] var5 = cachedValues;
      int var4 = cachedValues.length;

      for(int var3 = 0; var3 < var4; ++var3) {
         Enum_Commands3 var2;
         if ((var2 = var5[var3]).value == var0) {
            var1 = var2;
            break;
         }
      }

      return var1;
   }
}