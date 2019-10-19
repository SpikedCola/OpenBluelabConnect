package bluelab.connect;

import bluelab.connect.d.WeirdEncoder;
import bluelab.connect.l.UpdateChecker;
import bluelab.connect.model.SettingFileModel;
import bluelab.connect.ui.e;
import bluelab.connect.ui.BluelabFrame;
import com.google.gson.JsonSyntaxException;
import java.awt.Component;
import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import bluelab.connect.d.Interface_UpdateDevice;

public class Connect {
   private static final String userDataDirectory;
   public static final String logDirectory;
   private static SettingFileModel settings;
   private BluelabFrame frame;
   private bluelab.connect.d.BluelabDevice device;
   private Thread thread;
   private UpdateChecker updateChecker;
   private final ScheduledExecutorService scheduledExecutor = Executors.newScheduledThreadPool(1);

   static {
      String appDataDir = "";
      String osName;
      if ((osName = System.getProperty("os.name").toLowerCase()).contains("win")) {
         appDataDir = System.getenv("APPDATA");
      } else if (osName.contains("mac")) {
         appDataDir = System.getProperty("user.home") + File.separator + "Library" + File.separator + "Application Support";
      } else if (osName.contains("linux") && (appDataDir = System.getenv("XDG_DATA_HOME")) == null) {
         appDataDir = System.getProperty("user.home") + File.separator + ".local" + File.separator + "share";
      }

      String userDataDir = null;
      if ((new File(appDataDir)).exists()) {
         userDataDir = appDataDir + File.separator + "Bluelab" + File.separator + "Connect";
         if (!(new File(userDataDir)).exists() && !(new File(userDataDir)).mkdirs()) {
            appDataDir = GetTitle() + " could not create a valid user data directory:\n" + userDataDir;
            JOptionPane.showMessageDialog((Component)null, appDataDir, "Fatal error", 0);
            bluelab.connect.GoogleAnalytics.GA_tracker_Sender.GetInstance().reportException(appDataDir, true);
            System.exit(-1);
         }
      } else {
         appDataDir = GetTitle() + " could not detect a valid user data directory:\n" + appDataDir;
         JOptionPane.showMessageDialog((Component)null, appDataDir, "Fatal error", 0);
         bluelab.connect.GoogleAnalytics.GA_tracker_Sender.GetInstance().reportException(appDataDir, true);
         System.exit(-1);
      }

      userDataDirectory = userDataDir;
      logDirectory = userDataDirectory + File.separator + "logs";
   }

   public static void main(String[] var0) {
      Thread.setDefaultUncaughtExceptionHandler(new bluelab.connect.e.a());
      EventQueue.invokeLater(new BluelabRunner());
   }

   public static String GetTitle() {
      return String.format("%s %s", "Bluelab", "Connect");
   }

   public static String GetVersion() {
      return "2.1.0.9";
   }

   public static String GetServiceUrl() {
      return settings != null && settings.serviceUrl != null ? settings.serviceUrl : "https://www.bluelab-connect.com/v2";
   }

   public static String GetUpdateUrl() {
      return settings != null && settings.updateUrl != null ? settings.updateUrl : "https://download.bluelab-connect.com/download/updates.xml";
   }

   public static String GetUserDataDirectory() {
      return userDataDirectory;
   }

   public Connect() {
      this.device = new bluelab.connect.d.BluelabDevice(settings);
      this.frame = new BluelabFrame(this, this.device);
      this.frame.addWindowListener(new BluelabWindow(this));
      this.device.setCallbackInterface((Interface_UpdateDevice)this.frame);
      this.device.a((bluelab.connect.l.Interface_FirmwareUpdateNotify)this.frame);
      this.thread = new Thread(this.device);
      this.thread.start();
      this.updateChecker = new UpdateChecker();
      this.updateChecker.SetInvokeLaterInterface(this.frame);
      this.scheduledExecutor.scheduleWithFixedDelay(this.updateChecker::CheckForUpdates, 0L, 1L, TimeUnit.DAYS);
   }

   public final void shutdown() {
      this.scheduledExecutor.shutdown();
      if (this.device != null) {
         try {
            this.device.clearCallbackInterface();
            this.device.i();
            this.thread.join();
            return;
         } catch (InterruptedException var2) {
            Thread.currentThread().interrupt();
            bluelab.connect.d.WeirdEncoder.ReportException((Throwable)var2);
         }
      }

   }

   // $FF: synthetic method
   static void g() 
   throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException
   {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      UIManager.put("Button.font", bluelab.connect.ui.e.h);
      UIManager.put("CheckBox.font", bluelab.connect.ui.e.h);
      UIManager.put("ComboBox.font", bluelab.connect.ui.e.g);
      UIManager.put("Label.font", bluelab.connect.ui.e.h);
      UIManager.put("PasswordField.font", bluelab.connect.ui.e.h);
      UIManager.put("PopupMenu.font", bluelab.connect.ui.e.h);
      UIManager.put("ProgressBar.font", bluelab.connect.ui.e.h);
      UIManager.put("RadioButton.font", bluelab.connect.ui.e.h);
      UIManager.put("TextArea.font", bluelab.connect.ui.e.h);
      UIManager.put("TextField.font", bluelab.connect.ui.e.h);
      UIManager.put("ToggleButton.font", bluelab.connect.ui.e.h);
      UIManager.put("ToolTip.font", bluelab.connect.ui.e.i);
   }

   // $FF: synthetic method
   static void LoadSettings() throws Throwable {
      String var1;
      try {
         settings = SettingFileModel.load();
      } catch (JsonSyntaxException var2) {
         var1 = String.format("<html>Detected corrupted user settings file, using default settings.<br>%s</html>", var2.toString());
         JOptionPane.showMessageDialog((Component)null, var1, "Settings file error", 0);
         settings = new SettingFileModel();
         bluelab.connect.d.WeirdEncoder.ReportException((Throwable)var2);
      } catch (IOException var3) {
         var1 = String.format("<html>Settings file access error (%s).<br>%s</html>", SettingFileModel.SETTINGS_FILE_NAME, var3.toString());
         JOptionPane.showMessageDialog((Component)null, var1, "Settings file error", 0);
         bluelab.connect.d.WeirdEncoder.ReportException((Throwable)var3);
         System.exit(-1);
      }
   }

   // $FF: synthetic method
   static SettingFileModel GetSettings() {
      return settings;
   }

   // $FF: synthetic method
   static BluelabFrame GetFrame(Connect var0) {
      return var0.frame;
   }
}