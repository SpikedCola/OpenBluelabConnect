package bluelab.connect.c.a;

public final class ProControllerMonthDate extends MonthDate {
   private int e;

   public ProControllerMonthDate() {
      this(new CurrentMonthDate());
   }

   private ProControllerMonthDate(Interface_Int var1) {
      super(var1);
   }

   public final void b(int var1) {
      this.c();
      if (this.e == -1) {
         this.e = var1;
      } else {
         int var2 = var1 - this.e;
         this.e = var1;
         this.c += var2;
         long var3 = (long)(var2 * this.a);
         this.b += var3;
      }
   }

   public final void init() {
      super.init();
      this.e = -1;
   }
}