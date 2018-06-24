package adichauhan.com.entities;

/**
 * Created by adityachauhan on 23/06/18.
 *
 */

public class UserLocation {

    private Double lat;
    private Double lng;
    private String name;
    private String address;
    private Float accuracy;
    private String provider;
    private Long timestamp;
    private Float speed;
    private Float direction;
    private Integer distance;
    private Boolean gpsEnabled;
    private String type;
    private Boolean valid;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserLocation userLocation = (UserLocation) o;

        if (lat != null ? !lat.equals(userLocation.lat) : userLocation.lat != null) return false;
        if (lng != null ? !lng.equals(userLocation.lng) : userLocation.lng != null) return false;
        if (name != null ? !name.equals(userLocation.name) : userLocation.name != null) return false;
        if (address != null ? !address.equals(userLocation.address) : userLocation.address != null)
            return false;
        if (accuracy != null ? !accuracy.equals(userLocation.accuracy) : userLocation.accuracy != null)
            return false;
        if (provider != null ? !provider.equals(userLocation.provider) : userLocation.provider != null)
            return false;
        if (timestamp != null ? !timestamp.equals(userLocation.timestamp) : userLocation.timestamp != null)
            return false;
        if (speed != null ? !speed.equals(userLocation.speed) : userLocation.speed != null) return false;
        if (direction != null ? !direction.equals(userLocation.direction) : userLocation.direction != null)
            return false;
        if (distance != null ? !distance.equals(userLocation.distance) : userLocation.distance != null)
            return false;
        if (gpsEnabled != null ? !gpsEnabled.equals(userLocation.gpsEnabled) : userLocation.gpsEnabled != null)
            return false;
        if (type != null ? !type.equals(userLocation.type) : userLocation.type != null) return false;
        return valid != null ? valid.equals(userLocation.valid) : userLocation.valid == null;
    }

    @Override
    public int hashCode() {
        int result = lat != null ? lat.hashCode() : 0;
        result = 31 * result + (lng != null ? lng.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (accuracy != null ? accuracy.hashCode() : 0);
        result = 31 * result + (provider != null ? provider.hashCode() : 0);
        result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
        result = 31 * result + (speed != null ? speed.hashCode() : 0);
        result = 31 * result + (direction != null ? direction.hashCode() : 0);
        result = 31 * result + (distance != null ? distance.hashCode() : 0);
        result = 31 * result + (gpsEnabled != null ? gpsEnabled.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (valid != null ? valid.hashCode() : 0);
        return result;
    }
}
