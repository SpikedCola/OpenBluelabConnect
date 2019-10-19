package bluelab.connect.ui.b.b;

import java.awt.Component;
import javax.swing.JOptionPane;

final class n implements Runnable {
   // $FF: synthetic field
   private k a;
   // $FF: synthetic field
   private final Component b;
   // $FF: synthetic field
   private final String c;

   n(k var1, Component var2, String var3) {
      this.a = var1;
      this.b = var2;
      this.c = var3;
      super();
   }

   public final void run() {
      String var1 = "Pod settings error";
      this.a.e.setText("");
      bluelab.connect.GoogleAnalytics.GA_tracker_Sender.GetInstance().reportEvent("event", "DK_PodSettingFailure");
      JOptionPane.showMessageDialog(this.b, "<html>" + var1 + "<br><br>" + this.c + "<br></html>", var1, 0);
      this.a.f.setEnabled(true);
   }
}