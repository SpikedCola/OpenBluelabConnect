package bluelab.connect.ui;

import java.awt.event.FocusEvent;
import javax.swing.border.Border;

public final class FocusTextField extends BluelabTextField {
   public FocusTextField(String var1, int var2) {
      super(var1, 14);
      this.setOpaque(false);
      this.setBorder((Border)null);
   }

   public final void focusGained(FocusEvent var1) {
      super.focusGained(var1);
      this.setOpaque(true);
      this.repaint();
   }

   public final void focusLost(FocusEvent var1) {
      super.focusLost(var1);
      this.setOpaque(false);
      this.repaint();
   }
}