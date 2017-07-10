package pro.likada.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by Yusupov on 3/9/2017.
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="TRANSPORTATION_VEHICLE_TYPES")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TransportationVehicleType implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @OneToMany(mappedBy = "transportationVehicleType")
    private Set<Order> ordersOfTransportationVehicleType;

    @Override
    public String toString() {
        return "TRANSPORTATION_VEHICLE_TYPE [id=" + id + ", type=" + type + ", name=" + name + ", description=" + description;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof TransportationVehicleType))
            return false;
        TransportationVehicleType other = (TransportationVehicleType) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
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

    public Set<Order> getOrdersOfTransportationVehicleType() {
        return ordersOfTransportationVehicleType;
    }

    public void setOrdersOfTransportationVehicleType(Set<Order> ordersOfTransportationVehicleType) {
        this.ordersOfTransportationVehicleType = ordersOfTransportationVehicleType;
    }
}
