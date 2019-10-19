package bluelab.connect.h;

import java.util.Iterator;
import java.util.Map.Entry;
import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;

public final class TwoColumnTable extends AbstractTableModel implements Interface_DataChanged {
   private StateManager stateManager;

   public TwoColumnTable(StateManager stateManager) {
      this.stateManager = stateManager;
      stateManager.setDataChangedCallback((Interface_DataChanged)this);
   }

   public final int getColumnCount() {
      return 2;
   }

   public final int getRowCount() {
      return this.stateManager.getDataSize();
   }

   public final String getColumnName(int var1) {
      return var1 == 0 ? "Parameter name" : "Parameter value";
   }

   public final Object getValueAt(int var1, int var2) {
      Iterator var3 = this.stateManager.dataMap.entrySet().iterator();
      Entry var4 = null;

      for(int var5 = 0; var5 <= var1; ++var5) {
         var4 = (Entry)var3.next();
      }

      if (var4 == null) {
         return null;
      } else {
         return var2 == 0 ? var4.getKey() : var4.getValue();
      }
   }

   public final void dataChanged() {
      this.fireTableChanged((TableModelEvent)null);
   }
}