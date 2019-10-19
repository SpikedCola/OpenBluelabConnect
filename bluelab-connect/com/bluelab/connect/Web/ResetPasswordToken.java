package bluelab.connect.Web;

import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.core.MediaType;

public final class ResetPasswordToken extends BluelabWebRequestResponse {
   private String email;

   public ResetPasswordToken(String email) {
      super("account/resetpassword", MediaType.TEXT_PLAIN_TYPE, MediaType.TEXT_PLAIN_TYPE, "GET");
      this.email = email;
   }

   protected final Map<String, String> getFields() {
      HashMap map = new HashMap();
      map.put("email", this.email);
      return map;
   }
}