package bluelab.connect.c.d;

import bluelab.connect.c.BluelabConnectStick;
import bluelab.connect.c.BluelabRemoteXbeeDevice;
import bluelab.connect.c.c.BluelabException;
import com.digi.xbee.api.exceptions.TimeoutException;
import com.digi.xbee.api.exceptions.XBeeException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map.Entry;

public final class c extends a {
   private boolean h = true;
   private b i;
   // $FF: synthetic field
   private static int[] j;

   public c(BluelabConnectStick var1, BluelabRemoteXbeeDevice var2, InputStream var3, boolean var4) {
      super(var1, var2, var3, var4);
   }

   protected final boolean a() throws TimeoutException, XBeeException {
      this.a((String)"Identifying device...");
      Object var1;
      if (this.c) {
         var1 = new bluelab.connect.g.ObfuscatedMessage(bluelab.connect.c.d.d.POD_WHO_ARE_YOU.a(), (byte)this.d, (byte[])null);
      } else {
         var1 = new bluelab.connect.g.ObfuscatedDeviceMessage(bluelab.connect.c.d.d.WHO_ARE_YOU.a(), (byte[])null);
      }

      bluelab.connect.g.DeviceMessage var2 = this.a((bluelab.connect.g.DeviceMessage)var1, 3);
      String var3 = (new String(var2.GetInternalByteArray())).trim();
      this.a((String)String.format("Identification read [%s]", var3.replace("\u0000", " ")));
      return var3.length() > 0;
   }

   protected final void b() {
      b var2 = null;
      this.a((String)"Querying memory info...");
      byte[] var3;
      if ((var3 = this.a(Enum_BootloaderCommands.READ_VERSION, (short)0, 0, (byte[])null, 3)) != null) {
         var2 = new b(var3);
         logger.info("Max packet size: {}", var2.b());
         logger.info("Erase row size: {}", var2.c());
         logger.info("Write latch size: {}", var2.d());
      }

      this.i = var2;
      var2 = this.i;
      c var1 = this;
      this.a((String)"Initializing memory...");
      Iterator var5 = var2.a().iterator();

      while(var5.hasNext()) {
         l var6 = (l)var5.next();
         k var4 = new k(var6);
         var1.e.put(var6.a, var4);
         logger.info("Initialized {} starting at {} with size {}", new Object[]{var4.a, var4.b, var4.a()});
      }

      this.g();
   }

   protected final void c() throws BluelabException2 {
      int var2 = this.i.c();
      if (var2 <= 0) {
         throw new IllegalArgumentException("Erase block size should be greater than zero.");
      } else {
         k var3;
         if ((var3 = (k)this.e.get(bluelab.connect.c.d.Enum_MemoryType.PROGRAM_MEMORY)) != null) {
            int var4 = (int)var3.b;
            short var5 = (short)((int)Math.ceil((double)var3.c.length / (double)var2));
            this.a((String)"Erasing memory...");
            a(this.a(Enum_BootloaderCommands.ERASE_FLASH, var5, var4, (byte[])null, 3));
            this.a(this.i.d());
         } else {
            throw new BluelabException2("Program memory is not defined.");
         }
      }
   }

   protected final void d() throws InterruptedException, BluelabException2  {
      k var2;
      if ((var2 = (k)this.e.get(bluelab.connect.c.d.Enum_MemoryType.PROGRAM_MEMORY)) != null) {
         int var3 = (int)var2.b;
         this.a((String)"Verifying memory...");
         a(this.a(Enum_BootloaderCommands.CALC_CHECKSUM, (short)0, var3, (byte[])null, 3));
         if (this.c) {
            Thread.sleep(5000L);
         }

      } else {
         throw new BluelabException2("Program memory is not defined.");
      }
   }

   protected final void e() throws TimeoutException, XBeeException, BluelabException {
      this.a((String)"Restarting application...");
      byte[] var1 = this.a(Enum_BootloaderCommands.RESET_DEVICE, (short)0, 0, (byte[])null, 3);
      if (this.c) {
         bluelab.connect.c.c.MonitorController var2 = new bluelab.connect.c.c.MonitorController(this.bluelabRemoteDevice, bluelab.connect.c.Enum_Mode.MONITOR, false);
         Iterator var3 = this.bluelabRemoteDevice.getBluelabRemoteDevice().getDeviceMessages(var2).iterator();

         while(var3.hasNext()) {
            bluelab.connect.g.DeviceMessage var5 = (bluelab.connect.g.DeviceMessage)var3.next();
            this.a(var5, 1);
         }
      }

      a(var1);

      try {
         Thread.sleep(10000L);
      } catch (InterruptedException var4) {
         String var6 = var4.toString();
         var4.printStackTrace();
         logger.error("Firmware restart delay error: {}", var6);
      }
   }

   protected final void f() throws TimeoutException, XBeeException {
      this.a();
      this.bluelabRemoteDevice.initMonthDate();
      this.a((String)"Initialized device");
   }

