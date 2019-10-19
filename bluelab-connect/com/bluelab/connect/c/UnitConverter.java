package bluelab.connect.c;

import bluelab.connect.model.SettingFileModel;
import java.util.Locale;

public final class UnitConverter {
   public static String ConvertConductivityUnit(double var0, String var2) {
      bluelab.connect.k.Enum_ECUnit var12 = (bluelab.connect.k.Enum_ECUnit)bluelab.connect.j.EnumValueFind.FindValueCaseInsensitive(bluelab.connect.k.Enum_ECUnit.class, var2, bluelab.connect.k.Enum_ECUnit.EC);
      bluelab.connect.k.Enum_ECUnit var7 = bluelab.connect.k.Enum_ECUnit.EC;
      double var9 = ((bluelab.connect.k.Interface_ECFactor)var12).getFactor() / ((bluelab.connect.k.Interface_ECFactor)var7).getFactor();
      var0 *= var9;
      if (var12.equals(bluelab.connect.k.Enum_ECUnit.PPM) || var12.equals(bluelab.connect.k.Enum_ECUnit.TDS)) {
         double var11 = 10.0D;
         var0 = (double)Math.round(var0 / 10.0D) * 10.0D;
      }

      if (var12.equals(bluelab.connect.k.Enum_ECUnit.EC)) {
         var2 = "%.1f";
      } else {
         var2 = "%.0f";
      }

      return String.format(Locale.ROOT, var2, var0);
   }

   public static String FormatConductivityAsEC(double var0) {
      return String.format(Locale.ROOT, "%.1f", var0);
   }

   public static String ConvertTemperatureUnit(double var0, String var2) {
      bluelab.connect.k.Enum_TemperatureUnit var7 = (bluelab.connect.k.Enum_TemperatureUnit)bluelab.connect.j.EnumValueFind.FindValueCaseInsensitive(bluelab.connect.k.Enum_TemperatureUnit.class, var2, bluelab.connect.k.Enum_TemperatureUnit.DEG_C);
      double var10000 = var0;
      bluelab.connect.k.Enum_TemperatureUnit var6 = bluelab.connect.k.Enum_TemperatureUnit.DEG_C;
      double var4 = var10000;
      var0 = var6.equals(bluelab.connect.k.Enum_TemperatureUnit.DEG_C) && var7.equals(bluelab.connect.k.Enum_TemperatureUnit.DEG_F) ? var4 * 1.8D + 32.0D : (var6.equals(bluelab.connect.k.Enum_TemperatureUnit.DEG_F) && var7.equals(bluelab.connect.k.Enum_TemperatureUnit.DEG_C) ? (var4 - 32.0D) / 1.8D : var4);
      return String.format(Locale.ROOT, "%.0f", var0);
   }

   public static String GetUnit(Enum_ControlType type, SettingFileModel settingFileModel) {
      String unit = "";
      if (type.equals(Enum_ControlType.CONDUCTIVITY)) {
         unit = settingFileModel.conductivityUnit;
      } else if (type.equals(Enum_ControlType.PH)) {
         unit = "pH";
      } else if (type.equals(Enum_ControlType.TEMPERATURE)) {
         unit = settingFileModel.temperatureUnit;
      }

      return unit;
   }
}