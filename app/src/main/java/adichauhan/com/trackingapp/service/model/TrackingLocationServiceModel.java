package adichauhan.com.trackingapp.service.model;

import android.app.Service;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import com.google.android.gms.location.FusedLocationProviderClient;

import java.util.List;

import adichauhan.com.db.TrackingDatabase;
import adichauhan.com.db.entities.TrackingLocationEntity;
import adichauhan.com.entities.Optional;
import adichauhan.com.entities.api.request.UpdateUserLocationRequest;
import adichauhan.com.reactivecomponentproviders.LocationObservableProvider;
import adichauhan.com.trackingapp.app.service.ApiService;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by adityachauhan on 24/06/18.
 *
 */

public class TrackingLocationServiceModel {

    private final ApiService apiService;
    private final FusedLocationProviderClient fusedLocationProviderClient;
    private final Service service;
    private final TrackingDatabase trackingDatabase;
    private final Geocoder geocoder;

    public TrackingLocationServiceModel(Service service, ApiService apiService,
                                        FusedLocationProviderClient fusedLocationProviderClient,
                                        TrackingDatabase trackingDatabase, Geocoder geocoder) {
        this.fusedLocationProviderClient = fusedLocationProviderClient;
        this.apiService = apiService;
        this.service = service;
        this.trackingDatabase = trackingDatabase;
        this.geocoder = geocoder;
    }

    public Observable<Optional<Location>> getUserLocationUpdates() {
        return LocationObservableProvider
                .getUserLocationUpdatesObservable(service,
                        fusedLocationProviderClient)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Response<Void>> updateUserLocation(UpdateUserLocationRequest updateUserLocationRequest) {
        return apiService.updateUserLocation(updateUserLocationRequest)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    public Maybe<List<TrackingLocationEntity>> getUnSyncedEntities() {
        return trackingDatabase.trackingLocationDAO().
                getUnsyncedEntities()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    public Observable<Boolean> insertTrackingLocationEntity(final TrackingLocationEntity trackingLocationEntity) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) {
                trackingDatabase.trackingLocationDAO()
                        .insert(trackingLocationEntity);
                emitter.onNext(true);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<Address>> getAddressByLatLng(Double lat, Double lng) {
        return LocationObservableProvider
                .getLocationByPlaceIDObservable(lat,lng,geocoder)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }
}
