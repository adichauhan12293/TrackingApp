package adichauhan.com.trackingapp.app.di.modules;

import android.content.Context;

import adichauhan.com.db.TrackingDatabase;
import adichauhan.com.trackingapp.app.di.annotations.ApplicationScope;
import dagger.Module;
import dagger.Provides;

/**
 * Created by adityachauhan on 24/06/18.
 *
 */

@Module(includes = {ApplicationContextModule.class})
public class DatabaseModule {

    @Provides
    @ApplicationScope
    TrackingDatabase trackingDatabase(Context context) {
        return TrackingDatabase.getInstance(context.getApplicationContext());
    }
}
