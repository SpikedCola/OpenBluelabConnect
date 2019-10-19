package bluelab.connect.ui;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

final class u implements Runnable {
   // $FF: synthetic field
   private BluelabFrame a;
   // $FF: synthetic field
   private final List b;
   // $FF: synthetic field
   private final Component c;

   u(BluelabFrame var1, List var2, Component var3) {
      this.a = var1;
      this.b = var2;
      this.c = var3;
      super();
   }

   public final void run() {
      if (!this.b.isEmpty() && !BluelabFrame.f(this.a)) {
         String var1 = "Firmware updates are available for your devices:";
         String var2 = "Please update them as soon as possible. It takes about 5-10 minutes per device.";
         ArrayList var3 = new ArrayList();
         Iterator var5 = this.b.iterator();

         while(var5.hasNext()) {
            bluelab.connect.l.DeviceVersion var4 = (bluelab.connect.l.DeviceVersion)var5.next();
            var3.add(((bluelab.connect.c.Enum_DeviceVersion)var4.supportedDeviceVersions.iterator().next()).getDeviceType().getUiText() + ": " + var4.versionString);
         }

         BluelabFrame.a(this.a, true);
         JOptionPane.showMessageDialog(this.c, "<html>" + var1 + "<br>" + String.join("<br>", var3) + "<br><br>" + var2 + "</html>", "Firmware updates are available", 1, UIManager.getIcon("OptionPane.infoIcon"));
         BluelabFrame.a(this.a, false);
      }

   }
}