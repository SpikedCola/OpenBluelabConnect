package bluelab.connect.ui.b;

import java.awt.Component;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

final class h implements Runnable {
   // $FF: synthetic field
   private f a;
   // $FF: synthetic field
   private final Component b;

   h(f var1, Component var2) {
      this.a = var1;
      this.b = var2;
      super();
   }

   public final void run() {
      this.a.a.setIndeterminate(false);
      this.a.b.setEnabled(true);
      String var1 = f.a(this.a);
      var1 = String.format("<html>Your device has successfully updated to %s!<br><br>Note: Please double check your settings at the device.<br>If you have a PeriPod attached accept the pod change message if it appears.</html>", var1);
      JOptionPane.showMessageDialog(this.b, var1, "Device firmware update success", 1, UIManager.getIcon("OptionPane.infoIcon"));
   }
}