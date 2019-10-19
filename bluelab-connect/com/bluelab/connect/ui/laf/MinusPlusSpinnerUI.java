package bluelab.connect.ui.laf;

import bluelab.connect.ui.B;
import java.awt.Component;
import java.awt.FlowLayout;
import javax.swing.JComponent;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicSpinnerUI;

public class MinusPlusSpinnerUI extends BasicSpinnerUI {
   private boolean a;

   public MinusPlusSpinnerUI() {
      this(false);
   }

   private MinusPlusSpinnerUI(boolean var1) {
      this.a = false;
   }

   private JComponent a() {
      B var1;
      (var1 = new B("/resources/plus.png", "/resources/plus-hover.png")).setInheritsPopupMenu(true);
      var1.setName("Spinner.nextButton");
      this.installNextButtonListeners(var1);
      return var1;
   }

   private JComponent b() {
      B var1;
      (var1 = new B("/resources/minus.png", "/resources/minus-hover.png")).setInheritsPopupMenu(true);
      var1.setName("Spinner.previousButton");
      this.installPreviousButtonListeners(var1);
      return var1;
   }

   protected JComponent createEditor() {
      return super.createEditor();
   }

   public void installUI(JComponent var1) {
      super.installUI(var1);
      var1.removeAll();
      var1.setLayout(new FlowLayout(1, 5, 0));
      if (this.a) {
         var1.add(this.createEditor());
      }

      var1.add(this.b());
      var1.add(this.a());
      var1.setOpaque(false);
      var1.setBorder(new EmptyBorder(0, 0, 0, 0));
   }

   // $FF: synthetic method
   protected Component createNextButton() {
      return this.a();
   }

   // $FF: synthetic method
   protected Component createPreviousButton() {
      return this.b();
   }
}