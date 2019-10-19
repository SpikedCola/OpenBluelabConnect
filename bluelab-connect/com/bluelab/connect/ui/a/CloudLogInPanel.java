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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public final class CloudLogInPanel extends JPanel {
   private bluelab.connect.ui.BluelabTextField email;
   private bluelab.connect.ui.f password;
   private JLabel label;
   private bluelab.connect.Web.Interface_OnSuccess success;
   private SettingFileModel settingFileModel;

   public CloudLogInPanel(SettingFileModel settingFileModel, bluelab.connect.Web.Interface_OnSuccess onSuccess, ActionListener showSignup, ActionListener showPasswordReset) {
      this.settingFileModel = settingFileModel;
      this.success = onSuccess;
      this.setLayout(new GridBagLayout());
      this.setOpaque(false);
      GridBagConstraints var6;
      (var6 = new GridBagConstraints()).insets = new Insets(5, 0, 0, 0);
      var6.fill = 2;
      var6.weightx = 1.0D;
      JLabel var7;
      (var7 = new JLabel(Connect.GetTitle() + " Cloud")).setFont(bluelab.connect.ui.e.g);
      var6.gridx = 0;
      var6.gridy = 0;
      this.add(var7, var6);
      var6.gridx = 0;
      var6.gridy = 1;
      var6.gridwidth = 2;
      this.add(new JSeparator(0), var6);
      var6.gridx = 0;
      var6.gridy = 2;
      var6.gridwidth = 1;
      this.add(new JLabel("Email address"), var6);
      this.email = new bluelab.connect.ui.BluelabTextField("Enter your email address");
      this.email.setColumns(10);
      var6.gridx = 1;
      var6.gridy = 2;
      this.add(this.email, var6);
      var6.gridx = 0;
      var6.gridy = 3;
      this.add(new JLabel("Password"), var6);
      this.password = new bluelab.connect.ui.f("Enter your password");
      this.password.setColumns(10);
      this.password.addActionListener(this::logIn);
      var6.gridx = 1;
      var6.gridy = 3;
      this.add(this.password, var6);
      JPanel panel;
      (panel = new JPanel()).setOpaque(false);
      var6.anchor = 13;
      var6.gridx = 0;
      var6.gridy = 4;
      var6.gridwidth = 2;
      this.add(panel, var6);
      JButton resetPasswordBtn;
      (resetPasswordBtn = new JButton("Reset password")).setUI(new RoundedButtonUI(bluelab.connect.ui.Enum_RoundedButtonType.ALTERNATIVE));
      resetPasswordBtn.addActionListener(showPasswordReset);
      panel.add(resetPasswordBtn);
      JButton signUpBtn;
      (signUpBtn = new JButton("Sign up")).setUI(new RoundedButtonUI(bluelab.connect.ui.Enum_RoundedButtonType.ALTERNATIVE));
      signUpBtn.addActionListener(showSignup);
      panel.add(signUpBtn);
      JButton logInBtn;
      (logInBtn = new JButton("Log in")).setUI(new RoundedButtonUI());
      logInBtn.addActionListener(this::logIn);
      panel.add(logInBtn);
      this.label = new JLabel("Please sign up and follow the verification email instructions before attempting to log in.");
      var6.gridx = 0;
      var6.gridy = 5;
      this.add(this.label, var6);
   }

   private void logIn(ActionEvent event) {
      String email = this.email.getText();
      String password = new String(this.password.getPassword());
      String errors = "";
      if (!bluelab.connect.Web.ValidateInternetAddress.IsValid(email)) {
         errors = "Please provide a valid email address.";
      } else if (password.isEmpty()) {
         errors = "Please enter your password.";
      }

      if (errors.isEmpty()) {
         bluelab.connect.Web.UsernamePasswordTokenRequest var7;
         (var7 = new bluelab.connect.Web.UsernamePasswordTokenRequest(email, password, this.settingFileModel)).setCallbacks(this::a, this::a);
         (new Thread(var7)).start();
         this.label.setText("Logging you in...");
         this.label.setForeground(bluelab.connect.ui.BluelabColour.l);
      } else {
         this.label.setText("");
         JOptionPane.showMessageDialog(SwingUtilities.getRoot(this), "<html>" + errors + "</html>", "Log in error", 0, UIManager.getIcon("OptionPane.errorIcon"));
      }
   }

   protected final void a(int var1, Object var2) {
      EventQueue.invokeLater(new g(this, var1, var2));
   }

   protected final void a(int var1, String var2) {
      Component var3 = SwingUtilities.getRoot(this);
      EventQueue.invokeLater(new h(this, var1, var2, var3));
   }

   // $FF: synthetic method
   static bluelab.connect.ui.BluelabTextField a(CloudLogInPanel panel) {
      return panel.email;
   }

   // $FF: synthetic method
   static bluelab.connect.ui.f b(CloudLogInPanel panel) {
      return panel.password;
   }

   // $FF: synthetic method
   static JLabel c(CloudLogInPanel panel) {
      return panel.label;
   }

   // $FF: synthetic method
   static bluelab.connect.Web.Interface_OnSuccess OnSuccess(CloudLogInPanel panel) {
      return panel.success;
   }

   // $FF: synthetic method
   static SettingFileModel e(CloudLogInPanel panel) {
      return panel.settingFileModel;
   }
}