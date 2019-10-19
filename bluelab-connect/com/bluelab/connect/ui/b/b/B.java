package bluelab.connect.ui.b.b;

import java.util.TimerTask;

final class B extends TimerTask {
   // $FF: synthetic field
   private A a;

   B(A var1) {
      this.a = var1;
      super();
   }

   public final void run() {
      A.a(this.a).c();
   }
}