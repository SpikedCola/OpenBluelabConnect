package bluelab.connect.GoogleAnalytics;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GA_tracker implements Runnable {
   private static Logger e = LoggerFactory.getLogger(GA_tracker.class);
   private String v = "1";
   protected String trackingId;
   protected String customerNumber;
   protected GA_tracker_EventType type;
   private String title;
   private String className;
   private String version;
   protected Map<String, String> data = new LinkedHashMap();
   private HttpURLConnection connection;
   private boolean k = false;

   public final void setTitleClassVersion(String title, String className, String version) {
      this.title = title;
      this.className = className;
      this.version = version;
   }

   public void run() {
      this.doRun();
   }

   public int doRun() {
      int responseCode = 0;

      try {
         this.connection = (HttpURLConnection)getURL(false).openConnection();
         this.connection.setRequestMethod("POST");
         this.connection.addRequestProperty("Content-Type", "application/x-www-form-urlencoded");
         this.connection.setConnectTimeout(5000);
         this.connection.setReadTimeout(30000);
         this.connection.setDoOutput(true);
         this.fillData();
         OutputStream var2 = this.connection.getOutputStream();
         Map var3 = this.data;
         ArrayList var4 = new ArrayList();
         Iterator var5 = var3.entrySet().iterator();

         while(var5.hasNext()) {
            Entry var8 = (Entry)var5.next();
            var4.add((String)var8.getKey() + "=" + (String)var8.getValue());
         }

         byte[] var9 = String.join("&", var4).getBytes(StandardCharsets.UTF_8);
         var2.write(var9);
         var2.close();
         responseCode = this.connection.getResponseCode();
         e.info("GA tracker response code: {}", responseCode);
      } catch (UnknownHostException var6) {
         e.error("GA network error: {}", var6.toString());
      } catch (IOException var7) {
         e.error("GA IO error: {}", var7.toString());
      }

      return responseCode;
   }

   private static URL getURL(boolean var0) {
      try {
         return var0 ? new URL("https", "www.google-analytics.com", "/debug/collect") : new URL("https", "www.google-analytics.com", "/collect");
      } catch (MalformedURLException var1) {
         throw new RuntimeException(var1);
      }
   }

   protected void fillData() 
   throws UnsupportedEncodingException
   {
      this.data.put("v", this.v);
      this.data.put("tid", this.trackingId);
      this.data.put("cid", this.customerNumber);
      this.data.put("t", this.type.toString().toLowerCase());
      if (this.title != null && !this.title.isEmpty()) {
         this.data.put("an", this.title);
      }

      if (this.className != null && !this.className.isEmpty()) {
         this.data.put("aid", this.className);
      }

      if (this.version != null && !this.version.isEmpty()) {
         this.data.put("av", this.version);
      }

   }

   protected static String UTF8(String var0, boolean var1)
   throws UnsupportedEncodingException
   {
      if (var0 == null) {
         throw new IllegalArgumentException("Required parameter not set.");
      } else {
         return URLEncoder.encode(var0, StandardCharsets.UTF_8.name());
      }
   }
}