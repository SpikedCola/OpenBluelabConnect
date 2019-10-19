package bluelab.connect.ui;

import bluelab.connect.ui.laf.RoundedButtonUI;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public final class BluelabJDialog extends JDialog {
   private JLabel label;

   public BluelabJDialog(JFrame var1, String var2, Icon var3) {
      super(var1, var2);
      this.setBackground(BluelabColour.g);
      JPanel var5;
      (var5 = new JPanel(new BorderLayout())).setOpaque(false);
      var5.setBorder(new EmptyBorder(15, 15, 15, 15));
      this.label = new JLabel("", var3, 0);
      var5.add(this.label, "Center");
      JPanel var6;
      (var6 = new JPanel()).setOpaque(false);
      JButton var4;
      (var4 = new JButton("Close")).setUI(new RoundedButtonUI());
      var4.addActionListener(this::show);
      var6.add(var4);
      var5.add(var6, "South");
      this.setContentPane(var5);
      this.setLocationRelativeTo(var1);
      this.setDefaultCloseOperation(1);
      this.setVisible(false);
   }

   public final void show(ActionEvent var1) {
      this.setVisible(false);
   }

   public final void show(String text) {
      this.label.setText(text);
      this.pack();
      this.setLocationRelativeTo(this.getParent());
      this.setVisible(true);
   }
}