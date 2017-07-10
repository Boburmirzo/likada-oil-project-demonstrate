package pro.likada.rest.server.wrapper.drive;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by bumur on 03.04.2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DestinationWrapper {
    @JsonProperty("ID")
    private String id;
    @JsonProperty("POINT_TYPE")
    private String fromWhere;
    @JsonProperty("OBJECT")
    private String object;
    @JsonProperty("ADDRESS")
    private String address;
    @JsonProperty("CONTACT")
    private String contact;
    @JsonProperty("COORDINATES")
    private String coordinates;
    @JsonProperty("DESCRIPTION")
    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFromWhere() {
        return fromWhere;
    }

    public void setFromWhere(String fromWhere) {
        this.fromWhere = fromWhere;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
