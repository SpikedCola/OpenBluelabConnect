package bluelab.connect.model;

import bluelab.connect.c.PeripodWithPumps;
import bluelab.connect.c.Enum_PeripodType;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class PodSettings {
   public String name;
   private List<PumpSettings> pumps;
   private String productVersion;
   private String firmwareVersion;
   private Enum_PeripodType peripod;

   public PodSettings() {
      this.name = "";
      this.pumps = new ArrayList();
      this.productVersion = "";
      this.firmwareVersion = "";
      this.peripod = Enum_PeripodType.UNKNOWN;
   }

   public PodSettings(PodSettings podSettings) {
      this.name = podSettings.name;
      this.pumps = new ArrayList(podSettings.pumps.size());
      podSettings.pumps.forEach((var1x) -> {
         this.pumps.add(new PumpSettings(var1x));
      });
      this.productVersion = podSettings.productVersion;
      this.firmwareVersion = podSettings.firmwareVersion;
      this.peripod = podSettings.peripod;
   }

   public PodSettings(PeripodWithPumps peripodWithPumps, String unit) {
      this.name = peripodWithPumps.getName();
      this.pumps = new ArrayList();
      peripodWithPumps.getPumps().forEach((pump) -> {
         pump.setUnit(unit);
         this.pumps.add(new PumpSettings(pump.getPumpSettings()));
      });
      this.productVersion = peripodWithPumps.getProductVersion();
      this.firmwareVersion = peripodWithPumps.getFirmwareVersion();
      this.peripod = peripodWithPumps.getPeripod();
   }

   public List<PumpSettings> getPumpSettings() {
      return this.pumps;
   }

   public PumpSettings getPumpSettingsByIdx(int idx) {
      return (PumpSettings)this.pumps.get(idx);
   }

   public void disablePumps() {
      this.pumps.forEach((pumpSettings) -> {
         pumpSettings.disable();
      });
   }
}