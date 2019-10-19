package bluelab.connect.c.b;

import java.time.Instant;

public final class InstantNow implements GetInstant {
   public final Instant GetInstant() {
      return Instant.now();
   }
}