package bluelab.connect.k;

public enum Enum_ECUnit implements bluelab.connect.j.Interface_GetText, Interface_ECFactor {
   EC("EC", 1.0D),
   CF("CF", 10.0D),
   TDS("ppm500", 500.0D),
   PPM("ppm700", 700.0D);

   private final String text;
   private final double factor;

   private Enum_ECUnit(String text, double factor) {
      this.text = text;
      this.factor = factor;
   }

   public final String getText() {
      return this.text;
   }

   public final String toString() {
      return this.text;
   }

   public final double getFactor() {
      return this.factor;
   }

   public static Enum_ECUnit GetECType() {
      return EC;
   }
}