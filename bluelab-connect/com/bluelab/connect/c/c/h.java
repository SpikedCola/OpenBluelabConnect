package bluelab.connect.c.c;

import bluelab.connect.c.BluelabConnectStick;
import bluelab.connect.c.BluelabRemoteXbeeDevice;
import bluelab.connect.model.PodChainFileModel;
import bluelab.connect.model.PodSettings;
import java.util.Iterator;
import java.util.Map.Entry;

public final class h extends DeviceRemoteDeviceProcessor {
   private PodChainFileModel c;

   public h(BluelabConnectStick var1, BluelabRemoteXbeeDevice var2, PodChainFileModel var3) {
      super(var1, var2, 500L);
      this.c = var3;
   }

   protected final void getDeviceMessages() {
      this.deviceMessageList.clear();
      Iterator var2 = this.c.getPodSettings().entrySet().iterator();

      while(var2.hasNext()) {
         Entry var1;
         byte var3 = ((Integer)(var1 = (Entry)var2.next()).getKey()).byteValue();
         this.deviceMessageList.addAll(this.remoteXbeeDevice.getBluelabRemoteDevice().getDeviceMessages((PodSettings)var1.getValue(), var3));
      }

   }
}