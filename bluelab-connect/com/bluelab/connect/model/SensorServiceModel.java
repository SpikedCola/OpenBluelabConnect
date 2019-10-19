package bluelab.connect.model;

import bluelab.connect.c.ControlTypeManager;
import bluelab.connect.c.Enum_ControlType;
import bluelab.connect.c.Enum_AlarmState;
import bluelab.connect.c.Enum_Direction;
import bluelab.connect.c.TypeController;
import bluelab.connect.c.Enum_Range;
import java.time.Instant;
import java.util.List;

public class SensorServiceModel {
   protected long id;
   protected String property;
   protected String status;
   protected Double current;
   protected String timestamp;
   protected Double alarmLow;
   protected Double alarmHigh;
   protected String alarmStatus;
   protected Double required;
   protected Double requiredLow;
   protected Double requiredHigh;
   protected String controlDirection;
   protected List<String> activeLockouts;

   public SensorServiceModel() {
      this.id = 0L;
      this.property = Enum_ControlType.TEMPERATURE.getApiText();
      this.status = Enum_Range.ERROR.getApiText();
      this.current = 0.0D;
      this.timestamp = Instant.now().toString();
      this.alarmLow = 0.0D;
      this.alarmHigh = 0.0D;
      this.alarmStatus = Enum_AlarmState.OFF.getApiText();
      this.required = 0.0D;
      this.requiredLow = 0.0D;
      this.requiredHigh = 0.0D;
      this.controlDirection = Enum_Direction.OFF.getApiText();
      this.activeLockouts = null;
   }

   public SensorServiceModel(ControlTypeManager var1, TypeController var2) {
      this.id = var1.getId();
      this.property = var1.getControlType().getApiText();
      this.status = var1.getRange().getApiText();
      this.current = var1.getCurrentValue();
      this.timestamp = var1.getInstant().toString();
      this.alarmLow = var1.getMinMaxValueMinValue();
      this.alarmHigh = var1.getMinMaxValueMaxValue();
      this.alarmStatus = var1.getAlarmState().getApiText();
      this.setControlParameters(var2);
   }

   protected void setControlParameters(TypeController var1) {
      if (var1 == null) {
         this.required = null;
         this.requiredLow = null;
         this.requiredHigh = null;
         this.controlDirection = null;
         this.activeLockouts = null;
      } else {
         this.required = var1.getIsTemperatureControl() ? null : var1.getDeltaValue2();
         this.requiredLow = var1.getIsTemperatureControl() ? Math.min(var1.getDeltaValue1(), var1.getDeltaValue2()) : null;
         this.requiredHigh = var1.getIsTemperatureControl() ? Math.max(var1.getDeltaValue1(), var1.getDeltaValue2()) : null;
         this.controlDirection = var1.getDirection().getApiText();
         this.activeLockouts = var1.getLockoutsByApiText();
      }
   }

   public long getId() {
      return this.id;
   }

   public String getProperty() {
      return this.property;
   }
}