package bluelab.connect.model;

import bluelab.connect.c.Enum_PumpType;
import java.util.Objects;

public class PumpSettings {
   public String name;
   public Enum_PumpType pumpType;
   public int ratio;
   public double value;
   public String unit;

   public PumpSettings() {
      this.name = "";
      this.pumpType = Enum_PumpType.GetOff();
      this.ratio = 0;
      this.value = 0.0D;
      this.unit = "";
   }

   public boolean equals(Object var1) {
      if (var1 == null) {
         return false;
      } else if (!PumpSettings.class.isAssignableFrom(var1.getClass())) {
         return false;
      } else {
         PumpSettings var2 = (PumpSettings)var1;
         return Objects.equals(this.pumpType, var2.pumpType) && this.ratio == var2.ratio;
      }
   }

   public PumpSettings(PumpSettings var1) {
      this.copy(var1, true);
   }

   public void copy(PumpSettings pumpSettings, boolean copyUnits) {
      this.name = pumpSettings.name;
      this.pumpType = pumpSettings.pumpType;
      this.ratio = pumpSettings.ratio;
      this.value = pumpSettings.value;
      if (copyUnits) {
         this.unit = pumpSettings.unit;
      }

   }

   public void disable() {
      this.pumpType = Enum_PumpType.OFF;
      this.ratio = 0;
      this.value = 0.0D;
   }
}