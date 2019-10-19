package bluelab.connect.ui.b;

import bluelab.connect.c.BluelabRemoteXbeeDevice;
import bluelab.connect.model.SettingFileModel;
import java.awt.Component;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public final class c implements ListCellRenderer<BluelabRemoteXbeeDevice> {
   private j a;
   private d b;
   private SettingFileModel c;

   public c(SettingFileModel var1) {
      this.c = var1;
   }

   // $FF: synthetic method
   public final Component getListCellRendererComponent(JList var1, Object var2, int var3, boolean var4, boolean var5) {
      BluelabRemoteXbeeDevice var6 = (BluelabRemoteXbeeDevice)var2;
      if (var6.updateGetDevicePresence() == bluelab.connect.c.Enum_DevicePresence.INDISTINCT) {
         if (this.a == null) {
            this.a = new j(var6);
         }

         this.a.a(var6, var4);
         return this.a;
      } else {
         if (this.b == null) {
            this.b = new d(var6, this.c);
         }

         this.b.a(var6, var4);
         return this.b;
      }
   }
}