package bluelab.connect.f;

import bluelab.connect.j.Interface_GetValue;

public enum b implements bluelab.connect.j.Interface_GetText, Interface_GetValue {
   ONE_MIN(60, "1 minute"),
   FIVE_MIN(300, "5 minutes"),
   TEN_MIN(600, "10 minutes"),
   FIFTEEN_MIN(900, "15 minutes"),
   THIRTY_MIN(1800, "30 minutes"),
   ONE_HOUR(3600, "1 hour");

   private final int value;
   private final String text;
   private static b[] cachedValues = null;

   private b(int var3, String var4) {
      this.value = var3;
      this.text = var4;
   }

   public final int getValue() {
      return this.value;
   }

   public static b a(long var0) {
      b var2 = ONE_MIN;
      b var9 = ONE_HOUR;
      long var3 = (long)ONE_HOUR.value;
      if (cachedValues == null) {
         cachedValues = values();
      }

      b[] var8 = cachedValues;
      int var7 = cachedValues.length;

      for(int var6 = 0; var6 < var7; ++var6) {
         b var5;
         long var11;
         if ((var11 = Math.abs((long)(var5 = var8[var6]).value - var0)) < var3) {
            var2 = var5;
            var3 = var11;
         }
      }

      return var2;
   }

   public final String getText() {
      return this.text;
   }

   public final String toString() {
      return this.text;
   }

   public static b b() {
      return ONE_MIN;
   }
}