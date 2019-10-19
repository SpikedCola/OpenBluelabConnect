package bluelab.connect.c;

public final class BluelabPHControllerRemoteXbeeDevice extends BluelabRemoteXbeeDevice {
   public BluelabPHControllerRemoteXbeeDevice(BluelabRemoteXbeeDevice dev) {
      super(dev.getKeyCode(), bluelab.connect.c.Enum_DeviceType.PH_CONTROLLER, dev.getDeviceVersion(), bluelab.connect.c.Enum_DevicePresence.ONLINE, dev.getRemoteXbeeDevice());
      this.deviceTypeKnown = true;
      this.controlTypeControlTypeManagerMap.put(Enum_ControlType.PH, new ControlTypeManager(Enum_ControlType.PH));
      this.controlTypeControlTypeManagerMap.put(Enum_ControlType.TEMPERATURE, new ControlTypeManager(Enum_ControlType.TEMPERATURE));
      this.controlTypeControllerMap.put(Enum_ControlType.PH, new TypeController(Enum_ControlType.PH));
      this.lockoutListAddRemove(bluelab.connect.c.Enum_Lockout.INEFFECTIVE_CONTROL, true);
      this.setIdAndIdOnAllControlTypes(dev);
   }
}