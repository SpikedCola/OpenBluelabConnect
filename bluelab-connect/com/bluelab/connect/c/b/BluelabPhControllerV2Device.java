package bluelab.connect.c.b;

import bluelab.connect.c.ControlTypeManager;
import bluelab.connect.c.Enum_ControlType;
import bluelab.connect.c.Peripod;
import bluelab.connect.c.Enum_Messages;
import bluelab.connect.c.PeripodFactory;
import bluelab.connect.c.Enum_PeripodType;
import bluelab.connect.c.BluelabRemoteXbeeDevice;
import bluelab.connect.c.c.BluelabException;
import bluelab.connect.c.Exception2;
import bluelab.connect.model.PodSettings;
import bluelab.connect.model.PumpSettings;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BluelabPhControllerV2Device extends BluelabRemoteDevice {
   private static Logger logger = LoggerFactory.getLogger(BluelabPhControllerV2Device.class);
   private byte e = -1;
   private byte f = -1;
   private EnumSet<Enum_Commands> commandList;
   private Map<Integer, EnumSet<Enum_Commands>> h;
   private Map<Enum_ControlType, Integer> i;
   private boolean j;
   private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
   // $FF: synthetic field
   private static int[] l;

   public BluelabPhControllerV2Device(BluelabRemoteXbeeDevice dev) {
      super(dev, true);
      if (dev != null) {
         this.setControllerMonthDate((Supplier)(bluelab.connect.c.a.MonthDateTimezone::new));
      }

      this.commandList = EnumSet.noneOf(Enum_Commands.class);
      this.h = new HashMap();
      this.i = new HashMap();
      this.j = true;
   }

   public final List<bluelab.connect.g.DeviceMessage> buildMessageList() {
      ArrayList var1 = new ArrayList(4);
      if (this.device.changeTypeListContains(bluelab.connect.c.Enum_ChangeType.POD_STATUS)) {
         var1.add(new bluelab.connect.g.ObfuscatedDeviceMessage(bluelab.connect.c.b.Enum_Commands.GET_DEVICE_POD_SETUP.getValue(), (byte[])null));
         this.h.clear();
         this.device.addRemoveChangeType(bluelab.connect.c.Enum_ChangeType.POD_SETTING, true);
      }

      if (this.a(bluelab.connect.c.b.Enum_Commands.POD_WHO_ARE_YOU)) {
         var1.add(new bluelab.connect.g.ObfuscatedMessage(bluelab.connect.c.b.Enum_Commands.POD_WHO_ARE_YOU.getValue(), (byte)-1, (byte[])null));
      }

      if (this.a(bluelab.connect.c.b.Enum_Commands.GET_POD_SETTINGS)) {
         var1.add(new bluelab.connect.g.ObfuscatedMessage(bluelab.connect.c.b.Enum_Commands.GET_POD_SETTINGS.getValue(), (byte)-1, new byte[1]));
      }

      if (this.a(bluelab.connect.c.b.Enum_Commands.GET_POD_PUMP_RATES)) {
         var1.add(new bluelab.connect.g.ObfuscatedMessage(bluelab.connect.c.b.Enum_Commands.GET_POD_PUMP_RATES.getValue(), (byte)-1, (byte[])null));
      }

      if (var1.isEmpty() && this.device.getPeripodWithPumpsListSize() > 0) {
         var1.add(new bluelab.connect.g.ObfuscatedMessage(bluelab.connect.c.b.Enum_Commands.GET_POD_INFO.getValue(), (byte)-1, (byte[])null));
      }

      return var1;
   }

   public final List<bluelab.connect.g.DeviceMessage> a(boolean var1, byte var2) 
   throws BluelabException
   {
      ArrayList var3 = new ArrayList(1);
      if (var1) {
         bluelab.connect.c.c.MonitorController monitorController = new bluelab.connect.c.c.MonitorController(this.device, bluelab.connect.c.Enum_Mode.MONITOR, true);
         var3.add((bluelab.connect.g.DeviceMessage)this.getDeviceMessages(monitorController).get(0));
         var3.add(new bluelab.connect.g.ObfuscatedMessage(bluelab.connect.c.b.Enum_Commands.RESTART_POD_BOOTLOADER.getValue(), var2, (byte[])null));
      } else {
         var3.add(new bluelab.connect.g.ObfuscatedDeviceMessage(bluelab.connect.c.b.Enum_Commands.RESTART_DEVICE_BOOTLOADER.getValue(), (byte[])null));
      }

      return var3;
   }

   protected final boolean isValid2(ByteBuffer var1) {
      return bluelab.connect.g.DeviceMessage.IsValid2(var1);
   }

   protected final bluelab.connect.g.ByteArray createDeviceMessage(ByteBuffer var1) {
      byte var2;
      if ((var2 = bluelab.connect.g.DeviceMessage.GetMessageCommandByte(var1)) < 0) {
         return null;
      } else {
         return (bluelab.connect.g.ByteArray)(var2 < bluelab.connect.c.b.Enum_Commands.POD_WHO_ARE_YOU.getValue() ? new bluelab.connect.g.ObfuscatedDeviceMessage(var1) : new bluelab.connect.g.ObfuscatedMessage(var1));
      }
   }

   protected final void processMessage(bluelab.connect.g.ByteArray msg)
   throws BluelabException, Exception2
   {
      Enum_Commands cmd = bluelab.connect.c.b.Enum_Commands.getCommandByInt(((bluelab.connect.g.DeviceMessage)msg).getCommand());
      this.commandList.add(cmd);
      int var4;
      int var5;
      int var9;
      short var13;
      BluelabPhControllerV2Device var18;
      ByteBuffer var19;
      Enum_ControlType var23;
      int var27;
      ByteBuffer var28;
      switch(GetArrayOfEnumCommands()[cmd.ordinal()]) {
      case 1:
         String var21 = ReadStringFromBuffer(var19 = ByteBuffer.wrap(msg.GetInternalByteArray()).order(ByteOrder.LITTLE_ENDIAN), 17);
         String var29 = ReadStringFromBuffer(var19, 17);
         this.device.setIdent1(var21);
         this.device.setIdent2(var29);
         logger.info("Received 'who are you' response from '{}' ({} {})", new Object[]{this.device.getKeyCode(), this.device.getIdent1(), this.device.getIdent2()});
         return;
      case 2:
         var19 = ByteBuffer.wrap(msg.GetInternalByteArray()).order(ByteOrder.LITTLE_ENDIAN);
         byte var30 = var19.get();
         byte var22 = var19.get();
         if (var30 != this.e) {
            this.device.addRemoveChangeType(bluelab.connect.c.Enum_ChangeType.DEVICE_SETTING, true);
            this.e = var30;
         }

         if (var22 != this.f) {
            this.device.addRemoveChangeType(bluelab.connect.c.Enum_ChangeType.POD_STATUS, true);
            this.f = var22;
            logger.info("Pod status change detected ({})", Byte.toUnsignedInt(var22));
         }

         var28 = var19;
         var18 = this;
         Instant var31 = this.instant.GetInstant();
         var4 = Byte.toUnsignedInt(var19.get());

         for(var5 = 0; var5 < var4; ++var5) {
            Enum_ControlType var34 = bluelab.connect.c.b.PodParser.GetControlTypeByInt(Byte.toUnsignedInt(var28.get()));
            var13 = var28.getShort();
            Integer var26 = -var28.get();
            var18.i.put(var34, var26);
            var28.getShort();
            var28.get();
            var27 = Short.toUnsignedInt(var28.getShort());
            ControlTypeManager var32;
            if ((var32 = var18.device.getControlTypeManagerForType(var34)) != null && var26 != null) {
               var32.setRange(bluelab.connect.c.b.PodParser.ParseRange(var27));
               var32.setInstant(var31);
               var32.setCurrentValue(ScientificNotation(var13, var26));
               var32.setAlarmState(bluelab.connect.c.b.PodParser.ParseAlarmState(var18.j, var27));
               var32.setDueForUpdate(bluelab.connect.c.b.BluelabRemoteDevice.TestBit((long)var27, 6));
            }
         }

         var28 = var19;
         var18 = this;
         var9 = Byte.toUnsignedInt(var19.get());

         for(var4 = 0; var4 < var9; ++var4) {
            var23 = bluelab.connect.c.b.PodParser.GetControlTypeByInt(Byte.toUnsignedInt(var28.get()));
            int var35 = Short.toUnsignedInt(var28.getShort());
            bluelab.connect.c.TypeController var36;
            if ((var36 = var18.device.getTypeControllerForType(var23)) != null) {
               var36.addRemoveLockout(bluelab.connect.c.Enum_Lockout.NORMALLY_OPEN, bluelab.connect.c.b.BluelabRemoteDevice.TestBit((long)var35, 5));
               var36.addRemoveLockout(bluelab.connect.c.Enum_Lockout.NORMALLY_CLOSED, bluelab.connect.c.b.BluelabRemoteDevice.TestBit((long)var35, 6));
               var36.addRemoveLockout(bluelab.connect.c.Enum_Lockout.LOW_CONDUCTIVITY, bluelab.connect.c.b.BluelabRemoteDevice.TestBit((long)var35, 7));
               var36.addRemoveLockout(bluelab.connect.c.Enum_Lockout.INEFFECTIVE_CONTROL, bluelab.connect.c.b.BluelabRemoteDevice.TestBit((long)var35, 8));
               var36.addRemoveLockout(bluelab.connect.c.Enum_Lockout.OTHER, bluelab.connect.c.b.BluelabRemoteDevice.TestBit((long)var35, 9));
            }
         }

         return;
      case 3:
         var19 = ByteBuffer.wrap(msg.GetInternalByteArray()).order(ByteOrder.LITTLE_ENDIAN);
         this.device.setMode(bluelab.connect.c.b.PodParser.GetModeByInt(Byte.toUnsignedInt(var19.get())));
         var9 = Byte.toUnsignedInt(var19.get());
         this.j = bluelab.connect.c.b.BluelabRemoteDevice.TestBit((long)var9, 0);
         this.device.turnAlarmsOnOff(this.j);
         this.device.lockoutListAddRemove(bluelab.connect.c.Enum_Lockout.LOW_CONDUCTIVITY, bluelab.connect.c.b.BluelabRemoteDevice.TestBit((long)var9, 1));
         this.device.lockoutListAddRemove(bluelab.connect.c.Enum_Lockout.INEFFECTIVE_CONTROL, bluelab.connect.c.b.BluelabRemoteDevice.TestBit((long)var9, 2));
         this.device.b(bluelab.connect.c.b.BluelabRemoteDevice.TestBit((long)var9, 3));
         this.device.c(bluelab.connect.c.b.BluelabRemoteDevice.TestBit((long)var9, 4));
         this.device.getControlTypeControllerMap().forEach((var1x, var2x) -> {
            var2x.addRemoveLockout(bluelab.connect.c.Enum_Lockout.POD_CHAIN_CHANGE, this.device.modeIsControl() && this.device.N());
         });
         this.device.podChainChangeListAddRemove(bluelab.connect.c.Enum_PodChainChange.POD_CHAIN_CHANGE, this.device.N());
         var28 = var19;
         var18 = this;
         var9 = Byte.toUnsignedInt(var19.get());

         short var33;
         for(var4 = 0; var4 < var9; ++var4) {
            var23 = bluelab.connect.c.b.PodParser.GetControlTypeByInt(Byte.toUnsignedInt(var28.get()));
            var33 = var28.getShort();
            var13 = var28.getShort();
            ControlTypeManager var24 = var18.device.getControlTypeManagerForType(var23);
            Integer var7 = (Integer)var18.i.get(var23);
            if (var24 != null && var7 != null) {
               var24.setMinMaxValueMaxValue(ScientificNotation(var33, var7));
               var24.setMinMaxValueMinValue(ScientificNotation(var13, var7));
            }
         }

         var28 = var19;
         var18 = this;
         var9 = Byte.toUnsignedInt(var19.get());

         for(var4 = 0; var4 < var9; ++var4) {
            var23 = bluelab.connect.c.b.PodParser.GetControlTypeByInt(Byte.toUnsignedInt(var28.get()));
            var33 = var28.getShort();
            var13 = var28.getShort();
            bluelab.connect.c.Enum_Direction var25 = bluelab.connect.c.b.PodParser.GetDirectionByInt(Byte.toUnsignedInt(var28.get()));
            var27 = Short.toUnsignedInt(var28.getShort());
            int var10 = Short.toUnsignedInt(var28.getShort());
            bluelab.connect.c.TypeController typeController = var18.device.getTypeControllerForType(var23);
            Integer var20 = (Integer)var18.i.get(var23);
            if (typeController != null && var20 != null) {
               typeController.setDeltaValue1(ScientificNotation(var33, var20));
               typeController.setDeltaValue2(ScientificNotation(var13, var20));
               typeController.setIsTemperatureControl(typeController.getDeltaValue1() != typeController.getDeltaValue2());
               typeController.setDirection(var25);
               typeController.setOnTime(var27);
               typeController.setOffTime(var10);
            }
         }

         if (this.commandList.containsAll(EnumSet.of(bluelab.connect.c.b.Enum_Commands.GET_DEVICE_INFO, bluelab.connect.c.b.Enum_Commands.GET_DEVICE_SETTINGS))) {
            this.device.addRemoveChangeType(bluelab.connect.c.Enum_ChangeType.DEVICE_SETTING, false);
         }

         return;
      case 4:
         BluelabPhControllerV2Device var14 = this;
         Instant var3 = this.instant.GetInstant();
         ByteBuffer var17;
         long var8 = Integer.toUnsignedLong((var17 = ByteBuffer.wrap(msg.GetInternalByteArray()).order(ByteOrder.LITTLE_ENDIAN)).getInt());
         var4 = Byte.toUnsignedInt(var17.get());
         logger.info("{} has reference message timestamp of {}", this.device.getKeyCode(), var8);

         for(var5 = 0; var5 < var4; ++var5) {
            long var12 = Integer.toUnsignedLong(var17.getInt());
            int var6 = Byte.toUnsignedInt(var17.get());
            if (var8 < var12) {
               throw new IllegalArgumentException("Dosing timestamp should not be greater than message timestamp.");
            }

            var14.a(Enum_ControlType.CONDUCTIVITY, var3, var8, var12, bluelab.connect.c.b.BluelabRemoteDevice.TestBit((long)var6, 0));
            var14.a(Enum_ControlType.TEMPERATURE, var3, var8, var12, bluelab.connect.c.b.BluelabRemoteDevice.TestBit((long)var6, 2));
            var14.a(Enum_ControlType.PH, var3, var8, var12, bluelab.connect.c.b.BluelabRemoteDevice.TestBit((long)var6, 3));
         }

         return;
      case 5:
         this.f(msg);
         if (this.commandList.contains(bluelab.connect.c.b.Enum_Commands.GET_DEVICE_POD_SETUP)) {
            this.device.addRemoveChangeType(bluelab.connect.c.Enum_ChangeType.POD_STATUS, false);
         }

         return;
      case 6:
      case 11:
      case 13:
      case 14:
         if (ByteBuffer.wrap(msg.GetInternalByteArray()).order(ByteOrder.LITTLE_ENDIAN).get() <= 0) {
            throw new bluelab.connect.c.c.BluelabException(String.format("Setting was not successfully applied (0x%02X).", ((bluelab.connect.g.DeviceMessage)msg).getCommand()));
         }
      case 7:
      default:
         return;
      case 8:
         this.a(this::setPeripod, msg);
         return;
      case 9:
         this.a(this::setPumpValue, msg);
         return;
      case 10:
         this.a(this::setPump, msg);
         return;
      case 12:
         this.a(this::pumps, msg);
      }
   }

   private void a(Consumer<bluelab.connect.g.ByteArray> var1, bluelab.connect.g.ByteArray var2) {
      var1.accept(var2);
      bluelab.connect.g.ObfuscatedMessage msg = (bluelab.connect.g.ObfuscatedMessage)var2;
      byte var10001 = msg.getInnerCommand();
      Enum_Commands var3 = bluelab.connect.c.b.Enum_Commands.getCommandByInt(msg.getCommand());
      byte var6 = var10001;
      if (!this.h.containsKey(Integer.valueOf(var6))) {
         this.h.put(Integer.valueOf(var6), EnumSet.noneOf(Enum_Commands.class));
      }

      EnumSet var5;
      if ((var5 = (EnumSet)this.h.get(Integer.valueOf(var6))) != null) {
         var5.add(var3);
      }

      if (!this.a(bluelab.connect.c.b.Enum_Commands.POD_WHO_ARE_YOU) && !this.a(bluelab.connect.c.b.Enum_Commands.GET_POD_SETTINGS)) {
         this.device.addRemoveChangeType(bluelab.connect.c.Enum_ChangeType.POD_SETTING, false);
      }

   }

   public final List<bluelab.connect.g.DeviceMessage> getDeviceMessages(bluelab.connect.c.c.MonitorController monitorController) 
   throws BluelabException
   {
      ArrayList var2 = new ArrayList(1);
      ByteBuffer var3 = ByteBuffer.allocate(4 + monitorController.getControlTypeMinMaxValueMap().size() * 5 + monitorController.getControlTypeInstanceMap().size() * 10).order(ByteOrder.LITTLE_ENDIAN);
      var3.put(bluelab.connect.c.b.PodParser.GetDirectionValue(monitorController.mode));
      ??long var6 = bluelab.connect.c.b.BluelabRemoteDevice.a(bluelab.connect.c.b.BluelabRemoteDevice.a(bluelab.connect.c.b.BluelabRemoteDevice.a(bluelab.connect.c.b.BluelabRemoteDevice.a(0L, 0, monitorController.turnAlarmsOnOff), 1, monitorController.lowConductivityLockout), 2, monitorController.ineffectiveControlLockout), 3, monitorController.f);
      var3.put((byte)((int)var6));
      this.putMinMaxValueMapInBuf(monitorController.getControlTypeMinMaxValueMap(), var3);
      this.putControlTypeInstanceInBuf(monitorController.getControlTypeInstanceMap(), var3);
      var2.add(new bluelab.connect.g.ObfuscatedDeviceMessage(bluelab.connect.c.b.Enum_Commands.SET_DEVICE_SETTINGS.a(), var3.array()));
      return var2;
   }

   private void putMinMaxValueMapInBuf(Map<Enum_ControlType, bluelab.connect.c.c.MinMaxValue> var1, ByteBuffer buf) 
   throws BluelabException
   {
      buf.put((byte)var1.size());
      Iterator var3 = var1.entrySet().iterator();

      while(var3.hasNext()) {
         Entry var6;
         Enum_ControlType controlType = (Enum_ControlType)(var6 = (Entry)var3.next()).getKey();
         bluelab.connect.c.c.MinMaxValue minMaxValue = (bluelab.connect.c.c.MinMaxValue)var6.getValue();
         Integer var5;
         if ((var5 = (Integer)this.i.get(controlType)) == null) {
            throw new BluelabException("Cannot write settings, since exponent is unknown.");
         }

         buf.put(PodParser.GetControlTypeKeyByteValue(controlType));
         buf.putShort((short)ScientificNotationReciprocal(minMaxValue.maxValue, var5));
         buf.putShort((short)ScientificNotationReciprocal(minMaxValue.minValue, var5));
      }

   }

   private void putControlTypeInstanceInBuf(Map<Enum_ControlType, bluelab.connect.c.c.ControlTypeInstance> var1, ByteBuffer buf)
   throws BluelabException
   {
      buf.put((byte)var1.size());
      Iterator var3 = var1.entrySet().iterator();

      while(var3.hasNext()) {
         Entry var6;
         Enum_ControlType var4 = (Enum_ControlType)(var6 = (Entry)var3.next()).getKey();
         bluelab.connect.c.c.ControlTypeInstance var7 = (bluelab.connect.c.c.ControlTypeInstance)var6.getValue();
         Integer var5;
         if ((var5 = (Integer)this.i.get(var4)) == null) {
            throw new bluelab.connect.c.c.BluelabException("Cannot write settings, since exponent is unknown.");
         }

         buf.put(bluelab.connect.c.b.PodParser.GetControlTypeKeyByteValue(var4));
         buf.putShort((short)??(var7.deltaValue1, var5));
         buf.putShort((short)??(var7.deltaValue2, var5));
         buf.put(bluelab.connect.c.b.PodParser.GetDirectionValue(var7.direction));
         buf.putShort((short)var7.onTime);
         buf.putShort((short)var7.offTime);
      }

   }

   public final List<bluelab.connect.g.DeviceMessage> getDeviceMessages(PodSettings var1, byte var2) {
      ArrayList var3;
      (var3 = new ArrayList(1)).add(new bluelab.connect.g.ObfuscatedMessage(bluelab.connect.c.b.Enum_Commands.SET_POD_SETTINGS.getValue(), var2, a(var1)));
      return var3;
   }

   private static byte[] a(PodSettings var0) {
      List var6;
      int var1 = Math.min((var6 = var0.getPumpSettings()).size(), 4);
      ByteBuffer var2;
      (var2 = ByteBuffer.allocate(1 + var1 * 18).order(ByteOrder.LITTLE_ENDIAN)).put((byte)var1);

      for(int var3 = 0; var3 < var1; ++var3) {
         PumpSettings var4 = (PumpSettings)var6.get(var3);
         var2.put((byte)var3);
         String var5 = var4.name;
         var2.put((var5 + "\u0000").getBytes());
         var2.put(bluelab.connect.c.b.PodParser.GetPumpTypeValue(var4.pumpType));
         var2.put((byte)var4.ratio);
      }

      return var2.array();
   }

   private void f(bluelab.connect.g.ByteArray byteArray)
   throws Exception2
   {
      ByteBuffer buf = ByteBuffer.wrap(byteArray.GetInternalByteArray()).order(ByteOrder.LITTLE_ENDIAN);
      Map var2 = this.device.getPeripodWithPumpsList();
      var2.clear();
      int var3 = Byte.toUnsignedInt(buf.get());

      for(int idx = 0; idx < var3; ++idx) {
         int unused = Byte.toUnsignedInt(buf.get());
         byte peripodType = buf.get();
         int flags = Byte.toUnsignedInt(buf.get());
         int numPumps = Byte.toUnsignedInt(buf.get());
         Peripod var11 = PeripodFactory.InstantiatePeripod(unused, (Enum_PeripodType)bluelab.connect.j.EnumValueFind.FindValue(Enum_PeripodType.class, peripodType, Enum_PeripodType.UNKNOWN), numPumps);
         var11.addRemoveMessage(Enum_Messages.DAISY_CHAIN_ID, bluelab.connect.c.b.BluelabRemoteDevice.TestBit((long)flags, 1));
         var11.addRemoveMessage(Enum_Messages.ADDRESS, bluelab.connect.c.b.BluelabRemoteDevice.TestBit((long)flags, 2));
         var11.addRemoveMessage(Enum_Messages.TEMPERATURE, bluelab.connect.c.b.BluelabRemoteDevice.TestBit((long)flags, 3));
         var11.addRemoveMessage(Enum_Messages.NEW_CALIBRATION, bluelab.connect.c.b.BluelabRemoteDevice.TestBit((long)flags, 4));
         var11.addRemoveMessage(Enum_Messages.COMMUNICATION, bluelab.connect.c.b.BluelabRemoteDevice.TestBit((long)flags, 5));
         var11.addRemoveMessage(Enum_Messages.USER_BUSY, bluelab.connect.c.b.BluelabRemoteDevice.TestBit((long)flags, 6));
         var11.addRemoveMessage(Enum_Messages.UNRESPONSIVE, bluelab.connect.c.b.BluelabRemoteDevice.TestBit((long)flags, 7));
         var2.put(unused, var11);
      }

      byte normalizedPodCalibrationValue = buf.get();
      logger.debug("Normalized pod calibration value: {}", normalizedPodCalibrationValue);
   }

   private boolean a(Enum_Commands var1) {
      if (this.h.isEmpty()) {
         return true;
      } else {
         Iterator var3 = this.h.entrySet().iterator();

         EnumSet var2;
         do {
            if (!var3.hasNext()) {
               return false;
            }
         } while((var2 = (EnumSet)((Entry)var3.next()).getValue()) == null || var2.contains(var1));

         return true;
      }
   }

   private void a(Enum_ControlType type, Instant instant, long var3, long id, boolean on) {
      bluelab.connect.c.TypeController controller;
      if ((controller = this.device.getTypeControllerForType(type)) != null) {
         bluelab.connect.c.a.MonthDateTimezone var9 = (bluelab.connect.c.a.MonthDateTimezone)controller.getMonthDate();
         if (var9.a(instant, var3, id, on)) {
            logger.info("{} has {} dosing {} at {} with id {} (count = {}, time = {})", new Object[]{this.device.getKeyCode(), type, on ? "on" : "off", this.dateTimeFormatter.format(LocalDateTime.ofInstant(var9.e(), ZoneId.systemDefault())), id, this.device.getCountOfSomething(type), this.device.getTimeOfSomething(type)});
            return;
         }

         if (!var9.a(var3)) {
            logger.error("{} has {} invalid reference time id {} (previous id = {})", new Object[]{this.device.getKeyCode(), type, var3, var9.f()});
         }
      }

   }

   protected void setPeripod(bluelab.connect.g.ByteArray byteArray) {
      bluelab.connect.g.ObfuscatedMessage message = (bluelab.connect.g.ObfuscatedMessage)byteArray;
      ByteBuffer byteBuffer = ByteBuffer.wrap(byteArray.GetInternalByteArray()).order(ByteOrder.LITTLE_ENDIAN);
      bluelab.connect.c.PeripodWithPumps peripod = (bluelab.connect.c.PeripodWithPumps)this.device.getPeripodWithPumpsList().get(Integer.valueOf(message.getInnerCommand()));
      String productVersion = ReadStringFromBuffer(byteBuffer, 17);
      String firmwareVersion = ReadStringFromBuffer(byteBuffer, 17);
      String name = ReadStringFromBuffer(byteBuffer, 17);
      if (peripod != null) {
         peripod.setProductVersion(productVersion);
         peripod.setFirmwareVersion(firmwareVersion);
         peripod.setName(name);
      }

   }

   protected void setPumpValue(bluelab.connect.g.ByteArray var1) {
      bluelab.connect.g.ObfuscatedMessage var2 = (bluelab.connect.g.ObfuscatedMessage)var1;
      ByteBuffer var9 = ByteBuffer.wrap(var1.GetInternalByteArray()).order(ByteOrder.LITTLE_ENDIAN);
      bluelab.connect.c.PeripodWithPumps var10 = (bluelab.connect.c.PeripodWithPumps)this.device.getPeripodWithPumpsList().get(Integer.valueOf(var2.getInnerCommand()));
      Short.toUnsignedLong(var9.getShort());
      int var3 = Byte.toUnsignedInt(var9.get());

      for(int var4 = 0; var4 < var3; ++var4) {
         double var7 = (double)Integer.toUnsignedLong(var9.getInt()) * 0.1D;
         Byte.toUnsignedInt(var9.get());
         if (var10 != null && var4 < var10.getPumps().size()) {
            ((bluelab.connect.c.Pump)var10.getPumps().get(var4)).setCurrentValue(var7);
         }
      }

   }

   protected void setPump(bluelab.connect.g.ByteArray var1) {
      bluelab.connect.g.ObfuscatedMessage var2 = (bluelab.connect.g.ObfuscatedMessage)var1;
      ByteBuffer var9 = ByteBuffer.wrap(var1.GetInternalByteArray()).order(ByteOrder.LITTLE_ENDIAN);
      bluelab.connect.c.PeripodWithPumps peripod = (bluelab.connect.c.PeripodWithPumps)this.device.getPeripodWithPumpsList().get(Integer.valueOf(var2.getInnerCommand()));
      int var3 = Math.min(Byte.toUnsignedInt(var9.get()), 4);

      for(int var4 = 0; var4 < var3; ++var4) {
         int var5 = Byte.toUnsignedInt(var9.get());
         String var6 = ReadStringFromBuffer(var9, 15);
         int var7 = Byte.toUnsignedInt(var9.get());
         int var8 = Byte.toUnsignedInt(var9.get());
         bluelab.connect.c.Pump pump;
         if (peripod != null && var5 < peripod.getPumps().size() && (pump = (bluelab.connect.c.Pump)peripod.getPumps().get(var5)) != null) {
            pump.setName(var6);
            pump.setFunction(bluelab.connect.c.b.PodParser.GetPumpTypeByInt(var7));
            pump.SetRatio(var8);
         }
      }

   }

   protected void pumps(bluelab.connect.g.ByteArray var1) {
      bluelab.connect.g.ObfuscatedMessage var2 = (bluelab.connect.g.ObfuscatedMessage)var1;
      ByteBuffer var5 = ByteBuffer.wrap(var1.GetInternalByteArray()).order(ByteOrder.LITTLE_ENDIAN);
      bluelab.connect.c.PeripodWithPumps var6 = (bluelab.connect.c.PeripodWithPumps)this.device.getPeripodWithPumpsList().get(Integer.valueOf(var2.getInnerCommand()));
      int var3 = Byte.toUnsignedInt(var5.get());

      for(int var4 = 0; var4 < var3; ++var4) {
         Short.toUnsignedInt(var5.getShort());
         if (var6 != null && var4 < var6.getPumps().size()) {
            var6.getPumps().get(var4);
         }
      }

   }

   private static double ScientificNotation(int var0, int e) {
      return (double)var0 * Math.pow(10.0D, (double)e);
   }

   private static int ScientificNotationReciprocal(double var0, int e) {
      return (int)(var0 * Math.pow(10.0D, (double)(-e)));
   }

   // $FF: synthetic method
   private static int[] GetArrayOfEnumCommands() {
      int[] var10000 = l;
      if (l != null) {
         return var10000;
      } else {
         int[] var0 = new int[bluelab.connect.c.b.Enum_Commands.values().length];

         try {
            var0[bluelab.connect.c.b.Enum_Commands.DISPLAY_POD_ID.ordinal()] = 11;
         } catch (NoSuchFieldError var16) {
            ;
         }

         try {
            var0[bluelab.connect.c.b.Enum_Commands.GET_DEVICE_CONTROL_ACTIVITY.ordinal()] = 4;
         } catch (NoSuchFieldError var15) {
            ;
         }

         try {
            var0[bluelab.connect.c.b.Enum_Commands.GET_DEVICE_INFO.ordinal()] = 2;
         } catch (NoSuchFieldError var14) {
            ;
         }

         try {
            var0[bluelab.connect.c.b.Enum_Commands.GET_DEVICE_POD_SETUP.ordinal()] = 5;
         } catch (NoSuchFieldError var13) {
            ;
         }

         try {
            var0[bluelab.connect.c.b.Enum_Commands.GET_DEVICE_SETTINGS.ordinal()] = 3;
         } catch (NoSuchFieldError var12) {
            ;
         }

         try {
            var0[bluelab.connect.c.b.Enum_Commands.GET_POD_INFO.ordinal()] = 9;
         } catch (NoSuchFieldError var11) {
            ;
         }

         try {
            var0[bluelab.connect.c.b.Enum_Commands.GET_POD_PUMP_RATES.ordinal()] = 12;
         } catch (NoSuchFieldError var10) {
            ;
         }

         try {
            var0[bluelab.connect.c.b.Enum_Commands.GET_POD_SETTINGS.ordinal()] = 10;
         } catch (NoSuchFieldError var9) {
            ;
         }

         try {
            var0[bluelab.connect.c.b.Enum_Commands.NONE.ordinal()] = 16;
         } catch (NoSuchFieldError var8) {
            ;
         }

         try {
            var0[bluelab.connect.c.b.Enum_Commands.POD_WHO_ARE_YOU.ordinal()] = 8;
         } catch (NoSuchFieldError var7) {
            ;
         }

         try {
            var0[bluelab.connect.c.b.Enum_Commands.RESTART_DEVICE_BOOTLOADER.ordinal()] = 7;
         } catch (NoSuchFieldError var6) {
            ;
         }

         try {
            var0[bluelab.connect.c.b.Enum_Commands.RESTART_POD_BOOTLOADER.ordinal()] = 15;
         } catch (NoSuchFieldError var5) {
            ;
         }

         try {
            var0[bluelab.connect.c.b.Enum_Commands.SET_DEVICE_SETTINGS.ordinal()] = 6;
         } catch (NoSuchFieldError var4) {
            ;
         }

         try {
            var0[bluelab.connect.c.b.Enum_Commands.SET_POD_NAME.ordinal()] = 13;
         } catch (NoSuchFieldError var3) {
            ;
         }

         try {
            var0[bluelab.connect.c.b.Enum_Commands.SET_POD_SETTINGS.ordinal()] = 14;
         } catch (NoSuchFieldError var2) {
            ;
         }

         try {
            var0[bluelab.connect.c.b.Enum_Commands.WHO_ARE_YOU.ordinal()] = 1;
         } catch (NoSuchFieldError var1) {
            ;
         }

         l = var0;
         return var0;
      }
   }
}