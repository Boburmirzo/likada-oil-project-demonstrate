package pro.likada.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.*;

/**
 * Created by bumur on 25.03.2017.
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="DRIVES")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Drive implements Serializable{

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name="DRIVE_SERIAL")
    private String driveSerial;

    @Column(name="DATE_START_PLAN")
    private Date dateStartPlan;

    @Column(name="DATE_START_FACT")
    private Date dateStartFact;

    @Column(name="DATE_FINISH_PLAN")
    private Date dateFinishPlan;

    @Column(name="DATE_FINISH_FACT")
    private Date dateFinishFact;

    @Column(name="PRODUCT_NAME")
    private String productName;

    @Column(name = "QUANTITY_PLAN")
    private Double quantityPlan;

    @Column(name = "QUANTITY_FACT")
    private Double quantityFact;

    @Column(name = "VOLUME_PLAN")
    private Double volumePlan;

    @Column(name = "VOLUME_FACT")
    private Double volumeFact;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="CREATED", nullable = false)
    private Date created;

    @Column(name="DESCRIPTION")
    private String description;

    @OneToOne
    @JoinColumn(name = "VEHICLE_ID")
    private Vehicle vehicle;

    @OneToOne
    @JoinColumn(name = "LOGISTICIAN_ID")
    private User logistician;

    @OneToOne
    @JoinColumn(name = "DRIVER_ID")
    private Driver driver;

    @OneToOne
    @JoinColumn(name = "COMPANY_ID")
    private Contractor company;

    @OneToOne
    @JoinColumn(name = "CARRIER_ID")
    private Contractor carrier;

    @OneToOne
    @JoinColumn(name = "RECEIVER_ID")
    private Contractor receiver;

    @OneToOne
    @JoinColumn(name = "CREATED_USER_ID")
    private User createdUserid;

    @ManyToOne
    @JoinColumn(name = "DRIVE_STATUS_ID")
    private DriveStatus driveStatus;

    @Cascade(CascadeType.ALL)
    @OneToMany(mappedBy = "drive", orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<DriveState> driveStates;

    @Cascade(CascadeType.ALL)
    @OneToMany(mappedBy = "driveSource", orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<DrivePoint> drivePointsSource;

    @Cascade(CascadeType.ALL)
    @OneToMany(mappedBy = "driveDestination", orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<DrivePoint> drivePointsDestination;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "DRIVE_ORDERS",
            joinColumns = { @JoinColumn(name = "DRIVE_ID") },
            inverseJoinColumns = { @JoinColumn(name = "ORDER_ID") })
    private Set<Order> orders;

    @Override
    public String toString() {
        return "DRIVES[" +
                "id=" + id +
                ", DATE_START_PLAN='" + dateStartPlan +
                ", DATE_START_FACT='" + dateStartFact +
                ", DATE_FINISH_PLAN='" + dateFinishPlan +
                ", DATE_FINISH_FACT='" + dateStartFact +
                ", PRODUCT_NAME=" + productName +
                ", QUANTITY_PLAN='" + quantityPlan +
                ", QUANTITY_FACT='" + quantityFact +
                ", VOLUME_PLAN=" + volumePlan +
                ", VOLUME_FACT='" + volumeFact +
                ", CREATED='" + created +
                ", String='" + description +
                ']';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDriveSerial() {
        return driveSerial;
    }

    public void setDriveSerial(String driveSerial) {
        this.driveSerial = driveSerial;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    public List<Order> getOrderList() {
        ArrayList<Order> orders= new ArrayList<>();
        orders.addAll(this.orders);
        return orders;
    }

    public Date getDateStartPlan() {
        return dateStartPlan;
    }

    public void setDateStartPlan(Date dateStartPlan) {
        this.dateStartPlan = dateStartPlan;
    }

    public Date getDateStartFact() {
        return dateStartFact;
    }

    public void setDateStartFact(Date dateStartFact) {
        this.dateStartFact = dateStartFact;
    }

    public Date getDateFinishPlan() {
        return dateFinishPlan;
    }

    public void setDateFinishPlan(Date dateFinishPlan) {
        this.dateFinishPlan = dateFinishPlan;
    }

    public Date getDateFinishFact() {
        return dateFinishFact;
    }

    public void setDateFinishFact(Date dateFinishFact) {
        this.dateFinishFact = dateFinishFact;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getQuantityPlan() {
        return quantityPlan;
    }

    public void setQuantityPlan(Double quantityPlan) {
        this.quantityPlan = quantityPlan;
    }

    public Double getQuantityFact() {
        return quantityFact;
    }

    public void setQuantityFact(Double quantityFact) {
        this.quantityFact = quantityFact;
    }

    public Double getVolumePlan() {
        return volumePlan;
    }

    public void setVolumePlan(Double volumePlan) {
        this.volumePlan = volumePlan;
    }

    public Double getVolumeFact() {
        return volumeFact;
    }

    public void setVolumeFact(Double volumeFact) {
        this.volumeFact = volumeFact;
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

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public User getLogistician() {
        return logistician;
    }

    public void setLogistician(User logistician) {
        this.logistician = logistician;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Contractor getCompany() {
        return company;
    }

    public void setCompany(Contractor company) {
        this.company = company;
    }

    public Contractor getCarrier() {
        return carrier;
    }

    public void setCarrier(Contractor carrier) {
        this.carrier = carrier;
    }

    public Contractor getReceiver() {
        return receiver;
    }

    public void setReceiver(Contractor receiver) {
        this.receiver = receiver;
    }

    public User getCreatedUserid() {
        return createdUserid;
    }

    public void setCreatedUserid(User createdUserid) {
        this.createdUserid = createdUserid;
    }

    public DriveStatus getDriveStatus() {
        return driveStatus;
    }

    public void setDriveStatus(DriveStatus driveStatus) {
        this.driveStatus = driveStatus;
    }

    public Set<DriveState> getDriveStates() {
        return driveStates;
    }

    public void setDriveStates(Set<DriveState> driveStates) {
        this.driveStates = driveStates;
    }

    public List<DriveState> getDriveStatesList() {
        ArrayList<DriveState> driveStates= new ArrayList<>();
        driveStates.addAll(this.driveStates);
        Collections.sort(driveStates);
        return driveStates;
    }

    public Set<DrivePoint> getDrivePointsSource() {
        return drivePointsSource;
    }

    public List<DrivePoint> getSourceDrivePointsList() {
        ArrayList<DrivePoint> drivePoints= new ArrayList<>();
        drivePoints.addAll(this.drivePointsSource);
        return drivePoints;
    }

    public List<DrivePoint> getDestinationDrivePointsList() {
        ArrayList<DrivePoint> drivePoints= new ArrayList<>();
        drivePoints.addAll(this.drivePointsDestination);
        return drivePoints;
    }
    public void setDrivePointsSource(Set<DrivePoint> drivePointsSource) {
        this.drivePointsSource = drivePointsSource;
    }

    public Set<DrivePoint> getDrivePointsDestination() {
        return drivePointsDestination;
    }

    public void setDrivePointsDestination(Set<DrivePoint> drivePointsDestination) {
        this.drivePointsDestination = drivePointsDestination;
    }
}
