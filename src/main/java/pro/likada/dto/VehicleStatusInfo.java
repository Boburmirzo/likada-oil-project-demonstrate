package pro.likada.dto;

import pro.likada.model.User;

import java.util.Date;

/**
 * Created by bumur on 18.04.2017.
 */
public class VehicleStatusInfo {

    private String title;
    private Date created;
    private String description;
    private User creator;
    private int typeOfStatus;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public int getTypeOfStatus() {
        return typeOfStatus;
    }

    public void setTypeOfStatus(int typeOfStatus) {
        this.typeOfStatus = typeOfStatus;
    }
}
