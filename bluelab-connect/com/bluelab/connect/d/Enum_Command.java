package bluelab.connect.d;

public enum Enum_Command implements bluelab.connect.j.Interface_GetText, bluelab.connect.j.Interface_GetValue {
   WHO_ARE_YOU((byte)0, "Who are you?"),
   DATA_1((byte)1, "Data 1 request"),
   DATA_2((byte)2, "Data 2 request"),
   DATA_3((byte)3, "Data 3 request"),
   NONE((byte)-1, "None");

   private final byte value;
   private final String text;

   private Enum_Command(byte value, String text) {
      this.value = value;
      this.text = text;
   }

   public final int getValue() {
      return this.value;
   }

   public final String getText() {
      return this.text;
   }
}