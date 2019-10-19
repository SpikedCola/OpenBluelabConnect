package bluelab.connect.model;

import bluelab.connect.c.ControlTypeManager;
import bluelab.connect.c.Enum_ControlType;
import bluelab.connect.c.Enum_DeviceType;
import bluelab.connect.c.BluelabRemoteXbeeDevice;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class DeviceFileModel {
   protected String keycode;
   protected String address;
   protected String name;
   protected Enum_DeviceType type;
   protected long id;
   protected Map<Enum_ControlType, SensorFileModel> sensors;

   public DeviceFileModel() {
      this.keycode = "";
      this.address = "";
      this.name = "";
      this.type = Enum_DeviceType.UNKNOWN;
      this.id = 0L;
      this.sensors = new LinkedHashMap();
   }

   public DeviceFileModel(BluelabRemoteXbeeDevice dev) {
      this.keycode = dev.getKeyCode();
      this.address = dev.get64BitAddress();
      this.name = dev.getName();
      this.type = dev.getDeviceType();
      this.id = dev.getId();
      this.sensors = new LinkedHashMap();
      Iterator iterator = dev.getControlTypeControlTypeManagerMap().entrySet().iterator();

      while(iterator.hasNext()) {
         ControlTypeManager mgr = (ControlTypeManager)((Entry)iterator.next()).getValue();
         this.sensors.put(mgr.getControlType(), new SensorFileModel(mgr));
      }

   }

   public String getKeycode() {
      return this.keycode;
   }

   public void setKeycode(String var1) {
      this.keycode = var1;
   }

   public String getAddress() {
      return this.address;
   }

   public String getName() {
      return this.name;
   }

   public Enum_DeviceType getType() {
      return this.type;
   }

   public long getId() {
      return this.id;
   }

   public Map<Enum_ControlType, SensorFileModel> getSensors() {
      return this.sensors;
   }
}