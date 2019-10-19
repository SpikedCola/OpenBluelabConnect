package bluelab.connect.model;

import bluelab.connect.c.Peripod;
import bluelab.connect.c.PeripodFactory;
import bluelab.connect.c.Enum_PeripodType;
import bluelab.connect.c.Enum_PumpType;
import bluelab.connect.c.Exception2;
import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;

public class PodChainFileModelTest {
   private static final double EPSILON = 1.0E-6D;

   @Test
   public void testConstructorFromPods() {
      try {
         new PodChainFileModel(this.createPods(), "ml");
      } catch (Exception var1) {
         Assert.fail("Pod chain file model constructor error.");
      }
   }

   @Test
   public void testCopyConstructor() {
      try {
         PodChainFileModel var1 = new PodChainFileModel(this.createPods(), "ml");
         PodChainFileModel var2 = new PodChainFileModel(var1);
         PumpSettings var4 = ((PodSettings)var1.getPodSettings().get(1)).getPumpSettingsByIdx(0);
         PumpSettings var5 = ((PodSettings)var2.getPodSettings().get(1)).getPumpSettingsByIdx(0);
         var4.name = "hello";
         Assert.assertEquals("", var5.name);
      } catch (Exception var3) {
         Assert.fail("Pod chain file model constructor error.");
      }
   }

   @Test
   public void testCountsBeforeInit() {
      PodChainFileModel var1 = new PodChainFileModel();
      Assert.assertEquals(0L, (long)var1.getPodCount());
      Assert.assertEquals(0L, (long)var1.getPumpCount());
   }

   @Test
   public void testCountsAfterInit() throws Exception2 {
      PodChainFileModel var1 = new PodChainFileModel(this.createPods(), "ml");
      Assert.assertEquals(2L, (long)var1.getPodCount());
      Assert.assertEquals(7L, (long)var1.getPumpCount());
   }

   @Test
   public void testDisablePumps() throws Exception2 {
      PodChainFileModel var1;
      PumpSettings var2;
      (var2 = ((PodSettings)(var1 = new PodChainFileModel(this.createPods(), "ml")).getPodSettings().get(1)).getPumpSettingsByIdx(0)).pumpType = Enum_PumpType.EC;
      var2.ratio = 100;
      var1.disablePumps();
      Assert.assertEquals(Enum_PumpType.OFF, var2.pumpType);
      Assert.assertEquals(0L, (long)var2.ratio);
   }

   @Test
   public void testImportSettings() throws Exception2 {
      PodChainFileModel var1 = new PodChainFileModel(this.createPods(), "ml");
      PodChainFileModel var2 = new PodChainFileModel(this.createPod(), "l");
      PumpSettings var3 = ((PodSettings)var1.getPodSettings().get(1)).getPumpSettingsByIdx(0);
      PumpSettings var4 = ((PodSettings)var2.getPodSettings().get(1)).getPumpSettingsByIdx(0);
      var3.name = "hello";
      var3.pumpType = Enum_PumpType.PH;
      var3.ratio = 50;
      var3.value = 42.0D;
      var2.importPumpSettings(var1);
      Assert.assertNotEquals((long)var1.getPodCount(), (long)var2.getPodCount());
      Assert.assertNotEquals((long)var1.getPumpCount(), (long)var2.getPumpCount());
      Assert.assertEquals(var3, var4);
      Assert.assertEquals(var3.name, var4.name);
      Assert.assertEquals(var3.value, var4.value, 1.0E-6D);
      Assert.assertNotEquals(var3.unit, var4.unit);
   }

   private Map<Integer, Peripod> createPods() throws Exception2 {
      LinkedHashMap var1;
      (var1 = new LinkedHashMap()).put(1, PeripodFactory.InstantiatePeripod(1, Enum_PeripodType.PERIPOD_M3, 3));
      var1.put(2, PeripodFactory.InstantiatePeripod(2, Enum_PeripodType.PERIPOD_M4, 4));
      return var1;
   }

   private Map<Integer, Peripod> createPod() throws Exception2 {
      LinkedHashMap var1;
      (var1 = new LinkedHashMap()).put(1, PeripodFactory.InstantiatePeripod(1, Enum_PeripodType.PERIPOD_M4, 4));
      return var1;
   }
}