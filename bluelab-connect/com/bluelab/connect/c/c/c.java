package bluelab.connect.c.c;

import bluelab.connect.c.BluelabConnectStick;
import bluelab.connect.c.BluelabRemoteXbeeDevice;
import com.digi.xbee.api.RemoteXBeeDevice;
import com.digi.xbee.api.exceptions.XBeeException;
import com.digi.xbee.api.exceptions.TimeoutException;

public final class c extends DeviceRemoteDeviceProcessor {
   private MonitorController monitorController;

   public c(BluelabConnectStick dev, BluelabRemoteXbeeDevice remoteDev, MonitorController monitorController) {
      super(dev, remoteDev);
      this.monitorController = monitorController;
   }

   protected final void getDeviceMessages() throws BluelabException {
      this.deviceMessageList = this.remoteXbeeDevice.getBluelabRemoteDevice().getDeviceMessages(this.monitorController);
   }

   protected final void sendReceiveProcessMessages() throws InterruptedException, BluelabException, XBeeException, TimeoutException {
      String name = this.monitorController.name;
      BluelabRemoteXbeeDevice blRemoteDevice = this.remoteXbeeDevice;
      if ((name = name.trim()).length() == 0) {
         name = " ";
      } else if (name.length() > 20) {
         name = name.substring(0, 19);
      }

      RemoteXBeeDevice remoteDev = blRemoteDevice.getRemoteXbeeDevice();
      if (remoteDev == null) {
         throw new BluelabException("Cannot write device name, since XBee module is not ready.");
      } else {
         remoteDev.setNodeID(name);
         remoteDev.writeChanges();
         blRemoteDevice.setName(name);
         super.sendReceiveProcessMessages();
      }
   }
}