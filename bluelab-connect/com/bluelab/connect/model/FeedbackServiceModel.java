package bluelab.connect.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class FeedbackServiceModel {
   protected String message;
   protected String software;
   protected Map<String, String> userInfo;

   public FeedbackServiceModel() {
      this.message = "";
      this.software = "";
      this.userInfo = new LinkedHashMap();
   }

   public FeedbackServiceModel(String message, String software, Map<String, String> userInfo) {
      this.message = message;
      this.software = software;
      this.userInfo = userInfo;
   }
}