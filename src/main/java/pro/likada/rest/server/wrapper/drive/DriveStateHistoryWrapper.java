package pro.likada.rest.server.wrapper.drive;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by bumur on 03.04.2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DriveStateHistoryWrapper {
    @JsonProperty("ID")
    private Long id;
    @JsonProperty("TYPE_ID")
    private Long typeId;
    @JsonProperty("NAME")
    private String name;
    @JsonProperty("MODIFIED_DATE")
    private String modifiedDate;
    @JsonProperty("DESCRIPTION")
    private String description;
    @JsonProperty("CHECK_CONTINUE")
    private boolean checkContinue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCheckContinue() {
        return checkContinue;
    }

    public void setCheckContinue(boolean checkContinue) {
        this.checkContinue = checkContinue;
    }
}
