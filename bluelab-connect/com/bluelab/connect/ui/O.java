package bluelab.connect.ui;

import bluelab.connect.Connect;
import bluelab.connect.ui.laf.RoundedButtonUI;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public final class O extends JPanel {
   private JCheckBox a;
   private bluelab.connect.WindowInterface b;

   public O() {
      this.setLayout(new BorderLayout(0, 0));
      this.setOpaque(false);
      JPanel var2;
      (var2 = new JPanel(new GridBagLayout())).setBackground(BluelabColour.a);
      var2.setBorder(new EmptyBorder(20, 20, 20, 20));
      GridBagConstraints var3;
      (var3 = new GridBagConstraints()).gridy = -1;
      var3.ipadx = 40;
      var3.ipady = 30;
      var3.gridwidth = 0;
      JLabel var4 = new JLabel(new ImageIcon(this.getClass().getResource("/resources/welcome.png")));
      var2.add(var4, var3);
      JLabel var5;
      (var5 = new JLabel("Sign up for the new " + Connect.GetTitle() + " Mobile")).setFont(e.c);
      var5.setForeground(BluelabColour.h);
      var5.setHorizontalAlignment(0);
      var2.add(var5, var3);
      var3.gridx = -1;
      var3.ipady = 15;
      var3.gridwidth = 1;
      JLabel var1;
      (var1 = new JLabel("Stay connected")).setFont(e.g);
      var1.setForeground(BluelabColour.h);
      var1.setHorizontalAlignment(0);
      var2.add(var1, var3);
      (var5 = new JLabel("Respond quickly")).setFont(e.g);
      var5.setForeground(BluelabColour.h);
      var5.setHorizontalAlignment(0);
      var2.add(var5, var3);
      (var1 = new JLabel("Improve your growing")).setFont(e.g);
      var1.setForeground(BluelabColour.h);
      var1.setHorizontalAlignment(0);
      var2.add(var1, var3);
      var3.gridy = 3;
      (var4 = new JLabel("<html><div style='text-align: center;'>View your Bluelab devices<br>from anywhere with your<br>iOS or Android device.</div></html>")).setFont(e.i);
      var4.setForeground(BluelabColour.h);
      var4.setHorizontalAlignment(0);
      var2.add(var4, var3);
      (var5 = new JLabel("<html><div style='text-align: center;'>Receive important<br>notifications direct to<br>your mobile device.</div></html>")).setFont(e.i);
      var5.setForeground(BluelabColour.h);
      var5.setHorizontalAlignment(0);
      var2.add(var5, var3);
      (var5 = new JLabel("<html><div style='text-align: center;'>Download all your device<br>data for further analysis<br>and improvement.</div></html>")).setFont(e.i);
      var5.setForeground(BluelabColour.h);
      var5.setHorizontalAlignment(0);
      var2.add(var5, var3);
      this.add(var2, "North");
      (var2 = new JPanel(new GridBagLayout())).setBackground(BluelabColour.o);
      var2.setBorder(new EmptyBorder(10, 20, 0, 20));
      (var3 = new GridBagConstraints()).gridx = 0;
      var3.gridy = -1;
      JPanel var6;
      (var6 = new JPanel()).setOpaque(false);
      var2.add(var6, var3);
      JButton var7;
      (var7 = new JButton("Sign up")).setUI(new RoundedButtonUI(bluelab.connect.ui.Enum_RoundedButtonType.DEFAULT_BIGGER));
      var7.addActionListener(this::a);
      var6.add(var7);
      (var7 = new JButton("Not now")).setUI(new RoundedButtonUI(bluelab.connect.ui.Enum_RoundedButtonType.ALTERNATIVE_BIGGER));
      var7.addActionListener(this::c);
      var6.add(var7);
      (var6 = new JPanel()).setOpaque(false);
      var2.add(var6, var3);
      (var5 = new JLabel("<html>Already a " + Connect.GetTitle() + " user?</html>")).setFont(e.i);
      var5.setForeground(BluelabColour.d);
      var6.add(var5);
      K var8;
      (var8 = new K("<html><u>Log in here</u><html>", (String)null)).setFont(e.i);
      var8.setForeground(BluelabColour.d);
      var8.addActionListener(this::b);
      var6.add(var8);
      this.add(var2, "Center");
      (var2 = new JPanel(new FlowLayout(0))).setBackground(BluelabColour.o);
      var2.setBorder(new EmptyBorder(0, 10, 10, 10));
      this.a = new JCheckBox("Do not show this message again");
      this.a.setOpaque(false);
      this.a.setFont(e.i);
      this.a.setForeground(BluelabColour.d);
      this.a.addActionListener(this::d);
      var2.add(this.a);
      this.add(var2, "South");
   }

   public final void a(bluelab.connect.WindowInterface var1) {
      this.b = var1;
   }

   private void a(ActionEvent var1) {
      if (this.b != null) {
         this.b.a();
      }

   }

   private void b(ActionEvent var1) {
      if (this.b != null) {
         this.b.b();
      }

   }

   private void c(ActionEvent var1) {
      if (this.b != null) {
         this.b.c();
      }

   }

   private void d(ActionEvent var1) {
      if (this.b != null) {
         this.b.a(!this.a.isSelected());
      }

   }
}