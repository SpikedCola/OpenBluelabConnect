package bluelab.connect.ui.a;

import bluelab.connect.ui.Helpers;

final class g implements Runnable {
   // $FF: synthetic field
   private CloudLogInPanel a;
   // $FF: synthetic field
   private final int b;
   // $FF: synthetic field
   private final Object c;

   g(CloudLogInPanel var1, int var2, Object var3) {
      super();
      this.a = var1;
      this.b = var2;
      this.c = var3;
   }

   public final void run() {
      CloudLogInPanel.a(this.a).setText("");
      CloudLogInPanel.b(this.a).setText("");
      CloudLogInPanel.c(this.a).setText("Logged in");
      Helpers.a(CloudLogInPanel.c(this.a));
      if (CloudLogInPanel.OnSuccess(this.a) != null) {
         CloudLogInPanel.OnSuccess(this.a).onSuccess(this.b, this.c);
      }

      bluelab.connect.GoogleAnalytics.GA_tracker_Sender.GetInstance().setCustomerNumber(Long.toString(CloudLogInPanel.e(this.a).customerNr));
      bluelab.connect.GoogleAnalytics.GA_tracker_Sender.GetInstance().reportEvent("event", "DK_LogInSuccess");
   }
}