package bluelab.connect.ui;

import bluelab.connect.Connect;

final class t implements Runnable {
   // $FF: synthetic field
   private BluelabFrame a;
   // $FF: synthetic field
   private final String b;
   // $FF: synthetic field
   private final String c;

   t(BluelabFrame var1, String var2, String var3) {
      this.a = var1;
      this.b = var2;
      this.c = var3;
      super();
   }

   public final void run() {
      if (this.b != null) {
         String var1 = String.format("<html> %s %s is available. %s</html>", Connect.GetTitle(), this.b, this.c);
         BluelabFrame.d(this.a).setText(var1);
         BluelabFrame.e(this.a).setVisible(true);
      }

   }
}