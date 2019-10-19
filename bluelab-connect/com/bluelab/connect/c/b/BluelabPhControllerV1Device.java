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

public class BluelabPhControllerV1Device extends BluelabRemoteDevice2 {
   private EnumSet<Enum_Commands2> commandList;
   // $FF: synthetic field
   private static int[] e;

   static {
      LoggerFactory.getLogger(BluelabPhControllerV1Device.class);
   }

   public BluelabPhControllerV1Device(BluelabRemoteXbeeDevice var1) {
      super(var1);
      if (var1 != null) {
         var1.getControlTypeControllerMap().forEach((var0, var1x) -> {
            var1x.setMonthDate((bluelab.connect.c.a.MonthDate)(new bluelab.connect.c.a.PhControllerMonthDate()));
         });
      }

      this.commandList = EnumSet.noneOf(Enum_Commands2.class);
   }

   protected final boolean isValid2(ByteBuffer var1) {
      return bluelab.connect.g.DeviceMessage.IsValid2(var1);
   }

   protected final bluelab.connect.g.ByteArray createDeviceMessage(ByteBuffer var1) {
      return new bluelab.connect.g.DeviceMessage(var1);
   }

   protected final void processMessage(bluelab.connect.g.ByteArray buf) throws BluelabException {
      String[] tabArray = (new String(buf.GetInternalByteArray())).split("\t");
      byte commandByte;
      Enum_Commands2 command = Enum_Commands2.GetCommandByInt(commandByte = ((bluelab.connect.g.DeviceMessage)buf).getCommand());
      this.commandList.add(command);
      ControlTypeManager var22;
      switch(d()[command.ordinal()]) {
      case 1:
         this.processWhoAreYouResponse((String[])tabArray);
         return;
      case 2:
         if (tabArray.length < 8) {
            throw new ArrayIndexOutOfBoundsException("Incomplete pH Controller response 1 message received.");
         }

         var22 = this.device.getControlTypeManagerForType(Enum_ControlType.PH);
         ControlTypeManager var25 = this.device.getControlTypeManagerForType(Enum_ControlType.TEMPERATURE);
         bluelab.connect.c.TypeController var10 = this.device.getTypeControllerForType(Enum_ControlType.PH);
         Instant var26 = this.instant.GetInstant();
         this.device.getControlTypeControlTypeManagerMap().forEach((var1x, var2x) -> {
            var2x.setInstant(var26);
         });
         ParseSetUnderOverRange(var22, tabArray[0]);
         var10.SetDeltaValue1And2(Double.valueOf(tabArray[1]));
         var22.setAlarmState(ParseAlarmState(tabArray[2]));
         BluelabRemoteXbeeDevice dev = this.device;
         String str;
         bluelab.connect.c.Enum_Mode mode;
         if ((str = tabArray[3]).equals("M")) {
            mode = bluelab.connect.c.Enum_Mode.MONITOR;
         } else if (!str.equals("C") && !str.equals("L")) {
            if (str.equals(str.toLowerCase())) {
               mode = bluelab.connect.c.Enum_Mode.SETTINGS;
            } else {
               mode = bluelab.connect.c.Enum_Mode.UNKNOWN;
            }
         } else {
            mode = bluelab.connect.c.Enum_Mode.CONTROL;
         }

         dev.setMode(mode);
         var10.addRemoveLockout(bluelab.connect.c.Enum_Lockout.INEFFECTIVE_CONTROL, tabArray[3].equals("L"));
         ParseSetUnderOverRange(var25, tabArray[4].substring(1));
         var22.setDueForUpdate(ParseBool((String)tabArray[5].substring(2)));
         bluelab.connect.c.Enum_Direction dir;
         if ((str = tabArray[6]).equalsIgnoreCase("DD0")) {
            dir = bluelab.connect.c.Enum_Direction.DOWN;
         } else if (str.equalsIgnoreCase("DD1")) {
            dir = bluelab.connect.c.Enum_Direction.UP;
         } else {
            dir = bluelab.connect.c.Enum_Direction.OFF;
         }

         var10.setDirection(dir);
         this.clearDeviceSettingCommand();
         return;
      case 3:
         if (tabArray.length < 5) {
            throw new ArrayIndexOutOfBoundsException("Incomplete pH Controller response 2 message received.");
         }

         var22 = this.device.getControlTypeManagerForType(Enum_ControlType.PH);
         bluelab.connect.c.TypeController var24 = this.device.getTypeControllerForType(Enum_ControlType.PH);
         var22.setMinMaxValueMinValue(Double.valueOf(tabArray[1].substring(1)));
         var22.setMinMaxValueMaxValue(Double.valueOf(tabArray[2].substring(1)));
         var24.setOnTime(Integer.valueOf(tabArray[3].substring(1)));
         var24.setOffTime(Integer.valueOf(tabArray[4].substring(1)) * 60);
         this.clearDeviceSettingCommand();
         return;
      case 4:
         if (tabArray.length < 5) {
            throw new ArrayIndexOutOfBoundsException("Incomplete pH Controller response 3 message received.");
         }

         bluelab.connect.c.TypeController var21 = this.device.getTypeControllerForType(Enum_ControlType.PH);
         long var9 = (long)bluelab.connect.d.WeirdEncoder.TimeToInteger(tabArray[3].substring(1));
         long var11 = (long)(bluelab.connect.d.WeirdEncoder.TimeToInteger(tabArray[4].substring(1)) * 3600);
         BluelabRemoteXbeeDevice var18 = this.device;
         bluelab.connect.c.a.PhControllerMonthDate var19 = (bluelab.connect.c.a.PhControllerMonthDate)var21.getMonthDate();
         var19.setOnTime(var21.getOnTime());
         var19.setOnTime(var9);
         return;
      case 5:
      case 6:
      case 7:
      case 8:
      case 9:
      case 10:
      case 11:
      case 12:
         CheckSetSettingResponse(commandByte, tabArray);
      default:
      }
   }

