package bluelab.connect.ui.b;

final class i implements Runnable {
   // $FF: synthetic field
   private f a;
   // $FF: synthetic field
   private final String b;

   i(f var1, String var2) {
      this.a = var1;
      this.b = var2;
      super();
   }

   public final void run() {
      f.a(this.a, this.b);
      this.a.a.setIndeterminate(false);
      this.a.b.setEnabled(true);
   }
}