package pro.likada.rest.wrapper.autograph;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import pro.likada.rest.util.autograph.AutographJsonDateDeserializer;

import java.util.Date;

/**
 * Created by abuca on 24.03.17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ROnlineInfoWrapper {
    @JsonProperty("ID")
    private String guid;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("LastPosition")
    private RPointWrapper lastPosition;
    @JsonProperty("DT")
    private Date date;
    @JsonProperty("State")
    private Integer state;
    @JsonProperty("Speed")
    private Double speed;
    @JsonProperty("Course")
    private Double course;
    @JsonProperty("Address")
    private String address;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RPointWrapper getLastPosition() {
        return lastPosition;
    }

    public void setLastPosition(RPointWrapper lastPosition) {
        this.lastPosition = lastPosition;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public Double getCourse() {
        return course;
    }

    public void setCourse(Double course) {
        this.course = course;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

