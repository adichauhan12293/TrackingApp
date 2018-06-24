package adichauhan.com.entities.api.request;

import adichauhan.com.entities.BatteryStatus;
import adichauhan.com.entities.UserLocation;


public class UpdateUserLocationRequest {
    private UserLocation location;
    private BatteryStatus batteryStatus;

    public UserLocation getLocation() {
        return location;
    }

    public void setLocation(UserLocation location) {
        this.location = location;
    }

    public BatteryStatus getBatteryStatus() {
        return batteryStatus;
    }

    public void setBatteryStatus(BatteryStatus batteryStatus) {
        this.batteryStatus = batteryStatus;
    }
}
