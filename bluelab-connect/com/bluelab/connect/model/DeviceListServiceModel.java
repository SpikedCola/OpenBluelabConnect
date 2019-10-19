package bluelab.connect.model;

import bluelab.connect.c.BluelabRemoteXbeeDevice;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DeviceListServiceModel {
   public List<DeviceServiceModel> devices;

   public DeviceListServiceModel(List<BluelabRemoteXbeeDevice> list) {
      this.devices = new ArrayList(list.size());
      Iterator listIterator = list.iterator();

      while(listIterator.hasNext()) {
         BluelabRemoteXbeeDevice dev = (BluelabRemoteXbeeDevice)listIterator.next();
         this.devices.add(new DeviceServiceModel(dev));
      }

   }
}