package pro.likada.rest.server.wrapper.drive;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by bumur on 22.04.2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SleepStateWrapper {
    @JsonProperty("SLEEP_STATE_ID")
    private Long sleepStateId;
    @JsonProperty("SLEEP_STATE_NAME")
    private String sleepStateName;

    public Long getSleepStateId() {
        return sleepStateId;
    }

    public void setSleepStateId(Long sleepStateId) {
        this.sleepStateId = sleepStateId;
    }

    public String getSleepStateName() {
        return sleepStateName;
    }

    public void setSleepStateName(String sleepStateName) {
        this.sleepStateName = sleepStateName;
    }
}
