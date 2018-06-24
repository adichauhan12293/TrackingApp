package adichauhan.com.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import adichauhan.com.db.entities.TrackingLocationEntity;
import io.reactivex.Maybe;

/**
 * Created by adityachauhan on 24/06/18.
 *
 */

@Dao
public interface TrackingLocationDAO {

    @Query("SELECT * FROM tracking_location where is_synced = 0")
    Maybe<List<TrackingLocationEntity>> getUnsyncedEntities();

    @Query("SELECT * FROM tracking_location")
    Maybe<List<TrackingLocationEntity>> getTrackingLocationData();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TrackingLocationEntity trackingLocationEntity);

    @Query("DELETE FROM tracking_location")
    void delete();
}
