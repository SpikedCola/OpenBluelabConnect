package bluelab.connect.c;

public final class PeripodFactory {
   public static Peripod InstantiatePeripod(int unused, Enum_PeripodType peripodType, int numPumps) throws Exception2 {
      if (peripodType.getPodType().equals(Enum_Peripod.PERIPOD)) {
         PeripodWithPumps var3 = new PeripodWithPumps(peripodType.getText(), unused, peripodType, numPumps, 0.0D);
         return var3;
      } else {
         throw new Exception2(String.format("Pod product ID not supported (%d).", peripodType.getValue()));
      }
   }
}