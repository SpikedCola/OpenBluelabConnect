package bluelab.connect.c;

import java.util.EnumSet;

public abstract class Peripod {
   private String name;
   private String productVersion;
   private String firmwareVersion;
   private Enum_PeripodType peripod;
   private EnumSet<Enum_Messages> messages;

   public final String getName() {
      return this.name;
   }

   public final void setName(String name) {
      this.name = name;
   }

   public final String getProductVersion() {
      return this.productVersion;
   }

   public final void setProductVersion(String var1) {
      this.productVersion = var1;
   }

   public final String getFirmwareVersion() {
      return this.firmwareVersion;
   }

   public final void setFirmwareVersion(String firmwareVersion) {
      this.firmwareVersion = firmwareVersion;
   }

   public final Enum_PeripodType getPeripod() {
      return this.peripod;
   }

   public final boolean containsMessage(Enum_Messages message) {
      return this.messages.contains(message);
   }

   public final void addRemoveMessage(Enum_Messages message, boolean add) {
      if (add) {
         this.messages.add(message);
      } else {
         this.messages.remove(message);
      }
   }

   protected Peripod(Enum_Peripod unused, String name, int unused2, Enum_PeripodType peripod) {
      this.name = name;
      this.productVersion = "";
      this.firmwareVersion = "";
      this.peripod = peripod;
      this.messages = EnumSet.noneOf(Enum_Messages.class);
   }
}