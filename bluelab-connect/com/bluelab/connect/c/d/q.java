package bluelab.connect.c.d;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public final class q {
   private byte a;
   private short b;
   private short c;
   private int d;
   private byte[] e;
   // $FF: synthetic field
   private static int[] f;

   public q(byte var1, short var2, int var3, byte[] var4) {
      this.a = var1;
      this.b = var2;
      Enum_BootloaderCommands var5 = (Enum_BootloaderCommands)bluelab.connect.j.EnumValueFind.FindValue(Enum_BootloaderCommands.class, var1, Enum_BootloaderCommands.READ_VERSION);
      switch(d()[var5.ordinal()]) {
      case 3:
      case 4:
      case 6:
      case 8:
         this.c = -21931;
         break;
      case 5:
      case 7:
      default:
         this.c = 0;
      }

      short var10000 = this.c;
      this.d = var3;
      if (var4 != null) {
         if (Short.toUnsignedInt(var2) != var4.length) {
            throw new IllegalArgumentException("Payload length does not match.");
         } else {
            this.e = var4;
         }
      } else {
         this.e = new byte[0];
      }
   }

   public q(byte[] var1) {
      if (var1 != null && var1.length >= 10) {
         ByteBuffer var2;
         if ((var2 = ByteBuffer.wrap(var1).order(ByteOrder.LITTLE_ENDIAN)).get() == 85) {
            this.a = var2.get();
            this.b = var2.getShort();
            this.c = var2.getShort();
            this.d = var2.getInt();
            int var3 = var1.length - 10;
            this.e = new byte[var3];
            var2.get(this.e, 0, var3);
         } else {
            throw new IllegalArgumentException("Invalid bootloader message synchronization value!");
         }
      } else {
         throw new IllegalArgumentException("Invalid bootloader message length!");
      }
   }

   public final byte a() {
      return this.a;
   }

   public final byte[] b() {
      return this.e;
   }

   public final byte[] c() {
      ByteBuffer var1;
      (var1 = ByteBuffer.allocate(this.e.length + 10).order(ByteOrder.LITTLE_ENDIAN)).put((byte)85);
      var1.put(this.a);
      var1.putShort(this.b);
      var1.putShort(this.c);
      var1.putInt(this.d);
      var1.put(this.e);
      return var1.array();
   }

   // $FF: synthetic method
   private static int[] d() {
      int[] var10000 = f;
      if (f != null) {
         return var10000;
      } else {
         int[] var0 = new int[Enum_BootloaderCommands.values().length];

         try {
            var0[Enum_BootloaderCommands.CALC_CHECKSUM.ordinal()] = 9;
         } catch (NoSuchFieldError var10) {
            ;
         }

         try {
            var0[Enum_BootloaderCommands.ERASE_FLASH.ordinal()] = 4;
         } catch (NoSuchFieldError var9) {
            ;
         }

         try {
            var0[Enum_BootloaderCommands.READ_CONFIG.ordinal()] = 7;
         } catch (NoSuchFieldError var8) {
            ;
         }

         try {
            var0[Enum_BootloaderCommands.READ_EEPROM.ordinal()] = 5;
         } catch (NoSuchFieldError var7) {
            ;
         }

         try {
            var0[Enum_BootloaderCommands.READ_FLASH.ordinal()] = 2;
         } catch (NoSuchFieldError var6) {
            ;
         }

         try {
            var0[Enum_BootloaderCommands.READ_VERSION.ordinal()] = 1;
         } catch (NoSuchFieldError var5) {
            ;
         }

         try {
            var0[Enum_BootloaderCommands.RESET_DEVICE.ordinal()] = 10;
         } catch (NoSuchFieldError var4) {
            ;
         }

         try {
            var0[Enum_BootloaderCommands.WRITE_CONFIG.ordinal()] = 8;
         } catch (NoSuchFieldError var3) {
            ;
         }

         try {
            var0[Enum_BootloaderCommands.WRITE_EEPROM.ordinal()] = 6;
         } catch (NoSuchFieldError var2) {
            ;
         }

         try {
            var0[Enum_BootloaderCommands.WRITE_FLASH.ordinal()] = 3;
         } catch (NoSuchFieldError var1) {
            ;
         }

         f = var0;
         return var0;
      }
   }
}