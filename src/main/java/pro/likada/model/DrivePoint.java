package pro.likada.model;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by bumur on 25.03.2017.
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="DRIVE_POINTS")
public class DrivePoint implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "LINK_TO_OBJECT")
    private String linkToObject;

    @Column(name = "TYPE_OF_OBJECT")
    private Integer typeOfObject;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "CONTACT")
    private String contact;

    @Column(name = "COORDINATES")
    private String coordinates;

    @ManyToOne
    @JoinColumn(name = "DRIVE_POINT_TYPE_ID")
    private DrivePointType drivePointType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DRIVE_SOURCE_ID")
    private Drive driveSource;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DRIVE_DESTINATION_ID")
    private Drive driveDestination;

    @Override
    public String toString() {
        return "DRIVE_POINTS[" +
                "id=" + id +
                ", OBJECT='" + linkToObject +
                ", ADDRESS=" + address +
                ", DESCRIPTION=" + description +
                ", CONTACT=" + contact +
                ", COORDINATES=" + coordinates +
                ']';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTypeOfObject() {
        return typeOfObject;
    }

    public void setTypeOfObject(Integer typeOfObject) {
        this.typeOfObject = typeOfObject;
    }

    public String getLinkToObject() {
        return linkToObject;
    }

    public void setLinkToObject(String linkToObject) {
        this.linkToObject = linkToObject;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public DrivePointType getDrivePointType() {
        return drivePointType;
    }

    public void setDrivePointType(DrivePointType drivePointType) {
        this.drivePointType = drivePointType;
    }

    public Drive getDriveSource() {
        return driveSource;
    }

    public void setDriveSource(Drive driveSource) {
        this.driveSource = driveSource;
    }

    public Drive getDriveDestination() {
        return driveDestination;
    }

    public void setDriveDestination(Drive driveDestination) {
        this.driveDestination = driveDestination;
    }

}
