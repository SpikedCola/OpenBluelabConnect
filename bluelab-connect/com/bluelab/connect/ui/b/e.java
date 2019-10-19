package bluelab.connect.ui.b;

import java.awt.FlowLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public final class e extends JPanel {
   public e() {
      this.setLayout(new FlowLayout(0, 20, 0));
      this.setBorder(new EmptyBorder(5, 0, 0, 0));
      this.setOpaque(false);
      this.add(this.a("Controlling", "/resources/control-tag.png"));
      this.add(this.a("Locked out", "/resources/lock-tag.png"));
      this.add(this.a("In alarm", "/resources/alarm-tag.png"));
      this.add(this.a("pH cal. due", "/resources/calibration-due-tag.png"));
      this.add(this.a("Offline", "/resources/offline-tag.png"));
   }

   private JLabel a(String var1, String var2) {
      JLabel var3;
      (var3 = new JLabel()).setText(var1);
      var3.setIcon(new ImageIcon(this.getClass().getResource(var2)));
      var3.setFont(bluelab.connect.ui.e.i);
      var3.setHorizontalTextPosition(0);
      var3.setVerticalTextPosition(3);
      return var3;
   }
}