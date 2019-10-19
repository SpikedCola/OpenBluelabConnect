package bluelab.connect.ui.b.b;

import java.awt.Component;
import javax.swing.JOptionPane;

final class l implements Runnable {
   // $FF: synthetic field
   private final Component a;

   l(k var1, Component var2) {
      this.a = var2;
   }

   public final void run() {
      JOptionPane.showMessageDialog(this.a, "Some pod chain components are not currently compatible due to different pump sizes.", "Pod chain error", 0);
   }
}