package bluelab.connect.c;

public enum Enum_Lockout {
   NORMALLY_OPEN("External lockout (normally open)", "NORMALLY_OPEN"),
   NORMALLY_CLOSED("External lockout (normally closed)", "NORMALLY_CLOSED"),
   LOW_CONDUCTIVITY("Low conductivity lockout", "LOW_CONDUCTIVITY"),
   INEFFECTIVE_CONTROL("Ineffective control lockout", "INEFFECTIVE_CONTROL"),
   OTHER("Other channel or alarm lockout", "OTHER"),
   POD_CHAIN_CHANGE("Pod chain change lockout", "POD_CHAIN_CHANGE");

   private final String uiText;
   private final String apiText;

   private Enum_Lockout(String uiText, String apiText) {
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