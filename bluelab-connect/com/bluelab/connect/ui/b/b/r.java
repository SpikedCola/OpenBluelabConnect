package bluelab.connect.ui.b.b;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

final class r implements FocusListener {
   // $FF: synthetic field
   private p a;

   private r(p var1) {
      this.a = var1;
      super();
   }

   public final void focusGained(FocusEvent var1) {
   }

   public final void focusLost(FocusEvent var1) {
      p.a(this.a).name = p.b(this.a).getText();
   }

   // $FF: synthetic method
   r(p var1, byte var2) {
      this(var1);
   }
}