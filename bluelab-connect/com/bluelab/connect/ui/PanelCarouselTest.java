package bluelab.connect.ui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class PanelCarouselTest {
   private JFrame a;
   private z b;

   public static void main(String[] var0) {
      try {
         new PanelCarouselTest();
      } catch (Exception var1) {
         var1.printStackTrace();
      }
   }

   public PanelCarouselTest() {
      try {
         UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      } catch (InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException | ClassNotFoundException var4) {
         var4.printStackTrace();
      }

      JPanel var2;
      (var2 = new JPanel()).setBackground(BluelabColour.o);
      JButton var3;
      (var3 = new JButton("Help")).addActionListener(this::a);
      var2.add(var3);
      this.a = new JFrame();
      this.a.setDefaultCloseOperation(3);
      this.a.add(var2);
      this.a.pack();
      this.a.setLocationRelativeTo((Component)null);
      this.a.setVisible(true);
      ArrayList var5;
      (var5 = new ArrayList()).add(new M("Guardian", "/resources/guardian_80px.png", (String)null, new JLabel("Guardian"), 10));
      var5.add(new M("pH Controller", "/resources/phcontroller_80px.png", (String)null, new JLabel("pH Controller"), 10));
      var5.add(new M("Pro Controller", "/resources/procontroller_80px.png", (String)null, new JLabel("Pro Controller"), 10));
      var5.add(new M("Hello world", "/resources/bluelab-success-splash.png", "https://www.bluelab.com", new JLabel("Hello world"), 10));
      this.b = new z(this.a, "Help", var5, 10, 10, false);
   }

   private void a(ActionEvent var1) {
      z var2 = this.b;
      this.b.setVisible(true);
   }
}