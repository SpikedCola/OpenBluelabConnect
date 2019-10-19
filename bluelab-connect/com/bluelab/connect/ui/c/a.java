package bluelab.connect.ui.c;

import bluelab.connect.d.BluelabDevice;
import bluelab.connect.h.TransmitReceiveTable;
import bluelab.connect.h.StateManager;
import bluelab.connect.ui.BluelabColour;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

public final class a extends JPanel {
   public a(BluelabDevice var1) {
      this.setBackground(BluelabColour.o);
      this.setBorder(new EmptyBorder(10, 10, 10, 10));
      this.setLayout(new BorderLayout(10, 10));
      StateManager var5;
      StateManager var2 = var5 = var1.getStateManager();
      JPanel var3;
      (var3 = new JPanel(new BorderLayout())).setOpaque(false);
      var3.setBorder(BorderFactory.createTitledBorder("Coordinator info"));
      bluelab.connect.h.TwoColumnTable var6 = new bluelab.connect.h.TwoColumnTable(var2);
      JTable var7;
      (var7 = new JTable(var6)).setPreferredScrollableViewportSize(new Dimension(100, 160));
      var7.setFillsViewportHeight(true);
      JScrollPane var4;
      (var4 = new JScrollPane()).setOpaque(false);
      var4.getViewport().setOpaque(false);
      var4.setViewportView(var7);
      var4.setBorder(new EmptyBorder(0, 0, 0, 0));
      var3.add(var4, "Center");
      this.add(var3, "North");
      (var3 = new JPanel(new BorderLayout())).setOpaque(false);
      var3.setBorder(BorderFactory.createTitledBorder("Device info"));
      TransmitReceiveTable var8 = new TransmitReceiveTable(var5);
      (var7 = new JTable(var8)).setPreferredScrollableViewportSize(new Dimension(100, 100));
      var7.setFillsViewportHeight(true);
      (var4 = new JScrollPane()).setOpaque(false);
      var4.getViewport().setOpaque(false);
      var4.setViewportView(var7);
      var4.setBorder(new EmptyBorder(0, 0, 0, 0));
      var3.add(var4, "Center");
      this.add(var3, "Center");
   }
}