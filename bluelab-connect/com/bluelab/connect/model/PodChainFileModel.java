package bluelab.connect.model;

import bluelab.connect.c.PeripodWithPumps;
import bluelab.connect.c.Peripod;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiConsumer;

public class PodChainFileModel {
   private static final int DEFAULT_POD_CAPACITY = 15;
   private Map<Integer, PodSettings> pods;

   public PodChainFileModel() {
      this(15);
   }

   public PodChainFileModel(Map<Integer, Peripod> var1, String unit) {
      this(var1.size());
      var1.forEach((var2x, var3) -> {
         this.pods.put(var2x, new PodSettings((PeripodWithPumps)var3, unit));
      });
   }

   public PodChainFileModel(PodChainFileModel var1) {
      this(var1.pods.size());
      var1.pods.forEach((var1x, var2) -> {
         this.pods.put(var1x, new PodSettings(var2));
      });
   }

   private PodChainFileModel(int var1) {
      this.pods = new LinkedHashMap(var1);
   }

   public Map<Integer, PodSettings> getPodSettings() {
      return this.pods;
   }

   public int getPodCount() {
      return this.pods.size();
   }

   public int getPumpCount() {
      int var1 = 0;

      Entry var2;
      for(Iterator var3 = this.pods.entrySet().iterator(); var3.hasNext(); var1 += ((PodSettings)var2.getValue()).getPumpSettings().size()) {
         var2 = (Entry)var3.next();
      }

      return var1;
   }

   public void disablePumps() {
      this.pods.forEach((var0, var1) -> {
         var1.disablePumps();
      });
   }

   public boolean importPumpSettings(PodChainFileModel var1) {
      Iterator var2 = this.toPumpList().iterator();
      List var6 = var1.toPumpList();
      boolean var3 = true;

      PumpSettings var5;
      PumpSettings var7;
      for(Iterator var4 = var6.iterator(); var4.hasNext(); var5.copy(var7, false)) {
         var7 = (PumpSettings)var4.next();
         if (var2 == null || !var2.hasNext()) {
            break;
         }

         var5 = (PumpSettings)var2.next();
         if (var3) {
            var3 = var5.unit.equals(var7.unit);
         }
      }

      return var3;
   }

   public boolean importPumpValues(PodChainFileModel var1) {
      Iterator var2 = this.toPumpList().iterator();
      List var6 = var1.toPumpList();
      boolean var3 = true;
      Iterator var4 = var6.iterator();

      while(var4.hasNext()) {
         PumpSettings var7 = (PumpSettings)var4.next();
         if (var2 == null || !var2.hasNext()) {
            break;
         }

         PumpSettings var5 = (PumpSettings)var2.next();
         if (var3) {
            var3 = var5.unit.equals(var7.unit);
         }

         if (var5.equals(var7)) {
            var5.value = var7.value;
         }
      }

      return var3;
   }

   public boolean pumpFunctionEquals(PodChainFileModel var1) {
      Iterator var2 = this.toPumpList().iterator();
      Iterator var3 = var1.toPumpList().iterator();

      while(var3.hasNext()) {
         PumpSettings var5 = (PumpSettings)var3.next();
         if (var2 == null || !var2.hasNext()) {
            break;
         }

         if (!((PumpSettings)var2.next()).pumpType.equals(var5.pumpType)) {
            return false;
         }
      }

      return true;
   }

   private List<PumpSettings> toPumpList() {
      int var1 = this.getPumpCount();
      ArrayList var2 = new ArrayList(var1);
      this.pods.forEach((var1x, var2x) -> {
         var2.addAll(var2x.getPumpSettings());
      });
      return var2;
   }
}