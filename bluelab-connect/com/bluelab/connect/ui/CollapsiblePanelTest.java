package bluelab.connect.ui;

import java.awt.Component;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class CollapsiblePanelTest {
   public static void main(String[] var0) {
      try {
         new CollapsiblePanelTest();
      } catch (Exception var1) {
         var1.printStackTrace();
      }
   }

   public CollapsiblePanelTest() {
      try {
         UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      } catch (InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException | ClassNotFoundException var3) {
         var3.printStackTrace();
      }

      JPanel var1;
      (var1 = new JPanel()).setLayout(new BoxLayout(var1, 3));
      var1.add(new DownUpPanel("Legend 1", false, new bluelab.connect.ui.FocusTextField.e()));
      var1.add(new DownUpPanel("Legend 2", true, new bluelab.connect.ui.FocusTextField.e()));
      JFrame var2;
      (var2 = new JFrame()).setDefaultCloseOperation(3);
      var2.add(var1);
      var2.pack();
      var2.setLocationRelativeTo((Component)null);
      var2.setVisible(true);
   }
}