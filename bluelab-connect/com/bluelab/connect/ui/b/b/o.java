package bluelab.connect.ui.b.b;

import bluelab.connect.model.PodSettings;
import bluelab.connect.model.PumpSettings;
import java.awt.Component;
import java.util.Iterator;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.event.ChangeListener;

public final class o extends JPanel {
   private int a;
   private PodSettings b;
   private JPanel c;

   public o(int var1, PodSettings var2, boolean var3, ChangeListener var4) {
      this.a = var1;
      this.b = var2;
      this.a(var3, var4);
   }

   private void a(boolean var1, ChangeListener var2) {
      this.setLayout(new BoxLayout(this, 1));
      this.setOpaque(false);
      this.c = new JPanel();
      this.c.setOpaque(false);
      this.add(this.c);
      Iterator var4 = this.b.getPumpSettings().iterator();

      while(var4.hasNext()) {
         PumpSettings var3 = (PumpSettings)var4.next();
         this.c.add(new p(var3, var1, var2));
      }

   }

   public final int a() {
      return this.a;
   }

   public final void a(boolean var1) {
      Component[] var5;
      int var4 = (var5 = this.c.getComponents()).length;

      for(int var3 = 0; var3 < var4; ++var3) {
         ((p)var5[var3]).a(var1);
      }

   }

   public final double b() {
      double var1 = 0.0D;
      Component[] var6;
      int var5 = (var6 = this.c.getComponents()).length;

      for(int var4 = 0; var4 < var5; ++var4) {
         p var3;
         if ((var3 = (p)var6[var4]).b().equals(bluelab.connect.c.Enum_PumpType.EC)) {
            var1 = Math.max(var3.c(), var1);
         }
      }

      return var1;
   }

   public final void a(double var1) {
      Component[] var6;
      int var5 = (var6 = this.c.getComponents()).length;

      for(int var4 = 0; var4 < var5; ++var4) {
         ((p)var6[var4]).a(var1);
      }

   }

   public final void a(String var1) {
      Component[] var5;
      int var4 = (var5 = this.c.getComponents()).length;

      for(int var3 = 0; var3 < var4; ++var3) {
         ((p)var5[var3]).a(var1);
      }

   }

   public final void c() {
      Component[] var4;
      int var3 = (var4 = this.c.getComponents()).length;

      for(int var2 = 0; var2 < var3; ++var2) {
         ((p)var4[var2]).a();
      }

   }
}