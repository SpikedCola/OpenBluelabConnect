package bluelab.connect.ui.b;

import bluelab.connect.c.BluelabRemoteXbeeDevice;
import bluelab.connect.ui.G;
import bluelab.connect.ui.H;
import bluelab.connect.ui.Helpers;
import bluelab.connect.ui.v;
import bluelab.connect.ui.BluelabPanel;
import bluelab.connect.ui.b.b.C;
import bluelab.connect.ui.b.b.k;
import bluelab.connect.ui.b.b.s;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public final class OverviewPanel extends JPanel {
   private bluelab.connect.d.BluelabDevice dev;
   private BluelabRemoteXbeeDevice remoteDev;
   private BluelabPanel c;
   private G d;
   private JPanel e;
   private bluelab.connect.ui.b.a.a f;
   private bluelab.connect.ui.b.b.a g;
   private bluelab.connect.ui.b.b.a h;
   private f i;
   private Map<String, f> j;
   private bluelab.connect.ui.j k;
   // $FF: synthetic field
   private static int[] l;

   public OverviewPanel(bluelab.connect.d.BluelabDevice var1, BluelabRemoteXbeeDevice var2, Color var3, Color var4, boolean var5) {
      this.dev = var1;
      this.remoteDev = var2;
      this.setBackground(var3);
      this.setBorder(new EmptyBorder(0, 0, 0, 0));
      this.setLayout(new BorderLayout(0, 0));
      JPanel var6;
      (var6 = new JPanel(new BorderLayout(0, 0))).setOpaque(false);
      this.add(var6, "North");
      ArrayList var7;
      (var7 = new ArrayList()).add(new v(Helpers.a("DeviceOverview"), "DeviceOverview", (String)null, (String)null, Helpers.b("DeviceOverview")));
      if (var5) {
         var7.add(new v(Helpers.a("DeviceSettings"), "DeviceSettings", (String)null, (String)null, Helpers.b("DeviceSettings")));
         var7.add(new v(Helpers.a("NutrientDosing"), "NutrientDosing", (String)null, (String)null, Helpers.b("NutrientDosing")));
         var7.add(new v(Helpers.a("FirmwareUpdate"), "FirmwareUpdate", (String)null, (String)null, Helpers.b("FirmwareUpdate")));
      }

      this.c = new BluelabPanel((ImageIcon)null, (String)null, var7, this::b, var4, var3, 2);
      var6.add(this.c, "North");
      this.d = new G();
      var6.add(this.d, "Center");
      this.e = new JPanel(new CardLayout());
      this.e.setBackground(bluelab.connect.ui.BluelabColour.o);
      this.add(this.e, "Center");
      this.j = new HashMap();
      this.k = new bluelab.connect.ui.j("East");
      this.add(this.k, "South");
      this.g();
      this.b();
   }

   public final BluelabRemoteXbeeDevice a() {
      return this.remoteDev;
   }

   public final void a(BluelabRemoteXbeeDevice var1) {
      this.remoteDev = var1;
      this.b();
   }

   public final void a(ActionEvent var1) {
      Object var2 = ((JComboBox)var1.getSource()).getSelectedItem();
      this.h.a(var2);
      this.g.a(var2);
   }

   private void b() {
      String var3;
      if (this.remoteDev != null) {
         BluelabPanel var10000 = this.c;
         ImageIcon var2 = null;
         if (this.remoteDev != null) {
            var3 = Helpers.DeviceTypeToImage(this.remoteDev.getDeviceType());
            var2 = new ImageIcon(this.getClass().getResource(var3));
            var2 = new ImageIcon(var2.getImage().getScaledInstance(32, 32, 4));
         }

         var10000.a((ImageIcon)var2, (String)null);
      }

      OverviewPanel var1 = this;
      Object var10001;
      if (this.remoteDev != null) {
         switch(h()[this.remoteDev.getDeviceType().ordinal()]) {
         case 2:
            var10001 = new bluelab.connect.ui.b.a.e(this.remoteDev, this.dev.getSettingFileModel());
            break;
         case 3:
            var10001 = new bluelab.connect.ui.b.a.b();
            break;
         case 4:
            var10001 = new bluelab.connect.ui.b.a.f(this.remoteDev, this.dev.getSettingFileModel());
            break;
         case 5:
            var10001 = new bluelab.connect.ui.b.a.c(this.remoteDev, this.dev.getSettingFileModel());
            break;
         default:
            var10001 = new bluelab.connect.ui.b.a.d();
         }
      } else {
         var10001 = new bluelab.connect.ui.b.a.d();
      }

      this.f = (bluelab.connect.ui.b.a.a)var10001;
      synchronized(this) {
         if (var1.c()) {
            var1.h = var1.e();
         } else {
            var1.h = new C();
         }

         if (var1.d()) {
            var1.g = var1.f();
         } else {
            var1.g = new C();
         }
      }

      f var7;
      if (this.remoteDev != null) {
         var3 = this.remoteDev.getKeyCode();
         if (this.j.containsKey(var3)) {
            var7 = (f)this.j.get(var3);
         } else {
            f var4 = new f(this.dev, this.remoteDev);
            this.j.put(var3, var4);
            var7 = var4;
         }
      } else {
         var7 = null;
      }

      this.i = var7;
      this.e.removeAll();
      this.e.add(this.f, "DeviceOverview");
      if (this.h != null) {
         this.e.add(this.h, "DeviceSettings");
      }

      if (this.g != null) {
         this.e.add(this.g, "NutrientDosing");
      }

      if (this.i != null) {
         this.e.add(this.i, "FirmwareUpdate");
      }

      this.c.a("DeviceOverview");
      if (this.remoteDev != null) {
         boolean var6 = this.remoteDev.getDeviceVersion().equals(bluelab.connect.c.Enum_DeviceVersion.PRO_CONTROLLER_V2);
         this.c.a("NutrientDosing", var6);
         var6 = this.remoteDev.getBluelabRemoteDevice().a();
         this.c.a("FirmwareUpdate", var6);
      }

      this.a(true);
   }

   public final void a(boolean var1) {
      this.g();
      this.d.setVisible(this.remoteDev != null);
      if (this.remoteDev != null) {
         H var3;
         String var6;
         if (this.remoteDev.isOnline()) {
            if (!(var6 = this.remoteDev.getMessageAlarmRangeLockoutsPods()).isEmpty()) {
               var3 = H.ERROR;
            } else {
               var3 = H.ONLINE;
               var6 = "Device in " + this.remoteDev.getMode().getUiText().toLowerCase() + " mode";
            }
         } else {
            var3 = H.OFFLINE;
            var6 = "Device offline";
         }

         this.d.a(var3, var6);
      }

      if (this.remoteDev != null) {
         this.f.a();
         boolean var2 = this.remoteDev.getPeripodWithPumpsListSize() > 0;
         this.c.a("NutrientDosing", var2);
         if (this.g.isVisible() && !var2) {
            this.c.a("DeviceOverview");
         }

         synchronized(this) {
            if (this.h instanceof C && this.c()) {
               this.h = this.a(this.h, this::e, "DeviceSettings");
               this.h.a(true);
            } else {
               this.h.a(var1);
            }

            if (this.g instanceof C && this.d()) {
               this.g = this.a(this.g, this::f, "NutrientDosing");
               this.g.a(true);
            } else {
               this.g.a(var1);
            }
         }
      }

      if (this.remoteDev != null) {
         String var9 = "-";
         if (this.remoteDev.getMillis() > 0L) {
            long var10 = System.currentTimeMillis() - this.remoteDev.getMillis();
            var9 = String.format("%.0f", (double)var10 / 1000.0D);
         }

         this.k.a(String.format("Last message received %s s ago", var9));
      } else {
         this.k.a("");
      }
   }

   private boolean c() {
      return this.remoteDev != null && !this.remoteDev.changeTypeListContains(bluelab.connect.c.Enum_ChangeType.DEVICE_SETTING);
   }

   private boolean d() {
      return this.remoteDev != null && !this.remoteDev.changeTypeListContains(bluelab.connect.c.Enum_ChangeType.DEVICE_SETTING) && !this.remoteDev.changeTypeListContains(bluelab.connect.c.Enum_ChangeType.POD_SETTING);
   }

   private bluelab.connect.ui.b.b.a a(bluelab.connect.ui.b.b.a var1, Supplier<bluelab.connect.ui.b.b.a> var2, String var3) {
      boolean var4 = var1.isVisible();
      this.e.remove(var1);
      var1 = (bluelab.connect.ui.b.b.a)var2.get();
      this.e.add(var1, var3);
      if (var4) {
         ((CardLayout)this.e.getLayout()).show(this.e, var3);
      }

      return var1;
   }

   private bluelab.connect.ui.b.b.a e() {
      if (this.remoteDev != null) {
         switch(h()[this.remoteDev.getDeviceType().ordinal()]) {
         case 2:
            return new s(this.dev, this.remoteDev);
         case 3:
         case 5:
            return new bluelab.connect.ui.b.b.b(this.dev, this.remoteDev);
         case 4:
            return new bluelab.connect.ui.b.b.v(this.dev, this.remoteDev);
         default:
            return null;
         }
      } else {
         return null;
      }
   }

   private bluelab.connect.ui.b.b.a f() {
      return this.remoteDev != null && this.remoteDev.getDeviceType().equals(bluelab.connect.c.Enum_DeviceType.PRO_CONTROLLER) ? new k(this.dev, this.remoteDev) : null;
   }

   private void b(ActionEvent var1) {
      String var3 = ((AbstractButton)var1.getSource()).getActionCommand();
      ((CardLayout)this.e.getLayout()).show(this.e, var3);
      bluelab.connect.GoogleAnalytics.GA_tracker_Sender.GetInstance().reportScreenView("DK_" + var3);
      this.a(false);
   }

   private void g() {
      this.c.setVisible(this.remoteDev != null);
      if (this.remoteDev != null) {
         this.c.a(this.remoteDev.getName(), this.remoteDev.getDeviceTypeUiText() + " [" + this.remoteDev.getKeyCode() + "]  " + this.remoteDev.getIdent1() + " " + this.remoteDev.getIdent2());
      } else {
         this.c.a("", "");
      }
   }

   // $FF: synthetic method
   private static int[] h() {
      int[] var10000 = l;
      if (l != null) {
         return var10000;
      } else {
         int[] var0 = new int[bluelab.connect.c.Enum_DeviceType.values().length];

         try {
            var0[bluelab.connect.c.Enum_DeviceType.EXTENDER.ordinal()] = 3;
         } catch (NoSuchFieldError var5) {
            ;
         }

         try {
            var0[bluelab.connect.c.Enum_DeviceType.GUARDIAN.ordinal()] = 5;
         } catch (NoSuchFieldError var4) {
            ;
         }

         try {
            var0[bluelab.connect.c.Enum_DeviceType.PH_CONTROLLER.ordinal()] = 2;
         } catch (NoSuchFieldError var3) {
            ;
         }

         try {
            var0[bluelab.connect.c.Enum_DeviceType.PRO_CONTROLLER.ordinal()] = 4;
         } catch (NoSuchFieldError var2) {
            ;
         }

         try {
            var0[bluelab.connect.c.Enum_DeviceType.UNKNOWN.ordinal()] = 1;
         } catch (NoSuchFieldError var1) {
            ;
         }

         l = var0;
         return var0;
      }
   }
}