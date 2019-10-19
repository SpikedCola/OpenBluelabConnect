package bluelab.connect.ui.a;

final class ShowLogOutPanel implements Runnable {
   // $FF: synthetic field
   private AccountPanel panel;

   ShowLogOutPanel(AccountPanel var1) {
      super();
      this.panel = var1;
   }

   public final void run() {
      this.panel.showlogOut();
   }
}