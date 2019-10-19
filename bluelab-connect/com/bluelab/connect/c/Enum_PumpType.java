package bluelab.connect.c;

public enum Enum_PumpType implements bluelab.connect.j.Interface_GetText {
   OFF("Off"),
   PH("pH"),
   EC("EC");

   private final String text;

   private Enum_PumpType(String text) {
      this.text = text;
   }

   public final String getText() {
      return this.text;
   }

   public final String toString() {
      return this.text;
   }

   public static Enum_PumpType GetOff() {
      return OFF;
   }
}