package bluelab.connect.ui;

import bluelab.connect.model.PodChainFileModel;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class PeripodUiTest {
   private Map<Integer, bluelab.connect.c.Peripod> a;
   private JFrame b;
   private I c;
   private bluelab.connect.ui.b.b.t d;

   public static void main(String[] var0) {
      try {
         new PeripodUiTest();
      } catch (bluelab.connect.c.Exception2 var1) {
         var1.printStackTrace();
      }
   }

   public PeripodUiTest() {
      this.a();

      try {
         UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      } catch (InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException | ClassNotFoundException var4) {
         var4.printStackTrace();
      }

      JPanel var2;
      (var2 = new JPanel()).setBackground(bluelab.connect.ui.BluelabColour.o);
      var2.add(new JLabel("Ratios"));
      this.c = new I("Enabled", "Disabled", false);
      this.c.addActionListener(this::b);
      var2.add(this.c);
      this.d = new bluelab.connect.ui.b.b.t(this.c.isSelected());
      var2.add(this.d);
      JButton var3;
      (var3 = new JButton("Refresh")).addActionListener(this::a);
      var2.add(var3);
      this.b = new JFrame();
      this.b.setDefaultCloseOperation(3);
      this.b.add(var2);
      this.b.pack();
      this.b.setLocationRelativeTo((Component)null);
      this.b.setVisible(true);
      this.a((ActionEvent)null);
   }

   private void a() {
      this.a = new LinkedHashMap();

      for(int var1 = 1; var1 <= 2; ++var1) {
         bluelab.connect.c.Enum_PeripodType var2 = (bluelab.connect.c.Enum_PeripodType)bluelab.connect.j.EnumValueFind.FindValue(bluelab.connect.c.Enum_PeripodType.class, var1, bluelab.connect.c.Enum_PeripodType.PERIPOD_M3);
         bluelab.connect.c.PeripodWithPumps var3;
         (var3 = (bluelab.connect.c.PeripodWithPumps)bluelab.connect.c.PeripodFactory.InstantiatePeripod(var1, var2, 3)).setName("Pod " + String.valueOf(var1));
         this.a.put(var1, var3);
      }

   }

   public void a(ActionEvent var1) {
      this.d.a(new PodChainFileModel(this.a, "ml"));
   }

   public void b(ActionEvent var1) {
      this.d.a(this.c.isSelected());
      this.b.pack();
   }
}