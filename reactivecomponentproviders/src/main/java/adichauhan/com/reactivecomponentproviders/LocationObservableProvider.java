package adichauhan.com.reactivecomponentproviders;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;

import java.util.List;

import adichauhan.com.entities.Optional;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * Created by adityachauhan on 23/06/18.
 *
 */

public class LocationObservableProvider {

    public static Observable<Optional<Location>> getUserLocationUpdatesObservable(final Context context,
                                                                           final FusedLocationProviderClient client) {
        return Observable.create(new ObservableOnSubscribe<Optional<Location>>() {
            @SuppressLint("MissingPermission")
            @Override
            public void subscribe(final ObservableEmitter<Optional<Location>> emitter) {
                final LocationRequest locationRequest = new LocationRequest();
                locationRequest.setSmallestDisplacement(10);
                locationRequest.setInterval(30000);
                locationRequest.setFastestInterval(0);
                locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                client.requestLocationUpdates(locationRequest, new LocationCallback(){
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        super.onLocationResult(locationResult);
                        if(emitter.isDisposed()) {
                            client.removeLocationUpdates(this);
                            return;
                        }
                        if(locationResult != null && locationResult.getLastLocation() != null) {
                            emitter.onNext(Optional.of(locationResult.getLastLocation()));
                        }
                    }
                },context.getMainLooper());
            }
        });
    }

    public static Observable<List<Address>> getLocationByPlaceIDObservable(final Double lat, final Double lng,
                                                                                 final Geocoder geocoder) {
        return Observable.create(new ObservableOnSubscribe<List<Address>>() {
            @Override
            public void subscribe(ObservableEmitter<List<Address>> emitter) throws Exception {
                List<Address> addresses = geocoder.getFromLocation(lat,lng,1);
                emitter.onNext(addresses);
            }
        });
    }
}
