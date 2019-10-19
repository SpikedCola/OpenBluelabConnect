package bluelab.connect.c.c;

import bluelab.connect.c.Enum_ControlType;

public final class ControlTypeInstance {
   public double deltaValue1;
   public double deltaValue2;
   public bluelab.connect.c.Enum_Direction direction;
   public int onTime;
   public int offTime;

   public ControlTypeInstance(Enum_ControlType controlType) {
      this.deltaValue1 = controlType.getDeltaValue();
      this.deltaValue2 = controlType.getDeltaValue();
      this.direction = bluelab.connect.c.Enum_Direction.OFF;
      this.onTime = 0;
      this.offTime = 0;
   }

   public ControlTypeInstance(ControlTypeInstance controlType) {
      this.deltaValue1 = controlType.deltaValue1;
      this.deltaValue2 = controlType.deltaValue2;
      this.direction = controlType.direction;
      this.onTime = controlType.onTime;
      this.offTime = controlType.offTime;
   }
}