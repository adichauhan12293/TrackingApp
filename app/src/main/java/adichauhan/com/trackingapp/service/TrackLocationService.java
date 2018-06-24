package adichauhan.com.trackingapp.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import javax.inject.Inject;

import adichauhan.com.trackingapp.R;
import adichauhan.com.trackingapp.app.TrackingApplication;
import adichauhan.com.trackingapp.modules.tracking.TrackingActivity;
import adichauhan.com.trackingapp.service.di.component.DaggerTrackingLocationServiceComponent;
import adichauhan.com.trackingapp.service.di.component.TrackingLocationServiceComponent;
import adichauhan.com.trackingapp.service.di.modules.TrackingLocationServiceModule;
import adichauhan.com.trackingapp.service.presenter.TrackLocationServicePresenter;


/**
 * Created by adityachauhan on 24/06/18.
 *
 */

public class TrackLocationService extends Service {

    @Inject
    TrackLocationServicePresenter presenter;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent notificationIntent = new Intent(this, TrackingActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel;
            notificationChannel = new NotificationChannel("tracking-channel",
                    "tracking-channel", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setShowBadge(true);
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            if(manager != null)
                manager.createNotificationChannel(notificationChannel);
        }
        Notification notification = new NotificationCompat.Builder(this,"tracking-channel")
                .setContentTitle("Tracking in progress")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent)
                .setOngoing(true)
                .setChannelId("tracking-channel")
                .setContentText("tracking sever is being monitored")
                .build();
        startForeground(1, notification);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        TrackingLocationServiceComponent component = DaggerTrackingLocationServiceComponent
                .builder()
                .applicationComponent(TrackingApplication.getInstance().getApplicationComponent())
                .trackingLocationServiceModule(new TrackingLocationServiceModule(this)).build();
        component.injectTrackingLocationService(this);
        presenter.startLocationUpdates();
    }
}