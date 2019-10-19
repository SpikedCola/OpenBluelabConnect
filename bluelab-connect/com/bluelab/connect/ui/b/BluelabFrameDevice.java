package bluelab.connect.ui.b;

import bluelab.connect.Connect;
import bluelab.connect.c.BluelabRemoteXbeeDevice;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.border.EmptyBorder;

public final class BluelabFrameDevice extends JFrame {
   private OverviewPanel a;

   public BluelabFrameDevice(bluelab.connect.d.BluelabDevice blDev, BluelabRemoteXbeeDevice remoteDev) {
      this.setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/resources/connect_48px.png")));
      this.setTitle(remoteDev.getName() + " - " + Connect.GetTitle());
      this.setDefaultCloseOperation(1);
      this.setBounds(100, 100, 600, 500);
      this.setMinimumSize(new Dimension(500, 300));
      this.a = new OverviewPanel(blDev, remoteDev, bluelab.connect.ui.BluelabColour.a, bluelab.connect.ui.BluelabColour.b, false);
      this.a.setBorder(new EmptyBorder(5, 5, 5, 5));
      this.setContentPane(this.a);
   }

   public final void a(boolean var1) {
      this.a.a(var1);
   }
}