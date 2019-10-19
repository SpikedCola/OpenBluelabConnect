package bluelab.connect.ui.b.b;

import bluelab.connect.model.SettingFileModel;

final class e extends x {
   // $FF: synthetic field
   private final i b;

   e(b var1, double var2, double var4, double var6, double var8, bluelab.connect.c.Enum_ControlType var10, SettingFileModel var11, i var12) {
      super(var2, var4, var6, var8, var10, var11);
      this.b = var12;
   }

   protected final synchronized void b(long var1) {
      if (var1 <= this.b.d.a) {
         this.b.d.d();
      }

      if (this.b.c != null && var1 <= this.b.c.a) {
         this.b.c.d();
      }

   }
}