package adichauhan.com.trackingapp.app;

import android.app.Application;

import adichauhan.com.trackingapp.app.di.component.ApplicationComponent;
import adichauhan.com.trackingapp.app.di.component.DaggerApplicationComponent;
import adichauhan.com.trackingapp.app.di.modules.ApplicationContextModule;

/**
 * Created by adityachauhan on 23/06/18.
 *
 */

public class TrackingApplication extends Application {

    private static TrackingApplication instance;
    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        applicationComponent = DaggerApplicationComponent
                .builder()
                .applicationContextModule(new ApplicationContextModule(this))
                .build();
    }

    public static TrackingApplication getInstance() {
        return instance;
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

}
