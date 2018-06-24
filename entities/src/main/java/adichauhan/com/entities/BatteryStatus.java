package adichauhan.com.entities;

/**
 * Created by adityachauhan on 23/06/18.
 *
 */

public class BatteryStatus {

    private Float charge;
    private Integer expectedLife;
    private String chargingStatus;
    private Long timestamp;

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

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BatteryStatus that = (BatteryStatus) o;

        if (charge != null ? !charge.equals(that.charge) : that.charge != null) return false;
        if (expectedLife != null ? !expectedLife.equals(that.expectedLife) : that.expectedLife != null)
            return false;
        if (chargingStatus != null ? !chargingStatus.equals(that.chargingStatus) : that.chargingStatus != null)
            return false;
        return timestamp != null ? timestamp.equals(that.timestamp) : that.timestamp == null;
    }

    @Override
    public int hashCode() {
        int result = charge != null ? charge.hashCode() : 0;
        result = 31 * result + (expectedLife != null ? expectedLife.hashCode() : 0);
        result = 31 * result + (chargingStatus != null ? chargingStatus.hashCode() : 0);
        result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
        return result;
    }
}