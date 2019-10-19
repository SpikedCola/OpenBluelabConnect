package bluelab.connect.ui;

import java.awt.Color;
import javax.swing.JLabel;

public final class BluelabColour {
   public static final Color a = Color.decode("#0e4393");
   public static final Color b = Color.decode("#3e94d0");
   public static final Color c;
   public static final Color d;
   public static final Color e;
   public static final Color f;
   public static final Color g;
   private static Color q;
   public static final Color h;
   public static final Color i;
   public static final Color j;
   public static final Color k;
   public static final Color l;
   public static final Color m;
   public static final Color n;
   public static final Color o;
   public static final Color p;

   static {
      Color.decode("#82bee8");
      Color.decode("#b9e5fb");
      c = Color.decode("#f7941e");
      Color.decode("#000000");
      Color.decode("#424142");
      d = Color.decode("#939598");
      e = Color.decode("#bcbec0");
      f = Color.decode("#efefef");
      g = Color.decode("#f7f7f7");
      q = Color.decode("#ed1c24");
      h = Color.decode("#ffffff");
      i = q;
      j = d;
      k = b;
      l = (new JLabel()).getForeground();
      m = d;
      n = f;
      o = h;
      p = b;
   }

   public static boolean a(Color var0) {
      double var1;
      return (var1 = 1.0D - (0.2126D * (double)var0.getRed() + 0.7152D * (double)var0.getGreen() + 0.0722D * (double)var0.getBlue()) / 255.0D) > 0.5D;
   }

   public static Color a(Color var0, float var1) {
      float[] var2;
      (var2 = Color.RGBtoHSB(var0.getRed(), var0.getGreen(), var0.getBlue(), (float[])null))[2] = Math.min(var1 * var2[2], 1.0F);
      return new Color(Color.HSBtoRGB(var2[0], var2[1], var2[2]));
   }

   public static Color b(Color var0) {
      return a(var0) ? h : l;
   }
}