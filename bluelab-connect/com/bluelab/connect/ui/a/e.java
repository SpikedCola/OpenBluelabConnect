package bluelab.connect.ui.a;

import java.awt.Component;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

final class e implements Runnable {
   // $FF: synthetic field
   private c a;
   // $FF: synthetic field
   private final int b;
   // $FF: synthetic field
   private final String c;
   // $FF: synthetic field
   private final Component d;

   e(c var1, int var2, String var3, Component var4) {
      super();
      this.a = var1;
      this.b = var2;
      this.c = var3;
      this.d = var4;
   }

   public final void run() {
      String var1 = String.format("Feedback error (%d: %s)", this.b, this.c);
      List var2 = bluelab.connect.Web.BluelabWebRequestResponse.getErrorsForStatusCode(this.b);
      bluelab.connect.ui.a.c.b(this.a).a("");
      JOptionPane.showMessageDialog(this.d, "<html>" + var1 + "<br><br>" + String.join("<br>", var2) + "<br></html>", "Feedback error", 0, UIManager.getIcon("OptionPane.errorIcon"));
      bluelab.connect.GoogleAnalytics.GA_tracker_Sender.GetInstance().reportEvent("event", "DK_FeedbackFailure");
   }
}