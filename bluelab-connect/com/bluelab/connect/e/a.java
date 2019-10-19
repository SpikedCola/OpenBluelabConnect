package bluelab.connect.e;

import bluelab.connect.d.WeirdEncoder;
import java.lang.Thread.UncaughtExceptionHandler;

public final class a implements UncaughtExceptionHandler {
   public final void uncaughtException(Thread var1, Throwable var2) {
      WeirdEncoder.ReportException(var2);
   }
}