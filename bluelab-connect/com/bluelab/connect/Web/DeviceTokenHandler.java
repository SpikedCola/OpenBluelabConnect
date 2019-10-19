package bluelab.connect.Web;

import bluelab.connect.c.BluelabRemoteXbeeDevice;
import bluelab.connect.model.DeviceServiceModel;
import bluelab.connect.model.SettingFileModel;
import com.google.gson.Gson;
import java.util.LinkedHashMap;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public final class DeviceTokenHandler extends TokenHandler {
   private BluelabRemoteXbeeDevice remoteDev;

   public DeviceTokenHandler(long entityId, BluelabRemoteXbeeDevice remoteDev, SettingFileModel settingFileModel) {
      super(String.format("entities/%d/devices", entityId), MediaType.APPLICATION_JSON_TYPE, MediaType.APPLICATION_JSON_TYPE, "POST", settingFileModel);
      this.remoteDev = remoteDev;
   }

   protected final void onSuccess(Response response) {
      try {
         String responseString = (String)response.readEntity(String.class);
         DeviceServiceModel model = (DeviceServiceModel)(new Gson()).fromJson(responseString, DeviceServiceModel.class);
         this.remoteDev.setIdAndIdOnAllControlTypes(model);
         if (this.onSuccessCallback != null) {
            this.onSuccessCallback.onSuccess(response.getStatus(), responseString);
            return;
         }
      } catch (Throwable var4) {
         bluelab.connect.d.WeirdEncoder.ReportException(var4);
         if (this.onFailureCallback != null) {
            this.onFailureCallback.onFailure(response.getStatus(), "Unrecognized server response");
         }
      }

   }

   // $FF: synthetic method
   protected final Object getJson() {
      LinkedHashMap data;
      (data = new LinkedHashMap()).put("name", this.remoteDev.getName());
      data.put("uid", this.remoteDev.get64BitAddress());
      data.put("hardware", this.remoteDev.getDeviceType().getApiText());
      return (new Gson()).toJson(data);
   }
}