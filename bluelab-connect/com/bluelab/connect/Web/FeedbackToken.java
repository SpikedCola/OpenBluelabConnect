package bluelab.connect.Web;

import bluelab.connect.c.BluelabRemoteXbeeDevice;
import bluelab.connect.model.FeedbackServiceModel;
import bluelab.connect.model.SettingFileModel;
import com.google.gson.Gson;
import java.util.List;
import javax.ws.rs.core.MediaType;

public final class FeedbackToken extends TokenHandler {
   private FeedbackServiceModel feedbackServiceModel;

   public FeedbackToken(String message, String software, SettingFileModel settingFileModel, List<BluelabRemoteXbeeDevice> list) {
      super("feedback", MediaType.APPLICATION_JSON_TYPE, MediaType.APPLICATION_JSON_TYPE, "POST", settingFileModel);
      this.feedbackServiceModel = new FeedbackServiceModel(message, software, bluelab.connect.i.SettingsAndSystemProperties.GetUserInfo(settingFileModel, list));
   }

   // $FF: synthetic method
   protected final Object getJson() {
      return (new Gson()).toJson(this.feedbackServiceModel);
   }
}