package bluelab.connect.c;

import bluelab.connect.model.PodSettings;
import java.util.ArrayList;
import java.util.List;

public final class PeripodWithPumps extends Peripod {
   private List<Pump> pumps;

   public PeripodWithPumps(String name, int unused, Enum_PeripodType peripodType, int numPumps, double var5) {
      super(Enum_Peripod.PERIPOD, name, unused, peripodType);
      this.pumps = new ArrayList(numPumps);

      for(int idx = 0; idx < numPumps; ++idx) {
         this.pumps.add(new Pump(0.0D));
      }

   }

   public final List<Pump> getPumps() {
      return this.pumps;
   }

   public final void copyNameAndPumpSettings(PodSettings podSettings) {
      this.setName(podSettings.name);

      for(int idx = 0; idx < podSettings.getPumpSettings().size(); ++idx) {
         ((Pump)this.pumps.get(idx)).setPumpSettings(podSettings.getPumpSettingsByIdx(idx));
      }

   }
}