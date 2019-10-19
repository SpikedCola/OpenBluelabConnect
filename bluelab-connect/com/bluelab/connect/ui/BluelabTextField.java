package bluelab.connect.ui;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class BluelabTextField extends JTextField implements FocusListener {
   private final String a;
   private boolean b;
   private final Color c;

   public BluelabTextField(String var1) {
      super(var1);
      this.c = BluelabColour.h;
      super.setForeground(BluelabColour.m);
      super.setBackground(this.c);
      Border var2 = BorderFactory.createLineBorder(BluelabColour.n);
      super.setBorder(BorderFactory.createCompoundBorder(var2, BorderFactory.createEmptyBorder(3, 3, 3, 3)));
      this.a = var1;
      this.b = true;
      super.addFocusListener(this);
   }

   public BluelabTextField(String var1, int var2) {
      this(var1);
      super.setDocument(new J(var2));
   }

   public void focusGained(FocusEvent var1) {
      if (this.getText().trim().isEmpty()) {
         this.setTextForeground("");
      }

   }

   public void focusLost(FocusEvent var1) {
      if (this.getText().trim().isEmpty()) {
         this.setDefaultTextForegeround();
      }

   }

   public String getText() {
      return this.b ? "" : super.getText();
   }

   public void setText(String text) {
      if (this.hasFocus()) {
         super.setText(text);
      } else if (text.trim().isEmpty()) {
         this.setDefaultTextForegeround();
      } else {
         this.setTextForeground(text);
      }
   }

   private void setDefaultTextForegeround() {
      super.setText(this.a);
      super.setForeground(BluelabColour.m);
      this.b = true;
   }

   private void setTextForeground(String var1) {
      super.setText(var1);
      super.setForeground(BluelabColour.l);
      this.b = false;
   }
}