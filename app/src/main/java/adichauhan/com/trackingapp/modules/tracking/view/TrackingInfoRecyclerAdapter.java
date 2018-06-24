package adichauhan.com.trackingapp.modules.tracking.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import adichauhan.com.db.entities.TrackingLocationEntity;
import adichauhan.com.trackingapp.R;

/**
 * Created by adityachauhan on 24/06/18.
 */
public class TrackingInfoRecyclerAdapter extends RecyclerView.Adapter<TrackingInfoRecyclerAdapter.TrackingInfoViewHolder> {

    private List<TrackingLocationEntity> trackingLocationEntities;

    @Inject
    public TrackingInfoRecyclerAdapter() {
        trackingLocationEntities = new ArrayList<>();
    }

    @NonNull
    @Override
    public TrackingInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tracking_info,parent,false);
        return new TrackingInfoViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull TrackingInfoViewHolder holder, int position) {
            holder.populateData(trackingLocationEntities.get(position));
    }


    @Override
    public int getItemCount() {
        return trackingLocationEntities.size();
    }

    public void updateData(List<TrackingLocationEntity> trackingLocationEntities) {
        this.trackingLocationEntities = trackingLocationEntities;
        notifyDataSetChanged();
    }

    static class TrackingInfoViewHolder extends RecyclerView.ViewHolder {

        final TextView tvTrackingInfo;
        private final DateTimeFormatter dateTimeFormat = DateTimeFormat.forPattern("yyyy-mm-dd HH:mm");

        TrackingInfoViewHolder(View itemView) {
            super(itemView);
            tvTrackingInfo = itemView.findViewById(R.id.tv_tracking_info);
        }

        void populateData(TrackingLocationEntity trackingLocationEntity) {
            tvTrackingInfo
                    .setText(String.format("Time :%s\n lat: %s\n lng: %s \nSynced To server %s",
                            dateTimeFormat.print(trackingLocationEntity.getTimestamp()),
                            trackingLocationEntity.getLat(),
                            trackingLocationEntity.getLng(),
                            trackingLocationEntity.getIsSynced()==1?"True":"false"));
        }
    }
}
