package bluelab.connect.ui;

import bluelab.connect.Connect;
import bluelab.connect.model.SettingFileModel;
import bluelab.connect.ui.laf.MenuButtonUI;
import bluelab.connect.ui.laf.RoundedButtonUI;
import com.install4j.api.launcher.ApplicationLauncher;
import com.install4j.api.launcher.ApplicationLauncher.Callback;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BluelabFrame extends JFrame implements bluelab.connect.d.Interface_UpdateDevice, bluelab.connect.l.Interface_FirmwareUpdateNotify, bluelab.connect.l.InvokeLaterInterface {
   static Logger logger = LoggerFactory.getLogger(BluelabFrame.class);
   private Connect connect;
   private bluelab.connect.d.BluelabDevice device;
   private BluelabPanel bluelabPanel;
   private JSplitPane splitPane;
   private JLabel pabel;
   private JPanel panel;
   private G h;
   private y i;
   private D j;
   private JPanel panel2;
   private JLabel panel3;
   private JPanel panel4;
   private bluelab.connect.ui.b.OverviewPanel n;
   private Map<String, bluelab.connect.ui.b.BluelabFrameDevice> o = new HashMap();
   private Timer p;
   private BluelabJDialog dialog;
   private String lastMessage = "";
   private int sameMessageCount = 0;
   private boolean t = false;
   private boolean u = false;

   public BluelabFrame(Connect connect, bluelab.connect.d.BluelabDevice device) {
      this.connect = connect;
      this.device = device;
      SettingFileModel settingFileModel = device.getSettingFileModel();
      this.dialog = new BluelabJDialog(this, "Error", UIManager.getIcon("OptionPane.errorIcon"));
      this.setVisible(true);
      this.setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/resources/connect_48px.png")));
      String var3 = String.format("%s %s", Connect.GetTitle(), Connect.GetVersion());
      this.setTitle(var3);
      this.setBounds(100, 100, 1200, 700);
      this.setMinimumSize(new Dimension(480, 400));
      this.setDefaultCloseOperation(2);
      JPanel panel;
      (panel = new JPanel()).setBackground(bluelab.connect.ui.BluelabColour.a);
      panel.setBorder(new EmptyBorder(5, 5, 5, 5));
      panel.setLayout(new BorderLayout(0, 0));
      this.getContentPane().add(panel);
      JPanel var4;
      (var4 = new JPanel()).setOpaque(false);
      var4.setLayout(new BorderLayout(0, 0));
      panel.add(var4, "North");
      ArrayList var5;
      (var5 = new ArrayList()).add(new v("Devices", "Devices", "/resources/devices-blue.png", "/resources/devices.png", "Device view"));
      var5.add(new v("Settings", "Settings", "/resources/settings-blue.png", "/resources/settings.png", "Application settings"));
      var5.add(new v("Feedback", "Feedback", "/resources/feedback-blue.png", "/resources/feedback.png", "User feedback"));
      if (settingFileModel.isDiagnosticsEnabled()) {
         var5.add(new v("Diagnostic", "Diagnostic", "/resources/diagnostic-blue.png", "/resources/diagnostic.png", "Diagnostic info"));
      }

      this.bluelabPanel = new BluelabPanel(new ImageIcon(this.getClass().getResource("/resources/bluelab-logo.png")), "https://www.bluelab-connect.com/go?link=bluelabhome", var5, this::c, bluelab.connect.ui.BluelabColour.p, panel.getBackground(), 2);
      var4.add(this.bluelabPanel, "North");
      BluelabButton var14;
      (var14 = new BluelabButton("/resources/help-blue.png", "https://www.bluelab-connect.com/go?link=helpcentre")).setText("Help");
      var14.setUI(new MenuButtonUI(bluelab.connect.ui.BluelabColour.p, panel.getBackground()));
      this.bluelabPanel.a().add(var14);
      JPanel var6;
      (var6 = new JPanel(new FlowLayout(1, 5, 0))).setBorder(new EmptyBorder(0, 0, 5, 0));
      var6.setOpaque(false);
      var6.setVisible(false);
      this.pabel = new JLabel();
      this.pabel.setForeground(bluelab.connect.ui.BluelabColour.h);
      this.pabel.setHorizontalAlignment(0);
      var6.add(this.pabel);
      JButton var7;
      (var7 = new JButton("Update")).setUI(new RoundedButtonUI());
      var7.addActionListener(this::a);
      var6.add(var7);
      this.panel = var6;
      var4.add(this.panel, "Center");
      (var6 = new JPanel(new BorderLayout(0, 0))).setOpaque(false);
      var6.setPreferredSize(new Dimension(440, 90));
      this.h = new G();
      var6.add(this.h, "North");
      l var15 = new l(this);
      n var11 = new n(this);
      this.i = new y(this.device, var15, var11);
      var6.add(this.i, "Center");
      this.panel4 = new JPanel(new CardLayout());
      this.panel4.setOpaque(false);
      this.panel4.setBorder(new EmptyBorder(0, 5, 0, 0));
      this.splitPane = new JSplitPane(1, var6, this.panel4);
      this.splitPane.setOneTouchExpandable(true);
      panel.add(this.splitPane, "Center");
      this.n = new bluelab.connect.ui.b.OverviewPanel(device, (bluelab.connect.c.BluelabRemoteXbeeDevice)null, bluelab.connect.ui.BluelabColour.o, bluelab.connect.ui.BluelabColour.d, true);
      this.panel4.add(this.n, "Devices");
      this.j = new D(device.getCSVWriter(), device.getSettingFileModel());
      this.j.a((bluelab.connect.Web.Interface_OnSuccessFailure)device);
      this.j.a(this.n::a);
      this.panel4.add(this.j, "Settings");
      bluelab.connect.ui.a.c var12 = new bluelab.connect.ui.a.c(device);
      this.panel4.add(var12, "Feedback");
      if (settingFileModel.isDiagnosticsEnabled()) {
         bluelab.connect.ui.c.a var9 = new bluelab.connect.ui.c.a(device);
         this.panel4.add(var9, "Diagnostic");
      }

      var6 = new JPanel(new FlowLayout(0));
      this.panel3 = new JLabel();
      this.panel3.setFont(bluelab.connect.ui.e.i);
      var6.add(this.panel3);
      SettingFileModel var16 = this.device.getSettingFileModel();
      ArrayList var13 = new ArrayList();
      if (var16.serviceUrl != null) {
         var13.add("Service URL: " + var16.serviceUrl);
      }

      if (var16.updateUrl != null) {
         var13.add("Update URL: " + var16.updateUrl);
      }

      this.panel3.setText(String.join(", ", var13));
      this.panel2 = var6;
      this.panel2.setVisible(!this.panel3.getText().isEmpty());
      panel.add(this.panel2, "South");
      this.bluelabPanel.a("Devices");
      this.h.a(H.OFFLINE, "Connecting...");
      this.p = new Timer(500, this::b);
      this.p.start();
   }

   private void b(ActionEvent var1) {
      try {
         this.n.a(false);
         Iterator var2 = this.o.values().iterator();

         while(var2.hasNext()) {
            ((bluelab.connect.ui.b.BluelabFrameDevice)var2.next()).a(false);
         }

         this.i.a();
         if (this.t) {
            ArrayList var3 = new ArrayList();
            List var9;
            int var4 = bluelab.connect.d.WeirdEncoder.FilterCount(var9 = this.device.getRemoteDeviceCOWList(), (var0) -> {
               return true;
            });
            int var5 = bluelab.connect.d.WeirdEncoder.FilterCount(var9, (var0) -> {
               return var0.anyControlAtHighOrLow();
            });
            int var6 = bluelab.connect.d.WeirdEncoder.FilterCount(var9, (var0) -> {
               return var0.anyLockoutListNotEmpty();
            });
            int var7 = bluelab.connect.d.WeirdEncoder.FilterCount(var9, (var0) -> {
               return var0.updateGetDevicePresence().equals(bluelab.connect.c.Enum_DevicePresence.OFFLINE);
            });
            int var10 = bluelab.connect.d.WeirdEncoder.FilterCount(var9, (var0) -> {
               return var0.podChainChangeListNotEmpty();
            });
            if (var5 > 0) {
               var3.add(String.format("%s device(s) alarmed", var5));
            }

            if (var6 > 0) {
               var3.add(String.format("%s device(s) locked out", var6));
            }

            if (var7 > 0) {
               var3.add(String.format("%s device(s) off-line", var7));
            }

            if (var10 > 0) {
               var3.add(String.format("%s device(s) warning", var10));
            }

            if (var3.isEmpty()) {
               if (var4 > 0) {
                  var3.add("System OK");
               } else {
                  var3.add("No devices - add your first Bluelab Connect device below");
               }
            }

            H var11;
            if (var6 + var5 + var10 > 0) {
               var11 = H.ERROR;
            } else if (var7 > 0) {
               var11 = H.OFFLINE;
            } else {
               var11 = H.ONLINE;
            }

            this.h.a(var11, String.join(", ", var3));
            return;
         }
      } catch (Throwable var8) {
         bluelab.connect.d.WeirdEncoder.ReportException(var8);
         logger.error("Timer update data error: {}", var8.toString());
      }

   }

   public void dispose() {
      this.setTitle("Stopping engine...");
      this.connect.shutdown();
      this.p.stop();
      super.dispose();
   }

   public final void updateDevices(List<bluelab.connect.c.BluelabRemoteXbeeDevice> var1) {
      EventQueue.invokeLater(new o(this, var1));
   }

   public final void updateDevice(bluelab.connect.c.BluelabRemoteXbeeDevice var1) {
      EventQueue.invokeLater(new p(this, var1));
   }

   public final void update() {
      this.t = false;
      EventQueue.invokeLater(new q(this));
   }

   public final void shutdown() {
      this.t = false;
   }

   public final void foundConnectStick() {
      this.t = true;
      EventQueue.invokeLater(new r(this));
   }

   public final void d() {
      this.t = false;
      EventQueue.invokeLater(new s(this));
   }

   private void c(ActionEvent var1) {
      String var3 = ((AbstractButton)var1.getSource()).getActionCommand();
      ((CardLayout)this.panel4.getLayout()).show(this.panel4, var3);
      if (var3.equals("Settings")) {
         this.j.a();
      }

      if (this.splitPane.getDividerLocation() > this.splitPane.getMaximumDividerLocation()) {
         ((JButton)((BasicSplitPaneUI)this.splitPane.getUI()).getDivider().getComponent(0)).doClick();
      }

      bluelab.connect.GoogleAnalytics.GA_tracker_Sender.GetInstance().reportScreenView("DK_" + var3);
   }

   public final void InvokeLater(String var1, String rootComponent) {
      EventQueue.invokeLater(new t(this, var1, rootComponent));
   }

   public final void notifyFirmwareUpdates(List<bluelab.connect.l.DeviceVersion> var1) {
      Component rootComponent = SwingUtilities.getRoot(this);
      EventQueue.invokeLater(new u(this, var1, rootComponent));
   }

   public void Update(ActionEvent var1) {
      try {
         logger.info("Updating application...");
         ApplicationLauncher.launchApplication("updater", new String[]{"-q", "-VupdaterUpdatesUrl=" + bluelab.connect.l.UpdateChecker.a, "-splash", String.format("Updating %s...", Connect.GetTitle())}, false, (Callback)null);
      } catch (IOException var2) {
         bluelab.connect.d.WeirdEncoder.ReportException((Throwable)var2);
         logger.error("Exception: {}", var2.toString());
      }
   }

   public final void errorList(boolean isErrorCode, String message, List<String> errorList) {
      if (message.equals(this.lastMessage)) {
         ++this.sameMessageCount;
      } else {
         this.sameMessageCount = 0;
      }

      if (isErrorCode || this.sameMessageCount > 2) {
         EventQueue.invokeLater(new m(this, message, errorList));
         this.sameMessageCount = 0;
      }

      this.lastMessage = message;
   }

   // $FF: synthetic method
   static void a(BluelabFrame var0, MouseEvent var1) {
      JList var2;
      bluelab.connect.c.BluelabRemoteXbeeDevice var3;
      if ((var3 = (bluelab.connect.c.BluelabRemoteXbeeDevice)(var2 = (JList)var1.getComponent()).getSelectedValue()) != null) {
         int var4 = var2.getSelectedIndex();
         Rectangle var6;
         (var6 = var2.getCellBounds(var4, var4)).setSize(20, 20);
         if (var6.contains(var1.getPoint())) {
            if (var0.o.containsKey(var3.getKeyCode())) {
               ((Frame)var0.o.get(var3.getKeyCode())).dispose();
               var0.o.remove(var3.getKeyCode());
            }

            var0.device.c(var3.getKeyCode());
            return;
         }

         if ((var3.getDeviceType().equals(bluelab.connect.c.Enum_DeviceType.PH_CONTROLLER) || var3.getDeviceType().equals(bluelab.connect.c.Enum_DeviceType.PRO_CONTROLLER) || var3.getDeviceType().equals(bluelab.connect.c.Enum_DeviceType.GUARDIAN) || var3.getDeviceType().equals(bluelab.connect.c.Enum_DeviceType.EXTENDER)) && var1.getClickCount() == 2) {
            bluelab.connect.ui.b.BluelabFrameDevice var5;
            if (var0.o.containsKey(var3.getKeyCode())) {
               (var5 = (bluelab.connect.ui.b.BluelabFrameDevice)var0.o.get(var3.getKeyCode())).toFront();
               var5.setVisible(true);
               var5.a(true);
               return;
            }

            (var5 = new bluelab.connect.ui.b.BluelabFrameDevice(var0.device, var3)).setVisible(true);
            var0.o.put(var3.getKeyCode(), var5);
         }
      }

   }

   // $FF: synthetic method
   static void ListSelectEvent(BluelabFrame frame, ListSelectionEvent event) {
      bluelab.connect.c.BluelabRemoteXbeeDevice remoteDev = (bluelab.connect.c.BluelabRemoteXbeeDevice)((JList)event.getSource()).getSelectedValue();
      if (!event.getValueIsAdjusting()) {
         frame.bluelabPanel.a("Devices");
         if (remoteDev != null && remoteDev.isOnlineOffline()) {
            frame.n.a(remoteDev);
            bluelab.connect.GoogleAnalytics.GA_tracker_Sender.GetInstance().reportScreenView("DK_" + remoteDev.getDeviceTypeUiText());
            return;
         }

         frame.n.a((bluelab.connect.c.BluelabRemoteXbeeDevice)null);
      }

   }

   // $FF: synthetic method
   static y a(BluelabFrame var0) {
      return var0.i;
   }

   // $FF: synthetic method
   static bluelab.connect.ui.b.OverviewPanel b(BluelabFrame var0) {
      return var0.n;
   }

   // $FF: synthetic method
   static G c(BluelabFrame var0) {
      return var0.h;
   }

   // $FF: synthetic method
   static JLabel d(BluelabFrame var0) {
      return var0.pabel;
   }

   // $FF: synthetic method
   static JPanel e(BluelabFrame var0) {
      return var0.panel;
   }

   // $FF: synthetic method
   static boolean f(BluelabFrame var0) {
      return var0.u;
   }

   // $FF: synthetic method
   static void a(BluelabFrame var0, boolean var1) {
      var0.u = var1;
   }

   // $FF: synthetic method
   static BluelabJDialog getBluelabJDialog(BluelabFrame frame) {
      return frame.dialog;
   }

   // $FF: synthetic method
   static D h(BluelabFrame var0) {
      return var0.j;
   }
}