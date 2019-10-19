package bluelab.connect.ui.b.b;

import bluelab.connect.model.PodChainFileModel;
import bluelab.connect.ui.K;
import bluelab.connect.ui.Helpers;
import bluelab.connect.ui.M;
import bluelab.connect.ui.laf.RoundedButtonUI;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiConsumer;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeListener;

public final class t extends JPanel {
   private PodChainFileModel a = new PodChainFileModel();
   private boolean b;
   private JLabel c;
   private JPanel d;
   private JButton e;
   private JButton f;
   private bluelab.connect.ui.z g;
   private ChangeListener h;
   private static final Dimension i = new Dimension(540, 120);

   public t(boolean var1) {
      this.b = var1;
      this.h = new u(this);
      this.setLayout(new GridBagLayout());
      this.setBorder(new EmptyBorder(10, 0, 0, 0));
      this.setOpaque(false);
      GridBagConstraints var2 = new GridBagConstraints();
      this.c = new JLabel();
      this.c.setFont(bluelab.connect.ui.e.g);
      var2.gridx = 1;
      var2.insets = new Insets(0, 5, 0, 5);
      var2.anchor = 17;
      this.add(this.c, var2);
      JButton var3;
      (var3 = new JButton("Help")).setUI(new RoundedButtonUI(bluelab.connect.ui.Enum_RoundedButtonType.ALTERNATIVE));
      var3.addActionListener(this::a);
      var2.gridx = 2;
      var2.anchor = 13;
      this.add(var3, var2);
      this.e = new bluelab.connect.ui.B("/resources/chevron-left.png", "/resources/chevron-left-hover.png");
      this.e.addActionListener(this::b);
      var2.gridx = 0;
      var2.gridy = 1;
      var2.insets = new Insets(0, 0, 0, 0);
      this.add(this.e, var2);
      this.d = new JPanel(new CardLayout());
      this.d.setOpaque(false);
      var2.gridx = 1;
      var2.gridwidth = 2;
      this.add(this.d, var2);
      this.f = new bluelab.connect.ui.B("/resources/chevron-right.png", "/resources/chevron-right-hover.png");
      this.f.addActionListener(this::c);
      var2.gridx = 3;
      this.add(this.f, var2);
      ArrayList var4;
      (var4 = new ArrayList()).add(this.c());
      var4.add(this.d());
      var4.add(this.e());
      this.g = new bluelab.connect.ui.z((Window)SwingUtilities.getRoot(this), "Nutrient dosing help", var4, 20, 10, false);
   }

   private JPanel c() {
      ArrayList var1 = new ArrayList();
      String[] var4 = new String[]{"- You can deliver a multi-part nutrient recipe with Pro Controller and PeriPod by inputting nutrient feed chart values into Connect.", "- A feed chart from a nutrient manufacturer may be in ml/gal or ml/l, or you may know the volume of each nutrient for a fresh reservoir in ml. Your preferred Dosing display unit can be selected in Application Settings.", "- Set the required pH and EC/ppm for that feed chart period in Device Settings."};

      for(int var3 = 0; var3 < 3; ++var3) {
         String var2 = var4[var3];
         var1.add(b(var2));
      }

      return new M("Your feed chart", "/resources/feed-chart.png", (String)null, a(var1, 10), 20);
   }

   private JPanel d() {
      ArrayList var1 = new ArrayList();
      String[] var4 = new String[]{"- You can add up to 12 pumps in a maximum of three PeriPods to meet your nutrient part requirements.", "- Combinations of PeriPod M3 and M4 dosers can be daisy chained.", "- PeriPod M and L versions cannot be mixed for multi-part nutrient dosing."};

      for(int var3 = 0; var3 < 3; ++var3) {
         String var2 = var4[var3];
         var1.add(b(var2));
      }

      return new M("Adding more pumps", "/resources/pod-daisy-chain.png", (String)null, a(var1, 10), 20);
   }

   private JPanel e() {
      ArrayList var1 = new ArrayList();
      String[] var4 = new String[]{"Watch this short video for how to set up multi-part nutrient dosing in Connect.", "Want more information?"};

      for(int var3 = 0; var3 < 2; ++var3) {
         String var2 = var4[var3];
         var1.add(b(var2));
      }

      K var5 = new K("<html><u>Download product manuals</u><br></html>", "https://www.bluelab-connect.com/go?link=productmanuals");
      var1.add(Helpers.b(var5, 0));
      return new M("Setting up multi-part nutrient dosing", "/resources/dosing-video.png", "https://www.youtube.com/watch?v=DHLx1BDwBXA&feature=youtu.be", a(var1, 10), 20);
   }

