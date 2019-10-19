package bluelab.connect.ui.a;

import bluelab.connect.ui.Helpers;
import java.awt.Component;
import javax.swing.JOptionPane;

final class p implements Runnable {
   // $FF: synthetic field
   private o a;
   // $FF: synthetic field
   private final Component b;
   // $FF: synthetic field
   private final int c;
   // $FF: synthetic field
   private final Object d;

   p(o var1, Component var2, int var3, Object var4) {
      super();
      this.a = var1;
      this.b = var2;
      this.c = var3;
      this.d = var4;
   }

   public final void run() {
      o.a(this.a).setText("");
      o.b(this.a).setText("");
      o.c(this.a).setText("");
      o.d(this.a).setText("Sign up pending verification");
      Helpers.a(o.d(this.a));
      String var1 = "Please follow the verification email instructions before attempting to log in.";
      JOptionPane.showMessageDialog(this.b, var1);
      if (o.e(this.a) != null) {
         o.e(this.a).onSuccess(this.c, this.d);
      }

      bluelab.connect.GoogleAnalytics.GA_tracker_Sender.GetInstance().reportEvent("event", "DK_SignUpSuccess");
   }
}