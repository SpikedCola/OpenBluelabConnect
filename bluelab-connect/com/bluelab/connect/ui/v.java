package bluelab.connect.ui;

import javax.swing.ImageIcon;

public final class v {
   private String a;
   private String b;
   private ImageIcon c;
   private ImageIcon d;
   private String e;

   public v(String var1, String var2, String var3, String var4, String var5) {
      this.a = var1;
      this.b = var2;
      if (var3 != null) {
         this.c = new ImageIcon(this.getClass().getResource(var3));
      }

      if (var4 != null) {
         this.d = new ImageIcon(this.getClass().getResource(var4));
      }

      this.e = var5;
   }

   public final String a() {
      return this.a;
   }

   public final String b() {
      return this.b;
   }

   public final ImageIcon c() {
      return this.c;
   }

   public final ImageIcon d() {
      return this.d;
   }

   public final String e() {
      return this.e;
   }
}