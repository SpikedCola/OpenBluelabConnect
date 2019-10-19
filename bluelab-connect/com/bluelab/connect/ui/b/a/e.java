package bluelab.connect.ui.b.a;

import bluelab.connect.c.Enum_ControlType;
import bluelab.connect.c.BluelabRemoteXbeeDevice;
import bluelab.connect.model.SettingFileModel;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;

public final class e extends a {
   private JLabel f;
   private JLabel g;
   private JLabel h;
   private JLabel i;
   private JLabel j;
   private JLabel k;

   public e(BluelabRemoteXbeeDevice dev, SettingFileModel var2) {
      this.device = dev;
      this.settingFileModel = var2;
      this.setOpaque(false);
      this.setBorder(new EmptyBorder(15, 10, 10, 10));
      this.setLayout(new BorderLayout(0, 0));
      JPanel var4;
      (var4 = new JPanel(new BorderLayout(0, 0))).setOpaque(false);
      var4.setLayout(new GridBagLayout());
      this.add(var4, "North");
      GridBagConstraints var5;
      (var5 = new GridBagConstraints()).gridx = -1;
      var5.gridy = 0;
      var5.fill = 2;
      var5.insets = new Insets(10, 10, 10, 10);
      var5.weightx = 1.0D;
      JLabel var3 = new JLabel("Actual");
      var4.add(var3, var5);
      this.f = new JLabel("");
      this.f.setFont(c);
      this.f.setHorizontalAlignment(0);
      var4.add(this.f, var5);
      this.k = new JLabel("");
      this.k.setFont(c);
      this.k.setHorizontalAlignment(0);
      var4.add(this.k, var5);
      var5.gridy = 1;
      var3 = new JLabel("Required");
      var4.add(var3, var5);
      this.g = new JLabel("");
      this.g.setFont(d);
      this.g.setHorizontalAlignment(0);
      var4.add(this.g, var5);
      var5.gridy = 2;
      var5.gridwidth = 0;
      var4.add(new JSeparator(0), var5);
      var5.gridwidth = 1;
      var5.gridy = 3;
      var3 = new JLabel("Today's dose count");
      var4.add(var3, var5);
      this.h = new JLabel("");
      this.h.setFont(e);
      this.h.setHorizontalAlignment(0);
      var4.add(this.h, var5);
      var5.gridy = 4;
      var3 = new JLabel("Today's dose time");
      var4.add(var3, var5);
      this.i = new JLabel("");
      this.i.setFont(e);
      this.i.setHorizontalAlignment(0);
      var4.add(this.i, var5);
      var5.gridy = 5;
      var3 = new JLabel("Control direction");
      var4.add(var3, var5);
      this.j = new JLabel("");
      this.j.setFont(e);
      this.j.setHorizontalAlignment(0);
      var4.add(this.j, var5);
      var5.gridy = 6;
      var5.gridwidth = 0;
      var4.add(this.b(), var5);
      this.a();
   }

   public final void a() {
      this.a(this.f, Enum_ControlType.PH);
      this.a(this.g, this.h, this.i, this.j, Enum_ControlType.PH);
      this.a(this.k, Enum_ControlType.TEMPERATURE);
   }
}