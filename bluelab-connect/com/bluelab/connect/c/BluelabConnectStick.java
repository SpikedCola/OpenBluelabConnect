package bluelab.connect.c;

import bluelab.connect.Connect;
import com.digi.xbee.api.XBeeDevice;
import com.digi.xbee.api.exceptions.TimeoutException;
import com.digi.xbee.api.exceptions.XBeeException;
import com.digi.xbee.api.models.AssociationIndicationStatus;
import com.digi.xbee.api.models.XBeeMessage;
import com.digi.xbee.api.utils.HexUtils;
import com.google.common.collect.Lists;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.RXTXPort;
import gnu.io.UnsupportedCommOperationException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BluelabConnectStick extends XBeeDevice {
   private static Logger logger = LoggerFactory.getLogger(BluelabConnectStick.class);
   public static final Object readMutex = new Object();
   private bluelab.connect.h.StateManager stateManager = null;

   private BluelabConnectStick(String port, int baud, int databits, int stopbits, int var5, int var6) {
      super(port, 115200, 8, 1, 0, 0);
   }

   public final void setStateManager(bluelab.connect.h.StateManager var1) {
      this.stateManager = var1;
   }

   public static BluelabConnectStick FindConnectStickAndCreateLocalXbeeDevice() {
      BluelabConnectStick dev = null;
      logger.info("Gathering available serial ports...");
      List availablePorts;
      if ((availablePorts = GetAvailableCOMPorts()).isEmpty()) {
         logger.error("Could not find any available communication ports.");
      }

      Iterator availablePortsIterator = Lists.reverse(availablePorts).iterator();

      while(availablePortsIterator.hasNext()) {
         String port = (String)availablePortsIterator.next();
         logger.info("Trying to open port '{}'...", port);
         if (DetectConnectStickOnPort(port)) {
            try {
               (dev = new BluelabConnectStick(port, 115200, 8, 1, 0, 0)).setReceiveTimeout(3000);
               dev.open();
               logger.info("Trying to reset stick on port '{}'...", port);
               dev.reset();
               break;
            } catch (XBeeException ex) {
               bluelab.connect.d.WeirdEncoder.ReportException((Throwable)ex);
               logger.error("Connect stick exception: {}", ex.toString());
            }
         } else {
            dev = null;
         }
      }

      return dev;
   }

   private static List<String> GetAvailableCOMPorts() {
      ArrayList list = new ArrayList();
      Enumeration ports = CommPortIdentifier.getPortIdentifiers();

      while(ports.hasMoreElements()) {
         CommPortIdentifier port;
         String comPortName = (port = (CommPortIdentifier)ports.nextElement()).getName();
         if (port.getPortType() == 1) {
            try {
               port.open("CommUtil", 50).close();
               list.add(comPortName);
            } catch (PortInUseException ex) {
               logger.info("Port '{}' is in use.", comPortName);
            } catch (Throwable ex) {
               logger.error("Failed to open port '{}'", comPortName);
               bluelab.connect.d.WeirdEncoder.ReportException(ex);
            }
         }
      }

      return list;
   }

   private static boolean DetectConnectStickOnPort(String comPortName) {
      boolean validResponse = false;

      try {
         byte[] identPayload = new byte[]{126, 0, 4, 8, 1, 68, 68, 110};
         byte[] expectedResponse = new byte[]{126, 0, 9, -120, 1, 68, 68, 0, 0, 3, 0, 12};
         byte[] responseBuffer = new byte[12];
         RXTXPort port;
         (port = (RXTXPort)CommPortIdentifier.getPortIdentifier(comPortName).open(Connect.GetTitle(), 500)).enableReceiveTimeout(500);
         port.setSerialPortParams(115200, 8, 1, 0);
         port.setFlowControlMode(0);
         OutputStream outputStream = port.getOutputStream();
         InputStream inputStream = port.getInputStream();
         outputStream.write(identPayload);
         
         int totalBytesRead = 0;
         int bytesRead;
         // try and read up to 12 bytes from connect stick
         do {
            bytesRead = inputStream.read(responseBuffer, totalBytesRead, 12 - totalBytesRead);
            totalBytesRead += bytesRead;
         } while(bytesRead > 0 && totalBytesRead < 12);

         port.close();
         validResponse = Arrays.equals(expectedResponse, responseBuffer);
      } catch (PortInUseException | UnsupportedCommOperationException | IOException | NoSuchPortException var7) {
         bluelab.connect.d.WeirdEncoder.ReportException((Throwable)var7);
         logger.error("Valid Connect stick could not be detected: {}", var7.toString());
      }

      return validResponse;
   }

   public AssociationIndicationStatus getAssociationIndicationStatus() throws TimeoutException, XBeeException {
        return super.getAssociationIndicationStatus();
   }

   public final byte[] sendReceiveData(BluelabRemoteXbeeDevice dev, byte[] data, int attempts) throws TimeoutException, XBeeException {
      this.readDiscardRemainingData(dev);
      this.sendDataToRemoteDevice(dev, data, attempts);
      return this.receiveData(dev, attempts);
   }

   public final bluelab.connect.g.DeviceMessage readMessageFromDevice(BluelabRemoteXbeeDevice dev, bluelab.connect.g.DeviceMessage request, int dataAttempts)  throws TimeoutException, XBeeException {
      int messageReadParseAttempts = 10;
      Object response = null;
      this.readDiscardRemainingData(dev);
      this.sendDataToRemoteDevice(dev, request.ConstructPacket(), dataAttempts);

      do {
         byte[] receivedData = this.receiveData(dev, dataAttempts);
         if (request instanceof bluelab.connect.g.ObfuscatedMessage) {
            byte innerCommand = ((bluelab.connect.g.ObfuscatedMessage)request).getInnerCommand();
            if (bluelab.connect.g.ObfuscatedMessage.XorCheck(request.getCommand(), innerCommand, receivedData)) {
               response = new bluelab.connect.g.ObfuscatedMessage(receivedData);
            }
         } else if (request instanceof bluelab.connect.g.ObfuscatedDeviceMessage) {
            if (bluelab.connect.g.DeviceMessage.TestCommandByte(request.getCommand(), receivedData)) {
               response = new bluelab.connect.g.ObfuscatedDeviceMessage(receivedData);
            }
         } else if (bluelab.connect.g.DeviceMessage.TestCommandByte(request.getCommand(), receivedData)) {
            response = new bluelab.connect.g.DeviceMessage(receivedData);
         }
      } while(messageReadParseAttempts-- > 0 && response == null);

      return (bluelab.connect.g.DeviceMessage)response;
   }

   private void readDiscardRemainingData(BluelabRemoteXbeeDevice remoteDev) {
       // read data and throw away
       // clear buffer?
       // timeout 10 seconds
      while(this.readDataFrom(remoteDev.getRemoteXbeeDevice(), 10) != null) {
         ;
      }

   }

   public final void sendDataToRemoteDevice(BluelabRemoteXbeeDevice remoteDevice, byte[] data, int numAttempts) throws TimeoutException, XBeeException {
      byte attempts = 0;

      do {
         try {
            int currentAttempt = attempts + 1;
            logger.info("Sending data (attempt {}) [{}]", currentAttempt, HexUtils.prettyHexString(data));
            this.sendData(remoteDevice.getRemoteXbeeDevice(), data);
            if (this.stateManager != null) {
               this.stateManager.incrementStateCounter(remoteDevice.getKeyCode(), remoteDevice.get16BitAddress(), bluelab.connect.h.Enum_TransmitReceiveState.TRANSMIT);
               return;
            }
            break;
         } catch (TimeoutException ex) {
            if (this.stateManager != null) {
               this.stateManager.incrementStateCounter(remoteDevice.getKeyCode(), remoteDevice.get16BitAddress(), bluelab.connect.h.Enum_TransmitReceiveState.TIMEOUT);
            }

            if (numAttempts <= 0) {
               throw new TimeoutException("Data transmission timeout");
            }
         } catch (XBeeException ex) {
            if (numAttempts <= 0) {
               throw ex;
            }
         }
      } while(numAttempts-- > 0);

   }

   private byte[] receiveData(BluelabRemoteXbeeDevice dev, int numAttempts) throws TimeoutException {
      byte[] messageData = new byte[0];

      do {
         XBeeMessage message;
         if ((message = this.readDataFrom(dev.getRemoteXbeeDevice())) != null) {
            messageData = message.getData();
            // @bug? attempts hardcoded to 1, doesnt use numAttempts arg
            logger.info("Received data (attempt {}) [{}]", 1, HexUtils.prettyHexString(messageData));
            if (this.stateManager != null) {
               this.stateManager.incrementStateCounter(dev.getKeyCode(), dev.get16BitAddress(), bluelab.connect.h.Enum_TransmitReceiveState.RECEIVE);
            }
            break;
         }

         if (this.stateManager != null) {
            this.stateManager.incrementStateCounter(dev.getKeyCode(), dev.get16BitAddress(), bluelab.connect.h.Enum_TransmitReceiveState.TIMEOUT);
         }

         if (numAttempts <= 0) {
            throw new TimeoutException("Data reception timeout");
         }
      } while(numAttempts-- > 0);

      return messageData;
   }
}