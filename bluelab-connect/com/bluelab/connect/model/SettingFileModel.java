package bluelab.connect.model;

import bluelab.connect.Connect;
import bluelab.connect.f.b;
import bluelab.connect.k.Enum_ECUnit;
import bluelab.connect.k.Enum_VolumeUnit;
import bluelab.connect.k.Enum_TemperatureUnit;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

public class SettingFileModel {
   public static final String SETTINGS_FILE_NAME;
   public String email;
   public long customerNr;
   public long entityId;
   public String accessToken;
   public String refreshToken;
   public String temperatureUnit;
   public String conductivityUnit;
   public String ratioUnit;
   public String logDataDirectory;
   public long logDataInterval;
   public Boolean emailAlerts;
   public Boolean showWelcome;
   public String serviceUrl;
   public String updateUrl;
   public Boolean diagnostics;
   public Boolean experimental;

   static {
      SETTINGS_FILE_NAME = Connect.GetUserDataDirectory() + File.separator + "settings.json";
   }

   public SettingFileModel() {
      this.clearUserData();
      this.temperatureUnit = Enum_TemperatureUnit.GetTemperatureUnitC().getText();
      this.conductivityUnit = Enum_ECUnit.GetECType().getText();
      this.ratioUnit = Enum_VolumeUnit.GetVolumeTypeML().getText();
      this.logDataDirectory = Connect.logDirectory;
      this.logDataInterval = (long)b.b().getValue();
      this.emailAlerts = Boolean.FALSE;
      this.showWelcome = Boolean.TRUE;
      this.serviceUrl = null;
      this.updateUrl = null;
      this.diagnostics = null;
      this.experimental = null;
   }

   public void clearUserData() {
      this.email = null;
      this.customerNr = 0L;
      this.entityId = 0L;
      this.accessToken = "";
      this.refreshToken = "";
   }

   public boolean equals(Object var1) {
      if (var1 == null) {
         return false;
      } else if (!SettingFileModel.class.isAssignableFrom(var1.getClass())) {
         return false;
      } else {
         SettingFileModel var2 = (SettingFileModel)var1;
         return Objects.equals(this.email, var2.email) && this.customerNr == var2.customerNr && this.entityId == var2.entityId && Objects.equals(this.accessToken, var2.accessToken) && Objects.equals(this.refreshToken, var2.refreshToken) && Objects.equals(this.temperatureUnit, var2.temperatureUnit) && Objects.equals(this.conductivityUnit, var2.conductivityUnit) && Objects.equals(this.ratioUnit, var2.ratioUnit) && Objects.equals(this.logDataDirectory, var2.logDataDirectory) && this.logDataInterval == var2.logDataInterval && Objects.equals(this.emailAlerts, var2.emailAlerts) && Objects.equals(this.showWelcome, var2.showWelcome) && Objects.equals(this.serviceUrl, var2.serviceUrl) && Objects.equals(this.updateUrl, var2.updateUrl) && Objects.equals(this.diagnostics, var2.diagnostics) && Objects.equals(this.experimental, var2.experimental);
      }
   }

   public boolean isDiagnosticsEnabled() {
      return this.diagnostics != null ? this.diagnostics : false;
   }

   public boolean isExperimentalEnabled() {
      return this.experimental != null ? this.experimental : false;
   }

   public boolean isLoggedIn() {
      return this.email != null;
   }

   public boolean showWelcome() {
      return this.showWelcome;
   }

   public void save() {
      this.save(SETTINGS_FILE_NAME);
   }

   public static SettingFileModel load()
   throws Throwable
   {
      return load(SETTINGS_FILE_NAME);
   }

   protected synchronized void save(String param1) {
      // $FF: Couldn't be decompiled
   }

   protected static synchronized SettingFileModel load(String var0) 
   throws Throwable
   {
      SettingFileModel var1 = new SettingFileModel();
      Path var2 = Paths.get(var0);
      Charset var3 = StandardCharsets.UTF_8;
      boolean var4 = false;

      do {
         try {
            Throwable var5 = null;

            try {
               JsonReader var6 = new JsonReader(Files.newBufferedReader(var2, var3));

               try {
                  var1 = (SettingFileModel)(new Gson()).fromJson(var6, SettingFileModel.class);
                  var4 = false;
               } finally {
                  var6.close();
               }
            } catch (Throwable var16) {
               if (var5 == null) {
                  var5 = var16;
               } else if (var5 != var16) {
                  var5.addSuppressed(var16);
               }

               throw var5;
            }
         } catch (NoSuchFileException | FileNotFoundException var17) {
            var4 = false;
         } catch (JsonSyntaxException var18) {
            if (var4) {
               Files.copy(var2, Paths.get(var0 + ".bad"), StandardCopyOption.REPLACE_EXISTING);
               throw var18;
            }

            var3 = StandardCharsets.ISO_8859_1;
            var4 = true;
         }
      } while(var4);

      return var1;
   }
}