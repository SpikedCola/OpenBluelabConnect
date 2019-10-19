package bluelab.connect.g;

import bluelab.connect.d.WeirdEncoder;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public class DeviceMessage extends ByteArray {
   // is this byteArray + "random byte"? or + command byte (and random byte is accounted for in byteArray length?)
   protected short byteArrayLengthPlusOne; 
   protected byte command;
   protected short crc;

   public DeviceMessage(byte command, byte[] message) {
      this.command = command;
      if (message != null) {
         this.byteArray = Arrays.copyOf(message, message.length);
      } else {
         this.byteArray = new byte[0];
      }

      this.byteArrayLengthPlusOne = (short)(1 + this.byteArray.length);
      this.crc = 0;
   }

   public DeviceMessage(byte[] var1) {
      this(ByteBuffer.wrap(var1));
   }

   public DeviceMessage(ByteBuffer buf) {
      if (buf == null) {
         throw new NullPointerException("Input argument cannot be null!");
      } else if (!LocateStartCharacter(buf)) {
         throw new IllegalArgumentException("Device message synchronization value could not be found!");
      } else {
         buf.order(ByteOrder.BIG_ENDIAN);
         short dataLengthFromBuffer = buf.getShort(buf.position() + 1);
         int dataLengthPlusFour;
         if ((dataLengthPlusFour = dataLengthFromBuffer + 2 + 2) + 1 > buf.remaining()) {
            throw new IllegalArgumentException("Partial message!");
         } else {
            buf.get(); // consume byte
            if (dataLengthFromBuffer <= 0) {
               throw new IllegalArgumentException("Invalid device message length!");
            } else {
               byte[] bufArray = buf.array();
               int position = buf.position();
               boolean crcValid = false;
               if (bufArray != null && bufArray.length >= position + dataLengthPlusFour) {
                  crcValid = WeirdEncoder.CRC(bufArray, position, dataLengthPlusFour) == 0;
               }

               if (!crcValid) {
                  throw new IllegalArgumentException("Invalid device message CRC!");
               } else {
                  this.byteArrayLengthPlusOne = buf.getShort();
                  this.command = buf.get();
                  this.LoadByteArray(buf);
                  this.crc = buf.getShort();
               }
            }
         }
      }
   }

   public boolean equals(Object var1) {
      if (var1 == null) {
         return false;
      } else if (!this.getClass().isAssignableFrom(var1.getClass())) {
         return false;
      } else {
         DeviceMessage var2 = (DeviceMessage)var1;
         return this.byteArrayLengthPlusOne == var2.byteArrayLengthPlusOne && this.command == var2.command && this.crc == var2.crc;
      }
   }

   private static boolean LocateStartCharacter(ByteBuffer buf) {
       boolean foundTwentySeven = false;
      // scan packet looking for '27' (start of packet marker) 
      int idx;
      for (
          // starting at position()
          idx = buf.position(); 
          // stop at end of buffer, or when we find '27'
          idx <= (buf.limit() - 1) && !(foundTwentySeven = buf.get(idx) == 27); 
          // inc position each round
          ++idx
      ) {
          // do nothing
      }
        // set passed buffer position to index of start character
      buf.position(idx);
      return foundTwentySeven;
   }

   protected void LoadByteArray(ByteBuffer sourceBuf) {
      int len = this.byteArrayLengthPlusOne - 1;
      this.byteArray = new byte[len];
      sourceBuf.get(this.byteArray, 0, len);
   }

   public final byte getCommand() {
      return this.command;
   }

   public byte[] ConstructPacket() {
      ByteBuffer packet;
      packet = ByteBuffer.allocate(this.byteArray.length + 6);
      packet.put((byte)27);
      packet.putShort(this.byteArrayLengthPlusOne);
      packet.put(this.command);
      packet.put(this.byteArray);
      this.crc = WeirdEncoder.CRC(packet.array(), 1, packet.capacity() - 1 - 2);
      packet.putShort(this.crc);
      return packet.array();
   }

   public static boolean IsValid2(ByteBuffer var0) {
      if (LocateStartCharacter(var0) && var0.remaining() >= 6) {
         short var1 = var0.getShort(var0.position() + 1);
         int var2 = var1 + 3 + 2;
         return var0.remaining() >= var2 && var1 > 0;
      } else {
         return false;
      }
   }

   public static byte GetMessageCommandByte(ByteBuffer buf) {
      return LocateStartCharacter(buf) && buf.remaining() >= 6 ? buf.get(buf.position() + 3) : -1;
   }

   public static boolean TestCommandByte(byte cmd, byte[] buf) {
      return DeviceMessage.IsMessageCommandByte(cmd, ByteBuffer.wrap(buf));
   }

   public static boolean IsMessageCommandByte(byte cmd, ByteBuffer buf) {
      return LocateStartCharacter(buf) && buf.remaining() >= 6 && buf.get(buf.position() + 3) == cmd;
   }
}