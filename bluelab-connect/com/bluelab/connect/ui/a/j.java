package bluelab.connect.ui.a;

import bluelab.connect.ui.Helpers;

final class j implements Runnable {
   // $FF: synthetic field
   private Panel_LogInOut a;
   // $FF: synthetic field
   private final int b;
   // $FF: synthetic field
   private final Object c;

   j(Panel_LogInOut var1, int var2, Object var3) {
      super();
      this.a = var1;
      this.b = var2;
      this.c = var3;
   }

   public final void run() {
      Panel_LogInOut.a(this.a).setText("Logged out");
      Helpers.a(Panel_LogInOut.a(this.a));
      if (Panel_LogInOut.b(this.a) != null) {
         Panel_LogInOut.b(this.a).onSuccess(this.b, this.c);
      }

      bluelab.connect.GoogleAnalytics.GA_tracker_Sender.GetInstance().reportEvent("event", "DK_LogOutSuccess");
   }
}