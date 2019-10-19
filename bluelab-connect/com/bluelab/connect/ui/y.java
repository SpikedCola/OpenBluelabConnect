package bluelab.connect.ui;

import bluelab.connect.Connect;
import bluelab.connect.ui.laf.RoundedButtonUI;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.Iterator;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class y extends JPanel {
   private static Logger a = LoggerFactory.getLogger(y.class);
   private bluelab.connect.d.BluelabDevice b;
   private BluelabTextField c;
   private DefaultListModel<bluelab.connect.c.BluelabRemoteXbeeDevice> d;
   private JList<bluelab.connect.c.BluelabRemoteXbeeDevice> e;

   public y(bluelab.connect.d.BluelabDevice var1, MouseListener var2, ListSelectionListener var3) {
      this.b = var1;
      this.setBorder(new EmptyBorder(0, 0, 0, 0));
      this.setBackground(bluelab.connect.ui.BluelabColour.g);
      this.setLayout(new BorderLayout(0, 0));
      JScrollPane var4;
      (var4 = new JScrollPane()).setBorder(new EmptyBorder(0, 0, 0, 0));
      var4.getVerticalScrollBar().setUnitIncrement(10);
      this.add(var4, "Center");
      this.d = new DefaultListModel();
      this.e = new JList(this.d);
      this.e.setSelectionMode(1);
      this.e.setVisibleRowCount(0);
      this.e.setBackground(bluelab.connect.ui.BluelabColour.h);
      this.e.setCellRenderer(new bluelab.connect.ui.b.c(var1.getSettingFileModel()));
      this.e.addMouseListener(var2);
      this.e.addListSelectionListener(var3);
      var4.setPreferredSize(new Dimension(430, 90));
      this.e.setLayoutOrientation(2);
      JPanel var6;
      (var6 = new JPanel()).setOpaque(false);
      var6.setLayout(new BoxLayout(var6, 1));
      var6.add(new DownUpPanel("Alert legend", true, new bluelab.connect.ui.b.e()));
      JPanel var7;
      (var7 = new JPanel()).setOpaque(false);
      var7.setBorder(new EmptyBorder(10, 15, 10, 15));
      var7.setLayout(new BorderLayout(10, 0));
      JLabel var5 = new JLabel("New device");
      var7.add(var5, "West");
      this.c = new BluelabTextField("Enter the 4-digit device key-code");
      this.c.addActionListener(this::a);
      var7.add(this.c, "Center");
      JButton var8;
      (var8 = new JButton("Add")).setToolTipText("Add device to list");
      var8.setUI(new RoundedButtonUI());
      var8.addActionListener(this::a);
      var7.add(var8, "East");
      var6.add(var7);
      this.add(var6, "South");
      var4.setViewportView(this.e);
   }

   public final void a(List<bluelab.connect.c.BluelabRemoteXbeeDevice> var1) {
      try {
         this.d.clear();
         Iterator var2 = var1.iterator();

         while(var2.hasNext()) {
            bluelab.connect.c.BluelabRemoteXbeeDevice var4 = (bluelab.connect.c.BluelabRemoteXbeeDevice)var2.next();
            this.d.addElement(var4);
         }

         if (this.e.getComponentCount() > 0 && this.e.getSelectedIndex() == -1) {
            this.e.setSelectedIndex(0);
            return;
         }
      } catch (Throwable var3) {
         bluelab.connect.d.WeirdEncoder.ReportException(var3);
         a.error("Update model error: {}", var3.toString());
      }

   }

   public final void a(bluelab.connect.c.BluelabRemoteXbeeDevice var1) {
      int var2;
      if ((var2 = this.d.indexOf(var1)) != -1) {
         this.d.set(var2, var1);
      }

   }

   public final void a() {
      this.e.repaint();
   }

   public final bluelab.connect.c.BluelabRemoteXbeeDevice b() {
      return (bluelab.connect.c.BluelabRemoteXbeeDevice)this.e.getSelectedValue();
   }

   private void a(ActionEvent var1) {
      if (this.b != null) {
         String keyCode = this.c.getText().trim().toLowerCase();
         if (this.b.containsKeycode(keyCode)) {
            String msg = String.format("A device with the key-code '%s' is already in your list.", keyCode);
            JOptionPane.showMessageDialog(this, msg, Connect.GetTitle(), 2);
            return;
         }

         boolean var2 = true;
         boolean var3 = true;
         String var4 = null;
         if (keyCode.length() != 4) {
            var3 = false;
            var4 = "Keycode should be 4 digits long.";
         } else if (!keyCode.matches("^[a-z0-9]*$")) {
            var3 = false;
            var4 = "Keycode should be strictly alphanumeric.";
         }

         if (!var3) {
            JOptionPane.showMessageDialog((Component)null, var4, Connect.GetTitle(), 2);
         }

         if (var3) {
            this.b.b(keyCode);
            this.c.setText("");
         }
      }

   }
}