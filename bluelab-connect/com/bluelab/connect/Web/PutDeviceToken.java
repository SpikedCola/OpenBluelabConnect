package bluelab.connect.Web;

import bluelab.connect.c.BluelabRemoteXbeeDevice;
import bluelab.connect.model.DeviceListServiceModel;
import bluelab.connect.model.SettingFileModel;
import com.google.gson.Gson;
import java.util.List;
import javax.ws.rs.core.MediaType;

public final class PutDeviceToken extends TokenHandler {
   private DeviceListServiceModel deviceListServiceModel;

   public PutDeviceToken(long var1, List<BluelabRemoteXbeeDevice> dev, SettingFileModel settingFileModel) {
      super(String.format("entities/%d/devices", var1), MediaType.APPLICATION_JSON_TYPE, MediaType.APPLICATION_JSON_TYPE, "PUT", settingFileModel);
      this.useGzip = true;
      this.deviceListServiceModel = new DeviceListServiceModel(dev);
   }

   // $FF: synthetic method
   protected final Object getJson() {
      return (new Gson()).toJson(this.deviceListServiceModel);
   }
}