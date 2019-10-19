package bluelab.connect.ui.b.b;

import bluelab.connect.ui.I;
import bluelab.connect.ui.Helpers;
import java.awt.event.ActionEvent;

public final class s extends b {
   private I h;
   private I i;

   public s(bluelab.connect.d.BluelabDevice var1, bluelab.connect.c.BluelabRemoteXbeeDevice var2) {
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
      this.a(bluelab.connect.c.Enum_ControlType.PH, this.d, this.c, var1 + 1);
   }

   protected final void a(ActionEvent var1) {
      super.a(var1);
      this.h.setSelected(this.monitorController.b.equals(bluelab.connect.c.Enum_Mode.CONTROL));
      this.i.setSelected(this.monitorController.c);
   }

   protected final void a() {
      super.a();
      this.monitorController.b = this.h.isSelected() ? bluelab.connect.c.Enum_Mode.CONTROL : bluelab.connect.c.Enum_Mode.MONITOR;
      this.monitorController.c = this.i.isSelected();
      this.monitorController.f = false;
   }
}