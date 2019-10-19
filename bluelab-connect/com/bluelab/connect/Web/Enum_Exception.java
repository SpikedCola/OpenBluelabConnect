package bluelab.connect.Web;

import javax.ws.rs.core.Response.StatusType;
import javax.ws.rs.core.Response.Status.Family;

public enum Enum_Exception implements StatusType {
   PROCESSING_EXCEPTION(498, "Processing Exception"),
   GENERAL_EXCEPTION(499, "General Exception");

   private final int code;
   private final String reason;
   private final Family family;

   private Enum_Exception(int code, String reason) {
      this.code = code;
      this.reason = reason;
      this.family = Family.CLIENT_ERROR;
   }

   public final int getStatusCode() {
      return this.code;
   }

   public final Family getFamily() {
      return this.family;
   }

   public final String getReasonPhrase() {
      return this.reason;
   }

   public final String toString() {
      return this.getReasonPhrase();
   }
}