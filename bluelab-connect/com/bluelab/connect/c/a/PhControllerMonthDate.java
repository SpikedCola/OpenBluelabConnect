package bluelab.connect.c.a;

public final class PhControllerMonthDate extends MonthDate {
   private long e;
   private long f;

   public PhControllerMonthDate() {
      this(new CurrentMonthDate());
   }

   private PhControllerMonthDate(Interface_Int var1) {
      super(var1);
   }

   public final void setOnTime(long var1) {
      this.c();
      if (this.e == -1L) {
         this.e = var1;
         this.f = var1;
      } else {
         long var3 = var1 - this.e;
         this.e = var1;
         this.b += var3;
         int var5;
         if ((var5 = (int)((var1 - this.f) / (long)this.a)) > 0) {
            this.c += var5;
            this.f = var1;
         }

      }
   }

   public final void init() {
      super.init();
      this.e = -1L;
      this.f = -1L;
   }
}