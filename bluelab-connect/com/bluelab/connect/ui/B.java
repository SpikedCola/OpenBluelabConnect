package bluelab.connect.ui;

import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public final class B extends JButton {
   public B(String var1, String var2) {
      this.setContentAreaFilled(false);
      this.setBorderPainted(false);
      this.setFocusPainted(false);
      ImageIcon var3 = new ImageIcon(this.getClass().getResource(var1));
      this.setIcon(var3);
      this.setPreferredSize(new Dimension(var3.getIconWidth(), var3.getIconHeight()));
      this.setRolloverEnabled(true);
      this.setRolloverIcon(new ImageIcon(this.getClass().getResource(var2)));
   }
}