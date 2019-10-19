package bluelab.connect.c.d;

import bluelab.connect.c.BluelabConnectStick;
import bluelab.connect.c.BluelabRemoteXbeeDevice;
import bluelab.connect.c.c.BluelabException;
import com.digi.xbee.api.exceptions.TimeoutException;
import com.digi.xbee.api.exceptions.XBeeException;
import com.google.common.io.ByteStreams;
import com.microchip.intelhex.HexAddressRange;
import com.microchip.intelhex.HexAddressRangeList;
import com.microchip.intelhex.HexFile;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class a implements Runnable {
   static Logger logger = LoggerFactory.getLogger(a.class);
   private BluelabConnectStick connectStick;
   protected BluelabRemoteXbeeDevice bluelabRemoteDevice;
   private InputStream i;
   private boolean j;
   protected boolean c = false;
   protected int d = 0;
   protected Map<Enum_MemoryType, k> e;
   protected float f;
   protected i g;
   private j k;
   private h l;
   private EnumSet<t> m;

   protected a(BluelabConnectStick var1, BluelabRemoteXbeeDevice var2, InputStream var3, boolean var4) {
      this.connectStick = var1;
      this.bluelabRemoteDevice = var2;
      this.i = var3;
      this.j = var4;
      this.e = new LinkedHashMap();
      this.f = 0.0F;
      this.m = EnumSet.allOf(t.class);
   }

   public final void a(i var1, j var2, h var3) {
      this.g = var1;
      this.k = var2;
      this.l = var3;
   }

   public final void a(boolean var1, int var2) {
      this.c = var1;
      this.d = var2;
   }

   public void run() {
      try {
         a var1 = this;
         this.f = 0.0F;
         if (this.m.contains(t.IDENTIFY)) {
            this.a();
         }

         if (this.m.contains(t.RESTART_BOOT)) {
            try {
               a var9 = var1;
               byte[] var2 = new byte[0];
               var1.bluelabRemoteDevice.setAllDisconnected();
               var1.a("Restarting bootloader...");

               bluelab.connect.g.DeviceMessage msg;
               for(Iterator var4 = var1.bluelabRemoteDevice.getBluelabRemoteDevice().a(var1.c, (byte)var1.d).iterator(); var4.hasNext(); var2 = var9.a((bluelab.connect.g.DeviceMessage)msg, 1).GetInternalByteArray()) {
                  msg = (bluelab.connect.g.DeviceMessage)var4.next();
               }

               boolean var10000;
               if (var2.length > 0) {
                  var10000 = true;
               } else {
                  var10000 = false;
               }

               Thread.sleep(5000L);
            } catch (XBeeException var5) {
               this.a("No valid response received - already in bootloader?");
            } catch (Throwable var6) {
               throw var6;
            }
         }

         if (this.m.contains(t.IDENTIFY)) {
            this.a();
         }

         if (this.m.contains(t.QUERY)) {
            this.b();
         }

         if (this.m.contains(t.PROGRAM)) {
            this.c();
         }

         if (this.m.contains(t.VERIFY)) {
            this.d();
         }

         if (this.m.contains(t.RESTART_APP)) {
            this.e();
         }

         if (this.m.contains(t.INITIALIZE)) {
            this.f();
         }

         this.a("Completed");
         if (this.k != null) {
            this.k.onSuccess();
            return;
         }
      } catch (Throwable var7) {
         String var3 = var7.toString();
         var7.printStackTrace();
         if (this.l != null) {
            this.l.onFailure(var3);
         }

         logger.error("Firmware update error: {}", var3);
      }

   }

   protected abstract boolean a() throws TimeoutException, XBeeException;

   protected abstract void b();

   protected abstract void c() throws BluelabException2;

   protected abstract void d() throws InterruptedException, BluelabException2;

   protected abstract void e() throws TimeoutException, XBeeException, BluelabException;

   protected abstract void f() throws TimeoutException, XBeeException;

   protected final void a(String var1) {
      if (this.g != null) {
         this.g.onStatusUpdate(this.f, var1);
      }

      logger.info(var1);
   }

   protected final void g() throws Throwable {
      this.a("Reading firmware file...");

      try {
         Throwable var1 = null;

         try {
            boolean var4 = this.j;
            InputStream var3 = this.i;
            byte[] var5 = ByteStreams.toByteArray(var3);
            var3.close();
            if (var4) {
               this.a("Decrypting firmware file...");
               var5 = bluelab.connect.c.d.f.a(var5);
            }

            ByteArrayInputStream var2 = new ByteArrayInputStream(var5);

            try {
               HexAddressRange[] var6;
               int var25 = (var6 = HexAddressRangeList.getList(var2)).length;

               for(int var24 = 0; var24 < var25; ++var24) {
                  HexAddressRange var22;
                  long var9 = (var22 = var6[var24]).getStartingAddress();
                  int var23 = (int)var22.getRange();
                  boolean var7 = false;
                  var2.reset();
                  byte[] var8 = HexFile.getData(var2, var9, var23);
                  Iterator var11 = this.e.entrySet().iterator();

                  while(var11.hasNext()) {
                     k var12;
                     if (var7 = (var12 = (k)((Entry)var11.next()).getValue()).b(var9, var23)) {
                        var12.a(var8, var9);
                        logger.info("Copying {} bytes from hex file to {} starting at {}", new Object[]{var8.length, var12.a, var9});
                        break;
                     }
                  }

                  if (!var7) {
                     logger.info("Out of range: Ignoring hex file address range starting at {} with size {}", var9, var23);
                  }
               }
            } finally {
               var2.close();
            }

         } catch (Throwable var20) {
            if (var1 == null) {
               var1 = var20;
            } else if (var1 != var20) {
               var1.addSuppressed(var20);
            }

            throw var1;
         }
      } catch (Throwable var21) {
         throw var21;
      }
   }

   protected final bluelab.connect.g.DeviceMessage a(bluelab.connect.g.DeviceMessage message, int attempts) throws TimeoutException, XBeeException {
      //Object var3 = BluelabConnectStick.readMutex;
      synchronized(BluelabConnectStick.readMutex) {
         message = this.connectStick.readMessageFromDevice(this.bluelabRemoteDevice, message, attempts);
         return message;
      }
   }

   protected final byte[] sendReceiveData(byte[] data, int attempts)  throws TimeoutException, XBeeException{
      //Object var3 = BluelabConnectStick.readMutex;
      synchronized(BluelabConnectStick.readMutex) {
         data = this.connectStick.sendReceiveData(this.bluelabRemoteDevice, data, attempts);
         return data;
      }
   }
}