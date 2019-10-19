package bluelab.connect.ui;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class CustomToggleButtonTest {
   private JFrame a;

   public static void main(String[] var0) {
      new CustomToggleButtonTest();
   }

   public CustomToggleButtonTest() {
      try {
         UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      } catch (InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException | ClassNotFoundException var4) {
         var4.printStackTrace();
      }

      JPanel var2;
      (var2 = new JPanel()).setBackground(BluelabColour.g);
      I var3;
      (var3 = new I("Control", "Monitor", false)).setToolTipText("Tip text");
      var2.add(var3);
      (var3 = new I("Loooooooooooong", "Short", false)).setBackground(Color.BLUE);
      var3.setToolTipText("Tip text");
      var2.add(var3);
      this.a = new JFrame();
      this.a.setDefaultCloseOperation(3);
      this.a.add(var2);
      this.a.pack();
      this.a.setLocationRelativeTo((Component)null);
      this.a.setVisible(true);
   }
}