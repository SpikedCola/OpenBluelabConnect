package bluelab.connect.c.a;

public abstract class MonthDate {
   protected int a;
   protected long b;
   protected int c;
   protected int currentMonthDateInt;
   private Interface_Int currentMonthDate;

   protected MonthDate() {
      this(new CurrentMonthDate());
   }

   protected MonthDate(Interface_Int CurrentMonthDate) {
      this.a = 1;
      this.currentMonthDate = CurrentMonthDate;
      this.init();
   }

   public final int getC() {
      return this.c;
   }

   public final long getB() {
      return this.b;
   }

   public final void setOnTime(int a) {
      if (a <= 0) {
         this.a = 1;
      } else {
         this.a = a;
      }
   }

   protected final void c() {
      if (this.currentMonthDate.getInt() != this.currentMonthDateInt) {
         this.init();
      }

   }

   public void init() {
      this.c = 0;
      this.b = 0L;
      this.currentMonthDateInt = this.currentMonthDate.getInt();
   }
}