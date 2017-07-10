package pro.likada.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Yusupov on 12/22/2016.
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="BANK_ACCOUNTS")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BankAccount implements Serializable{

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(name="BANK_NAME")
    private String bankName;

    @Column(name="DESCRIPTION")
    private String description;

    @Column(name="BANK_NUMBER")
    private String bankNumber;

    @Column(name="BANK_ACCOUNT_NUMBER")
    private String bankAccountNumber;

    @Column(name="BIK")
    private String bik;

    @Column(name="CITY")
    private String city;

    @Column(name="ACTIVE", nullable=false, columnDefinition = "BOOLEAN DEFAULT true")
    private boolean active;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CONTRACTOR_ID")
    private Contractor contractor;

    @Override
    public String toString() {
        return "BANK_ACCOUNT [ID=" + id + ", BANK_NAME=" + bankName + ", DESCRIPTION=" + description
                + ", BANK_NUMBER=" + bankNumber + ", BANK_ACCOUNT_NUMBER=" + bankAccountNumber
                + ", BIK=" + bik + ", CITY=" + city + "]";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBankNumber() {
        return bankNumber;
    }

    public void setBankNumber(String bankNumber) {
        this.bankNumber = bankNumber;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getBik() {
        return bik;
    }

    public void setBik(String bik) {
        this.bik = bik;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Contractor getContractor() {
        return contractor;
    }

    public void setContractor(Contractor contractor) {
        this.contractor = contractor;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
