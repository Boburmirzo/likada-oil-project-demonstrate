package pro.likada.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by bumur on 11.02.2017.
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="SHIPMENT_BASIS")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ShipmentBasis implements Serializable{

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TURN")
    private Long turn;

    @Column(name = "NAME_FULL")
    private String nameFull;

    @Column(name = "NAME_SHORT")
    private String nameShort;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "latitude")
    private Double longitude;

    @Column(name = "longitude")
    private Double latitude;

    @Column(name = "icon")
    private String icon;

    @OneToMany(mappedBy = "shipmentBase")
    private Set<Order> ordersOfShipmentBase;

    @Override
    public String toString() {
        return "ShipmentBasis[" +
                "id=" + id +
                ", turn=" + turn +
                ", nameFull='" + nameFull +
                ", nameShort='" + nameShort +
                ", address='" + address +
                ", description='" + description +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", icon='" + icon +
                ']';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTurn() {
        return turn;
    }

    public void setTurn(Long turn) {
        this.turn = turn;
    }

    public String getNameFull() {
        return nameFull;
    }

    public void setNameFull(String nameFull) {
        this.nameFull = nameFull;
    }

    public String getNameShort() {
        return nameShort;
    }

    public void setNameShort(String nameShort) {
        this.nameShort = nameShort;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Set<Order> getOrdersOfShipmentBase() {
        return ordersOfShipmentBase;
    }

    public void setOrdersOfShipmentBase(Set<Order> ordersOfShipmentBase) {
        this.ordersOfShipmentBase = ordersOfShipmentBase;
    }
}
