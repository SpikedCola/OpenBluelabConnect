package bluelab.connect.ui;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;

public final class A extends CardLayout {
   public final Dimension preferredLayoutSize(Container var1) {
      Component[] var5;
      int var4 = (var5 = var1.getComponents()).length;
      int var3 = 0;

      Component var10000;
      Component var2;
      while(true) {
         if (var3 >= var4) {
            var10000 = null;
            break;
         }

         if ((var2 = var5[var3]).isVisible()) {
            var10000 = var2;
            break;
         }

         ++var3;
      }

      var2 = var10000;
      if (var10000 != null) {
         Insets var6 = var1.getInsets();
         Dimension var7;
         Dimension var8 = var7 = var2.getPreferredSize();
         var8.width += var6.left + var6.right;
         var7.height += var6.top + var6.bottom;
         return var7;
      } else {
         return super.preferredLayoutSize(var1);
      }
   }
}