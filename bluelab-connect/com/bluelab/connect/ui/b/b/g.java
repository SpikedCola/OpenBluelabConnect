package bluelab.connect.ui.b.b;

final class g implements Runnable {
   // $FF: synthetic field
   private b a;

   g(b var1) {
      this.a = var1;
      super();
   }

   public final void run() {
      this.a.b("Device settings written");
      bluelab.connect.GoogleAnalytics.GA_tracker_Sender.GetInstance().reportEvent("event", "DK_DeviceSettingSuccess");
      this.a.f.setEnabled(true);
   }
}