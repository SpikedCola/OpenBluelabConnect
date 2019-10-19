package bluelab.connect.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public final class DownUpPanel extends JPanel {
   private JButton a;
   private Component b;

   public DownUpPanel(String var1, boolean var2, Component var3) {
      this.b = var3;
      this.setLayout(new BorderLayout(0, 0));
      this.setBackground(BluelabColour.o);
      this.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, BluelabColour.n), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
      this.a = new JButton(var1);
      this.a.setBorder(new EmptyBorder(0, 0, 0, 0));
      this.a.setContentAreaFilled(false);
      this.a.setBorderPainted(false);
      this.a.setFocusPainted(false);
      this.a.setHorizontalAlignment(2);
      this.a.setRolloverEnabled(true);
      this.a.addActionListener(this::a);
      this.add(this.a, "North");
      this.add(var3, "Center");
      if (var2) {
         this.a((ActionEvent)null);
      } else {
         this.a();
      }
   }

   private void a(ActionEvent var1) {
      this.b.setVisible(!this.b.isVisible());
      this.a();
   }

   private void a() {
      String var1 = this.b.isVisible() ? "/resources/chevron-down_16px.png" : "/resources/chevron-up_16px.png";
      String var2 = this.b.isVisible() ? "/resources/chevron-down-hover_16px.png" : "/resources/chevron-up-hover_16px.png";
      this.a.setIcon(new ImageIcon(this.getClass().getResource(var1)));
      this.a.setRolloverIcon(new ImageIcon(this.getClass().getResource(var2)));
   }
}