package pro.likada.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * Created by Yusupov on 1/24/2017.
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="AGREEMENTS")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Agreement implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne // TODO need to be optimized (put fetchMode LAZY)
    @JoinColumn(name = "CLIENT_ID")
    private Contractor client;

    @ManyToOne // TODO need to be optimized (put fetchMode LAZY)
    @JoinColumn(name = "CONTRACTOR_ID")
    private Contractor contractor;

    @Column(name = "AGREEMENT_NUMBER")
    private String agreementNumber;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="CREATED", nullable = false)
    private Date created;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="VALID_FROM", nullable = false)
    private Date validFrom;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="VALID_TO", nullable = false)
    private Date  validTo;

    @Column(name = "DESCRIPTION")
    private String description;

    @ManyToOne // TODO need to be optimized (put fetchMode LAZY)
    @JoinColumn(name = "STATUS_ID")
    private AgreementStatus status;

    @ManyToOne // TODO need to be optimized (put fetchMode LAZY)
    @JoinColumn(name = "TYPE_ID")
    private AgreementType type;

    @OneToMany(mappedBy = "agreement")
    private Set<Order> ordersOfAgreement;

    @OneToMany(mappedBy = "transportationAgreement")
    private Set<Order> ordersOfTransportationAgreement;

    @OneToMany(mappedBy = "additionalAgreement")
    private Set<Order> ordersOfAdditionalAgreement;

    @Override
    public String toString() {
        return "AGREEMENT [id=" + id + ", AGREEMENT_NUMBER=" + agreementNumber + ", CREATED=" + created + ", DESCRIPTION=" + description;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((agreementNumber == null) ? 0 : agreementNumber.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Agreement))
            return false;
        Agreement other = (Agreement) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (agreementNumber == null) {
            if (other.agreementNumber != null)
                return false;
        } else if (!agreementNumber.equals(other.agreementNumber))
            return false;
        return true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Contractor getClient() {
        return client;
    }

    public void setClient(Contractor client) {
        this.client = client;
    }

    public Contractor getContractor() {
        return contractor;
    }

    public void setContractor(Contractor contractor) {
        this.contractor = contractor;
    }

    public String getAgreementNumber() {
        return agreementNumber;
    }

    public void setAgreementNumber(String agreementNumber) {
        this.agreementNumber = agreementNumber;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    public Date getValidTo() {
        return validTo;
    }

    public void setValidTo(Date validTo) {
        this.validTo = validTo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AgreementStatus getStatus() {
        return status;
    }

    public void setStatus(AgreementStatus status) {
        this.status = status;
    }

    public AgreementType getType() {
        return type;
    }

    public void setType(AgreementType type) {
        this.type = type;
    }

    public Set<Order> getOrdersOfAgreement() {
        return ordersOfAgreement;
    }

    public void setOrdersOfAgreement(Set<Order> ordersOfAgreement) {
        this.ordersOfAgreement = ordersOfAgreement;
    }

    public Set<Order> getOrdersOfTransportationAgreement() {
        return ordersOfTransportationAgreement;
    }

    public void setOrdersOfTransportationAgreement(Set<Order> ordersOfTransportationAgreement) {
        this.ordersOfTransportationAgreement = ordersOfTransportationAgreement;
    }

    public Set<Order> getOrdersOfAdditionalAgreement() {
        return ordersOfAdditionalAgreement;
    }

    public void setOrdersOfAdditionalAgreement(Set<Order> ordersOfAdditionalAgreement) {
        this.ordersOfAdditionalAgreement = ordersOfAdditionalAgreement;
    }
}
