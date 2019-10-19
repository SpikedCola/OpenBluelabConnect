package bluelab.connect.GoogleAnalytics;

import java.io.UnsupportedEncodingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GA_tracker_ScreenView extends GA_tracker {
   private static Logger logger = LoggerFactory.getLogger(GA_tracker_ScreenView.class);
   private String screenName;

   public GA_tracker_ScreenView(String tid, String cid, String screenName) {
      super.trackingId = tid;
      super.customerNumber = cid;
      this.type = bluelab.connect.GoogleAnalytics.GA_tracker_EventType.ScreenView;
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
      this.data.put("cd", bluelab.connect.GoogleAnalytics.GA_tracker.UTF8(this.screenName, true));
   }
}