package bluelab.connect.ui.a;

import bluelab.connect.model.SettingFileModel;
import bluelab.connect.ui.A;
import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public final class AccountPanel extends JPanel {
   private Panel_LogInOut logInOutPanel;
   private List<bluelab.connect.Web.Interface_OnSuccessFailure> callbackList;
   private SettingFileModel settingFileModel;

   public AccountPanel(SettingFileModel settingFileModel) {
      this.settingFileModel = settingFileModel;
      this.callbackList = new ArrayList();
      this.setLayout(new A());
      this.setBorder(new EmptyBorder(5, 15, 0, 15));
      this.setOpaque(false);
      bluelab.connect.Web.Interface_OnSuccess var10002 = this::showLogOutPanel;
      ActionListener var10003 = this::showLogIn;
      ActionListener var10004 = this::showPasswordReset;
      o var2 = new o(var10002, var10003);
      this.add(var2, "signUp");
      CloudLogInPanel var3 = new CloudLogInPanel(settingFileModel, this::success, this::showSignUp, this::showPasswordReset);
      this.add(var3, "logIn");
      var10002 = this::showLogOutPanel;
      var10003 = this::showSignUp;
      l var4 = new l(var10002, this::showLogIn);
      this.add(var4, "passwordReset");
      this.logInOutPanel = new Panel_LogInOut(settingFileModel, this::failure, this::showLogOutPanel);
      this.add(this.logInOutPanel, "logOut");
   }

   public final void a(bluelab.connect.Web.Interface_OnSuccessFailure func) {
      this.callbackList.add(func);
   }

   public final void showlogOut() {
      if (this.settingFileModel.isLoggedIn()) {
         this.logInOutPanel.a(this.settingFileModel);
         ((CardLayout)this.getLayout()).show(this, "logOut");
         bluelab.connect.GoogleAnalytics.GA_tracker_Sender.GetInstance().reportScreenView("DK_LogOut");
      } else {
         this.showLogIn((ActionEvent)null);
      }
   }

   public final void showSignUp(ActionEvent e) {
      ((CardLayout)this.getLayout()).show(this, "signUp");
      bluelab.connect.GoogleAnalytics.GA_tracker_Sender.GetInstance().reportScreenView("DK_SignUp");
   }

   public final void showLogIn(ActionEvent e) {
      ((CardLayout)this.getLayout()).show(this, "logIn");
      bluelab.connect.GoogleAnalytics.GA_tracker_Sender.GetInstance().reportScreenView("DK_LogIn");
   }

   private void showPasswordReset(ActionEvent e) {
      ((CardLayout)this.getLayout()).show(this, "passwordReset");
      bluelab.connect.GoogleAnalytics.GA_tracker_Sender.GetInstance().reportScreenView("DK_PasswordReset");
   }

   private void failure(int var1, Object var2) {
      this.showLogOutPanel(var1, var2);
      this.callbackList.forEach((callback) -> {
         callback.onFailure();
      });
   }

   private void showLogOutPanel(int var1, String var2) {
      this.showLogOutPanel(var1, var2);
   }

   private void success(int var1, Object var2) {
      this.showLogOutPanel(var1, var2);
      this.callbackList.forEach((callback) -> {
         callback.onSuccess();
      });
   }

   private void showLogOutPanel(int var1, Object var2) {
      EventQueue.invokeLater(new ShowLogOutPanel(this));
   }
}