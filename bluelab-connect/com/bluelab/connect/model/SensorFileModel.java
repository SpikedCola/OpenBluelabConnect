package bluelab.connect.model;

import bluelab.connect.c.ControlTypeManager;

public class SensorFileModel {
   protected long id;

   public SensorFileModel() {
      this(0L);
   }

   public SensorFileModel(long var1) {
      this.id = var1;
   }

   public SensorFileModel(ControlTypeManager var1) {
      this.id = var1.getId();
   }

   public long getId() {
      return this.id;
   }
}