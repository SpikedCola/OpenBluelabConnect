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
import javax.swing.border.EmptyBorder;

public final class c extends a {
   private JLabel f;
   private JLabel g;
   private JLabel h;

   public c(BluelabRemoteXbeeDevice var1, SettingFileModel var2) {
      this.device = var1;
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
      this.g = new JLabel("");
      this.g.setFont(c);
      this.g.setHorizontalAlignment(0);
      var4.add(this.g, var5);
      this.h = new JLabel("");
      this.h.setFont(c);
      this.h.setHorizontalAlignment(0);
      var4.add(this.h, var5);
      this.a();
   }

   public final void a() {
      this.a(this.f, Enum_ControlType.CONDUCTIVITY);
      this.a(this.g, Enum_ControlType.PH);
      this.a(this.h, Enum_ControlType.TEMPERATURE);
   }
}