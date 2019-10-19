package bluelab.connect.c;

import bluelab.connect.model.PumpSettings;
import java.util.Locale;

public final class Pump {
   private double currentValue = 0.0D;
   private PumpSettings pumpSettings = new PumpSettings();

   public Pump(double unused) {
   }

   public final void setCurrentValue(double value) {
      this.currentValue = value;
   }

   public final PumpSettings getPumpSettings() {
      return this.pumpSettings;
   }

   public final void setPumpSettings(PumpSettings settings) {
      this.pumpSettings = settings;
   }

   public final String getName() {
      return this.pumpSettings.name;
   }

   public final void setName(String name) {
      this.pumpSettings.name = name;
   }

   public final void setFunction(Enum_PumpType function) {
      this.pumpSettings.pumpType = function;
   }

   public final void SetRatio(int ratio) {
      this.pumpSettings.ratio = ratio;
   }

   public final void setUnit(String unit) {
      this.pumpSettings.unit = unit;
   }

   public final String getCurrentValue() {
      return String.format(Locale.ROOT, "%.1f", this.currentValue);
   }
}