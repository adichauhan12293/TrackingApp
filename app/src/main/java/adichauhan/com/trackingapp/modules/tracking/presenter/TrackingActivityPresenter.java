package adichauhan.com.trackingapp.modules.tracking.presenter;

import android.Manifest;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import adichauhan.com.db.entities.TrackingLocationEntity;
import adichauhan.com.reactivecomponentproviders.BroadcastManagerObservableProvider;
import adichauhan.com.trackingapp.modules.tracking.model.TrackingActivityModel;
import adichauhan.com.trackingapp.modules.tracking.view.TrackingActivityView;
import adichauhan.com.trackingapp.service.TrackLocationService;
import adichauhan.com.trackingapp.utils.PermissionUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by adityachauhan on 23/06/18.
 *
 */

public class TrackingActivityPresenter {

    private final TrackingActivityModel model;
    private final TrackingActivityView view;
    private final AppCompatActivity activity;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    private static final String ACTION_LOCATION_UPDATED
            = TrackLocationService.class.getName() + "LocationBroadcast";

    public static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    public TrackingActivityPresenter(AppCompatActivity activity,
                                     TrackingActivityView view, TrackingActivityModel model) {
        this.model = model;
        this.view = view;
        this.activity = activity;
    }

    public void onCreate() {
        compositeDisposable.add(observeStartTracking());
        compositeDisposable.add(observeStopTracking());
        compositeDisposable.add(getLocationUpdates());
    }

    private Disposable observeStartTracking() {
        return view.observeStartTrackingClick().subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) {
                model.deleteUserLocationTrackingData().subscribe();
                if(PermissionUtils.checkPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    Intent intent = new Intent(activity,TrackLocationService.class);
                    activity.startService(intent);
                } else {
                    PermissionUtils
                            .getPermissions(activity,LOCATION_PERMISSION_REQUEST_CODE,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION});
                }
            }
        });
    }

    private Disposable observeStopTracking() {
        return view.observeStopTrackingClick().subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) {
                model.deleteUserLocationTrackingData().subscribe();
                activity.stopService(new Intent(activity,TrackLocationService.class));
            }
        });
    }

    public void onLocationPermissionGranted() {
        Intent intent = new Intent(activity,TrackLocationService.class);
        activity.startService(intent);
    }

    public void onDestroy() {
        compositeDisposable.clear();
    }


    private Disposable getLocationUpdates() {
        return BroadcastManagerObservableProvider
                .getBroadcastObservable(activity,ACTION_LOCATION_UPDATED)
                .subscribe(new Consumer<Intent>() {
            @Override
            public void accept(Intent intent) {
                compositeDisposable.add(model.getUserLocationTrackingData()
                        .subscribe(new Consumer<List<TrackingLocationEntity>>() {
                    @Override
                    public void accept(List<TrackingLocationEntity> trackingLocationEntities) {
                        view.updateData(trackingLocationEntities);
                    }
                }));
            }
        });
    }
}
