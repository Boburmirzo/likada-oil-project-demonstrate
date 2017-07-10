package pro.likada.rest.wrapper.autograph;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by abuca on 24.03.17.
 */
public class RPointWrapper {
    @JsonProperty("Lat")
    private Double latitude;
    @JsonProperty("Lng")
    private Double longitude;

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
