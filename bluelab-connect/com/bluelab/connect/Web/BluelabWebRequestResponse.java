package bluelab.connect.Web;

import bluelab.connect.Connect;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.StatusType;
import javax.ws.rs.core.Response.Status.Family;
import org.glassfish.jersey.client.filter.EncodingFilter;
import org.glassfish.jersey.message.GZipEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BluelabWebRequestResponse implements Runnable {
   private static Logger logger = LoggerFactory.getLogger(BluelabWebRequestResponse.class);
   private static final String serviceUrl = Connect.GetServiceUrl();
   private String path;
   private String requestMethod;
   private MediaType h;
   private MediaType i;
   protected boolean useGzip;
   private static Client client = (Client)((Client)ClientBuilder.newClient().property("jersey.config.client.connectTimeout", 20000)).property("jersey.config.client.readTimeout", 20000);
   private static Client gzipClient = (Client)((Client)((Client)((Client)((Client)ClientBuilder.newClient().register(EncodingFilter.class)).register(GZipEncoder.class)).property("jersey.config.client.useEncoding", "gzip")).property("jersey.config.client.connectTimeout", 20000)).property("jersey.config.client.readTimeout", 20000);
   protected Interface_OnSuccess onSuccessCallback;
   protected Interface_OnFailure onFailureCallback;

   public BluelabWebRequestResponse(String path, MediaType mediaType, MediaType mediaType2, String requestMethod) {
      this.path = path;
      this.h = mediaType;
      this.i = mediaType2;
      this.requestMethod = requestMethod;
      this.useGzip = false;
   }

   public final void setCallbacks(Interface_OnSuccess onSuccess, Interface_OnFailure onFailure) {
      this.onSuccessCallback = onSuccess;
      this.onFailureCallback = onFailure;
   }

   public final void sendReceiveRequest() {
      Response response = null;

      try {
         WebTarget baseTarget;
         if (this.useGzip) {
            baseTarget = gzipClient.target(serviceUrl);
         } else {
            baseTarget = client.target(serviceUrl);
         }

         WebTarget target = baseTarget.path(this.path);

         // add query params
         Entry var11;
         for(Iterator var4 = this.getFields().entrySet().iterator(); var4.hasNext(); target = target.queryParam((String)var11.getKey(), new Object[]{var11.getValue()})) {
            var11 = (Entry)var4.next();
         }

         response = target.request(new MediaType[]{this.i}).headers(this.a()).build(this.requestMethod, Entity.entity(this.getJson(), this.h)).invoke();
         if (response.getStatusInfo().getFamily().equals(Family.SUCCESSFUL)) {
            this.onSuccess(response);
         } else {
            this.onError(response);
         }

         return;
      } catch (ProcessingException ex) {
         logger.error("Request processing error: {}", ex.toString());
         if (this.onFailureCallback != null) {
            this.onFailureCallback.onFailure(bluelab.connect.Web.Enum_Exception.PROCESSING_EXCEPTION.getStatusCode(), "Request processing error");
         }

         return;
      } catch (Throwable ex) {
         bluelab.connect.d.WeirdEncoder.ReportException(ex);
         logger.error("Error: {}", ex.toString());
         if (this.onFailureCallback != null) {
            this.onFailureCallback.onFailure(bluelab.connect.Web.Enum_Exception.GENERAL_EXCEPTION.getStatusCode(), ex.toString());
         }
      } finally {
         if (response != null) {
            response.close();
         }

      }

   }

   public void run() {
      this.sendReceiveRequest();
   }

   public static List<String> getErrorsForStatusCode(int statusCode) {
      ArrayList errorList = new ArrayList();
      if (statusCode == Status.BAD_REQUEST.getStatusCode()) {
         errorList.add("Please try to log back in again.");
      } else if (statusCode == bluelab.connect.Web.Enum_Exception.PROCESSING_EXCEPTION.getStatusCode()) {
         errorList.add("Please check your internet connection.");
      }

      return errorList;
   }

   public static boolean isBadRequest(int statusCode) {
      return statusCode == Status.BAD_REQUEST.getStatusCode();
   }

   protected Map<String, String> getFields() {
      return new HashMap();
   }

   protected MultivaluedMap<String, Object> a() {
      return new MultivaluedHashMap();
   }

   protected Object getJson() {
      return !this.requestMethod.equals("GET") && !this.requestMethod.equals("DELETE") ? new MultivaluedHashMap() : null;
   }

   protected void onSuccess(Response var1) {
      if (this.onSuccessCallback != null) {
         this.onSuccessCallback.onSuccess(var1.getStatus(), var1.readEntity(String.class));
      }

   }

   protected void onError(Response var1) {
      StatusType var2 = var1.getStatusInfo();
      logger.error("Response error ({}: {})", var2.getStatusCode(), var2.getReasonPhrase());
      if (this.onFailureCallback != null) {
         this.onFailureCallback.onFailure(var2.getStatusCode(), var2.getReasonPhrase());
      }

   }
}