package bluelab.connect.ui;

import java.awt.Cursor;
import java.awt.Insets;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public final class K extends JButton {
   public K() {
      this((String)null, (String)null);
   }

   public K(String var1, String var2) {
      super(var1);
      this.setOpaque(false);
      this.setContentAreaFilled(false);
      this.setBorderPainted(false);
      this.setFocusPainted(false);
      this.setMargin(new Insets(0, 0, 0, 0));
      this.a(var2);
   }

   private void a(String var1) {
      if (var1 != null) {
         this.setToolTipText(var1);
         ActionListener[] var5;
         int var4 = (var5 = this.getActionListeners()).length;

         for(int var3 = 0; var3 < var4; ++var3) {
            ActionListener var2 = var5[var3];
            this.removeActionListener(var2);
         }

         this.addActionListener((var1x) -> {
            Helpers.c(var1);
         });
         this.setCursor(new Cursor(12));
      }

   }
}