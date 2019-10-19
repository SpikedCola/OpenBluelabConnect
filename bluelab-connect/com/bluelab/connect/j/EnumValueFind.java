package bluelab.connect.j;

import java.util.Arrays;
import java.util.function.Predicate;

public final class EnumValueFind {
   public static <T extends Enum<T> & Interface_GetValue> T FindValue(Class<T> enumClass, int search, T defaultResult) {
      return (Enum)Arrays.stream((Enum[])enumClass.getEnumConstants()).filter((var1x) -> {
         return ((Interface_GetValue)var1x).getValue() == search;
      }).findFirst().orElse(defaultResult);
   }

   public static <T extends Enum<T> & Interface_GetText> T FindValueCaseInsensitive(Class<T> enumClass, String search, T defaultResult) {
      return (Enum)Arrays.stream((Enum[])enumClass.getEnumConstants()).filter((var1x) -> {
         return ((Interface_GetText)var1x).getText().equalsIgnoreCase(search);
      }).findFirst().orElse(defaultResult);
   }
}