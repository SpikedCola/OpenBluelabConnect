package bluelab.connect.c.d;

public enum Enum_MemoryType implements bluelab.connect.j.Interface_GetText {
   PROGRAM_MEMORY("Program memory"),
   CONFIG_MEMORY("Configuration memory"),
   EEPROM("EEPROM");

   private final String text;

   private Enum_MemoryType(String var3) {
      this.text = var3;
   }

   public final String getText() {
      return this.text;
   }
}