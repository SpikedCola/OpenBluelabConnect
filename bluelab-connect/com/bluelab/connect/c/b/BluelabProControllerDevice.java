package bluelab.connect.c.b;

import bluelab.connect.c.ControlTypeManager;
import bluelab.connect.c.Enum_ControlType;
import bluelab.connect.c.BluelabRemoteXbeeDevice;
import bluelab.connect.c.c.BluelabException;
import bluelab.connect.model.PodSettings;
import com.google.common.primitives.Doubles;
import java.nio.ByteBuffer;
import java.time.Instant;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.function.BiConsumer;
import org.slf4j.LoggerFactory;

public class BluelabProControllerDevice extends BluelabRemoteDevice2 {
   private EnumSet<Enum_Commands3> d;
   // $FF: synthetic field
   private static int[] e;

   static {
      LoggerFactory.getLogger(BluelabProControllerDevice.class);
   }

   public BluelabProControllerDevice(BluelabRemoteXbeeDevice var1) {
      super(var1);
      if (var1 != null) {
         var1.getControlTypeControllerMap().forEach((var0, var1x) -> {
            var1x.setMonthDate((bluelab.connect.c.a.MonthDate)(new bluelab.connect.c.a.ProControllerMonthDate()));
         });
      }

      this.d = EnumSet.noneOf(Enum_Commands3.class);
   }

   protected final boolean isValid2(ByteBuffer var1) {
      return bluelab.connect.g.DeviceMessage.IsValid2(var1);
   }

   protected final bluelab.connect.g.ByteArray createDeviceMessage(ByteBuffer var1) {
      return new bluelab.connect.g.DeviceMessage(var1);
   }

