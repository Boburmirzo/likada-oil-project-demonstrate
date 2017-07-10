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
@Table(name="TRAILERS")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Trailer {
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

    @Column(name = "CALIBRATION")
    private Double calibration;

    @Column(name = "CAPACITY")
    private Double capacity;

    @Column(name = "BOTTOM_LOADING")
    private Boolean bottomLoading;

    @Column(name = "BOTTOM_LOADING_CALIBRATION")
    private Double bottomLoadingCalibration;

    @Column(name = "DESCRIPTION")
    private String description;

    @OneToMany(mappedBy = "trailer")
    private Set<Vehicle> vehicles;

    @OneToMany(mappedBy = "trailer")
    private Set<Order> ordersOfTrailer;

    @Override
    public String toString() {
        return "TRAILER [" +
                "id=" + id +
                ", TITLE='" + title +
                ", BRAND='" + brand +
                ", MODEL='" + model +
                ", GOV_NUMBER='" + govNumber +
                ", CALIBRATION=" + calibration +
                ", CAPACITY=" + capacity +
                ", BOTTOM_LOADING=" + bottomLoading +
                ", BOTTOM_LOADING_CALIBRATION=" + bottomLoadingCalibration +
                ", DESCRIPTION='" + description +
                ']';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Trailer)) return false;

        Trailer trailer = (Trailer) o;

        if (id != null ? !id.equals(trailer.id) : trailer.id != null) return false;
        if (title != null ? !title.equals(trailer.title) : trailer.title != null) return false;
        if (brand != null ? !brand.equals(trailer.brand) : trailer.brand != null) return false;
        if (model != null ? !model.equals(trailer.model) : trailer.model != null) return false;
        if (govNumber != null ? !govNumber.equals(trailer.govNumber) : trailer.govNumber != null) return false;
        if (calibration != null ? !calibration.equals(trailer.calibration) : trailer.calibration != null) return false;
        if (capacity != null ? !capacity.equals(trailer.capacity) : trailer.capacity != null) return false;
        if (bottomLoading != null ? !bottomLoading.equals(trailer.bottomLoading) : trailer.bottomLoading != null)
            return false;
        if (bottomLoadingCalibration != null ? !bottomLoadingCalibration.equals(trailer.bottomLoadingCalibration) : trailer.bottomLoadingCalibration != null)
            return false;
        return description != null ? description.equals(trailer.description) : trailer.description == null;

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (brand != null ? brand.hashCode() : 0);
        result = 31 * result + (model != null ? model.hashCode() : 0);
        result = 31 * result + (govNumber != null ? govNumber.hashCode() : 0);
        result = 31 * result + (calibration != null ? calibration.hashCode() : 0);
        result = 31 * result + (capacity != null ? capacity.hashCode() : 0);
        result = 31 * result + (bottomLoading != null ? bottomLoading.hashCode() : 0);
        result = 31 * result + (bottomLoadingCalibration != null ? bottomLoadingCalibration.hashCode() : 0);
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

    public Double getCalibration() {
        return calibration;
    }

    public void setCalibration(Double calibration) {
        this.calibration = calibration;
    }

    public Double getCapacity() {
        return capacity;
    }

    public void setCapacity(Double capacity) {
        this.capacity = capacity;
    }

    public Boolean getBottomLoading() {
        return bottomLoading;
    }

    public void setBottomLoading(Boolean bottomLoading) {
        this.bottomLoading = bottomLoading;
    }

    public Double getBottomLoadingCalibration() {
        return bottomLoadingCalibration;
    }

    public void setBottomLoadingCalibration(Double bottomLoadingCalibration) {
        this.bottomLoadingCalibration = bottomLoadingCalibration;
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

    public Set<Order> getOrdersOfTrailer() {
        return ordersOfTrailer;
    }

    public void setOrdersOfTrailer(Set<Order> ordersOfTrailer) {
        this.ordersOfTrailer = ordersOfTrailer;
    }
}
