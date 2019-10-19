package bluelab.connect.h;

import java.util.ArrayList;

public final class TransmitReceiveStateManager {
   private String device16BitAddress;
   private ArrayList<Long> stateList = new ArrayList();

   public TransmitReceiveStateManager() {
      Enum_TransmitReceiveState[] states = Enum_TransmitReceiveState.values();

      for(int idx = 0; idx < states.length; ++idx) {
         Enum_TransmitReceiveState state = states[idx];
         this.stateList.add(state.getValue(), (long)0);
      }

   }

   public final String get16BitAddress() {
      return this.device16BitAddress;
   }

   public final void set16BitAddress(String addr) {
      this.device16BitAddress = addr;
   }

   public final Long getCountOfState(int state) {
      return this.stateList.get(state);
   }

   public final synchronized void incrementCounterForState(Enum_TransmitReceiveState state) {
      long currentValue = this.stateList.get(state.getValue());
      this.stateList.set(state.getValue(), currentValue + 1L);
   }
}