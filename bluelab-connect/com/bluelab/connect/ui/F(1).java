package bluelab.connect.ui;

import java.awt.Component;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

final class F implements Runnable {
   // $FF: synthetic field
   private D a;
   // $FF: synthetic field
   private final int b;
   // $FF: synthetic field
   private final String c;
   // $FF: synthetic field
   private final Component d;

   F(D var1, int var2, String var3, Component var4) {
      this.a = var1;
      this.b = var2;
      this.c = var3;
      this.d = var4;
      super();
   }

   public final void run() {
      String var1 = String.format("Settings update error (%d: %s)", this.b, this.c);
      List var2 = bluelab.connect.Web.BluelabWebRequestResponse.getErrorsForStatusCode(this.b);
      D.a(this.a).a("");
      JOptionPane.showMessageDialog(this.d, "<html>" + var1 + "<br><br>" + String.join("<br>", var2) + "<br></html>", "Settings update error", 0, UIManager.getIcon("OptionPane.errorIcon"));
      this.a.a();
   }
}