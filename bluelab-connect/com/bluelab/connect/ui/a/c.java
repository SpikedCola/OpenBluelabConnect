package bluelab.connect.ui.a;

import bluelab.connect.Connect;
import bluelab.connect.ui.laf.RoundedButtonUI;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public final class c extends JPanel {
   private bluelab.connect.d.BluelabDevice a;
   private bluelab.connect.ui.g b;
   private bluelab.connect.ui.j c;

   public c(bluelab.connect.d.BluelabDevice var1) {
      this.a = var1;
      this.setBackground(bluelab.connect.ui.BluelabColour.o);
      this.setLayout(new BorderLayout(0, 0));
      JPanel var3;
      (var3 = new JPanel(new BorderLayout(0, 0))).setBorder(new EmptyBorder(20, 15, 10, 15));
      var3.setOpaque(false);
      var3.setPreferredSize(new Dimension(100, 100));
      JScrollPane var2;
      (var2 = new JScrollPane()).setOpaque(false);
      var2.getViewport().setOpaque(false);
      var2.setViewportView(var3);
      var2.setBorder(new EmptyBorder(0, 0, 0, 0));
      this.add(var2, "Center");
      JTextArea var5;
      (var5 = new JTextArea("Your feedback is important to us so that we can continue to develop and improve Connect.\n\nPlease let us know of any bugs you experience, what we can do to improve and also what you love about Connect.\n\nWe may not respond directly to all comments, but we will be reviewing all the feedback. If we need further information we will contact you directly. Thanks!\n")).setOpaque(false);
      var5.setLineWrap(true);
      var5.setWrapStyleWord(true);
      var5.setEditable(false);
      var3.add(var5, "North");
      this.b = new bluelab.connect.ui.g("Write some feedback...");
      var3.add(new JScrollPane(this.b), "Center");
      JPanel var6;
      (var6 = new JPanel()).setLayout(new BoxLayout(var6, 1));
      var6.setBorder(new EmptyBorder(5, 5, 5, 5));
      var6.setOpaque(false);
      var3.add(var6, "South");
      JButton var4;
      (var4 = new JButton("Send feedback")).setUI(new RoundedButtonUI());
      var4.addActionListener(this::a);
      var4.setAlignmentX(0.5F);
      var6.add(var4);
      this.c = new bluelab.connect.ui.j("West");
      this.add(this.c, "South");
   }

   private void a(ActionEvent var1) {
      String var2;
      String var3;
      if ((var3 = this.b.getText()).length() > 0) {
         var2 = String.format("%s (Desktop) %s", Connect.GetTitle(), Connect.GetVersion());
         bluelab.connect.Web.FeedbackToken var4;
         (var4 = new bluelab.connect.Web.FeedbackToken(var3, var2, this.a.getSettingFileModel(), this.a.getRemoteDeviceCOWList())).setCallbacks(this::a, this::a);
         (new Thread(var4)).start();
         this.c.a("Sending feedback...");
      } else {
         var2 = "Please enter some feedback.";
         JOptionPane.showMessageDialog(SwingUtilities.getRoot(this), "<html>" + var2 + "</html>", "Feedback error", 0, UIManager.getIcon("OptionPane.errorIcon"));
      }
   }

   protected final void a(int var1, Object var2) {
      EventQueue.invokeLater(new d(this));
   }

   protected final void a(int var1, String var2) {
      Component var3 = SwingUtilities.getRoot(this);
      EventQueue.invokeLater(new e(this, var1, var2, var3));
   }

   // $FF: synthetic method
   static bluelab.connect.ui.g a(c var0) {
      return var0.b;
   }

   // $FF: synthetic method
   static bluelab.connect.ui.j b(c var0) {
      return var0.c;
   }
}