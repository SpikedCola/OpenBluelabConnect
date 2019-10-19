package bluelab.connect.ui.a;

import bluelab.connect.Connect;
import bluelab.connect.model.SettingFileModel;
import bluelab.connect.ui.laf.RoundedButtonUI;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingUtilities;

public final class Panel_LogInOut extends JPanel {
   private JLabel a;
   private JLabel b;
   private bluelab.connect.Web.Interface_OnSuccess c;
   private bluelab.connect.Web.Interface_OnFailure d;
   private SettingFileModel e;

   public Panel_LogInOut(SettingFileModel var1, bluelab.connect.Web.Interface_OnSuccess var2, bluelab.connect.Web.Interface_OnFailure var3) {
      this.e = var1;
      this.c = var2;
      this.d = var3;
      this.setLayout(new GridBagLayout());
      this.setOpaque(false);
      GridBagConstraints var4;
      (var4 = new GridBagConstraints()).insets = new Insets(5, 0, 0, 0);
      var4.fill = 2;
      var4.weightx = 1.0D;
      var4.gridx = 0;
      var4.gridy = -1;
      JLabel var5;
      (var5 = new JLabel(Connect.GetTitle() + " Cloud")).setFont(bluelab.connect.ui.e.g);
      this.add(var5, var4);
      var4.gridwidth = 2;
      this.add(new JSeparator(0), var4);
      this.a = new JLabel("Not logged in");
      var4.gridwidth = 1;
      this.add(this.a, var4);
      JPanel var6;
      (var6 = new JPanel()).setOpaque(false);
      var4.anchor = 13;
      var4.gridwidth = 2;
      this.add(var6, var4);
      JButton var7;
      (var7 = new JButton("Log out")).setUI(new RoundedButtonUI());
      var7.addActionListener(this::a);
      var6.add(var7);
      this.b = new JLabel("");
      var4.gridx = 0;
      var4.gridwidth = 1;
      this.add(this.b, var4);
   }

   private void a(ActionEvent var1) {
      bluelab.connect.Web.LogoutToken var2;
      (var2 = new bluelab.connect.Web.LogoutToken(this.e)).setCallbacks(this::a, this::a);
      (new Thread(var2)).start();
      this.b.setText("Logging out...");
      this.b.setForeground(bluelab.connect.ui.BluelabColour.l);
   }

   private void a(int var1, Object var2) {
      EventQueue.invokeLater(new j(this, var1, var2));
   }

   protected final void a(int var1, String var2) {
      Component var3 = SwingUtilities.getRoot(this);
      EventQueue.invokeLater(new k(this, var1, var2, var3));
   }

   public final void a(SettingFileModel var1) {
      this.a.setText(String.format("<html>You are logged in as <i>%s</i>, customer number <i>%d</i>.</html>", var1.email, var1.customerNr));
   }

   // $FF: synthetic method
   static JLabel a(Panel_LogInOut var0) {
      return var0.b;
   }

   // $FF: synthetic method
   static bluelab.connect.Web.Interface_OnSuccess b(Panel_LogInOut var0) {
      return var0.c;
   }

   // $FF: synthetic method
   static bluelab.connect.Web.Interface_OnFailure c(Panel_LogInOut var0) {
      return var0.d;
   }
}