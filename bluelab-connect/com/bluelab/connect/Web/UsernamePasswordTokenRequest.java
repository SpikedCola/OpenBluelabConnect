package bluelab.connect.Web;

import bluelab.connect.model.SettingFileModel;
import javax.ws.rs.core.MultivaluedMap;
import org.glassfish.jersey.client.oauth2.OAuth2Parameters.GrantType;

public final class UsernamePasswordTokenRequest extends TokenRequest {
   private String username;
   private String password;

   public UsernamePasswordTokenRequest(String var1, String var2, SettingFileModel var3) {
      super(GrantType.PASSWORD, var3);
      this.username = var1;
      this.password = var2;
   }

   protected final MultivaluedMap<String, String> GetRequestFields() {
      MultivaluedMap map = super.GetRequestFields();
      map.add("username", this.username);
      map.add("password", this.password);
      return map;
   }

   protected final void loadJSON(String json) {
      this.settingFileModel.email = this.username;
      super.loadJSON(json);
   }

   // $FF: synthetic method
   protected final Object getJson() {
      return this.GetRequestFields();
   }
}