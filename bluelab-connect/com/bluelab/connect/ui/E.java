package bluelab.connect.ui;

final class E implements Runnable {
   // $FF: synthetic field
   private D a;

   E(D var1) {
      this.a = var1;
      super();
   }

   public final void run() {
      D.a(this.a).a("Settings updated successfully", true);
   }
}