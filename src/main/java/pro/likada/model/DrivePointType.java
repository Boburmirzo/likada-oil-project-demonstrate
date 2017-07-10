package pro.likada.model;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by bumur on 25.03.2017.
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="DRIVE_POINT_TYPES")
public class DrivePointType implements Serializable{

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @OneToMany(mappedBy = "driveStateType")
    private Set<DriveState> driveStates;

    @OneToMany(mappedBy = "drivePointType")
    private Set<DrivePoint> drivePoints;

    @Override
    public String toString() {
        return "DRIVE_POINT_TYPES[" +
                "id=" + id +
                ", TYPE='" + type +
                ", NAME=" + name +
                ", DESCRIPTION=" + description +
                ", driveStateType=" + driveStates +
                ", drivePointType=" + drivePoints +
                ']';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<DriveState> getDriveStates() {
        return driveStates;
    }

    public void setDriveStates(Set<DriveState> driveStates) {
        this.driveStates = driveStates;
    }

    public Set<DrivePoint> getDrivePoints() {
        return drivePoints;
    }

    public void setDrivePoints(Set<DrivePoint> drivePoints) {
        this.drivePoints = drivePoints;
    }
}
