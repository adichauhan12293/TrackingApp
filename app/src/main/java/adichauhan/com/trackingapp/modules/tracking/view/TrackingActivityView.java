package adichauhan.com.trackingapp.modules.tracking.view;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.LinearLayout;

import com.jakewharton.rxbinding2.view.RxView;

import java.util.List;

import javax.inject.Inject;

import adichauhan.com.db.entities.TrackingLocationEntity;
import adichauhan.com.trackingapp.R;
import io.reactivex.Observable;

/**
 * Created by adityachauhan on 23/06/18.
 *
 */

@SuppressLint("ViewConstructor")
public class TrackingActivityView extends LinearLayout {

    private final Button btnStartTracking;
    private final Button btnStopTracking;
    private final RecyclerView rvTrackingInfo;

    final TrackingInfoRecyclerAdapter adapter;

    @Inject
    public TrackingActivityView(AppCompatActivity appCompatActivity,TrackingInfoRecyclerAdapter adapter) {
        super(appCompatActivity);
        inflate(getContext(), R.layout.activity_tracking,this);
        btnStartTracking = findViewById(R.id.btn_start_tracking);
        btnStopTracking = findViewById(R.id.btn_stop_tracking);
        rvTrackingInfo = findViewById(R.id.rv_tracking_info);
        this.adapter = adapter;
        rvTrackingInfo.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL,true));
        rvTrackingInfo.setAdapter(adapter);
    }

    public Observable<Object> observeStartTrackingClick() {
        return RxView.clicks(btnStartTracking);
    }

    public Observable<Object> observeStopTrackingClick() {
        return RxView.clicks(btnStopTracking);
    }

    public void updateData(List<TrackingLocationEntity> trackingLocationEntities) {
        adapter.updateData(trackingLocationEntities);
        if(adapter.getItemCount() != 0) {
            rvTrackingInfo.smoothScrollToPosition(adapter.getItemCount() - 1);
        }
    }
}
