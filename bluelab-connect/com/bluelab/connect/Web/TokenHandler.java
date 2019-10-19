package bluelab.connect.Web;

import bluelab.connect.model.SettingFileModel;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.StatusType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TokenHandler extends BluelabWebRequestResponse {
   private static Logger logger = LoggerFactory.getLogger(TokenHandler.class);
   protected SettingFileModel settingFileModel;
   private boolean validToken;

   public TokenHandler(String path, MediaType var2, MediaType var3, String requestMethod, SettingFileModel settingFileModel) {
      super(path, var2, var3, requestMethod);
      this.settingFileModel = settingFileModel;
      this.validToken = false;
   }

   protected final MultivaluedMap<String, Object> a() {
      MultivaluedHashMap map = new MultivaluedHashMap();
      map.add("Authorization", "Bearer " + this.settingFileModel.accessToken);
      return map;
   }

   protected final void onError(Response response) {
      boolean success = false;
      StatusType status = response.getStatusInfo();
      int statusCode= status.getStatusCode();
      if (!this.validToken && statusCode >= 400 && statusCode <= 403) {
         logger.info("Attempting to refresh tokens...");
         success = false;
         if (this.settingFileModel.refreshToken != null) {
            RefreshToken token  = new RefreshToken(this.settingFileModel);
            token.setCallbacks(this::refreshSuccess, this::refreshError);
            token.sendReceiveRequest();
            success = true;
         }

      }

      if (!success) {
         super.onError(response);
      }

   }

   public void refreshSuccess(int statusCode, Object response) {
      logger.info("Token refresh succeeded");
      this.validToken = true;
      this.sendReceiveRequest();
   }

   public void refreshError(int statusCode, String response) {
      logger.error("Token refresh error ({}: {})", statusCode, response);
      if (bluelab.connect.Web.BluelabWebRequestResponse.isBadRequest(statusCode)) {
         this.settingFileModel.clearUserData();
         this.settingFileModel.save();
         logger.info("User automatically logged out, since token was deemed invalid/expired.");
      }

      if (this.onFailureCallback != null) {
         this.onFailureCallback.onFailure(statusCode, String.format("Token refresh error (%s)", response));
      }

   }
}