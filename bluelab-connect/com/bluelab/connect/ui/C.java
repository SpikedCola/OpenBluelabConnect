package bluelab.connect.ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

public class C extends JPanel {
   private int a;

   public C() {
      this(10);
   }

   private C(int var1) {
      this.a = 10;
      this.setOpaque(false);
   }

   protected void paintComponent(Graphics var1) {
      super.paintComponent(var1);
      Dimension var2 = new Dimension(this.a, this.a);
      int var3 = this.getWidth();
      int var4 = this.getHeight();
      Graphics2D var5;
      (var5 = (Graphics2D)var1).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      var5.setColor(this.getBackground());
      var5.fillRoundRect(0, 0, var3 - 1, var4 - 1, var2.width, var2.height);
      var5.setColor(this.getForeground());
      var5.drawRoundRect(0, 0, var3 - 1, var4 - 1, var2.width, var2.height);
   }
}