package bluelab.connect.c.d;

public enum Enum_BootloaderCommands implements bluelab.connect.j.Interface_GetText, bluelab.connect.j.Interface_GetValue {
   READ_VERSION((byte)0, "Version request"),
   READ_FLASH((byte)1, "Read Flash request"),
   WRITE_FLASH((byte)2, "Write Flash request"),
   ERASE_FLASH((byte)3, "Erase Flash request"),
   READ_EEPROM((byte)4, "Read EEPROM request"),
   WRITE_EEPROM((byte)5, "Write EEPROM request"),
   READ_CONFIG((byte)6, "Read configuration request"),
   WRITE_CONFIG((byte)7, "Write configuration request"),
   CALC_CHECKSUM((byte)8, "Calculate checksum request"),
   RESET_DEVICE((byte)9, "Reset device request");

   private final byte value;
   private final String text;

   private Enum_BootloaderCommands(byte var3, String var4) {
      this.value = var3;
      this.text = var4;
   }

   public final int getValue() {
      return this.value;
   }

   public final String getText() {
      return this.text;
   }
}