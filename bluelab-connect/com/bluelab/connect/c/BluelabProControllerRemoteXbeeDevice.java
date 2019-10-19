package bluelab.connect.c;

public final class BluelabProControllerRemoteXbeeDevice extends BluelabRemoteXbeeDevice {
   public BluelabProControllerRemoteXbeeDevice(BluelabRemoteXbeeDevice dev) {
      super(dev.getKeyCode(), bluelab.connect.c.Enum_DeviceType.PRO_CONTROLLER, dev.getDeviceVersion(), bluelab.connect.c.Enum_DevicePresence.ONLINE, dev.getRemoteXbeeDevice());
      this.deviceTypeKnown = true;
      this.controlTypeControlTypeManagerMap.put(Enum_ControlType.CONDUCTIVITY, new ControlTypeManager(Enum_ControlType.CONDUCTIVITY));
      this.controlTypeControlTypeManagerMap.put(Enum_ControlType.PH, new ControlTypeManager(Enum_ControlType.PH));
      this.controlTypeControlTypeManagerMap.put(Enum_ControlType.TEMPERATURE, new ControlTypeManager(Enum_ControlType.TEMPERATURE));
      this.controlTypeControllerMap.put(Enum_ControlType.CONDUCTIVITY, new TypeController(Enum_ControlType.CONDUCTIVITY));
      this.controlTypeControllerMap.put(Enum_ControlType.PH, new TypeController(Enum_ControlType.PH));
      this.controlTypeControllerMap.put(Enum_ControlType.TEMPERATURE, new TypeController(Enum_ControlType.TEMPERATURE));
      this.lockoutListAddRemove(bluelab.connect.c.Enum_Lockout.NORMALLY_OPEN, true);
      this.lockoutListAddRemove(bluelab.connect.c.Enum_Lockout.NORMALLY_CLOSED, true);
      this.lockoutListAddRemove(bluelab.connect.c.Enum_Lockout.LOW_CONDUCTIVITY, true);
      this.lockoutListAddRemove(bluelab.connect.c.Enum_Lockout.INEFFECTIVE_CONTROL, true);
      this.setIdAndIdOnAllControlTypes(dev);
   }
}