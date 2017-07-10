package pro.likada.model;

import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;
import pro.likada.dto.VehicleStatusInfo;
import pro.likada.util.VehicleStateEnum;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.*;

/**
 * Created by abuca on 04.03.17.
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="VEHICLES")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Vehicle {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "AUTOGRAPH_DEVICE_ID")
    private Long autographDeviceID;

    @Column(name = "GUID")
    @Pattern(regexp = "[0-9a-z]{8}-[0-9a-z]{4}-[0-9a-z]{4}-[0-9a-z]{4}-[0-9a-z]{12}")
    private String GUID;

    @Column(name = "DESCRIPTION")
    private String description;

    @ManyToOne
    @JoinColumn(name = "CARRIER_ID")
    private Contractor carrier;

    @ManyToOne
    @JoinColumn(name = "LOGISTICIAN_ID")
    private User logistician;

    @OneToOne
    @JoinColumn(name = "TERMINAL_VEHICLE_ID")
    private User terminalVehicleID;

    @OneToOne
    @JoinColumn(name = "CURRENT_VEHICLE_STATUS_ID")
    private VehicleStatus status;

    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @OneToMany(mappedBy = "vehicle", orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<VehicleStatus> statuses;

    @ManyToOne
    @JoinColumn(name = "TRUCK_ID", nullable = false)
    private Truck truck;

    @ManyToOne
    @JoinColumn(name = "TRAILER_ID")
    private Trailer trailer;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "VEHICLES_DRIVERS",
            joinColumns = { @JoinColumn(name = "VEHICLE_ID") },
            inverseJoinColumns = { @JoinColumn(name = "DRIVER_ID") })
    private Set<Driver> drivers;

    @OneToMany(mappedBy = "vehicle")
    private Set<Order> ordersOfVehicle;

    @Transient
    private Double latitude;
    @Transient
    private Double longitude;
    @Transient
    private Double speed;
    @Transient
    private Double course;
    @Transient
    private Date lastObservationDate;
    @Transient
    private VehicleStateEnum moveState;

    @Override
    public String toString() {
        return "VEHICLE[" +
                "id=" + id +
                ", DESCRIPTION='" + description +
                ", CARRIER=" + carrier +
                ", LOGISTICIAN=" + logistician +
                ", TRUCK=" + truck +
                ", TRAILER=" + trailer +
                ']';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vehicle)) return false;

        Vehicle vehicle = (Vehicle) o;

        if (GUID != null ? !GUID.equals(vehicle.GUID) : vehicle.GUID != null) return false;
        if (truck != null ? !truck.equals(vehicle.truck) : vehicle.truck != null) return false;
        return trailer != null ? trailer.equals(vehicle.trailer) : vehicle.trailer == null;

    }

    @Override
    public int hashCode() {
        int result = GUID != null ? GUID.hashCode() : 0;
        result = 31 * result + (truck != null ? truck.hashCode() : 0);
        result = 31 * result + (trailer != null ? trailer.hashCode() : 0);
        return result;
    }

    public Long getAutographDeviceID() {
        return autographDeviceID;
    }

    public void setAutographDeviceID(Long autographDeviceID) {
        this.autographDeviceID = autographDeviceID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGUID() {
        return GUID;
    }

    public void setGUID(String GUID) {
        this.GUID = GUID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Contractor getCarrier() {
        return carrier;
    }

    public void setCarrier(Contractor carrier) {
        this.carrier = carrier;
    }

    public User getLogistician() {
        return logistician;
    }

    public void setLogistician(User logistician) {
        this.logistician = logistician;
    }

    public User getTerminalVehicleID() {
        return terminalVehicleID;
    }

    public void setTerminalVehicleID(User terminalVehicleID) {
        this.terminalVehicleID = terminalVehicleID;
    }

    public VehicleStatus getStatus() {
        return status;
    }

    public void setStatus(VehicleStatus status) {
        this.status = status;
    }

    public Truck getTruck() {
        return truck;
    }

    public void setTruck(Truck truck) {
        this.truck = truck;
    }

    public Trailer getTrailer() {
        return trailer;
    }

    public void setTrailer(Trailer trailer) {
        this.trailer = trailer;
    }

    public Set<VehicleStatus> getStatuses() {
        return statuses;
    }

    public void setStatuses(Set<VehicleStatus> statuses) {
        this.statuses = statuses;
    }

    public Set<Driver> getDrivers() {
        return drivers;
    }

    public void setDrivers(Set<Driver> drivers) {
        this.drivers = drivers;
    }

    public List<Driver> getDriverList() {
        if(drivers != null){
            return new ArrayList<>(drivers);
        }
        else {
            return null;
        }
    }

    public void setDriverList(List<Driver> driverList) {
        if(driverList != null){
            this.drivers = new HashSet<>(driverList);
        }
        else {
            this.drivers = null;
        }
    }

    public List<VehicleStatusInfo> getVehicleStatusesList() {
        List<VehicleStatusInfo> vehicleStatusInfos = new ArrayList<>();
        List<VehicleLogisticianStatus> vehicleLogisticianStatuses = new ArrayList<>();
        List<VehicleMechanicalStatus> vehicleMechanicalStatuses = new ArrayList<>();
        for(VehicleStatus vehicleStatus:this.statuses){
              if(vehicleStatus.getLogisticianStatus()!=null && !vehicleLogisticianStatuses.contains(vehicleStatus.getLogisticianStatus())){
                  vehicleLogisticianStatuses.add(vehicleStatus.getLogisticianStatus());
                  VehicleStatusInfo vehicleStatusInfo = new VehicleStatusInfo();
                  vehicleStatusInfo.setCreated(vehicleStatus.getLogisticianStatus().getStatusDate());
                  vehicleStatusInfo.setCreator(vehicleStatus.getLogisticianStatus().getCreator());
                  vehicleStatusInfo.setDescription(vehicleStatus.getLogisticianStatus().getDescription());
                  vehicleStatusInfo.setTitle(vehicleStatus.getLogisticianStatus().getTitle());
                  vehicleStatusInfo.setTypeOfStatus(0);
                  vehicleStatusInfos.add(vehicleStatusInfo);
              }
              if(vehicleStatus.getMechanicalStatus()!=null  && !vehicleMechanicalStatuses.contains(vehicleStatus.getMechanicalStatus())){
                  vehicleMechanicalStatuses.add(vehicleStatus.getMechanicalStatus());
                  VehicleStatusInfo vehicleStatusInfo = new VehicleStatusInfo();
                  vehicleStatusInfo.setTitle(vehicleStatus.getMechanicalStatus().getMehahicalStatusTitle());
                  vehicleStatusInfo.setCreator(vehicleStatus.getMechanicalStatus().getCreator());
                  vehicleStatusInfo.setDescription(vehicleStatus.getMechanicalStatus().getDescription());
                  vehicleStatusInfo.setTypeOfStatus(1);
                  vehicleStatusInfos.add(vehicleStatusInfo);
              }

        }
        return vehicleStatusInfos;
    }

    public Set<Order> getOrdersOfVehicle() {
        return ordersOfVehicle;
    }

    public void setOrdersOfVehicle(Set<Order> ordersOfVehicle) {
        this.ordersOfVehicle = ordersOfVehicle;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public Double getCourse() {
        return course;
    }

    public void setCourse(Double course) {
        this.course = course;
    }

    public Date getLastObservationDate() {
        return lastObservationDate;
    }

    public void setLastObservationDate(Date lastObservationDate) {
        this.lastObservationDate = lastObservationDate;
    }

    public VehicleStateEnum getMoveState() {
        return moveState;
    }

    public void setMoveState(VehicleStateEnum moveState) {
        this.moveState = moveState;
    }
}

