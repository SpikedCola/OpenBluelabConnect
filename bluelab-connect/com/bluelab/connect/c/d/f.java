package bluelab.connect.c.d;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public final class f {
   private static final byte[] a = new byte[]{34, 121, 41, 113, 83, 11, 80, 49, 116, 81, 79, 85, 52, 48, 108, 5};
   private static final byte[] b = new byte[]{115, 32, 99, 79, 74, 90, 112, 81, 48, 21, 76, 56, 10, 98, 13, 17};

   public static byte[] a(byte[] var0) 
   throws e {
      return a(var0, 2);
   }

   private static byte[] a(byte[] var0, int var1) 
   throws e
   {
      if (var0 == null) {
         return null;
      } else {
         SecretKeySpec var5 = new SecretKeySpec(a, "AES");
         IvParameterSpec var2 = new IvParameterSpec(b);

         try {
            Cipher var3;
            (var3 = Cipher.getInstance("AES/CBC/PKCS5Padding")).init(2, var5, var2);
            return var3.doFinal(var0);
         } catch (NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException var4) {
            throw new e(var4.toString());
         }
      }
   }
}