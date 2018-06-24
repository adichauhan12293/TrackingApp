package adichauhan.com.trackingapp.modules.tracking.di.component;

import adichauhan.com.trackingapp.app.di.component.ApplicationComponent;
import adichauhan.com.trackingapp.modules.tracking.TrackingActivity;
import adichauhan.com.trackingapp.modules.tracking.di.annotations.TrackingActivityScope;
import adichauhan.com.trackingapp.modules.tracking.di.modules.TrackingActivityModule;
import dagger.Component;

/**
 * Created by adityachauhan on 23/06/18.
 *
 */

@TrackingActivityScope
@Component(modules = {TrackingActivityModule.class}, dependencies = {ApplicationComponent.class})
public interface TrackingActivityComponent {
    void injectTrackingActivity(TrackingActivity activity);
}
