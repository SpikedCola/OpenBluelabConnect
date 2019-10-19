package bluelab.connect.g;

import java.security.SecureRandom;

public final class BluelabSecureRandom {
   private static SecureRandom a = new SecureRandom();

   public static byte GetRandomByte() {
      byte[] var0 = new byte[1];
      a.nextBytes(var0);
      return var0[0];
   }
}