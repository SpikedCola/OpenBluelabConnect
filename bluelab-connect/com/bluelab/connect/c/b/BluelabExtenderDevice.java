package bluelab.connect.c.b;

import bluelab.connect.c.BluelabRemoteXbeeDevice;
import bluelab.connect.model.PodSettings;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.LoggerFactory;

public final class BluelabExtenderDevice extends BluelabRemoteDevice {
   static {
      LoggerFactory.getLogger(BluelabGuardianRemoteDevice.class);
   }

   public BluelabExtenderDevice(BluelabRemoteXbeeDevice dev) {
      super(dev);
      dev.addRemoveChangeType(bluelab.connect.c.Enum_ChangeType.DEVICE_SETTING, false);
   }

   protected final boolean isValid2(ByteBuffer var1) {
      return false;
   }

   protected final bluelab.connect.g.ByteArray createDeviceMessage(ByteBuffer var1) {
      return null;
   }

   protected final void processMessage(bluelab.connect.g.ByteArray var1) {
   }

   public final List<bluelab.connect.g.DeviceMessage> getDeviceMessages(bluelab.connect.c.c.MonitorController var1) {
      return new ArrayList(0);
   }

   public final List<bluelab.connect.g.DeviceMessage> getDeviceMessages(PodSettings var1, byte var2) {
      return new ArrayList(0);
   }
}