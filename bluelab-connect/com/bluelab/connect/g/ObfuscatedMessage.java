package bluelab.connect.g;

import bluelab.connect.d.WeirdEncoder;
import com.google.common.primitives.Bytes;
import java.nio.ByteBuffer;

public final class ObfuscatedMessage extends ObfuscatedDeviceMessage {
   private byte innerCommand;

   public ObfuscatedMessage(byte command, byte innerCommand, byte[] message) {
      this(command, bluelab.connect.g.BluelabSecureRandom.GetRandomByte(), innerCommand, message);
   }

   private ObfuscatedMessage(byte command, byte randomByte, byte innerCommand, byte[] message) {
      super(command, randomByte, message);
      ++this.byteArrayLengthPlusOne;
      this.innerCommand = innerCommand;
   }

   public ObfuscatedMessage(byte[] buf) {
      this(ByteBuffer.wrap(buf));
   }

   public ObfuscatedMessage(ByteBuffer var1) {
      super(var1);
   }

   protected final void LoadByteArray(ByteBuffer buf) {
      int len;
      if ((len = this.byteArrayLengthPlusOne - 1 - 1 - 1) < 0) {
         throw new IllegalArgumentException("Invalid obfuscated message length!");
      } else {
          // get first byte of buffer
         this.randomByte = buf.get();
         this.FillSecondKeyFromFirstKeyUsing(this.randomByte);
         byte[] var3 = new byte[len + 1];
         // copy rest of var1 array -> var3
         buf.get(var3);
         buf = ByteBuffer.wrap(XorArrayWithKey(var3, this.key2));
         // get first byte from new var1
         this.innerCommand = buf.get();
         this.byteArray = new byte[len];
         // copy rest of var1 array -> this.byteArray
         buf.get(this.byteArray);
      }
   }

   public final byte getInnerCommand() {
      return this.innerCommand;
   }

   public final byte[] ConstructPacket() {
      ByteBuffer packet;
      packet = ByteBuffer.allocate(this.byteArray.length + 8 + 1);
      packet.put((byte)27);
      packet.putShort(this.byteArrayLengthPlusOne); // 2 bytes
      packet.put(this.innerCommand); // 1 byte
      packet.put(this.randomByte); // 1 byte
      byte[] var2 = Bytes.concat(new byte[][]{{this.innerCommand}, this.byteArray});
      // note that b is prepended to byteArray before XOR
      packet.put(XorArrayWithKey(var2, this.key2)); // variable length (byteArray)
      this.crc = bluelab.connect.d.WeirdEncoder.CRC(packet.array(), 1, packet.capacity() - 1 - 2 - 1);
      packet.putShort(this.crc); // 2 bytes
      packet.put((byte)13); // 1 byte
      return packet.array();
   }

   public static boolean XorCheck(byte expectedPosition3Value, byte resultShouldEqualThis, byte[] buf) {
      boolean res = false;
      ByteBuffer byteBuffer = ByteBuffer.wrap(buf);
      // if var2 position 3 == var0
      if (bluelab.connect.g.DeviceMessage.IsMessageCommandByte(expectedPosition3Value, byteBuffer)) {
         byte fourthByte = byteBuffer.get(byteBuffer.position() + 4); // get 4th byte
         byte fifthByte = byteBuffer.get(byteBuffer.position() + 5); // get 5th byte
         byte[] var4 = new byte[]{(byte)(fourthByte ^ secretKey[0])};
         // (fifthByte XOR (fourthByte XOR secretKey[0])) == var1
         res = XorArrayWithKey(new byte[]{fifthByte}, var4)[0] == resultShouldEqualThis;
      }

      return res;
   }
}