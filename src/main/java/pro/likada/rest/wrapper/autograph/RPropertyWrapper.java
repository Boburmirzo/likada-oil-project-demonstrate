package pro.likada.rest.wrapper.autograph;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by abuca on 24.03.17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RPropertyWrapper{
    @JsonProperty("Inherited")
    public Boolean inherited;
    @JsonProperty("Name")
    public String name;
    @JsonProperty("Value")
    public String value;

    public Boolean getInherited() {
        return inherited;
    }

    public void setInherited(Boolean inherited) {
        this.inherited = inherited;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
