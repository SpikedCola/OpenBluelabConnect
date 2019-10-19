package bluelab.connect.ui.b;

import bluelab.connect.c.Enum_ControlType;
import bluelab.connect.c.UnitConverter;
import bluelab.connect.c.BluelabRemoteXbeeDevice;
import bluelab.connect.model.SettingFileModel;
import bluelab.connect.ui.Helpers;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class d extends JPanel {
   private SettingFileModel a;
   private boolean b;
   private JLabel c;
   private JLabel d;
   private JLabel e;
   private JLabel f;
   private JLabel g;
   private JLabel h;
   private JLabel i;
   private JLabel j;
   private JLabel k;
   private JLabel l;
   private JLabel m;
   private Border n;
   private Border o;
   private Color p;

   public d(BluelabRemoteXbeeDevice dev, SettingFileModel var2) {
      super(new BorderLayout(0, 0));
      this.a = var2;
      this.b = false;
      this.setPreferredSize(new Dimension(430, 90));
      JPanel var5;
      (var5 = new JPanel(new BorderLayout(0, 0))).setOpaque(false);
      this.add(var5, "West");
      this.h = new JLabel("");
      this.h.setBorder(new EmptyBorder(10, 10, 10, 10));
      var5.add(this.h, "Center");
      (var5 = new JPanel(new BorderLayout(0, 5))).setOpaque(false);
      var5.setBorder(new EmptyBorder(10, 5, 10, 10));
      this.add(var5, "Center");
      JPanel var3;
      (var3 = new JPanel(new BorderLayout(0, 0))).setOpaque(false);
      var5.add(var3, "North");
      this.c = new JLabel("");
      this.c.setFont(bluelab.connect.ui.e.e);
      var3.add(this.c, "West");
      this.g = new JLabel("");
      this.g.setFont(bluelab.connect.ui.e.i);
      var3.add(this.g, "South");
      JPanel var4;
      (var4 = new JPanel(new FlowLayout(1, 5, 0))).setOpaque(false);
      var3.add(var4, "East");
      this.i = new JLabel(new ImageIcon(this.getClass().getResource("/resources/control-tag.png")));
      this.i.setToolTipText("Controlling");
      var4.add(this.i);
      this.j = new JLabel(new ImageIcon(this.getClass().getResource("/resources/lock-tag.png")));
      this.j.setToolTipText("Locked out");
      var4.add(this.j);
      this.k = new JLabel(new ImageIcon(this.getClass().getResource("/resources/alarm-tag.png")));
      this.k.setToolTipText("Alarmed");
      var4.add(this.k);
      this.l = new JLabel(new ImageIcon(this.getClass().getResource("/resources/calibration-due-tag.png")));
      this.l.setToolTipText("Calibration due");
      var4.add(this.l);
      this.m = new JLabel(new ImageIcon(this.getClass().getResource("/resources/offline-tag.png")));
      this.m.setToolTipText("Offline");
      var4.add(this.m);
      (var3 = new JPanel(new GridLayout(1, 3, 0, 0))).setOpaque(false);
      var5.add(var3, "Center");
      (var5 = new JPanel(new FlowLayout(0, 5, 5))).setOpaque(false);
      var3.add(var5);
      this.d = new JLabel("");
      this.d.setFont(bluelab.connect.ui.e.b);
      var5.add(this.d);
      (var5 = new JPanel(new FlowLayout(1, 5, 5))).setOpaque(false);
      var3.add(var5);
      this.f = new JLabel("");
      this.f.setFont(bluelab.connect.ui.e.b);
      var5.add(this.f);
      (var5 = new JPanel(new FlowLayout(2, 5, 5))).setOpaque(false);
      var3.add(var5);
      this.e = new JLabel();
      this.e.setFont(bluelab.connect.ui.e.b);
      this.e.setHorizontalAlignment(4);
      var5.add(this.e);
      this.o = BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, bluelab.connect.ui.BluelabColour.n), BorderFactory.createEmptyBorder(0, 2, 0, 0));
      this.n = BorderFactory.createMatteBorder(0, 2, 1, 1, bluelab.connect.ui.BluelabColour.n);
      this.a(dev, false);
   }

   public final void a(BluelabRemoteXbeeDevice var1, boolean var2) {
      if (var2) {
         this.setBorder(this.n);
         this.p = bluelab.connect.ui.BluelabColour.g;
      } else {
         this.setBorder(this.o);
         this.p = bluelab.connect.ui.BluelabColour.h;
      }

      String image = Helpers.DeviceTypeToImage(var1.getDeviceType());
      this.h.setIcon(new ImageIcon(this.getClass().getResource(image)));
      this.c.setText(var1.getName());
      this.g.setText(var1.getDeviceTypeUiText() + " [" + var1.getKeyCode() + "]");
      if (var1.isOnline()) {
         this.c.setForeground(bluelab.connect.ui.BluelabColour.l);
         this.g.setForeground(bluelab.connect.ui.BluelabColour.l);
      } else {
         this.c.setForeground(bluelab.connect.ui.BluelabColour.j);
         this.g.setForeground(bluelab.connect.ui.BluelabColour.j);
      }

      this.a(this.d, var1, Enum_ControlType.CONDUCTIVITY);
      this.a(this.f, var1, Enum_ControlType.PH);
      this.a(this.e, var1, Enum_ControlType.TEMPERATURE);
      this.i.setVisible(var1.isOnline() && var1.G());
      this.j.setVisible(var1.modeIsControl() && var1.E());
      this.k.setVisible(var1.D());
      this.l.setVisible(var1.C());
      this.m.setVisible(!var1.isOnline());
      this.b = var1.A();
   }

   private void a(JLabel var1, BluelabRemoteXbeeDevice var2, Enum_ControlType var3) {
      boolean var4;
      if (var4 = var2.a(var3) != null) {
         var1.setText(String.format("<html>%s<sup style='font-size:0.5em'> %s</sup></html>", var2.a(var3, this.a), bluelab.connect.c.UnitConverter.GetUnit(var3, this.a)));
         if (!var2.isOnline()) {
            var1.setForeground(bluelab.connect.ui.BluelabColour.j);
         } else if (var2.d(var3)) {
            var1.setForeground(bluelab.connect.ui.BluelabColour.i);
         } else {
            var1.setForeground(bluelab.connect.ui.BluelabColour.l);
         }
      }

      var1.setVisible(var4);
   }

   protected void paintComponent(Graphics var1) {
      super.paintComponent(var1);
      Graphics2D var3 = (Graphics2D)var1;
      Dimension var2 = this.getSize();
      var3.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
      var3.setColor(this.p);
      var3.fillRect(0, 0, var2.width, var2.height);
      if (this.b) {
         var3.setColor(bluelab.connect.ui.BluelabColour.i);
         var3.setStroke(new BasicStroke(4.0F));
         var3.drawLine(7, 20, 7, var2.height - 10);
      }

      var3.setColor(bluelab.connect.ui.BluelabColour.e);
      var3.setStroke(new BasicStroke(2.0F));
      var3.drawLine(5, 5, 10, 10);
      var3.drawLine(5, 10, 10, 5);
   }
}