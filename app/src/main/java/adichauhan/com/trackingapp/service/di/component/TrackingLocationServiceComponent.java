package adichauhan.com.trackingapp.service.di.component;

import adichauhan.com.trackingapp.app.di.component.ApplicationComponent;
import adichauhan.com.trackingapp.service.TrackLocationService;
import adichauhan.com.trackingapp.service.di.annotations.TrackLocationServiceScope;
import adichauhan.com.trackingapp.service.di.modules.TrackingLocationServiceModule;
import dagger.Component;

/**
 * Created by adityachauhan on 24/06/18.
 *
 */

@TrackLocationServiceScope
@Component(modules = {TrackingLocationServiceModule.class}, dependencies = {ApplicationComponent.class})
public interface TrackingLocationServiceComponent {
    void injectTrackingLocationService(TrackLocationService trackLocationService);
}
