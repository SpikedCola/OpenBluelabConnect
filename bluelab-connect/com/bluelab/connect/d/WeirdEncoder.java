package bluelab.connect.d;

import bluelab.connect.c.BluelabRemoteXbeeDevice;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public final class WeirdEncoder {
   public static String KeyCodeFrom64BitAddress(byte[] var0) {
       // only last 3 bytes used, added into an int.
       // int is masked, 1 nibble at a time, into 5 nibbles.
       // nibbles are rearranged, added back together, and converted to a base36 string
       // spaces replaced with zeros, then printed as 4 character string.
       
       // eg. new byte[] {(byte)0x01,(byte)0x13,(byte)0xA2,(byte)0x00,(byte)0x12,(byte)0x34,(byte)0x56,(byte)0x78} 
       //     = '95kl'
       
       // make int from last 3 bytes, [n-2][n-1][n]
      int combined = 
                 (var0[var0.length - 1] & 255) + 
                ((var0[var0.length - 2] & 255) << 8) + 
                ((var0[var0.length - 3] & 255) << 16);
      
      // rearrange nibbles 0,1,2,3,4 -> 2,0,4,1,3
      int[] nibbles = new int[5];
      nibbles[0] = (combined &     0xF) >> 0;
      nibbles[1] = (combined &    0xF0) >> 4;
      nibbles[2] = (combined &   0xF00) >> 8;
      nibbles[3] = (combined &  0xF000) >> 12;
      nibbles[4] = (combined & 0xF0000) >> 16;
      long rearranged = (long)(
              (nibbles[2] << 16) + 
              (nibbles[0] << 12) + 
              (nibbles[4] << 8)  + 
              (nibbles[1] << 4)  + 
              (nibbles[3] << 0)
      );
      // convert to base36, format as 4 characters, replace spaces with zeros
      String base36 = Long.toString(rearranged, 36);
      return String.format("%4s", base36).replace(' ', '0'); 
   }

   public static short CRC(byte[] var0, int var1, int var2) {
      short ret = 0;
      int var4 = Math.min(var0.length, var1);
      var1 = Math.min(var0.length, var1 + var2);

      for(var2 = var4; var2 < var1; ++var2) {
         for(var4 = 0; var4 < 8; ++var4) {
            boolean var5 = (var0[var2] >> 7 - var4 & 1) == 1;
            boolean var6 = (ret >> 15 & 1) == 1;
            ret = (short)(ret << 1);
            if (var6 ^ var5) {
               ret = (short)(ret ^ 4129);
            }
         }
      }

      return ret;
   }

   public static Integer TimeToInteger(String var0) {
      int var1 = 0;
      String[] var2;
      if ((var2 = var0.split(":")).length == 1) {
         var1 = Integer.parseInt(var2[0]);
      } else if (var2.length >= 3) {
         var1 = Integer.parseInt(var2[0]) * 3600 + Integer.parseInt(var2[1]) * 60 + Integer.parseInt(var2[2]);
      }

      return new Integer(var1);
   }

   public static void ReportException(Throwable ex) {
      ex.printStackTrace();
      String stackTrace = GetStackTrace(ex);
      bluelab.connect.GoogleAnalytics.GA_tracker_Sender.GetInstance().reportException(stackTrace, false);
   }

   private static String GetStackTrace(Throwable var0) {
      String var1 = "";

      try {
         StringWriter var2 = new StringWriter();
         PrintWriter var3 = new PrintWriter(var2);
         var0.printStackTrace(var3);
         byte[] var5;
         int var6 = Math.min((var5 = var2.toString().getBytes("UTF-8")).length, 500);
         var1 = new String(Arrays.copyOfRange(var5, 0, var6));
      } catch (UnsupportedEncodingException ex) {
         ex.printStackTrace();
      }

      return var1;
   }

   public static List<BluelabRemoteXbeeDevice> FilterCollect(List<BluelabRemoteXbeeDevice> var0, Predicate<? super BluelabRemoteXbeeDevice> var1) {
      return (List)var0.stream().filter(var1).collect(Collectors.toList());
   }

   public static boolean AnyMatch(List<BluelabRemoteXbeeDevice> var0, Predicate<? super BluelabRemoteXbeeDevice> var1) {
      return var0.stream().anyMatch(var1);
   }

   public static int FilterCount(List<BluelabRemoteXbeeDevice> var0, Predicate<? super BluelabRemoteXbeeDevice> var1) {
      return (int)var0.stream().filter(var1).count();
   }
}