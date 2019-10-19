package bluelab.connect.Web;

import bluelab.connect.model.SettingFileModel;
import com.google.gson.Gson;
import java.lang.reflect.Type;
import java.util.Map;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.client.oauth2.OAuth2Parameters.GrantType;

public class TokenRequest extends BluelabWebRequestResponse {
   private GrantType grantType;
   protected SettingFileModel settingFileModel;
   private String clientId;

   public TokenRequest(GrantType grantType, SettingFileModel settingFileModel) {
      super("token", MediaType.APPLICATION_FORM_URLENCODED_TYPE, MediaType.APPLICATION_JSON_TYPE, "POST");
      this.grantType = grantType;
      this.settingFileModel = settingFileModel;
      this.clientId = "ConnectDesktop";
   }

   protected MultivaluedMap<String, String> GetRequestFields() {
      MultivaluedHashMap map = new MultivaluedHashMap();
      map.add("grant_type", this.grantType.toString().toLowerCase());
      map.add("client_id", this.clientId);
      return map;
   }

   protected final void onSuccess(Response response) {
      try {
         String json = (String)response.readEntity(String.class);
         this.loadJSON(json);
         if (this.onSuccessCallback != null) {
            this.onSuccessCallback.onSuccess(response.getStatus(), json);
            return;
         }
      } catch (Throwable ex) {
         bluelab.connect.d.WeirdEncoder.ReportException(ex);
         if (this.onFailureCallback != null) {
            this.onFailureCallback.onFailure(response.getStatus(), "Unrecognized server response");
         }
      }

   }

   protected void loadJSON(String json) {
      Type jsonRequestTypeToken = (new JsonRequestTypeToken(this)).getType();
      Map map = (Map)(new Gson()).fromJson(json, jsonRequestTypeToken);
      String refreshToken = (String)map.get("refresh_token");
      if (refreshToken != null) {
         this.settingFileModel.refreshToken = refreshToken;
      }

      this.settingFileModel.accessToken = (String)map.get("access_token");
      this.settingFileModel.customerNr = Long.valueOf((String)map.get("customerNumber"));
      this.settingFileModel.entityId = Long.valueOf((String)map.get("entities"));
      this.settingFileModel.save();
   }

   // $FF: synthetic method
   protected Object getJson() {
      return this.GetRequestFields();
   }
}