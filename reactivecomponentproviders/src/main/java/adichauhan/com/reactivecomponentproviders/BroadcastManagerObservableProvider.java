package adichauhan.com.reactivecomponentproviders;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;


/**
 * Created by adityachauhan on 24/06/18.
 *
 */

public class BroadcastManagerObservableProvider {

    public static Observable<Intent> getBroadcastObservable(final Context context, final String intentFilter) {
        return Observable.create(new ObservableOnSubscribe<Intent>() {
            @Override
            public void subscribe(final ObservableEmitter<Intent> emitter) {
                LocalBroadcastManager.getInstance(context).registerReceiver(new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        if(emitter == null) {
                            return;
                        }
                        if(emitter.isDisposed()) {
                            LocalBroadcastManager.getInstance(context).unregisterReceiver(this);
                            return;
                        }
                        emitter.onNext(intent);
                    }
                },new IntentFilter(intentFilter));
            }
        });
    }
}
