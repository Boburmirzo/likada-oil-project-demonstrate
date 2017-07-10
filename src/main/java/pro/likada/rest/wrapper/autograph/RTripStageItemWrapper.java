package pro.likada.rest.wrapper.autograph;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import pro.likada.rest.util.autograph.AutographJsonDateDeserializer;

import java.util.Date;

/**
 * Created by abuca on 27.03.17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RTripStageItemWrapper {
    @JsonProperty("Index")
    private Integer index;
    @JsonProperty("SD")
    private Date startDate;
    @JsonProperty("ED")
    private Date endDate;
    @JsonProperty("Status")
    private Integer status;
    @JsonProperty("StatusID")
    private String statusGUID;
    @JsonProperty("StatusIDs")
    private String[] statusGUIDs;
    @JsonProperty("Caption")
    private String statusName;
    @JsonProperty("Values")
    private String[] values;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusGUID() {
        return statusGUID;
    }

    public void setStatusGUID(String statusGUID) {
        this.statusGUID = statusGUID;
    }

    public String[] getStatusGUIDs() {
        return statusGUIDs;
    }

    public void setStatusGUIDs(String[] statusGUIDs) {
        this.statusGUIDs = statusGUIDs;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String[] getValues() {
        return values;
    }

    public void setValues(String[] values) {
        this.values = values;
    }
}
