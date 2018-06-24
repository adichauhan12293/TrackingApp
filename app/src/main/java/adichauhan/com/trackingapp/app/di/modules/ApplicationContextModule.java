package adichauhan.com.trackingapp.app.di.modules;

import android.content.Context;

import adichauhan.com.trackingapp.app.di.annotations.ApplicationScope;
import dagger.Module;
import dagger.Provides;

/**
 * Created by adityachauhan on 23/06/18.
 *
 */

@Module
public class ApplicationContextModule {

    private final Context context;

    public ApplicationContextModule(Context context) {
        this.context = context;
    }

    @Provides
    @ApplicationScope
    Context context() {
        return context;
    }
}
