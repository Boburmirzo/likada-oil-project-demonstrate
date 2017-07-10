package pro.likada.rest.server.wrapper.drive;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by bumur on 06.04.2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CancelStateWrapper {

    @JsonProperty("CANCEL_STATE_ID")
    private Long cancelStateId;
    @JsonProperty("CANCEL_STATE_NAME")
    private String cancelStateName;

    public Long getCancelStateId() {
        return cancelStateId;
    }

    public void setCancelStateId(Long cancelStateId) {
        this.cancelStateId = cancelStateId;
    }

    public String getCancelStateName() {
        return cancelStateName;
    }

    public void setCancelStateName(String cancelStateName) {
        this.cancelStateName = cancelStateName;
    }
}