    protected final void processMessage(bluelab.connect.g.ByteArray var1) throws BluelabException {
        String[] tabArray = (new String(var1.GetInternalByteArray())).split("\t");
        byte var10;
        Enum_Commands3 var3 = Enum_Commands3.GetCommandByInt(var10 = ((bluelab.connect.g.DeviceMessage) var1).getCommand());
        this.d.add(var3);
        ControlTypeManager var13;
        ControlTypeManager var4;
        ControlTypeManager var5;
        bluelab.connect.c.TypeController var6;
        bluelab.connect.c.TypeController var7;
        bluelab.connect.c.TypeController var8;
        switch (d()[var3.ordinal()]) {
            case 1:
                this.processWhoAreYouResponse((String[]) tabArray);
                return;
            case 2:
                if (tabArray.length < 12) {
                    throw new ArrayIndexOutOfBoundsException("Incomplete Pro Controller response 1 message received.");
                }

                var13 = this.device.getControlTypeManagerForType(Enum_ControlType.CONDUCTIVITY);
                var4 = this.device.getControlTypeManagerForType(Enum_ControlType.TEMPERATURE);
                var5 = this.device.getControlTypeManagerForType(Enum_ControlType.PH);
                var6 = this.device.getTypeControllerForType(Enum_ControlType.CONDUCTIVITY);
                var7 = this.device.getTypeControllerForType(Enum_ControlType.TEMPERATURE);
                var8 = this.device.getTypeControllerForType(Enum_ControlType.PH);
                Instant var9 = this.instant.GetInstant();
                this.device.getControlTypeControlTypeManagerMap().forEach((var1x, var2x) -> {
                    var2x.setInstant(var9);
                });
                ParseSetRange(var13, tabArray[0]);
                ParseSetRange(var4, tabArray[1]);
                ParseSetRange(var5, tabArray[2]);
                BluelabRemoteXbeeDevice dev = this.device;
                String modeStr = tabArray[4];
                bluelab.connect.c.Enum_Mode mode;
                if (modeStr.equals("M")) {
                    mode = bluelab.connect.c.Enum_Mode.MONITOR;
                } else if (modeStr.equals("C")) {
                    mode = bluelab.connect.c.Enum_Mode.CONTROL;
                } else if (modeStr.equals("S")) {
                    mode = bluelab.connect.c.Enum_Mode.SETTINGS;
                } else {
                    mode = bluelab.connect.c.Enum_Mode.UNKNOWN;
                }

                dev.setMode(mode);
                SetClearLockoutIfL(var6, tabArray[5]);
                SetClearLockoutIfL(var7, tabArray[6]);
                SetClearLockoutIfL(var8, tabArray[7]);
                var13.setAlarmState(AlarmStateDirectionParser.GetAlarmStateByString(tabArray[8]));
                var4.setAlarmState(AlarmStateDirectionParser.GetAlarmStateByString(tabArray[9]));
                var5.setAlarmState(AlarmStateDirectionParser.GetAlarmStateByString(tabArray[10]));
                this.c();
                return;
            case 3:
                if (tabArray.length < 8) {
                    throw new ArrayIndexOutOfBoundsException("Incomplete Pro Controller response 2 message received.");
                }

                bluelab.connect.c.TypeController var14 = this.device.getTypeControllerForType(Enum_ControlType.CONDUCTIVITY);
                bluelab.connect.c.TypeController var15 = this.device.getTypeControllerForType(Enum_ControlType.TEMPERATURE);
                bluelab.connect.c.TypeController var16 = this.device.getTypeControllerForType(Enum_ControlType.PH);
                var14.setDirection(AlarmStateDirectionParser.GetDirectionByString(tabArray[0]));
                var15.setDirection(AlarmStateDirectionParser.GetDirectionByString(tabArray[1]));
                var16.setDirection(AlarmStateDirectionParser.GetDirectionByString(tabArray[2]));
                boolean var17;
                boolean var18 = (var17 = ParseBool((String) tabArray[3])) ? ParseBool((String) tabArray[4]) : false;
                boolean var19 = var17 ? ParseBool((String) tabArray[5]) : false;
                this.device.getControlTypeControllerMap().forEach((var2x, var3x) -> {
                    var3x.addRemoveLockout(bluelab.connect.c.Enum_Lockout.NORMALLY_OPEN, var18);
                    var3x.addRemoveLockout(bluelab.connect.c.Enum_Lockout.NORMALLY_CLOSED, var19);
                });
                this.device.lockoutListAddRemove(bluelab.connect.c.Enum_Lockout.LOW_CONDUCTIVITY, Double.valueOf(tabArray[6]) > 0.1D);
                this.device.lockoutListAddRemove(bluelab.connect.c.Enum_Lockout.INEFFECTIVE_CONTROL, ParseBool((String) tabArray[7]));
                this.c();
                return;
            case 4:
                if (tabArray.length < 20) {
                    throw new ArrayIndexOutOfBoundsException("Incomplete Pro Controller response 3 message received.");
                }

                var13 = this.device.getControlTypeManagerForType(Enum_ControlType.CONDUCTIVITY);
                var4 = this.device.getControlTypeManagerForType(Enum_ControlType.TEMPERATURE);
                var5 = this.device.getControlTypeManagerForType(Enum_ControlType.PH);
                var6 = this.device.getTypeControllerForType(Enum_ControlType.CONDUCTIVITY);
                var7 = this.device.getTypeControllerForType(Enum_ControlType.TEMPERATURE);
                var8 = this.device.getTypeControllerForType(Enum_ControlType.PH);
                var6.SetDeltaValue1And2(Double.valueOf(tabArray[0]));
                var7.setDeltaValue1(Double.valueOf(tabArray[1]));
                var7.setDeltaValue2(Double.valueOf(tabArray[2]));
                var8.SetDeltaValue1And2(Double.valueOf(tabArray[3]));
                var6.setOnTime(Integer.valueOf(tabArray[4]));
                var6.setOffTime(Integer.valueOf(tabArray[5]));
                var7.setOnTime(Integer.valueOf(tabArray[6]));
                var7.setOffTime(Integer.valueOf(tabArray[7]));
                var8.setOnTime(Integer.valueOf(tabArray[8]));
                var8.setOffTime(Integer.valueOf(tabArray[9]));
                var13.setMinMaxValueMinValue(Double.valueOf(tabArray[11]));
                var13.setMinMaxValueMaxValue(Double.valueOf(tabArray[12]));
                var4.setMinMaxValueMinValue(Double.valueOf(tabArray[13]));
                var4.setMinMaxValueMaxValue(Double.valueOf(tabArray[14]));
                var5.setMinMaxValueMinValue(Double.valueOf(tabArray[15]));
                var5.setMinMaxValueMaxValue(Double.valueOf(tabArray[16]));
                a(var6, Integer.valueOf(tabArray[17]));
                a(var7, Integer.valueOf(tabArray[18]));
                a(var8, Integer.valueOf(tabArray[19]));
                this.c();
                return;
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
                CheckSetSettingResponse(var10, tabArray);
            default:
        }
    }

