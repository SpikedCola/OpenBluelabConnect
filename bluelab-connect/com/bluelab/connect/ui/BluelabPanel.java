package bluelab.connect.ui;

import bluelab.connect.ui.laf.MenuButtonUI;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;
import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.EmptyBorder;

public final class BluelabPanel extends JPanel {
   private JButton a;
   private JPanel b;
   private JLabel c;
   private JLabel d;
   private Color e;

   public BluelabPanel(ImageIcon icon, String var2, List<v> var3, ActionListener var4, Color var5, Color var6, int var7) {
      this.e = var5;
      this.setBackground(var6);
      this.setBorder(new EmptyBorder(5, 5, 5, 5));
      this.setLayout(new BorderLayout(0, 0));
      var5 = bluelab.connect.ui.BluelabColour.b(var6);
      this.a = new BluelabButton(icon, var2);
      this.add(this.a, "West");
      JPanel panel;
      (panel = new JPanel(new BorderLayout(0, 0))).setOpaque(false);
      this.add(panel, "Center");
      this.c = new JLabel("");
      this.c.setFont(bluelab.connect.ui.e.d);
      this.c.setForeground(var5);
      panel.add(this.c, "West");
      this.d = new JLabel("");
      this.d.setFont(bluelab.connect.ui.e.i);
      this.d.setForeground(var5);
      panel.add(this.d, "South");
      this.b = this.a(var3, 2, var4);
      this.add(this.b, "East");
   }

   public final JPanel a() {
      return this.b;
   }

   public final void a(ImageIcon var1, String var2) {
      if (var1 != null) {
         this.a.setIcon(var1);
      }

   }

   public final void a(String var1, String var2) {
      this.c.setText(var1);
      this.d.setText(var2);
   }

   public final void a(String var1) {
      Component[] var5;
      int var4 = (var5 = this.b.getComponents()).length;

      for(int var3 = 0; var3 < var4; ++var3) {
         AbstractButton var2;
         if ((var2 = (AbstractButton)var5[var3]).getActionCommand().equals(var1)) {
            var2.doClick();
            return;
         }
      }

   }

   public final void a(String var1, boolean var2) {
      Component[] var6;
      int var5 = (var6 = this.b.getComponents()).length;

      for(int var4 = 0; var4 < var5; ++var4) {
         AbstractButton var3;
         if ((var3 = (AbstractButton)var6[var4]).getActionCommand().equals(var1)) {
            var3.setVisible(var2);
            return;
         }
      }

   }

   private JPanel a(List<v> var1, int var2, ActionListener var3) {
      JPanel var4 = new JPanel();
      ButtonGroup var5 = new ButtonGroup();
      var4.setLayout(new BoxLayout(var4, var2));
      var4.setOpaque(false);
      Iterator var8 = var1.iterator();

      while(var8.hasNext()) {
         v var7 = (v)var8.next();
         JToggleButton var6 = new JToggleButton(var7.a());
         if (var7.c() != null) {
            var6.setIcon(var7.c());
         }

         if (var7.d() != null) {
            var6.setSelectedIcon(var7.d());
         }

         var6.setUI(new MenuButtonUI(this.e, this.getBackground()));
         if (!var7.e().isEmpty()) {
            var6.setToolTipText(var7.e());
         }

         if (var3 != null) {
            var6.addActionListener(var3);
         }

         var6.setActionCommand(var7.b());
         var6.setAlignmentX(0.5F);
         var4.add(var6);
         var5.add(var6);
      }

      return var4;
   }
}