   private static JPanel a(List<Component> var0, int var1) {
      JPanel var6;
      (var6 = new JPanel(new GridBagLayout())).setOpaque(false);
      GridBagConstraints var2;
      (var2 = new GridBagConstraints()).ipady = 10;
      var2.gridx = 0;
      var2.fill = 2;
      var2.weightx = 1.0D;
      Iterator var3 = var0.iterator();

      while(var3.hasNext()) {
         Component var4 = (Component)var3.next();
         var6.add(var4, var2);
      }

      JPanel var10000 = var6;
      String var7 = "North";
      JPanel var5 = var10000;
      JPanel var8;
      (var8 = new JPanel(new BorderLayout())).setOpaque(false);
      var8.add(var5, var7);
      var8.setPreferredSize(i);
      return var8;
   }

   private static JTextArea b(String var0) {
      JTextArea var1;
      (var1 = new JTextArea(var0)).setEditable(false);
      var1.setLineWrap(true);
      var1.setWrapStyleWord(true);
      return var1;
   }

   public final PodChainFileModel a() {
      return this.a;
   }

   private void a(ActionEvent var1) {
      bluelab.connect.ui.z var2 = this.g;
      this.g.setVisible(true);
   }

   private void f() {
      double var1 = 0.0D;
      Component[] var6;
      int var5 = (var6 = this.d.getComponents()).length;

      int var4;
      for(var4 = 0; var4 < var5; ++var4) {
         var1 = Math.max(((o)var6[var4]).b(), var1);
      }

      var5 = (var6 = this.d.getComponents()).length;

      for(var4 = 0; var4 < var5; ++var4) {
         ((o)var6[var4]).a(var1);
      }

   }

   public final void a(boolean var1) {
      this.b = var1;
      Component[] var5;
      int var4 = (var5 = this.d.getComponents()).length;

      for(int var3 = 0; var3 < var4; ++var3) {
         ((o)var5[var3]).a(var1);
      }

   }

   public final void a(PodChainFileModel var1) {
      this.a = new PodChainFileModel(var1);
      this.d.removeAll();
      this.a.getPodSettings().forEach((var1x, var2) -> {
         this.d.add(new o(var1x, var2, this.b, this.h));
      });
      boolean var3 = var1.getPodCount() > 1;
      this.e.setVisible(var3);
      this.f.setVisible(var3);
      this.g();
   }

   public final void a(PodChainFileModel var1, boolean var2) {
      this.a.disablePumps();
      if (!this.a.importPumpSettings(var1)) {
         JOptionPane.showMessageDialog(SwingUtilities.getRoot(this), "Loaded file uses a ratio unit different to your current settings.", "Device settings warning", 1);
      }

      Component[] var4;
      int var3 = (var4 = this.d.getComponents()).length;

      for(int var5 = 0; var5 < var3; ++var5) {
         ((o)var4[var5]).c();
      }

      this.f();
   }

   public final PodChainFileModel b() {
      return new PodChainFileModel(this.a);
   }

   public final void a(String var1) {
      Component[] var5;
      int var4 = (var5 = this.d.getComponents()).length;

      for(int var3 = 0; var3 < var4; ++var3) {
         ((o)var5[var3]).a(var1);
      }

   }

   private void b(ActionEvent var1) {
      ((CardLayout)this.d.getLayout()).previous(this.d);
      this.g();
   }

   private void c(ActionEvent var1) {
      ((CardLayout)this.d.getLayout()).next(this.d);
      this.g();
   }

   private void g() {
      Component[] var4;
      int var3 = (var4 = this.d.getComponents()).length;
      int var2 = 0;

      int var10000;
      while(true) {
         if (var2 >= var3) {
            var10000 = -1;
            break;
         }

         Component var1;
         if ((var1 = var4[var2]).isVisible()) {
            var10000 = ((o)var1).a();
            break;
         }

         ++var2;
      }

      int var5 = var10000;
      var2 = this.a.getPodCount();
      this.c.setText(String.format(" Pod %d of %d", var5, var2));
   }

   // $FF: synthetic method
   static void a(t var0) {
      var0.f();
   }
}