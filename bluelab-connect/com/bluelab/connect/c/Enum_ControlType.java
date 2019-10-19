package bluelab.connect.c;

public enum Enum_ControlType {
   NONE(0.0D, 0.0D, 0.0D, "None", "none"),
   TIME(0.0D, 1000000.0D, 1.0D, "Time", "time"),
   CONDUCTIVITY(0.0D, 5.0D, 0.1D, "Conductivity", "conductivity"),
   PH(0.0D, 14.0D, 0.1D, "pH", "ph"),
   TEMPERATURE(0.0D, 50.0D, 1.0D, "Temperature", "temperature");

   private final double minValue = 0.0D;
   private final double maxValue;
   private final double deltaValue;
   private final String uiText;
   private final String apiText;

   private Enum_ControlType(double var3, double maxValue, double deltaValue, String uiText, String apiText) {
      this.maxValue = maxValue;
      this.deltaValue = deltaValue;
      this.uiText = uiText;
      this.apiText = apiText;
   }

   public final double getMinValue() {
      return this.minValue;
   }

   public final double getMaxValue() {
      return this.maxValue;
   }

   public final double getDeltaValue() {
      return this.deltaValue;
   }

   public final String getUiText() {
      return this.uiText;
   }

   public final String getApiText() {
      return this.apiText;
   }
}