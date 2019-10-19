package bluelab.connect.c;

import bluelab.connect.model.SensorFileModel;
import java.time.Instant;

public final class ControlTypeManager {
   private Enum_ControlType controlType;
   private long id;
   private double currentValue;
   private double minValue;
   private double maxValue;
   private Instant instant;
   private Enum_Range range;
   private boolean dueForUpdate;
   private Enum_AlarmState alarmState;
   private bluelab.connect.c.c.MinMaxValue minMaxValue;

   public ControlTypeManager(SensorFileModel var1) {
      this.id = var1.getId();
   }

   public ControlTypeManager(Enum_ControlType controlType) {
      this.controlType = controlType;
      this.minValue = controlType.getMinValue();
      this.maxValue = controlType.getMaxValue();
      this.id = 0L;
      this.currentValue = this.minValue;
      this.instant = Instant.now();
      this.range = Enum_Range.ERROR;
      this.dueForUpdate = false;
      this.alarmState = bluelab.connect.c.Enum_AlarmState.OFF;
      this.minMaxValue = new bluelab.connect.c.c.MinMaxValue();
      this.minMaxValue.minValue = this.minValue;
      this.minMaxValue.maxValue = this.maxValue;
   }

   public final Enum_ControlType getControlType() {
      return this.controlType;
   }

   public final String getControlTypeUiText() {
      return this.controlType.getUiText();
   }

   public final long getId() {
      return this.id;
   }

   public final void setId(long var1) {
      this.id = var1;
   }

   public final double getCurrentValue() {
      return this.currentValue;
   }

   public final void setCurrentValue(double var1) {
      this.currentValue = var1;
   }

   public final double getMinValue() {
      return this.minValue;
   }

   public final double getMaxValue() {
      return this.maxValue;
   }

   public final Instant getInstant() {
      return this.instant;
   }

   public final void setInstant(Instant var1) {
      this.instant = var1;
   }

   public final Enum_Range getRange() {
      return this.range;
   }

   public final void setRange(Enum_Range range) {
      this.range = range;
   }

   public final boolean getDueForUpdate() {
      return this.dueForUpdate;
   }

   public final void setDueForUpdate(boolean var1) {
      this.dueForUpdate = var1;
   }

   public final Enum_AlarmState getAlarmState() {
      return this.alarmState;
   }

   public final void setAlarmState(Enum_AlarmState var1) {
      this.alarmState = var1;
   }

   public final bluelab.connect.c.c.MinMaxValue getMinMaxValue() {
      return this.minMaxValue;
   }

   public final void setMinMaxValue(bluelab.connect.c.c.MinMaxValue var1) {
      this.minMaxValue = var1;
   }

   public final double getMinMaxValueMinValue() {
      return this.minMaxValue.minValue;
   }

   public final void setMinMaxValueMinValue(double var1) {
      this.minMaxValue.minValue = var1;
   }

   public final double getMinMaxValueMaxValue() {
      return this.minMaxValue.maxValue;
   }

   public final void setMinMaxValueMaxValue(double var1) {
      this.minMaxValue.maxValue = var1;
   }

   public final boolean isAtHighOrLow() {
      return this.alarmState == bluelab.connect.c.Enum_AlarmState.HIGH || this.alarmState == bluelab.connect.c.Enum_AlarmState.LOW;
   }

   public final boolean isOverOrUnderRange() {
      return this.range.equals(Enum_Range.UNDER_RANGE) || this.range.equals(Enum_Range.OVER_RANGE);
   }

   public final boolean isOn() {
      return this.alarmState != bluelab.connect.c.Enum_AlarmState.OFF;
   }

   public final boolean isIdNonZero() {
      return this.id != 0L;
   }
}