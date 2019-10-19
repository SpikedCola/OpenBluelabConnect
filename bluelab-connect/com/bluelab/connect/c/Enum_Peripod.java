package bluelab.connect.c;

public enum Enum_Peripod implements bluelab.connect.j.Interface_GetText {
   UNKNOWN("Unknown"),
   PERIPOD("Peripod");

   private final String text;

   private Enum_Peripod(String text) {
      this.text = text;
   }

   public final String getText() {
      return this.text;
   }
}