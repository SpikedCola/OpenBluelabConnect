package bluelab.connect;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

final class BluelabWindow extends WindowAdapter {
   BluelabWindow(Connect var1) {
   }

   public final void windowClosed(WindowEvent var1) {
      System.exit(0);
   }
}