package adichauhan.com.trackingapp.utils;

import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;

import adichauhan.com.trackingapp.app.TrackingApplication;

/**
 * Created by adityachauhan on 23/06/18.
 *
 */

public class PermissionUtils {

    public static Boolean checkPermission(String permission) {
        return ContextCompat.checkSelfPermission(TrackingApplication.getInstance().getApplicationContext(),
                permission) == PackageManager.PERMISSION_GRANTED;
    }

    public static void getPermissions(FragmentActivity activity, Integer requestCode, String[] permissions) {
        ActivityCompat.requestPermissions(activity, permissions, requestCode);
    }
}
