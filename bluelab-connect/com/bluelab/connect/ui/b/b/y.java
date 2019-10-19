package bluelab.connect.ui.b.b;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;

final class y extends MouseAdapter {
   private Timer a;
   // $FF: synthetic field
   private x b;

   y(x var1) {
      this.b = var1;
      super();
   }

   public final void mousePressed(MouseEvent var1) {
      this.a = new Timer();
      this.a.schedule(new z(this), 500L, 10L);
   }

   public final void mouseReleased(MouseEvent var1) {
      if (this.a != null) {
         this.a.cancel();
      }

      this.b.d();
   }

   // $FF: synthetic method
   static x a(y var0) {
      return var0.b;
   }
}