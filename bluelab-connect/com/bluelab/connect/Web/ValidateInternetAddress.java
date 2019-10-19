package bluelab.connect.Web;

import javax.mail.internet.InternetAddress;

public final class ValidateInternetAddress {
   public static boolean IsValid(String url) {
      boolean valid = false;

      try {
         (new InternetAddress(url)).validate();
         valid = true;
      } catch (Throwable var2) {
         ;
      }

      return valid;
   }
}