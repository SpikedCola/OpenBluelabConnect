package bluelab.connect.ui.a;

import bluelab.connect.Connect;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public final class o extends JPanel {
   private bluelab.connect.ui.BluelabTextField a;
   private bluelab.connect.ui.f b;
   private bluelab.connect.ui.f c;
   private JLabel d;
   private bluelab.connect.Web.Interface_OnSuccess e;

   public o(bluelab.connect.Web.Interface_OnSuccess var1, ActionListener var2) {
      this.e = var1;
      this.setLayout(new GridBagLayout());
      this.setOpaque(false);
      GridBagConstraints var5;
      (var5 = new GridBagConstraints()).insets = new Insets(5, 0, 0, 0);
      var5.fill = 2;
      var5.weightx = 1.0D;
      JLabel var3;
      (var3 = new JLabel(Connect.GetTitle() + " Cloud")).setFont(bluelab.connect.ui.e.g);
      var5.gridx = 0;
      var5.gridy = 0;
      this.add(var3, var5);
      var5.gridx = 0;
      var5.gridy = 1;
      var5.gridwidth = 2;
      this.add(new JSeparator(0), var5);
      var5.gridx = 0;
      var5.gridy = 2;
      var5.gridwidth = 1;
      this.add(new JLabel("Email address"), var5);
      this.a = new bluelab.connect.ui.BluelabTextField("Enter your email address");
      this.a.setColumns(10);
      var5.gridx = 1;
      var5.gridy = 2;
      this.add(this.a, var5);
      var5.gridx = 0;
      var5.gridy = 3;
      this.add(new JLabel("Password"), var5);
      this.b = new bluelab.connect.ui.f("Enter your password");
      this.b.setColumns(10);
      var5.gridx = 1;
      var5.gridy = 3;
      this.add(this.b, var5);
      var5.gridx = 0;
      var5.gridy = 4;
      this.add(new JLabel("Password confirmation"), var5);
      this.c = new bluelab.connect.ui.f("Confirm your password");
      this.c.setColumns(10);
      this.c.addActionListener(this::a);
      var5.gridx = 1;
      var5.gridy = 4;
      this.add(this.c, var5);
      (var3 = new JLabel(String.format("Password needs to be at least %d characters.", 8))).setFont(bluelab.connect.ui.e.i);
      var5.gridx = 1;
      var5.gridy = 5;
      this.add(var3, var5);
      JPanel var9;
      (var9 = new JPanel()).setOpaque(false);
      var5.anchor = 13;
      var5.gridx = 0;
      var5.gridy = 6;
      var5.gridwidth = 2;
      this.add(var9, var5);
      JButton var4;
      (var4 = new JButton("Already signed up? Log in")).setUI(new RoundedButtonUI(bluelab.connect.ui.Enum_RoundedButtonType.ALTERNATIVE));
      var4.addActionListener(var2);
      var9.add(var4);
      JButton var7;
      (var7 = new JButton("Sign up")).setUI(new RoundedButtonUI());
      var7.addActionListener(this::a);
      var9.add(var7);
      this.d = new JLabel("");
      var5.gridx = 0;
      var5.gridy = 7;
      this.add(this.d, var5);
      JPanel var8;
      (var8 = new JPanel()).setOpaque(false);
      var5.gridx = 0;
      var5.gridy = 8;
      var5.gridwidth = 2;
      this.add(var8, var5);
      bluelab.connect.ui.BluelabButton var6 = new bluelab.connect.ui.BluelabButton("/resources/apple-app-store-badge.png", "https://itunes.apple.com/app/id1103915535");
      var8.add(var6);
      var6 = new bluelab.connect.ui.BluelabButton("/resources/google-play-store-badge.png", "https://play.google.com/store/apps/details?id=bluelab.connect");
      var8.add(var6);
   }

   private void a(ActionEvent var1) {
      String var7 = this.a.getText();
      String var2 = new String(this.b.getPassword());
      String var3 = new String(this.c.getPassword());
      String var6 = "";
      if (!bluelab.connect.Web.ValidateInternetAddress.IsValid(var7)) {
         var6 = "Please provide a valid email address.";
      } else if (var2 == null || var2.length() < 8) {
         var6 = String.format("Please provide a valid password that contains at least %d characters.", 8);
      } else if (!var2.equals(var3)) {
         var6 = "Please ensure that your password and confirmation match.";
      }

      if (var6.isEmpty()) {
         bluelab.connect.Web.RegisterToken var8;
         (var8 = new bluelab.connect.Web.RegisterToken(var7, var2)).setCallbacks(this::a, this::a);
         (new Thread(var8)).start();
         this.d.setText("Signing you up...");
         this.d.setForeground(bluelab.connect.ui.BluelabColour.l);
      } else {
         this.d.setText("");
         JOptionPane.showMessageDialog(SwingUtilities.getRoot(this), "<html>" + var6 + "</html>", "Sign up error", 0, UIManager.getIcon("OptionPane.errorIcon"));
      }
   }

   protected final void a(int var1, Object var2) {
      Component var3 = SwingUtilities.getRoot(this);
      EventQueue.invokeLater(new p(this, var3, var1, var2));
   }

   protected final void a(int var1, String var2) {
      Component var3 = SwingUtilities.getRoot(this);
      EventQueue.invokeLater(new q(this, var1, var2, var3));
   }

   // $FF: synthetic method
   static bluelab.connect.ui.BluelabTextField a(o var0) {
      return var0.a;
   }

   // $FF: synthetic method
   static bluelab.connect.ui.f b(o var0) {
      return var0.b;
   }

   // $FF: synthetic method
   static bluelab.connect.ui.f c(o var0) {
      return var0.c;
   }

   // $FF: synthetic method
   static JLabel d(o var0) {
      return var0.d;
   }

   // $FF: synthetic method
   static bluelab.connect.Web.Interface_OnSuccess e(o var0) {
      return var0.e;
   }
}