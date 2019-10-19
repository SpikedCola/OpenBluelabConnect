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

public final class l extends JPanel {
   private bluelab.connect.ui.BluelabTextField a;
   private JLabel b;
   private bluelab.connect.Web.Interface_OnSuccess c;

   public l(bluelab.connect.Web.Interface_OnSuccess var1, ActionListener var2) {
      this.c = var1;
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
      this.a.addActionListener(this::a);
      var5.gridx = 1;
      var5.gridy = 2;
      this.add(this.a, var5);
      JPanel var7;
      (var7 = new JPanel()).setOpaque(false);
      var5.anchor = 13;
      var5.gridx = 0;
      var5.gridy = 3;
      var5.gridwidth = 2;
      this.add(var7, var5);
      JButton var4;
      (var4 = new JButton("Cancel")).setUI(new RoundedButtonUI(bluelab.connect.ui.Enum_RoundedButtonType.ALTERNATIVE));
      var4.addActionListener(var2);
      var7.add(var4);
      JButton var6;
      (var6 = new JButton("Reset password")).setUI(new RoundedButtonUI());
      var6.addActionListener(this::a);
      var7.add(var6);
      this.b = new JLabel("Please sign up and follow the verification email instructions before attempting to reset.");
      var5.gridx = 0;
      var5.gridy = 4;
      this.add(this.b, var5);
   }

   private void a(ActionEvent var1) {
      String var4;
      String var2 = var4 = this.a.getText();
      String var3 = "";
      if (!bluelab.connect.Web.ValidateInternetAddress.IsValid(var2)) {
         var3 = "Please provide a valid email address.";
      }

      if (var3.isEmpty()) {
         bluelab.connect.Web.ResetPasswordToken var5;
         (var5 = new bluelab.connect.Web.ResetPasswordToken(var4)).setCallbacks(this::a, this::a);
         (new Thread(var5)).start();
         this.b.setText("Resetting your password...");
         this.b.setForeground(bluelab.connect.ui.BluelabColour.l);
      } else {
         this.b.setText("");
         JOptionPane.showMessageDialog(SwingUtilities.getRoot(this), "<html>" + var3 + "</html>", "Password reset error", 0, UIManager.getIcon("OptionPane.errorIcon"));
      }
   }

   protected final void a(int var1, Object var2) {
      Component var3 = SwingUtilities.getRoot(this);
      EventQueue.invokeLater(new m(this, var3, var1, var2));
   }

   protected final void a(int var1, String var2) {
      Component var3 = SwingUtilities.getRoot(this);
      EventQueue.invokeLater(new n(this, var1, var2, var3));
   }

   // $FF: synthetic method
   static bluelab.connect.ui.BluelabTextField a(l var0) {
      return var0.a;
   }

   // $FF: synthetic method
   static JLabel b(l var0) {
      return var0.b;
   }

   // $FF: synthetic method
   static bluelab.connect.Web.Interface_OnSuccess c(l var0) {
      return var0.c;
   }
}