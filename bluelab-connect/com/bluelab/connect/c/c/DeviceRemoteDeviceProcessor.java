package bluelab.connect.c.c;

import bluelab.connect.c.BluelabConnectStick;
import bluelab.connect.c.BluelabRemoteXbeeDevice;
import com.digi.xbee.api.exceptions.XBeeException;
import com.digi.xbee.api.exceptions.TimeoutException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class DeviceRemoteDeviceProcessor implements Runnable {
   private static Logger logger = LoggerFactory.getLogger(DeviceRemoteDeviceProcessor.class);
   private BluelabConnectStick connectStick;
   protected BluelabRemoteXbeeDevice remoteXbeeDevice;
   private long threadSleepTime;
   private int attempts; 
   private Interface_OnSuccess onSuccess;
   private Interface_OnFailure onFailure;
   protected List<bluelab.connect.g.DeviceMessage> deviceMessageList;

   protected DeviceRemoteDeviceProcessor(BluelabConnectStick stick, BluelabRemoteXbeeDevice remoteDev) {
      this(stick, remoteDev, 0L, 3);
   }

   protected DeviceRemoteDeviceProcessor(BluelabConnectStick stick, BluelabRemoteXbeeDevice remoteDev, long threadSleepTime) {
      this(stick, remoteDev, 500L, 3);
   }

   private DeviceRemoteDeviceProcessor(BluelabConnectStick stick, BluelabRemoteXbeeDevice remoteDev, long threadSleepTime, int var5) {
      this.connectStick = stick;
      this.remoteXbeeDevice = remoteDev;
      this.threadSleepTime = threadSleepTime;
      this.attempts = 3; // @bug was this hardcoded to 1 further down?
      this.deviceMessageList = new ArrayList();
   }

   public final void setSuccessFailCallbacks(Interface_OnSuccess successCB, Interface_OnFailure failCB) {
      this.onSuccess = successCB;
      this.onFailure = failCB;
   }

   public void run() {
      DeviceRemoteDeviceProcessor processor = this;

      try {
         if (!processor.remoteXbeeDevice.isOnline()) {
            throw new BluelabException("Cannot write messages, since device is not online.");
         }

         processor.getDeviceMessages();
         processor.sendReceiveProcessMessages();
         if (processor.onSuccess != null) {
            processor.onSuccess.onSuccess();
         }
      } catch (Throwable ex) {
         bluelab.connect.d.WeirdEncoder.ReportException(ex);
         logger.error("Error: {}", ex.toString());
         if (this.onFailure != null) {
            this.onFailure.onFailure(ex.toString());
         }
      }

   }

   protected abstract void getDeviceMessages() throws BluelabException;

   protected void sendReceiveProcessMessages() throws BluelabException, InterruptedException, TimeoutException, XBeeException {
      Iterator deviceMessageIterator = this.deviceMessageList.iterator();

      while(deviceMessageIterator.hasNext()) {
         bluelab.connect.g.DeviceMessage nextMessage = (bluelab.connect.g.DeviceMessage)deviceMessageIterator.next();
         //Object var4 = BluelabConnectStick.readMutex;
         bluelab.connect.g.DeviceMessage responseMessage;
         synchronized(BluelabConnectStick.readMutex) {
            responseMessage = this.connectStick.readMessageFromDevice(this.remoteXbeeDevice, nextMessage, this.attempts);
         }

         if (responseMessage == null) {
            throw new BluelabException(String.format("No command message response (0x%02X)", nextMessage.getCommand()));
         }

         this.remoteXbeeDevice.getBluelabRemoteDevice().processBytes(responseMessage.ConstructPacket());
         Thread.sleep(this.threadSleepTime);
      }

   }
}