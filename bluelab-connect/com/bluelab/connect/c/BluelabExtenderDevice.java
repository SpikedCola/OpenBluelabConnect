package bluelab.connect.c;

public final class BluelabExtenderDevice extends BluelabRemoteXbeeDevice {
   public BluelabExtenderDevice(BluelabRemoteXbeeDevice dev) {
      super(dev.getName(), bluelab.connect.c.Enum_DeviceType.EXTENDER, dev.getDeviceVersion(), bluelab.connect.c.Enum_DevicePresence.ONLINE, dev.getRemoteXbeeDevice());
      this.deviceTypeKnown = true;
      this.setIdAndIdOnAllControlTypes(dev);
   }
}