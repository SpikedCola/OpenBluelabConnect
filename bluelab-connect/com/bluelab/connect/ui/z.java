package bluelab.connect.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public final class z extends JDialog {
   private static final Font a;
   private JPanel b;
   private JPanel c;
   private JLabel d;
   private JButton e;
   private JButton f;
   private boolean g = false;

   static {
      a = bluelab.connect.ui.e.i;
   }

   public z(Window var1, String var2, List<JPanel> var3, int var4, int var5, boolean var6) {
      super(var1, var2, ModalityType.MODELESS);
      this.setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/resources/connect_48px.png")));
      this.setResizable(false);
      this.setUndecorated(false);
      this.setDefaultCloseOperation(1);
      this.a(var3, var4, 10);
      this.pack();
      this.setLocationRelativeTo(this.getParent());
   }

   private void a(List<JPanel> var1, int var2, int var3) {
      this.b = new JPanel(new BorderLayout(0, var3 / 2));
      this.b.setBorder(new EmptyBorder(var2, var2, var2, var2));
      this.b.setBackground(bluelab.connect.ui.BluelabColour.o);
      this.c = new JPanel(new CardLayout());
      this.c.setOpaque(false);
      this.b.add(this.c, "North");
      Iterator var5 = var1.iterator();

      while(var5.hasNext()) {
         JPanel var4 = (JPanel)var5.next();
         this.c.add(var4);
      }

      JPanel var10000 = this.b;
      JPanel var6;
      (var6 = new JPanel(new BorderLayout())).setOpaque(false);
      this.e = new B("/resources/chevron-left.png", "/resources/chevron-left-hover.png");
      this.e.addActionListener(this::a);
      var6.add(Helpers.b(this.e, 1), "West");
      this.f = new B("/resources/chevron-right.png", "/resources/chevron-right-hover.png");
      this.f.addActionListener(this::b);
      var6.add(Helpers.b(this.f, 1), "East");
      this.d = new JLabel();
      this.d.setFont(a);
      this.d.setHorizontalAlignment(0);
      var6.add(this.d, "Center");
      var10000.add(var6, "South");
      this.a();
      this.setContentPane(this.b);
   }

   private void a(ActionEvent var1) {
      ((CardLayout)this.c.getLayout()).previous(this.c);
      this.a();
   }

   private void b(ActionEvent var1) {
      ((CardLayout)this.c.getLayout()).next(this.c);
      this.a();
   }

   private void a() {
      int var1;
      if ((var1 = this.c.getComponentCount()) > 0) {
         int var3 = 1;
         Component[] var6;
         int var5 = (var6 = this.c.getComponents()).length;

         for(int var4 = 0; var4 < var5 && !var6[var4].isVisible(); ++var4) {
            ++var3;
         }

         this.d.setText(String.format("%d of %d", var3, var1));
         this.e.setVisible(this.g || var3 != 1);
         this.f.setVisible(this.g || var3 != var1);
      }

   }
}