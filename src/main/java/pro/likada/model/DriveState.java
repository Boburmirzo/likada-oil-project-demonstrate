package pro.likada.model;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by bumur on 25.03.2017.
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="DRIVE_STATES")
public class DriveState implements Serializable, Comparable<DriveState>{

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "FILE_LINK")
    private String fileLink;

    @Column(name = "JSON_DATA")
    private String jsonData;

    @Column(name="MODIFIED_DATE")
    private Date modifiedDate;

    @Column(name = "DISTANCE")
    private Double distance;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "CHECK_PREVIOUS_STATE")
    private boolean checkPreviousState;

    @OneToOne
    @JoinColumn(name = "RESPONSIBLE_USER_ID")
    private User responsible;

    @ManyToOne
    @JoinColumn(name = "DRIVE_STATUS_TYPE_ID")
    private DriveStateType driveStateType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DRIVE_ID")
    private Drive drive;

    @Override
    public String toString() {
        return "DRIVE_STATES[" +
                "id=" + id +
                ", FILE_LINK='" + fileLink +
                ", DATA=" + jsonData +
                ", DATE=" + modifiedDate +
                ", DESCRIPTION=" + description +
                ", DRIVE_STATUS_TYPE_ID=" + driveStateType +
                ']';
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileLink() {
        return fileLink;
    }

    public void setFileLink(String fileLink) {
        this.fileLink = fileLink;
    }

    public String getJsonData() {
        return jsonData;
    }

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCheckPreviousState() {
        return checkPreviousState;
    }

    public void setCheckPreviousState(boolean checkPreviousState) {
        this.checkPreviousState = checkPreviousState;
    }

    public User getResponsible() {
        return responsible;
    }

    public void setResponsible(User responsible) {
        this.responsible = responsible;
    }

    public DriveStateType getDriveStateType() {
        return driveStateType;
    }

    public void setDriveStateType(DriveStateType driveStateType) {
        this.driveStateType = driveStateType;
    }

    public Drive getDrive() {
        return drive;
    }

    public void setDrive(Drive drive) {
        this.drive = drive;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }


    @Override
    public int compareTo(DriveState arg1) {
        if (arg1 != null) {
            if (this.getModifiedDate() != null && arg1.getModifiedDate() != null) {
                int compare = this.getModifiedDate().compareTo(arg1.getModifiedDate());
                switch (compare) {
                    case -1:
                        return 1;
                    case 1:
                        return -1;
                    case 0:
                        return 0;
                }
            } else if (this.getModifiedDate() != null && arg1.getModifiedDate() == null) {
                return -1;
            } else if (this.getModifiedDate() == null && arg1.getModifiedDate() != null) {
                return 1;
            }
            return 0;
        }
        return 0;
    }
}
