package bluelab.connect.d;

import bluelab.connect.c.BluelabRemoteXbeeDevice;
import bluelab.connect.model.DeviceFileModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;

public final class DevicesFileManager {
   public static synchronized void SaveDevicesToFile(List<BluelabRemoteXbeeDevice> list, String file) {
      Path filepath = Paths.get(file);

      try {
         Throwable lastException = null;

         try {
            BufferedWriter writer = Files.newBufferedWriter(filepath, StandardCharsets.UTF_8);

            try {
               ArrayList deviceModels = new ArrayList(list.size());
               Iterator iterator = list.iterator();

               while(iterator.hasNext()) {
                  BluelabRemoteXbeeDevice var14 = (BluelabRemoteXbeeDevice)iterator.next();
                  deviceModels.add(new DeviceFileModel(var14));
               }

               (new GsonBuilder()).setPrettyPrinting().setVersion(1.0D).create().toJson(deviceModels, writer);
            } finally {
               if (writer != null) {
                  writer.close();
               }

            }
         } catch (Throwable ex) {
            if (lastException == null) {
               lastException = ex;
            } else if (lastException != ex) {
               lastException.addSuppressed(ex);
            }

            throw lastException;
         }
      } catch (IOException ex) {
         WeirdEncoder.ReportException((Throwable)ex);
      }

   }

   public static synchronized CopyOnWriteArrayList<BluelabRemoteXbeeDevice> LoadDevicesFromFile(String filename) {
      ArrayList var1 = new ArrayList(0);
      Path var2 = Paths.get(filename);
      Charset var3 = StandardCharsets.UTF_8;
      boolean var4 = false;

      do {
         try {
            Throwable var5 = null;

            try {
               JsonReader var6 = new JsonReader(Files.newBufferedReader(var2, var3));

               try {
                  List var7 = (List)(new Gson()).fromJson(var6, (new e()).getType());
                  var1 = new ArrayList(var7.size());
                  Iterator var8 = var7.iterator();

                  while(var8.hasNext()) {
                     DeviceFileModel var21 = (DeviceFileModel)var8.next();
                     var1.add(new BluelabRemoteXbeeDevice(var21));
                  }

                  var4 = false;
               } finally {
                  var6.close();
               }
            } catch (Throwable var18) {
               if (var5 == null) {
                  var5 = var18;
               } else if (var5 != var18) {
                  var5.addSuppressed(var18);
               }

               throw var5;
            }
         } catch (NoSuchFileException | FileNotFoundException var19) {
            var4 = false;
         } catch (JsonSyntaxException var20) {
            if (var4) {
               Files.copy(var2, Paths.get(filename + ".bad"), StandardCopyOption.REPLACE_EXISTING);
               throw var20;
            }

            var3 = StandardCharsets.ISO_8859_1;
            var4 = true;
         }
      } while(var4);

      return new CopyOnWriteArrayList(var1);
   }

   public static synchronized CopyOnWriteArrayList<BluelabRemoteXbeeDevice> CreateDevicesWithKeycodesFromFile(String filename) {
      ArrayList var1 = new ArrayList(0);

      try {
         File var5;
         if ((var5 = new File(filename)).exists()) {
            ArrayList var2 = new ArrayList();
            Scanner var6;
            (var6 = new Scanner(new FileReader(var5.getPath()))).nextLine();

            DeviceFileModel var8;
            while(var6.hasNext()) {
               for(int var3 = 0; var3 < 3; ++var3) {
                  var6.nextLine();
               }

               (var8 = new DeviceFileModel()).setKeycode(var6.nextLine());
               var2.add(var8);
            }

            var6.close();
            var1 = new ArrayList(var2.size());
            Iterator var7 = var2.iterator();

            while(var7.hasNext()) {
               var8 = (DeviceFileModel)var7.next();
               var1.add(new BluelabRemoteXbeeDevice(var8));
            }
         }
      } catch (Throwable var4) {
         WeirdEncoder.ReportException(var4);
      }

      return new CopyOnWriteArrayList(var1);
   }
}