   private void clearDeviceSettingCommand() {
      if (this.commandList.containsAll(EnumSet.of(Enum_Commands2.GET_DATA_1, Enum_Commands2.GET_DATA_2))) {
         this.device.addRemoveChangeType(bluelab.connect.c.Enum_ChangeType.DEVICE_SETTING, false);
      }

   }

   public final List<bluelab.connect.g.DeviceMessage> getDeviceMessages(bluelab.connect.c.c.MonitorController var1) {
      bluelab.connect.c.c.MinMaxValue var2 = var1.getMinMaxValue(Enum_ControlType.PH);
      bluelab.connect.c.c.ControlTypeInstance var3 = var1.getControlTypeInstance(Enum_ControlType.PH);
      ArrayList var4 = new ArrayList();
      String var5 = String.format("%s", var1.mode.equals(bluelab.connect.c.Enum_Mode.CONTROL) ? "1" : "0");
      var4.add(new bluelab.connect.g.DeviceMessage(Enum_Commands2.SET_MODE.getValue(), var5.getBytes()));
      var5 = String.format("%.1f", var3.deltaValue2);
      var4.add(new bluelab.connect.g.DeviceMessage(Enum_Commands2.SET_REQUIRED.getValue(), var5.getBytes()));
      var5 = String.format("%s", var1.turnAlarmsOnOff ? "1" : "0");
      var4.add(new bluelab.connect.g.DeviceMessage(Enum_Commands2.SET_ALARM_ENABLED.getValue(), var5.getBytes()));
      var5 = String.format("%.1f", var2.minValue);
      var4.add(new bluelab.connect.g.DeviceMessage(Enum_Commands2.SET_LOW_ALARM.getValue(), var5.getBytes()));
      var5 = String.format("%.1f", var2.maxValue);
      var4.add(new bluelab.connect.g.DeviceMessage(Enum_Commands2.SET_HIGH_ALARM.getValue(), var5.getBytes()));
      var5 = String.format("%d", var3.onTime);
      var4.add(new bluelab.connect.g.DeviceMessage(Enum_Commands2.SET_ON_TIME.getValue(), var5.getBytes()));
      var5 = String.format("%.0f", (double)var3.offTime / 60.0D);
      var4.add(new bluelab.connect.g.DeviceMessage(Enum_Commands2.SET_OFF_TIME.getValue(), var5.getBytes()));
      var4.add(new bluelab.connect.g.DeviceMessage(Enum_Commands2.APPLY_SETTINGS.getValue(), (byte[])null));
      return var4;
   }

