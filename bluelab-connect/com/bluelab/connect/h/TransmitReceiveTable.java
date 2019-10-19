package bluelab.connect.h;

import java.util.Iterator;
import java.util.Map.Entry;
import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;

public final class TransmitReceiveTable extends AbstractTableModel implements Interface_TableChanged {
   private StateManager stateManager;

   public TransmitReceiveTable(StateManager stateManager) {
      this.stateManager = stateManager;
      stateManager.setTransmitReceiveTable((Interface_TableChanged)this);
   }

   public final int getColumnCount() {
      return 2 + Enum_TransmitReceiveState.values().length;
   }

   public final int getRowCount() {
      return this.stateManager.getBSize();
   }

   public final String getColumnName(int var1) {
      Enum_TransmitReceiveState[] var2 = Enum_TransmitReceiveState.values();
      if (var1 == 0) {
         return "Key";
      } else {
         return var1 == 1 ? "Address" : var2[var1 - 2].getText();
      }
   }

   public final Object getValueAt(int var1, int var2) {
      Iterator bIterator = this.stateManager.keyCodeManagerMap.entrySet().iterator();
      Entry entry = null;

      for(int idx = 0; idx <= var1; ++idx) {
         entry = (Entry)bIterator.next();
      }

      if (entry == null) {
         return null;
      } else if (var2 == 0) {
         return entry.getKey();
      } else {
         return var2 == 1 ? ((TransmitReceiveStateManager)entry.getValue()).get16BitAddress() : ((TransmitReceiveStateManager)entry.getValue()).getCountOfState(var2 - 2);
      }
   }

   public final void tableChanged(String key) {
      Iterator var2 = this.stateManager.keyCodeManagerMap.entrySet().iterator();

      for(int rowIdx = 0; rowIdx < this.getRowCount(); ++rowIdx) {
         Entry var3 = (Entry)var2.next();
         if (key.equals(var3.getKey())) {
            this.fireTableRowsUpdated(rowIdx, rowIdx);
            return;
         }
      }

   }

   public final void tableChanged() {
      this.fireTableChanged((TableModelEvent)null);
   }
}