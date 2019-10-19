package bluelab.connect.k;

public enum Enum_TemperatureUnit implements bluelab.connect.j.Interface_GetText {
   DEG_C("°C"),
   DEG_F("°F");

   private final String text;

   private Enum_TemperatureUnit(String text) {
      this.text = text;
   }

   public final String getText() {
      return this.text;
   }

   public final String toString() {
      return this.text;
   }

   public static Enum_TemperatureUnit GetTemperatureUnitC() {
      return DEG_C;
   }
}