package bluelab.connect.h;

import java.util.LinkedHashMap;
import java.util.Map;

public final class StateManager {
   protected Map<String, String> dataMap = new LinkedHashMap();
   private Interface_DataChanged callbackInterface = null;
   protected Map<String, TransmitReceiveStateManager> keyCodeManagerMap = new LinkedHashMap();
   private Interface_TableChanged transmitReceiveTable = null;

   public final void setDataChangedCallback(Interface_DataChanged dataChangedCallback) {
      this.callbackInterface = dataChangedCallback;
   }

   public final void setTransmitReceiveTable(Interface_TableChanged table) {
      this.transmitReceiveTable = table;
   }

   public final int getDataSize() {
      return this.dataMap.size();
   }

   public final int getBSize() {
      return this.keyCodeManagerMap.size();
   }

   public final void put(String name, String value) {
      this.dataMap.put(name, value);
      if (this.callbackInterface != null) {
         this.callbackInterface.dataChanged();
      }

   }

   public final synchronized void incrementStateCounter(String keyCode, String device16BitAddress, Enum_TransmitReceiveState state) {
      TransmitReceiveStateManager manager = (TransmitReceiveStateManager)this.keyCodeManagerMap.get(keyCode);
      if (manager == null) {
         manager = new TransmitReceiveStateManager();
         this.keyCodeManagerMap.put(keyCode, manager);
         if (this.transmitReceiveTable != null) {
            this.transmitReceiveTable.tableChanged();
         }
      }

      manager.set16BitAddress(device16BitAddress);
      manager.incrementCounterForState(state);
      if (this.transmitReceiveTable != null) {
         this.transmitReceiveTable.tableChanged(keyCode);
      }

   }
}