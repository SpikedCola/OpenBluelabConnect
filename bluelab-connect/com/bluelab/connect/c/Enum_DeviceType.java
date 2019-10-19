package bluelab.connect.c;

public enum Enum_DeviceType {
   UNKNOWN("Unknown", "unknown"),
   PH_CONTROLLER("pH Controller", "phcontroller"),
   EXTENDER("Range Extender", "extender"),
   PRO_CONTROLLER("Pro Controller", "procontroller"),
   GUARDIAN("Guardian", "guardian");

   private final String uiText;
   private final String apiText;

   private Enum_DeviceType(String uiText, String apiText) {
      this.uiText = uiText;
      this.apiText = apiText;
   }

   public final String getUiText() {
      return this.uiText;
   }

   public final String getApiText() {
      return this.apiText;
   }
}