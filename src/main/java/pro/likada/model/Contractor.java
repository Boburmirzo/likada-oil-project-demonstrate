package pro.likada.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Created by Yusupov on 12/22/2016.
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="CONTRACTORS")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Contractor {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name="NAME_FULL", unique = true)
    private String nameFull;

    @Column(name="NAME_SHORT", unique = true)
    private String nameShort;

    @Column(name="DESCRIPTION")
    private String description;

    @NotEmpty
    @Column(name="INN", nullable=false, unique=true)
    private String inn;

    @Column(name="KPP", unique=true)
    private String kpp;

    @Column(name="OKPO")
    private String okpo;

    @Column(name="OGRN")
    private String ogrn;

    @Column(name="OKATO")
    private String okato;

    @Column(name="PHONE")
    private String phone;

    @Column(name="FAX")
    private String fax;

    @Column(name="EMAIL")
    private String email;

    @Column(name="WWW")
    private String www;

    @Column(name="NAME_WORK")
    private String nameWork;

    @Column(name = "ACTIVE", nullable=false, columnDefinition = "BOOLEAN DEFAULT true")
    private boolean active;

    @Column(name = "OURS", nullable=false, columnDefinition = "BOOLEAN DEFAULT false")
    private boolean ours;

    @ManyToOne // TODO need to be optimized (put fetchMode LAZY)
    @JoinColumn(name = "ORGANIZATION_TYPE_ID")
    private ContractorOrganizationType organizationType;

    @ManyToOne // TODO need to be optimized (put fetchMode LAZY)
    @JoinColumn(name = "AGENCY_TYPE_ID")
    private ContractorAgencyType agencyType;

    @ManyToOne // TODO need to be optimized (put fetchMode LAZY)
    @JoinColumn(name = "AGREEMENT_PATTERN_ID")
    private ContractorAgreementPattern agreementPattern;

    @ManyToOne
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "CUSTOMER_ID")
    private Customer customer;

    @OneToMany(mappedBy = "contractor", fetch = FetchType.EAGER, orphanRemoval = true)// TODO need to be optimized (put fetchMode LAZY)
    @Cascade(CascadeType.ALL)
    private Set<BankAccount> bankAccounts;

    @OneToOne
    @JoinColumn(name = "DEFAULT_BANK_ACCOUNT_ID")
    private BankAccount defaultBankAccount;

    @OneToMany(mappedBy = "contractor", fetch = FetchType.EAGER, orphanRemoval = true) // TODO need to be optimized (put fetchMode LAZY)
    @Cascade(CascadeType.ALL)
    private Set<Requisite> requisites;

    @OneToMany(mappedBy = "client")
    private Set<Agreement> clientAgreements;

    @OneToMany(mappedBy = "contractor")
    private Set<Agreement> contractorAgreements;

    @OneToOne
    @JoinColumn(name = "CREATOR_ID")
    private User creator;

    @OneToMany(mappedBy = "contractor")
    private Set<Order> ordersOfContractor;

    @OneToMany(mappedBy = "transportationContractor")
    private Set<Order> ordersOfTransportationContractor;

    @OneToMany(mappedBy = "company")
    private Set<Order> ordersOfCompany;

    @OneToMany(mappedBy = "transportationCompany")
    private Set<Order> ordersOfTransportationCompany;

    @OneToMany(mappedBy = "carrier")
    private Set<Order> ordersOfCarrier;

    @OneToMany(mappedBy = "provider")
    private Set<Order> ordersOfProvider;

    @Override
    public String toString() {
        return "CONTRACTOR [id=" + id + ", NAME_FULL=" + nameFull + ", NAME_SHORT=" + nameShort + ", DESCRIPTION=" + description
                + ", INN=" + inn + ", KPP=" + kpp + ", OKPO=" + okpo + ", OGRN=" + ogrn + ", OKATO=" + okato + ", PHONE=" + phone
                + ", FAX=" + fax + ", EMAIL=" + email + ", WWW=" + www + ", NAME_WORK=" + nameWork + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((inn == null) ? 0 : inn.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Contractor))
            return false;
        Contractor other = (Contractor) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (inn == null) {
            if (other.inn != null)
                return false;
        } else if (!inn.equals(other.inn))
            return false;
        return true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public String getKpp() {
        return kpp;
    }

    public void setKpp(String kpp) {
        this.kpp = kpp;
    }

    public String getOkpo() {
        return okpo;
    }

    public void setOkpo(String okpo) {
        this.okpo = okpo;
    }

    public String getOgrn() {
        return ogrn;
    }

    public void setOgrn(String ogrn) {
        this.ogrn = ogrn;
    }

    public String getOkato() {
        return okato;
    }

    public void setOkato(String okato) {
        this.okato = okato;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWww() {
        return www;
    }

    public void setWww(String www) {
        this.www = www;
    }

    public String getNameWork() {
        return nameWork;
    }

    public void setNameWork(String nameWork) {
        this.nameWork = nameWork;
    }

    public ContractorOrganizationType getOrganizationType() {
        return organizationType;
    }

    public void setOrganizationType(ContractorOrganizationType organizationType) {
        this.organizationType = organizationType;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Set<BankAccount> getBankAccounts() {
        return bankAccounts;
    }

    public List<BankAccount> getBankAccountsList() {
        ArrayList<BankAccount> bankAccountsList= new ArrayList<BankAccount>();
        bankAccountsList.addAll(this.bankAccounts);
        return bankAccountsList;
    }

    public void setBankAccounts(Set<BankAccount> bankAccounts) {
        this.bankAccounts = bankAccounts;
    }

    public BankAccount getDefaultBankAccount() {

        return defaultBankAccount;
    }

    public void setDefaultBankAccount(BankAccount defaultBankAccount) {
        this.defaultBankAccount = defaultBankAccount;
    }

    public Set<Requisite> getRequisites() {
        return requisites;
    }

    public void setRequisites(Set<Requisite> requisites) {
        this.requisites = requisites;
    }

    public List<Requisite> getRequisitesList() {
        ArrayList<Requisite> requisitesList= new ArrayList<Requisite>();
        requisitesList.addAll(this.requisites);
        Collections.sort(requisitesList);
        return requisitesList;
    }

    public ContractorAgencyType getAgencyType() {
        return agencyType;
    }

    public void setAgencyType(ContractorAgencyType agencyType) {
        this.agencyType = agencyType;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public ContractorAgreementPattern getAgreementPattern() {
        return agreementPattern;
    }

    public void setAgreementPattern(ContractorAgreementPattern agreementPattern) {
        this.agreementPattern = agreementPattern;
    }

    public Set<Agreement> getClientAgreements() {
        return clientAgreements;
    }

    public void setClientAgreements(Set<Agreement> clientAgreements) {
        this.clientAgreements = clientAgreements;
    }

    public Set<Agreement> getContractorAgreements() {
        return contractorAgreements;
    }

    public void setContractorAgreements(Set<Agreement> contractorAgreements) {
        this.contractorAgreements = contractorAgreements;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public boolean isOurs() {
        return ours;
    }

    public void setOurs(boolean ours) {
        this.ours = ours;
    }

    public Set<Order> getOrdersOfContractor() {
        return ordersOfContractor;
    }

    public void setOrdersOfContractor(Set<Order> ordersOfContractor) {
        this.ordersOfContractor = ordersOfContractor;
    }

    public Set<Order> getOrdersOfTransportationContractor() {
        return ordersOfTransportationContractor;
    }

    public void setOrdersOfTransportationContractor(Set<Order> ordersOfTransportationContractor) {
        this.ordersOfTransportationContractor = ordersOfTransportationContractor;
    }

    public Set<Order> getOrdersOfCompany() {
        return ordersOfCompany;
    }

    public void setOrdersOfCompany(Set<Order> ordersOfCompany) {
        this.ordersOfCompany = ordersOfCompany;
    }

    public Set<Order> getOrdersOfTransportationCompany() {
        return ordersOfTransportationCompany;
    }

    public void setOrdersOfTransportationCompany(Set<Order> ordersOfTransportationCompany) {
        this.ordersOfTransportationCompany = ordersOfTransportationCompany;
    }

    public Set<Order> getOrdersOfCarrier() {
        return ordersOfCarrier;
    }

    public void setOrdersOfCarrier(Set<Order> ordersOfCarrier) {
        this.ordersOfCarrier = ordersOfCarrier;
    }

    public Set<Order> getOrdersOfProvider() {
        return ordersOfProvider;
    }

    public void setOrdersOfProvider(Set<Order> ordersOfProvider) {
        this.ordersOfProvider = ordersOfProvider;
    }
}
