package bluelab.connect.ui.a;

import bluelab.connect.ui.Helpers;
import java.awt.Component;
import javax.swing.JOptionPane;

final class m implements Runnable {
   // $FF: synthetic field
   private l a;
   // $FF: synthetic field
   private final Component b;
   // $FF: synthetic field
   private final int c;
   // $FF: synthetic field
   private final Object d;

   m(l var1, Component var2, int var3, Object var4) {
      super();
      this.a = var1;
      this.b = var2;
      this.c = var3;
      this.d = var4;
   }

   public final void run() {
      String var1 = l.a(this.a).getText();
      l.a(this.a).setText("");
      l.b(this.a).setText("Password was reset");
      Helpers.a(l.b(this.a));
      var1 = String.format("An email containing password reset details has been sent to %s.", var1);
      JOptionPane.showMessageDialog(this.b, var1, "Password Reset", 1);
      if (l.c(this.a) != null) {
         l.c(this.a).onSuccess(this.c, this.d);
      }

      bluelab.connect.GoogleAnalytics.GA_tracker_Sender.GetInstance().reportEvent("event", "DK_PasswordResetSuccess");
   }
}