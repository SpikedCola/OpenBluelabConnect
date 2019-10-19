package bluelab.connect.ui.a;

import java.awt.Component;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

final class q implements Runnable {
   // $FF: synthetic field
   private o a;
   // $FF: synthetic field
   private final int b;
   // $FF: synthetic field
   private final String c;
   // $FF: synthetic field
   private final Component d;

   q(o var1, int var2, String var3, Component var4) {
      super();
      this.a = var1;
      this.b = var2;
      this.c = var3;
      this.d = var4;
   }

   public final void run() {
      String var1 = String.format("Sign up error (%d: %s)", this.b, this.c);
      List var2 = bluelab.connect.Web.BluelabWebRequestResponse.getErrorsForStatusCode(this.b);
      o.d(this.a).setText("");
      JOptionPane.showMessageDialog(this.d, "<html>" + var1 + "<br><br>" + String.join("<br>", var2) + "<br></html>", "Sign up error", 0, UIManager.getIcon("OptionPane.errorIcon"));
      bluelab.connect.GoogleAnalytics.GA_tracker_Sender.GetInstance().reportEvent("event", "DK_SignUpFailure");
   }
}