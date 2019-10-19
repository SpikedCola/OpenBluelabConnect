package bluelab.connect.c;

import java.util.EnumSet;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class TypeController {
   private Enum_ControlType controlType;
   private EnumSet<Enum_Lockout> lockoutList;
   private boolean isTemperatureControl;
   private bluelab.connect.c.a.MonthDate monthDate;
   private bluelab.connect.c.c.ControlTypeInstance controlTypeInstance;

   public TypeController(Enum_ControlType controlType) {
      this.controlType = controlType;
      this.lockoutList = EnumSet.noneOf(Enum_Lockout.class);
      this.isTemperatureControl = controlType.equals(Enum_ControlType.TEMPERATURE);
      this.monthDate = null;
      this.controlTypeInstance = new bluelab.connect.c.c.ControlTypeInstance(controlType);
   }

   public final String getControlTypeUiText() {
      return this.controlType.getUiText();
   }

   public final boolean getIsTemperatureControl() {
      return this.isTemperatureControl;
   }

   public final void setIsTemperatureControl(boolean var1) {
      this.isTemperatureControl = var1;
   }

   public final bluelab.connect.c.a.MonthDate getMonthDate() {
      return this.monthDate;
   }

   public final void setMonthDate(bluelab.connect.c.a.MonthDate var1) {
      this.monthDate = var1;
   }

   public final void initMonthDate() {
      if (this.monthDate != null) {
         this.monthDate.init();
      }

   }

   public final bluelab.connect.c.c.ControlTypeInstance getControlTypeInstance() {
      return this.controlTypeInstance;
   }

   public final void setControlTypeInstance(bluelab.connect.c.c.ControlTypeInstance var1) {
      this.controlTypeInstance = var1;
   }

   public final double getDeltaValue1() {
      return this.controlTypeInstance.deltaValue1;
   }

   public final void setDeltaValue1(double var1) {
      this.controlTypeInstance.deltaValue1 = var1;
   }

/* dupe of line 74?
   public final double g() {
      return this.controlTypeInstance.deltaValue2;
   }
*/
   public final void setDeltaValue2(double var1) {
      this.controlTypeInstance.deltaValue2 = var1;
   }

   public final double getDeltaValue2() {
      return this.controlTypeInstance.deltaValue2;
   }

   public final void SetDeltaValue1And2(double var1) {
      this.controlTypeInstance.deltaValue1 = var1;
      this.controlTypeInstance.deltaValue2 = var1;
   }

   public final Enum_Direction getDirection() {
      return this.controlTypeInstance.direction;
   }

   public final void setDirection(Enum_Direction direction) {
      this.controlTypeInstance.direction = direction;
   }

   public final int getOnTime() {
      return this.controlTypeInstance.onTime;
   }

   public final void setOnTime(int var1) {
      this.controlTypeInstance.onTime = var1;
   }

   public final void setOffTime(int var1) {
      this.controlTypeInstance.offTime = var1;
   }

   public final boolean lockoutListNotEmpty() {
      return !this.lockoutList.isEmpty();
   }

   public final boolean lockoutListContains(Enum_Lockout var1) {
      return this.lockoutList.contains(var1);
   }

   public final void addRemoveLockout(Enum_Lockout var1, boolean add) {
      if (add) {
         this.lockoutList.add(var1);
      } else {
         this.lockoutList.remove(var1);
      }
   }

   public final List<String> getLockoutsByApiText() {
      return (List)this.lockoutList.stream().map(Enum_Lockout::getApiText).collect(Collectors.toList());
   }
}