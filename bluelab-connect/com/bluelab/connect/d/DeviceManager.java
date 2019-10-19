package bluelab.connect.d;

import bluelab.connect.c.BluelabRemoteXbeeDevice;
import bluelab.connect.model.SettingFileModel;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import bluelab.connect.Web.Interface_OnSuccess;
import bluelab.connect.Web.Interface_OnFailure;

public class DeviceManager {
   private static Logger logger = LoggerFactory.getLogger(DeviceManager.class);
   private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
   private SettingFileModel settingFileModel;
   private List<Interface_DeviceList> deviceList;

   public DeviceManager(SettingFileModel var1, Interface_DeviceList var2) {
      this.settingFileModel = var1;
      this.deviceList = new ArrayList();
      if (var2 != null) {
         this.deviceList.add(var2);
      }

   }

   public final void registerDevices(List<BluelabRemoteXbeeDevice> deviceList) {
      Iterator iterator = deviceList.iterator();

      while(iterator.hasNext()) {
         BluelabRemoteXbeeDevice remoteDev = (BluelabRemoteXbeeDevice)iterator.next();
         if (this.settingFileModel.isLoggedIn() && !remoteDev.allControlTypeManagerIdsNonZero()) {
            logger.info("Registering device '{}'...", remoteDev.getKeyCode());
                bluelab.connect.Web.DeviceTokenHandler tokenHandler;
            (tokenHandler = new bluelab.connect.Web.DeviceTokenHandler(this.settingFileModel.entityId, remoteDev, this.settingFileModel)).setCallbacks(this::deviceRegistrationSuccess, this::deviceRegistrationError);
            tokenHandler.run();
         }
      }

   }

   public final void updateRegisteredDevices(List<BluelabRemoteXbeeDevice> deviceList) {
      deviceList = WeirdEncoder.FilterCollect(deviceList, (var0) -> {
         return var0.allControlTypeManagerIdsNonZero();
      });
      if (this.settingFileModel.isLoggedIn() && !deviceList.isEmpty()) {
         logger.info("Updating registered devices...");
            bluelab.connect.Web.PutDeviceToken var2;
         (var2 = new bluelab.connect.Web.PutDeviceToken(this.settingFileModel.entityId, deviceList, this.settingFileModel)).setCallbacks(this::deviceUpdateSuccess, this::deviceUpdateError);
         var2.run();
      }

   }

   public final void deregisterDevice(BluelabRemoteXbeeDevice dev) {
      if (this.settingFileModel.isLoggedIn() && dev.allControlTypeManagerIdsNonZero()) {
         logger.info("Deregistering device '{}'...", dev.getKeyCode());
            bluelab.connect.Web.DeleteDeviceToken var2;
         (var2 = new bluelab.connect.Web.DeleteDeviceToken(this.settingFileModel.entityId, dev, this.settingFileModel)).setCallbacks(this::deviceDeregistrationSuccess, this::deviceDeregistrationError);
         var2.run();
      }

   }

   private void deviceRegistrationSuccess(int var1, Object var2) {
      logger.info("Device registration success");
      this.deviceList.forEach((dev) -> {
         dev.saveToFile();
      });
   }

   private void deviceRegistrationError(int code, String msg) {
      msg = String.format("Device registration error (%d: %s) at %s.", code, msg, LocalDateTime.now().format(dateTimeFormatter));
      logger.error(msg);
      final String msg2 = msg; // fix error 'local variables referenced from a lambda expression must be final'
      this.deviceList.forEach((dev) -> {
         dev.deviceRegistrationErrorList(bluelab.connect.Web.BluelabWebRequestResponse.isBadRequest(code), msg2, bluelab.connect.Web.BluelabWebRequestResponse.getErrorsForStatusCode(code));
      });
   }

   private void deviceUpdateSuccess(int var1, Object var2) {
      logger.info("Device update success");
      this.deviceList.forEach((dev) -> {
      });
   }

   private void deviceUpdateError(int code, String msg) {
      msg = String.format("Device update error (%d: %s) at %s.", code, msg, LocalDateTime.now().format(dateTimeFormatter));
      logger.error(msg);
      final String msg2 = msg; // fix error 'local variables referenced from a lambda expression must be final'
      this.deviceList.forEach((dev) -> {
         dev.deviceUpdateErrorList(bluelab.connect.Web.BluelabWebRequestResponse.isBadRequest(code), msg2, bluelab.connect.Web.BluelabWebRequestResponse.getErrorsForStatusCode(code));
      });
   }

   private void deviceDeregistrationSuccess(int var1, Object var2) {
      logger.info("Device deregistration success");
   }

   private void deviceDeregistrationError(int code, String msg) {
      msg = String.format("Device deregistration error (%d: %s) at %s.", code, msg, LocalDateTime.now().format(dateTimeFormatter));
      logger.error(msg);
      final String msg2 = msg; // fix error 'local variables referenced from a lambda expression must be final'
      this.deviceList.forEach((dev) -> {
         dev.deviceDeregistrationErrorList(bluelab.connect.Web.BluelabWebRequestResponse.isBadRequest(code), msg2, bluelab.connect.Web.BluelabWebRequestResponse.getErrorsForStatusCode(code));
      });
   }
}