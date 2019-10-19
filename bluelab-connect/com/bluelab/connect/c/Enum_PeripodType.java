package bluelab.connect.c;

public enum Enum_PeripodType implements bluelab.connect.j.Interface_GetText, bluelab.connect.j.Interface_GetValue {
   UNKNOWN((byte)-1, Enum_Peripod.UNKNOWN, "Unknown"),
   PERIPOD_TEST((byte)0, Enum_Peripod.PERIPOD, "Test peripod"),
   PERIPOD_M3((byte)1, Enum_Peripod.PERIPOD, "Peripod M3"),
   PERIPOD_L3((byte)2, Enum_Peripod.PERIPOD, "Peripod L3"),
   PERIPOD_L4((byte)3, Enum_Peripod.PERIPOD, "Peripod L4"),
   PERIPOD_M4((byte)4, Enum_Peripod.PERIPOD, "Peripod M4");

   private final byte value;
   private final Enum_Peripod podType;
   private final String text;

   private Enum_PeripodType(byte value, Enum_Peripod peripod, String var5) {
      this.value = value;
      this.podType = peripod;
      this.text = var5;
   }

   public final Enum_Peripod getPodType() {
      return this.podType;
   }

   public final int getValue() {
      return this.value;
   }

   public final String getText() {
      return this.text;
   }
}