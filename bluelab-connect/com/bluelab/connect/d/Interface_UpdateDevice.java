package bluelab.connect.d;

import bluelab.connect.c.BluelabRemoteXbeeDevice;
import java.util.List;

public interface Interface_UpdateDevice {
   void updateDevices(List<BluelabRemoteXbeeDevice> var1);

   void updateDevice(BluelabRemoteXbeeDevice var1);

   void update();

   void shutdown();

   void foundConnectStick();

   void d();

   void errorList(boolean isErrorCode, String message, List<String> errorList);
}