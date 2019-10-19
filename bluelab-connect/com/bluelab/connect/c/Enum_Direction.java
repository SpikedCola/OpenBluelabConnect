package bluelab.connect.c;

public enum Enum_Direction {
   DOWN("Down", "DIRECTION_DOWN"),
   UP("Up", "DIRECTION_UP"),
   OFF("Off", "DIRECTION_OFF");

   private final String uiText;
   private final String apiText;

   private Enum_Direction(String uiText, String apiText) {
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