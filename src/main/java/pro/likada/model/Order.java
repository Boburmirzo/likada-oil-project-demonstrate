package pro.likada.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * Created by Yusupov on 3/9/2017.
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="ORDERS")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "PRODUCT_COUNT_PLAN")
    private Double productCountPlan;

    @Column(name = "PRODUCT_VOLUME_PLAN")
    private Double productVolumePlan;

    @Column(name = "PRODUCT_COUNT_FACT")
    private Double productCountFact;

    @Column(name = "PRODUCT_VOLUME_FACT")
    private Double productVolumeFact;

    @Column(name = "STATE_NEW", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean stateNew;

    @Column(name = "STATE_DELETE", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean stateDelete;

    @Column(name = "STATE_CANCELLED", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean stateCancelled;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LOAD_CLAIM_DATE")
    private Date loadClaimDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CLOSED_DATE")
    private Date closedDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CANCELLED_DATE")
    private Date cancelledDate;

    @Column(name = "LOAD_POINT_TYPE", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean loadPointType;

    @Column(name = "LOAD_CONTACTS")
    private String loadContacts;

    @Column(name = "LOAD_DESCRIPTION")
    private String loadDescription;

    @Column(name = "LOAD_POINT_OTHER_NAME")
    private String loadPointOtherName;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LOAD_DATE_PLAN")
    private Date loadDatePlan;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LOAD_DATE_FACT")
    private Date loadDateFact;

    @Column(name = "LOAD_PRODUCT_COUNT")
    private Double loadProductCount;

    @Column(name = "LOAD_PRODUCT_VOLUME")
    private Double loadProductVolume;

    @Column(name = "UNLOAD_POINT_TYPE", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean unloadPointType;

    @Column(name = "UNLOAD_CONTACTS")
    private String unloadContacts;

    @Column(name = "UNLOAD_DESCRIPTION")
    private String unloadDescription;

    @Column(name = "UNLOAD_POINT_OTHER_NAME")
    private String unloadPointOtherName;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UNLOAD_DATE_PLAN")
    private Date unloadDatePlan;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UNLOAD_DATE_FACT")
    private Date unloadDateFact;

    @Column(name = "UNLOAD_PRODUCT_COUNT")
    private Double unloadProductCount;

    @Column(name = "UNLOAD_PRODUCT_VOLUME")
    private Double unloadProductVolume;

    @Column(name = "TRANSPORT_COST_PLAN")
    private Double transportCostPlan;

    @Column(name = "TRANSPORT_COST_FACT")
    private Double transportCostFact;

    @Column(name = "TRANSPORT_PRICE_PLAN")
    private Double transportPricePlan;

    @Column(name = "TRANSPORT_PRICE_FACT")
    private Double transportPriceFact;

    @Column(name = "PRODUCT_COST_PLAN")
    private Double productCostPlan;

    @Column(name = "PRODUCT_COST_FACT")
    private Double productCostFact;

    @Column(name = "PRODUCT_PRICE_PLAN")
    private Double productPricePlan;

    @Column(name = "PRODUCT_PRICE_FACT")
    private Double productPriceFact;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ORDER_PAYMENT_DATE_PLAN")
    private Date orderPaymentDatePlan;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ORDER_PAYMENT_DATE_FACT")
    private Date orderPaymentDateFact;

    @Column(name = "NUMBER_OF_VEHICLES")
    private Integer numberOfVehicles;

    @Column(name = "VEHICLE_VOLUME")
    private Double vehicleVolume;

    /* Encapsulate User for CREATOR */

    @ManyToOne
    @Cascade(CascadeType.PERSIST)
    @JoinColumn(name = "CREATOR_ID")
    private User creator;

    @Column(name = "CREATOR_USERNAME")
    private String creatorUsername;

    @Column(name = "CREATOR_FIRST_NAME")
    private String creatorFirstName;

    @Column(name = "CREATOR_LAST_NAME")
    private String creatorLastName;

    @Column(name = "CREATOR_PATRONYMIC")
    private String creatorPatronymic;

    @Column(name = "CREATOR_TITLE")
    private String creatorTitle;

    /* Encapsulate User for OWNER */

    @ManyToOne
    @Cascade(CascadeType.PERSIST)
    @JoinColumn(name = "OWNER_ID")
    private User owner;

    @Column(name = "OWNER_USERNAME")
    private String ownerUsername;

    @Column(name = "OWNER_FIRST_NAME")
    private String ownerFirstName;

    @Column(name = "OWNER_LAST_NAME")
    private String ownerLastName;

    @Column(name = "OWNER_PATRONYMIC")
    private String ownerPatronymic;

    @Column(name = "OWNER_TITLE")
    private String ownerTitle;

    /* Encapsulate CUSTOMER */

    @ManyToOne
    @Cascade(CascadeType.PERSIST)
    @JoinColumn(name = "CUSTOMER_ID")
    private Customer customer;

    @Column(name = "CUSTOMER_TITLE")
    private String customerTitle;

    @Column(name = "CUSTOMER_CONTACTS")
    private String customerContacts;

    /* Encapsulate CONTRACTOR */

    @ManyToOne
    @Cascade(CascadeType.PERSIST)
    @JoinColumn(name = "CONTRACTOR_ID")
    private Contractor contractor;

    @Column(name = "CONTRACTOR_NAME_FULL")
    private String contractorNameFull;

    @Column(name = "CONTRACTOR_NAME_SHORT")
    private String contractorNameShort;

    @Column(name = "CONTRACTOR_INN")
    private String contractorInn;

    @Column(name = "CONTRACTOR_KPP")
    private String contractorKpp;

    @Column(name = "CONTRACTOR_OKPO")
    private String contractorOkpo;

    @Column(name = "CONTRACTOR_OGRN")
    private String contractorOgrn;

    @Column(name = "CONTRACTOR_OKATO")
    private String contractorOkato;

    @Column(name = "CONTRACTOR_NAME_WORK")
    private String contractorNameWork;

    /* Encapsulate CONTRACTORS_ORGANIZATION_TYPE */

    @Column(name = "CONTRACTOR_ORGANIZATION_TYPE_ID")
    private Long contractorOrganizationTypeId;

    @Column(name = "CONTRACTOR_ORGANIZATION_TYPE_NAME_FULL")
    private String contractorOrganizationTypeNameFull;

    @Column(name = "CONTRACTOR_ORGANIZATION_TYPE_NAME_SHORT")
    private String contractorOrganizationTypeNameShort;

    /* Encapsulate CONTRACTORS_AGENCY_TYPES */

    @Column(name = "CONTRACTOR_AGENCY_TYPE_ID")
    private Long contractorAgencyTypeId;

    @Column(name = "CONTRACTOR_AGENCY_TYPE")
    private String contractorAgencyType;

    @Column(name = "CONTRACTOR_AGENCY_TYPE_NAME")
    private String contractorAgencyTypeName;

    /* Encapsulate CONTRACTOR for transportation */

    @ManyToOne
    @Cascade(CascadeType.PERSIST)
    @JoinColumn(name = "TRANSPORTATION_CONTRACTOR_ID")
    private Contractor transportationContractor;

    @Column(name = "TRANSPORTATION_CONTRACTOR_NAME_FULL")
    private String transportationContractorNameFull;

    @Column(name = "TRANSPORTATION_CONTRACTOR_NAME_SHORT")
    private String transportationContractorNameShort;

    @Column(name = "TRANSPORTATION_CONTRACTOR_INN")
    private String transportationContractorInn;

    @Column(name = "TRANSPORTATION_CONTRACTOR_KPP")
    private String transportationContractorKpp;

    @Column(name = "TRANSPORTATION_CONTRACTOR_OKPO")
    private String transportationContractorOkpo;

    @Column(name = "TRANSPORTATION_CONTRACTOR_OGRN")
    private String transportationContractorOgrn;

    @Column(name = "TRANSPORTATION_CONTRACTOR_OKATO")
    private String transportationContractorOkato;

    @Column(name = "TRANSPORTATION_CONTRACTOR_NAME_WORK")
    private String transportationContractorNameWork;

    /* Encapsulate CONTRACTORS_ORGANIZATION_TYPE for transportation */

    @Column(name = "TRANSPORTATION_CONTRACTOR_ORGANIZATION_TYPE_ID")
    private Long transportationContractorOrganizationTypeId;

    @Column(name = "TRANSPORTATION_CONTRACTOR_ORGANIZATION_TYPE_NAME_FULL")
    private String transportationContractorOrganizationTypeNameFull;

    @Column(name = "TRANSPORTATION_CONTRACTOR_ORGANIZATION_TYPE_NAME_SHORT")
    private String transportationContractorOrganizationTypeNameShort;

    /* Encapsulate CONTRACTORS_AGENCY_TYPES for transportation */

    @Column(name = "TRANSPORTATION_CONTRACTOR_AGENCY_TYPE_ID")
    private Long transportationContractorAgencyTypeId;

    @Column(name = "TRANSPORTATION_CONTRACTOR_AGENCY_TYPE")
    private String transportationContractorAgencyType;

    @Column(name = "TRANSPORTATION_CONTRACTOR_AGENCY_TYPE_NAME")
    private String transportationContractorAgencyTypeName;

    /* Encapsulate COMPANY for seller */

    @ManyToOne
    @Cascade(CascadeType.PERSIST)
    @JoinColumn(name = "COMPANY_ID")
    private Contractor company;

    @Column(name = "COMPANY_NAME_FULL")
    private String companyNameFull;

    @Column(name = "COMPANY_NAME_SHORT")
    private String companyNameShort;

    @Column(name = "COMPANY_INN")
    private String companyInn;

    @Column(name = "COMPANY_KPP")
    private String companyKpp;

    @Column(name = "COMPANY_OKPO")
    private String companyOkpo;

    @Column(name = "COMPANY_OGRN")
    private String companyOgrn;

    @Column(name = "COMPANY_OKATO")
    private String companyOkato;

    @Column(name = "COMPANY_NAME_WORK")
    private String companyNameWork;

    /* Encapsulate COMPANY_ORGANIZATION_TYPE */

    @Column(name = "COMPANY_ORGANIZATION_TYPE_ID")
    private Long companyOrganizationTypeId;

    @Column(name = "COMPANY_ORGANIZATION_TYPE_NAME_FULL")
    private String companyOrganizationTypeNameFull;

    @Column(name = "COMPANY_ORGANIZATION_TYPE_NAME_SHORT")
    private String companyOrganizationTypeNameShort;

    /* Encapsulate COMPANY_AGENCY_TYPES */

    @Column(name = "COMPANY_AGENCY_TYPE_ID")
    private Long companyAgencyTypeId;

    @Column(name = "COMPANY_AGENCY_TYPE")
    private String companyAgencyType;

    @Column(name = "COMPANY_AGENCY_TYPE_NAME")
    private String companyAgencyTypeName;

    /* Encapsulate seller COMPANY for transportation */

    @ManyToOne
    @Cascade(CascadeType.PERSIST)
    @JoinColumn(name = "TRANSPORTATION_COMPANY_ID")
    private Contractor transportationCompany;

    @Column(name = "TRANSPORTATION_COMPANY_NAME_FULL")
    private String transportationCompanyNameFull;

    @Column(name = "TRANSPORTATION_COMPANY_NAME_SHORT")
    private String transportationCompanyNameShort;

    @Column(name = "TRANSPORTATION_COMPANY_INN")
    private String transportationCompanyInn;

    @Column(name = "TRANSPORTATION_COMPANY_KPP")
    private String transportationCompanyKpp;

    @Column(name = "TRANSPORTATION_COMPANY_OKPO")
    private String transportationCompanyOkpo;

    @Column(name = "TRANSPORTATION_COMPANY_OGRN")
    private String transportationCompanyOgrn;

    @Column(name = "TRANSPORTATION_COMPANY_OKATO")
    private String transportationCompanyOkato;

    @Column(name = "TRANSPORTATION_COMPANY_NAME_WORK")
    private String transportationCompanyNameWork;

    /* Encapsulate seller COMPANY_ORGANIZATION_TYPE for transportation */

    @Column(name = "TRANSPORTATION_COMPANY_ORGANIZATION_TYPE_ID")
    private Long transportationCompanyOrganizationTypeId;

    @Column(name = "TRANSPORTATION_COMPANY_ORGANIZATION_TYPE_NAME_FULL")
    private String transportationCompanyOrganizationTypeNameFull;

    @Column(name = "TRANSPORTATION_COMPANY_ORGANIZATION_TYPE_NAME_SHORT")
    private String transportationCompanyOrganizationTypeNameShort;

    /* Encapsulate seller COMPANY_AGENCY_TYPES for transportation */

    @Column(name = "TRANSPORTATION_COMPANY_AGENCY_TYPE_ID")
    private Long transportationCompanyAgencyTypeId;

    @Column(name = "TRANSPORTATION_COMPANY_AGENCY_TYPE")
    private String transportationCompanyAgencyType;

    @Column(name = "TRANSPORTATION_COMPANY_AGENCY_TYPE_NAME")
    private String transportationCompanyAgencyTypeName;

    /* Encapsulate CARRIER */

    @ManyToOne
    @Cascade(CascadeType.PERSIST)
    @JoinColumn(name = "CARRIER_ID")
    private Contractor carrier;

    @Column(name = "CARRIER_NAME_FULL")
    private String carrierNameFull;

    @Column(name = "CARRIER_NAME_SHORT")
    private String carrierNameShort;

    @Column(name = "CARRIER_INN")
    private String carrierInn;

    @Column(name = "CARRIER_KPP")
    private String carrierKpp;

    @Column(name = "CARRIER_OKPO")
    private String carrierOkpo;

    @Column(name = "CARRIER_OGRN")
    private String carrierOgrn;

    @Column(name = "CARRIER_OKATO")
    private String carrierOkato;

    @Column(name = "CARRIER_NAME_WORK")
    private String carrierNameWork;

    /* Encapsulate CARRIER_ORGANIZATION_TYPE */

    @Column(name = "CARRIER_ORGANIZATION_TYPE_ID")
    private Long carrierOrganizationTypeId;

    @Column(name = "CARRIER_ORGANIZATION_TYPE_NAME_FULL")
    private String carrierOrganizationTypeNameFull;

    @Column(name = "CARRIER_ORGANIZATION_TYPE_NAME_SHORT")
    private String carrierOrganizationTypeNameShort;

    /* Encapsulate CARRIER_AGENCY_TYPES */

    @Column(name = "CARRIER_AGENCY_TYPE_ID")
    private Long carrierAgencyTypeId;

    @Column(name = "CARRIER_AGENCY_TYPE")
    private String carrierAgencyType;

    @Column(name = "CARRIER_AGENCY_TYPE_NAME")
    private String carrierAgencyTypeName;

    /* Encapsulate PROVIDER */

    @ManyToOne
    @Cascade(CascadeType.PERSIST)
    @JoinColumn(name = "PROVIDER_ID")
    private Contractor provider;

    @Column(name = "PROVIDER_NAME_FULL")
    private String providerNameFull;

    @Column(name = "PROVIDER_NAME_SHORT")
    private String providerNameShort;

    @Column(name = "PROVIDER_INN")
    private String providerInn;

    @Column(name = "PROVIDER_KPP")
    private String providerKpp;

    @Column(name = "PROVIDER_OKPO")
    private String providerOkpo;

    @Column(name = "PROVIDER_OGRN")
    private String providerOgrn;

    @Column(name = "PROVIDER_OKATO")
    private String providerOkato;

    @Column(name = "PROVIDER_NAME_WORK")
    private String providerNameWork;

    /* Encapsulate PROVIDER_ORGANIZATION_TYPE */

    @Column(name = "PROVIDER_ORGANIZATION_TYPE_ID")
    private Long providerOrganizationTypeId;

    @Column(name = "PROVIDER_ORGANIZATION_TYPE_NAME_FULL")
    private String providerOrganizationTypeNameFull;

    @Column(name = "PROVIDER_ORGANIZATION_TYPE_NAME_SHORT")
    private String providerOrganizationTypeNameShort;

    /* Encapsulate PROVIDER_AGENCY_TYPES */

    @Column(name = "PROVIDER_AGENCY_TYPE_ID")
    private Long providerAgencyTypeId;

    @Column(name = "PROVIDER_AGENCY_TYPE")
    private String providerAgencyType;

    @Column(name = "PROVIDER_AGENCY_TYPE_NAME")
    private String providerAgencyTypeName;

    /* Encapsulate PRODUCTS */

    @ManyToOne
    @Cascade(CascadeType.PERSIST)
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @Column(name = "PRODUCT_NAME_FULL")
    private String productNameFull;

    @Column(name = "PRODUCT_NAME_SHORT")
    private String productNameShort;

    @Column(name = "PRODUCT_CODE")
    private String productCode;

    @Column(name = "PRODUCT_TYPE")
    private Long productType;

    @Column(name = "PRODUCT_CONTRACT_PRICE")
    private boolean productContractPrice;

    @Column(name = "PRODUCT_BALANCE")
    private String productBalance;

    @Column(name = "PRODUCT_MARK_UP")
    private Double productMarkUp;

    /* Encapsulate PRODUCTS_TYPES */

    @Column(name = "PRODUCT_TYPE_ID")
    private Long productTypeId;

    @Column(name = "PRODUCT_TYPE_NAME_FULL")
    private String productTypeNameFull;

    @Column(name = "PRODUCT_TYPE_NAME_WORK")
    private String productTypeNameWork;

    @Column(name = "PRODUCT_TYPE_DENSITY")
    private Double productTypeDensity;

    /* Encapsulate PRODUCTS_GROUPS */

    @ManyToOne
    @Cascade(CascadeType.PERSIST)
    @JoinColumn(name = "PRODUCT_GROUP_ID")
    private ProductGroup productGroup;

    @Column(name = "PRODUCT_GROUP_TITLE")
    private String productGroupTitle;

    /* Encapsulate MEASURE_UNITS */

    @Column(name = "MEASURE_UNIT_ID")
    private Long measureUnitId;

    @Column(name = "MEASURE_UNIT_NAME_FULL")
    private String measureUnitNameFull;

    @Column(name = "MEASURE_UNIT_NAME_SHORT")
    private String measureUnitNameShort;

    @Column(name = "MEASURE_UNIT_CODE")
    private String measureUnitCode;

    /* Encapsulate PRODUCTS_PRICE */

    @Column(name = "PRODUCT_PRICE_ID")
    private Long productPriceId;

    @Column(name = "PRODUCT_PRICE")
    private Double productPrice;

    /* Encapsulate SHIPMENT_BASIS */

    @ManyToOne
    @Cascade(CascadeType.PERSIST)
    @JoinColumn(name = "SHIPMENT_BASE_ID")
    private ShipmentBasis shipmentBase;

    @Column(name = "SHIPMENT_BASE_NAME_FULL")
    private String shipmentBaseNameFull;

    @Column(name = "SHIPMENT_BASE_NAME_SHORT")
    private String shipmentBaseNameShort;

    @Column(name = "SHIPMENT_BASE_ADDRESS")
    private String shipmentBaseAddress;

    @Column(name = "SHIPMENT_BASE_LATITUDE")
    private Double shipmentBaseLongitude;

    @Column(name = "SHIPMENT_BASE_LONGITUDE")
    private Double shipmentBaseLatitude;

    /* Encapsulate ORDER_PAYMENT_TYPES */

    @ManyToOne
    @Cascade(CascadeType.PERSIST)
    @JoinColumn(name = "ORDER_PAYMENT_TYPE_ID")
    private OrderPaymentType orderPaymentType;

    @Column(name = "ORDER_PAYMENT_TYPE")
    private String orderPaymentTypeType;

    @Column(name = "ORDER_PAYMENT_TYPE_NAME")
    private String orderPaymentTypeName;

    /* Encapsulate ORDER_STATUS_TYPES */

    @ManyToOne
    @Cascade(CascadeType.PERSIST)
    @JoinColumn(name = "ORDER_STATUS_TYPE_ID")
    private OrderStatusType orderStatusType;

    @Column(name = "ORDER_STATUS_ORDER")
    private Integer orderStatusTypeOrder;

    @Column(name = "ORDER_STATUS_TYPE")
    private String orderStatusTypeType;

    @Column(name = "ORDER_STATUS_TYPE_NAME")
    private String orderStatusTypeName;

    /* Encapsulate User for TRANSPORT_MANAGER */

    @ManyToOne
    @Cascade(CascadeType.PERSIST)
    @JoinColumn(name = "TRANSPORT_MANAGER_ID")
    private User transportManager;

    @Column(name = "TRANSPORT_MANAGER_USERNAME")
    private String transportManagerUsername;

    @Column(name = "TRANSPORT_MANAGER_FIRST_NAME")
    private String transportManagerFirstName;

    @Column(name = "TRANSPORT_MANAGER_LAST_NAME")
    private String transportManagerLastName;

    @Column(name = "TRANSPORT_MANAGER_PATRONYMIC")
    private String transportManagerPatronymic;

    @Column(name = "TRANSPORT_MANAGER_TITLE")
    private String transportManagerTitle;

    /* Encapsulate TRANSPORTATION_VEHICLE_TYPES */

    @ManyToOne
    @Cascade(CascadeType.PERSIST)
    @JoinColumn(name = "TRANSPORTATION_VEHICLE_TYPE_ID")
    private TransportationVehicleType transportationVehicleType;

    @Column(name = "TRANSPORTATION_VEHICLE_TYPE")
    private String transportationVehicleTypeType;

    @Column(name = "TRANSPORTATION_VEHICLE_TYPE_NAME")
    private String transportationVehicleTypeName;

    /* Encapsulate TRANSPORTATION_COMPANY_TYPES */

    @ManyToOne
    @Cascade(CascadeType.PERSIST)
    @JoinColumn(name = "TRANSPORTATION_COMPANY_TYPE_ID")
    private TransportationCompanyType transportationCompanyType;

    @Column(name = "TRANSPORTATION_COMPANY_TYPE")
    private String transportationCompanyTypeType;

    @Column(name = "TRANSPORTATION_COMPANY_TYPE_NAME")
    private String transportationCompanyTypeName;

    /* Encapsulate TRANSPORTATION_HIRED_VEHICLE_TYPES */

    @ManyToOne
    @Cascade(CascadeType.PERSIST)
    @JoinColumn(name = "TRANSPORTATION_HIRED_VEHICLE_TYPE_ID")
    private TransportationHiredVehicleType transportationHiredVehicleType;

    @Column(name = "TRANSPORTATION_HIRED_VEHICLE_TYPE")
    private String transportationHiredVehicleTypeType;

    @Column(name = "TRANSPORTATION_HIRED_VEHICLE_TYPE_NAME")
    private String transportationHiredVehicleTypeName;

    /* Encapsulate TRUCKS */

    @ManyToOne
    @Cascade(CascadeType.PERSIST)
    @JoinColumn(name = "TRUCK_ID")
    private Truck truck;

    @Column(name = "TRUCK_TITLE")
    private String truckTitle;

    @Column(name = "TRUCK_BRAND")
    private String truckBrand;

    @Column(name = "TRUCK_MODEL")
    private String truckModel;

    @Column(name = "TRUCK_GOV_NUMBER")
    private String truckGovNumber;

    /* Encapsulate TRAILERS */

    @ManyToOne
    @Cascade(CascadeType.PERSIST)
    @JoinColumn(name = "TRAILER_ID")
    private Trailer trailer;

    @Column(name = "TRAILER_TITLE")
    private String trailerTitle;

    @Column(name = "TRAILER_BRAND")
    private String trailerBrand;

    @Column(name = "TRAILER_MODEL")
    private String trailerModel;

    @Column(name = "TRAILER_GOV_NUMBER")
    private String trailerGovNumber;

    @Column(name = "TRAILER_CALIBRATION")
    private Double trailerCalibration;

    @Column(name = "TRAILER_CAPACITY")
    private Double trailerCapacity;

    @Column(name = "TRAILER_BOTTOM_LOADING")
    private Boolean trailerBottomLoading;

    @Column(name = "TRAILER_BOTTOM_LOADING_CALIBRATION")
    private Double trailerBottomLoadingCalibration;

    /* Encapsulate DRIVERS */

    @ManyToOne
    @Cascade(CascadeType.PERSIST)
    @JoinColumn(name = "DRIVER_ID")
    private Driver driver;

    @Column(name = "DRIVER_TITLE")
    private String driverTitle;

    @Column(name = "DRIVER_FIRST_NAME")
    private String driverFirstName;

    @Column(name = "DRIVER_LAST_NAME")
    private String driverLastName;

    @Column(name = "DRIVER_PATRONYMIC")
    private String driverPatronymic;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DRIVER_DATE_OF_BIRTH")
    private Date driverDateOfBirth;

    @Column(name = "DRIVER_DOC_SERIAL")
    private String driverDocSerial;

    @Column(name = "DRIVER_DOC_NUMBER")
    private String driverDocNumber;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DRIVER_DOC_GIVEN_DATE")
    private Date driverDocGivenDate;

    @Column(name = "DRIVER_DOC_GIVEN_FROM")
    private String driverDocGivenFrom;

    @Column(name = "DRIVER_DOC_CODE")
    private String driverDocCode;

    @Column(name = "DRIVER_DOC_DESCRIPTION")
    private String driverDocDescription;

    @Column(name = "DRIVER_PHONE_1")
    private String driverPhone1;

    @Column(name = "DRIVER_PHONE_2")
    private String driverPhone2;

    @Column(name = "DRIVER_PHONE_EMERGENCY")
    private String driverPhoneEmergency;

    /* Encapsulate VEHICLES */

    @ManyToOne
    @Cascade(CascadeType.PERSIST)
    @JoinColumn(name = "VEHICLE_ID")
    private Vehicle vehicle;

    @Column(name = "VEHICLE_DESCRIPTION")
    private String vehicleDescription;

    /* Encapsulate VEHICLE_STATUSES */

    @Column(name = "VEHICLE_STATUS_ID")
    private Long vehicleStatusId;

    @Column(name = "VEHICLE_STATUS_TITLE")
    private String vehicleStatusTitle;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "VEHICLE_STATUS_CREATED")
    private Date vehicleStatusCreated;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "VEHICLE_STATUS_STATUS_DATE")
    private Date vehicleStatusStatusDate;

    @Column(name = "VEHICLE_STATUS_DESCRIPTION")
    private String vehicleStatusDescription;

    /* Encapsulate AGREEMENTS */

    @ManyToOne
    @Cascade(CascadeType.PERSIST)
    @JoinColumn(name = "AGREEMENT_ID")
    private Agreement agreement;

    @Column(name = "AGREEMENT_NUMBER")
    private String agreementNumber;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "AGREEMENT_CREATED", nullable = false)
    private Date agreementCreated;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "AGREEMENT_VALID_FROM", nullable = false)
    private Date agreementValidFrom;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "AGREEMENT_VALID_TO", nullable = false)
    private Date agreementValidTo;

    @Column(name = "AGREEMENT_DESCRIPTION")
    private String agreementDescription;

    /* Encapsulate AGREEMENT_STATUSES */

    @Column(name = "AGREEMENT_STATUS_ID")
    private Long agreementStatusId;

    @Column(name = "AGREEMENT_STATUS_TYPE")
    private String agreementStatusType;

    @Column(name = "AGREEMENT_STATUS_NAME")
    private String agreementStatusName;

    /* Encapsulate AGREEMENT_TYPES */

    @Column(name = "AGREEMENT_TYPE_ID")
    private Long agreementTypeId;

    @Column(name = "AGREEMENT_TYPE")
    private String agreementType;

    @Column(name = "AGREEMENT_TYPE_NAME")
    private String agreementTypeName;

    /* Encapsulate AGREEMENTS for transportation */

    @ManyToOne
    @Cascade(CascadeType.PERSIST)
    @JoinColumn(name = "TRANSPORTATION_AGREEMENT_ID")
    private Agreement transportationAgreement;

    @Column(name = "TRANSPORTATION_AGREEMENT_NUMBER")
    private String transportationAgreementNumber;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TRANSPORTATION_AGREEMENT_CREATED", nullable = false)
    private Date transportationAgreementCreated;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TRANSPORTATION_AGREEMENT_VALID_FROM", nullable = false)
    private Date transportationAgreementValidFrom;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TRANSPORTATION_AGREEMENT_VALID_TO", nullable = false)
    private Date transportationAgreementValidTo;

    @Column(name = "TRANSPORTATION_AGREEMENT_DESCRIPTION")
    private String transportationAgreementDescription;

    /* Encapsulate AGREEMENT_STATUSES for transportation */

    @Column(name = "TRANSPORTATION_AGREEMENT_STATUS_ID")
    private Long transportationAgreementStatusId;

    @Column(name = "TRANSPORTATION_AGREEMENT_STATUS_TYPE")
    private String transportationAgreementStatusType;

    @Column(name = "TRANSPORTATION_AGREEMENT_STATUS_NAME")
    private String transportationAgreementStatusName;

    /* Encapsulate AGREEMENT_TYPES for transportation */

    @Column(name = "TRANSPORTATION_AGREEMENT_TYPE_ID")
    private Long transportationAgreementTypeId;

    @Column(name = "TRANSPORTATION_AGREEMENT_TYPE")
    private String transportationAgreementType;

    @Column(name = "TRANSPORTATION_AGREEMENT_TYPE_NAME")
    private String transportationAgreementTypeName;

    /* Encapsulate AGREEMENTS for additional agreements */

    @ManyToOne
    @Cascade(CascadeType.PERSIST)
    @JoinColumn(name = "ADDITIONAL_AGREEMENT_ID")
    private Agreement additionalAgreement;

    @Column(name = "ADDITIONAL_AGREEMENT_NUMBER")
    private String additionalAgreementNumber;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ADDITIONAL_AGREEMENT_CREATED", nullable = false)
    private Date additionalAgreementCreated;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ADDITIONAL_AGREEMENT_VALID_FROM", nullable = false)
    private Date additionalAgreementValidFrom;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ADDITIONAL_AGREEMENT_VALID_TO", nullable = false)
    private Date additionalAgreementValidTo;

    @Column(name = "ADDITIONAL_AGREEMENT_DESCRIPTION")
    private String additionalAgreementDescription;

    /* Encapsulate AGREEMENT_STATUSES for additional agreements */

    @ManyToOne
    @Cascade(CascadeType.PERSIST)
    @JoinColumn(name = "ADDITIONAL_AGREEMENT_STATUS_ID")
    private AgreementStatus additionalAgreementStatus;

    @Column(name = "ADDITIONAL_AGREEMENT_STATUS_TYPE")
    private String additionalAgreementStatusType;

    @Column(name = "ADDITIONAL_AGREEMENT_STATUS_NAME")
    private String additionalAgreementStatusName;

    /* Encapsulate AGREEMENT_TYPES for additional agreements */

    @Column(name = "ADDITIONAL_AGREEMENT_TYPE_ID")
    private Long additionalAgreementTypeId;

    @Column(name = "ADDITIONAL_AGREEMENT_TYPE")
    private String additionalAgreementType;

    @Column(name = "ADDITIONAL_AGREEMENT_TYPE_NAME")
    private String additionalAgreementTypeName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TELEGRAM_USER_ID")
    private TelegramUser telegramUser;

    @Column(name = "TELEGRAM_RECEIVED_ALREADY", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean telegramReceivedAlready;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "orders")
    private Set<Drive> drives;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Order))
            return false;
        Order other = (Order) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }


    /*  --- GETTER/SETTER ---  */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getProductCountPlan() {
        return productCountPlan;
    }

    public void setProductCountPlan(Double productCountPlan) {
        this.productCountPlan = productCountPlan;
    }

    public Double getProductVolumePlan() {
        return productVolumePlan;
    }

    public void setProductVolumePlan(Double productVolumePlan) {
        this.productVolumePlan = productVolumePlan;
    }

    public Double getProductCountFact() {
        return productCountFact;
    }

    public void setProductCountFact(Double productCountFact) {
        this.productCountFact = productCountFact;
    }

    public Double getProductVolumeFact() {
        return productVolumeFact;
    }

    public void setProductVolumeFact(Double productVolumeFact) {
        this.productVolumeFact = productVolumeFact;
    }

    public boolean isStateNew() {
        return stateNew;
    }

    public void setStateNew(boolean stateNew) {
        this.stateNew = stateNew;
    }

    public boolean isStateDelete() {
        return stateDelete;
    }

    public void setStateDelete(boolean stateDelete) {
        this.stateDelete = stateDelete;
    }

    public boolean isStateCancelled() {
        return stateCancelled;
    }

    public void setStateCancelled(boolean stateCancelled) {
        this.stateCancelled = stateCancelled;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLoadClaimDate() {
        return loadClaimDate;
    }

    public void setLoadClaimDate(Date loadClaimDate) {
        this.loadClaimDate = loadClaimDate;
    }

    public Date getClosedDate() {
        return closedDate;
    }

    public void setClosedDate(Date closedDate) {
        this.closedDate = closedDate;
    }

    public Date getCancelledDate() {
        return cancelledDate;
    }

    public void setCancelledDate(Date cancelledDate) {
        this.cancelledDate = cancelledDate;
    }

    public boolean isLoadPointType() {
        return loadPointType;
    }

    public void setLoadPointType(boolean loadPointType) {
        this.loadPointType = loadPointType;
    }

    public String getLoadContacts() {
        return loadContacts;
    }

    public void setLoadContacts(String loadContacts) {
        this.loadContacts = loadContacts;
    }

    public String getLoadDescription() {
        return loadDescription;
    }

    public void setLoadDescription(String loadDescription) {
        this.loadDescription = loadDescription;
    }

    public String getLoadPointOtherName() {
        return loadPointOtherName;
    }

    public void setLoadPointOtherName(String loadPointOtherName) {
        this.loadPointOtherName = loadPointOtherName;
    }

    public Date getLoadDatePlan() {
        return loadDatePlan;
    }

    public void setLoadDatePlan(Date loadDatePlan) {
        this.loadDatePlan = loadDatePlan;
    }

    public Date getLoadDateFact() {
        return loadDateFact;
    }

    public void setLoadDateFact(Date loadDateFact) {
        this.loadDateFact = loadDateFact;
    }

    public Double getLoadProductCount() {
        return loadProductCount;
    }

    public void setLoadProductCount(Double loadProductCount) {
        this.loadProductCount = loadProductCount;
    }

    public Double getLoadProductVolume() {
        return loadProductVolume;
    }

    public void setLoadProductVolume(Double loadProductVolume) {
        this.loadProductVolume = loadProductVolume;
    }

    public boolean isUnloadPointType() {
        return unloadPointType;
    }

    public void setUnloadPointType(boolean unloadPointType) {
        this.unloadPointType = unloadPointType;
    }

    public String getUnloadContacts() {
        return unloadContacts;
    }

    public void setUnloadContacts(String unloadContacts) {
        this.unloadContacts = unloadContacts;
    }

    public String getUnloadDescription() {
        return unloadDescription;
    }

    public void setUnloadDescription(String unloadDescription) {
        this.unloadDescription = unloadDescription;
    }

    public String getUnloadPointOtherName() {
        return unloadPointOtherName;
    }

    public void setUnloadPointOtherName(String unloadPointOtherName) {
        this.unloadPointOtherName = unloadPointOtherName;
    }

    public Date getUnloadDatePlan() {
        return unloadDatePlan;
    }

    public void setUnloadDatePlan(Date unloadDatePlan) {
        this.unloadDatePlan = unloadDatePlan;
    }

    public Date getUnloadDateFact() {
        return unloadDateFact;
    }

    public void setUnloadDateFact(Date unloadDateFact) {
        this.unloadDateFact = unloadDateFact;
    }

    public Double getUnloadProductCount() {
        return unloadProductCount;
    }

    public void setUnloadProductCount(Double unloadProductCount) {
        this.unloadProductCount = unloadProductCount;
    }

    public Double getUnloadProductVolume() {
        return unloadProductVolume;
    }

    public void setUnloadProductVolume(Double unloadProductVolume) {
        this.unloadProductVolume = unloadProductVolume;
    }

    public Double getTransportCostPlan() {
        return transportCostPlan;
    }

    public void setTransportCostPlan(Double transportCostPlan) {
        this.transportCostPlan = transportCostPlan;
    }

    public Double getTransportCostFact() {
        return transportCostFact;
    }

    public void setTransportCostFact(Double transportCostFact) {
        this.transportCostFact = transportCostFact;
    }

    public Double getTransportPricePlan() {
        return transportPricePlan;
    }

    public void setTransportPricePlan(Double transportPricePlan) {
        this.transportPricePlan = transportPricePlan;
    }

    public Double getTransportPriceFact() {
        return transportPriceFact;
    }

    public void setTransportPriceFact(Double transportPriceFact) {
        this.transportPriceFact = transportPriceFact;
    }

    public Double getProductCostPlan() {
        return productCostPlan;
    }

    public void setProductCostPlan(Double productCostPlan) {
        this.productCostPlan = productCostPlan;
    }

    public Double getProductCostFact() {
        return productCostFact;
    }

    public void setProductCostFact(Double productCostFact) {
        this.productCostFact = productCostFact;
    }

    public Double getProductPricePlan() {
        return productPricePlan;
    }

    public void setProductPricePlan(Double productPricePlan) {
        this.productPricePlan = productPricePlan;
    }

    public Double getProductPriceFact() {
        return productPriceFact;
    }

    public void setProductPriceFact(Double productPriceFact) {
        this.productPriceFact = productPriceFact;
    }

    public Date getOrderPaymentDatePlan() {
        return orderPaymentDatePlan;
    }

    public void setOrderPaymentDatePlan(Date orderPaymentDatePlan) {
        this.orderPaymentDatePlan = orderPaymentDatePlan;
    }

    public Date getOrderPaymentDateFact() {
        return orderPaymentDateFact;
    }

    public void setOrderPaymentDateFact(Date orderPaymentDateFact) {
        this.orderPaymentDateFact = orderPaymentDateFact;
    }

    public Integer getNumberOfVehicles() {
        return numberOfVehicles;
    }

    public void setNumberOfVehicles(Integer numberOfVehicles) {
        this.numberOfVehicles = numberOfVehicles;
    }

    public Double getVehicleVolume() {
        return vehicleVolume;
    }

    public void setVehicleVolume(Double vehicleVolume) {
        this.vehicleVolume = vehicleVolume;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public String getCreatorUsername() {
        return creatorUsername;
    }

    public void setCreatorUsername(String creatorUsername) {
        this.creatorUsername = creatorUsername;
    }

    public String getCreatorFirstName() {
        return creatorFirstName;
    }

    public void setCreatorFirstName(String creatorFirstName) {
        this.creatorFirstName = creatorFirstName;
    }

    public String getCreatorLastName() {
        return creatorLastName;
    }

    public void setCreatorLastName(String creatorLastName) {
        this.creatorLastName = creatorLastName;
    }

    public String getCreatorPatronymic() {
        return creatorPatronymic;
    }

    public void setCreatorPatronymic(String creatorPatronymic) {
        this.creatorPatronymic = creatorPatronymic;
    }

    public String getCreatorTitle() {
        return creatorTitle;
    }

    public void setCreatorTitle(String creatorTitle) {
        this.creatorTitle = creatorTitle;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public void setOwnerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
    }

    public String getOwnerFirstName() {
        return ownerFirstName;
    }

    public void setOwnerFirstName(String ownerFirstName) {
        this.ownerFirstName = ownerFirstName;
    }

    public String getOwnerLastName() {
        return ownerLastName;
    }

    public void setOwnerLastName(String ownerLastName) {
        this.ownerLastName = ownerLastName;
    }

    public String getOwnerPatronymic() {
        return ownerPatronymic;
    }

    public void setOwnerPatronymic(String ownerPatronymic) {
        this.ownerPatronymic = ownerPatronymic;
    }

    public String getOwnerTitle() {
        return ownerTitle;
    }

    public void setOwnerTitle(String ownerTitle) {
        this.ownerTitle = ownerTitle;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getCustomerTitle() {
        return customerTitle;
    }

    public void setCustomerTitle(String customerTitle) {
        this.customerTitle = customerTitle;
    }

    public String getCustomerContacts() {
        return customerContacts;
    }

    public void setCustomerContacts(String customerContacts) {
        this.customerContacts = customerContacts;
    }

    public Contractor getContractor() {
        return contractor;
    }

    public void setContractor(Contractor contractor) {
        this.contractor = contractor;
    }

    public String getContractorNameFull() {
        return contractorNameFull;
    }

    public void setContractorNameFull(String contractorNameFull) {
        this.contractorNameFull = contractorNameFull;
    }

    public String getContractorNameShort() {
        return contractorNameShort;
    }

    public void setContractorNameShort(String contractorNameShort) {
        this.contractorNameShort = contractorNameShort;
    }

    public String getContractorInn() {
        return contractorInn;
    }

    public void setContractorInn(String contractorInn) {
        this.contractorInn = contractorInn;
    }

    public String getContractorKpp() {
        return contractorKpp;
    }

    public void setContractorKpp(String contractorKpp) {
        this.contractorKpp = contractorKpp;
    }

    public String getContractorOkpo() {
        return contractorOkpo;
    }

    public void setContractorOkpo(String contractorOkpo) {
        this.contractorOkpo = contractorOkpo;
    }

    public String getContractorOgrn() {
        return contractorOgrn;
    }

    public void setContractorOgrn(String contractorOgrn) {
        this.contractorOgrn = contractorOgrn;
    }

    public String getContractorOkato() {
        return contractorOkato;
    }

    public void setContractorOkato(String contractorOkato) {
        this.contractorOkato = contractorOkato;
    }

    public String getContractorNameWork() {
        return contractorNameWork;
    }

    public void setContractorNameWork(String contractorNameWork) {
        this.contractorNameWork = contractorNameWork;
    }

    public Long getContractorOrganizationTypeId() {
        return contractorOrganizationTypeId;
    }

    public void setContractorOrganizationTypeId(Long contractorOrganizationTypeId) {
        this.contractorOrganizationTypeId = contractorOrganizationTypeId;
    }

    public String getContractorOrganizationTypeNameFull() {
        return contractorOrganizationTypeNameFull;
    }

    public void setContractorOrganizationTypeNameFull(String contractorOrganizationTypeNameFull) {
        this.contractorOrganizationTypeNameFull = contractorOrganizationTypeNameFull;
    }

    public String getContractorOrganizationTypeNameShort() {
        return contractorOrganizationTypeNameShort;
    }

    public void setContractorOrganizationTypeNameShort(String contractorOrganizationTypeNameShort) {
        this.contractorOrganizationTypeNameShort = contractorOrganizationTypeNameShort;
    }

    public Long getContractorAgencyTypeId() {
        return contractorAgencyTypeId;
    }

    public void setContractorAgencyTypeId(Long contractorAgencyTypeId) {
        this.contractorAgencyTypeId = contractorAgencyTypeId;
    }

    public String getContractorAgencyType() {
        return contractorAgencyType;
    }

    public void setContractorAgencyType(String contractorAgencyType) {
        this.contractorAgencyType = contractorAgencyType;
    }

    public String getContractorAgencyTypeName() {
        return contractorAgencyTypeName;
    }

    public void setContractorAgencyTypeName(String contractorAgencyTypeName) {
        this.contractorAgencyTypeName = contractorAgencyTypeName;
    }

    public Contractor getTransportationContractor() {
        return transportationContractor;
    }

    public void setTransportationContractor(Contractor transportationContractor) {
        this.transportationContractor = transportationContractor;
    }

    public String getTransportationContractorNameFull() {
        return transportationContractorNameFull;
    }

    public void setTransportationContractorNameFull(String transportationContractorNameFull) {
        this.transportationContractorNameFull = transportationContractorNameFull;
    }

    public String getTransportationContractorNameShort() {
        return transportationContractorNameShort;
    }

    public void setTransportationContractorNameShort(String transportationContractorNameShort) {
        this.transportationContractorNameShort = transportationContractorNameShort;
    }

    public String getTransportationContractorInn() {
        return transportationContractorInn;
    }

    public void setTransportationContractorInn(String transportationContractorInn) {
        this.transportationContractorInn = transportationContractorInn;
    }

    public String getTransportationContractorKpp() {
        return transportationContractorKpp;
    }

    public void setTransportationContractorKpp(String transportationContractorKpp) {
        this.transportationContractorKpp = transportationContractorKpp;
    }

    public String getTransportationContractorOkpo() {
        return transportationContractorOkpo;
    }

    public void setTransportationContractorOkpo(String transportationContractorOkpo) {
        this.transportationContractorOkpo = transportationContractorOkpo;
    }

    public String getTransportationContractorOgrn() {
        return transportationContractorOgrn;
    }

    public void setTransportationContractorOgrn(String transportationContractorOgrn) {
        this.transportationContractorOgrn = transportationContractorOgrn;
    }

    public String getTransportationContractorOkato() {
        return transportationContractorOkato;
    }

    public void setTransportationContractorOkato(String transportationContractorOkato) {
        this.transportationContractorOkato = transportationContractorOkato;
    }

    public String getTransportationContractorNameWork() {
        return transportationContractorNameWork;
    }

    public void setTransportationContractorNameWork(String transportationContractorNameWork) {
        this.transportationContractorNameWork = transportationContractorNameWork;
    }

    public Long getTransportationContractorOrganizationTypeId() {
        return transportationContractorOrganizationTypeId;
    }

    public void setTransportationContractorOrganizationTypeId(Long transportationContractorOrganizationTypeId) {
        this.transportationContractorOrganizationTypeId = transportationContractorOrganizationTypeId;
    }

    public String getTransportationContractorOrganizationTypeNameFull() {
        return transportationContractorOrganizationTypeNameFull;
    }

    public void setTransportationContractorOrganizationTypeNameFull(String transportationContractorOrganizationTypeNameFull) {
        this.transportationContractorOrganizationTypeNameFull = transportationContractorOrganizationTypeNameFull;
    }

    public String getTransportationContractorOrganizationTypeNameShort() {
        return transportationContractorOrganizationTypeNameShort;
    }

    public void setTransportationContractorOrganizationTypeNameShort(String transportationContractorOrganizationTypeNameShort) {
        this.transportationContractorOrganizationTypeNameShort = transportationContractorOrganizationTypeNameShort;
    }

    public Long getTransportationContractorAgencyTypeId() {
        return transportationContractorAgencyTypeId;
    }

    public void setTransportationContractorAgencyTypeId(Long transportationContractorAgencyTypeId) {
        this.transportationContractorAgencyTypeId = transportationContractorAgencyTypeId;
    }

    public String getTransportationContractorAgencyType() {
        return transportationContractorAgencyType;
    }

    public void setTransportationContractorAgencyType(String transportationContractorAgencyType) {
        this.transportationContractorAgencyType = transportationContractorAgencyType;
    }

    public String getTransportationContractorAgencyTypeName() {
        return transportationContractorAgencyTypeName;
    }

    public void setTransportationContractorAgencyTypeName(String transportationContractorAgencyTypeName) {
        this.transportationContractorAgencyTypeName = transportationContractorAgencyTypeName;
    }

    public Contractor getCompany() {
        return company;
    }

    public void setCompany(Contractor company) {
        this.company = company;
    }

    public String getCompanyNameFull() {
        return companyNameFull;
    }

    public void setCompanyNameFull(String companyNameFull) {
        this.companyNameFull = companyNameFull;
    }

    public String getCompanyNameShort() {
        return companyNameShort;
    }

    public void setCompanyNameShort(String companyNameShort) {
        this.companyNameShort = companyNameShort;
    }

    public String getCompanyInn() {
        return companyInn;
    }

    public void setCompanyInn(String companyInn) {
        this.companyInn = companyInn;
    }

    public String getCompanyKpp() {
        return companyKpp;
    }

    public void setCompanyKpp(String companyKpp) {
        this.companyKpp = companyKpp;
    }

    public String getCompanyOkpo() {
        return companyOkpo;
    }

    public void setCompanyOkpo(String companyOkpo) {
        this.companyOkpo = companyOkpo;
    }

    public String getCompanyOgrn() {
        return companyOgrn;
    }

    public void setCompanyOgrn(String companyOgrn) {
        this.companyOgrn = companyOgrn;
    }

    public String getCompanyOkato() {
        return companyOkato;
    }

    public void setCompanyOkato(String companyOkato) {
        this.companyOkato = companyOkato;
    }

    public String getCompanyNameWork() {
        return companyNameWork;
    }

    public void setCompanyNameWork(String companyNameWork) {
        this.companyNameWork = companyNameWork;
    }

    public Long getCompanyOrganizationTypeId() {
        return companyOrganizationTypeId;
    }

    public void setCompanyOrganizationTypeId(Long companyOrganizationTypeId) {
        this.companyOrganizationTypeId = companyOrganizationTypeId;
    }

    public String getCompanyOrganizationTypeNameFull() {
        return companyOrganizationTypeNameFull;
    }

    public void setCompanyOrganizationTypeNameFull(String companyOrganizationTypeNameFull) {
        this.companyOrganizationTypeNameFull = companyOrganizationTypeNameFull;
    }

    public String getCompanyOrganizationTypeNameShort() {
        return companyOrganizationTypeNameShort;
    }

    public void setCompanyOrganizationTypeNameShort(String companyOrganizationTypeNameShort) {
        this.companyOrganizationTypeNameShort = companyOrganizationTypeNameShort;
    }

    public Long getCompanyAgencyTypeId() {
        return companyAgencyTypeId;
    }

    public void setCompanyAgencyTypeId(Long companyAgencyTypeId) {
        this.companyAgencyTypeId = companyAgencyTypeId;
    }

    public String getCompanyAgencyType() {
        return companyAgencyType;
    }

    public void setCompanyAgencyType(String companyAgencyType) {
        this.companyAgencyType = companyAgencyType;
    }

    public String getCompanyAgencyTypeName() {
        return companyAgencyTypeName;
    }

    public void setCompanyAgencyTypeName(String companyAgencyTypeName) {
        this.companyAgencyTypeName = companyAgencyTypeName;
    }

    public Contractor getTransportationCompany() {
        return transportationCompany;
    }

    public void setTransportationCompany(Contractor transportationCompany) {
        this.transportationCompany = transportationCompany;
    }

    public String getTransportationCompanyNameFull() {
        return transportationCompanyNameFull;
    }

    public void setTransportationCompanyNameFull(String transportationCompanyNameFull) {
        this.transportationCompanyNameFull = transportationCompanyNameFull;
    }

    public String getTransportationCompanyNameShort() {
        return transportationCompanyNameShort;
    }

    public void setTransportationCompanyNameShort(String transportationCompanyNameShort) {
        this.transportationCompanyNameShort = transportationCompanyNameShort;
    }

    public String getTransportationCompanyInn() {
        return transportationCompanyInn;
    }

    public void setTransportationCompanyInn(String transportationCompanyInn) {
        this.transportationCompanyInn = transportationCompanyInn;
    }

    public String getTransportationCompanyKpp() {
        return transportationCompanyKpp;
    }

    public void setTransportationCompanyKpp(String transportationCompanyKpp) {
        this.transportationCompanyKpp = transportationCompanyKpp;
    }

    public String getTransportationCompanyOkpo() {
        return transportationCompanyOkpo;
    }

    public void setTransportationCompanyOkpo(String transportationCompanyOkpo) {
        this.transportationCompanyOkpo = transportationCompanyOkpo;
    }

    public String getTransportationCompanyOgrn() {
        return transportationCompanyOgrn;
    }

    public void setTransportationCompanyOgrn(String transportationCompanyOgrn) {
        this.transportationCompanyOgrn = transportationCompanyOgrn;
    }

    public String getTransportationCompanyOkato() {
        return transportationCompanyOkato;
    }

    public void setTransportationCompanyOkato(String transportationCompanyOkato) {
        this.transportationCompanyOkato = transportationCompanyOkato;
    }

    public String getTransportationCompanyNameWork() {
        return transportationCompanyNameWork;
    }

    public void setTransportationCompanyNameWork(String transportationCompanyNameWork) {
        this.transportationCompanyNameWork = transportationCompanyNameWork;
    }

    public Long getTransportationCompanyOrganizationTypeId() {
        return transportationCompanyOrganizationTypeId;
    }

    public void setTransportationCompanyOrganizationTypeId(Long transportationCompanyOrganizationTypeId) {
        this.transportationCompanyOrganizationTypeId = transportationCompanyOrganizationTypeId;
    }

    public String getTransportationCompanyOrganizationTypeNameFull() {
        return transportationCompanyOrganizationTypeNameFull;
    }

    public void setTransportationCompanyOrganizationTypeNameFull(String transportationCompanyOrganizationTypeNameFull) {
        this.transportationCompanyOrganizationTypeNameFull = transportationCompanyOrganizationTypeNameFull;
    }

    public String getTransportationCompanyOrganizationTypeNameShort() {
        return transportationCompanyOrganizationTypeNameShort;
    }

    public void setTransportationCompanyOrganizationTypeNameShort(String transportationCompanyOrganizationTypeNameShort) {
        this.transportationCompanyOrganizationTypeNameShort = transportationCompanyOrganizationTypeNameShort;
    }

    public Long getTransportationCompanyAgencyTypeId() {
        return transportationCompanyAgencyTypeId;
    }

    public void setTransportationCompanyAgencyTypeId(Long transportationCompanyAgencyTypeId) {
        this.transportationCompanyAgencyTypeId = transportationCompanyAgencyTypeId;
    }

    public String getTransportationCompanyAgencyType() {
        return transportationCompanyAgencyType;
    }

    public void setTransportationCompanyAgencyType(String transportationCompanyAgencyType) {
        this.transportationCompanyAgencyType = transportationCompanyAgencyType;
    }

    public String getTransportationCompanyAgencyTypeName() {
        return transportationCompanyAgencyTypeName;
    }

    public void setTransportationCompanyAgencyTypeName(String transportationCompanyAgencyTypeName) {
        this.transportationCompanyAgencyTypeName = transportationCompanyAgencyTypeName;
    }

    public Contractor getCarrier() {
        return carrier;
    }

    public void setCarrier(Contractor carrier) {
        this.carrier = carrier;
    }

    public String getCarrierNameFull() {
        return carrierNameFull;
    }

    public void setCarrierNameFull(String carrierNameFull) {
        this.carrierNameFull = carrierNameFull;
    }

    public String getCarrierNameShort() {
        return carrierNameShort;
    }

    public void setCarrierNameShort(String carrierNameShort) {
        this.carrierNameShort = carrierNameShort;
    }

    public String getCarrierInn() {
        return carrierInn;
    }

    public void setCarrierInn(String carrierInn) {
        this.carrierInn = carrierInn;
    }

    public String getCarrierKpp() {
        return carrierKpp;
    }

    public void setCarrierKpp(String carrierKpp) {
        this.carrierKpp = carrierKpp;
    }

    public String getCarrierOkpo() {
        return carrierOkpo;
    }

    public void setCarrierOkpo(String carrierOkpo) {
        this.carrierOkpo = carrierOkpo;
    }

    public String getCarrierOgrn() {
        return carrierOgrn;
    }

    public void setCarrierOgrn(String carrierOgrn) {
        this.carrierOgrn = carrierOgrn;
    }

    public String getCarrierOkato() {
        return carrierOkato;
    }

    public void setCarrierOkato(String carrierOkato) {
        this.carrierOkato = carrierOkato;
    }

    public String getCarrierNameWork() {
        return carrierNameWork;
    }

    public void setCarrierNameWork(String carrierNameWork) {
        this.carrierNameWork = carrierNameWork;
    }

    public Long getCarrierOrganizationTypeId() {
        return carrierOrganizationTypeId;
    }

    public void setCarrierOrganizationTypeId(Long carrierOrganizationTypeId) {
        this.carrierOrganizationTypeId = carrierOrganizationTypeId;
    }

    public String getCarrierOrganizationTypeNameFull() {
        return carrierOrganizationTypeNameFull;
    }

    public void setCarrierOrganizationTypeNameFull(String carrierOrganizationTypeNameFull) {
        this.carrierOrganizationTypeNameFull = carrierOrganizationTypeNameFull;
    }

    public String getCarrierOrganizationTypeNameShort() {
        return carrierOrganizationTypeNameShort;
    }

    public void setCarrierOrganizationTypeNameShort(String carrierOrganizationTypeNameShort) {
        this.carrierOrganizationTypeNameShort = carrierOrganizationTypeNameShort;
    }

    public Long getCarrierAgencyTypeId() {
        return carrierAgencyTypeId;
    }

    public void setCarrierAgencyTypeId(Long carrierAgencyTypeId) {
        this.carrierAgencyTypeId = carrierAgencyTypeId;
    }

    public String getCarrierAgencyType() {
        return carrierAgencyType;
    }

    public void setCarrierAgencyType(String carrierAgencyType) {
        this.carrierAgencyType = carrierAgencyType;
    }

    public String getCarrierAgencyTypeName() {
        return carrierAgencyTypeName;
    }

    public void setCarrierAgencyTypeName(String carrierAgencyTypeName) {
        this.carrierAgencyTypeName = carrierAgencyTypeName;
    }

    public Contractor getProvider() {
        return provider;
    }

    public void setProvider(Contractor provider) {
        this.provider = provider;
    }

    public String getProviderNameFull() {
        return providerNameFull;
    }

    public void setProviderNameFull(String providerNameFull) {
        this.providerNameFull = providerNameFull;
    }

    public String getProviderNameShort() {
        return providerNameShort;
    }

    public void setProviderNameShort(String providerNameShort) {
        this.providerNameShort = providerNameShort;
    }

    public String getProviderInn() {
        return providerInn;
    }

    public void setProviderInn(String providerInn) {
        this.providerInn = providerInn;
    }

    public String getProviderKpp() {
        return providerKpp;
    }

    public void setProviderKpp(String providerKpp) {
        this.providerKpp = providerKpp;
    }

    public String getProviderOkpo() {
        return providerOkpo;
    }

    public void setProviderOkpo(String providerOkpo) {
        this.providerOkpo = providerOkpo;
    }

    public String getProviderOgrn() {
        return providerOgrn;
    }

    public void setProviderOgrn(String providerOgrn) {
        this.providerOgrn = providerOgrn;
    }

    public String getProviderOkato() {
        return providerOkato;
    }

    public void setProviderOkato(String providerOkato) {
        this.providerOkato = providerOkato;
    }

    public String getProviderNameWork() {
        return providerNameWork;
    }

    public void setProviderNameWork(String providerNameWork) {
        this.providerNameWork = providerNameWork;
    }

    public Long getProviderOrganizationTypeId() {
        return providerOrganizationTypeId;
    }

    public void setProviderOrganizationTypeId(Long providerOrganizationTypeId) {
        this.providerOrganizationTypeId = providerOrganizationTypeId;
    }

    public String getProviderOrganizationTypeNameFull() {
        return providerOrganizationTypeNameFull;
    }

    public void setProviderOrganizationTypeNameFull(String providerOrganizationTypeNameFull) {
        this.providerOrganizationTypeNameFull = providerOrganizationTypeNameFull;
    }

    public String getProviderOrganizationTypeNameShort() {
        return providerOrganizationTypeNameShort;
    }

    public void setProviderOrganizationTypeNameShort(String providerOrganizationTypeNameShort) {
        this.providerOrganizationTypeNameShort = providerOrganizationTypeNameShort;
    }

    public Long getProviderAgencyTypeId() {
        return providerAgencyTypeId;
    }

    public void setProviderAgencyTypeId(Long providerAgencyTypeId) {
        this.providerAgencyTypeId = providerAgencyTypeId;
    }

    public String getProviderAgencyType() {
        return providerAgencyType;
    }

    public void setProviderAgencyType(String providerAgencyType) {
        this.providerAgencyType = providerAgencyType;
    }

    public String getProviderAgencyTypeName() {
        return providerAgencyTypeName;
    }

    public void setProviderAgencyTypeName(String providerAgencyTypeName) {
        this.providerAgencyTypeName = providerAgencyTypeName;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getProductNameFull() {
        return productNameFull;
    }

    public void setProductNameFull(String productNameFull) {
        this.productNameFull = productNameFull;
    }

    public String getProductNameShort() {
        return productNameShort;
    }

    public void setProductNameShort(String productNameShort) {
        this.productNameShort = productNameShort;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Long getProductType() {
        return productType;
    }

    public void setProductType(Long productType) {
        this.productType = productType;
    }

    public boolean isProductContractPrice() {
        return productContractPrice;
    }

    public void setProductContractPrice(boolean productContractPrice) {
        this.productContractPrice = productContractPrice;
    }

    public String getProductBalance() {
        return productBalance;
    }

    public void setProductBalance(String productBalance) {
        this.productBalance = productBalance;
    }

    public Double getProductMarkUp() {
        return productMarkUp;
    }

    public void setProductMarkUp(Double productMarkUp) {
        this.productMarkUp = productMarkUp;
    }

    public Long getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(Long productTypeId) {
        this.productTypeId = productTypeId;
    }

    public String getProductTypeNameFull() {
        return productTypeNameFull;
    }

    public void setProductTypeNameFull(String productTypeNameFull) {
        this.productTypeNameFull = productTypeNameFull;
    }

    public String getProductTypeNameWork() {
        return productTypeNameWork;
    }

    public void setProductTypeNameWork(String productTypeNameWork) {
        this.productTypeNameWork = productTypeNameWork;
    }

    public Double getProductTypeDensity() {
        return productTypeDensity;
    }

    public void setProductTypeDensity(Double productTypeDensity) {
        this.productTypeDensity = productTypeDensity;
    }

    public ProductGroup getProductGroup() {
        return productGroup;
    }

    public void setProductGroup(ProductGroup productGroup) {
        this.productGroup = productGroup;
    }

    public String getProductGroupTitle() {
        return productGroupTitle;
    }

    public void setProductGroupTitle(String productGroupTitle) {
        this.productGroupTitle = productGroupTitle;
    }

    public Long getMeasureUnitId() {
        return measureUnitId;
    }

    public void setMeasureUnitId(Long measureUnitId) {
        this.measureUnitId = measureUnitId;
    }

    public String getMeasureUnitNameFull() {
        return measureUnitNameFull;
    }

    public void setMeasureUnitNameFull(String measureUnitNameFull) {
        this.measureUnitNameFull = measureUnitNameFull;
    }

    public String getMeasureUnitNameShort() {
        return measureUnitNameShort;
    }

    public void setMeasureUnitNameShort(String measureUnitNameShort) {
        this.measureUnitNameShort = measureUnitNameShort;
    }

    public String getMeasureUnitCode() {
        return measureUnitCode;
    }

    public void setMeasureUnitCode(String measureUnitCode) {
        this.measureUnitCode = measureUnitCode;
    }

    public Long getProductPriceId() {
        return productPriceId;
    }

    public void setProductPriceId(Long productPriceId) {
        this.productPriceId = productPriceId;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public ShipmentBasis getShipmentBase() {
        return shipmentBase;
    }

    public void setShipmentBase(ShipmentBasis shipmentBase) {
        this.shipmentBase = shipmentBase;
    }

    public String getShipmentBaseNameFull() {
        return shipmentBaseNameFull;
    }

    public void setShipmentBaseNameFull(String shipmentBaseNameFull) {
        this.shipmentBaseNameFull = shipmentBaseNameFull;
    }

    public String getShipmentBaseNameShort() {
        return shipmentBaseNameShort;
    }

    public void setShipmentBaseNameShort(String shipmentBaseNameShort) {
        this.shipmentBaseNameShort = shipmentBaseNameShort;
    }

    public String getShipmentBaseAddress() {
        return shipmentBaseAddress;
    }

    public void setShipmentBaseAddress(String shipmentBaseAddress) {
        this.shipmentBaseAddress = shipmentBaseAddress;
    }

    public Double getShipmentBaseLongitude() {
        return shipmentBaseLongitude;
    }

    public void setShipmentBaseLongitude(Double shipmentBaseLongitude) {
        this.shipmentBaseLongitude = shipmentBaseLongitude;
    }

    public Double getShipmentBaseLatitude() {
        return shipmentBaseLatitude;
    }

    public void setShipmentBaseLatitude(Double shipmentBaseLatitude) {
        this.shipmentBaseLatitude = shipmentBaseLatitude;
    }

    public OrderPaymentType getOrderPaymentType() {
        return orderPaymentType;
    }

    public void setOrderPaymentType(OrderPaymentType orderPaymentType) {
        this.orderPaymentType = orderPaymentType;
    }

    public String getOrderPaymentTypeType() {
        return orderPaymentTypeType;
    }

    public void setOrderPaymentTypeType(String orderPaymentTypeType) {
        this.orderPaymentTypeType = orderPaymentTypeType;
    }

    public String getOrderPaymentTypeName() {
        return orderPaymentTypeName;
    }

    public void setOrderPaymentTypeName(String orderPaymentTypeName) {
        this.orderPaymentTypeName = orderPaymentTypeName;
    }

    public OrderStatusType getOrderStatusType() {
        return orderStatusType;
    }

    public void setOrderStatusType(OrderStatusType orderStatusType) {
        this.orderStatusType = orderStatusType;
    }

    public Integer getOrderStatusTypeOrder() {
        return orderStatusTypeOrder;
    }

    public void setOrderStatusTypeOrder(Integer orderStatusTypeOrder) {
        this.orderStatusTypeOrder = orderStatusTypeOrder;
    }

    public String getOrderStatusTypeType() {
        return orderStatusTypeType;
    }

    public void setOrderStatusTypeType(String orderStatusTypeType) {
        this.orderStatusTypeType = orderStatusTypeType;
    }

    public String getOrderStatusTypeName() {
        return orderStatusTypeName;
    }

    public void setOrderStatusTypeName(String orderStatusTypeName) {
        this.orderStatusTypeName = orderStatusTypeName;
    }

    public User getTransportManager() {
        return transportManager;
    }

    public void setTransportManager(User transportManager) {
        this.transportManager = transportManager;
    }

    public String getTransportManagerUsername() {
        return transportManagerUsername;
    }

    public void setTransportManagerUsername(String transportManagerUsername) {
        this.transportManagerUsername = transportManagerUsername;
    }

    public String getTransportManagerFirstName() {
        return transportManagerFirstName;
    }

    public void setTransportManagerFirstName(String transportManagerFirstName) {
        this.transportManagerFirstName = transportManagerFirstName;
    }

    public String getTransportManagerLastName() {
        return transportManagerLastName;
    }

    public void setTransportManagerLastName(String transportManagerLastName) {
        this.transportManagerLastName = transportManagerLastName;
    }

    public String getTransportManagerPatronymic() {
        return transportManagerPatronymic;
    }

    public void setTransportManagerPatronymic(String transportManagerPatronymic) {
        this.transportManagerPatronymic = transportManagerPatronymic;
    }

    public String getTransportManagerTitle() {
        return transportManagerTitle;
    }

    public void setTransportManagerTitle(String transportManagerTitle) {
        this.transportManagerTitle = transportManagerTitle;
    }

    public TransportationVehicleType getTransportationVehicleType() {
        return transportationVehicleType;
    }

    public void setTransportationVehicleType(TransportationVehicleType transportationVehicleType) {
        this.transportationVehicleType = transportationVehicleType;
    }

    public String getTransportationVehicleTypeType() {
        return transportationVehicleTypeType;
    }

    public void setTransportationVehicleTypeType(String transportationVehicleTypeType) {
        this.transportationVehicleTypeType = transportationVehicleTypeType;
    }

    public String getTransportationVehicleTypeName() {
        return transportationVehicleTypeName;
    }

    public void setTransportationVehicleTypeName(String transportationVehicleTypeName) {
        this.transportationVehicleTypeName = transportationVehicleTypeName;
    }

    public TransportationCompanyType getTransportationCompanyType() {
        return transportationCompanyType;
    }

    public void setTransportationCompanyType(TransportationCompanyType transportationCompanyType) {
        this.transportationCompanyType = transportationCompanyType;
    }

    public String getTransportationCompanyTypeType() {
        return transportationCompanyTypeType;
    }

    public void setTransportationCompanyTypeType(String transportationCompanyTypeType) {
        this.transportationCompanyTypeType = transportationCompanyTypeType;
    }

    public String getTransportationCompanyTypeName() {
        return transportationCompanyTypeName;
    }

    public void setTransportationCompanyTypeName(String transportationCompanyTypeName) {
        this.transportationCompanyTypeName = transportationCompanyTypeName;
    }

    public TransportationHiredVehicleType getTransportationHiredVehicleType() {
        return transportationHiredVehicleType;
    }

    public void setTransportationHiredVehicleType(TransportationHiredVehicleType transportationHiredVehicleType) {
        this.transportationHiredVehicleType = transportationHiredVehicleType;
    }

    public String getTransportationHiredVehicleTypeType() {
        return transportationHiredVehicleTypeType;
    }

    public void setTransportationHiredVehicleTypeType(String transportationHiredVehicleTypeType) {
        this.transportationHiredVehicleTypeType = transportationHiredVehicleTypeType;
    }

    public String getTransportationHiredVehicleTypeName() {
        return transportationHiredVehicleTypeName;
    }

    public void setTransportationHiredVehicleTypeName(String transportationHiredVehicleTypeName) {
        this.transportationHiredVehicleTypeName = transportationHiredVehicleTypeName;
    }

    public Truck getTruck() {
        return truck;
    }

    public void setTruck(Truck truck) {
        this.truck = truck;
    }

    public String getTruckTitle() {
        return truckTitle;
    }

    public void setTruckTitle(String truckTitle) {
        this.truckTitle = truckTitle;
    }

    public String getTruckBrand() {
        return truckBrand;
    }

    public void setTruckBrand(String truckBrand) {
        this.truckBrand = truckBrand;
    }

    public String getTruckModel() {
        return truckModel;
    }

    public void setTruckModel(String truckModel) {
        this.truckModel = truckModel;
    }

    public String getTruckGovNumber() {
        return truckGovNumber;
    }

    public void setTruckGovNumber(String truckGovNumber) {
        this.truckGovNumber = truckGovNumber;
    }

    public Trailer getTrailer() {
        return trailer;
    }

    public void setTrailer(Trailer trailer) {
        this.trailer = trailer;
    }

    public String getTrailerTitle() {
        return trailerTitle;
    }

    public void setTrailerTitle(String trailerTitle) {
        this.trailerTitle = trailerTitle;
    }

    public String getTrailerBrand() {
        return trailerBrand;
    }

    public void setTrailerBrand(String trailerBrand) {
        this.trailerBrand = trailerBrand;
    }

    public String getTrailerModel() {
        return trailerModel;
    }

    public void setTrailerModel(String trailerModel) {
        this.trailerModel = trailerModel;
    }

    public String getTrailerGovNumber() {
        return trailerGovNumber;
    }

    public void setTrailerGovNumber(String trailerGovNumber) {
        this.trailerGovNumber = trailerGovNumber;
    }

    public Double getTrailerCalibration() {
        return trailerCalibration;
    }

    public void setTrailerCalibration(Double trailerCalibration) {
        this.trailerCalibration = trailerCalibration;
    }

    public Double getTrailerCapacity() {
        return trailerCapacity;
    }

    public void setTrailerCapacity(Double trailerCapacity) {
        this.trailerCapacity = trailerCapacity;
    }

    public Boolean getTrailerBottomLoading() {
        return trailerBottomLoading;
    }

    public void setTrailerBottomLoading(Boolean trailerBottomLoading) {
        this.trailerBottomLoading = trailerBottomLoading;
    }

    public Double getTrailerBottomLoadingCalibration() {
        return trailerBottomLoadingCalibration;
    }

    public void setTrailerBottomLoadingCalibration(Double trailerBottomLoadingCalibration) {
        this.trailerBottomLoadingCalibration = trailerBottomLoadingCalibration;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public String getDriverTitle() {
        return driverTitle;
    }

    public void setDriverTitle(String driverTitle) {
        this.driverTitle = driverTitle;
    }

    public String getDriverFirstName() {
        return driverFirstName;
    }

    public void setDriverFirstName(String driverFirstName) {
        this.driverFirstName = driverFirstName;
    }

    public String getDriverLastName() {
        return driverLastName;
    }

    public void setDriverLastName(String driverLastName) {
        this.driverLastName = driverLastName;
    }

    public String getDriverPatronymic() {
        return driverPatronymic;
    }

    public void setDriverPatronymic(String driverPatronymic) {
        this.driverPatronymic = driverPatronymic;
    }

    public Date getDriverDateOfBirth() {
        return driverDateOfBirth;
    }

    public void setDriverDateOfBirth(Date driverDateOfBirth) {
        this.driverDateOfBirth = driverDateOfBirth;
    }

    public String getDriverDocSerial() {
        return driverDocSerial;
    }

    public void setDriverDocSerial(String driverDocSerial) {
        this.driverDocSerial = driverDocSerial;
    }

    public String getDriverDocNumber() {
        return driverDocNumber;
    }

    public void setDriverDocNumber(String driverDocNumber) {
        this.driverDocNumber = driverDocNumber;
    }

    public Date getDriverDocGivenDate() {
        return driverDocGivenDate;
    }

    public void setDriverDocGivenDate(Date driverDocGivenDate) {
        this.driverDocGivenDate = driverDocGivenDate;
    }

    public String getDriverDocGivenFrom() {
        return driverDocGivenFrom;
    }

    public void setDriverDocGivenFrom(String driverDocGivenFrom) {
        this.driverDocGivenFrom = driverDocGivenFrom;
    }

    public String getDriverDocCode() {
        return driverDocCode;
    }

    public void setDriverDocCode(String driverDocCode) {
        this.driverDocCode = driverDocCode;
    }

    public String getDriverDocDescription() {
        return driverDocDescription;
    }

    public void setDriverDocDescription(String driverDocDescription) {
        this.driverDocDescription = driverDocDescription;
    }

    public String getDriverPhone1() {
        return driverPhone1;
    }

    public void setDriverPhone1(String driverPhone1) {
        this.driverPhone1 = driverPhone1;
    }

    public String getDriverPhone2() {
        return driverPhone2;
    }

    public void setDriverPhone2(String driverPhone2) {
        this.driverPhone2 = driverPhone2;
    }

    public String getDriverPhoneEmergency() {
        return driverPhoneEmergency;
    }

    public void setDriverPhoneEmergency(String driverPhoneEmergency) {
        this.driverPhoneEmergency = driverPhoneEmergency;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public String getVehicleDescription() {
        return vehicleDescription;
    }

    public void setVehicleDescription(String vehicleDescription) {
        this.vehicleDescription = vehicleDescription;
    }

    public Long getVehicleStatusId() {
        return vehicleStatusId;
    }

    public void setVehicleStatusId(Long vehicleStatusId) {
        this.vehicleStatusId = vehicleStatusId;
    }

    public String getVehicleStatusTitle() {
        return vehicleStatusTitle;
    }

    public void setVehicleStatusTitle(String vehicleStatusTitle) {
        this.vehicleStatusTitle = vehicleStatusTitle;
    }

    public Date getVehicleStatusCreated() {
        return vehicleStatusCreated;
    }

    public void setVehicleStatusCreated(Date vehicleStatusCreated) {
        this.vehicleStatusCreated = vehicleStatusCreated;
    }

    public Date getVehicleStatusStatusDate() {
        return vehicleStatusStatusDate;
    }

    public void setVehicleStatusStatusDate(Date vehicleStatusStatusDate) {
        this.vehicleStatusStatusDate = vehicleStatusStatusDate;
    }

    public String getVehicleStatusDescription() {
        return vehicleStatusDescription;
    }

    public void setVehicleStatusDescription(String vehicleStatusDescription) {
        this.vehicleStatusDescription = vehicleStatusDescription;
    }

    public Agreement getAgreement() {
        return agreement;
    }

    public void setAgreement(Agreement agreement) {
        this.agreement = agreement;
    }

    public String getAgreementNumber() {
        return agreementNumber;
    }

    public void setAgreementNumber(String agreementNumber) {
        this.agreementNumber = agreementNumber;
    }

    public Date getAgreementCreated() {
        return agreementCreated;
    }

    public void setAgreementCreated(Date agreementCreated) {
        this.agreementCreated = agreementCreated;
    }

    public Date getAgreementValidFrom() {
        return agreementValidFrom;
    }

    public void setAgreementValidFrom(Date agreementValidFrom) {
        this.agreementValidFrom = agreementValidFrom;
    }

    public Date getAgreementValidTo() {
        return agreementValidTo;
    }

    public void setAgreementValidTo(Date agreementValidTo) {
        this.agreementValidTo = agreementValidTo;
    }

    public String getAgreementDescription() {
        return agreementDescription;
    }

    public void setAgreementDescription(String agreementDescription) {
        this.agreementDescription = agreementDescription;
    }

    public Long getAgreementStatusId() {
        return agreementStatusId;
    }

    public void setAgreementStatusId(Long agreementStatusId) {
        this.agreementStatusId = agreementStatusId;
    }

    public String getAgreementStatusType() {
        return agreementStatusType;
    }

    public void setAgreementStatusType(String agreementStatusType) {
        this.agreementStatusType = agreementStatusType;
    }

    public String getAgreementStatusName() {
        return agreementStatusName;
    }

    public void setAgreementStatusName(String agreementStatusName) {
        this.agreementStatusName = agreementStatusName;
    }

    public Long getAgreementTypeId() {
        return agreementTypeId;
    }

    public void setAgreementTypeId(Long agreementTypeId) {
        this.agreementTypeId = agreementTypeId;
    }

    public String getAgreementType() {
        return agreementType;
    }

    public void setAgreementType(String agreementType) {
        this.agreementType = agreementType;
    }

    public String getAgreementTypeName() {
        return agreementTypeName;
    }

    public void setAgreementTypeName(String agreementTypeName) {
        this.agreementTypeName = agreementTypeName;
    }

    public Agreement getTransportationAgreement() {
        return transportationAgreement;
    }

    public void setTransportationAgreement(Agreement transportationAgreement) {
        this.transportationAgreement = transportationAgreement;
    }

    public String getTransportationAgreementNumber() {
        return transportationAgreementNumber;
    }

    public void setTransportationAgreementNumber(String transportationAgreementNumber) {
        this.transportationAgreementNumber = transportationAgreementNumber;
    }

    public Date getTransportationAgreementCreated() {
        return transportationAgreementCreated;
    }

    public void setTransportationAgreementCreated(Date transportationAgreementCreated) {
        this.transportationAgreementCreated = transportationAgreementCreated;
    }

    public Date getTransportationAgreementValidFrom() {
        return transportationAgreementValidFrom;
    }

    public void setTransportationAgreementValidFrom(Date transportationAgreementValidFrom) {
        this.transportationAgreementValidFrom = transportationAgreementValidFrom;
    }

    public Date getTransportationAgreementValidTo() {
        return transportationAgreementValidTo;
    }

    public void setTransportationAgreementValidTo(Date transportationAgreementValidTo) {
        this.transportationAgreementValidTo = transportationAgreementValidTo;
    }

    public String getTransportationAgreementDescription() {
        return transportationAgreementDescription;
    }

    public void setTransportationAgreementDescription(String transportationAgreementDescription) {
        this.transportationAgreementDescription = transportationAgreementDescription;
    }

    public Long getTransportationAgreementStatusId() {
        return transportationAgreementStatusId;
    }

    public void setTransportationAgreementStatusId(Long transportationAgreementStatusId) {
        this.transportationAgreementStatusId = transportationAgreementStatusId;
    }

    public String getTransportationAgreementStatusType() {
        return transportationAgreementStatusType;
    }

    public void setTransportationAgreementStatusType(String transportationAgreementStatusType) {
        this.transportationAgreementStatusType = transportationAgreementStatusType;
    }

    public String getTransportationAgreementStatusName() {
        return transportationAgreementStatusName;
    }

    public void setTransportationAgreementStatusName(String transportationAgreementStatusName) {
        this.transportationAgreementStatusName = transportationAgreementStatusName;
    }

    public Long getTransportationAgreementTypeId() {
        return transportationAgreementTypeId;
    }

    public void setTransportationAgreementTypeId(Long transportationAgreementTypeId) {
        this.transportationAgreementTypeId = transportationAgreementTypeId;
    }

    public String getTransportationAgreementType() {
        return transportationAgreementType;
    }

    public void setTransportationAgreementType(String transportationAgreementType) {
        this.transportationAgreementType = transportationAgreementType;
    }

    public String getTransportationAgreementTypeName() {
        return transportationAgreementTypeName;
    }

    public void setTransportationAgreementTypeName(String transportationAgreementTypeName) {
        this.transportationAgreementTypeName = transportationAgreementTypeName;
    }

    public Agreement getAdditionalAgreement() {
        return additionalAgreement;
    }

    public void setAdditionalAgreement(Agreement additionalAgreement) {
        this.additionalAgreement = additionalAgreement;
    }

    public String getAdditionalAgreementNumber() {
        return additionalAgreementNumber;
    }

    public void setAdditionalAgreementNumber(String additionalAgreementNumber) {
        this.additionalAgreementNumber = additionalAgreementNumber;
    }

    public Date getAdditionalAgreementCreated() {
        return additionalAgreementCreated;
    }

    public void setAdditionalAgreementCreated(Date additionalAgreementCreated) {
        this.additionalAgreementCreated = additionalAgreementCreated;
    }

    public Date getAdditionalAgreementValidFrom() {
        return additionalAgreementValidFrom;
    }

    public void setAdditionalAgreementValidFrom(Date additionalAgreementValidFrom) {
        this.additionalAgreementValidFrom = additionalAgreementValidFrom;
    }

    public Date getAdditionalAgreementValidTo() {
        return additionalAgreementValidTo;
    }

    public void setAdditionalAgreementValidTo(Date additionalAgreementValidTo) {
        this.additionalAgreementValidTo = additionalAgreementValidTo;
    }

    public String getAdditionalAgreementDescription() {
        return additionalAgreementDescription;
    }

    public void setAdditionalAgreementDescription(String additionalAgreementDescription) {
        this.additionalAgreementDescription = additionalAgreementDescription;
    }

    public AgreementStatus getAdditionalAgreementStatus() {
        return additionalAgreementStatus;
    }

    public void setAdditionalAgreementStatus(AgreementStatus additionalAgreementStatus) {
        this.additionalAgreementStatus = additionalAgreementStatus;
    }

    public String getAdditionalAgreementStatusType() {
        return additionalAgreementStatusType;
    }

    public void setAdditionalAgreementStatusType(String additionalAgreementStatusType) {
        this.additionalAgreementStatusType = additionalAgreementStatusType;
    }

    public String getAdditionalAgreementStatusName() {
        return additionalAgreementStatusName;
    }

    public void setAdditionalAgreementStatusName(String additionalAgreementStatusName) {
        this.additionalAgreementStatusName = additionalAgreementStatusName;
    }

    public Long getAdditionalAgreementTypeId() {
        return additionalAgreementTypeId;
    }

    public void setAdditionalAgreementTypeId(Long additionalAgreementTypeId) {
        this.additionalAgreementTypeId = additionalAgreementTypeId;
    }

    public String getAdditionalAgreementType() {
        return additionalAgreementType;
    }

    public void setAdditionalAgreementType(String additionalAgreementType) {
        this.additionalAgreementType = additionalAgreementType;
    }

    public String getAdditionalAgreementTypeName() {
        return additionalAgreementTypeName;
    }

    public void setAdditionalAgreementTypeName(String additionalAgreementTypeName) {
        this.additionalAgreementTypeName = additionalAgreementTypeName;
    }

    public TelegramUser getTelegramUser() {
        return telegramUser;
    }

    public void setTelegramUser(TelegramUser telegramUser) {
        this.telegramUser = telegramUser;
    }

    public boolean isTelegramReceivedAlready() {
        return telegramReceivedAlready;
    }

    public void setTelegramReceivedAlready(boolean telegramReceivedAlready) {
        this.telegramReceivedAlready = telegramReceivedAlready;
    }

    public Set<Drive> getDrives() {
        return drives;
    }

    public void setDrives(Set<Drive> drives) {
        this.drives = drives;
    }
}