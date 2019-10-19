package bluelab.connect.l;

public final class VersionComparer implements Comparable<VersionComparer> {
   private String version;

   public VersionComparer(String version) {
      if (version == null) {
         throw new IllegalArgumentException("Version can not be null");
      } else if (!version.matches("[0-9]+(\\.[0-9]+)*")) {
         throw new IllegalArgumentException("Invalid version format");
      } else {
         this.version = parseVersionString(version);
      }
   }

   private static String parseVersionString(String version) {
      String[] versionParts = version.split("\\.");
      String ret = new String("");

      for(int idx = 0; idx < versionParts.length; ++idx) {
         ret = ret + Integer.parseInt(versionParts[idx]);
         if (idx < versionParts.length - 1) {
            ret = ret + ".";
         }
      }

      return ret;
   }

   public final int compareTo(VersionComparer suppliedVersion) {
      if (suppliedVersion == null) {
         return 1;
      } else {
         String[] currentVersionParts = this.version.split("\\.");
         String[] suppliedVersionParts = suppliedVersion.version.split("\\.");
         int var3 = Math.max(currentVersionParts.length, suppliedVersionParts.length);

         for(int var4 = 0; var4 < var3; ++var4) {
            int var5 = var4 < currentVersionParts.length ? Integer.parseInt(currentVersionParts[var4]) : 0;
            int var6 = var4 < suppliedVersionParts.length ? Integer.parseInt(suppliedVersionParts[var4]) : 0;
            if (var5 < var6) {
               return -1;
            }

            if (var5 > var6) {
               return 1;
            }
         }

         return 0;
      }
   }

   public final boolean equals(Object test) {
      if (test == null) {
         return false;
      } else if (this == test) {
         return true;
      } else if (this.getClass() != test.getClass()) {
         return false;
      } else {
         return this.compareTo((VersionComparer)test) == 0;
      }
   }

}