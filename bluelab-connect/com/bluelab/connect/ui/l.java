package bluelab.connect.ui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

final class l extends MouseAdapter {
   // $FF: synthetic field
   private BluelabFrame a;

   l(BluelabFrame var1) {
      this.a = var1;
      super();
   }

   public final void mouseClicked(MouseEvent var1) {
      BluelabFrame.a(this.a, var1);
   }
}