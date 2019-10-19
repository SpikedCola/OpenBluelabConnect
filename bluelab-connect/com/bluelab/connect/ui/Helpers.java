package bluelab.connect.ui;

import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import org.apache.commons.lang3.StringUtils;

public final class Helpers {
   public static void a(JLabel var0) {
      Timer var1;
      (var1 = new Timer(3000, (var1x) -> {
         var0.setText("");
      })).setRepeats(false);
      var1.start();
   }

   public static void a(Component var0, int var1) {
      Dimension var2;
      (var2 = var0.getPreferredSize()).width = var1;
      var0.setPreferredSize(var2);
   }

   public static JPanel b(Component var0, int var1) {
      JPanel var2;
      (var2 = new JPanel(new FlowLayout(var1, 0, 0))).setOpaque(false);
      var2.setPreferredSize(var0.getPreferredSize());
      var2.add(var0);
      return var2;
   }

   public static String a(String var0) {
      String[] var1 = StringUtils.splitByCharacterTypeCamelCase(var0);
      return "<html><div style='text-align: center;'>" + String.join("<br>", var1) + "</html>";
   }

   public static String b(String var0) {
      return String.join(" ", StringUtils.splitByCharacterTypeCamelCase(var0));
   }

   public static void c(String var0) {
      if (Desktop.isDesktopSupported()) {
         try {
            Desktop.getDesktop().browse(new URI(var0));
            return;
         } catch (IOException | URISyntaxException var1) {
            bluelab.connect.d.WeirdEncoder.ReportException((Throwable)var1);
         }
      }

   }

   public static String DeviceTypeToImage(bluelab.connect.c.Enum_DeviceType deviceType) {
      String image;
      if (deviceType.equals(bluelab.connect.c.Enum_DeviceType.EXTENDER)) {
         image = "/resources/extender_80px.png";
      } else if (deviceType.equals(bluelab.connect.c.Enum_DeviceType.GUARDIAN)) {
         image = "/resources/guardian_80px.png";
      } else if (deviceType.equals(bluelab.connect.c.Enum_DeviceType.PH_CONTROLLER)) {
         image = "/resources/phcontroller_80px.png";
      } else if (deviceType.equals(bluelab.connect.c.Enum_DeviceType.PRO_CONTROLLER)) {
         image = "/resources/procontroller_80px.png";
      } else {
         image = "/resources/unknown_80px.png";
      }

      return image;
   }
}