package adichauhan.com.db.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by adityachauhan on 24/06/18.
 *
 */

@Entity(tableName = "tracking_location")
public class TrackingLocationEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private Long id;

    @ColumnInfo(name = "lat")
    private Double lat;

    @ColumnInfo(name = "lng")
    private Double lng;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "address")
    private String address;

    @ColumnInfo(name = "accuracy")
    private Float accuracy;

    @ColumnInfo(name = "provider")
    private String provider;

    @ColumnInfo(name = "timestamp")
    private Long timestamp;

    @ColumnInfo(name = "speed")
    private Float speed;

    @ColumnInfo(name = "direction")
    private Float direction;

    @ColumnInfo(name = "distance")
    private Integer distance;

    @ColumnInfo(name = "gps_enabled")
    private Boolean gpsEnabled;

    @ColumnInfo(name = "type")
    private String type;

    @ColumnInfo(name = "valid")
    private Boolean valid;

    @ColumnInfo(name = "charge")
    private Float charge;

    @ColumnInfo(name = "expected_life")
    private Integer expectedLife;

    @ColumnInfo(name = "charging_status")
    private String chargingStatus;

    @ColumnInfo(name = "is_synced")
    private Integer isSynced;

    @ColumnInfo(name = "battery_status_timestamp")
    private Long batteryStatusTimestamp;

    @NonNull
    public Long getId() {
        return id;
    }

    public void setId(@NonNull Long id) {
        this.id = id;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Float getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(Float accuracy) {
        this.accuracy = accuracy;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Float getSpeed() {
        return speed;
    }

    public void setSpeed(Float speed) {
        this.speed = speed;
    }

    public Float getDirection() {
        return direction;
    }

    public void setDirection(Float direction) {
        this.direction = direction;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public Boolean getGpsEnabled() {
        return gpsEnabled;
    }

    public void setGpsEnabled(Boolean gpsEnabled) {
        this.gpsEnabled = gpsEnabled;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public Float getCharge() {
        return charge;
    }

    public void setCharge(Float charge) {
        this.charge = charge;
    }

    public Integer getExpectedLife() {
        return expectedLife;
    }

    public void setExpectedLife(Integer expectedLife) {
        this.expectedLife = expectedLife;
    }

    public String getChargingStatus() {
        return chargingStatus;
    }

    public void setChargingStatus(String chargingStatus) {
        this.chargingStatus = chargingStatus;
    }

    public Integer getIsSynced() {
        return isSynced;
    }

    public void setIsSynced(Integer isSynced) {
        this.isSynced = isSynced;
    }

    public Long getBatteryStatusTimestamp() {
        return batteryStatusTimestamp;
    }

    public void setBatteryStatusTimestamp(Long batteryStatusTimestamp) {
        this.batteryStatusTimestamp = batteryStatusTimestamp;
    }
}