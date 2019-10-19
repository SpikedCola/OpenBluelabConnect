package bluelab.connect.g;

public abstract class ByteArray {
   protected byte[] byteArray;

   public final byte[] GetInternalByteArray() {
      return this.byteArray;
   }

   public abstract byte[] ConstructPacket();
}