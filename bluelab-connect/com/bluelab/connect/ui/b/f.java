package bluelab.connect.ui.b;

import bluelab.connect.c.Peripod;
import bluelab.connect.c.BluelabRemoteXbeeDevice;
import bluelab.connect.ui.laf.RoundedButtonUI;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

public final class f extends JPanel {
   private GridBagConstraints c;
   private JPanel d;
   private JFileChooser e;
   private JTextField f;
   protected JProgressBar a;
   private JTextArea g;
   protected JButton b;
   private JCheckBox h;
   private JSpinner i;
   private File j;
   private String k;
   private InputStream l;
   private bluelab.connect.d.BluelabDevice dev;
   private BluelabRemoteXbeeDevice remoteDev;

   public f(bluelab.connect.d.BluelabDevice var1, BluelabRemoteXbeeDevice var2) {
      this.dev = var1;
      this.remoteDev = var2;
      this.setOpaque(false);
      this.setBorder(new EmptyBorder(10, 10, 10, 10));
      this.setLayout(new BorderLayout(0, 0));
      boolean var3 = var1.getSettingFileModel().isExperimentalEnabled();
      this.c = new GridBagConstraints();
      this.c.anchor = 11;
      this.c.fill = 0;
      this.c.insets = new Insets(5, 5, 5, 5);
      this.d = new JPanel();
      this.d.setOpaque(false);
      this.d.setLayout(new GridBagLayout());
      this.add(this.d, "North");
      JLabel var6;
      (var6 = new JLabel("Firmware file:")).setFont(new Font("Arial", 0, 12));
      var6.setHorizontalAlignment(2);
      var6.setVisible(var3);
      this.c.gridx = 0;
      this.c.gridy = 0;
      this.c.weightx = 0.0D;
      this.d.add(var6, this.c);
      this.e = new JFileChooser();
      this.e.setVisible(var3);
      this.f = new JTextField("");
      this.f.setEditable(false);
      this.f.setVisible(var3);
      this.c.gridx = 1;
      this.c.fill = 2;
      this.c.weightx = 1.0D;
      this.c.gridwidth = 2;
      this.d.add(this.f, this.c);
      JButton var7;
      (var7 = new JButton("Open...")).setUI(new RoundedButtonUI(bluelab.connect.ui.Enum_RoundedButtonType.ALTERNATIVE));
      var7.addActionListener(this::a);
      var7.setVisible(var3);
      this.c.gridx = 3;
      this.c.weightx = 0.0D;
      this.c.gridwidth = 1;
      this.d.add(var7, this.c);
      if (var3) {
         this.b = new JButton("Update");
         this.b.addActionListener(this::c);
      } else {
         this.b = new JButton("Check for update");
         this.b.addActionListener(this::b);
      }

      this.b.setUI(new RoundedButtonUI());
      this.c.gridx = 3;
      this.c.gridy = 1;
      this.c.weightx = 0.0D;
      this.d.add(this.b, this.c);
      this.a = new JProgressBar(0, 100);
      this.a.setStringPainted(true);
      this.c.gridx = 0;
      this.c.gridy = 1;
      this.c.gridwidth = 3;
      this.c.weightx = 1.0D;
      this.d.add(this.a, this.c);
      this.h = new JCheckBox("Pod update");
      this.h.setOpaque(false);
      this.h.setVisible(var3);
      this.h.setFont(new Font("Arial", 0, 12));
      this.h.addActionListener(this::d);
      this.c.gridx = -1;
      this.c.gridy = 2;
      this.c.gridwidth = 1;
      this.c.weightx = 0.0D;
      this.d.add(this.h, this.c);
      SpinnerNumberModel var8 = new SpinnerNumberModel(1, 1, 15, 1);
      this.i = new JSpinner(var8);
      this.i.setVisible(var3);
      this.i.setEditor(new DefaultEditor(this.i));
      this.i.setToolTipText("Pod chain index");
      this.i.setEnabled(false);
      this.d.add(this.i, this.c);
      JLabel var4;
      (var4 = new JLabel("<html>Note: While a firmware update is in progress, it is recommended not to change settings or update firmware for other devices.</html>")).setFont(new Font("Arial", 0, 12));
      var4.setHorizontalAlignment(2);
      this.c.gridx = 0;
      this.c.gridy = 3;
      this.c.gridwidth = 4;
      this.d.add(var4, this.c);
      this.g = new JTextArea();
      this.g.setFont(new Font("Arial", 0, 12));
      this.g.setLineWrap(true);
      this.g.setWrapStyleWord(true);
      this.g.setEditable(false);
      JScrollPane var5;
      (var5 = new JScrollPane()).setOpaque(false);
      var5.getViewport().setOpaque(false);
      var5.setViewportView(this.g);
      var5.setBorder(new EmptyBorder(0, 0, 0, 0));
      var5.setBorder(BorderFactory.createLineBorder(bluelab.connect.ui.BluelabColour.n));
      this.add(var5, "Center");
   }

