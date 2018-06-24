package adichauhan.com.trackingapp.modules.tracking.model;

import java.util.List;

import javax.inject.Inject;

import adichauhan.com.db.TrackingDatabase;
import adichauhan.com.db.entities.TrackingLocationEntity;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by adityachauhan on 23/06/18.
 *
 */

public class TrackingActivityModel {

    private final TrackingDatabase trackingDatabase;

    @Inject
    public TrackingActivityModel(TrackingDatabase trackingDatabase) {
        this.trackingDatabase = trackingDatabase;
    }

    public Maybe<List<TrackingLocationEntity>> getUserLocationTrackingData() {
        return trackingDatabase.trackingLocationDAO()
                .getTrackingLocationData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Boolean> deleteUserLocationTrackingData() {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) {
                trackingDatabase.trackingLocationDAO()
                        .delete();
                emitter.onNext(true);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
