package bluelab.connect.GoogleAnalytics;

import java.io.UnsupportedEncodingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GA_tracker_Exception extends GA_tracker {
   private static Logger logger = LoggerFactory.getLogger(GA_tracker_Exception.class);
   private String message;
   private String fatal;

   public GA_tracker_Exception(String trackingId, String customerNumber, String message, boolean fatal) {
      super.trackingId = trackingId;
      super.customerNumber = customerNumber;
      this.type = GA_tracker_EventType.Exception;
      this.message = message;
      this.fatal = fatal ? "1" : "0";
   }

   public final int doRun() {
      logger.info("GA tracker started for {} '{}'", this.type.toString().toLowerCase(), this.message);
      return super.doRun();
   }

   protected final void fillData()
   throws UnsupportedEncodingException
   {
      super.fillData();
      this.data.put("exd", bluelab.connect.GoogleAnalytics.GA_tracker.UTF8(this.message, true));
      this.data.put("exf", bluelab.connect.GoogleAnalytics.GA_tracker.UTF8(this.fatal, true));
   }
}