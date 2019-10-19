package bluelab.connect.ui;

final class q implements Runnable {
   // $FF: synthetic field
   private BluelabFrame a;

   q(BluelabFrame var1) {
      this.a = var1;
      super();
   }

   public final void run() {
      BluelabFrame.c(this.a).a(H.OFFLINE, "Detecting Connect stick...");
   }
}