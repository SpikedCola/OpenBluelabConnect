package bluelab.connect.ui;

import bluelab.connect.ui.laf.SwitchButtonUI;
import javax.swing.JToggleButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public final class I extends JToggleButton implements ChangeListener {
   private String a;
   private String b;

   public I(String var1, String var2, boolean var3) {
      super(var3 ? var1 : var2, var3);
      this.a = var1;
      this.b = var2;
      this.addChangeListener(this);
      this.setUI(new SwitchButtonUI());
   }

   public final void stateChanged(ChangeEvent var1) {
      if (this.isSelected()) {
         this.setText(this.a);
      } else {
         this.setText(this.b);
      }
   }
}