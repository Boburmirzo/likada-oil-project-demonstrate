package pro.likada.rest.server.wrapper.drive;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by bumur on 06.04.2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class StopStateWrapper {

    @JsonProperty("STOP_STATE_ID")
    private Long stopeStateId;
    @JsonProperty("STOP_STATE_NAME")
    private String stopStateName;

    public Long getStopeStateId() {
        return stopeStateId;
    }

    public void setStopeStateId(Long stopeStateId) {
        this.stopeStateId = stopeStateId;
    }

    public String getStopStateName() {
        return stopStateName;
    }

    public void setStopStateName(String stopStateName) {
        this.stopStateName = stopStateName;
    }
}
