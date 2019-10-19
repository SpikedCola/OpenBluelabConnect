package bluelab.connect.ui;

final class r implements Runnable {
   // $FF: synthetic field
   private BluelabFrame a;

   r(BluelabFrame var1) {
      this.a = var1;
      super();
   }

   public final void run() {
      BluelabFrame.c(this.a).a(H.ONLINE, "Connect stick found");
   }
}