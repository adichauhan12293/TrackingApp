package adichauhan.com.trackingapp.modules.tracking;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import javax.inject.Inject;

import adichauhan.com.trackingapp.app.TrackingApplication;
import adichauhan.com.trackingapp.modules.tracking.di.component.DaggerTrackingActivityComponent;
import adichauhan.com.trackingapp.modules.tracking.di.component.TrackingActivityComponent;
import adichauhan.com.trackingapp.modules.tracking.di.modules.TrackingActivityModule;
import adichauhan.com.trackingapp.modules.tracking.presenter.TrackingActivityPresenter;
import adichauhan.com.trackingapp.modules.tracking.view.TrackingActivityView;


public class TrackingActivity extends AppCompatActivity {

    @Inject
    TrackingActivityView view;

    @Inject
    TrackingActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TrackingActivityComponent component = DaggerTrackingActivityComponent.builder()
                .trackingActivityModule(new TrackingActivityModule(this))
                .applicationComponent(TrackingApplication.getInstance()
                        .getApplicationComponent()).build();
        component.injectTrackingActivity(this);
        setContentView(view);
        presenter.onCreate();
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case TrackingActivityPresenter.LOCATION_PERMISSION_REQUEST_CODE:
                if(grantResults.length > 0
                        &&  grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    presenter.onLocationPermissionGranted();
                } else {
                    Toast.makeText(this,"UserLocation permission denied",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