   private void c() {
      if (this.d.containsAll(EnumSet.of(Enum_Commands3.GET_DATA_1, Enum_Commands3.GET_DATA_2, Enum_Commands3.GET_DATA_3))) {
         this.device.addRemoveChangeType(bluelab.connect.c.Enum_ChangeType.DEVICE_SETTING, false);
      }

   }

   public final List<bluelab.connect.g.DeviceMessage> getDeviceMessages(bluelab.connect.c.c.MonitorController var1) {
      bluelab.connect.c.c.MinMaxValue var2 = var1.getMinMaxValue(Enum_ControlType.CONDUCTIVITY);
      bluelab.connect.c.c.ControlTypeInstance var3 = var1.getControlTypeInstance(Enum_ControlType.CONDUCTIVITY);
      bluelab.connect.c.c.MinMaxValue var4 = var1.getMinMaxValue(Enum_ControlType.PH);
      bluelab.connect.c.c.ControlTypeInstance var5 = var1.getControlTypeInstance(Enum_ControlType.PH);
      bluelab.connect.c.c.MinMaxValue var6 = var1.getMinMaxValue(Enum_ControlType.TEMPERATURE);
      bluelab.connect.c.c.ControlTypeInstance var7 = var1.getControlTypeInstance(Enum_ControlType.TEMPERATURE);
      ArrayList var8 = new ArrayList();
      String var9 = String.format("%.1f\t%.0f\t%.0f\t%.1f", var3.deltaValue2, var7.deltaValue1, var7.deltaValue2, var5.deltaValue2);
      var8.add(new bluelab.connect.g.DeviceMessage(Enum_Commands3.SET_REQUIRED.getValue(), var9.getBytes()));
      var9 = String.format("%s\t%s", var1.mode.equals(bluelab.connect.c.Enum_Mode.CONTROL) ? "1" : "0", var1.turnAlarmsOnOff ? "1" : "0");
      var8.add(new bluelab.connect.g.DeviceMessage(Enum_Commands3.SET_MODE.getValue(), var9.getBytes()));
      var9 = String.format("%d\t%d", var3.onTime, var3.offTime);
      var8.add(new bluelab.connect.g.DeviceMessage(Enum_Commands3.SET_EC_ON_OFF_TIME.getValue(), var9.getBytes()));
      var9 = String.format("%d\t%d", var7.onTime, var7.offTime);
      var8.add(new bluelab.connect.g.DeviceMessage(Enum_Commands3.SET_TEMP_ON_OFF_TIME.getValue(), var9.getBytes()));
      var9 = String.format("%d\t%d", var5.onTime, var5.offTime);
      var8.add(new bluelab.connect.g.DeviceMessage(Enum_Commands3.SET_PH_ON_OFF_TIME.getValue(), var9.getBytes()));
      var9 = String.format("%.1f\t%.1f", var2.minValue, var2.maxValue);
      var8.add(new bluelab.connect.g.DeviceMessage(Enum_Commands3.SET_EC_ALARM.getValue(), var9.getBytes()));
      var9 = String.format("%.0f\t%.0f", var6.minValue, var6.maxValue);
      var8.add(new bluelab.connect.g.DeviceMessage(Enum_Commands3.SET_TEMP_ALARM.getValue(), var9.getBytes()));
      var9 = String.format("%.1f\t%.1f", var4.minValue, var4.maxValue);
      var8.add(new bluelab.connect.g.DeviceMessage(Enum_Commands3.SET_PH_ALARM.getValue(), var9.getBytes()));
      var9 = String.format("%s", var1.lowConductivityLockout ? "1" : "0");
      var8.add(new bluelab.connect.g.DeviceMessage(Enum_Commands3.SET_LOW_EC_LOCKOUT.getValue(), var9.getBytes()));
      var9 = String.format("%s", var1.ineffectiveControlLockout ? "1" : "0");
      var8.add(new bluelab.connect.g.DeviceMessage(Enum_Commands3.SET_IC_LOCKOUT.getValue(), var9.getBytes()));
      var8.add(new bluelab.connect.g.DeviceMessage(Enum_Commands3.APPLY_SETTINGS.getValue(), (byte[])null));
      return var8;
   }

   public final List<bluelab.connect.g.DeviceMessage> getDeviceMessages(PodSettings var1, byte var2) {
      return new ArrayList(0);
   }

