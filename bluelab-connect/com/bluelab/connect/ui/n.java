package bluelab.connect.ui;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

final class n implements ListSelectionListener {
   // $FF: synthetic field
   private BluelabFrame a;

   n(BluelabFrame var1) {
      this.a = var1;
      super();
   }

   public final void valueChanged(ListSelectionEvent var1) {
      BluelabFrame.ListSelectEvent(this.a, var1);
   }
}