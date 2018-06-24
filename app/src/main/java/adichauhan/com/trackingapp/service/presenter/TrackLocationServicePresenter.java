package adichauhan.com.trackingapp.service.presenter;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Address;
import android.location.Location;
import android.location.LocationManager;
import android.os.BatteryManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import org.joda.time.DateTime;

import java.util.List;

import adichauhan.com.db.entities.TrackingLocationEntity;
import adichauhan.com.entities.BatteryStatus;
import adichauhan.com.entities.Optional;
import adichauhan.com.entities.UserLocation;
import adichauhan.com.entities.api.request.UpdateUserLocationRequest;
import adichauhan.com.trackingapp.service.TrackLocationService;
import adichauhan.com.trackingapp.service.model.TrackingLocationServiceModel;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by adityachauhan on 24/06/18.
 *
 */

public class TrackLocationServicePresenter {

    private final Service service;
    private final TrackingLocationServiceModel model;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final LocationManager locationManager;
    private BatteryStatus currentBatteryStatus;
    private Disposable uploadLocationDataDisposable;
    private ObservableEmitter<Intent> batteryStatusObservableEmmitter;

    private static final String ACTION_LOCATION_UPDATED
            = TrackLocationService.class.getName() + "LocationBroadcast";

    public TrackLocationServicePresenter(Service service,
                                         TrackingLocationServiceModel model) {
        this.model = model;
        this.service = service;
        locationManager = (LocationManager) service.getSystemService(Context.LOCATION_SERVICE);
        currentBatteryStatus = new BatteryStatus();
    }

    public void startLocationUpdates() {
        compositeDisposable.add(getUserLocationUpdates());
        compositeDisposable.add(getBatteryStatusUpdates());
    }

    private Disposable getBatteryStatusUpdates() {
        return Observable
                .create(new ObservableOnSubscribe<Intent>() {
            @Override
            public void subscribe(ObservableEmitter<Intent> emitter) {
                batteryStatusObservableEmmitter = emitter;
                service.registerReceiver(batteryStatusBroadcastReciever,
                        new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
            }
        }).subscribe(new Consumer<Intent>() {
            @Override
            public void accept(Intent intent) {
                int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
                int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
                int chargePlug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
                boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                        status == BatteryManager.BATTERY_STATUS_FULL;
                String chargingStatus = "UNAVAILABLE";
                if(isCharging) {
                    switch (chargePlug) {
                        case BatteryManager.BATTERY_PLUGGED_USB:
                            chargingStatus = "PLUGGED_USB";
                            break;
                        case BatteryManager.BATTERY_PLUGGED_AC:
                            chargingStatus = "PLUGGED_AC";
                            break;
                        default:
                            chargingStatus = "CHARGING";
                    }
                }
                float charge = -1;
                if(level != -1 && scale != -1)
                    charge = level / (float)scale;
                BatteryStatus batteryStatus = new BatteryStatus();
                batteryStatus.setCharge(charge);
                batteryStatus.setChargingStatus(chargingStatus);
                batteryStatus.setTimestamp(DateTime.now().getMillis());
                currentBatteryStatus = batteryStatus;
            }
        });
    }

    private Disposable getUserLocationUpdates() {
        return model.getUserLocationUpdates()
                .subscribe(new Consumer<Optional<Location>>() {
                    @Override
                    public void accept(Optional<Location> locationOptional) {
                        if(!locationOptional.isPresent())
                            return;
                        final Location location = locationOptional.get();
                        Log.d("adasdasd",""+locationOptional.get().getProvider());
                        updateUI();
                        model.getAddressByLatLng(location.getLatitude(),location.getLongitude())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new DisposableObserver<List<Address>>() {
                                    @Override
                                    public void onNext(List<Address> addresses) {
                                        Log.d("adasdasdasda",""+addresses.size());
                                        TrackingLocationEntity trackingLocationEntity = new TrackingLocationEntity();
                                        if(addresses.size()>0) {
                                            trackingLocationEntity.setName(addresses.get(0).getFeatureName());
                                            trackingLocationEntity.setAddress(addresses.get(0)
                                                    .getAddressLine(1));
                                        }
                                        trackingLocationEntity.setLat(location.getLatitude());
                                        trackingLocationEntity.setLng(location.getLongitude());
                                        trackingLocationEntity.setAccuracy(location.getAccuracy());
                                        trackingLocationEntity.setSpeed(location.getSpeed());
                                        trackingLocationEntity.setDirection(location.getBearing());
                                        trackingLocationEntity.setProvider(location.getProvider());
                                        trackingLocationEntity.setGpsEnabled(locationManager != null && locationManager
                                                .isProviderEnabled(LocationManager.GPS_PROVIDER));
                                        trackingLocationEntity.setIsSynced(0);
                                        trackingLocationEntity.setTimestamp(DateTime.now().getMillis());
                                        trackingLocationEntity.setType("PLACE");

                                        //trackingLocationEntity.setDistance();
                                        trackingLocationEntity.setChargingStatus(currentBatteryStatus.getChargingStatus());
                                        trackingLocationEntity.setCharge(currentBatteryStatus.getCharge());
                                        trackingLocationEntity.setBatteryStatusTimestamp(currentBatteryStatus.getTimestamp());
                                        model.insertTrackingLocationEntity(trackingLocationEntity)
                                                .subscribeOn(Schedulers.io())
                                                .observeOn(AndroidSchedulers.mainThread()).subscribe();
                                        uploadLocationData();
                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                    }

                                    @Override
                                    public void onComplete() {
                                        dispose();
                                    }
                                });
                    }
                });
    }

