package pro.likada.model;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;
import java.util.Set;

/**
 * Created by abuca on 03.04.17.
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="VEHICLE_LOGISTICIAN_STATUSES")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class VehicleLogisticianStatus {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TITLE")
    private String title;

    @ManyToOne
    @JoinColumn(name = "CREATOR_ID", nullable = false)
    private User creator;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED")
    private Date created;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "STATUS_DATE")
    private Date statusDate;

    @Column(name = "DESCRIPTION")
    private String description;

    @OneToMany(mappedBy = "logisticianStatus")
    private Set<VehicleStatus> statuses;

    public VehicleLogisticianStatus() {
    }

    public VehicleLogisticianStatus(VehicleLogisticianStatus other) {
        this.title = other.getTitle();
        this.creator = other.getCreator();
        this.created = other.getCreated();
        this.statusDate = other.getStatusDate();
        this.description = other.getDescription();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