   protected final void a(ActionEvent var1) {
      this.e.setFileSelectionMode(0);
      this.e.resetChoosableFileFilters();
      FileNameExtensionFilter var2 = new FileNameExtensionFilter(String.format("Firmware Files (*.%s; *.%s)", "blb", "hex"), new String[]{"blb", "hex"});
      this.e.addChoosableFileFilter(var2);
      this.e.setAcceptAllFileFilterUsed(false);
      if (this.e.showOpenDialog(this) == 0) {
         this.j = this.e.getSelectedFile();
         this.k = this.j.getAbsolutePath();
         this.f.setText(this.k);
      }

   }

   protected final void b(ActionEvent var1) {
      bluelab.connect.l.DeviceVersion var2;
      if ((var2 = bluelab.connect.l.FirmwareUpdater.GetPossibleUpdate(this.remoteDev)) != null) {
         String var3 = String.format("Update %s is available for this device.\nContinue with update?", var2.versionString);
         if (JOptionPane.showConfirmDialog(SwingUtilities.getRoot(this), var3, "Firmware update is available", 2, 3) == 0) {
            this.c(var1);
            return;
         }
      } else {
         this.b("No update is available for this device.");
      }

   }

   protected final void c(ActionEvent var1) {
      this.c();
      this.l = this.b();
      if (this.l != null && this.k != null) {
         this.b.setEnabled(false);
         this.g.setText("");
         boolean var3 = this.k.endsWith("blb");
         bluelab.connect.c.d.c var4;
         (var4 = new bluelab.connect.c.d.c(this.dev.getXbeeDevice(), this.remoteDev, this.l, var3)).a(this::a, this::a, this::a);
         SpinnerNumberModel var2 = (SpinnerNumberModel)this.i.getModel();
         var4.a(this.h.isSelected(), var2.getNumber().intValue());
         this.a.setValue(0);
         this.a.setIndeterminate(true);
         (new Thread(var4)).start();
      } else {
         this.b("No update is available for this device.");
      }
   }

   private InputStream b() {
      Object var1 = null;
      if (this.j != null && this.j.exists()) {
         this.k = this.j.getAbsolutePath();

         try {
            var1 = new FileInputStream(this.j);
         } catch (FileNotFoundException var2) {
            this.b("Selected firmware update file could not be found.");
         }
      } else {
         this.k = bluelab.connect.l.FirmwareUpdater.getPossibleUpdateFirmwareFile(this.remoteDev);
         if (this.k != null) {
            var1 = this.getClass().getResourceAsStream(this.k);
            this.f.setText(this.k);
         }
      }

      return (InputStream)var1;
   }

   private void c() {
      try {
         if (this.l != null) {
            this.l.close();
            return;
         }
      } catch (IOException var1) {
         this.b("Firmware update resource could not be closed.");
      }

   }

   private void b(String var1) {
      JOptionPane.showMessageDialog(SwingUtilities.getRoot(this), var1, "Device firmware update warning", 2, UIManager.getIcon("OptionPane.warningIcon"));
   }

   protected final void d(ActionEvent var1) {
      this.i.setEnabled(this.h.isSelected());
   }

   protected final void a(float var1, String var2) {
      EventQueue.invokeLater(new g(this, var1, var2));
   }

   protected final void a() {
      this.c();
      Component var1 = SwingUtilities.getRoot(this);
      EventQueue.invokeLater(new h(this, var1));
   }

   protected final void a(String var1) {
      this.c();
      EventQueue.invokeLater(new i(this, var1));
   }

   // $FF: synthetic method
   static void a(f var0, String var1) {
      if (!var1.isEmpty()) {
         LocalDateTime var2 = LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault());
         var0.g.append(String.format("%s  %s\n", var2.toString(), var1));
      }

   }

   // $FF: synthetic method
   static String a(f var0) {
      if (var0.h.isSelected()) {
         int var1 = ((SpinnerNumberModel)var0.i.getModel()).getNumber().intValue();
         return ((Peripod)var0.remoteDev.getPeripodWithPumpsList().get(var1)).getFirmwareVersion();
      } else {
         return var0.remoteDev.getIdent2();
      }
   }
}