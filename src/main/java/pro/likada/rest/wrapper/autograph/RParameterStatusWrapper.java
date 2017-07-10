package pro.likada.rest.wrapper.autograph;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by abuca on 27.03.17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RParameterStatusWrapper {
    @JsonProperty("Value")
    private Integer value;
    @JsonProperty("caption")
    private String caption;
    @JsonProperty("ReferenceID")
    private String referenceGUID;
    @JsonProperty("ReferenceIDs")
    private String[] referenceGUIDs;

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getReferenceGUID() {
        return referenceGUID;
    }

    public void setReferenceGUID(String referenceGUID) {
        this.referenceGUID = referenceGUID;
    }

    public String[] getReferenceGUIDs() {
        return referenceGUIDs;
    }

    public void setReferenceGUIDs(String[] referenceGUIDs) {
        this.referenceGUIDs = referenceGUIDs;
    }
}

