package bluelab.connect.d;

import java.util.List;

public interface Interface_DeviceList {
   void saveToFile();

   void deviceRegistrationErrorList(boolean isErrorCode, String message, List<String> errorList);

   void deviceUpdateErrorList(boolean isErrorCode, String message, List<String> errorList);

   void deviceDeregistrationErrorList(boolean isErrorCode, String message, List<String> errorList);
}