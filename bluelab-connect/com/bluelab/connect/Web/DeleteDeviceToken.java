package bluelab.connect.Web;

import bluelab.connect.c.BluelabRemoteXbeeDevice;
import bluelab.connect.model.SettingFileModel;
import javax.ws.rs.core.MediaType;

public final class DeleteDeviceToken extends TokenHandler {
   public DeleteDeviceToken(long token, BluelabRemoteXbeeDevice dev, SettingFileModel settingFile) {
      super(String.format("entities/%d/devices/%d", token, dev.getId()), MediaType.TEXT_PLAIN_TYPE, MediaType.TEXT_PLAIN_TYPE, "DELETE", settingFile);
   }
}