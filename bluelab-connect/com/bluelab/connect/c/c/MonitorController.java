package bluelab.connect.c.c;

import bluelab.connect.c.Enum_ControlType;
import bluelab.connect.c.Enum_Lockout;
import bluelab.connect.c.BluelabRemoteXbeeDevice;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public final class MonitorController {
   public String name;
   public bluelab.connect.c.Enum_Mode mode;
   public boolean turnAlarmsOnOff;
   public boolean lowConductivityLockout;
   public boolean ineffectiveControlLockout;
   public boolean f;
   private Map<Enum_ControlType, MinMaxValue> controlTypeMinMaxValueMap;
   private Map<Enum_ControlType, ControlTypeInstance> controlTypeInstanceMap;

   public MonitorController() {
      this.name = "";
      this.mode = bluelab.connect.c.Enum_Mode.MONITOR;
      this.turnAlarmsOnOff = false;
      this.lowConductivityLockout = false;
      this.ineffectiveControlLockout = false;
      this.f = false;
      this.controlTypeMinMaxValueMap = new LinkedHashMap();
      this.controlTypeInstanceMap = new LinkedHashMap();
   }

   public MonitorController(BluelabRemoteXbeeDevice dev) {
      this.name = dev.getName();
      this.mode = dev.getMode();
      this.turnAlarmsOnOff = dev.anyOn();
      this.lowConductivityLockout = dev.lockoutListContains(Enum_Lockout.LOW_CONDUCTIVITY);
      this.ineffectiveControlLockout = dev.lockoutListContains(Enum_Lockout.INEFFECTIVE_CONTROL);
      this.f = dev.M();
      this.controlTypeMinMaxValueMap = new LinkedHashMap();
      dev.getControlTypeControlTypeManagerMap().forEach((controlType, controlTypeManager) -> {
         this.controlTypeMinMaxValueMap.put(controlType, new MinMaxValue(controlTypeManager.getMinMaxValue()));
      });
      this.controlTypeInstanceMap = new LinkedHashMap();
      dev.getControlTypeControllerMap().forEach((controlType, controller) -> {
         this.controlTypeInstanceMap.put(controlType, new ControlTypeInstance(controller.getControlTypeInstance()));
      });
   }

   public MonitorController(BluelabRemoteXbeeDevice remoteXbeeDev, bluelab.connect.c.Enum_Mode mode, boolean var3) {
      this(remoteXbeeDev);
      this.mode = mode;
      this.f = var3;
      this.controlTypeMinMaxValueMap.clear();
      this.controlTypeInstanceMap.clear();
   }

   public final Map<Enum_ControlType, MinMaxValue> getControlTypeMinMaxValueMap() {
      return this.controlTypeMinMaxValueMap;
   }

   public final Map<Enum_ControlType, ControlTypeInstance> getControlTypeInstanceMap() {
      return this.controlTypeInstanceMap;
   }

   public final MinMaxValue getMinMaxValue(Enum_ControlType controlType) {
      return (MinMaxValue)this.controlTypeMinMaxValueMap.get(controlType);
   }

   public final ControlTypeInstance getControlTypeInstance(Enum_ControlType controlType) {
      return (ControlTypeInstance)this.controlTypeInstanceMap.get(controlType);
   }
}