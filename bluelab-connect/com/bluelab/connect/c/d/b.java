package bluelab.connect.c.d;

import java.util.ArrayList;
import java.util.List;

public final class b extends p {
   private List<l> b;

   public b(byte[] var1) {
      super(var1);
      this.a.getShort();
      l var3;
      (var3 = new l()).a = Enum_MemoryType.PROGRAM_MEMORY;
      var3.b = Integer.toUnsignedLong(this.a.getInt());
      var3.c = this.a.getInt();
      l var2;
      (var2 = new l()).a = Enum_MemoryType.CONFIG_MEMORY;
      var2.b = Integer.toUnsignedLong(this.a.getInt());
      var2.c = Short.toUnsignedInt(this.a.getShort());
      (var2 = new l()).a = Enum_MemoryType.EEPROM;
      var2.b = Integer.toUnsignedLong(this.a.getInt());
      var2.c = Short.toUnsignedInt(this.a.getShort());
      this.b = new ArrayList();
      this.b.add(var3);
   }

   public final List<l> a() {
      return this.b;
   }
}