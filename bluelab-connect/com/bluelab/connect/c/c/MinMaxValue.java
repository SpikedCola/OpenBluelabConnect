package bluelab.connect.c.c;

public final class MinMaxValue {
   public double minValue;
   public double maxValue;

   public MinMaxValue() {
      this.minValue = 0.0D;
      this.maxValue = 0.0D;
   }

   public MinMaxValue(MinMaxValue var1) {
      this.minValue = var1.minValue;
      this.maxValue = var1.maxValue;
   }
}