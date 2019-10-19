package bluelab.connect.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.Assert;
import org.junit.Test;

public class SettingFileModelTest {
   @Test
   public void testSettings() {
      Assert.assertNotNull(new SettingFileModel());
   }

   @Test
   public void testEquals() {
      String var1 = "ciao@mars.com";
      SettingFileModel var2 = new SettingFileModel();
      SettingFileModel var3 = new SettingFileModel();
      Assert.assertEquals(var2, var3);
      var2.email = var1;
      Assert.assertNotEquals(var2, var3);
      var3.email = var1;
      Assert.assertEquals(var2, var3);
   }

   @Test
   public void testSaveLoad() throws Throwable {
      String var1 = "settings_test.json";
      SettingFileModel var2 = new SettingFileModel();
      Path var3 = Paths.get(var1);

      try {
         Files.deleteIfExists(var3);
      } catch (IOException var7) {
         Assert.fail("Could not delete settings test file.");
      }

      try {
         SettingFileModel.load(var1);
      } catch (Exception var6) {
         Assert.fail("Exception thrown while trying to load from non existing file.");
      }

      var2.email = "hello@world.com";
      var2.save(var1);
      Assert.assertTrue(Files.exists(var3, new LinkOption[0]));

      try {
         SettingFileModel var8 = SettingFileModel.load(var1);
         Assert.assertEquals(var2, var8);
      } catch (Exception var5) {
         Assert.fail("Exception thrown while trying to load settings from existing file.");
      }

      try {
         var2.save(var1);
      } catch (Exception var4) {
         Assert.fail("Exception thrown while trying to save settings to existing file.");
      }
   }
}