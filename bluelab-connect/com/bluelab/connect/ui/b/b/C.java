package bluelab.connect.ui.b.b;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

public final class C extends a {
   public C() {
      this.setOpaque(false);
      this.setBorder(new EmptyBorder(15, 10, 10, 10));
      this.setLayout(new BorderLayout(0, 0));
      this.setVisible(true);
      JLabel var1;
      (var1 = new JLabel("<html><div style='text-align: center;'><b>Waiting to receive settings from device...</b><br><small>This can take a few seconds.</small></div></html>")).setHorizontalAlignment(0);
      this.add(var1, "Center");
   }

   public final void a(boolean var1) {
   }

   public final void a(Object var1) {
   }
}