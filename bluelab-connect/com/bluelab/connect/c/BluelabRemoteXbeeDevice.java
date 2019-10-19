package bluelab.connect.c;

import bluelab.connect.c.c.BluelabException;
import bluelab.connect.model.DeviceFileModel;
import bluelab.connect.model.DeviceServiceModel;
import bluelab.connect.model.PodChainFileModel;
import bluelab.connect.model.SensorFileModel;
import bluelab.connect.model.SensorServiceModel;
import bluelab.connect.model.SettingFileModel;
import com.digi.xbee.api.RemoteXBeeDevice;
import com.digi.xbee.api.utils.HexUtils;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class BluelabRemoteXbeeDevice {
   private String keyCode; // e
   private String name; // f
   private Enum_DeviceVersion deviceVersion; // g 
   private Enum_DeviceType deviceType; // h
   private long id; // i
   protected Enum_Mode mode;
   private Enum_DevicePresence devicePresence;
   private String ident1;
   private String ident2;
   private long millis;
   protected boolean deviceTypeKnown;
   protected Map<Enum_ControlType, ControlTypeManager> controlTypeControlTypeManagerMap;
   protected Map<Enum_ControlType, TypeController> controlTypeControllerMap;
   private EnumSet<Enum_Lockout> lockoutList;
   private EnumSet<Enum_ChangeType> changeTypeList;
   private EnumSet<Enum_PodChainChange> podChainChangeList;
   private Map<Integer, Peripod> peripodWithPumpsList;
   private boolean r;
   private boolean s;
   private RemoteXBeeDevice remoteXbeeDevice;
   private bluelab.connect.c.b.BluelabRemoteDevice remoteDevice;
   // $FF: synthetic field
   private static boolean v = !BluelabRemoteXbeeDevice.class.desiredAssertionStatus();

   public BluelabRemoteXbeeDevice(String keyCode) {
      this(keyCode, bluelab.connect.c.Enum_DeviceType.UNKNOWN, bluelab.connect.c.Enum_DeviceVersion.UNKNOWN, bluelab.connect.c.Enum_DevicePresence.INDISTINCT, (RemoteXBeeDevice)null);
      this.keyCode = keyCode; // e
   }

   public BluelabRemoteXbeeDevice(RemoteXBeeDevice remoteDev) {
      this(remoteDev.getNodeID(), bluelab.connect.c.Enum_DeviceType.UNKNOWN, bluelab.connect.c.Enum_DeviceVersion.UNKNOWN, bluelab.connect.c.Enum_DevicePresence.ALIEN, remoteDev);
      if (!v && remoteDev == null) {
         throw new AssertionError();
      }
   }

   public BluelabRemoteXbeeDevice(DeviceFileModel deviceFileModel) {
      this(deviceFileModel.getName(), deviceFileModel.getType(), bluelab.connect.c.Enum_DeviceVersion.UNKNOWN, bluelab.connect.c.Enum_DevicePresence.INDISTINCT, (RemoteXBeeDevice)null);
      this.keyCode = deviceFileModel.getKeycode(); // e
      this.id = deviceFileModel.getId();
      Iterator deviceIterator = deviceFileModel.getSensors().entrySet().iterator();

      while(deviceIterator.hasNext()) {
         Entry entry;
         Enum_ControlType controlType = (Enum_ControlType)(entry = (Entry)deviceIterator.next()).getKey();
         SensorFileModel sensorFileModel = (SensorFileModel)entry.getValue();
         this.controlTypeControlTypeManagerMap.put(controlType, new ControlTypeManager(sensorFileModel));
      }

   }

   protected BluelabRemoteXbeeDevice(String keyCode, Enum_DeviceType deviceType, Enum_DeviceVersion deviceVersion, Enum_DevicePresence var4, RemoteXBeeDevice remoteDev) {
      this.deviceType = deviceType;
      this.deviceVersion = deviceVersion;
      this.devicePresence = var4;
      this.remoteXbeeDevice = remoteDev;
      if (remoteDev != null) {
          // e
         this.keyCode = bluelab.connect.d.WeirdEncoder.KeyCodeFrom64BitAddress(remoteDev.get64BitAddress().getValue());
      }

      if (keyCode != null) {
         this.setName(keyCode); // c(keyCode)
      } else {
         this.setName(this.keyCode); // c(e)
      }

      this.id = 0L;
      this.mode = bluelab.connect.c.Enum_Mode.UNKNOWN;
      this.ident2 = "";
      this.ident1 = "";
      this.millis = System.currentTimeMillis();
      this.deviceTypeKnown = false;
      this.controlTypeControlTypeManagerMap = new LinkedHashMap();
      this.controlTypeControllerMap = new LinkedHashMap();
      this.lockoutList = EnumSet.noneOf(Enum_Lockout.class);
      this.changeTypeList = EnumSet.allOf(Enum_ChangeType.class);
      this.podChainChangeList = EnumSet.noneOf(Enum_PodChainChange.class);
      this.peripodWithPumpsList = new LinkedHashMap();
      this.r = false;
      this.s = false;
   }

   public final void initMonthDate() {
      this.resetDoseCounters();
   }

   public final Enum_DeviceVersion getDeviceVersion() {
      return this.deviceVersion;
   }

   public final void setDeviceVersion(Enum_DeviceVersion var1) {
      this.deviceVersion = var1;
   }

   public final Enum_Mode getMode() {
      return this.mode;
   }

   public final void setMode(Enum_Mode var1) {
      this.mode = var1;
   }

   public final Enum_DevicePresence updateGetDevicePresence() {
      if (this.remoteXbeeDevice != null && this.deviceTypeKnown) {
         if (System.currentTimeMillis() - this.millis < 60000L) {
            this.devicePresence = bluelab.connect.c.Enum_DevicePresence.ONLINE;
         } else {
            this.devicePresence = bluelab.connect.c.Enum_DevicePresence.OFFLINE;
         }
      } else {
         this.devicePresence = bluelab.connect.c.Enum_DevicePresence.INDISTINCT;
      }

      return this.devicePresence;
   }

   public final void setDevicePresence(Enum_DevicePresence var1) {
      this.devicePresence = var1;
   }

   public final boolean isOnline() {
      return this.updateGetDevicePresence().equals(bluelab.connect.c.Enum_DevicePresence.ONLINE);
   }

   public final boolean isOnlineOffline() {
      Enum_DevicePresence var1;
      return (var1 = this.updateGetDevicePresence()).equals(bluelab.connect.c.Enum_DevicePresence.ONLINE) || var1.equals(bluelab.connect.c.Enum_DevicePresence.OFFLINE);
   }

   public final String getKeyCode() {
      return this.keyCode; // e
   }

   public final String getIdent1() {
       // some kind of identifier, used in 'who are you'
      return this.ident1;
   }

   public final void setIdent1(String var1) {
       // var1 = whoAreYouResponse[0]
      this.ident1 = var1;
   }

   public final String getIdent2() {
       // some kind of identifier, used in 'who are you'
      return this.ident2;
   }

   public final void setIdent2(String var1) {
       // var1 = whoAreYouResponse[1].substring(5)
      this.ident2 = var1;
   }

   public final ControlTypeManager getControlTypeManagerForType(Enum_ControlType var1) {
      return (ControlTypeManager)this.controlTypeControlTypeManagerMap.get(var1);
   }

   public final Map<Enum_ControlType, ControlTypeManager> getControlTypeControlTypeManagerMap() {
      return this.controlTypeControlTypeManagerMap;
   }

   public final TypeController getTypeControllerForType(Enum_ControlType var1) {
      return (TypeController)this.controlTypeControllerMap.get(var1);
   }

   public final Map<Enum_ControlType, TypeController> getControlTypeControllerMap() {
      return this.controlTypeControllerMap;
   }

   public final Map<Integer, Peripod> getPeripodWithPumpsList() {
      return this.peripodWithPumpsList;
   }

   public final int getPeripodWithPumpsListSize() {
      return this.peripodWithPumpsList.size();
   }

   public final long getMillis() {
      return this.millis;
   }

   public final boolean isDeviceTypeKnown() {
       // if yes, online/offline is status
       // if no, indistinct is status
      return this.deviceTypeKnown;
   }

   public final boolean allControlTypeManagerIdsNonZero() {
      boolean ret;
      if (ret = this.id != 0L) {
         ret = this.controlTypeControlTypeManagerMap.values().stream().allMatch((var0) -> {
            return var0.isIdNonZero();
         });
      }

      return ret;
   }

   public final long getId() {
      return this.id;
   }

   public final String getName() {
      return this.name; // f
   }
   
   public final boolean setName(String name) { // c(String)
      if (name != null && !name.trim().isEmpty()) {
         this.name = name; //f 
         return true;
      } else {
         return false;
      }
   }

   public final Enum_DeviceType getDeviceType() {
      return this.deviceType;
   }

   public final String getDeviceTypeUiText() {
      return this.deviceType.getUiText();
   }

   public final long getIdForControlType(Enum_ControlType var1) {
      ControlTypeManager var2;
      return (var2 = this.getControlTypeManagerForType(var1)) != null ? var2.getId() : 0L;
   }

   private void setIdForControlType(Enum_ControlType type, long newId) {
      ControlTypeManager var4;
      if ((var4 = this.getControlTypeManagerForType(type)) != null) {
         var4.setId(newId);
      }

   }

   public final void setIdAndIdOnAllControlTypes(BluelabRemoteXbeeDevice remoteDev) {
      this.id = remoteDev.id;
      remoteDev.controlTypeControlTypeManagerMap.forEach((controlType, controlTypeMgr) -> {
         this.setIdForControlType(controlType, controlTypeMgr.getId());
      });
   }

   public final void setIdAndIdOnAllControlTypes(DeviceServiceModel model) {
      if (this.get64BitAddress().equals(model.getUid())) {
         this.id = model.getId();
         Iterator sensorIterator = model.getSensors().iterator();

         while(sensorIterator.hasNext()) {
            SensorServiceModel sensorServiceModel;
            Enum_ControlType controlType = Enum_ControlType.valueOf((sensorServiceModel = (SensorServiceModel)sensorIterator.next()).getProperty().toUpperCase());
            this.setIdForControlType(controlType, sensorServiceModel.getId());
         }
      }

   }

   public final void clearIdAndIdOnAllControlTypes() {
      this.id = 0L;
      this.controlTypeControlTypeManagerMap.forEach((controlType, controlTypeMgr) -> {
         controlTypeMgr.setId(0L);
      });
   }

   public final void a(bluelab.connect.c.c.MonitorController monitorController) {
      this.setName(monitorController.name); // this.c
      Enum_Mode mmode = monitorController.mode; 
      this.mode = mmode;
      this.turnAlarmsOnOff(monitorController.turnAlarmsOnOff);
      this.lockoutListAddRemove(bluelab.connect.c.Enum_Lockout.LOW_CONDUCTIVITY, monitorController.lowConductivityLockout);
      this.lockoutListAddRemove(bluelab.connect.c.Enum_Lockout.INEFFECTIVE_CONTROL, monitorController.ineffectiveControlLockout);
      boolean var4 = monitorController.f;
      this.r = var4;
      monitorController.getControlTypeMinMaxValueMap().forEach((var1x, var2) -> {
         ControlTypeManager mgr;
         if ((mgr = this.getControlTypeManagerForType(var1x)) != null) {
            mgr.setMinMaxValue(new bluelab.connect.c.c.MinMaxValue(var2));
         }

      });
      monitorController.getControlTypeInstanceMap().forEach((var1x, var2) -> {
         TypeController var3;
         if ((var3 = this.getTypeControllerForType(var1x)) != null) {
            var3.setControlTypeInstance(new bluelab.connect.c.c.ControlTypeInstance(var2));
         }

      });
   }

   public final RemoteXBeeDevice getRemoteXbeeDevice() {
      return this.remoteXbeeDevice;
   }

   public final void setRemoteXbeeDevice(RemoteXBeeDevice remoteDev) {
      if (remoteDev != null) {
         this.remoteXbeeDevice = remoteDev;
         this.millis = System.currentTimeMillis();
      }

   }

   public final boolean isDeviceNotNull() {
      return this.remoteXbeeDevice != null;
   }

   public final bluelab.connect.c.b.BluelabRemoteDevice getBluelabRemoteDevice() {
      return this.remoteDevice;
   }

   public final void setBluelabRemoteDevice(bluelab.connect.c.b.BluelabRemoteDevice var1) {
      this.remoteDevice = var1;
   }

   public final String get64BitAddress() {
      return this.remoteXbeeDevice != null ? HexUtils.byteArrayToHexString(this.remoteXbeeDevice.get64BitAddress().getValue()) : "";
   }

   public final String get16BitAddress() {
      return this.remoteXbeeDevice != null ? this.remoteXbeeDevice.get16BitAddress().toString().toLowerCase() : "";
   }

   public boolean equals(Object var1) {
      if (var1 == null) {
         return false;
      } else if (!BluelabRemoteXbeeDevice.class.isAssignableFrom(var1.getClass())) {
         return false;
      } else {
         BluelabRemoteXbeeDevice var2 = (BluelabRemoteXbeeDevice)var1;
         return this.keyCode.equals(var2.keyCode); // this.e.equals(var2.e)
      }
   }

   
   public final void setRemoteXbeeDeviceParameter(String var1, byte[] var2) throws com.digi.xbee.api.exceptions.XBeeException {
      synchronized(this) {
         this.remoteXbeeDevice.setParameter(var1, var2);
      }
   }

   public final boolean anyControlOutOfRange() {
      return this.anyControlAtHighOrLow() || this.anyLockoutListNotEmpty() || this.controlTypeControlTypeManagerMap.entrySet().stream().anyMatch((var0) -> {
         return ((ControlTypeManager)var0.getValue()).isOverOrUnderRange();
      }) || this.podChainChangeListNotEmpty();
   }

   public final boolean allControlsWithinRange(Enum_ControlType controlType) {
      boolean outOfRange = false;
      ControlTypeManager controlTypeManager;
      if ((controlTypeManager = this.getControlTypeManagerForType(controlType)) != null) {
         outOfRange = controlTypeManager.isAtHighOrLow();
      }

      if (!outOfRange) {
         TypeController typeController;
         if ((typeController = this.getTypeControllerForType(controlType)) != null) {
            outOfRange = typeController.lockoutListNotEmpty();
         }

         if (!outOfRange) {
            if ((controlTypeManager = this.getControlTypeManagerForType(controlType)) != null) {
               outOfRange = controlTypeManager.isOverOrUnderRange();
            }

            if (!outOfRange) {
               return false;
            }
         }
      }

      return true;
   }

   public final boolean anyOn() {
      boolean on = false;
      Iterator var2 = this.controlTypeControlTypeManagerMap.entrySet().iterator();

      while(var2.hasNext() && !(on = ((ControlTypeManager)((Entry)var2.next()).getValue()).isOn())) {
         ;
      }

      return on;
   }

   public final void turnAlarmsOnOff(boolean on) { // a(boolean)
      this.controlTypeControlTypeManagerMap.forEach((controlType, controlTypeManager) -> {
         if (on) {
            if (controlTypeManager.getAlarmState().equals(bluelab.connect.c.Enum_AlarmState.OFF)) {
               controlTypeManager.setAlarmState(bluelab.connect.c.Enum_AlarmState.OK);
               return;
            }
         } else {
            controlTypeManager.setAlarmState(bluelab.connect.c.Enum_AlarmState.OFF);
         }

      });
   }

   public final boolean anyDueForUpdate() {
      boolean var1 = false;
      Iterator var2 = this.controlTypeControlTypeManagerMap.entrySet().iterator();

      while(var2.hasNext() && !(var1 = ((ControlTypeManager)((Entry)var2.next()).getValue()).getDueForUpdate())) {
         ;
      }

      return var1;
   }

   public final boolean anyControlAtHighOrLow() {
      return this.controlTypeControlTypeManagerMap.entrySet().stream().anyMatch((var0) -> {
         return ((ControlTypeManager)var0.getValue()).isAtHighOrLow();
      });
   }

   public final boolean anyLockoutListNotEmpty() {
      return this.controlTypeControllerMap.entrySet().stream().anyMatch((var0) -> {
         return ((TypeController)var0.getValue()).lockoutListNotEmpty();
      });
   }

   public final boolean lockoutListContains(Enum_Lockout lockout) {
      return this.lockoutList.contains(lockout);
   }

   public final void lockoutListAddRemove(Enum_Lockout lockout, boolean add) {
      if (add) {
         this.lockoutList.add(lockout);
      } else {
         this.lockoutList.remove(lockout);
      }
   }

   public final boolean podChainChangeListNotEmpty() {
      return !this.podChainChangeList.isEmpty();
   }

   public final void podChainChangeListAddRemove(Enum_PodChainChange podchainchange, boolean add) {
      if (add) {
         this.podChainChangeList.add(podchainchange);
      } else {
         this.podChainChangeList.remove(podchainchange);
      }
   }

   public final int processBytes(byte[] var1) throws BluelabException {
      return this.remoteDevice.processBytes(var1);
   }

   public final boolean modeIsControl() {
      return this.mode.equals(bluelab.connect.c.Enum_Mode.CONTROL);
   }

   public final void setAllDisconnected() {
      this.controlTypeControlTypeManagerMap.forEach((var0, manager) -> {
         manager.setRange(bluelab.connect.c.Enum_Range.DISCONNECTED);
      });
   }

   public final String getMessageAlarmRangeLockoutsPods() {
      ArrayList list = new ArrayList();
      this.controlTypeControlTypeManagerMap.forEach((controlType, controlTypeManager) -> {
         Enum_AlarmState alarmState;
         if ((alarmState = controlTypeManager.getAlarmState()).equals(bluelab.connect.c.Enum_AlarmState.LOW)) {
            list.add(controlTypeManager.getControlTypeUiText() + " is below low alarm");
         } else if (alarmState.equals(bluelab.connect.c.Enum_AlarmState.HIGH)) {
            list.add(controlTypeManager.getControlTypeUiText() + " is above high alarm");
         }

         Enum_Range range;
         if ((range = controlTypeManager.getRange()).equals(bluelab.connect.c.Enum_Range.UNDER_RANGE)) {
            list.add(controlTypeManager.getControlTypeUiText() + " is under-range");
         } else {
            if (range.equals(bluelab.connect.c.Enum_Range.OVER_RANGE)) {
               list.add(controlTypeManager.getControlTypeUiText() + " is over-range");
            }

         }
      });
      if (this.modeIsControl()) {
         this.controlTypeControllerMap.forEach((var1x, controller) -> {
            if (controller.lockoutListContains(bluelab.connect.c.Enum_Lockout.INEFFECTIVE_CONTROL)) {
               list.add(controller.getControlTypeUiText() + " ineffective control lockout");
            }

         });
         EnumSet allLockoutsExceptIneffective;
         (allLockoutsExceptIneffective = EnumSet.allOf(Enum_Lockout.class)).remove(bluelab.connect.c.Enum_Lockout.INEFFECTIVE_CONTROL);
         Iterator allLockoutsExceptIneffectiveIterator = allLockoutsExceptIneffective.iterator();

         while(allLockoutsExceptIneffectiveIterator.hasNext()) {
            Enum_Lockout lockout = (Enum_Lockout)allLockoutsExceptIneffectiveIterator.next();
            if (this.controlTypeControllerMap.entrySet().stream().anyMatch((var1x) -> {
               return ((TypeController)var1x.getValue()).lockoutListContains(lockout);
            })) {
               list.add(lockout.getUiText());
            }
         }
      } else {
         Iterator podChainChangeIterator = EnumSet.allOf(Enum_PodChainChange.class).iterator();

         while(podChainChangeIterator.hasNext()) {
            Enum_PodChainChange podChainChange = (Enum_PodChainChange)podChainChangeIterator.next();
            if (this.podChainChangeList.contains(podChainChange)) {
               list.add(podChainChange.getUiText());
            }
         }
      }

      this.peripodWithPumpsList.forEach((podIdx, peripod) -> {
         Enum_Messages[] podMessages;
         int podMessagesLength = (podMessages = bluelab.connect.c.Enum_Messages.values()).length;

         for(int idx = 0; idx < podMessagesLength; ++idx) {
            Enum_Messages message = podMessages[idx];
            if (peripod.containsMessage(message)) {
               list.add("Pod " + podIdx.toString() + message.getUiText());
            }
         }

      });
      return String.join("\n", list);
   }

   public final List<String> getPodChainChangeUiTextList() {
      return (List)this.podChainChangeList.stream().map(Enum_PodChainChange::getUiText).collect(Collectors.toList());
   }

   public final int getCountOfSomething(Enum_ControlType var1) {
      bluelab.connect.c.a.MonthDate var2;
      return (var2 = this.getTypeControllerForType(var1).getMonthDate()) != null ? var2.getC() : 0;
   }

   public final long getTimeOfSomething(Enum_ControlType var1) {
      bluelab.connect.c.a.MonthDate var2;
      return (var2 = this.getTypeControllerForType(var1).getMonthDate()) != null ? var2.getB() : 0L;
   }

   public final void resetDoseCounters() {
      this.controlTypeControllerMap.forEach((controlType, controller) -> {
         controller.initMonthDate();
      });
   }

   public final String getCurrentValueOfControlType(Enum_ControlType controlType, SettingFileModel settingFileModel) {
      String ret = "---";
      ControlTypeManager controlTypeManager;
      if ((controlTypeManager = this.getControlTypeManagerForType(controlType)) != null && this.isOnline()) {
         Enum_Range range;
         if ((range = controlTypeManager.getRange()).equals(bluelab.connect.c.Enum_Range.IN_RANGE)) {
            if (controlType.equals(Enum_ControlType.CONDUCTIVITY)) {
               ret = bluelab.connect.c.UnitConverter.ConvertConductivityUnit(controlTypeManager.getCurrentValue(), settingFileModel.conductivityUnit);
            } else if (controlType.equals(Enum_ControlType.PH)) {
               ret = bluelab.connect.c.UnitConverter.FormatConductivityAsEC(controlTypeManager.getCurrentValue());
            } else if (controlType.equals(Enum_ControlType.TEMPERATURE)) {
               ret = bluelab.connect.c.UnitConverter.ConvertTemperatureUnit(controlTypeManager.getCurrentValue(), settingFileModel.temperatureUnit);
            }
         } else if (range.equals(bluelab.connect.c.Enum_Range.UNDER_RANGE)) {
            ret = "U/R";
         } else if (range.equals(bluelab.connect.c.Enum_Range.OVER_RANGE)) {
            ret = "O/R";
         }
      }

      return ret;
   }

   public final String formatMinMaxValue(Enum_ControlType var1, SettingFileModel var2) {
      String var3 = "---";
      TypeController var4;
      if ((var4 = this.getTypeControllerForType(var1)) != null && this.isOnline()) {
         if (var1.equals(Enum_ControlType.CONDUCTIVITY)) {
            var3 = bluelab.connect.c.UnitConverter.ConvertConductivityUnit(var4.getDeltaValue2(), var2.conductivityUnit);
         } else if (var1.equals(Enum_ControlType.PH)) {
            var3 = bluelab.connect.c.UnitConverter.FormatConductivityAsEC(var4.getDeltaValue2());
         } else if (var1.equals(Enum_ControlType.TEMPERATURE)) {
            double var5 = Math.min(var4.getDeltaValue1(), var4.getDeltaValue2());
            double var7 = Math.max(var4.getDeltaValue1(), var4.getDeltaValue2());
            var3 = String.format("%s-%s", bluelab.connect.c.UnitConverter.ConvertTemperatureUnit(var5, var2.temperatureUnit), bluelab.connect.c.UnitConverter.ConvertTemperatureUnit(var7, var2.temperatureUnit));
         }
      }

      return var3;
   }

   public final String getDirectionUiText(Enum_ControlType var1) {
      String var2 = "Off";
      TypeController var3;
      if ((var3 = this.getTypeControllerForType(var1)) != null) {
         var2 = var3.getDirection().getUiText();
      }

      return var2;
   }

   public final boolean changeTypeListContains(Enum_ChangeType var1) {
      return this.changeTypeList.contains(var1);
   }

   public final void addRemoveChangeType(Enum_ChangeType var1, boolean add) {
      if (add) {
         this.changeTypeList.add(var1);
      } else {
         this.changeTypeList.remove(var1);
      }
   }

   public final boolean isProController() {
      return this.deviceType.equals(bluelab.connect.c.Enum_DeviceType.PRO_CONTROLLER);
   }

   public final boolean M() {
      return this.r;
   }

   public final void b(boolean var1) {
      this.r = var1;
   }

   public final boolean N() {
      return this.s;
   }

   public final void c(boolean var1) {
      this.s = var1;
   }

   public final void copyNameAndSettingsToAllPeripods(PodChainFileModel podChainFileModel) {
      podChainFileModel.getPodSettings().forEach((var1x, podSettings) -> {
         PeripodWithPumps peripodWithPumps;
         if ((peripodWithPumps = (PeripodWithPumps)this.peripodWithPumpsList.get(var1x)) != null) {
            peripodWithPumps.copyNameAndPumpSettings(podSettings);
         }

      });
   }

   public final boolean hasPeripod() {
      EnumSet var1 = EnumSet.noneOf(Enum_PeripodType.class);
      Iterator var3 = this.peripodWithPumpsList.entrySet().iterator();

      while(var3.hasNext()) {
         Peripod var2 = (Peripod)((Entry)var3.next()).getValue();
         var1.add(var2.getPeripod());
      }

      boolean var4 = var1.contains(bluelab.connect.c.Enum_PeripodType.PERIPOD_M3) || var1.contains(bluelab.connect.c.Enum_PeripodType.PERIPOD_M4);
      boolean var5 = var1.contains(bluelab.connect.c.Enum_PeripodType.PERIPOD_L3) || var1.contains(bluelab.connect.c.Enum_PeripodType.PERIPOD_L4);
      return !var4 || !var5;
   }

   public final int getTotalPumpCount() {
      int pumpCount = 0;
      Iterator peripodWithPumpsList = this.peripodWithPumpsList.entrySet().iterator();

      while(peripodWithPumpsList.hasNext()) {
         Peripod pod;
         if ((pod = (Peripod)((Entry)peripodWithPumpsList.next()).getValue()) instanceof PeripodWithPumps) {
            pumpCount += ((PeripodWithPumps)pod).getPumps().size();
         }
      }

      return pumpCount;
   }
}