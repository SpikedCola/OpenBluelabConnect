package bluelab.connect.c;

public enum Enum_Messages {
   LAST_IN_CHAIN(" is last in the chain."),
   DAISY_CHAIN_ID(" is involved in a chain change (ID). Please check device."),
   ADDRESS(" is involved in a chain change (MAC). Please check device."),
   TEMPERATURE(" has a temperature problem."),
   NEW_CALIBRATION(" has a new calibration."),
   COMMUNICATION(" has a communication problem."),
   USER_BUSY(" is being primed or calibrated."),
   UNRESPONSIVE(" is unresponsive.");

   private final String uiText;

   private Enum_Messages(String uiText) {
      this.uiText = uiText;
   }

   public final String getUiText() {
      return this.uiText;
   }
}