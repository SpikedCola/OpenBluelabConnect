package bluelab.connect.model;

import bluelab.connect.c.ControlTypeManager;
import bluelab.connect.c.TypeController;
import bluelab.connect.c.Enum_Mode;
import bluelab.connect.c.Enum_DevicePresence;
import bluelab.connect.c.BluelabRemoteXbeeDevice;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

public class DeviceServiceModel {
   protected long id;
   protected String uid;
   protected String name;
   protected String state;
   protected String mode;
   protected boolean alarmsEnabled;
   protected List<SensorServiceModel> sensors;
   protected List<String> warnings;

   public DeviceServiceModel() {
      this.id = 0L;
      this.uid = "";
      this.name = "";
      this.state = Enum_DevicePresence.OFFLINE.getApiText();
      this.mode = Enum_Mode.UNKNOWN.getApiText();
      this.alarmsEnabled = false;
      this.sensors = new ArrayList();
      this.warnings = new ArrayList();
   }

   public DeviceServiceModel(BluelabRemoteXbeeDevice dev) {
      this.id = dev.getId();
      this.uid = dev.get64BitAddress();
      this.name = dev.getName();
      this.state = dev.updateGetDevicePresence().getApiText();
      this.mode = dev.getMode().getApiText();
      this.alarmsEnabled = dev.anyOn();
      this.sensors = new ArrayList();
      Iterator controlTypeManagerMapIterator = dev.getControlTypeControlTypeManagerMap().entrySet().iterator();

      while(controlTypeManagerMapIterator.hasNext()) {
         ControlTypeManager controlTypeManager = (ControlTypeManager)((Entry)controlTypeManagerMapIterator.next()).getValue();
         TypeController typeController = dev.getTypeControllerForType(controlTypeManager.getControlType());
         this.sensors.add(new SensorServiceModel(controlTypeManager, typeController));
      }

      this.warnings = dev.getPodChainChangeUiTextList();
   }

   public long getId() {
      return this.id;
   }

   public String getUid() {
      return this.uid;
   }

   public List<SensorServiceModel> getSensors() {
      return this.sensors;
   }
}