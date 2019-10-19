package bluelab.connect.g;

import bluelab.connect.d.WeirdEncoder;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class ObfuscatedDeviceMessage extends DeviceMessage {
   protected static final byte[] secretKey = new byte[]{110, 103, -59, 121, -35, 97, 2, 26};
   protected byte[] key2;
   protected byte randomByte;

   public ObfuscatedDeviceMessage(byte command, byte[] message) {
      this(command, bluelab.connect.g.BluelabSecureRandom.GetRandomByte(), message);
   }

   protected ObfuscatedDeviceMessage(byte command, byte randomByte, byte[] message) {
      super(command, message);
      ++this.byteArrayLengthPlusOne;
      this.randomByte = randomByte;
      this.FillSecondKeyFromFirstKeyUsing(randomByte);
   }

   public ObfuscatedDeviceMessage(byte[] var1) {
      this(ByteBuffer.wrap(var1));
   }

   public ObfuscatedDeviceMessage(ByteBuffer var1) {
      super(var1);
   }

   protected void LoadByteArray(ByteBuffer var1) {
      int var2;
      if ((var2 = this.byteArrayLengthPlusOne - 1 - 1) < 0) {
         throw new IllegalArgumentException("Invalid obfuscated message length!");
      } else {
         this.randomByte = var1.get();
         this.FillSecondKeyFromFirstKeyUsing(this.randomByte);
         this.byteArray = new byte[var2];
         var1.get(this.byteArray, 0, var2);
         this.byteArray = XorArrayWithKey(this.byteArray, this.key2);
      }
   }

   protected final void FillSecondKeyFromFirstKeyUsing(byte var1) {
      this.key2 = Arrays.copyOf(secretKey, 8);

      for(int idx = 0; idx < this.key2.length; ++idx) {
         this.key2[idx] ^= var1;
      }

   }

   public byte[] ConstructPacket() {
      ByteBuffer packet;
      packet = ByteBuffer.allocate(this.byteArray.length + 7);
      packet.put((byte)27);
      packet.putShort(this.byteArrayLengthPlusOne);
      packet.put(this.command);
      packet.put(this.randomByte);
      packet.put(XorArrayWithKey(this.byteArray, this.key2));
      this.crc = bluelab.connect.d.WeirdEncoder.CRC(packet.array(), 1, packet.capacity() - 1 - 2);
      packet.putShort(this.crc);
      return packet.array();
   }

   protected static byte[] XorArrayWithKey(byte[] buf, byte[] key) {
      buf = Arrays.copyOf(buf, buf.length);

      for(int idx = 0; idx < buf.length; ++idx) {
         buf[idx] ^= key[idx % key.length];
      }

      return buf;
   }
}