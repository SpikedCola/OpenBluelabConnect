package bluelab.connect.ui;

import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public final class j extends JPanel {
   private JLabel a;

   public j(String var1) {
      this.setBackground(BluelabColour.g);
      this.setLayout(new BorderLayout());
      this.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, BluelabColour.n), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
      this.a = new JLabel("");
      this.a.setFont(e.i);
      this.a.setForeground(BluelabColour.l);
      this.add(this.a, var1);
   }

   public final void a(String var1) {
      this.a(var1, false);
   }

   public final void a(String var1, boolean var2) {
      this.a.setText(var1);
      if (var2) {
         Helpers.a(this.a);
      }

   }
}