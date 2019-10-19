package bluelab.connect.ui;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.BorderFactory;
import javax.swing.JTextArea;
import javax.swing.border.Border;

public final class g extends JTextArea implements FocusListener {
   private final String a;
   private boolean b;
   private final Color c;

   public g(String var1) {
      super(var1);
      this.c = BluelabColour.h;
      super.setForeground(BluelabColour.m);
      super.setBackground(this.c);
      Border var2 = BorderFactory.createLineBorder(BluelabColour.n);
      super.setBorder(BorderFactory.createCompoundBorder(var2, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
      super.setLineWrap(true);
      super.setWrapStyleWord(true);
      this.a = var1;
      this.b = true;
      super.addFocusListener(this);
   }

   public final void focusGained(FocusEvent var1) {
      if (this.getText().trim().isEmpty()) {
         this.a("");
      }

   }

   public final void focusLost(FocusEvent var1) {
      if (this.getText().trim().isEmpty()) {
         this.a();
      }

   }

   public final String getText() {
      return this.b ? "" : super.getText();
   }

   public final void setText(String var1) {
      if (this.hasFocus()) {
         super.setText(var1);
      } else if (var1.trim().isEmpty()) {
         this.a();
      } else {
         this.a(var1);
      }
   }

   private void a() {
      super.setText(this.a);
      super.setForeground(BluelabColour.m);
      this.b = true;
   }

   private void a(String var1) {
      super.setText(var1);
      super.setForeground(BluelabColour.l);
      this.b = false;
   }
}