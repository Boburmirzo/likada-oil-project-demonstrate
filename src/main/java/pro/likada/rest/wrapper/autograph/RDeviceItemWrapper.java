package pro.likada.rest.wrapper.autograph;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by abuca on 24.03.17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RDeviceItemWrapper extends RGroupItemWrapper{
    @JsonProperty("Serial")
    private Integer serial;
    @JsonProperty("Allowed")
    private Boolean allowed;
    @JsonProperty("Properties")
    private RPropertyWrapper[] properties;
    @JsonProperty("FixedLocation")
    private RPointWrapper fixedLocation;
    @JsonProperty("IsAreaEnabled")
    private Boolean isAreaEnabled;

    public Integer getSerial() {
        return serial;
    }

    public void setSerial(Integer serial) {
        this.serial = serial;
    }

    public Boolean getAllowed() {
        return allowed;
    }

    public void setAllowed(Boolean allowed) {
        this.allowed = allowed;
    }

    public RPropertyWrapper[] getProperties() {
        return properties;
    }

    public void setProperties(RPropertyWrapper[] properties) {
        this.properties = properties;
    }

    public RPointWrapper getFixedLocation() {
        return fixedLocation;
    }

    public void setFixedLocation(RPointWrapper fixedLocation) {
        this.fixedLocation = fixedLocation;
    }

    public Boolean getAreaEnabled() {
        return isAreaEnabled;
    }

    public void setAreaEnabled(Boolean areaEnabled) {
        isAreaEnabled = areaEnabled;
    }
}

