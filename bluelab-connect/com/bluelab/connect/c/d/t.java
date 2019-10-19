package bluelab.connect.c.d;

public enum t {
   RESTART_BOOT("Restart boot"),
   IDENTIFY("Identify"),
   QUERY("Query"),
   PROGRAM("Program"),
   VERIFY("Verify"),
   RESTART_APP("Restart app"),
   INITIALIZE("Reinitialize");

   private final String text;

   private t(String var3) {
      this.text = var3;
   }

   public final String toString() {
      return this.text;
   }
}