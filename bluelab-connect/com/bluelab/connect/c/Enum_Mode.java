package bluelab.connect.c;

public enum Enum_Mode {
   MONITOR("Monitor", "MODE_MONITOR"),
   CONTROL("Control", "MODE_CONTROL"),
   SETTINGS("Settings", "MODE_SETTINGS"),
   CALIBRATION("Calibration", "MODE_CALIBRATION"),
   UNKNOWN("Unknown", "MODE_UNKNOWN");

   private final String uiText;
   private final String apiText;

   private Enum_Mode(String uiText, String apiText) {
      this.uiText = uiText;
      this.apiText = apiText;
   }

   public final String getUiText() { // a()
      return this.uiText;
   }

   public final String getApiText() { // b()
      return this.apiText;
   }
}