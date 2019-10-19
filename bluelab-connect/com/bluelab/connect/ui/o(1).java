package bluelab.connect.ui;

import java.util.List;

final class o implements Runnable {
   // $FF: synthetic field
   private BluelabFrame frame;
   // $FF: synthetic field
   private final List b;

   o(BluelabFrame frame, List var2) {
      super();
      this.frame = frame;
      this.b = var2;
   }

   public final void run() {
      BluelabFrame.logger.info("Devices changed");
      BluelabFrame.a(this.frame).a(this.b);
   }
}