package bluelab.connect.k;

public enum Enum_VolumeUnit implements bluelab.connect.j.Interface_GetText {
   MILLILITRE("ml"),
   MILLILITRE_PER_GALLON("ml/gal"),
   MILLILITRE_PER_LITRE("ml/l");

   private final String text;

   private Enum_VolumeUnit(String text) {
      this.text = text;
   }

   public final String getText() {
      return this.text;
   }

   public final String toString() {
      return this.text;
   }

   public static Enum_VolumeUnit GetVolumeTypeML() {
      return MILLILITRE;
   }
}