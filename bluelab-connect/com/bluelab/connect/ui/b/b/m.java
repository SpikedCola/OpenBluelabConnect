package bluelab.connect.ui.b.b;

final class m implements Runnable {
   // $FF: synthetic field
   private k a;

   m(k var1) {
      this.a = var1;
      super();
   }

   public final void run() {
      this.a.remoteDev.copyNameAndSettingsToAllPeripods(k.a(this.a));
      this.a.b((String)"Pod settings written");
      bluelab.connect.GoogleAnalytics.GA_tracker_Sender.GetInstance().reportEvent("event", "DK_PodSettingSuccess");
      this.a.f.setEnabled(true);
      k.a(this.a, k.a(this.a.remoteDev));
   }
}