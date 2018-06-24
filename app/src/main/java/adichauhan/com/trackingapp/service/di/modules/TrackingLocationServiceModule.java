package adichauhan.com.trackingapp.service.di.modules;

import android.app.Service;
import android.location.Geocoder;

import com.google.android.gms.location.FusedLocationProviderClient;

import adichauhan.com.db.TrackingDatabase;
import adichauhan.com.trackingapp.app.service.ApiService;
import adichauhan.com.trackingapp.service.di.annotations.TrackLocationServiceScope;
import adichauhan.com.trackingapp.service.model.TrackingLocationServiceModel;
import adichauhan.com.trackingapp.service.presenter.TrackLocationServicePresenter;
import dagger.Module;
import dagger.Provides;

/**
 * Created by adityachauhan on 24/06/18.
 *
 */

@Module
public class TrackingLocationServiceModule {

    private final Service service;

    public TrackingLocationServiceModule(Service service) {
        this.service = service;
    }

    @Provides
    @TrackLocationServiceScope
    TrackLocationServicePresenter trackLocationServicePresenter(TrackingLocationServiceModel trackingLocationServiceModel)  {
        return new TrackLocationServicePresenter(service,trackingLocationServiceModel);
    }

    @Provides
    @TrackLocationServiceScope
    TrackingLocationServiceModel trackingLocationServiceModule(ApiService apiService,
                                                               FusedLocationProviderClient fusedLocationProviderClient,
                                                               TrackingDatabase trackingDatabase,
                                                               Geocoder geocoder) {
        return new TrackingLocationServiceModel(service,apiService,fusedLocationProviderClient,
                trackingDatabase,geocoder);
    }
}
