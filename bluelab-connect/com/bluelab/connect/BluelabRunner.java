package bluelab.connect;

import bluelab.connect.d.WeirdEncoder;
import bluelab.connect.ui.BluelabDialog;
import java.awt.Dialog;
import javax.swing.UnsupportedLookAndFeelException;

final class BluelabRunner implements Runnable {
    public final void run() {
        try {
            Connect.g();
        } catch (InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException | ClassNotFoundException var2) {
            WeirdEncoder.ReportException((Throwable) var2);
        }

        bluelab.connect.GoogleAnalytics.GA_tracker_Sender.GetInstance().setTitleClassVersion(Connect.GetTitle(), "bluelab.connect", Connect.GetVersion());
        try {
            Connect.LoadSettings();
        } catch (Throwable ex) {
            // @TODO: do something
            bluelab.connect.GoogleAnalytics.GA_tracker_Sender.GetInstance().reportException(ex.toString(), true);
            System.exit(-1);
        }
        if (Connect.GetSettings().showWelcome() && !Connect.GetSettings().isLoggedIn()) {
            (new BluelabDialog((Dialog) null, Connect.GetSettings())).setVisible(true);
        }

        Connect.GetFrame(new Connect()).setVisible(true);
    }
}
