package bluelab.connect.h;

public enum Enum_TransmitReceiveState implements bluelab.connect.j.Interface_GetText, bluelab.connect.j.Interface_GetValue {
   TRANSMIT(0, "Transmit"),
   RECEIVE(1, "Receive"),
   RETRY(2, "Retry"),
   TIMEOUT(3, "Time-out"),
   INVALID(4, "Invalid");

   private int value;
   private String text;

   private Enum_TransmitReceiveState(int value, String text) {
      this.value = value;
      this.text = text;
   }

   public final int getValue() {
      return this.value;
   }

   public final String getText() {
      return this.text;
   }
}