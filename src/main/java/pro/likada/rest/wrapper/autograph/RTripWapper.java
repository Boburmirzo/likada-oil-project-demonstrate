package pro.likada.rest.wrapper.autograph;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import pro.likada.rest.util.autograph.AutographJsonDateDeserializer;

import java.util.*;

/**
 * Created by abuca on 27.03.17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RTripWapper {
    @JsonProperty("Index")
    private Integer index;
    @JsonProperty("SD")
    private Date startDate;
    @JsonProperty("ED")
    private Date endDate;
    @JsonProperty("PointStart")
    private RPointWrapper startPoint;
    @JsonProperty("PointEnd")
    private RPointWrapper endPoint;
    @JsonProperty("Stages")
    private RTripStageWrapper[] stages;
    @JsonProperty("Total")
    private Map<String,String> driveParams;

    public List<RTripStageWrapper> getMotionStages(){
        List<RTripStageWrapper> motionStages = new ArrayList<>();
        for(RTripStageWrapper stage : stages){
            if(stage.getName().equals(ResourceBundle.getBundle("autograph").getString("motion_stage_name"))){
                motionStages.add(stage);
            }
        }
        return motionStages;
    }

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

    public RPointWrapper getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(RPointWrapper startPoint) {
        this.startPoint = startPoint;
    }

    public RPointWrapper getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(RPointWrapper endPoint) {
        this.endPoint = endPoint;
    }

    public RTripStageWrapper[] getStages() {
        return stages;
    }

    public void setStages(RTripStageWrapper[] stages) {
        this.stages = stages;
    }

    public Map<String, String> getDriveParams() {
        return driveParams;
    }

    public void setDriveParams(Map<String, String> driveParams) {
        this.driveParams = driveParams;
    }
}
