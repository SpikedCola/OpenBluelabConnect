package bluelab.connect.ui;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.BorderFactory;
import javax.swing.JPasswordField;
import javax.swing.border.Border;

public final class f extends JPasswordField implements FocusListener {
   private final String a;
   private char b;
   private boolean c;
   private final Color d;

   public f(String var1) {
      super(var1);
      this.d = bluelab.connect.ui.BluelabColour.h;
      super.setForeground(bluelab.connect.ui.BluelabColour.m);
      super.setBackground(this.d);
      Border var2 = BorderFactory.createLineBorder(bluelab.connect.ui.BluelabColour.n);
      super.setBorder(BorderFactory.createCompoundBorder(var2, BorderFactory.createEmptyBorder(5, 5, 5, 5)));
      this.a = var1;
      this.b = super.getEchoChar();
      super.setEchoChar('\u0000');
      this.c = true;
      super.addFocusListener(this);
   }

   public final void focusGained(FocusEvent var1) {
      if (this.getPassword().length == 0) {
         super.setText("");
         super.setForeground(bluelab.connect.ui.BluelabColour.l);
         super.setEchoChar(this.b);
         this.c = false;
      }

   }

   public final void focusLost(FocusEvent var1) {
      if (this.getPassword().length == 0) {
         super.setText(this.a);
         super.setForeground(bluelab.connect.ui.BluelabColour.m);
         super.setEchoChar('\u0000');
         this.c = true;
      }

   }

   public final char[] getPassword() {
      return this.c ? "".toCharArray() : super.getPassword();
   }
}