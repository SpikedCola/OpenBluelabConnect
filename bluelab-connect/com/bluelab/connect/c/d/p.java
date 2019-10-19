package bluelab.connect.c.d;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class p {
   private int b;
   private int c;
   private int d;
   protected ByteBuffer a;

   public p(byte[] var1) {
      this.a = ByteBuffer.wrap(var1);
      this.a.order(ByteOrder.LITTLE_ENDIAN);
      this.a.getShort();
      this.b = Short.toUnsignedInt(this.a.getShort());
      this.a.getShort();
      this.a.getShort();
      this.a.getShort();
      this.c = Byte.toUnsignedInt(this.a.get());
      this.d = Byte.toUnsignedInt(this.a.get());
   }

   public final int b() {
      return this.b;
   }

   public final int c() {
      return this.c;
   }

   public final int d() {
      return this.d;
   }
}