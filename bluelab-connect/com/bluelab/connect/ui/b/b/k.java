package bluelab.connect.ui.b.b;

import bluelab.connect.Connect;
import bluelab.connect.model.PodChainFileModel;
import bluelab.connect.ui.I;
import bluelab.connect.ui.Helpers;
import bluelab.connect.ui.laf.RoundedButtonUI;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public final class k extends b {
   private I h;
   private t i;
   private boolean j = false;
   private PodChainFileModel k;
   private JFileChooser l = new JFileChooser();
   private FileFilter m = new FileNameExtensionFilter(String.format("Pod settings file (*.%s)", "bps"), new String[]{"bps"});

   public k(bluelab.connect.d.BluelabDevice var1, bluelab.connect.c.BluelabRemoteXbeeDevice var2) {
      super(var1, var2);
   }

   protected final void a(int var1) {
      a(new w("Nutrient Dosing", bluelab.connect.ui.e.e), this.d, this.c, var1);
      this.h = new I("Enabled", "Disabled", true);
      this.h.addActionListener(this::e);
      Helpers.a(this.h, 90);
      a("Multi-part nutrient", bluelab.connect.ui.e.g, this.h, this.d, this.c, var1 + 1);
      a(a((String)"Enables each nutrient pump to be set to dose proportionally. When disabled, all nutrient pumps dose equally."), this.d, this.c, var1 + 1);
      this.i = new t(this.h.isSelected());
      a(this.i, this.d, this.c, var1 + 1);
      JPanel var2;
      (var2 = new JPanel()).setOpaque(false);
      JButton var3;
      (var3 = new JButton("Save to file...")).setUI(new RoundedButtonUI(bluelab.connect.ui.Enum_RoundedButtonType.ALTERNATIVE));
      var3.setToolTipText("Save ratios to file");
      var3.addActionListener(this::c);
      var2.add(var3);
      (var3 = new JButton("Load from file...")).setUI(new RoundedButtonUI(bluelab.connect.ui.Enum_RoundedButtonType.ALTERNATIVE));
      var3.setToolTipText("Load ratios from file");
      var3.addActionListener(this::d);
      var2.add(var3);
      a(var2, this.d, this.c, var1 + 1);
   }

   private void c(ActionEvent var1) {
      this.l.setDialogTitle("Save settings to file");
      this.l.setApproveButtonText("Save");
      this.l.setFileSelectionMode(0);
      this.l.setAcceptAllFileFilterUsed(true);
      this.l.setFileFilter(this.m);
      this.l.addChoosableFileFilter(this.m);
      if (this.l.showSaveDialog(this) == 0) {
         String var2;
         if (!(var2 = this.l.getSelectedFile().getAbsolutePath()).endsWith(".bps")) {
            var2 = var2 + ".bps";
         }

         this.d(var2);
      }

   }

   private void d(ActionEvent var1) {
      this.l.setDialogTitle("Load settings from file");
      this.l.setApproveButtonText("Load");
      this.l.setFileSelectionMode(0);
      this.l.setAcceptAllFileFilterUsed(true);
      this.l.setFileFilter(this.m);
      this.l.addChoosableFileFilter(this.m);
      if (this.l.showOpenDialog(this) == 0) {
         String var18 = this.l.getSelectedFile().getAbsolutePath();
         String var2 = var18;
         k var19 = this;
         Path var20 = Paths.get(var2);

         try {
            Throwable var3 = null;

            try {
               JsonReader var21 = new JsonReader(Files.newBufferedReader(var20, StandardCharsets.UTF_8));

               try {
                  PodChainFileModel var4 = (PodChainFileModel)(new Gson()).fromJson(var21, PodChainFileModel.class);
                  int var7 = 0;
                  int var6 = var4.getPumpCount();
                  int var8 = var19.remoteDev.getTotalPumpCount();
                  if (var6 != var8) {
                     String var22 = String.format("<html>Loaded file contains %d pumps, but your system currently has %d pumps.<br><br>Data import is possible, but please check your settings before applying them.<br>Proceed with import?</html>", var6, var8);
                     bluelab.connect.GoogleAnalytics.GA_tracker_Sender.GetInstance().reportEvent("event", "DK_PodLoadMismatch");
                     var7 = JOptionPane.showConfirmDialog(SwingUtilities.getRoot(var19), var22, "Device settings warning", 2, 3);
                  }

                  if (var7 == 0) {
                     var19.b(true);
                     var19.i.a(var19.c());
                     var19.i.a(var4, true);
                     var19.e.setText("");
                  }
               } finally {
                  var21.close();
               }

               return;
            } catch (Throwable var16) {
               if (var3 == null) {
                  var3 = var16;
               } else if (var3 != var16) {
                  var3.addSuppressed(var16);
               }

               throw var3;
            }
         } catch (IOException | JsonSyntaxException var17) {
            bluelab.connect.GoogleAnalytics.GA_tracker_Sender.GetInstance().reportEvent("event", "DK_PodInvalidFile");
            JOptionPane.showMessageDialog(SwingUtilities.getRoot(this), "Pod settings could not be loaded; something is wrong with the selected file.", "Device settings error", 0);
         }
      }

   }

   private void b(boolean var1) {
      this.h.setSelected(true);
      this.i.a(true);
   }

   private void d(String param1) {
      // $FF: Couldn't be decompiled
   }

   private void e(ActionEvent var1) {
      this.i.a(this.h.isSelected());
   }

   public final void a(boolean var1) {
      if (var1) {
         this.a((ActionEvent)null);
      } else if (this.remoteDev.changeTypeListContains(bluelab.connect.c.Enum_ChangeType.POD_STATUS)) {
         this.j = true;
         this.e.setText("Obtaining new pod settings...");
      } else {
         if (this.j && !this.remoteDev.changeTypeListContains(bluelab.connect.c.Enum_ChangeType.POD_SETTING)) {
            this.e.setText("New pod settings are available. Refresh to view it.");
            this.j = false;
            if (this.remoteDev.getPeripodWithPumpsListSize() > 0 && this.i.a().getPodCount() == 0) {
               this.a((ActionEvent)null);
            }
         }

      }
   }

   protected final void a(ActionEvent var1) {
      this.b(true);
      this.i.a(this.c());
      if (this.e != null) {
         this.b((String)"Pod settings refreshed");
      }

      if (!this.remoteDev.hasPeripod()) {
         Component var2 = SwingUtilities.getRoot(this);
         EventQueue.invokeLater(new l(this, var2));
      }

   }

   protected final void b(ActionEvent var1) {
      int var2 = this.remoteDev.getPeripodWithPumpsListSize();
      PodChainFileModel var3;
      int var4 = (var3 = this.i.a()).getPodCount();
      String var5 = "";
      boolean var7;
      if (!this.remoteDev.getMode().equals(bluelab.connect.c.Enum_Mode.MONITOR)) {
         var5 = "<html>Settings could not be applied.<br>Device needs to be in monitor mode.</html>";
         var7 = false;
      } else if (var2 != var4) {
         var5 = String.format("<html>Settings could not be applied.<br>Settings contain %d pods, but your system currently has %d pods.<br>Please check your hardware to ensure all pods are connected and powered up,<br>and your settings to ensure they match your system.</html>", var4, var2);
         var7 = false;
         bluelab.connect.GoogleAnalytics.GA_tracker_Sender.GetInstance().reportEvent("event", "DK_PodApplyMismatch");
      } else {
         if (!var3.pumpFunctionEquals(this.c())) {
            var5 = "<html>Settings will be applied, but a pump function has changed.<br>Please ensure that your hardware is correctly configured.<html>";
         }

         var7 = true;
      }

      if (!var5.isEmpty()) {
         JOptionPane.showMessageDialog(SwingUtilities.getRoot(this), var5, "Device settings warning", 2);
      }

      if (var7) {
         this.k = this.i.b();
         bluelab.connect.c.c.h var6;
         (var6 = new bluelab.connect.c.c.h(this.dev.getXbeeDevice(), this.remoteDev, this.k)).setSuccessFailCallbacks(this::b, this::c);
         (new Thread(var6)).start();
         this.e.setText("Writing pod settings...");
         this.e.setForeground(bluelab.connect.ui.BluelabColour.l);
         this.f.setEnabled(false);
      }

   }

   public final void b() {
      EventQueue.invokeLater(new m(this));
   }

   private static String b(bluelab.connect.c.BluelabRemoteXbeeDevice var0) {
      return Connect.GetUserDataDirectory() + File.separator + var0.get64BitAddress() + ".bps";
   }

   public final void c(String var1) {
      Component var2 = SwingUtilities.getRoot(this);
      EventQueue.invokeLater(new n(this, var2, var1));
   }

   public final void a(Object var1) {
      if (var1 instanceof bluelab.connect.k.Enum_VolumeUnit) {
         this.i.a(var1.toString());
      }

   }

   private PodChainFileModel c() {
      String var1 = this.dev.getSettingFileModel().ratioUnit;
      PodChainFileModel var21 = new PodChainFileModel(this.remoteDev.getPeripodWithPumpsList(), var1);
      PodChainFileModel var3 = var21;
      Path var4 = Paths.get(b(this.remoteDev));

      try {
         Throwable var5 = null;

         try {
            JsonReader var22 = new JsonReader(Files.newBufferedReader(var4, StandardCharsets.UTF_8));

            try {
               PodChainFileModel var6;
               int var7 = (var6 = (PodChainFileModel)(new Gson()).fromJson(var22, PodChainFileModel.class)).getPumpCount();
               int var8 = var3.getPumpCount();
               if (var7 != var8) {
                  bluelab.connect.GoogleAnalytics.GA_tracker_Sender.GetInstance().reportEvent("event", "DK_PodLoadLastMismatch");
               }

               var3.importPumpValues(var6);
            } finally {
               var22.close();
            }
         } catch (Throwable var18) {
            if (var5 == null) {
               var5 = var18;
            } else if (var5 != var18) {
               var5.addSuppressed(var18);
            }

            throw var5;
         }
      } catch (NoSuchFileException | FileNotFoundException var19) {
         ;
      } catch (IOException | JsonSyntaxException var20) {
         bluelab.connect.GoogleAnalytics.GA_tracker_Sender.GetInstance().reportEvent("event", "DK_PodInvalidLastFile");
         JOptionPane.showMessageDialog(SwingUtilities.getRoot(this), "Last pod settings could not be imported during pod sytem model creation.", "Device settings error", 0);
      }

      return var21;
   }

   // $FF: synthetic method
   static PodChainFileModel a(k var0) {
      return var0.k;
   }

   // $FF: synthetic method
   static String a(bluelab.connect.c.BluelabRemoteXbeeDevice var0) {
      return b(var0);
   }

   // $FF: synthetic method
   static void a(k var0, String var1) {
      var0.d(var1);
   }
}