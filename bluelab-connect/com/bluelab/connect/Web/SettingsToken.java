package bluelab.connect.Web;

import bluelab.connect.model.SettingFileModel;
import bluelab.connect.model.SettingListServiceModel;
import com.google.gson.Gson;
import javax.ws.rs.core.MediaType;

public final class SettingsToken extends TokenHandler {
   private SettingListServiceModel settingListServiceModel;

   public SettingsToken(SettingFileModel settingFileModel) {
      super("settings", MediaType.APPLICATION_JSON_TYPE, MediaType.APPLICATION_JSON_TYPE, "PUT", settingFileModel);
      this.settingListServiceModel = new SettingListServiceModel(settingFileModel);
   }

   // $FF: synthetic method
   protected final Object getJson() {
      return (new Gson()).toJson(this.settingListServiceModel);
   }
}