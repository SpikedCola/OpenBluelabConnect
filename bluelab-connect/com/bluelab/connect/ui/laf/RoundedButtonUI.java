package bluelab.connect.ui.laf;

import bluelab.connect.ui.Enum_RoundedButtonType;
import bluelab.connect.ui.BluelabColour;
import bluelab.connect.ui.e;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;

public class RoundedButtonUI extends BasicButtonUI {
   private Enum_RoundedButtonType a;
   // $FF: synthetic field
   private static int[] b;

   public RoundedButtonUI() {
      this(bluelab.connect.ui.Enum_RoundedButtonType.DEFAULT);
   }

   public RoundedButtonUI(Enum_RoundedButtonType var1) {
      this.a = var1;
   }

   public void installUI(JComponent var1) {
      super.installUI(var1);
      AbstractButton var4 = (AbstractButton)var1;
      switch(a()[this.a.ordinal()]) {
      case 3:
      case 4:
         var4.setBackground(BluelabColour.h);
         var4.setForeground(BluelabColour.l);
         break;
      default:
         var4.setBackground(BluelabColour.c);
         var4.setForeground(BluelabColour.h);
      }

      switch(a()[this.a.ordinal()]) {
      case 2:
      case 4:
         var4.setFont(e.e);
         var4.setMargin(new Insets(10, 10, 10, 10));
         break;
      case 3:
      default:
         var4.setFont(e.h);
         var4.setMargin(new Insets(5, 5, 5, 5));
      }

      var4.setBorder(new EmptyBorder(7, 10, 7, 10));
      var4.setOpaque(false);
      var4.setBorderPainted(false);
      var4.setFocusPainted(false);
   }

   public void paint(Graphics var1, JComponent var2) {
      AbstractButton var3;
      Dimension var4 = (var3 = (AbstractButton)var2).getSize();
      Graphics2D var5 = (Graphics2D)var1;
      RenderingHints var6;
      (var6 = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)).put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
      var5.setRenderingHints(var6);
      if (var3.getModel().isRollover()) {
         var5.setColor(BluelabColour.a(var3.getBackground(), 0.95F));
      } else {
         var5.setColor(var3.getBackground());
      }

      var5.fillRoundRect(0, 0, var4.width, var4.height, var4.height / 4, var4.height / 4);
      var5.setColor(BluelabColour.a(var3.getBackground(), 0.9F));
      var5.drawRoundRect(0, 0, var4.width - 1, var4.height - 1, var4.height / 4, var4.height / 4);
      var1.setFont(var2.getFont());
      FontMetrics var7 = var1.getFontMetrics();
      String var9 = var3.getText();
      int var10 = (var4.width - var7.stringWidth(var9)) / 2;
      int var8 = (var4.height - var7.getHeight()) / 2 + var7.getAscent();
      var5.setColor(var3.getForeground());
      var5.drawString(var9, var10, var8);
   }

   // $FF: synthetic method
   private static int[] a() {
      int[] var10000 = b;
      if (b != null) {
         return var10000;
      } else {
         int[] var0 = new int[bluelab.connect.ui.Enum_RoundedButtonType.values().length];

         try {
            var0[bluelab.connect.ui.Enum_RoundedButtonType.ALTERNATIVE.ordinal()] = 3;
         } catch (NoSuchFieldError var4) {
            ;
         }

         try {
            var0[bluelab.connect.ui.Enum_RoundedButtonType.ALTERNATIVE_BIGGER.ordinal()] = 4;
         } catch (NoSuchFieldError var3) {
            ;
         }

         try {
            var0[bluelab.connect.ui.Enum_RoundedButtonType.DEFAULT.ordinal()] = 1;
         } catch (NoSuchFieldError var2) {
            ;
         }

         try {
            var0[bluelab.connect.ui.Enum_RoundedButtonType.DEFAULT_BIGGER.ordinal()] = 2;
         } catch (NoSuchFieldError var1) {
            ;
         }

         b = var0;
         return var0;
      }
   }
}