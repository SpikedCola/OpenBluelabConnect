package bluelab.connect.c;

public enum Enum_AlarmState {
   OFF("Off", "ALARM_OFF"),
   OK("OK", "ALARM_OK"),
   LOW("Low", "ALARM_LOW"),
   HIGH("High", "ALARM_HIGH");

   private final String uiText;
   private final String apiText;

   private Enum_AlarmState(String var3, String var4) {
      this.uiText = var3;
      this.apiText = var4;
   }

   public final String getApiText() {
      return this.apiText;
   }
}