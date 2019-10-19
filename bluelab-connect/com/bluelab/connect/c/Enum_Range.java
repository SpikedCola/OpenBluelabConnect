package bluelab.connect.c;

public enum Enum_Range {
   IN_RANGE("In range", "IN_RANGE"),
   ERROR("Error", "ERROR"),
   DISCONNECTED("Disconnected", "DISCONNECTED"),
   UNDER_RANGE("Under range", "UNDER_RANGE"),
   OVER_RANGE("Over range", "OVER_RANGE");

   private final String uiText;
   private final String apiText;

   private Enum_Range(String uiText, String apiText) {
      this.uiText = uiText;
      this.apiText = apiText;
   }

   public final String getApiText() {
      return this.apiText;
   }
}