package bluelab.connect.c;

public final class BluelabGuardianRemoteXbeeDevice extends BluelabRemoteXbeeDevice {
   public BluelabGuardianRemoteXbeeDevice(BluelabRemoteXbeeDevice dev) {
      super(dev.getKeyCode(), bluelab.connect.c.Enum_DeviceType.GUARDIAN, dev.getDeviceVersion(), bluelab.connect.c.Enum_DevicePresence.ONLINE, dev.getRemoteXbeeDevice());
      this.mode = bluelab.connect.c.Enum_Mode.MONITOR;
      this.deviceTypeKnown = true;
      this.controlTypeControlTypeManagerMap.put(Enum_ControlType.CONDUCTIVITY, new ControlTypeManager(Enum_ControlType.CONDUCTIVITY));
      this.controlTypeControlTypeManagerMap.put(Enum_ControlType.PH, new ControlTypeManager(Enum_ControlType.PH));
      this.controlTypeControlTypeManagerMap.put(Enum_ControlType.TEMPERATURE, new ControlTypeManager(Enum_ControlType.TEMPERATURE));
      this.setIdAndIdOnAllControlTypes(dev);
   }
}