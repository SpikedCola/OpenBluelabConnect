package bluelab.connect.g;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public final class SynchronizationMessage extends ByteArray {
   public SynchronizationMessage(ByteBuffer buf) {
      if (buf == null) {
         throw new NullPointerException("Input argument cannot be null!");
      } else {
         buf.order(ByteOrder.BIG_ENDIAN);
         if (!LocateSyncCharacter(buf)) {
            throw new IllegalArgumentException("Device message synchronization value could not be found!");
         } else {
            buf.getShort();
            int remaining = buf.remaining();
            this.byteArray = new byte[remaining];
            buf.get(this.byteArray, 0, remaining);
         }
      }
   }

   public final boolean equals(Object var1) {
      if (var1 == null) {
         return false;
      } else if (!this.getClass().isAssignableFrom(var1.getClass())) {
         return false;
      } else {
         SynchronizationMessage var2 = (SynchronizationMessage)var1;
         return Arrays.equals(this.byteArray, var2.byteArray);
      }
   }

   private static boolean LocateSyncCharacter(ByteBuffer buf) {
      boolean found = false;

      int var1;
      for(var1 = buf.position(); var1 <= buf.limit() - 2 && !(found = buf.getShort(var1) == 2313); ++var1) {
         ;
      }

      buf.position(var1);
      return found;
   }

   public final byte[] ConstructPacket() {
      ByteBuffer var1;
      (var1 = ByteBuffer.allocate(this.byteArray.length + 2)).putShort((short)2313);
      var1.put(this.byteArray);
      return var1.array();
   }

   public static boolean IsValid(ByteBuffer var0) {
      return LocateSyncCharacter(var0) && var0.remaining() > 0;
   }
}