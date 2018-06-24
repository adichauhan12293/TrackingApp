package adichauhan.com.trackingapp.app.di.modules;

import android.content.Context;
import android.location.Geocoder;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import adichauhan.com.trackingapp.app.di.annotations.ApplicationScope;
import dagger.Module;
import dagger.Provides;

/**
 * Created by adityachauhan on 23/06/18.
 *
 */

@Module(includes = {ApplicationContextModule.class})
public class LocationModule {

    @Provides
    @ApplicationScope
    public FusedLocationProviderClient fusedLocationProviderClient(Context context) {
        return LocationServices.getFusedLocationProviderClient(context);
    }

    @Provides
    @ApplicationScope
    Geocoder geocoder(Context context) {
        return new Geocoder(context.getApplicationContext());
    }
}
