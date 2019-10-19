package bluelab.connect.ui.b;

import bluelab.connect.c.BluelabRemoteXbeeDevice;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public final class j extends JPanel {
   private JLabel a;
   private Border b;
   private Border c;
   private Color d;

   public j(BluelabRemoteXbeeDevice dev) {
      super(new BorderLayout(0, 0));
      this.setPreferredSize(new Dimension(430, 90));
      JPanel var2;
      (var2 = new JPanel(new BorderLayout(0, 0))).setOpaque(false);
      this.add(var2, "West");
      JLabel var3;
      (var3 = new JLabel("")).setBorder(new EmptyBorder(10, 10, 10, 10));
      var3.setIcon(new ImageIcon(d.class.getResource("/resources/unknown_80px.png")));
      var2.add(var3, "Center");
      (var2 = new JPanel(new BorderLayout(0, 0))).setOpaque(false);
      var2.setBorder(new EmptyBorder(10, 20, 10, 10));
      this.add(var2, "Center");
      this.a = new JLabel("");
      this.a.setHorizontalAlignment(2);
      var2.add(this.a, "Center");
      this.c = BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, bluelab.connect.ui.BluelabColour.n), BorderFactory.createEmptyBorder(0, 2, 0, 0));
      this.b = BorderFactory.createMatteBorder(0, 2, 1, 1, bluelab.connect.ui.BluelabColour.n);
      this.a(dev, false);
   }

   public final void a(BluelabRemoteXbeeDevice dev, boolean var2) {
      if (var2) {
         this.setBorder(this.b);
         this.d = bluelab.connect.ui.BluelabColour.g;
      } else {
         this.setBorder(this.c);
         this.d = bluelab.connect.ui.BluelabColour.h;
      }

      if (!dev.isDeviceNotNull()) {
         this.a.setText("Waiting for device [" + dev.getKeyCode() + "]...");
      } else if (dev.getDeviceVersion().equals(bluelab.connect.c.Enum_DeviceVersion.UNKNOWN)) {
         this.a.setText("Detecting device type [" + dev.getKeyCode() + "]...");
      } else if (dev.getDeviceVersion().equals(bluelab.connect.c.Enum_DeviceVersion.UNSUPPORTED)) {
         this.a.setText("<html>Detected unsupported device.<br>Please update the application.</html>");
      } else {
         this.a.setText("Detected " + dev.getDeviceVersion().getDeviceType().getUiText());
      }
   }

   protected final void paintComponent(Graphics var1) {
      super.paintComponent(var1);
      Graphics2D var4;
      (var4 = (Graphics2D)var1).setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
      int var2 = this.getWidth();
      int var3 = this.getHeight();
      var4.setColor(this.d);
      var4.fillRect(0, 0, var2, var3);
      var4.setColor(bluelab.connect.ui.BluelabColour.e);
      var4.setStroke(new BasicStroke(2.0F));
      var4.drawLine(5, 5, 10, 10);
      var4.drawLine(5, 10, 10, 5);
   }
}