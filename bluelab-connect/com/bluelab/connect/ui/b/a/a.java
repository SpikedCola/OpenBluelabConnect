package bluelab.connect.ui.b.a;

import bluelab.connect.c.Enum_ControlType;
import bluelab.connect.c.UnitConverter;
import bluelab.connect.c.BluelabRemoteXbeeDevice;
import bluelab.connect.model.SettingFileModel;
import bluelab.connect.ui.laf.RoundedButtonUI;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class a extends JPanel {
   private static Logger logger = LoggerFactory.getLogger(a.class);
   protected SettingFileModel settingFileModel;
   protected BluelabRemoteXbeeDevice device;
   protected static final Font c;
   protected static final Font d;
   protected static final Font e;

   static {
      c = bluelab.connect.ui.e.a;
      d = bluelab.connect.ui.e.b;
      e = bluelab.connect.ui.e.c;
   }

   public abstract void a();

   public final void a(JLabel var1, Enum_ControlType controlType) {
      var1.setText(String.format("<html>%s<sup style='font-size:0.5em'> %s</sup></html>", this.device.getCurrentValueOfControlType(controlType, this.settingFileModel), UnitConverter.GetUnit(controlType, this.settingFileModel)));
      if (!this.device.isOnline()) {
         var1.setForeground(bluelab.connect.ui.BluelabColour.j);
      } else if (this.device.allControlsWithinRange(controlType)) {
         var1.setForeground(bluelab.connect.ui.BluelabColour.i);
      } else {
         var1.setForeground(bluelab.connect.ui.BluelabColour.l);
      }
   }

   public final void a(JLabel var1, JLabel var2, JLabel var3, JLabel var4, Enum_ControlType controlType) {
      var1.setText(String.format("<html>%s<sup style='font-size:0.5em'> %s</sup></html>", this.device.formatMinMaxValue(controlType, this.settingFileModel), UnitConverter.GetUnit(controlType, this.settingFileModel)));
      var2.setText(String.format("%d", this.device.getCountOfSomething(controlType)));
      long var9;
      long var11 = (var9 = this.device.getTimeOfSomething(controlType)) / 3600L;
      long var13 = (var9 %= 3600L) / 60L;
      var9 %= 60L;
      var3.setText(String.format("%02d:%02d:%02d", var11, var13, var9));
      var4.setText(this.device.getDirectionUiText(controlType));
      Color var15;
      if (this.device.isOnline() && this.device.modeIsControl()) {
         var15 = bluelab.connect.ui.BluelabColour.l;
      } else {
         var15 = bluelab.connect.ui.BluelabColour.j;
      }

      var1.setForeground(var15);
      var2.setForeground(var15);
      var3.setForeground(var15);
      var4.setForeground(var15);
   }

   public final JComponent b() {
      JPanel var1;
      (var1 = new JPanel()).setOpaque(false);
      JButton var2;
      (var2 = new JButton("Reset dose counters")).setUI(new RoundedButtonUI());
      var2.setToolTipText("Reset device dose counters");
      var2.addActionListener(this::resetDoseCounters);
      var1.add(var2);
      return var1;
   }

   public void resetDoseCounters(ActionEvent var1) {
      this.device.resetDoseCounters();
      logger.info("User reset device '{}' dose counters", this.device.getKeyCode());
   }
}