   private void a(int var1) throws BluelabException2, TimeoutException {
      if (var1 <= 0) {
         throw new IllegalArgumentException("Write block size should be greater than zero.");
      } else {
         int var2 = this.b(var1);
         int var3 = 0;
         Iterator var5 = this.e.entrySet().iterator();

         while(true) {
            k var4;
            Enum_BootloaderCommands var6;
            do {
               do {
                  if (!var5.hasNext()) {
                     return;
                  }
               } while((var4 = (k)((Entry)var5.next()).getValue()) == null);
            } while((var6 = a(var4.a)) == null);

            this.a((String)String.format("Writing %s...", var4.a.getText()));

            for(long var8 = var4.b; var8 < var4.b(); var8 += (long)var1) {
               if (!var4.a(var8, var1)) {
                  byte[] var7 = new byte[var1];
                  var4.a(var7, var8, var1);
                  short var10002 = (short)var1;
                  int var10003 = (int)var8;
                  boolean var14 = true;
                  int var12 = var10003;
                  short var11 = var10002;
                  a((new s()).a(() -> {
                     return this.a(var6, var11, var12, var7, var5);
                  }, () -> {
                     String var2 = "Device communications failed, recovering...";
                     if (!var2.isEmpty()) {
                        this.a((String)var2);
                     }

                     bluelab.connect.g.ObfuscatedDeviceMessage var3 = new bluelab.connect.g.ObfuscatedDeviceMessage(bluelab.connect.c.d.d.WHO_ARE_YOU.a(), (byte[])null);
                     return this.a(var3, 3).GetInternalByteArray();
                  }));
                  ++var3;
                  this.f = (float)var3 / (float)var2;
                  if (this.g != null) {
                     this.g.onStatusUpdate(this.f, "");
                  }
               }
            }
         }
      }
   }

   private int b(int var1) {
      int var2 = 0;
      Iterator var4 = this.e.entrySet().iterator();

      while(true) {
         k var3;
         do {
            do {
               if (!var4.hasNext()) {
                  return var2;
               }
            } while((var3 = (k)((Entry)var4.next()).getValue()) == null);
         } while(a(var3.a) == null);

         for(long var7 = var3.b; var7 < var3.b(); var7 += (long)var1) {
            if (!var3.a(var7, var1)) {
               ++var2;
            }
         }
      }
   }

   private static Enum_BootloaderCommands a(Enum_MemoryType var0) {
      Enum_BootloaderCommands var1;
      switch(h()[var0.ordinal()]) {
      case 1:
         var1 = Enum_BootloaderCommands.WRITE_FLASH;
         break;
      case 2:
      default:
         var1 = null;
         break;
      case 3:
         var1 = Enum_BootloaderCommands.WRITE_EEPROM;
      }

      return var1;
   }

   private byte[] a(Enum_BootloaderCommands var1, short var2, int var3, byte[] var4, int var5) throws XBeeException {
      q var6 = new q((byte)var1.getValue(), var2, var3, var4);
      if (this.h) {
         Object var7;
         if (this.c) {
            var7 = new bluelab.connect.g.ObfuscatedMessage(bluelab.connect.c.d.d.POD_BOOTLOADER.a(), (byte)this.d, var6.c());
         } else {
            var7 = new bluelab.connect.g.ObfuscatedDeviceMessage(bluelab.connect.c.d.d.DEVICE_BOOTLOADER.a(), var6.c());
         }

         bluelab.connect.g.DeviceMessage var8 = this.a((bluelab.connect.g.DeviceMessage)var7, var5);
         var6 = new q(var8.GetInternalByteArray());
      } else {
         byte[] var9 = this.sendReceiveData(var6.c(), var5);
         var6 = new q(var9);
      }

      return var6.a() != var1.getValue() ? new byte[0] : var6.b();
   }

   private static void a(byte[] var0) throws BluelabException2 {
      if (var0 != null && var0.length > 0) {
         byte var1;
         if ((var1 = var0[0]) != r.SUCCESS.getValue()) {
            throw new BluelabException2(String.format("Firmware update response error (%d).", var1));
         }
      } else {
         throw new BluelabException2("Invalid firmware update response.");
      }
   }

   // $FF: synthetic method
   private static int[] h() {
      int[] var10000 = j;
      if (j != null) {
         return var10000;
      } else {
         int[] var0 = new int[bluelab.connect.c.d.Enum_MemoryType.values().length];

         try {
            var0[bluelab.connect.c.d.Enum_MemoryType.CONFIG_MEMORY.ordinal()] = 2;
         } catch (NoSuchFieldError var3) {
            ;
         }

         try {
            var0[bluelab.connect.c.d.Enum_MemoryType.EEPROM.ordinal()] = 3;
         } catch (NoSuchFieldError var2) {
            ;
         }

         try {
            var0[bluelab.connect.c.d.Enum_MemoryType.PROGRAM_MEMORY.ordinal()] = 1;
         } catch (NoSuchFieldError var1) {
            ;
         }

         j = var0;
         return var0;
      }
   }
}