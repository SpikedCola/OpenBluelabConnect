package bluelab.connect.c.b;

import bluelab.connect.c.ControlTypeManager;
import bluelab.connect.c.Enum_ControlType;
import bluelab.connect.c.BluelabRemoteXbeeDevice;
import bluelab.connect.model.PodSettings;
import com.google.common.primitives.Doubles;
import java.nio.ByteBuffer;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import org.slf4j.LoggerFactory;

public class BluelabGuardianRemoteDevice extends BluelabRemoteDevice2 {
   static {
      LoggerFactory.getLogger(BluelabGuardianRemoteDevice.class);
      bluelab.connect.c.Enum_DeviceVersion.GUARDIAN_V1.getId();
   }

   public BluelabGuardianRemoteDevice(BluelabRemoteXbeeDevice var1) {
      super(var1);
   }

   protected final boolean isValid2(ByteBuffer var1) {
      return bluelab.connect.g.SynchronizationMessage.IsValid(var1);
   }

   protected final bluelab.connect.g.ByteArray createDeviceMessage(ByteBuffer var1) {
      return new bluelab.connect.g.SynchronizationMessage(var1);
   }

   protected final void processMessage(bluelab.connect.g.ByteArray var1) {
      String[] splitString;
      if ((splitString = (new String(var1.GetInternalByteArray())).split("\t")).length < 10) {
         throw new ArrayIndexOutOfBoundsException("Incomplete Guardian message received.");
      } else {
         ControlTypeManager conductivityControl = this.device.getControlTypeManagerForType(Enum_ControlType.CONDUCTIVITY);
         ControlTypeManager temperatureControl = this.device.getControlTypeManagerForType(Enum_ControlType.TEMPERATURE);
         ControlTypeManager phControl = this.device.getControlTypeManagerForType(Enum_ControlType.PH);
         Instant inst = this.instant.GetInstant();
         this.device.getControlTypeControlTypeManagerMap().forEach((controlType, controlTypeManager) -> {
            controlTypeManager.setInstant(inst);
         });
         ProcessUnderOverRange(conductivityControl, splitString[0]);
         ProcessUnderOverRange(temperatureControl, splitString[1]);
         ProcessUnderOverRange(phControl, splitString[3]);
         phControl.setDueForUpdate(splitString[6].equalsIgnoreCase("Due"));
         conductivityControl.setAlarmState(ParseAlarmState(splitString[7]));
         temperatureControl.setAlarmState(ParseAlarmState(splitString[8]));
         phControl.setAlarmState(ParseAlarmState(splitString[9]));
         this.device.addRemoveChangeType(bluelab.connect.c.Enum_ChangeType.DEVICE_SETTING, false);
      }
   }

   public final List<bluelab.connect.g.DeviceMessage> getDeviceMessages(bluelab.connect.c.c.MonitorController var1) {
      return new ArrayList(0);
   }

   public final List<bluelab.connect.g.DeviceMessage> getDeviceMessages(PodSettings var1, byte var2) {
      return new ArrayList(0);
   }

   private static void ProcessUnderOverRange(ControlTypeManager controlTypeMgr, String str) {
      Double var2;
      if ((var2 = Doubles.tryParse(str = str.trim())) == null) {
         if (str.equalsIgnoreCase("Ur")) {
            controlTypeMgr.setRange(bluelab.connect.c.Enum_Range.UNDER_RANGE);
            controlTypeMgr.setCurrentValue(controlTypeMgr.getMinValue());
         } else if (str.equalsIgnoreCase("Or")) {
            controlTypeMgr.setRange(bluelab.connect.c.Enum_Range.OVER_RANGE);
            controlTypeMgr.setCurrentValue(controlTypeMgr.getMaxValue());
         } else {
            controlTypeMgr.setRange(bluelab.connect.c.Enum_Range.ERROR);
         }
      } else {
         if (var2 <= -100.0D) {
            controlTypeMgr.setRange(bluelab.connect.c.Enum_Range.ERROR);
         } else if (var2 >= controlTypeMgr.getMinValue() && var2 != 52.0D) {
            if (var2 > controlTypeMgr.getMaxValue()) {
               controlTypeMgr.setRange(bluelab.connect.c.Enum_Range.OVER_RANGE);
            } else {
               controlTypeMgr.setRange(bluelab.connect.c.Enum_Range.IN_RANGE);
            }
         } else {
            controlTypeMgr.setRange(bluelab.connect.c.Enum_Range.UNDER_RANGE);
         }

         controlTypeMgr.setCurrentValue(var2);
      }
   }
}