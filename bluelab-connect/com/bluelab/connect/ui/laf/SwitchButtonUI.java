package bluelab.connect.ui.laf;

import bluelab.connect.ui.BluelabColour;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicToggleButtonUI;

public class SwitchButtonUI extends BasicToggleButtonUI {
   private static final Color a;

   static {
      a = BluelabColour.d;
   }

   public void installUI(JComponent var1) {
      super.installUI(var1);
      AbstractButton var2;
      (var2 = (AbstractButton)var1).setForeground(BluelabColour.h);
      var2.setBackground(BluelabColour.c);
      var2.setOpaque(false);
      var2.setBorderPainted(false);
      var2.setFocusPainted(false);
   }

   public void paint(Graphics var1, JComponent var2) {
      AbstractButton var3;
      Dimension var4 = (var3 = (AbstractButton)var2).getSize();
      Graphics2D var5 = (Graphics2D)var1;
      RenderingHints var6;
      (var6 = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)).put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
      var5.setRenderingHints(var6);
      if (var3.getModel().isRollover()) {
         var5.setColor(BluelabColour.a(a(var3), 0.95F));
      } else {
         var5.setColor(a(var3));
      }

      var5.fillRoundRect(0, 0, var4.width, var4.height, var4.height, var4.height);
      var5.setColor(var3.getForeground());
      if (var3.isSelected()) {
         var5.fillOval(var4.width - var4.height * 5 / 6, var4.height / 6, (var4.height << 2) / 6, (var4.height << 2) / 6);
      } else {
         var5.fillOval(var4.height / 6, var4.height / 6, (var4.height << 2) / 6, (var4.height << 2) / 6);
      }

      var5.setColor(BluelabColour.a(a(var3), 0.9F));
      var5.drawRoundRect(0, 0, var4.width - 1, var4.height - 1, var4.height, var4.height);
      var1.setFont(var2.getFont());
      FontMetrics var7 = var1.getFontMetrics();
      String var9 = var3.getText();
      int var10 = (var4.width - var7.stringWidth(var9)) / 2;
      int var8 = (var4.height - var7.getHeight()) / 2 + var7.getAscent();
      var5.setColor(var3.getForeground());
      if (var3.isSelected()) {
         var5.drawString(var9, var10 - var4.height / 3, var8);
      } else {
         var5.drawString(var9, var10 + var4.height / 3, var8);
      }
   }

   private static Color a(AbstractButton var0) {
      return var0.isSelected() ? var0.getBackground() : a;
   }
}