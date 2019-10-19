package bluelab.connect.ui.b.a;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

public final class d extends a {
   public d() {
      this.setOpaque(false);
      this.setBorder(new EmptyBorder(15, 10, 10, 10));
      this.setLayout(new BoxLayout(this, 2));
      this.setVisible(true);
      JLabel var1;
      (var1 = new JLabel(new ImageIcon(this.getClass().getResource("/resources/intro.png")))).setAlignmentX(0.0F);
      var1.setAlignmentY(1.0F);
      this.add(var1);
   }

   public final void a() {
   }
}