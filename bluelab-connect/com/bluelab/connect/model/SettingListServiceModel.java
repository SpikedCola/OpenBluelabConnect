package bluelab.connect.model;

import java.util.ArrayList;
import java.util.List;

public class SettingListServiceModel {
   public List<KeyValueModel> settings = new ArrayList();

   public SettingListServiceModel(SettingFileModel var1) {
      this.settings.add(new KeyValueModel("email_alerts", String.valueOf(var1.emailAlerts)));
   }
}