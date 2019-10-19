package bluelab.connect.c.d;

public enum r implements bluelab.connect.j.Interface_GetText, bluelab.connect.j.Interface_GetValue {
   SUCCESS((byte)1, "Success"),
   UNSUPPORTED((byte)-1, "Unsupported"),
   ADDRESS_ERROR((byte)-2, "Address error");

   private final byte value;
   private final String text;

   private r(byte var3, String var4) {
      this.value = var3;
      this.text = var4;
   }

   public final int getValue() {
      return this.value;
   }

   public final String getText() {
      return this.text;
   }
}