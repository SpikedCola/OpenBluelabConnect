package bluelab.connect.c.d;

import com.digi.xbee.api.exceptions.TimeoutException;
import com.digi.xbee.api.exceptions.XBeeException;
import java.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class s {
   private static Logger a = LoggerFactory.getLogger(s.class);
   private static int b = 2000;
   private static int c = 4000;
   private static int d = 20000;
   private static int e = 120000;

   public final byte[] a(Interface_Send var1, Interface_Send var2) throws TimeoutException {
      try {
         return var1.send();
      } catch (XBeeException var6) {
         a.error("Initial send failed, waiting for device to respond: {}", var6.toString());
         Interface_Send var3 = var2;
         Instant var7 = Instant.now().plusMillis((long)e);
         int var4 = b;

         while(true) {
            try {
               if (!Instant.now().isBefore(var7)) {
                  break;
               }

               a.info("Couldn't talk to device, waiting [{}]", var4);
               Thread.sleep((long)var4);
               a.info("Trying to re-establish comms with device");
               if (!a(var3)) {
                  if ((var4 += c) > d) {
                     var4 = d;
                  }
                  continue;
               }
            } catch (InterruptedException var5) {
               a.error("Sleep interrupted while waiting to retry: {}", var5.toString());
               break;
            }

            return var1.send();
         }

         throw new TimeoutException("Failed to recover device communication");
      }
   }

   private static boolean a(Interface_Send var0) {
      try {
         if (var0.send() != null) {
            return true;
         }
      } catch (XBeeException var1) {
         a.error("Poking device failed: {}", var1.toString());
      }

      return false;
   }
}