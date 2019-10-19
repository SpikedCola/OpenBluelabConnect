package bluelab.connect.ui.b.b;

import java.awt.Component;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

final class h implements Runnable {
   // $FF: synthetic field
   private b a;
   // $FF: synthetic field
   private final Component b;
   // $FF: synthetic field
   private final String c;

   h(b var1, Component var2, String var3) {
      this.a = var1;
      this.b = var2;
      this.c = var3;
      super();
   }

   public final void run() {
      String var1 = "Device settings error";
      this.a.e.setText("");
      JOptionPane.showMessageDialog(this.b, "<html>" + var1 + "<br><br>" + this.c + "<br></html>", var1, 0, UIManager.getIcon("OptionPane.errorIcon"));
      bluelab.connect.GoogleAnalytics.GA_tracker_Sender.GetInstance().reportEvent("event", "DK_DeviceSettingFailure");
      this.a.f.setEnabled(true);
   }
}