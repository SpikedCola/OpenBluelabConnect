package bluelab.connect.Web;

import com.google.gson.Gson;
import java.time.ZoneId;
import java.util.LinkedHashMap;
import javax.ws.rs.core.MediaType;

public final class RegisterToken extends BluelabWebRequestResponse {
   private String email;
   private String password;
   private String timezone;

   public RegisterToken(String email, String password) {
      super("account/register", MediaType.APPLICATION_JSON_TYPE, MediaType.APPLICATION_JSON_TYPE, "POST");
      this.email = email;
      this.password = password;
      this.timezone = ZoneId.systemDefault().toString();
   }

   // $FF: synthetic method
   protected final Object getJson() {
      LinkedHashMap var2 = new LinkedHashMap();
      var2.put("email", this.email);
      var2.put("password", this.password);
      var2.put("confirmPassword", this.password);
      var2.put("timezone", this.timezone);
      return (new Gson()).toJson(var2);
   }
}