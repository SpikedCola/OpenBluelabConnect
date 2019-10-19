package bluelab.connect.ui.b.b;

import bluelab.connect.ui.I;
import bluelab.connect.ui.Helpers;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public final class v extends b {
   private I h;
   private I i;
   private I j;
   private I k;

   public v(bluelab.connect.d.BluelabDevice var1, bluelab.connect.c.BluelabRemoteXbeeDevice var2) {
      super(var1, var2);
   }

   protected final void a(int var1) {
      super.a(var1);
      this.h = new I("Control", "Monitor", false);
      this.h.setToolTipText("Switches the device between monitor and control modes.");
      Helpers.a(this.h, 90);
      this.a("Mode", this.h, this.d, this.c, var1 + 1);
      this.i = new I("Enabled", "Disabled", false);
      Helpers.a(this.i, 90);
      this.a("Alarms", this.i, this.d, this.c, var1 + 1);
      this.a(bluelab.connect.c.Enum_ControlType.CONDUCTIVITY, this.d, this.c, var1 + 1);
      this.a(bluelab.connect.c.Enum_ControlType.PH, this.d, this.c, var1 + 1);
      this.a(bluelab.connect.c.Enum_ControlType.TEMPERATURE, this.d, this.c, var1 + 1);
      a(new w("Lockouts"), this.d, this.c, var1 + 1);
      this.j = new I("Enabled", "Disabled", false);
      Helpers.a(this.j, 90);
      a("Ineffective control lockout", bluelab.connect.ui.e.g, this.j, this.d, this.c, var1 + 2);
      a(a("Device enters lockout if 15 doses causes no change."), this.d, this.c, var1 + 2);
      this.k = new I("Enabled", "Disabled", false);
      Helpers.a(this.k, 90);
      a("Low conductivity lockout", bluelab.connect.ui.e.g, this.k, this.d, this.c, var1 + 2);
      a(a("Device enters lockout if measured conductivity is 0.2 EC or lower (2 CF, 100 ppm500, 150 ppm700)."), this.d, this.c, var1 + 2);
   }

   public final void a(boolean var1) {
      if (var1) {
         this.a((ActionEvent)null);
      }

   }

   protected final void a(ActionEvent var1) {
      super.a(var1);
      this.h.setSelected(this.monitorController.mode.equals(bluelab.connect.c.Enum_Mode.CONTROL));
      this.i.setSelected(this.monitorController.turnAlarmsOnOff);
      this.j.setSelected(this.monitorController.lowConductivityLockout);
      this.k.setSelected(this.monitorController.ineffectiveControlLockout);
   }

   protected final void b(ActionEvent var1) {
      if (!this.remoteDev.getMode().equals(bluelab.connect.c.Enum_Mode.SETTINGS) && !this.remoteDev.getMode().equals(bluelab.connect.c.Enum_Mode.CALIBRATION)) {
         this.a();
         bluelab.connect.c.c.c var2;
         (var2 = new bluelab.connect.c.c.c(this.dev.getXbeeDevice(), this.remoteDev, this.monitorController)).setSuccessFailCallbacks(this::b, this::c);
         (new Thread(var2)).start();
         this.e.setText("Writing device settings...");
         this.e.setForeground(bluelab.connect.ui.BluelabColour.l);
      } else {
         JOptionPane.showMessageDialog(SwingUtilities.getRoot(this), "Cannot apply settings while device is in settings/calibration mode.", "Device settings warning", 2, UIManager.getIcon("OptionPane.warningIcon"));
      }
   }

   protected final void a() {
      super.a();
      this.monitorController.mode = this.h.isSelected() ? bluelab.connect.c.Enum_Mode.CONTROL : bluelab.connect.c.Enum_Mode.MONITOR;
      this.monitorController.turnAlarmsOnOff = this.i.isSelected();
      this.monitorController.lowConductivityLockout = this.k.isSelected();
      this.monitorController.ineffectiveControlLockout = this.j.isSelected();
      this.monitorController.f = false;
   }
}