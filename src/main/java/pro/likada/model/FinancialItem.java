package pro.likada.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by bumur on 27.02.2017.
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="FINANCIAL_ITEMS")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class FinancialItem implements Serializable{

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "SUM")
    private Double sum;

    @Column(name="DATE")
    private Date date;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="CREATED", nullable = false)
    private Date created;

    @Column(name = "ACTIVE")
    private boolean active;

    @Column(name = "BALANCE_TYPE")
    private Integer balanceType;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "SOURCE_TYPE")
    private Integer sourceType;

    @Column(name = "SOURCE_TEXT")
    private String sourceText;

    @NotNull
    @Column(name = "CREATOR_USER_ID", nullable = false)
    private Long creatorUserId;

    @Column(name = "CREATOR_USER_TITLE")
    private String creatorUserTitle;

    @OneToOne
    @JoinColumn(name = "CONTRACTOR_FROM_ID")
    private Contractor contractorFrom;

    @OneToOne
    @JoinColumn(name = "CONTRACTOR_TO_ID")
    private Contractor contractorTo;

    @ManyToOne
    @JoinColumn(name = "FINANCIAL_ITEM_TYPE_ID")
    private FinancialItemType financialItemType;

    @Cascade(CascadeType.ALL)
    @OneToMany(mappedBy = "financialItem", orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<InPaymentWay> inPaymentWays;

    @Transient
    private Boolean expanded=false;

    @Override
    public String toString() {
        return "FINANCIAL_ITEM [id=" + id + ", SUM=" + sum + ", DATE="  + date + ", CREATED=" + created + ", ACTIVE=" + active+ ", BALANCE_TYPE=" + balanceType
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Integer getBalanceType() {
        return balanceType;
    }

    public void setBalanceType(Integer balanceType) {
        this.balanceType = balanceType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSourceType() {
        return sourceType;
    }

    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
    }

    public String getSourceText() {
        return sourceText;
    }

    public void setSourceText(String sourceText) {
        this.sourceText = sourceText;
    }

    public Long getCreatorUserId() {
        return creatorUserId;
    }

    public void setCreatorUserId(Long creatorUserId) {
        this.creatorUserId = creatorUserId;
    }

    public String getCreatorUserTitle() {
        return creatorUserTitle;
    }

    public void setCreatorUserTitle(String creatorUserTitle) {
        this.creatorUserTitle = creatorUserTitle;
    }

    public Contractor getContractorFrom() {
        return contractorFrom;
    }

    public void setContractorFrom(Contractor contractorFrom) {
        this.contractorFrom = contractorFrom;
    }

    public Contractor getContractorTo() {
        return contractorTo;
    }

    public void setContractorTo(Contractor contractorTo) {
        this.contractorTo = contractorTo;
    }

    public FinancialItemType getFinancialItemType() {
        return financialItemType;
    }

    public void setFinancialItemType(FinancialItemType financialItemType) {
        this.financialItemType = financialItemType;
    }

    public Set<InPaymentWay> getInPaymentWays() {
        return inPaymentWays;
    }

    public void setInPaymentWays(Set<InPaymentWay> inPaymentWays) {
        this.inPaymentWays = inPaymentWays;
    }

    public List<InPaymentWay> getInPaymentWayList() {
        ArrayList<InPaymentWay> inPaymentWaysList= new ArrayList<>();
        inPaymentWaysList.addAll(this.inPaymentWays);
        return inPaymentWaysList;
    }

    public Boolean getExpanded() {
        return expanded;
    }

    public void setExpanded(Boolean expanded) {
        this.expanded = expanded;
    }
}
