package pro.likada.rest.wrapper.autograph;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * Created by abuca on 27.03.17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RTripsWrapper {
    @JsonProperty("ID")
    private String guid;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("Trips")
    private RTripWapper[] trips;
    @JsonProperty("Total")
    private Map<String,String> finalValues;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RTripWapper[] getTrips() {
        return trips;
    }

    public void setTrips(RTripWapper[] trips) {
        this.trips = trips;
    }

    public Map<String, String> getFinalValues() {
        return finalValues;
    }

    public void setFinalValues(Map<String, String> finalValues) {
        this.finalValues = finalValues;
    }
}
