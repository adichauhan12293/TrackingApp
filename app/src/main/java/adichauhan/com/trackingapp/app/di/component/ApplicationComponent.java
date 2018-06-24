package adichauhan.com.trackingapp.app.di.component;

import android.location.Geocoder;

import com.google.android.gms.location.FusedLocationProviderClient;

import adichauhan.com.db.TrackingDatabase;
import adichauhan.com.trackingapp.app.di.annotations.ApplicationScope;
import adichauhan.com.trackingapp.app.di.modules.DatabaseModule;
import adichauhan.com.trackingapp.app.di.modules.LocationModule;
import adichauhan.com.trackingapp.app.di.modules.NetworkModule;
import adichauhan.com.trackingapp.app.service.ApiService;
import dagger.Component;

/**
 * Created by adityachauhan on 23/06/18.
 *
 */

@ApplicationScope
@Component(modules = {NetworkModule.class, LocationModule.class, DatabaseModule.class})
public interface ApplicationComponent {
    ApiService apiService();
    FusedLocationProviderClient fusedLocationProviderClient();
    TrackingDatabase trackingDatabase();
    Geocoder geocoder();
}
