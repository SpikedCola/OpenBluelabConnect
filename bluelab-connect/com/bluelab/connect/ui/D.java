package bluelab.connect.ui;

import bluelab.connect.model.SettingFileModel;
import bluelab.connect.ui.laf.RoundedButtonUI;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public final class D extends JPanel implements bluelab.connect.Web.Interface_OnSuccessFailure {
   private bluelab.connect.f.CSVWriter a;
   private SettingFileModel b;
   private bluelab.connect.ui.a.AccountPanel c;
   private JComboBox<bluelab.connect.k.Enum_TemperatureUnit> d;
   private JComboBox<bluelab.connect.k.Enum_ECUnit> e;
   private JComboBox<bluelab.connect.k.Enum_VolumeUnit> f;
   private JTextField g;
   private JComboBox<bluelab.connect.f.b> h;
   private JCheckBox i;
   private j j;
   private List<ActionListener> k;

   public D(bluelab.connect.f.CSVWriter var1, SettingFileModel var2) {
      this.a = var1;
      this.b = var2;
      this.setOpaque(false);
      this.setLayout(new BorderLayout(0, 0));
      JScrollPane var8;
      (var8 = new JScrollPane()).setBorder(new EmptyBorder(0, 0, 0, 0));
      var8.getVerticalScrollBar().setUnitIncrement(10);
      this.add(var8, "Center");
      JPanel var3;
      (var3 = new JPanel(new BorderLayout(0, 0))).setBackground(bluelab.connect.ui.BluelabColour.o);
      var8.setViewportView(var3);
      JPanel var9;
      (var9 = new JPanel()).setOpaque(false);
      var9.setLayout(new GridBagLayout());
      var3.add(var9, "North");
      GridBagConstraints var10;
      (var10 = new GridBagConstraints()).anchor = 11;
      var10.insets = new Insets(0, 0, 5, 0);
      var10.gridx = 0;
      var10.gridy = -1;
      var10.fill = 2;
      var10.weightx = 1.0D;
      this.c = new bluelab.connect.ui.a.AccountPanel(var2);
      this.c.a((bluelab.connect.Web.Interface_OnSuccessFailure)this);
      var9.add(this.c, var10);
      JPanel var4;
      (var4 = new JPanel(new GridBagLayout())).setOpaque(false);
      GridBagConstraints var5;
      (var5 = new GridBagConstraints()).insets = new Insets(5, 15, 0, 15);
      var5.fill = 2;
      JLabel var6;
      (var6 = new JLabel("Units")).setFont(bluelab.connect.ui.e.g);
      var5.gridx = 0;
      var5.gridy = 0;
      var4.add(var6, var5);
      var5.gridx = 0;
      var5.gridy = 1;
      var5.gridwidth = 2;
      var4.add(new JSeparator(0), var5);
      var5.gridx = 0;
      var5.gridy = 2;
      var5.gridwidth = 1;
      var5.weightx = 1.0D;
      var4.add(new JLabel("Temperature display unit"), var5);
      this.d = new JComboBox(bluelab.connect.k.Enum_TemperatureUnit.values());
      this.d.setBackground(bluelab.connect.ui.BluelabColour.h);
      this.d.setFocusable(false);
      this.d.setEditable(false);
      bluelab.connect.k.Enum_TemperatureUnit var11 = (bluelab.connect.k.Enum_TemperatureUnit)bluelab.connect.j.EnumValueFind.FindValueCaseInsensitive(bluelab.connect.k.Enum_TemperatureUnit.class, this.b.temperatureUnit, bluelab.connect.k.Enum_TemperatureUnit.GetTemperatureUnitC());
      this.d.setSelectedItem(var11);
      this.d.addActionListener(this::a);
      var5.gridx = 1;
      var5.gridy = 2;
      var5.weightx = 0.0D;
      var4.add(this.d, var5);
      var5.gridx = 0;
      var5.gridy = 3;
      var5.weightx = 1.0D;
      var4.add(new JLabel("Conductivity display unit"), var5);
      this.e = new JComboBox(bluelab.connect.k.Enum_ECUnit.values());
      this.e.setBackground(bluelab.connect.ui.BluelabColour.h);
      this.e.setFocusable(false);
      bluelab.connect.k.Enum_ECUnit var12 = (bluelab.connect.k.Enum_ECUnit)bluelab.connect.j.EnumValueFind.FindValueCaseInsensitive(bluelab.connect.k.Enum_ECUnit.class, this.b.conductivityUnit, bluelab.connect.k.Enum_ECUnit.GetECType());
      this.e.setSelectedItem(var12);
      this.e.addActionListener(this::a);
      var5.gridx = 1;
      var5.gridy = 3;
      var5.weightx = 0.0D;
      var4.add(this.e, var5);
      var5.gridx = 0;
      var5.gridy = 4;
      var5.weightx = 1.0D;
      var4.add(new JLabel("Dosing display unit"), var5);
      this.f = new JComboBox(bluelab.connect.k.Enum_VolumeUnit.values());
      this.f.setBackground(bluelab.connect.ui.BluelabColour.h);
      this.f.setFocusable(false);
      bluelab.connect.k.Enum_VolumeUnit var13 = (bluelab.connect.k.Enum_VolumeUnit)bluelab.connect.j.EnumValueFind.FindValueCaseInsensitive(bluelab.connect.k.Enum_VolumeUnit.class, this.b.ratioUnit, bluelab.connect.k.Enum_VolumeUnit.GetVolumeTypeML());
      this.f.setSelectedItem(var13);
      this.f.addActionListener(this::a);
      var5.gridx = 1;
      var5.gridy = 4;
      var5.weightx = 0.0D;
      var4.add(this.f, var5);
      var9.add(var4, var10);
      (var4 = new JPanel(new GridBagLayout())).setOpaque(false);
      (var5 = new GridBagConstraints()).insets = new Insets(5, 15, 0, 15);
      var5.fill = 2;
      (var6 = new JLabel("Data logging")).setFont(bluelab.connect.ui.e.g);
      var5.gridx = 0;
      var5.gridy = 0;
      var4.add(var6, var5);
      var5.gridx = 0;
      var5.gridy = 1;
      var5.gridwidth = 3;
      var4.add(new JSeparator(0), var5);
      var5.gridx = 0;
      var5.gridy = 2;
      var5.gridwidth = 1;
      var5.weightx = 0.0D;
      var4.add(new JLabel("Log data directory"), var5);
      this.g = new JTextField(this.b.logDataDirectory);
      this.g.setToolTipText(this.b.logDataDirectory);
      this.g.setEditable(false);
      var5.gridx = 1;
      var5.gridwidth = 2;
      var5.weightx = 1.0D;
      var4.add(this.g, var5);
      JPanel var14;
      (var14 = new JPanel()).setOpaque(false);
      var5.gridx = 0;
      var5.gridy = 3;
      var5.gridwidth = 3;
      var4.add(var14, var5);
      JButton var7;
      (var7 = new JButton("Select...")).setUI(new RoundedButtonUI(bluelab.connect.ui.Enum_RoundedButtonType.ALTERNATIVE));
      var7.addActionListener(this::b);
      var14.add(var7);
      (var7 = new JButton("Open")).setUI(new RoundedButtonUI(bluelab.connect.ui.Enum_RoundedButtonType.ALTERNATIVE));
      var7.addActionListener(this::c);
      var14.add(var7);
      var5.gridx = 0;
      var5.gridy = 4;
      var5.gridwidth = 1;
      var5.weightx = 0.0D;
      var4.add(new JLabel("Log data interval"), var5);
      this.h = new JComboBox(bluelab.connect.f.b.values());
      this.h.setBackground(bluelab.connect.ui.BluelabColour.h);
      this.h.setFocusable(false);
      this.h.setSelectedItem(bluelab.connect.f.b.a(this.b.logDataInterval));
      this.h.addActionListener(this::d);
      var5.gridx = 2;
      var5.fill = 0;
      var5.anchor = 13;
      var4.add(this.h, var5);
      var9.add(var4, var10);
      (var4 = new JPanel(new GridBagLayout())).setOpaque(false);
      (var5 = new GridBagConstraints()).insets = new Insets(5, 15, 0, 15);
      var5.fill = 2;
      (var6 = new JLabel("Alerts")).setFont(bluelab.connect.ui.e.g);
      var5.gridx = 0;
      var5.gridy = 0;
      var4.add(var6, var5);
      var5.gridx = 0;
      var5.gridy = 1;
      var5.gridwidth = 2;
      var4.add(new JSeparator(0), var5);
      this.i = new JCheckBox("Device email alerts", this.b.emailAlerts);
      this.i.setOpaque(false);
      this.i.addActionListener(this::e);
      var5.gridx = 0;
      var5.gridy = 2;
      var5.weightx = 1.0D;
      var4.add(this.i, var5);
      var5.gridx = 0;
      var5.gridy = 3;
      var4.add(new JLabel("Note: Email alerts require you to be logged in."), var5);
      var9.add(var4, var10);
      this.j = new j("West");
      this.add(this.j, "South");
      this.k = new ArrayList();
      this.a();
   }

   public final void a(bluelab.connect.Web.Interface_OnSuccessFailure func) {
      this.c.a(func);
   }

   public final void a(ActionListener var1) {
      this.k.add(var1);
   }

   public final void a() {
      this.c.a();
      this.i.setEnabled(this.b.isLoggedIn());
   }

   private void a(ActionEvent var1) {
      this.b();
      Iterator var3 = this.k.iterator();

      while(var3.hasNext()) {
         ActionListener var2;
         if ((var2 = (ActionListener)var3.next()) != null) {
            var2.actionPerformed(var1);
         }
      }

   }

   private void b() {
      this.b.temperatureUnit = this.d.getSelectedItem().toString();
      this.b.conductivityUnit = this.e.getSelectedItem().toString();
      this.b.ratioUnit = this.f.getSelectedItem().toString();
      this.b.logDataDirectory = this.g.getToolTipText();
      bluelab.connect.f.b var1 = (bluelab.connect.f.b)this.h.getSelectedItem();
      this.b.logDataInterval = (long)var1.getValue();
      this.b.emailAlerts = this.i.isSelected();
      this.b.save();
   }

   private void b(ActionEvent var1) {
      String var3 = this.g.getToolTipText();
      JFileChooser var2;
      (var2 = new JFileChooser()).setDialogTitle("Select data directory");
      var2.setApproveButtonText("Select");
      var2.setFileSelectionMode(1);
      var2.setCurrentDirectory(new File(var3));
      var2.setAcceptAllFileFilterUsed(false);
      String var4;
      if (var2.showOpenDialog(this) == 0 && !(var4 = var2.getSelectedFile().getAbsolutePath()).equals(var3)) {
         this.a.mkdirRecursive(var4);
         this.g.setText(var4);
         this.g.setToolTipText(var4);
         this.b();
      }

   }

   private void c(ActionEvent var1) {
      try {
         Desktop.getDesktop().open(new File(this.g.getToolTipText()));
      } catch (IOException var2) {
         var2.printStackTrace();
      }
   }

   private void d(ActionEvent var1) {
      this.b();
      LocalDateTime var3 = this.a.scheduleWritingToCSV(this.b.logDataInterval);
      DateTimeFormatter var2 = DateTimeFormatter.ofPattern("HH:mm");
      this.j.a(String.format("Scheduled to start log at %s.", var3.format(var2)), true);
   }

   private void e(ActionEvent var1) {
      this.b();
        bluelab.connect.Web.SettingsToken var2;
      (var2 = new bluelab.connect.Web.SettingsToken(this.b)).setCallbacks(this::a, this::a);
      (new Thread(var2)).start();
      this.j.a("Updating settings to service...");
   }

   protected final void a(int var1, Object var2) {
      EventQueue.invokeLater(new E(this));
   }

   protected final void a(int var1, String var2) {
      Component var3 = SwingUtilities.getRoot(this);
      EventQueue.invokeLater(new F(this, var1, var2, var3));
   }

   public final void onSuccess() {
      this.a();
   }

   public final void onFailure() {
      this.a();
   }

   // $FF: synthetic method
   static j a(D var0) {
      return var0.j;
   }
}