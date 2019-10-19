package bluelab.connect.ui;

import bluelab.connect.Connect;
import bluelab.connect.model.SettingFileModel;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import javax.swing.JDialog;
import javax.swing.JPanel;

public final class BluelabDialog extends JDialog implements bluelab.connect.Web.Interface_OnSuccessFailure, bluelab.connect.WindowInterface {
   private SettingFileModel a;
   private JPanel b;
   private bluelab.connect.ui.a.a c;
   private Dimension d = new Dimension(520, 270);

   public BluelabDialog(Dialog var1, SettingFileModel var2) {
      super((Dialog)null, true);
      this.a = var2;
      this.setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/resources/connect_48px.png")));
      this.setTitle(Connect.GetTitle() + " " + Connect.GetVersion());
      this.setResizable(false);
      this.setUndecorated(false);
      JPanel var4;
      (var4 = new JPanel(new BorderLayout(0, 0))).setBackground(bluelab.connect.ui.BluelabColour.o);
      this.b = new JPanel(new A());
      this.b.setOpaque(false);
      var4.add(this.b, "North");
      O var3;
      (var3 = new O()).a((bluelab.connect.c)this);
      this.b.add(var3, "welcome");
      this.c = new bluelab.connect.ui.a.a(var2);
      this.c.a((bluelab.connect.Web.Interface_OnSuccessFailure)this);
      this.b.add(this.c, "cloud");
      this.setContentPane(var4);
      this.pack();
      this.setLocationRelativeTo(this.getParent());
      this.setDefaultCloseOperation(2);
   }

   private void f() {
      ((CardLayout)this.b.getLayout()).show(this.b, "cloud");
      this.getContentPane().setPreferredSize(this.d);
      this.pack();
   }

   public final void a() {
      this.c.a((ActionEvent)null);
      this.f();
   }

   public final void b() {
      this.c.b((ActionEvent)null);
      this.f();
   }

   public final void c() {
      this.dispose();
   }

   public final void a(boolean var1) {
      this.a.showWelcome = var1;
      this.a.save();
   }

   public final void onSuccess() {
      this.dispose();
   }

   public final void onFailure() {
      this.dispose();
   }
}