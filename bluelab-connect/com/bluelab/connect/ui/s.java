package bluelab.connect.ui;

final class s implements Runnable {
   // $FF: synthetic field
   private BluelabFrame a;

   s(BluelabFrame var1) {
      this.a = var1;
      super();
   }

   public final void run() {
      BluelabFrame.c(this.a).a(H.OFFLINE, "Connect stick disconnected");
   }
}