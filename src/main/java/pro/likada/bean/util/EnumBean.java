package pro.likada.bean.util;

import pro.likada.util.*;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Created by Yusupov on 1/9/2017.
 */
@Named
@ApplicationScoped
public final class EnumBean implements Serializable {

    private ModelConstantEnum modelCustomer = ModelConstantEnum.CUSTOMER;
    private ModelConstantEnum modelContractor = ModelConstantEnum.CONTRACTOR;
    private ModelConstantEnum modelAgreement = ModelConstantEnum.AGREEMENT;
    private ModelConstantEnum modelProduct = ModelConstantEnum.PRODUCT;
    private ModelConstantEnum modelProductType = ModelConstantEnum.PRODUCT_TYPE;
    private ModelConstantEnum modelOrder = ModelConstantEnum.ORDER;
    private ModelConstantEnum modelBases = ModelConstantEnum.BASIS;
    private ModelConstantEnum modelCarrier = ModelConstantEnum.CARRIER;
    private ModelConstantEnum modelProvider = ModelConstantEnum.PROVIDER;
    private ModelConstantEnum modelCompany = ModelConstantEnum.COMPANY;
    private ModelConstantEnum modelTruck = ModelConstantEnum.TRUCK;
    private ModelConstantEnum modelTrailer = ModelConstantEnum.TRAILER;
    private ModelConstantEnum modelFinancialItem = ModelConstantEnum.FINANCIAL_ITEM;
    private ModelConstantEnum modelVehicle = ModelConstantEnum.VEHICLE;
    private ModelConstantEnum modelDriver = ModelConstantEnum.DRIVER;
    private ModelConstantEnum modelVehicleStatus = ModelConstantEnum.VEHICLE_STATUS;

    private TransportationCompanyTypeEnum transportationCompanyTypeHired = TransportationCompanyTypeEnum.HIRED;
    private TransportationCompanyTypeEnum transportationCompanyTypeSelf = TransportationCompanyTypeEnum.SELF;
    private TransportationCompanyTypeEnum transportationCompanyTypeTransport = TransportationCompanyTypeEnum.TRANSPORT;

    private TransportationHiredVehicleTypeEnum transportationHiredVehicleTypeSearch = TransportationHiredVehicleTypeEnum.SEARCH;
    private TransportationHiredVehicleTypeEnum transportationHiredVehicleTypeInternal = TransportationHiredVehicleTypeEnum.INTERNAL;
    private TransportationHiredVehicleTypeEnum transportationHiredVehicleTypeExternal = TransportationHiredVehicleTypeEnum.EXTERNAL;

    private TransportationVehicleTypeEnum transportationVehicleTypeAuto = TransportationVehicleTypeEnum.AUTO;
    private TransportationVehicleTypeEnum transportationVehicleTypeRailway = TransportationVehicleTypeEnum.RAILWAY;

    private OrderPaymentTypeEnum orderPaymentTypeFact = OrderPaymentTypeEnum.FACT;
    private OrderPaymentTypeEnum orderPaymentTypePre = OrderPaymentTypeEnum.PREPAYMENT;
    private OrderPaymentTypeEnum orderPaymentTypePost = OrderPaymentTypeEnum.POSTPAYMENT;

    private AccessTypeEnum accessView = AccessTypeEnum.VIEW;
    private AccessTypeEnum accessAdd = AccessTypeEnum.ADD;
    private AccessTypeEnum accessEdit = AccessTypeEnum.EDIT;
    private AccessTypeEnum accessDelete = AccessTypeEnum.DELETE;

    public ModelConstantEnum getModelCustomer() {
        return modelCustomer;
    }

    public ModelConstantEnum getModelContractor() {
        return modelContractor;
    }

    public AccessTypeEnum getAccessView() {
        return accessView;
    }

    public AccessTypeEnum getAccessAdd() {
        return accessAdd;
    }

    public AccessTypeEnum getAccessEdit() {
        return accessEdit;
    }

    public AccessTypeEnum getAccessDelete() {
        return accessDelete;
    }

    public ModelConstantEnum getModelAgreement() {
        return modelAgreement;
    }

    public ModelConstantEnum getModelProduct() {
        return modelProduct;
    }

    public ModelConstantEnum getModelProductType() {
        return modelProductType;
    }

    public ModelConstantEnum getModelOrder() {
        return modelOrder;
    }

    public ModelConstantEnum getModelBases() {
        return modelBases;
    }

    public ModelConstantEnum getModelCarrier() {
        return modelCarrier;
    }

    public ModelConstantEnum getModelProvider() {
        return modelProvider;
    }

    public ModelConstantEnum getModelCompany() {
        return modelCompany;
    }

    public ModelConstantEnum getModelTruck() {
        return modelTruck;
    }

    public ModelConstantEnum getModelTrailer() {
        return modelTrailer;
    }

    public ModelConstantEnum getModelFinancialItem() {
        return modelFinancialItem;
    }

    public ModelConstantEnum getModelVehicle() {
        return modelVehicle;
    }

    public ModelConstantEnum getModelDriver() {
        return modelDriver;
    }

    public ModelConstantEnum getModelVehicleStatus() {
        return modelVehicleStatus;
    }

    public TransportationCompanyTypeEnum getTransportationCompanyTypeHired() {
        return transportationCompanyTypeHired;
    }

    public TransportationCompanyTypeEnum getTransportationCompanyTypeSelf() {
        return transportationCompanyTypeSelf;
    }

    public TransportationCompanyTypeEnum getTransportationCompanyTypeTransport() {
        return transportationCompanyTypeTransport;
    }

    public TransportationHiredVehicleTypeEnum getTransportationHiredVehicleTypeSearch() {
        return transportationHiredVehicleTypeSearch;
    }

    public TransportationHiredVehicleTypeEnum getTransportationHiredVehicleTypeInternal() {
        return transportationHiredVehicleTypeInternal;
    }

    public TransportationHiredVehicleTypeEnum getTransportationHiredVehicleTypeExternal() {
        return transportationHiredVehicleTypeExternal;
    }

    public TransportationVehicleTypeEnum getTransportationVehicleTypeAuto() {
        return transportationVehicleTypeAuto;
    }

    public TransportationVehicleTypeEnum getTransportationVehicleTypeRailway() {
        return transportationVehicleTypeRailway;
    }

    public OrderPaymentTypeEnum getOrderPaymentTypeFact() {
        return orderPaymentTypeFact;
    }

    public OrderPaymentTypeEnum getOrderPaymentTypePre() {
        return orderPaymentTypePre;
    }

    public OrderPaymentTypeEnum getOrderPaymentTypePost() {
        return orderPaymentTypePost;
    }
}

