package bluelab.connect.c;

public enum Enum_PodChainChange {
   POD_CHAIN_CHANGE("Pod chain has changed", "POD_CHAIN_CHANGE");

   private final String uiText;
   private final String apiText;

   private Enum_PodChainChange(String uiText, String apiText) {
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