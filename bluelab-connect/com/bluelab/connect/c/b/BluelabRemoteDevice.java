package bluelab.connect.c.b;

import bluelab.connect.c.BluelabRemoteXbeeDevice;
import bluelab.connect.c.Exception2;
import bluelab.connect.model.PodSettings;
import bluelab.connect.c.c.BluelabException;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BluelabRemoteDevice {
   static Logger logger = LoggerFactory.getLogger(BluelabRemoteDevice.class);
   protected BluelabRemoteXbeeDevice device;
   private BluelabByteBuffer byteBuffer;
   private boolean e;
   protected GetInstant instant;

   protected BluelabRemoteDevice(BluelabRemoteXbeeDevice remoteDev) {
      this(remoteDev, false);
   }

   protected BluelabRemoteDevice(BluelabRemoteXbeeDevice remoteDev, boolean var2) {
      this.device = remoteDev;
      this.byteBuffer = new BluelabByteBuffer();
      this.e = var2;
      this.instant = new InstantNow();
   }

   public final void setControllerMonthDate(Supplier<bluelab.connect.c.a.MonthDate> monthDate) {
      this.device.getControlTypeControllerMap().forEach((var1x, controller) -> {
         controller.setMonthDate((bluelab.connect.c.a.MonthDate)monthDate.get());
      });
   }

   public final boolean a() {
      return this.e;
   }

   public List<bluelab.connect.g.DeviceMessage> buildMessageList() {
      return new ArrayList(0);
   }

   public List<bluelab.connect.g.DeviceMessage> a(boolean var1, byte var2)
    throws BluelabException
   {
      return new ArrayList(0);
   }

   public final int processBytes(byte[] buf) 
   throws BluelabException
   {
      int numBytesProcessed = 0;
      this.byteBuffer.addBytes(buf);

      bluelab.connect.g.ByteArray message;
      do {
         try {
            if (this.isValid2(this.byteBuffer.getBuffer())) {
               if ((message = this.createDeviceMessage(this.byteBuffer.getBuffer())) != null) {
                  this.processMessage(message);
                  ++numBytesProcessed;
               }
            } else {
               message = null;
            }
         } catch (BluelabException ex) {
            this.byteBuffer.compactOrClear();
            throw ex;
         } catch (Throwable ex) {
            message = null;
            logger.error("Processing error: {}", ex.toString());
         }
      } while(message != null);

      this.byteBuffer.compactOrClear();
      return numBytesProcessed;
   }

   protected abstract boolean isValid2(ByteBuffer var1);

   protected abstract bluelab.connect.g.ByteArray createDeviceMessage(ByteBuffer var1);

   protected abstract void processMessage(bluelab.connect.g.ByteArray var1) throws BluelabException, Exception2;

   public abstract List<bluelab.connect.g.DeviceMessage> getDeviceMessages(bluelab.connect.c.c.MonitorController var1) throws BluelabException;

   public abstract List<bluelab.connect.g.DeviceMessage> getDeviceMessages(PodSettings var1, byte var2);

   public static bluelab.connect.c.Enum_DeviceVersion ParseDeviceVersion(byte[] buf) {
      bluelab.connect.c.Enum_DeviceVersion version;
      if (bluelab.connect.g.DeviceMessage.TestCommandByte((byte)bluelab.connect.d.Enum_Command.WHO_ARE_YOU.getValue(), buf)) {
         bluelab.connect.g.DeviceMessage var1 = new bluelab.connect.g.DeviceMessage(buf);
		 // getbyId(n, true) means return UNSUPPORTED even if cant parse
		 version = bluelab.connect.c.Enum_DeviceVersion.GetByID(new String(var1.GetInternalByteArray()), true);
         if (version == bluelab.connect.c.Enum_DeviceVersion.UNSUPPORTED) {
            bluelab.connect.g.ObfuscatedDeviceMessage var3 = new bluelab.connect.g.ObfuscatedDeviceMessage(buf);
            version = bluelab.connect.c.Enum_DeviceVersion.GetByID(new String(var3.GetInternalByteArray()), true);
         }
      } else {
         version = bluelab.connect.c.Enum_DeviceVersion.GetByID(new String(buf), false);
      }

      return version;
   }

   protected static boolean TestBit(long num, int shift) {
      return (num >> shift & 1L) > 0L;
   }

   protected static long SetClearBit(long num, int bitNumber, boolean setBit) {
      if (setBit) {
         num |= (long)(1 << bitNumber);
      } else {
         num &= (long)(~(1 << bitNumber));
      }

      return num;
   }

   protected static String ReadStringFromBuffer(ByteBuffer buf, int maxLen) {
      ByteArrayOutputStream retBuf = new ByteArrayOutputStream();
      byte character = buf.get();

      for(int idx = 1; character != 0 && idx <= maxLen; ++idx) {
         retBuf.write(character);
         character = buf.get();
      }

      return (new String(retBuf.toByteArray(), StandardCharsets.US_ASCII)).trim();
   }
}