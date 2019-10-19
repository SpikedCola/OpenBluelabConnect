package bluelab.connect.ui.b;

final class g implements Runnable {
   // $FF: synthetic field
   private f a;
   // $FF: synthetic field
   private final float b;
   // $FF: synthetic field
   private final String c;

   g(f var1, float var2, String var3) {
      this.a = var1;
      this.b = var2;
      this.c = var3;
      super();
   }

   public final void run() {
      if (this.b > 0.0F) {
         this.a.a.setIndeterminate(false);
      }

      if (this.b >= 1.0F) {
         this.a.a.setIndeterminate(true);
      }

      this.a.a.setValue((int)(this.b * (float)this.a.a.getMaximum()));
      f.a(this.a, this.c);
   }
}