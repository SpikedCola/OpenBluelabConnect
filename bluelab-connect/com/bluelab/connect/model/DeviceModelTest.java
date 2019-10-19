package bluelab.connect.model;

import bluelab.connect.c.Enum_ControlType;
import bluelab.connect.c.BluelabGuardianRemoteXbeeDevice;
import bluelab.connect.c.BluelabRemoteXbeeDevice;
import org.junit.Assert;
import org.junit.Test;

public class DeviceModelTest {
   @Test
   public void testTransferIds() {
      DeviceFileModel model = new DeviceFileModel();
      model.id = 123456L;
      model.sensors.put(Enum_ControlType.CONDUCTIVITY, new SensorFileModel(234L));
      model.sensors.put(Enum_ControlType.PH, new SensorFileModel(345L));
      model.sensors.put(Enum_ControlType.TEMPERATURE, new SensorFileModel(456L));
      BluelabRemoteXbeeDevice dev = new BluelabRemoteXbeeDevice(model);
      Assert.assertEquals(123456L, dev.getId());
      Assert.assertEquals(234L, dev.getIdForControlType(Enum_ControlType.CONDUCTIVITY));
      Assert.assertEquals(345L, dev.getIdForControlType(Enum_ControlType.PH));
      Assert.assertEquals(456L, dev.getIdForControlType(Enum_ControlType.TEMPERATURE));
      BluelabGuardianRemoteXbeeDevice guardianDev = new BluelabGuardianRemoteXbeeDevice(dev);
      Assert.assertEquals(123456L, guardianDev.getId());
      Assert.assertEquals(234L, guardianDev.getIdForControlType(Enum_ControlType.CONDUCTIVITY));
      Assert.assertEquals(345L, guardianDev.getIdForControlType(Enum_ControlType.PH));
      Assert.assertEquals(456L, guardianDev.getIdForControlType(Enum_ControlType.TEMPERATURE));
   }
}