   public final List<bluelab.connect.g.DeviceMessage> getDeviceMessages(PodSettings var1, byte var2) {
      return new ArrayList(0);
   }

   private static void ParseSetUnderOverRange(ControlTypeManager manager, String str) {
      Double val;
      if ((val = Doubles.tryParse(str = str.trim())) != null) {
         if (val < manager.getMinValue()) {
            manager.setRange(bluelab.connect.c.Enum_Range.UNDER_RANGE);
         } else if (val > manager.getMaxValue()) {
            manager.setRange(bluelab.connect.c.Enum_Range.OVER_RANGE);
         } else {
            manager.setRange(bluelab.connect.c.Enum_Range.IN_RANGE);
         }

         manager.setCurrentValue(val);
      } else if (str.endsWith("L")) {
         manager.setRange(bluelab.connect.c.Enum_Range.UNDER_RANGE);
         manager.setCurrentValue(manager.getMinValue());
      } else if (str.endsWith("H")) {
         manager.setRange(bluelab.connect.c.Enum_Range.OVER_RANGE);
         manager.setCurrentValue(manager.getMaxValue());
      } else {
         manager.setRange(bluelab.connect.c.Enum_Range.ERROR);
      }
   }

   // $FF: synthetic method
   private static int[] d() {
      int[] var10000 = e;
      if (e != null) {
         return var10000;
      } else {
         int[] var0 = new int[Enum_Commands2.values().length];

         try {
            var0[Enum_Commands2.APPLY_SETTINGS.ordinal()] = 12;
         } catch (NoSuchFieldError var13) {
            ;
         }

         try {
            var0[Enum_Commands2.GET_DATA_1.ordinal()] = 2;
         } catch (NoSuchFieldError var12) {
            ;
         }

         try {
            var0[Enum_Commands2.GET_DATA_2.ordinal()] = 3;
         } catch (NoSuchFieldError var11) {
            ;
         }

         try {
            var0[Enum_Commands2.GET_DATA_3.ordinal()] = 4;
         } catch (NoSuchFieldError var10) {
            ;
         }

         try {
            var0[Enum_Commands2.NONE.ordinal()] = 13;
         } catch (NoSuchFieldError var9) {
            ;
         }

         try {
            var0[Enum_Commands2.SET_ALARM_ENABLED.ordinal()] = 7;
         } catch (NoSuchFieldError var8) {
            ;
         }

         try {
            var0[Enum_Commands2.SET_HIGH_ALARM.ordinal()] = 9;
         } catch (NoSuchFieldError var7) {
            ;
         }

         try {
            var0[Enum_Commands2.SET_LOW_ALARM.ordinal()] = 8;
         } catch (NoSuchFieldError var6) {
            ;
         }

         try {
            var0[Enum_Commands2.SET_MODE.ordinal()] = 5;
         } catch (NoSuchFieldError var5) {
            ;
         }

         try {
            var0[Enum_Commands2.SET_OFF_TIME.ordinal()] = 11;
         } catch (NoSuchFieldError var4) {
            ;
         }

         try {
            var0[Enum_Commands2.SET_ON_TIME.ordinal()] = 10;
         } catch (NoSuchFieldError var3) {
            ;
         }

         try {
            var0[Enum_Commands2.SET_REQUIRED.ordinal()] = 6;
         } catch (NoSuchFieldError var2) {
            ;
         }

         try {
            var0[Enum_Commands2.WHO_ARE_YOU.ordinal()] = 1;
         } catch (NoSuchFieldError var1) {
            ;
         }

         e = var0;
         return var0;
      }
   }
}