package bluelab.connect.ui.b.b;

import bluelab.connect.model.PumpSettings;
import bluelab.connect.ui.Helpers;
import bluelab.connect.ui.laf.MinusPlusSpinnerUI;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public final class p extends bluelab.connect.ui.C {
   private PumpSettings a;
   private bluelab.connect.ui.FocusTextField b;
   private JComboBox<bluelab.connect.c.Enum_PumpType> c;
   private JPanel d;
   private JLabel e;
   private JSpinner f;
   private JLabel g;
   private ChangeListener h;

   public p(PumpSettings var1, boolean var2, ChangeListener var3) {
      this.h = var3;
      this.setBackground(bluelab.connect.ui.BluelabColour.f);
      this.setLayout(new BoxLayout(this, 1));
      this.setBorder(new EmptyBorder(10, 5, 10, 5));
      this.b = new bluelab.connect.ui.FocusTextField("Pump name", 14);
      this.b.setText(var1.name);
      this.b.setFont(bluelab.connect.ui.e.g);
      this.b.setHorizontalAlignment(0);
      this.b.setAlignmentX(0.5F);
      this.b.setToolTipText("Pump name");
      this.b.setColumns(8);
      this.b.addFocusListener(new r(this, (byte)0));
      this.add(this.b);
      this.add(Box.createRigidArea(new Dimension(0, 8)));
      this.c = new JComboBox(bluelab.connect.c.Enum_PumpType.values());
      this.c.setSelectedItem(var1.pumpType);
      this.c.setFont(bluelab.connect.ui.e.g);
      this.c.setAlignmentX(0.5F);
      ((JLabel)this.c.getRenderer()).setHorizontalAlignment(0);
      this.c.addActionListener(this::a);
      this.c.setToolTipText("Pump function");
      this.c.setBorder(new EmptyBorder(0, 10, 0, 10));
      this.add(Helpers.b(this.c, 1));
      this.add(Box.createRigidArea(new Dimension(0, 7)));
      this.d = new JPanel(new CardLayout());
      this.add(this.d);
      JPanel var5;
      (var5 = new JPanel()).setLayout(new BoxLayout(var5, 1));
      this.d.add(var5, "value");
      this.e = new JLabel();
      this.e.setFont(bluelab.connect.ui.e.c);
      this.e.setHorizontalAlignment(0);
      this.e.setAlignmentX(0.5F);
      this.e.setToolTipText("Value only determines the pump ratio, which together with the dose on time determines the average pump flow rate.");
      var5.add(this.e);
      var5.add(Box.createRigidArea(new Dimension(0, 4)));
      this.f = new JSpinner(new SpinnerNumberModel(var1.value, 0.0D, 5000.0D, 1.0D));
      this.f.setUI(new MinusPlusSpinnerUI());
      this.f.addChangeListener(this::a);
      var5.add(this.f);
      var5.add(Box.createRigidArea(new Dimension(0, 5)));
      this.g = new JLabel(String.format("Ratio: %d%%", var1.ratio));
      this.g.setForeground(bluelab.connect.ui.BluelabColour.m);
      this.g.setHorizontalAlignment(0);
      this.g.setAlignmentX(0.5F);
      var5.add(this.g);
      this.d.add(new JPanel(), "none");
      this.a = var1;
      if (var1.pumpType != bluelab.connect.c.Enum_PumpType.EC) {
         var1.value = 0.0D;
      }

      this.a();
      this.a(var2, false);
   }

   public final void setBackground(Color var1) {
      super.setBackground(var1);
      var1 = bluelab.connect.ui.BluelabColour.a(var1, 0.95F);
      this.setForeground(var1);
   }

   public final void a(boolean var1) {
      this.a(var1, true);
   }

   private void a(boolean var1, boolean var2) {
      if (var2 && var1) {
         this.f.setValue(0.0D);
      } else if (this.a.pumpType.equals(bluelab.connect.c.Enum_PumpType.EC) && !var1) {
         this.f.setValue(100.0D);
      }

      this.d.setVisible(var1);
   }

   public final void a() {
      this.b.setText(this.a.name);
      this.c.setSelectedItem(this.a.pumpType);
      this.f.setValue(this.a.value);
      this.d();
   }

   public final bluelab.connect.c.Enum_PumpType b() {
      return this.a.pumpType;
   }

   public final double c() {
      return this.a.value;
   }

   public final void a(double var1) {
      if (var1 > 0.0D) {
         this.a.ratio = (int)Math.round(this.a.value / var1 * 100.0D);
      } else {
         this.a.ratio = 0;
      }

      EventQueue.invokeLater(new q(this));
   }

   public final void a(String var1) {
      this.a.unit = var1;
      this.d();
   }

   private void d() {
      if (this.a.value == 0.0D && this.a.ratio != 0) {
         this.e.setText("---");
      } else {
         this.e.setText(String.format("<html><div style='text-align: center;'>%.0f<sup style='font-size:0.7em'> %s</sup></div></html>", this.a.value, this.a.unit));
      }

      this.g.setText(String.format("Ratio: %d%%", this.a.ratio));
   }

   private void a(ActionEvent var1) {
      this.a.pumpType = (bluelab.connect.c.Enum_PumpType)this.c.getSelectedItem();
      CardLayout var2 = (CardLayout)this.d.getLayout();
      if (this.a.pumpType.equals(bluelab.connect.c.Enum_PumpType.EC)) {
         var2.show(this.d, "value");
      } else {
         var2.show(this.d, "none");
         this.f.setValue(0.0D);
      }
   }

   public final void a(ChangeEvent var1) {
      this.a.value = (Double)this.f.getValue();
      if (this.h != null) {
         this.h.stateChanged(var1);
      }

   }

   // $FF: synthetic method
   static PumpSettings a(p var0) {
      return var0.a;
   }

   // $FF: synthetic method
   static bluelab.connect.ui.FocusTextField b(p var0) {
      return var0.b;
   }

   // $FF: synthetic method
   static void c(p var0) {
      var0.d();
   }
}