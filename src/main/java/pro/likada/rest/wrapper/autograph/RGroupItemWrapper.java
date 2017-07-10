package pro.likada.rest.wrapper.autograph;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by abuca on 24.03.17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RGroupItemWrapper{
    @JsonProperty("ID")
    private String GUID;
    @JsonProperty("ParentID")
    private String ParentGUID;
    @JsonProperty("Name")
    private String name;

    public String getGUID() {
        return GUID;
    }

    public void setGUID(String GUID) {
        this.GUID = GUID;
    }

    public String getParentGUID() {
        return ParentGUID;
    }

    public void setParentGUID(String parentGUID) {
        ParentGUID = parentGUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
