package pro.likada.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by abuca on 04.03.17.
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="TRUCKS")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Truck {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "BRAND")
    private String brand;

    @Column(name = "MODEL")
    private String model;

    @Column(name = "GOV_NUMBER")
    private String govNumber;

    @Column(name = "DESCRIPTION")
    private String description;

    @OneToMany(mappedBy = "truck")
    private Set<Vehicle> vehicles;

    @OneToMany(mappedBy = "truck")
    private Set<Order> ordersOfTruck;

    @Override
    public String toString() {
        return "TRUCK[" +
                "id=" + id +
                ", TITLE='" + title +
                ", BRAND='" + brand +
                ", MODEL='" + model +
                ", GOV_NUMBER='" + govNumber +
                ", DESCRIPTION='" + description +
                ']';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (this == o) return true;
        if (!(o instanceof Truck)) return false;

        Truck truck = (Truck) o;

        if (id != null ? !id.equals(truck.id) : truck.id != null) return false;
        if (title != null ? !title.equals(truck.title) : truck.title != null) return false;
        if (brand != null ? !brand.equals(truck.brand) : truck.brand != null) return false;
        if (model != null ? !model.equals(truck.model) : truck.model != null) return false;
        if (govNumber != null ? !govNumber.equals(truck.govNumber) : truck.govNumber != null) return false;
        return description != null ? description.equals(truck.description) : truck.description == null;

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (brand != null ? brand.hashCode() : 0);
        result = 31 * result + (model != null ? model.hashCode() : 0);
        result = 31 * result + (govNumber != null ? govNumber.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
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

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getGovNumber() {
        return govNumber;
    }

    public void setGovNumber(String govNumber) {
        this.govNumber = govNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(Set<Vehicle> vehicleList) {
        this.vehicles = vehicleList;
    }

    public Set<Order> getOrdersOfTruck() {
        return ordersOfTruck;
    }

    public void setOrdersOfTruck(Set<Order> ordersOfTruck) {
        this.ordersOfTruck = ordersOfTruck;
    }
}
