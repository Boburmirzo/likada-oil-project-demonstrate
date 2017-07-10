package pro.likada.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by abuca on 03.04.17.
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="VEHICLE_MECHANICAL_STATUSES")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class VehicleMechanicalStatus {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "VEHICLE_MECHANICAL_STATUS_MECHANICAL_STATUS_TYPE",
            joinColumns = { @JoinColumn(name = "MECHANICAL_STATUS_ID") },
            inverseJoinColumns = { @JoinColumn(name = "MECHANICAL_STATUS_TYPE_ID") })
    private Set<VehicleMechanicalStatusType> mechanicalStatuses;

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

    @OneToMany(mappedBy = "mechanicalStatus")
    private Set<VehicleStatus> statuses;

    public VehicleMechanicalStatus() {
        this.mechanicalStatuses = new HashSet<>();
    }

    public VehicleMechanicalStatus(VehicleMechanicalStatus other) {
        if(other.getMechanicalStatuses() != null) {
            this.mechanicalStatuses = new HashSet<>(other.getMechanicalStatuses());
        }
        else{
            this.mechanicalStatuses = new HashSet<>();
        }
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

    public Set<VehicleMechanicalStatusType> getMechanicalStatuses() {
        return mechanicalStatuses;
    }

    public String getMehahicalStatusTitle(){
        return this.mechanicalStatuses
                .stream()
                .map(vehicleMechanicalStatusType -> vehicleMechanicalStatusType.getName())
                .reduce((s, s2) -> s+", "+s2)
                .orElse("");
    }

    public void setMechanicalStatuses(Set<VehicleMechanicalStatusType> mechanicalStatuses) {
        this.mechanicalStatuses = mechanicalStatuses;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }

    public Set<VehicleStatus> getStatuses() {
        return statuses;
    }

    public void setStatuses(Set<VehicleStatus> statuses) {
        this.statuses = statuses;
    }
}
