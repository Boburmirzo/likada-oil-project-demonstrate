package pro.likada.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Created by bumur on 13.02.2017.
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="PRODUCTS")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name="NEW_STATE", nullable=false, columnDefinition = "BOOLEAN DEFAULT false")
    private boolean newState;

    @Column(name = "TURN")
    private Long turn;

    @Column(name = "ACTIVE")
    private boolean active;

    @Column(name = "NAME_FULL")
    private String nameFull;

    @Column(name = "NAME_SHORT")
    private String nameShort;

    @Column(name = "CODE")
    private String code;

    @Column(name = "TYPE")
    private Long type;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "CONTRACT_PRICE")
    private boolean contractPrice;

    @Column(name = "BALANCE")
    private String balance;

    @Column(name = "MARK_UP")
    private Double markUp;

    @OneToOne
    @JoinColumn(name = "product_type_id")
    private ProductType productType;

    @OneToOne
    @JoinColumn(name = "GROUP_ID")
    private ProductGroup groupId;

    @OneToOne
    @JoinColumn(name = "shipment_base_id")
    private ShipmentBasis shipmentBaseId;

    @Cascade(CascadeType.ALL)
    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER, orphanRemoval = true) // TODO need to be optimized (put fetchMode LAZY)
    private Set<ProductPrice> productPrices;

    @OneToOne
    @JoinColumn(name = "measure_unit_id")
    private MeasureUnit measureUnit;

    @OneToMany(mappedBy = "product")
    private Set<Order> ordersOfProduct;

    @Override
    public String toString() {
        return "PRODUCTS [id=" + id + ", TURN=" + turn + ", ACTIVE="  + active + ", NAME_FULL=" + nameFull + ", NAME_SHORT=" + nameShort+ ", CODE=" + code
                + ", TYPE=" + type + ", ProductType=" + productType + ", CONTRACT_PRICE=" + contractPrice+ ", BALANCE=" + balance
                + ", MARK_UP=" + markUp+ ", GROUP_ID=" + groupId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isNewState() {
        return newState;
    }

    public void setNewState(boolean newState) {
        this.newState = newState;
    }

    public Long getTurn() {
        return turn;
    }

    public void setTurn(Long turn) {
        this.turn = turn;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isContractPrice() {
        return contractPrice;
    }

    public void setContractPrice(boolean contractPrice) {
        this.contractPrice = contractPrice;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public Double getMarkUp() {
        return markUp;
    }

    public void setMarkUp(Double markUp) {
        this.markUp = markUp;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public ProductGroup getGroupId() {
        return groupId;
    }

    public void setGroupId(ProductGroup groupId) {
        this.groupId = groupId;
    }

    public ShipmentBasis getShipmentBaseId() {
        return shipmentBaseId;
    }

    public void setShipmentBaseId(ShipmentBasis shipmentBaseId) {
        this.shipmentBaseId = shipmentBaseId;
    }

    public Set<ProductPrice> getProductPrices() {
        return productPrices;
    }

    public void setProductPrices(Set<ProductPrice> productPrices) {
        this.productPrices = productPrices;
    }

    public List<ProductPrice> getProductPricesList() {
        ArrayList<ProductPrice> productPricesList= new ArrayList<>();
        productPricesList.addAll(this.productPrices);
        Collections.sort(productPricesList);
        return productPricesList;
    }

    public MeasureUnit getMeasureUnit() {
        return measureUnit;
    }

    public void setMeasureUnit(MeasureUnit measureUnit) {
        this.measureUnit = measureUnit;
    }

    public Set<Order> getOrdersOfProduct() {
        return ordersOfProduct;
    }

    public void setOrdersOfProduct(Set<Order> ordersOfProduct) {
        this.ordersOfProduct = ordersOfProduct;
    }
}
