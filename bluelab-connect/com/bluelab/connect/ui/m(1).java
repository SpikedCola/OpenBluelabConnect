package bluelab.connect.ui;

import java.util.List;

final class m implements Runnable {
   // $FF: synthetic field
   private BluelabFrame frame;
   // $FF: synthetic field
   private final String message;
   // $FF: synthetic field
   private final List errorList;

   m(BluelabFrame frame, String message, List errorList) {
      super();
      this.frame = frame;
      this.message = message;
      this.errorList = errorList;
   }

   public final void run() {
      BluelabFrame.getBluelabJDialog(this.frame).show("<html>" + this.message + "<br><br>" + String.join("<br>", this.errorList) + "<br></html>");
      BluelabFrame.h(this.frame).a();
   }
}