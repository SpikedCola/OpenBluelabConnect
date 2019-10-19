package bluelab.connect.c;

public enum Enum_DevicePresence {
   ALIEN("Alien", "ALIEN"),
   INDISTINCT("Indistinct", "INDISTINCT"),
   ONLINE("Online", "ONLINE"),
   OFFLINE("Offline", "OFFLINE");

   private final String uiText;
   private final String apiText;

   private Enum_DevicePresence(String uiText, String apiText) {
      this.uiText = uiText;
      this.apiText = apiText;
   }

   public final String getApiText() {
      return this.apiText;
   }
}