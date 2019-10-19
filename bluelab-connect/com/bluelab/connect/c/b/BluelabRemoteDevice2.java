package bluelab.connect.c.b;

import bluelab.connect.c.BluelabRemoteXbeeDevice;
import bluelab.connect.c.c.BluelabException;
import com.google.common.primitives.Doubles;

public abstract class BluelabRemoteDevice2 extends BluelabRemoteDevice {
   protected BluelabRemoteDevice2(BluelabRemoteXbeeDevice dev) {
      super(dev);
   }

   protected final boolean processWhoAreYouResponse(String[] whoAreYouResponse) {
      if (whoAreYouResponse.length < 2) {
         throw new ArrayIndexOutOfBoundsException("Incomplete 'who are you' response received.");
      } else {
         this.device.setIdent1(whoAreYouResponse[0]);
         this.device.setIdent2(whoAreYouResponse[1].substring(5));
         logger.info("Received 'who are you' response from '{}' ({} {})", new Object[]{this.device.getKeyCode(), this.device.getIdent1(), this.device.getIdent2()});
         return true;
      }
   }

   protected static Boolean ParseBool(String str) {
      Boolean ret;
      if (Doubles.tryParse(str) != null) {
         ret = Double.valueOf(str) > 0.0D;
      } else {
         ret = Boolean.parseBoolean(str);
      }

      return ret;
   }

   protected static bluelab.connect.c.Enum_AlarmState ParseAlarmState(String str) {
      bluelab.connect.c.Enum_AlarmState alarmState;
      if ((str = str.trim().toLowerCase()).endsWith("h")) {
         alarmState = bluelab.connect.c.Enum_AlarmState.HIGH;
      } else if (str.endsWith("l")) {
         alarmState = bluelab.connect.c.Enum_AlarmState.LOW;
      } else if (str.endsWith("-")) {
         alarmState = bluelab.connect.c.Enum_AlarmState.OK;
      } else {
         alarmState = bluelab.connect.c.Enum_AlarmState.OFF;
      }

      return alarmState;
   }

    protected static void CheckSetSettingResponse(byte code, String[] response) 
    throws BluelabException
    {
      if (!String.join(" ", response).contains("OK")) {
         throw new bluelab.connect.c.c.BluelabException(String.format("Setting was not successfully applied (0x%02X).", code));
      }
   }
}