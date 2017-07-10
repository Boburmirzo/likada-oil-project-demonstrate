package pro.likada.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
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
@Table(name="DRIVE_STATUSES")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DriveStatus implements Serializable{

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name="TYPE")
    private String type;

    @Column(name="NAME")
    private String name;

    @OneToMany(mappedBy = "driveStatus")
    private Set<Drive> drives;

    @Override
    public String toString() {
        return "DRIVE_STATUSES[" +
                "id=" + id +
                ", NAME='" + name +
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

    public Set<Drive> getDrives() {
        return drives;
    }

    public void setDrives(Set<Drive> drives) {
        this.drives = drives;
    }
}
