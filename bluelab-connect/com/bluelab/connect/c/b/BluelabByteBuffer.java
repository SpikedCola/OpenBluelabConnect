package bluelab.connect.c.b;

import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;

public final class BluelabByteBuffer {
   private ByteBuffer byteBuf = ByteBuffer.allocate(1000);

   public final ByteBuffer getBuffer() {
      return this.byteBuf;
   }

   public final void addBytes(byte[] bytes) {
      if (bytes.length > this.byteBuf.remaining()) {
         this.byteBuf.clear();
         throw new BufferOverflowException();
      } else {
         this.byteBuf.put(bytes);
         this.byteBuf.flip();
      }
   }

   public final void compactOrClear() {
      if (this.byteBuf.hasRemaining()) {
         this.byteBuf.compact();
      } else {
         this.byteBuf.clear();
      }
   }
}