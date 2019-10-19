package bluelab.connect.ui.laf;

import bluelab.connect.ui.BluelabColour;
import bluelab.connect.ui.e;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;

public class MenuButtonUI extends BasicButtonUI {
   private Color a;
   private Color b;
   private Color c;
   private Color d;

   public MenuButtonUI(Color var1, Color var2) {
      this.a = var1;
      this.b = bluelab.connect.ui.BluelabColour.b(var2);
      this.c = var2;
      if (bluelab.connect.ui.BluelabColour.a(var2)) {
         this.d = bluelab.connect.ui.BluelabColour.a(var2, 1.1F);
      } else {
         this.d = bluelab.connect.ui.BluelabColour.a(var2, 0.95F);
      }
   }

   public void installUI(JComponent var1) {
      super.installUI(var1);
      AbstractButton var2;
      (var2 = (AbstractButton)var1).setFont(e.i);
      var2.setForeground(this.a);
      var2.setBackground(this.c);
      var2.setVerticalTextPosition(3);
      var2.setHorizontalTextPosition(0);
      var2.setAlignmentY(1.0F);
      var2.setVerticalAlignment(0);
      var2.setBorder(new EmptyBorder(5, 7, 5, 7));
      var2.setBorderPainted(false);
      var2.setFocusPainted(false);
   }

   public void paint(Graphics var1, JComponent var2) {
      Graphics2D var3 = (Graphics2D)var1;
      AbstractButton var4;
      Dimension var5 = (var4 = (AbstractButton)var2).getSize();
      if (var4.getModel().isSelected()) {
         var4.setForeground(this.b);
         var3.setColor(this.b);
         var3.setStroke(new BasicStroke(2.0F));
         var3.drawLine(0, var5.height - 2, var5.width, var5.height - 2);
      } else {
         var4.setForeground(this.a);
      }

      if (var4.getModel().isRollover()) {
         var4.setBackground(this.d);
      } else {
         var4.setBackground(this.c);
      }

      super.paint(var1, var2);
   }
}