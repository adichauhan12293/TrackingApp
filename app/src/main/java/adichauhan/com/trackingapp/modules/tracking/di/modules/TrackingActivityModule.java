package adichauhan.com.trackingapp.modules.tracking.di.modules;

import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import adichauhan.com.db.TrackingDatabase;
import adichauhan.com.trackingapp.modules.tracking.di.annotations.TrackingActivityScope;
import adichauhan.com.trackingapp.modules.tracking.model.TrackingActivityModel;
import adichauhan.com.trackingapp.modules.tracking.presenter.TrackingActivityPresenter;
import adichauhan.com.trackingapp.modules.tracking.view.TrackingActivityView;
import adichauhan.com.trackingapp.modules.tracking.view.TrackingInfoRecyclerAdapter;
import dagger.Module;
import dagger.Provides;

/**
 * Created by adityachauhan on 23/06/18.
 *
 */

@Module
public class TrackingActivityModule {

    private final AppCompatActivity activity;

    @Inject
    public TrackingActivityModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Provides
    @TrackingActivityScope
    TrackingActivityView trackingView(TrackingInfoRecyclerAdapter adapter) {
        return new TrackingActivityView(activity,adapter);
    }

    @Provides
    @TrackingActivityScope
    TrackingActivityModel trackingModel(TrackingDatabase trackingDatabase) {
        return new TrackingActivityModel(trackingDatabase);
    }

    @Provides
    @TrackingActivityScope
    TrackingActivityPresenter trackingPresenter(TrackingActivityView view,
                                                TrackingActivityModel model) {
        return new TrackingActivityPresenter(activity,view,model);
    }

    @Provides
    @TrackingActivityScope
    TrackingInfoRecyclerAdapter trackingInfoRecyclerAdapter() {
        return new TrackingInfoRecyclerAdapter();
    }
}
