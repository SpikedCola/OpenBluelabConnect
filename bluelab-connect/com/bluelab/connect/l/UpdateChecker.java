package bluelab.connect.l;

import bluelab.connect.Connect;
import bluelab.connect.d.WeirdEncoder;
import com.install4j.api.update.ApplicationDisplayMode;
import com.install4j.api.update.UpdateChecker;
import com.install4j.api.update.UpdateDescriptor;
import com.install4j.api.update.UpdateDescriptorEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpdateChecker {
   private static Logger log = LoggerFactory.getLogger(UpdateChecker.class);
   public static final String a = Connect.GetUpdateUrl();
   private UpdateDescriptor updateDescriptor;
   private UpdateDescriptorEntry possibleUpdate;
   private InvokeLaterInterface invokeLaterInterface;

   public final void SetInvokeLaterInterface(InvokeLaterInterface var1) {
      this.invokeLaterInterface = var1;
   }

   public void CheckForUpdates() {
      try {
         log.info("Checking for update...");
         this.updateDescriptor = UpdateChecker.getUpdateDescriptor(a, ApplicationDisplayMode.UNATTENDED);
         this.possibleUpdate = this.updateDescriptor.getPossibleUpdateEntry();
         if (this.possibleUpdate == null) {
            log.info("No update available");
            return;
         }

         log.info("Update available {}...", this.possibleUpdate.getNewVersion());
         if (this.invokeLaterInterface != null) {
            String newVersion = this.possibleUpdate.getNewVersion();
            String comment = this.possibleUpdate.getComment();
            this.possibleUpdate.getFileName();
            this.invokeLaterInterface.InvokeLater(newVersion, comment);
            return;
         }
      } catch (Throwable var2) {
         WeirdEncoder.ReportException(var2);
         log.error("Error: {}", var2.toString());
      }

   }
}