package bluelab.connect.ui;

import java.awt.Cursor;
import java.awt.Insets;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

public class BluelabButton extends JButton {
   public BluelabButton() {
      this((ImageIcon)null, (String)null);
   }

   public BluelabButton(String icon, String toolTipText) {
      this(new ImageIcon(BluelabButton.class.getResource(icon)), toolTipText);
   }

   public BluelabButton(ImageIcon icon, String toolTipText) {
      if (icon != null) {
         this.setIcon(icon);
      }

      this.setBorder(new EmptyBorder(0, 0, 0, 0));
      this.setContentAreaFilled(false);
      this.setBorderPainted(false);
      this.setFocusPainted(false);
      this.setMargin(new Insets(0, 0, 0, 0));
      this.setToolTipTextAndUpdateListeners(toolTipText);
   }

   private void setToolTipTextAndUpdateListeners(String toolTipText) {
      if (toolTipText != null) {
         this.setToolTipText(toolTipText);
         ActionListener[] var5;
         int var4 = (var5 = this.getActionListeners()).length;

         for(int var3 = 0; var3 < var4; ++var3) {
            ActionListener var2 = var5[var3];
            this.removeActionListener(var2);
         }

         this.addActionListener((var1x) -> {
            Helpers.c(toolTipText);
         });
         this.setCursor(new Cursor(12));
      }

   }
}