package bluelab.connect.ui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.util.HashMap;
import java.util.Map;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class G extends JPanel {
   private static Map<H, Color> a;
   private static Map<H, ImageIcon> b;
   private JTextPane c;
   private JLabel d;

   static {
      (a = new HashMap()).put(H.ONLINE, bluelab.connect.ui.BluelabColour.k);
      a.put(H.OFFLINE, bluelab.connect.ui.BluelabColour.j);
      a.put(H.ERROR, bluelab.connect.ui.BluelabColour.i);
      (b = new HashMap()).put(H.ONLINE, new ImageIcon(G.class.getResource("/resources/ok.png")));
      b.put(H.OFFLINE, new ImageIcon(G.class.getResource("/resources/offline.png")));
      b.put(H.ERROR, new ImageIcon(G.class.getResource("/resources/error.png")));
   }

   public G() {
      this.setBorder(new EmptyBorder(0, 5, 0, 0));
      this.setLayout(new FlowLayout(0));
      this.d = new JLabel();
      this.add(this.d);
      this.c = new JTextPane();
      this.c.setOpaque(false);
      this.c.setFont(e.g);
      this.c.setForeground(bluelab.connect.ui.BluelabColour.h);
      this.c.setEditable(false);
      StyledDocument var1 = this.c.getStyledDocument();
      SimpleAttributeSet var2;
      StyleConstants.setAlignment(var2 = new SimpleAttributeSet(), 0);
      var1.setParagraphAttributes(0, var1.getLength(), var2, false);
      this.add(this.c);
      this.a(H.OFFLINE, "");
   }

   public final void a(H var1, String var2) {
      if (a.containsKey(var1)) {
         this.setBackground((Color)a.get(var1));
      }

      if (b.containsKey(var1)) {
         this.d.setIcon((Icon)b.get(var1));
      }

      this.c.setText(var2);
   }
}