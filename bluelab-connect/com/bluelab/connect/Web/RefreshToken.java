package bluelab.connect.Web;

import bluelab.connect.model.SettingFileModel;
import javax.ws.rs.core.MultivaluedMap;
import org.glassfish.jersey.client.oauth2.OAuth2Parameters.GrantType;

public final class RefreshToken extends TokenRequest {
   private String refreshToken;

   public RefreshToken(SettingFileModel settingFileModel) {
      super(GrantType.REFRESH_TOKEN, settingFileModel);
      this.refreshToken = settingFileModel.refreshToken;
   }

   protected final MultivaluedMap<String, String> GetRequestFields() {
      MultivaluedMap var1;
      (var1 = super.GetRequestFields()).add("refresh_token", this.refreshToken);
      return var1;
   }

   // $FF: synthetic method
   protected final Object getJson() {
      return this.GetRequestFields();
   }
}