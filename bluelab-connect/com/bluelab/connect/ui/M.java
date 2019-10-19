package bluelab.connect.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;

public final class M extends JPanel {
   private static final Font a;

   static {
      a = e.c;
   }

   public M(String var1, String var2, String var3, Component var4, int var5) {
      this.setLayout(new BorderLayout(0, var5));
      this.setOpaque(false);
      JLabel var6;
      (var6 = new JLabel(var1)).setFont(a);
      this.add(var6, "North");
      BluelabButton var7;
      (var7 = new BluelabButton(var2, var3)).setHorizontalAlignment(0);
      this.add(var7, "Center");
      if (var4 != null) {
         this.add(var4, "South");
      }

   }
}