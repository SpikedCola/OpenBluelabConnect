package bluelab.connect.GoogleAnalytics;

import java.io.UnsupportedEncodingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GA_tracker_Event extends GA_tracker {
   private static Logger logger = LoggerFactory.getLogger(GA_tracker_Event.class);
   private String eventType;
   private String screenName;

   public GA_tracker_Event(String trackingId, String customerNumber, String eventType, String screenName) {
      super.trackingId = trackingId;
      super.customerNumber = customerNumber;
      this.type = bluelab.connect.GoogleAnalytics.GA_tracker_EventType.Event;
      this.eventType = eventType;
      this.screenName = screenName;
   }

   public final int doRun() {
      logger.info("GA tracker started for {} '{}'", this.type.toString().toLowerCase(), this.screenName);
      return super.doRun();
   }

   protected final void fillData()
   throws UnsupportedEncodingException
   {
      super.fillData();
      this.data.put("ec", bluelab.connect.GoogleAnalytics.GA_tracker.UTF8(this.eventType, true));
      this.data.put("ea", bluelab.connect.GoogleAnalytics.GA_tracker.UTF8(this.screenName, true));
   }
}