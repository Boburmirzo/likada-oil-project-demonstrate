package pro.likada.model;

import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by abuca on 03.04.17.
 */

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="MECHANICAL_STATUS_TYPES")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class VehicleMechanicalStatusType implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "mechanicalStatuses")
    private Set<VehicleMechanicalStatus> vehicleStatuses;

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

    public Set<VehicleMechanicalStatus> getVehicleStatuses() {
        return vehicleStatuses;
    }

    public void setVehicleStatuses(Set<VehicleMechanicalStatus> vehicleStatuses) {
        this.vehicleStatuses = vehicleStatuses;
    }
}
