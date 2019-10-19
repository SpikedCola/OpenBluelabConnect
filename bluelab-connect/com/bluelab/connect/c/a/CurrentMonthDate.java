package bluelab.connect.c.a;

import java.util.Calendar;
import java.util.Date;

public final class CurrentMonthDate implements Interface_Int {
   public final int getInt() {
      Date var1 = new Date();
      Calendar var2;
      (var2 = Calendar.getInstance()).setTime(var1);
      return var2.get(7); // Calendar.MONTH + Calendar.DATE
   }
}