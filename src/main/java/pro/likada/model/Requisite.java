package pro.likada.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by Yusupov on 12/27/2016.
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="REQUISITES")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Requisite implements Comparable<Requisite> {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ACTIVE", nullable=false, columnDefinition = "BOOLEAN DEFAULT true")
    private boolean active;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="VALID_FROM", nullable = false)
    private Date validFrom;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="VALID_TO", nullable = false)
    private Date  validTo;

    @Column(name = "DOC_KIND")
    private Long docKind;

    @Column(name = "DOC_SERIAL")
    private String docSerial;

    @Column(name = "DOC_NUMBER")
    private String docNumber;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="DOC_GIVEN_DATE", nullable = false)
    private Date docGivenDate;

    @Column(name = "DOC_GIVEN_FROM")
    private String docGivenFrom;

    @Column(name = "DOC_DEPARTMENT_CODE")
    private String docDepartmentCode;

    @Column(name = "DOC_DESCRIPTION")
    private String docDescription;

    @Column(name = "MANAGER_POST")
    private String managerPost;

    @Column(name = "MANAGER_POST_GEN")
    private String managerPostGen;

    @Column(name = "MANAGER_NAME_SHORT")
    private String managerNameShort;

    @Column(name = "MANAGER_NAME_SHORT_GEN")
    private String managerNameShortGen;

    @Column(name = "MANAGER_NAME_FULL")
    private String managerNameFull;

    @Column(name = "MANAGER_NAME_FULL_GEN")
    private String managerNameFullGen;

    @Column(name = "MANAGER_DOC_REFERENCE")
    private String managerDocReference;

    @Column(name = "MANAGER_DESCRIPTION")
    private String managerDescription;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="MANAGER_DATE_OF_BIRTH")
    private Date managerDateOfBirth;

    @Column(name = "ADDRESS_FACT_INDEX")
    private String addressFactIndex;

    @Column(name = "ADDRESS_FACT_POSTBOX")
    private String addressFactPostBox;

    @ManyToOne
    @Cascade(CascadeType.PERSIST)
    @JoinColumn(name = "ADDRESS_FACT_REGION_CODE")
    private AddressRegion addressFactRegion;

    @Column(name = "ADDRESS_FACT_TOWNSHIP")
    private String addressFactTownship;

    @Column(name = "ADDRESS_FACT_CITY")
    private String addressFactCity;

    @Column(name = "ADDRESS_FACT_TOWN")
    private String addressFactTown;

    @Column(name = "ADDRESS_FACT_STREET")
    private String addressFactStreet;

    @ManyToOne
    @Cascade(CascadeType.PERSIST)
    @JoinColumn(name = "ADDRESS_FACT_HOUSE_TYPE")
    private AddressHouseType addressFactHouseType;

    @Column(name = "ADDRESS_FACT_HOUSE_NUMBER")
    private String addressFactHouseNumber;

    @ManyToOne
    @Cascade(CascadeType.PERSIST)
    @JoinColumn(name = "ADDRESS_FACT_BUILDING_TYPE")
    private AddressBuildingType addressFactBuildingType;

    @Column(name = "ADDRESS_FACT_BUILDING_NUMBER")
    private String addressFactBuildingNumber;

    @ManyToOne
    @Cascade(CascadeType.PERSIST)
    @JoinColumn(name = "ADDRESS_FACT_APARTMENT_TYPE")
    private AddressApartmentType addressFactApartmentType;

    @Column(name = "ADDRESS_FACT_APARTMENT_NUMBER")
    private String addressFactApartmentNumber;

    @Column(name = "ADDRESS_FACT_DESCRIPTION")
    private String addressFactDescription;

    @Column(name = "ADDRESS_LEGAL_INDEX")
    private String addressLegalIndex;

    @Column(name = "ADDRESS_LEGAL_POSTBOX")
    private String addressLegalPostBox;

    @ManyToOne
    @Cascade(CascadeType.PERSIST)
    @JoinColumn(name = "ADDRESS_LEGAL_REGION_CODE")
    private AddressRegion addressLegalRegion;

    @Column(name = "ADDRESS_LEGAL_TOWNSHIP")
    private String addressLegalTownship;

    @Column(name = "ADDRESS_LEGAL_CITY")
    private String addressLegalCity;

    @Column(name = "ADDRESS_LEGAL_TOWN")
    private String addressLegalTown;

    @Column(name = "ADDRESS_LEGAL_STREET")
    private String addressLegalStreet;

    @ManyToOne
    @Cascade(CascadeType.PERSIST)
    @JoinColumn(name = "ADDRESS_LEGAL_HOUSE_TYPE")
    private AddressHouseType addressLegalHouseType;

    @Column(name = "ADDRESS_LEGAL_HOUSE_NUMBER")
    private String addressLegalHouseNumber;

    @ManyToOne
    @Cascade(CascadeType.PERSIST)
    @JoinColumn(name = "ADDRESS_LEGAL_BUILDING_TYPE")
    private AddressBuildingType addressLegalBuildingType;

    @Column(name = "ADDRESS_LEGAL_BUILDING_NUMBER")
    private String addressLegalBuildingNumber;

    @ManyToOne
    @Cascade(CascadeType.PERSIST)
    @JoinColumn(name = "ADDRESS_LEGAL_APARTMENT_TYPE")
    private AddressApartmentType addressLegalApartmentType;

    @Column(name = "ADDRESS_LEGAL_APARTMENT_NUMBER")
    private String addressLegalApartmentNumber;

    @Column(name = "ADDRESS_LEGAL_DESCRIPTION")
    private String addressLegalDescription;

    @Column(name = "ADDRESS_LEGAL_EQUALS_FACT", nullable=false, columnDefinition = "BOOLEAN DEFAULT true")
    private boolean addressLegalEqualsFact;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CONTRACTOR_ID")
    private Contractor contractor;

    @Override
    public int compareTo(Requisite requisite) {
        if (requisite != null) {
            if (this.getValidFrom()!= null && requisite.getValidFrom() != null) {
                return this.getValidFrom().compareTo(requisite.getValidFrom());
            } else if (this.getValidFrom() != null && requisite.getValidFrom() == null) {
                return 1;
            } else if (this.getValidFrom() == null && requisite.getValidFrom() != null) {
                return -1;
            }
            return 0;
        } else if (this.getValidFrom() != null && requisite.getValidFrom() == null) {
            return 1;
        } else if (this.getValidFrom() == null && requisite.getValidFrom() != null) {
            return -1;
        }

        return 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
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

    public Long getDocKind() {
        return docKind;
    }

    public void setDocKind(Long docKind) {
        this.docKind = docKind;
    }

    public String getDocSerial() {
        return docSerial;
    }

    public void setDocSerial(String docSerial) {
        this.docSerial = docSerial;
    }

    public String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    public Date getDocGivenDate() {
        return docGivenDate;
    }

    public void setDocGivenDate(Date docGivenDate) {
        this.docGivenDate = docGivenDate;
    }

    public String getDocGivenFrom() {
        return docGivenFrom;
    }

    public void setDocGivenFrom(String docGivenFrom) {
        this.docGivenFrom = docGivenFrom;
    }

    public String getDocDepartmentCode() {
        return docDepartmentCode;
    }

    public void setDocDepartmentCode(String docDepartmentCode) {
        this.docDepartmentCode = docDepartmentCode;
    }

    public String getDocDescription() {
        return docDescription;
    }

    public void setDocDescription(String docDescription) {
        this.docDescription = docDescription;
    }

    public String getManagerPost() {
        return managerPost;
    }

    public void setManagerPost(String managerPost) {
        this.managerPost = managerPost;
    }

    public String getManagerPostGen() {
        return managerPostGen;
    }

    public void setManagerPostGen(String managerPostGen) {
        this.managerPostGen = managerPostGen;
    }

    public String getManagerNameShort() {
        return managerNameShort;
    }

    public void setManagerNameShort(String managerNameShort) {
        this.managerNameShort = managerNameShort;
    }

    public String getManagerNameShortGen() {
        return managerNameShortGen;
    }

    public void setManagerNameShortGen(String managerNameShortGen) {
        this.managerNameShortGen = managerNameShortGen;
    }

    public String getManagerNameFull() {
        return managerNameFull;
    }

    public void setManagerNameFull(String managerNameFull) {
        this.managerNameFull = managerNameFull;
    }

    public String getManagerNameFullGen() {
        return managerNameFullGen;
    }

    public void setManagerNameFullGen(String managerNameFullGen) {
        this.managerNameFullGen = managerNameFullGen;
    }

    public String getManagerDocReference() {
        return managerDocReference;
    }

    public void setManagerDocReference(String managerDocReference) {
        this.managerDocReference = managerDocReference;
    }

    public String getManagerDescription() {
        return managerDescription;
    }

    public void setManagerDescription(String managerDescription) {
        this.managerDescription = managerDescription;
    }

    public Date getManagerDateOfBirth() {
        return managerDateOfBirth;
    }

    public void setManagerDateOfBirth(Date managerDateOfBirth) {
        this.managerDateOfBirth = managerDateOfBirth;
    }

    public String getAddressFactIndex() {
        return addressFactIndex;
    }

    public void setAddressFactIndex(String addressFactIndex) {
        this.addressFactIndex = addressFactIndex;
    }

    public String getAddressFactPostBox() {
        return addressFactPostBox;
    }

    public void setAddressFactPostBox(String addressFactPostBox) {
        this.addressFactPostBox = addressFactPostBox;
    }

    public AddressRegion getAddressFactRegion() {
        return addressFactRegion;
    }

    public void setAddressFactRegion(AddressRegion addressFactRegion) {
        this.addressFactRegion = addressFactRegion;
    }

    public String getAddressFactTownship() {
        return addressFactTownship;
    }

    public void setAddressFactTownship(String addressFactTownship) {
        this.addressFactTownship = addressFactTownship;
    }

    public String getAddressFactCity() {
        return addressFactCity;
    }

    public void setAddressFactCity(String addressFactCity) {
        this.addressFactCity = addressFactCity;
    }

    public String getAddressFactTown() {
        return addressFactTown;
    }

    public void setAddressFactTown(String addressFactTown) {
        this.addressFactTown = addressFactTown;
    }

    public String getAddressFactStreet() {
        return addressFactStreet;
    }

    public void setAddressFactStreet(String addressFactStreet) {
        this.addressFactStreet = addressFactStreet;
    }

    public AddressHouseType getAddressFactHouseType() {
        return addressFactHouseType;
    }

    public void setAddressFactHouseType(AddressHouseType addressFactHouseType) {
        this.addressFactHouseType = addressFactHouseType;
    }

    public String getAddressFactHouseNumber() {
        return addressFactHouseNumber;
    }

    public void setAddressFactHouseNumber(String addressFactHouseNumber) {
        this.addressFactHouseNumber = addressFactHouseNumber;
    }

    public AddressBuildingType getAddressFactBuildingType() {
        return addressFactBuildingType;
    }

    public void setAddressFactBuildingType(AddressBuildingType addressFactBuildingType) {
        this.addressFactBuildingType = addressFactBuildingType;
    }

    public String getAddressFactBuildingNumber() {
        return addressFactBuildingNumber;
    }

    public void setAddressFactBuildingNumber(String addressFactBuildingNumber) {
        this.addressFactBuildingNumber = addressFactBuildingNumber;
    }

    public AddressApartmentType getAddressFactApartmentType() {
        return addressFactApartmentType;
    }

    public void setAddressFactApartmentType(AddressApartmentType addressFactApartmentType) {
        this.addressFactApartmentType = addressFactApartmentType;
    }

    public String getAddressFactApartmentNumber() {
        return addressFactApartmentNumber;
    }

    public void setAddressFactApartmentNumber(String addressFactApartmentNumber) {
        this.addressFactApartmentNumber = addressFactApartmentNumber;
    }

    public String getAddressFactDescription() {
        return addressFactDescription;
    }

    public void setAddressFactDescription(String addressFactDescription) {
        this.addressFactDescription = addressFactDescription;
    }

    public String getAddressLegalIndex() {
        return addressLegalIndex;
    }

    public void setAddressLegalIndex(String addressLegalIndex) {
        this.addressLegalIndex = addressLegalIndex;
    }

    public String getAddressLegalPostBox() {
        return addressLegalPostBox;
    }

    public void setAddressLegalPostBox(String addressLegalPostBox) {
        this.addressLegalPostBox = addressLegalPostBox;
    }

    public AddressRegion getAddressLegalRegion() {
        return addressLegalRegion;
    }

    public void setAddressLegalRegion(AddressRegion addressLegalRegion) {
        this.addressLegalRegion = addressLegalRegion;
    }

    public String getAddressLegalTownship() {
        return addressLegalTownship;
    }

    public void setAddressLegalTownship(String addressLegalTownship) {
        this.addressLegalTownship = addressLegalTownship;
    }

    public String getAddressLegalCity() {
        return addressLegalCity;
    }

    public void setAddressLegalCity(String addressLegalCity) {
        this.addressLegalCity = addressLegalCity;
    }

    public String getAddressLegalTown() {
        return addressLegalTown;
    }

    public void setAddressLegalTown(String addressLegalTown) {
        this.addressLegalTown = addressLegalTown;
    }

    public String getAddressLegalStreet() {
        return addressLegalStreet;
    }

    public void setAddressLegalStreet(String addressLegalStreet) {
        this.addressLegalStreet = addressLegalStreet;
    }

    public AddressHouseType getAddressLegalHouseType() {
        return addressLegalHouseType;
    }

    public void setAddressLegalHouseType(AddressHouseType addressLegalHouseType) {
        this.addressLegalHouseType = addressLegalHouseType;
    }

    public String getAddressLegalHouseNumber() {
        return addressLegalHouseNumber;
    }

    public void setAddressLegalHouseNumber(String addressLegalHouseNumber) {
        this.addressLegalHouseNumber = addressLegalHouseNumber;
    }

    public AddressBuildingType getAddressLegalBuildingType() {
        return addressLegalBuildingType;
    }

    public void setAddressLegalBuildingType(AddressBuildingType addressLegalBuildingType) {
        this.addressLegalBuildingType = addressLegalBuildingType;
    }

    public String getAddressLegalBuildingNumber() {
        return addressLegalBuildingNumber;
    }

    public void setAddressLegalBuildingNumber(String addressLegalBuildingNumber) {
        this.addressLegalBuildingNumber = addressLegalBuildingNumber;
    }

    public AddressApartmentType getAddressLegalApartmentType() {
        return addressLegalApartmentType;
    }

    public void setAddressLegalApartmentType(AddressApartmentType addressLegalApartmentType) {
        this.addressLegalApartmentType = addressLegalApartmentType;
    }

    public String getAddressLegalApartmentNumber() {
        return addressLegalApartmentNumber;
    }

    public void setAddressLegalApartmentNumber(String addressLegalApartmentNumber) {
        this.addressLegalApartmentNumber = addressLegalApartmentNumber;
    }

    public String getAddressLegalDescription() {
        return addressLegalDescription;
    }

    public void setAddressLegalDescription(String addressLegalDescription) {
        this.addressLegalDescription = addressLegalDescription;
    }

    public Boolean getAddressLegalEqualsFact() {
        return addressLegalEqualsFact;
    }

    public void setAddressLegalEqualsFact(Boolean addressLegalEqualsFact) {
        this.addressLegalEqualsFact = addressLegalEqualsFact;
    }

    public Contractor getContractor() {
        return contractor;
    }

    public void setContractor(Contractor contractor) {
        this.contractor = contractor;
    }

}
