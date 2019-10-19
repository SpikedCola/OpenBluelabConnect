package bluelab.connect.c.d;

import java.util.Arrays;

public final class k {
   protected Enum_MemoryType a;
   protected long b;
   protected byte[] c;
   private byte d;

   public k(l var1) {
      this.a = var1.a;
      this.b = var1.b;
      this.c = new byte[var1.c];
      Arrays.fill(this.c, var1.d);
      this.d = var1.d;
   }

   public final int a() {
      return this.c.length;
   }

   public final void a(byte[] var1, long var2) {
      if (!this.b(var2, var1.length)) {
         throw new ArrayIndexOutOfBoundsException("Address out of range.");
      } else {
         System.arraycopy(var1, 0, this.c, (int)(var2 - this.b), var1.length);
      }
   }

   public final void a(byte[] var1, long var2, int var4) {
      if (!this.b(var2, var4)) {
         throw new ArrayIndexOutOfBoundsException("Address out of range.");
      } else {
         System.arraycopy(this.c, (int)(var2 - this.b), var1, 0, var4);
      }
   }

   public final boolean a(long var1, int var3) {
      if (!this.b(var1, var3)) {
         throw new ArrayIndexOutOfBoundsException("Address out of range.");
      } else {
         int var4;
         for(int var2 = var4 = (int)(var1 - this.b); var2 < var4 + var3; ++var2) {
            if (this.c[var2] != this.d) {
               return false;
            }
         }

         return true;
      }
   }

   public final boolean b(long var1, int var3) {
      return var1 >= this.b && var1 + (long)var3 <= this.b();
   }

   public final long b() {
      return this.b + (long)this.c.length;
   }
}