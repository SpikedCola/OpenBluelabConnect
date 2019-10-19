package bluelab.connect.c.d;

enum d {
   WHO_ARE_YOU((byte)0),
   DEVICE_BOOTLOADER((byte)48),
   POD_WHO_ARE_YOU((byte)50),
   POD_BOOTLOADER((byte)98),
   NONE((byte)-1);

   private final byte value;

   private d(byte var3) {
      this.value = var3;
   }

   public final byte a() {
      return this.value;
   }
}