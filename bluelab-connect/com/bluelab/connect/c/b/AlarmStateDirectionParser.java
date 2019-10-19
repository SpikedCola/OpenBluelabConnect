package bluelab.connect.c.b;

final class AlarmStateDirectionParser {
   protected static bluelab.connect.c.Enum_AlarmState GetAlarmStateByString(String var0) {
      bluelab.connect.c.Enum_AlarmState ret;
      if (var0.equalsIgnoreCase("hi")) {
         ret = bluelab.connect.c.Enum_AlarmState.HIGH;
      } else if (var0.equalsIgnoreCase("lo")) {
         ret = bluelab.connect.c.Enum_AlarmState.LOW;
      } else if (var0.equalsIgnoreCase("ok")) {
         ret = bluelab.connect.c.Enum_AlarmState.OK;
      } else {
         ret = bluelab.connect.c.Enum_AlarmState.OFF;
      }

      return ret;
   }

   protected static bluelab.connect.c.Enum_Direction GetDirectionByString(String var0) {
      bluelab.connect.c.Enum_Direction ret;
      if (var0.equals("2")) {
         ret = bluelab.connect.c.Enum_Direction.DOWN;
      } else if (var0.equals("1")) {
         ret = bluelab.connect.c.Enum_Direction.UP;
      } else {
         ret = bluelab.connect.c.Enum_Direction.OFF;
      }

      return ret;
   }
}