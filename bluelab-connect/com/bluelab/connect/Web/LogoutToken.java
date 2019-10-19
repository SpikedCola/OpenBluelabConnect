package bluelab.connect.Web;

import bluelab.connect.model.SettingFileModel;
import com.google.gson.Gson;
import java.util.LinkedHashMap;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public final class LogoutToken extends TokenHandler {
   public LogoutToken(SettingFileModel settingFileModel) {
      super("account/logout", MediaType.APPLICATION_JSON_TYPE, MediaType.APPLICATION_JSON_TYPE, "POST", settingFileModel);
   }

   protected final void onSuccess(Response response) {
      this.settingFileModel.clearUserData();
      this.settingFileModel.save();
      bluelab.connect.GoogleAnalytics.GA_tracker_Sender.GetInstance().setCustomerNumber(Long.toString(this.settingFileModel.customerNr));
      super.onSuccess(response);
   }

   // $FF: synthetic method
   protected final Object getJson() {
      LinkedHashMap var2 = new LinkedHashMap();
      var2.put("refreshToken", this.settingFileModel.refreshToken);
      return (new Gson()).toJson(var2);
   }
}