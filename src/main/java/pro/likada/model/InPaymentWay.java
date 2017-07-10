package pro.likada.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by bumur on 27.02.2017.
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="INPAYMENT_WAYS")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class InPaymentWay implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "SUM")
    private Double sum;

    @Column(name = "OBJECT_LINK")
    private String objectLink;

    @Column(name = "OBJECT_TYPE")
    private Integer objectType;

    @Column(name = "DESCRIPTION")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FINANCIAL_ITEM_ID")
    private FinancialItem financialItem;

    @Override
    public String toString() {
        return "INPAYMENT_TYPE [id=" + id + ", SUM=" + sum + ", OBJECT_LINK=" + objectLink + ", OBJECT_TYPE=" + objectType
                + ", DESCRIPTION=" + description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    public String getObjectLink() {
        return objectLink;
    }

    public void setObjectLink(String objectLink) {
        this.objectLink = objectLink;
    }

    public Integer getObjectType() {
        return objectType;
    }

    public void setObjectType(Integer objectType) {
        this.objectType = objectType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public FinancialItem getFinancialItem() {
        return financialItem;
    }

    public void setFinancialItem(FinancialItem financialItem) {
        this.financialItem = financialItem;
    }
}