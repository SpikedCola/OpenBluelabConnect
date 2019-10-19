package bluelab.connect.ui.a;

final class d implements Runnable {
   // $FF: synthetic field
   private c a;

   d(c var1) {
      super();
      this.a = var1;
   }

   public final void run() {
      c.a(this.a).setText("");
      c.b(this.a).a("Feedback message sent", true);
      bluelab.connect.GoogleAnalytics.GA_tracker_Sender.GetInstance().reportEvent("event", "DK_FeedbackSuccess");
   }
}