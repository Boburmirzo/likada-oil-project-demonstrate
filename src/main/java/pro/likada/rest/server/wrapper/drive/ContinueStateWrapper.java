package pro.likada.rest.server.wrapper.drive;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by bumur on 06.04.2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContinueStateWrapper {

    @JsonProperty("CONTINUE_STATE_ID")
    private Long continueStateId;
    @JsonProperty("CONTINUE_STATE_NAME")
    private String continueStateName;

    public Long getContinueStateId() {
        return continueStateId;
    }

    public void setContinueStateId(Long continueStateId) {
        this.continueStateId = continueStateId;
    }

    public String getContinueStateName() {
        return continueStateName;
    }

    public void setContinueStateName(String continueStateName) {
        this.continueStateName = continueStateName;
    }
}
