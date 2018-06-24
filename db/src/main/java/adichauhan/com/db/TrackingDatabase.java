package adichauhan.com.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import adichauhan.com.db.dao.TrackingLocationDAO;
import adichauhan.com.db.entities.TrackingLocationEntity;

/**
 * Created by adityachauhan on 24/06/18.
 *
 */
@Database(entities = {TrackingLocationEntity.class},version = 1, exportSchema = false)
public abstract class TrackingDatabase extends RoomDatabase {

    private static TrackingDatabase INSTANCE;
    private final static Object sLock = new Object();

    public static TrackingDatabase getInstance(Context context) {
        synchronized (sLock) {
            if(INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext()
                        ,TrackingDatabase.class,"tracking-db.db")
                        .build();
            }
            return INSTANCE;
        }
    }

    public abstract TrackingLocationDAO trackingLocationDAO();
}
