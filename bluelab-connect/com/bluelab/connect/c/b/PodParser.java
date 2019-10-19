package bluelab.connect.c.b;

import bluelab.connect.c.Enum_ControlType;
import bluelab.connect.c.Enum_PumpType;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

final class PodParser {
   private static BiMap<Enum_ControlType, Integer> controlTypeMap;
   private static BiMap<bluelab.connect.c.Enum_Mode, Integer> modeMap;
   private static BiMap<bluelab.connect.c.Enum_Direction, Integer> directionMap;
   private static BiMap<Enum_PumpType, Integer> pumpTypeMap;

   static {
      controlTypeMap = HashBiMap.create();
      controlTypeMap.put(Enum_ControlType.CONDUCTIVITY, 0);
      controlTypeMap.put(Enum_ControlType.TEMPERATURE, 1);
      controlTypeMap.put(Enum_ControlType.PH, 2);
      
      modeMap = HashBiMap.create();
      modeMap.put(bluelab.connect.c.Enum_Mode.MONITOR, 0);
      modeMap.put(bluelab.connect.c.Enum_Mode.CONTROL, 1);
      modeMap.put(bluelab.connect.c.Enum_Mode.CALIBRATION, 2);
      modeMap.put(bluelab.connect.c.Enum_Mode.SETTINGS, 3);
      
      directionMap = HashBiMap.create();
      directionMap.put(bluelab.connect.c.Enum_Direction.OFF, 0);
      directionMap.put(bluelab.connect.c.Enum_Direction.UP, 1);
      directionMap.put(bluelab.connect.c.Enum_Direction.DOWN, 2);
      
      pumpTypeMap = HashBiMap.create();
      pumpTypeMap.put(Enum_PumpType.OFF, 0);
      pumpTypeMap.put(Enum_PumpType.PH, 1);
      pumpTypeMap.put(Enum_PumpType.EC, 2);
   }

   protected static Enum_ControlType GetControlTypeByInt(int var0) {
      return controlTypeMap.containsValue(var0) ? (Enum_ControlType)controlTypeMap.inverse().get(var0) : Enum_ControlType.NONE;
   }

   protected static byte GetControlTypeKeyByteValue(Enum_ControlType var0) {
      return controlTypeMap.containsKey(var0) ? ((Integer)controlTypeMap.get(var0)).byteValue() : -1;
   }

   protected static bluelab.connect.c.Enum_Range ParseRange(int buf) {
      bluelab.connect.c.Enum_Range ret;
      if (bluelab.connect.c.b.BluelabRemoteDevice.TestBit((long)buf, 0)) {
         ret = bluelab.connect.c.Enum_Range.ERROR;
      } else if (bluelab.connect.c.b.BluelabRemoteDevice.TestBit((long)buf, 1)) {
         ret = bluelab.connect.c.Enum_Range.DISCONNECTED;
      } else if (bluelab.connect.c.b.BluelabRemoteDevice.TestBit((long)buf, 2)) {
         ret = bluelab.connect.c.Enum_Range.UNDER_RANGE;
      } else if (bluelab.connect.c.b.BluelabRemoteDevice.TestBit((long)buf, 3)) {
         ret = bluelab.connect.c.Enum_Range.OVER_RANGE;
      } else {
         ret = bluelab.connect.c.Enum_Range.IN_RANGE;
      }

      return ret;
   }

   protected static bluelab.connect.c.Enum_AlarmState ParseAlarmState(boolean defaultOK, int buf) {
      bluelab.connect.c.Enum_AlarmState ret;
      if (bluelab.connect.c.b.BluelabRemoteDevice.TestBit((long)buf, 4)) {
         ret = bluelab.connect.c.Enum_AlarmState.HIGH;
      } else if (bluelab.connect.c.b.BluelabRemoteDevice.TestBit((long)buf, 5)) {
         ret = bluelab.connect.c.Enum_AlarmState.LOW;
      } else if (defaultOK) {
         ret = bluelab.connect.c.Enum_AlarmState.OK;
      } else {
         ret = bluelab.connect.c.Enum_AlarmState.OFF;
      }

      return ret;
   }

   protected static bluelab.connect.c.Enum_Mode GetModeByInt(int var0) {
      return modeMap.containsValue(var0) ? (bluelab.connect.c.Enum_Mode)modeMap.inverse().get(var0) : bluelab.connect.c.Enum_Mode.UNKNOWN;
   }

   protected static byte GetModeValue(bluelab.connect.c.Enum_Mode var0) {
      return modeMap.containsKey(var0) ? ((Integer)modeMap.get(var0)).byteValue() : 0;
   }

   protected static bluelab.connect.c.Enum_Direction GetDirectionByInt(int var0) {
      return directionMap.containsValue(var0) ? (bluelab.connect.c.Enum_Direction)directionMap.inverse().get(var0) : bluelab.connect.c.Enum_Direction.OFF;
   }

   protected static byte GetDirectionValue(bluelab.connect.c.Enum_Direction var0) {
      return directionMap.containsKey(var0) ? ((Integer)directionMap.get(var0)).byteValue() : 0;
   }

   protected static Enum_PumpType GetPumpTypeByInt(int var0) {
      return pumpTypeMap.containsValue(var0) ? (Enum_PumpType)pumpTypeMap.inverse().get(var0) : Enum_PumpType.OFF;
   }

   protected static byte GetPumpTypeValue(Enum_PumpType var0) {
      return pumpTypeMap.containsKey(var0) ? ((Integer)pumpTypeMap.get(var0)).byteValue() : 0;
   }
}