package bluelab.connect.ui.b.b;

import bluelab.connect.model.SettingFileModel;
import bluelab.connect.ui.Helpers;
import bluelab.connect.ui.laf.RoundedButtonUI;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class b extends a {
   protected bluelab.connect.d.BluelabDevice dev;
   protected bluelab.connect.c.BluelabRemoteXbeeDevice remoteDev;
   protected GridBagConstraints c;
   protected JPanel d;
   private JScrollPane h;
   private bluelab.connect.ui.BluelabTextField i;
   protected JLabel e;
   protected JButton f;
   protected bluelab.connect.c.c.MonitorController monitorController;
   private Map<bluelab.connect.c.Enum_ControlType, i> controlTypePanelMap;
   // $FF: synthetic field
   private static int[] k;

   public b(bluelab.connect.d.BluelabDevice var1, bluelab.connect.c.BluelabRemoteXbeeDevice var2) {
      this.dev = var1;
      this.remoteDev = var2;
      this.setOpaque(false);
      this.setBorder(new EmptyBorder(5, 5, 5, 5));
      this.setLayout(new BorderLayout(0, 0));
      this.h = new JScrollPane();
      this.h.setBorder(BorderFactory.createLineBorder(bluelab.connect.ui.BluelabColour.n));
      this.h.getVerticalScrollBar().setUnitIncrement(10);
      this.add(this.h, "Center");
      JPanel var5;
      (var5 = new JPanel(new BorderLayout(0, 0))).setBackground(bluelab.connect.ui.BluelabColour.o);
      this.h.setViewportView(var5);
      this.d = new JPanel(new GridBagLayout());
      this.d.setBorder(new EmptyBorder(5, 0, 5, 0));
      this.d.setOpaque(false);
      this.c = c();
      this.a(0);
      var5.add(this.d, "North");
      JPanel var6;
      (var6 = new JPanel()).setOpaque(false);
      var6.setBorder(new EmptyBorder(5, 10, 0, 10));
      var6.setLayout(new BorderLayout());
      JPanel var3;
      (var3 = new JPanel()).setOpaque(false);
      this.f = new JButton("Apply");
      this.f.setUI(new RoundedButtonUI());
      this.f.setToolTipText("Apply/write settings to device");
      this.f.addActionListener(this::b);
      var3.add(this.f);
      JButton var4;
      (var4 = new JButton("Refresh")).setUI(new RoundedButtonUI(bluelab.connect.ui.Enum_RoundedButtonType.ALTERNATIVE));
      var4.setToolTipText("Refresh settings read from device");
      var4.addActionListener(this::a);
      var3.add(var4);
      JPanel var7;
      (var7 = new JPanel(new BorderLayout())).setOpaque(false);
      var7.setBorder(new EmptyBorder(0, 5, 5, 5));
      this.e = new JLabel("");
      this.e.setFont(bluelab.connect.ui.e.i);
      var7.add(this.e, "West");
      c().fill = 2;
      var6.add(var3, "North");
      var6.add(var7, "South");
      this.add(var6, "South");
   }

   protected void a(int var1) {
      this.controlTypePanelMap = new HashMap();
      a((JComponent)(new w("Device Settings", bluelab.connect.ui.e.e)), this.d, this.c, var1);
      this.i = new bluelab.connect.ui.BluelabTextField("Device name", 20);
      this.i.setFont(bluelab.connect.ui.e.f);
      Helpers.a(this.i, 140);
      this.a("Name", this.i, this.d, this.c, var1 + 1);
   }

   protected static JTextArea a(String var0) {
      JTextArea var1;
      (var1 = new JTextArea(var0 + "\n")).setFont(bluelab.connect.ui.e.j);
      var1.setForeground(bluelab.connect.ui.BluelabColour.m);
      var1.setEditable(false);
      var1.setLineWrap(true);
      var1.setWrapStyleWord(true);
      return var1;
   }

   private i a(bluelab.connect.c.Enum_ControlType var1) {
      if (this.controlTypePanelMap.containsKey(var1)) {
         return (i)this.controlTypePanelMap.get(var1);
      } else {
         i var2 = new i(this);
         this.controlTypePanelMap.put(var1, var2);
         return var2;
      }
   }

   protected final void a(bluelab.connect.c.Enum_ControlType var1, JPanel var2, GridBagConstraints var3, int var4) {
      a((JComponent)(new w(var1.getUiText())), var2, var3, var4);
      int var9 = var4 + 1;
      SettingFileModel var10 = this.dev.getSettingFileModel();
      i var11 = this.a(var1);
      double var12 = var1.getMinValue();
      double var14 = var1.getMaxValue();
      double var16 = var1.getDeltaValue();
      if (var1 == bluelab.connect.c.Enum_ControlType.TEMPERATURE) {
         var11.c = new c(this, var12 + var16, var12 + var16, var14 - var16, var16, var1, var10, var11);
         this.a("Required (on)", var11.c, var2, var3, var9);
      }

      var11.d = new d(this, var12 + var16, var12 + var16, var14 - var16, var16, var1, var10, var11);
      if (var1 == bluelab.connect.c.Enum_ControlType.TEMPERATURE) {
         this.a("Required (off)", var11.d, var2, var3, var9);
      } else {
         this.a("Required", var11.d, var2, var3, var9);
      }

      var9 = var4 + 1;
      var10 = this.dev.getSettingFileModel();
      var11 = this.a(var1);
      bluelab.connect.c.Enum_DeviceType var18 = this.remoteDev.getDeviceType();
      j var15 = new j(this);
      switch(d()[var18.ordinal()]) {
      case 2:
         var15.a = 1.0D;
         var15.b = 60.0D;
         var15.c = 1.0D;
         var15.d = 60.0D;
         var15.e = 3600.0D;
         var15.f = 60.0D;
         break;
      default:
         var15.a = 0.0D;
         var15.b = 600.0D;
         var15.c = 1.0D;
         var15.d = 10.0D;
         var15.e = 3600.0D;
         var15.f = 10.0D;
      }

      var11.e = new x(var15.a, var15.a, var15.b, var15.c, bluelab.connect.c.Enum_ControlType.TIME, var10);
      this.a("On time", var11.e, var2, var3, var9);
      var11.f = new x(var15.d, var15.d, var15.e, var15.f, bluelab.connect.c.Enum_ControlType.TIME, var10);
      this.a("Off time", var11.f, var2, var3, var9);
      var9 = var4 + 1;
      var10 = this.dev.getSettingFileModel();
      var11 = this.a(var1);
      var12 = var1.getMinValue();
      var14 = var1.getMaxValue();
      var16 = var1.getDeltaValue();
      var11.b = new e(this, var14, var12 + var16 * 2.0D, var14, var16, var1, var10, var11);
      this.a("High alarm", var11.b, var2, var3, var9);
      var11.a = new f(this, var12, var12, var14 - var16 * 2.0D, var16, var1, var10, var11);
      this.a("Low alarm", var11.a, var2, var3, var9);
   }

   private static GridBagConstraints c() {
      GridBagConstraints var0;
      (var0 = new GridBagConstraints()).anchor = 11;
      var0.gridx = -1;
      var0.gridy = 0;
      var0.fill = 0;
      var0.weightx = 1.0D;
      return var0;
   }

   protected static void a(JComponent var0, JPanel var1, GridBagConstraints var2, int var3) {
      var2.anchor = 17;
      var2.insets = new Insets(3, 20 + var3 * 15, 3, 20);
      var2.weightx = 0.0D;
      var2.gridwidth = 2;
      var2.fill = 2;
      var1.add(var0, var2);
      ++var2.gridy;
   }

   protected final JLabel a(String var1, JComponent var2, JPanel var3, GridBagConstraints var4, int var5) {
      return a(var1, bluelab.connect.ui.e.h, var2, var3, var4, var5);
   }

   protected static JLabel a(String var0, Font var1, JComponent var2, JPanel var3, GridBagConstraints var4, int var5) {
      JPanel var6;
      (var6 = new JPanel(new FlowLayout(1, 0, 0))).setOpaque(false);
      JLabel var7;
      (var7 = new JLabel(var0)).setFont(var1);
      var6.add(var7);
      var4.anchor = 17;
      var4.insets = new Insets(3, 20 + var5 * 15, 3, 150);
      var4.fill = 0;
      var4.weightx = 0.0D;
      var4.gridwidth = 1;
      var3.add(var6, var4);
      var4.anchor = 13;
      var4.insets = new Insets(3, 20, 3, 20);
      var4.fill = 0;
      var4.weightx = 0.0D;
      var3.add(var2, var4);
      ++var4.gridy;
      return var7;
   }

   protected final void b(String var1) {
      this.e.setText(var1);
      this.e.setForeground(bluelab.connect.ui.BluelabColour.l);
      Helpers.a(this.e);
   }

   public void a(boolean var1) {
      if (var1) {
         this.a((ActionEvent)null);
      }

   }

   protected void a(ActionEvent var1) {
      this.monitorController = new bluelab.connect.c.c.MonitorController(this.remoteDev);
      this.i.setText(this.monitorController.name.trim());
      this.controlTypePanelMap.forEach((var1x, var2) -> {
         i var3 = (i)this.controlTypePanelMap.get(var1x);
         bluelab.connect.c.c.MinMaxValue var4 = this.monitorController.getMinMaxValue(var1x);
         bluelab.connect.c.c.ControlTypeInstance var5 = this.monitorController.getControlTypeInstance(var1x);
         if (var3 != null) {
            if (var4 != null) {
               var3.a.a(var4.minValue);
               var3.b.a(var4.maxValue);
            }

            if (var5 != null) {
               var3.d.a(var5.deltaValue2);
               if (var3.c != null) {
                  var3.c.a(var5.deltaValue1);
               }

               var3.e.a((double)var5.onTime);
               var3.f.a((double)var5.offTime);
            }
         }

      });
      if (this.e != null) {
         this.b("Device settings refreshed");
      }

   }

   protected void b(ActionEvent var1) {
      if (!this.remoteDev.getMode().equals(bluelab.connect.c.Enum_Mode.SETTINGS) && !this.remoteDev.getMode().equals(bluelab.connect.c.Enum_Mode.CALIBRATION)) {
         this.a();
         bluelab.connect.c.c.c var2;
         (var2 = new bluelab.connect.c.c.c(this.dev.getXbeeDevice(), this.remoteDev, this.monitorController)).setSuccessFailCallbacks(this::b, this::c);
         (new Thread(var2)).start();
         this.e.setText("Writing device settings...");
         this.e.setForeground(bluelab.connect.ui.BluelabColour.l);
         this.f.setEnabled(false);
      } else {
         JOptionPane.showMessageDialog(SwingUtilities.getRoot(this), "Cannot apply settings while device is in settings/calibration mode.", "Device settings warning", 2, UIManager.getIcon("OptionPane.warningIcon"));
      }
   }

   protected void a() {
      this.monitorController.name = this.i.getText().trim();
      this.controlTypePanelMap.forEach((var1, var2) -> {
         i var3 = (i)this.controlTypePanelMap.get(var1);
         bluelab.connect.c.c.MinMaxValue var4 = this.monitorController.getMinMaxValue(var1);
         bluelab.connect.c.c.ControlTypeInstance var5 = this.monitorController.getControlTypeInstance(var1);
         if (var3 != null) {
            if (var4 != null) {
               var4.minValue = var3.a.a();
               var4.maxValue = var3.b.a();
            }

            if (var5 != null) {
               var5.deltaValue2 = var3.d.a();
               if (var3.c != null) {
                  var5.deltaValue1 = var3.c.a();
               }

               var5.onTime = (int)var3.e.a();
               var5.offTime = (int)var3.f.a();
            }
         }

      });
   }

   public void b() {
      this.remoteDev.a(this.monitorController);
      EventQueue.invokeLater(new g(this));
   }

   public void c(String var1) {
      Component var2 = SwingUtilities.getRoot(this);
      EventQueue.invokeLater(new h(this, var2, var1));
   }

   public void a(Object var1) {
      this.controlTypePanelMap.forEach((var1x, var2) -> {
         i var3;
         if ((var3 = (i)this.controlTypePanelMap.get(var1x)) != null) {
            var3.a.b();
            var3.b.b();
            if (var3.c != null) {
               var3.c.b();
            }

            var3.d.b();
            var3.e.b();
            var3.f.b();
         }

      });
   }

   // $FF: synthetic method
   private static int[] d() {
      int[] var10000 = k;
      if (k != null) {
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

         k = var0;
         return var0;
      }
   }
}