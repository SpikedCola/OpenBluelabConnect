package bluelab.connect.ui.b.b;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;

public final class w extends JPanel {
   protected w(String var1) {
      this(var1, bluelab.connect.ui.e.f);
   }

   protected w(String var1, Font var2) {
      this.setOpaque(false);
      this.setBorder(new EmptyBorder(5, 0, 5, 0));
      this.setLayout(new BorderLayout(5, 5));
      JLabel var3;
      (var3 = new JLabel(var1)).setVerticalAlignment(0);
      var3.setFont(var2);
      this.add(var3, "West");
      this.add(new JSeparator(0), "South");
   }
}