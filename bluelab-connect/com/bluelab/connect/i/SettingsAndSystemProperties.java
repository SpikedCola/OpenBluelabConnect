package bluelab.connect.i;

import bluelab.connect.c.BluelabRemoteXbeeDevice;
import bluelab.connect.model.SettingFileModel;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class SettingsAndSystemProperties {
   public static Map<String, String> GetUserInfo(SettingFileModel settingFileModel, List<BluelabRemoteXbeeDevice> deviceList) {
      LinkedHashMap ret = new LinkedHashMap();
      GetSystemProperties(ret);
      ret.put("setting.unit.temperature", settingFileModel.temperatureUnit);
      ret.put("setting.unit.conductivity", settingFileModel.conductivityUnit);
      ret.put("setting.unit.dosing", settingFileModel.ratioUnit);
      ret.put("setting.log.directory", settingFileModel.logDataDirectory);
      ret.put("setting.log.interval", String.valueOf(settingFileModel.logDataInterval));
      ret.put("setting.alert.email", String.valueOf(settingFileModel.emailAlerts));
      if (deviceList != null) {
         ret.put("device.count", String.valueOf(deviceList.size()));
      }

      return ret;
   }

   private static void GetSystemProperties(Map<String, String> propertyMap) {
      String[] properties = new String[]{"os.name", "os.arch", "os.version", "java.version", "user.country", "user.language", "user.timezone"};

      for(int idx = 0; idx < 7; ++idx) {
         String property = properties[idx];
         propertyMap.put(property, System.getProperty(property));
      }

   }
}