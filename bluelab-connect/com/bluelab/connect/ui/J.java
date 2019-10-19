package bluelab.connect.ui;

import javax.swing.text.AttributeSet;
import javax.swing.text.PlainDocument;

public final class J extends PlainDocument {
   private int a;

   J(int var1) {
      this.a = var1;
   }

   public final void insertString(int var1, String var2, AttributeSet var3) {
      if (var2 != null) {
         if (this.getLength() + var2.length() <= this.a) {
            super.insertString(var1, var2, var3);
         }

      }
   }
}