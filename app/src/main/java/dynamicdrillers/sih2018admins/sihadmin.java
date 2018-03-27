package dynamicdrillers.sih2018admins;

import android.app.Application;

import com.onesignal.OneSignal;

/**
 * Created by Happy-Singh on 3/27/2018.
 */

public class sihadmin extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // OneSignal Initialization
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();
    }
}
