package bluelab.connect.GoogleAnalytics;

public final class GA_tracker_Sender {
   private static GA_tracker_Sender instance = null;
   private String customerNumber = "unknown";
   private String title;
   private String className;
   private String version;

   public static GA_tracker_Sender GetInstance() {
      if (instance == null) {
         instance = new GA_tracker_Sender();
      }
      return instance;
   }

   public final void setCustomerNumber(String customerNumber) {
      this.customerNumber = customerNumber;
   }

   public final void setTitleClassVersion(String title, String className, String version) {
      this.title = title;
      this.className = className;
      this.version = version;
   }

   public final void reportScreenView(String screenName) {
      GA_tracker_ScreenView tracker = new GA_tracker_ScreenView("UA-86872379-1", this.customerNumber, screenName);
      this.sendTrackerThreaded((GA_tracker)tracker);
   }

   public final void reportEvent(String eventType, String screenName) {
      GA_tracker_Event tracker = new GA_tracker_Event("UA-86872379-1", this.customerNumber, eventType, screenName);
      this.sendTrackerThreaded((GA_tracker)tracker);
   }

   public final void reportException(String message, boolean fatal) {
      GA_tracker_Exception tracker = new GA_tracker_Exception("UA-86872379-1", this.customerNumber, message, fatal);
      this.sendTrackerThreaded((GA_tracker)tracker);
   }

   private void sendTrackerThreaded(GA_tracker tracker) {
      tracker.setTitleClassVersion(this.title, this.className, this.version);
      (new Thread(tracker)).start();
   }
}