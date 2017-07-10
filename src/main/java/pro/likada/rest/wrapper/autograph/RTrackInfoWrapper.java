package pro.likada.rest.wrapper.autograph;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import pro.likada.dto.VehiclePosition;
import pro.likada.model.Vehicle;
import pro.likada.rest.util.autograph.AutographJsonDateDeserializer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by abuca on 27.03.17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RTrackInfoWrapper {
    @JsonProperty("Index")
    private Integer index;
    @JsonProperty("DT")
    private Date[] dates;
    @JsonProperty("Speed")
    private Double[] speeds;
    @JsonProperty("Lat")
    private Double[] latitudes;
    @JsonProperty("Lng")
    private Double[] longitudes;

    public List<VehiclePosition> toVehiclePositions(){
        int len = dates.length;
        List<VehiclePosition> vehiclePositions = new ArrayList<>();
        for(int i = 0; i< len; i++){
            VehiclePosition vehiclePosition = new VehiclePosition();
            vehiclePosition.setDate(dates[i]);
            vehiclePosition.setSpeed(speeds[i]);
            vehiclePosition.setLatitude(latitudes[i]);
            vehiclePosition.setLongitude(longitudes[i]);
            vehiclePositions.add(vehiclePosition);
        }
        return vehiclePositions;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Date[] getDates() {
        return dates;
    }

    public void setDates(Date[] dates) {
        this.dates = dates;
    }

    public Double[] getSpeeds() {
        return speeds;
    }

    public void setSpeeds(Double[] speeds) {
        this.speeds = speeds;
    }

    public Double[] getLatitudes() {
        return latitudes;
    }

    public void setLatitudes(Double[] latitudes) {
        this.latitudes = latitudes;
    }

    public Double[] getLongitudes() {
        return longitudes;
    }

    public void setLongitudes(Double[] longitudes) {
        this.longitudes = longitudes;
    }
}
