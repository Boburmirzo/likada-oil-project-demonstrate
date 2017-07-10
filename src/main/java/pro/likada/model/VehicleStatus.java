package pro.likada.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by abuca on 04.03.17.
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="VEHICLE_STATUSES")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class VehicleStatus {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "logistician_status_id")
    private VehicleLogisticianStatus logisticianStatus;

    @ManyToOne
    @JoinColumn(name = "mechanical_status_id")
    private VehicleMechanicalStatus mechanicalStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VEHICLE_ID", nullable = false)
    private Vehicle vehicle;


    public VehicleStatus() {
    }

    public VehicleStatus(VehicleStatus other) {
        this.logisticianStatus = other.getLogisticianStatus();
        this.mechanicalStatus = other.getMechanicalStatus();
        this.vehicle = other.getVehicle();
    }

    @Override
    public String toString() {
        return "VehicleStatus[" +
                "id=" + id +
                ", logisticianStatus=" + logisticianStatus +
                ", mechanicalStatus=" + mechanicalStatus +
                ", vehicle=" + vehicle +
                ']';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VehicleStatus)) return false;

        VehicleStatus that = (VehicleStatus) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (logisticianStatus != null ? !logisticianStatus.equals(that.logisticianStatus) : that.logisticianStatus != null)
            return false;
        if (mechanicalStatus != null ? !mechanicalStatus.equals(that.mechanicalStatus) : that.mechanicalStatus != null)
            return false;
        return vehicle != null ? vehicle.equals(that.vehicle) : that.vehicle == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (logisticianStatus != null ? logisticianStatus.hashCode() : 0);
        result = 31 * result + (mechanicalStatus != null ? mechanicalStatus.hashCode() : 0);
        result = 31 * result + (vehicle != null ? vehicle.hashCode() : 0);
        return result;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public VehicleLogisticianStatus getLogisticianStatus() {
        return logisticianStatus;
    }

    public void setLogisticianStatus(VehicleLogisticianStatus logisticianStatus) {
        this.logisticianStatus = logisticianStatus;
    }

    public VehicleMechanicalStatus getMechanicalStatus() {
        return mechanicalStatus;
    }

    public void setMechanicalStatus(VehicleMechanicalStatus mechanicalStatus) {
        this.mechanicalStatus = mechanicalStatus;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
}