    public void onDestroy() {
        compositeDisposable.clear();
        service.unregisterReceiver(batteryStatusBroadcastReciever);
    }

    private void updateUI() {
        Intent intent = new Intent(ACTION_LOCATION_UPDATED);
        LocalBroadcastManager.getInstance(service).sendBroadcast(intent);
    }

    private void uploadLocationData() {
        if(uploadLocationDataDisposable != null
                && !uploadLocationDataDisposable.isDisposed())
            uploadLocationDataDisposable.dispose();
        uploadLocationDataDisposable = model.getUnSyncedEntities()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<TrackingLocationEntity>>() {
                    @Override
                    public void accept(List<TrackingLocationEntity> trackingLocationEntities) {
                        if(trackingLocationEntities != null
                                && !trackingLocationEntities.isEmpty()) {
                            for(final TrackingLocationEntity trackingLocationEntity : trackingLocationEntities) {
                                model.updateUserLocation(getUpdateUserLocationRequest(trackingLocationEntity))
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new DisposableObserver<Response<Void>>() {
                                            @Override
                                            public void onNext(Response<Void> response) {
                                                if (response.isSuccessful()) {
                                                    trackingLocationEntity.setIsSynced(1);
                                                    model.insertTrackingLocationEntity(trackingLocationEntity)
                                                            .subscribeOn(Schedulers.io())
                                                            .subscribe();
                                                    updateUI();
                                                }
                                            }

                                            @Override
                                            public void onError(Throwable ignored) {}

                                            @Override
                                            public void onComplete() {
                                                dispose();
                                            }
                                        });
                            }
                        }
                    }
                });
    }

    private UpdateUserLocationRequest getUpdateUserLocationRequest(TrackingLocationEntity trackingLocationEntity) {
        UpdateUserLocationRequest updateUserLocationRequest = new UpdateUserLocationRequest();
        UserLocation userLocation = new UserLocation();
        userLocation.setAccuracy(trackingLocationEntity.getAccuracy());
        userLocation.setAddress(trackingLocationEntity.getAddress());
        userLocation.setDirection(trackingLocationEntity.getDirection());
        userLocation.setGpsEnabled(trackingLocationEntity.getGpsEnabled());
        userLocation.setLat(trackingLocationEntity.getLat());
        userLocation.setLng(trackingLocationEntity.getLng());
        userLocation.setName(trackingLocationEntity.getName());
        userLocation.setProvider(trackingLocationEntity.getProvider());
        userLocation.setSpeed(trackingLocationEntity.getSpeed());
        userLocation.setTimestamp(trackingLocationEntity.getTimestamp());
        userLocation.setType(trackingLocationEntity.getType());
        BatteryStatus batteryStatus = new BatteryStatus();
        batteryStatus.setTimestamp(trackingLocationEntity.getBatteryStatusTimestamp());
        batteryStatus.setCharge(trackingLocationEntity.getCharge());
        batteryStatus.setChargingStatus(trackingLocationEntity.getChargingStatus());
        updateUserLocationRequest.setBatteryStatus(batteryStatus);
        updateUserLocationRequest.setLocation(userLocation);
        return updateUserLocationRequest;
    }

    private final BroadcastReceiver batteryStatusBroadcastReciever = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if(batteryStatusObservableEmmitter == null || batteryStatusObservableEmmitter.isDisposed()) {
                service.unregisterReceiver(this);
                return;
            }
            batteryStatusObservableEmmitter.onNext(intent);
        }
    };
}