   private static void a(bluelab.connect.c.TypeController var0, int var1) {
      bluelab.connect.c.a.ProControllerMonthDate var2 = (bluelab.connect.c.a.ProControllerMonthDate)var0.getMonthDate();
      var2.setOnTime(var0.getOnTime());
      var2.b(var1);
   }

   private static void ParseSetRange(ControlTypeManager mgr, String str) {
      Double val;
      if ((val = Doubles.tryParse(str.trim())) != null) {
         if (val <= -100.0D) {
            mgr.setRange(bluelab.connect.c.Enum_Range.ERROR);
         } else if (val < mgr.getMinValue()) {
            mgr.setRange(bluelab.connect.c.Enum_Range.UNDER_RANGE);
         } else if (val > mgr.getMaxValue()) {
            mgr.setRange(bluelab.connect.c.Enum_Range.OVER_RANGE);
         } else {
            mgr.setRange(bluelab.connect.c.Enum_Range.IN_RANGE);
         }

         mgr.setCurrentValue(val);
      } else {
         mgr.setRange(bluelab.connect.c.Enum_Range.ERROR);
      }
   }

   private static void SetClearLockoutIfL(bluelab.connect.c.TypeController controller, String str) {
      controller.addRemoveLockout(bluelab.connect.c.Enum_Lockout.INEFFECTIVE_CONTROL, str.equals("L"));
   }

   // $FF: synthetic method
   private static int[] d() {
      int[] var10000 = e;
      if (e != null) {
         return var10000;
      } else {
         int[] var0 = new int[Enum_Commands3.values().length];

         try {
            var0[Enum_Commands3.APPLY_SETTINGS.ordinal()] = 15;
         } catch (NoSuchFieldError var16) {
            ;
         }

         try {
            var0[Enum_Commands3.GET_DATA_1.ordinal()] = 2;
         } catch (NoSuchFieldError var15) {
            ;
         }

         try {
            var0[Enum_Commands3.GET_DATA_2.ordinal()] = 3;
         } catch (NoSuchFieldError var14) {
            ;
         }

         try {
            var0[Enum_Commands3.GET_DATA_3.ordinal()] = 4;
         } catch (NoSuchFieldError var13) {
            ;
         }

         try {
            var0[Enum_Commands3.NONE.ordinal()] = 16;
         } catch (NoSuchFieldError var12) {
            ;
         }

         try {
            var0[Enum_Commands3.SET_EC_ALARM.ordinal()] = 10;
         } catch (NoSuchFieldError var11) {
            ;
         }

         try {
            var0[Enum_Commands3.SET_EC_ON_OFF_TIME.ordinal()] = 7;
         } catch (NoSuchFieldError var10) {
            ;
         }

         try {
            var0[Enum_Commands3.SET_IC_LOCKOUT.ordinal()] = 14;
         } catch (NoSuchFieldError var9) {
            ;
         }

         try {
            var0[Enum_Commands3.SET_LOW_EC_LOCKOUT.ordinal()] = 13;
         } catch (NoSuchFieldError var8) {
            ;
         }

         try {
            var0[Enum_Commands3.SET_MODE.ordinal()] = 6;
         } catch (NoSuchFieldError var7) {
            ;
         }

         try {
            var0[Enum_Commands3.SET_PH_ALARM.ordinal()] = 12;
         } catch (NoSuchFieldError var6) {
            ;
         }

         try {
            var0[Enum_Commands3.SET_PH_ON_OFF_TIME.ordinal()] = 9;
         } catch (NoSuchFieldError var5) {
            ;
         }

         try {
            var0[Enum_Commands3.SET_REQUIRED.ordinal()] = 5;
         } catch (NoSuchFieldError var4) {
            ;
         }

         try {
            var0[Enum_Commands3.SET_TEMP_ALARM.ordinal()] = 11;
         } catch (NoSuchFieldError var3) {
            ;
         }

         try {
            var0[Enum_Commands3.SET_TEMP_ON_OFF_TIME.ordinal()] = 8;
         } catch (NoSuchFieldError var2) {
            ;
         }

         try {
            var0[Enum_Commands3.WHO_ARE_YOU.ordinal()] = 1;
         } catch (NoSuchFieldError var1) {
            ;
         }

         e = var0;
         return var0;
      }
   }
}