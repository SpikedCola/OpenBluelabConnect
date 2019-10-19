package bluelab.connect.c;

public enum Enum_ChangeType implements bluelab.connect.j.Interface_GetText {
   DEVICE_SETTING("Device setting change"),
   POD_STATUS("Pod status change"),
   POD_SETTING("Pod setting change");

   private final String text;

   private Enum_ChangeType(String text) {
      this.text = text;
   }

   public final String getText() {
      return this.text;
   }
}