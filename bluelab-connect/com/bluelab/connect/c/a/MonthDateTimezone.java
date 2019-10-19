package bluelab.connect.c.a;

import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

public final class MonthDateTimezone extends MonthDate {
   private long e;
   private Instant f;
   private Instant g;
   private boolean h;
   private ZoneId zoneId;

   public MonthDateTimezone() {
      this(ZoneId.systemDefault(), Instant.now());
   }

   private MonthDateTimezone(ZoneId var1, Instant var2) {
      super((Interface_Int)null);
      this.zoneId = var1;
      this.f = var2;
      this.e = 0L;
   }

   public final Instant e() {
      return this.g;
   }

   public final long f() {
      return this.e;
   }

   public final boolean a(long var1) {
      return var1 > this.e;
   }

   public final boolean a(Instant var1, long var2, long id, boolean on) {
      Instant var11 = var1.atZone(this.zoneId).truncatedTo(ChronoUnit.DAYS).toInstant();
      Instant var12 = this.f.atZone(this.zoneId).truncatedTo(ChronoUnit.DAYS).toInstant();
      if (var11.isAfter(var12)) {
         this.initAt(var11);
      }

      boolean var7 = false;
      var1 = this.e > 0L && this.g != null && !on ? this.g.plusSeconds(this.b(id)) : var1.minusSeconds(var2 - id);
      if (this.a(id) && var1.isAfter(this.f) && (var7 = on ^ this.h)) {
         if (!on) {
            ++this.c;
            this.b += this.b(id);
         }

         this.e = id;
         this.g = var1;
         this.f = var1;
         this.h = on;
      }

      return var7;
   }

   private long b(long var1) {
      return this.a(var1) ? var1 - this.e : 0L;
   }

   public final void init() {
      this.initAt(Instant.now());
   }

   private void initAt(Instant var1) {
      this.c = 0;
      this.b = 0L;
      this.currentMonthDateInt = 0;
      this.e = 0L;
      this.f = var1;
      this.h = false;
      this.g = null;
   }
}