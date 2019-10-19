package bluelab.connect.ui.b.b;

import bluelab.connect.model.SettingFileModel;
import java.awt.Dimension;
import java.util.Locale;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class x extends JPanel {
   protected long a;
   private long b;
   private long c;
   private long d;
   private JLabel e;
   private bluelab.connect.c.Enum_ControlType f;
   private SettingFileModel g;

   public x(double var1, double var3, double var5, double var7, bluelab.connect.c.Enum_ControlType var9, SettingFileModel var10) {
      this.a = (long)(var1 * 1000.0D);
      this.b = (long)(var3 * 1000.0D);
      this.c = (long)(var5 * 1000.0D);
      this.d = (long)(var7 * 1000.0D);
      this.f = var9;
      this.g = var10;
      this.setOpaque(false);
      this.setLayout(new BoxLayout(this, 0));
      this.e = new JLabel(this.e());
      this.e.setFont(bluelab.connect.ui.e.e);
      this.e.setVerticalAlignment(0);
      this.e.setHorizontalAlignment(0);
      this.add(this.e);
      this.add(Box.createRigidArea(new Dimension(5, 0)));
      bluelab.connect.ui.B var11;
      (var11 = new bluelab.connect.ui.B("/resources/minus.png", "/resources/minus-hover.png")).addMouseListener(new y(this));
      this.add(var11);
      (var11 = new bluelab.connect.ui.B("/resources/plus.png", "/resources/plus-hover.png")).addMouseListener(new A(this));
      this.add(var11);
   }

   public final synchronized double a() {
      return new Double(Long.toString(this.a)) / 1000.0D;
   }

   public final synchronized void a(double var1) {
      this.a = (long)(var1 * 1000.0D);
      this.b();
   }

   public final void b() {
      this.e.setText(this.e());
   }

   private String e() {
      double var1 = this.a();
      SettingFileModel var2 = this.g;
      double var5 = var1;
      bluelab.connect.c.Enum_ControlType var15 = this.f;
      String var3 = "";
      String var16;
      if (var15.equals(bluelab.connect.c.Enum_ControlType.TIME)) {
         var16 = "";
         if (var5 < 1.0E-6D) {
            var16 = "Continuous";
         } else if (var5 < 60.0D) {
            var16 = String.format(Locale.ROOT, "%.0f s", var5);
         } else if (var5 >= 60.0D) {
            int var17 = (int)var5 / 60;
            double var13 = var5 % 60.0D;
            var16 = String.format(Locale.ROOT, "%d m %.0f s", var17, var13);
         }

         var3 = var16;
      } else if (var15.equals(bluelab.connect.c.Enum_ControlType.CONDUCTIVITY)) {
         var3 = bluelab.connect.c.UnitConverter.ConvertConductivityUnit(var5, var2.conductivityUnit);
      } else if (var15.equals(bluelab.connect.c.Enum_ControlType.PH)) {
         var3 = bluelab.connect.c.UnitConverter.FormatConductivityAsEC(var5);
      } else if (var15.equals(bluelab.connect.c.Enum_ControlType.TEMPERATURE)) {
         var3 = bluelab.connect.c.UnitConverter.ConvertTemperatureUnit(var5, var2.temperatureUnit);
      }

      var16 = var3;
      if (this.f != bluelab.connect.c.Enum_ControlType.TIME) {
         var16 = var3 + " " + bluelab.connect.c.UnitConverter.GetUnit(this.f, this.g);
      }

      return var16;
   }

   protected final synchronized void c() {
      this.a += this.d;
      this.a = Math.min(this.a, this.c);
      this.a(this.a);
      this.b();
   }

   protected final synchronized void d() {
      this.a -= this.d;
      this.a = Math.max(this.a, this.b);
      this.b(this.a);
      this.b();
   }

   protected synchronized void a(long var1) {
   }

   protected synchronized void b(long var1) {
   }
}