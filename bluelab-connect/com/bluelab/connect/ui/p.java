package bluelab.connect.ui;

final class p implements Runnable {
   // $FF: synthetic field
   private BluelabFrame a;
   // $FF: synthetic field
   private final bluelab.connect.c.BluelabRemoteXbeeDevice b;

   p(BluelabFrame var1, bluelab.connect.c.BluelabRemoteXbeeDevice var2) {
      this.a = var1;
      this.b = var2;
      super();
   }

   public final void run() {
      BluelabFrame.a(this.a).a(this.b);
      bluelab.connect.c.BluelabRemoteXbeeDevice var1 = BluelabFrame.a(this.a).b();
      bluelab.connect.c.BluelabRemoteXbeeDevice var2 = BluelabFrame.b(this.a).a();
      if (var1 != null && !var1.equals(var2) && var1.equals(this.b)) {
         BluelabFrame.b(this.a).a(this.b);
      }

   }
}