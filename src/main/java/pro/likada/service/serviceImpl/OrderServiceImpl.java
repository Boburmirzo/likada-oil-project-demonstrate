package pro.likada.service.serviceImpl;

import org.apache.commons.lang.time.DateUtils;
import org.hibernate.HibernateException;
import pro.likada.bean.controller.LoginController;
import pro.likada.dao.OrderDAO;
import pro.likada.model.*;
import pro.likada.service.OrderService;
import pro.likada.service.ProductPriceService;
import pro.likada.util.Constants;
import pro.likada.util.TransportationCompanyTypeEnum;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Yusupov on 3/10/2017.
 */
@Named("orderService")
@Transactional
public class OrderServiceImpl implements OrderService {

    @Inject
    private OrderDAO orderDAO;
    @Inject
    private ProductPriceService productPriceService;
    @Inject
    private LoginController loginController;

    @Override
    public Order findById(long id) {
        return orderDAO.findById(id);
    }

    @Override
    public Order findByTelegramUserAndSubmissionStatus(TelegramUser telegramUser, Boolean submittedAlready) throws HibernateException {
        Order order = orderDAO.findByTelegramUserAndSubmissionStatus(telegramUser, submittedAlready);
        if (order == null){
            order = new Order();
            order.setTelegramReceivedAlready(submittedAlready);
        }
        order.setCreatedDate(Calendar.getInstance().getTime());
        order.setTelegramUser(telegramUser);
        return order;
    }

    @Override
    public List<Order> findAllOrdersByCustomer(Customer customer) {
        return orderDAO.findAllOrdersByCustomer(customer);
    }

    @Override
    public void save(Order order) {
        orderDAO.save(order);
    }

    @Override
    public void delete(Order order) {
        orderDAO.delete(order);
    }

    @Override
    public void deleteById(long id) {
        orderDAO.deleteById(id);
    }

    @Override
    public List<Order> findAllOrdersBasedOnSearchStringAndFilterPeriod(String searchString, String filterPeriodOneButton) {
        Calendar from = DateUtils.truncate(Calendar.getInstance(), Calendar.DATE);
        if(Constants.FILTER_PERIOD_FOR_TWO_DAYS.equals(filterPeriodOneButton)){
            from.add(Calendar.DAY_OF_MONTH, -1);
        } else if (Constants.FILTER_PERIOD_FOR_THREE_DAYS.equals(filterPeriodOneButton)){
            from.add(Calendar.DAY_OF_MONTH, -2);
        } else if (Constants.FILTER_PERIOD_FOR_WEEK.equals(filterPeriodOneButton)){
            from.add(Calendar.DAY_OF_MONTH, -6);
        } else if (Constants.FILTER_PERIOD_ALL.equals(filterPeriodOneButton)){
            from = null;
        }
        return orderDAO.findAllOrdersBasedOnSearchStringAndFilterPeriod(searchString, from!=null?from.getTime():null, Calendar.getInstance().getTime());
    }

    @Override
    public void copySelectedOrderStatusTypeInfo(Order newOrder, OrderStatusType orderStatusType) {
        if(newOrder!=null){
            if(orderStatusType!=null){
                newOrder.setOrderStatusType(orderStatusType);
                newOrder.setOrderStatusTypeOrder(orderStatusType.getStatusOrder());
                newOrder.setOrderStatusTypeType(orderStatusType.getType());
                newOrder.setOrderStatusTypeName(orderStatusType.getName());
            } else {
                resetCopySelectedOrderStatusTypeInfo(newOrder);
            }
        }
    }
    private void resetCopySelectedOrderStatusTypeInfo(Order newOrder){
        newOrder.setOrderStatusType(null);
        newOrder.setOrderStatusTypeOrder(null);
        newOrder.setOrderStatusTypeType(null);
        newOrder.setOrderStatusTypeName(null);
    }

    @Override
    public void copyCurrentUserInfoForCreatorAndOwner(Order newOrder, User user) {
        if (newOrder!=null) {
            // Apply filter to show related customers of the user
            if(user == null){
                user = loginController.getCurrentUser();
            }
            // Copy User info for creator fields of Order
            copySelectedUserInfoForCreator(newOrder, user);
            // Copy User info for owner fields of Order
            for (Role role: user.getUserRoles()){
                if(RoleEnum.SALES_MANAGER.getRoleType().equals(role.getType()) || RoleEnum.LOGISTICS_MANAGER.getRoleType().equals(role.getType())){
                    copySelectedUserInfoForOwner(newOrder, user);
                }
            }
        }
    }

    @Override
    public void copySelectedProductGroupInfo(Order newOrder, ProductGroup productGroup) {
        if(newOrder!=null){
            if(productGroup!=null){
                newOrder.setProductGroup(productGroup);
                newOrder.setProductGroupTitle(productGroup.getTitle());
            } else {
                resetCopySelectedProductGroupInfo(newOrder);
            }
        }
    }
    private void resetCopySelectedProductGroupInfo(Order newOrder){
        newOrder.setProductGroup(null);
        newOrder.setProductGroupTitle(null);
    }

    @Override
    public void copySelectedProductInfo(Order newOrder, Product product) {
        if(newOrder!=null){
            if(product!=null){
                newOrder.setProduct(product);
                newOrder.setProductNameFull(product.getNameFull());
                newOrder.setProductNameShort(product.getNameShort());
                newOrder.setProductCode(product.getCode());
                newOrder.setProductType(product.getType());
                newOrder.setProductContractPrice(product.isContractPrice());
                newOrder.setProductBalance(product.getBalance());
                newOrder.setProductMarkUp(product.getMarkUp());
                if(product.getProductType()!=null){
                    newOrder.setProductTypeId(product.getProductType().getId());
                    newOrder.setProductTypeNameFull(product.getProductType().getNameFull());
                    newOrder.setProductTypeNameWork(product.getProductType().getNameWork());
                    newOrder.setProductTypeDensity(product.getProductType().getDensity());
                }
                if (product.getGroupId()!=null){
                    newOrder.setProductGroup(product.getGroupId());
                    newOrder.setProductGroupTitle(product.getGroupId().getTitle());
                }
                if(product.getMeasureUnit()!=null){
                    newOrder.setMeasureUnitId(product.getMeasureUnit().getId());
                    newOrder.setMeasureUnitNameFull(product.getMeasureUnit().getNameFull());
                    newOrder.setMeasureUnitNameShort(product.getMeasureUnit().getNameShort());
                    newOrder.setMeasureUnitCode(product.getMeasureUnit().getCode());
                }
                ProductPrice productPrice = productPriceService.getProductActualPrice(product.getProductPricesList());
                if(productPrice != null){
                    newOrder.setProductPriceId(productPrice.getId());
                    newOrder.setProductPrice(productPrice.getPrice());
                    newOrder.setProductCostPlan(productPrice.getPrice());
                }
            } else {
                resetCopySelectedUserInfoForProduct(newOrder);
            }
        }
    }
    private void resetCopySelectedUserInfoForProduct(Order newOrder){
        newOrder.setProduct(null);
        newOrder.setProductNameFull(null);
        newOrder.setProductNameShort(null);
        newOrder.setProductCode(null);
        newOrder.setProductType(null);
        newOrder.setProductContractPrice(false);
        newOrder.setProductBalance(null);
        newOrder.setProductMarkUp(null);
        newOrder.setProductTypeId(null);
        newOrder.setProductTypeNameFull(null);
        newOrder.setProductTypeNameWork(null);
        newOrder.setProductTypeDensity(null);
        newOrder.setProductGroup(null);
        newOrder.setProductGroupTitle(null);
        newOrder.setMeasureUnitId(null);
        newOrder.setMeasureUnitNameFull(null);
        newOrder.setMeasureUnitNameShort(null);
        newOrder.setMeasureUnitCode(null);
        newOrder.setProductPriceId(null);
        newOrder.setProductPrice(null);
        newOrder.setProductCostPlan(null);
    }

    @Override
    public void copyShipmentBasisInfo(Order newOrder, ShipmentBasis shipmentBasis) {
        if(newOrder!=null){
            if (shipmentBasis!=null){
                newOrder.setShipmentBase(shipmentBasis);
                newOrder.setShipmentBaseNameFull(shipmentBasis.getNameFull());
                newOrder.setShipmentBaseNameShort(shipmentBasis.getNameShort());
                newOrder.setShipmentBaseAddress(shipmentBasis.getAddress());
                newOrder.setShipmentBaseLatitude(shipmentBasis.getLatitude());
                newOrder.setShipmentBaseLongitude(shipmentBasis.getLongitude());
            } else {
                resetCopyShipmentBasisInfo(newOrder);
            }
        }
    }
    private void resetCopyShipmentBasisInfo(Order newOrder){
        newOrder.setShipmentBase(null);
        newOrder.setShipmentBaseNameFull(null);
        newOrder.setShipmentBaseNameShort(null);
        newOrder.setShipmentBaseAddress(null);
        newOrder.setShipmentBaseLatitude(null);
        newOrder.setShipmentBaseLongitude(null);
    }



    @Override
    public void copySelectedUserInfoForCreator(Order newOrder, User creator) {
        if(newOrder!=null){
            if (creator!=null){
                newOrder.setCreator(creator);
                newOrder.setCreatorUsername(creator.getUsername());
                newOrder.setCreatorFirstName(creator.getFirstName());
                newOrder.setCreatorLastName(creator.getLastName());
                newOrder.setCreatorPatronymic(creator.getPatronymic());
                newOrder.setCreatorTitle(creator.getTitle());
            } else {
                resetCopySelectedUserInfoForCreator(newOrder);
            }
        }
    }
    private void resetCopySelectedUserInfoForCreator(Order newOrder){
        newOrder.setCreator(null);
        newOrder.setCreatorUsername(null);
        newOrder.setCreatorFirstName(null);
        newOrder.setCreatorLastName(null);
        newOrder.setCreatorPatronymic(null);
        newOrder.setCreatorTitle(null);
    }

    @Override
    public void copySelectedUserInfoForOwner(Order newOrder, User owner) {
        if(newOrder!=null){
            if (owner!=null){
                newOrder.setOwner(owner);
                newOrder.setOwnerUsername(owner.getUsername());
                newOrder.setOwnerFirstName(owner.getFirstName());
                newOrder.setOwnerLastName(owner.getLastName());
                newOrder.setOwnerPatronymic(owner.getPatronymic());
                newOrder.setOwnerTitle(owner.getTitle());
            } else {
                resetCopySelectedUserInfoForOwner(newOrder);
            }
        }
    }
    private void resetCopySelectedUserInfoForOwner(Order newOrder){
        newOrder.setOwner(null);
        newOrder.setOwnerUsername(null);
        newOrder.setOwnerFirstName(null);
        newOrder.setOwnerLastName(null);
        newOrder.setOwnerPatronymic(null);
        newOrder.setOwnerTitle(null);
    }

    @Override
    public void copySelectedCompanyInfo(Order newOrder, Contractor company) {
        if(newOrder!=null){
            if (company!=null){
                newOrder.setCompany(company);
                newOrder.setCompanyNameFull(company.getNameFull());
                newOrder.setCompanyNameShort(company.getNameShort());
                newOrder.setCompanyInn(company.getInn());
                newOrder.setCompanyKpp(company.getKpp());
                newOrder.setCompanyOkpo(company.getOkpo());
                newOrder.setCompanyOgrn(company.getOgrn());
                newOrder.setCompanyOkato(company.getOkato());
                newOrder.setCompanyNameWork(company.getNameWork());
                if(company.getOrganizationType()!=null){
                    newOrder.setCompanyOrganizationTypeId(company.getOrganizationType().getId());
                    newOrder.setCompanyOrganizationTypeNameFull(company.getOrganizationType().getNameFull());
                    newOrder.setCompanyOrganizationTypeNameShort(company.getOrganizationType().getNameShort());
                }
                if(company.getAgencyType()!=null){
                    newOrder.setCompanyAgencyTypeId(company.getAgencyType().getId());
                    newOrder.setCompanyAgencyType(company.getAgencyType().getType());
                    newOrder.setCompanyAgencyTypeName(company.getAgencyType().getName());
                }
            } else {
                resetCopySelectedCompanyInfo(newOrder);
            }
        }
    }
    private void resetCopySelectedCompanyInfo(Order newOrder){
        newOrder.setCompany(null);
        newOrder.setCompanyNameFull(null);
        newOrder.setCompanyNameShort(null);
        newOrder.setCompanyInn(null);
        newOrder.setCompanyKpp(null);
        newOrder.setCompanyOkpo(null);
        newOrder.setCompanyOgrn(null);
        newOrder.setCompanyOkato(null);
        newOrder.setCompanyNameWork(null);
        newOrder.setCompanyOrganizationTypeId(null);
        newOrder.setCompanyOrganizationTypeNameFull(null);
        newOrder.setCompanyOrganizationTypeNameShort(null);
        newOrder.setCompanyAgencyTypeId(null);
        newOrder.setCompanyAgencyType(null);
        newOrder.setCompanyAgencyTypeName(null);
    }

    @Override
    public void copySelectedTransportationCompanyInfo(Order newOrder, Contractor transportationCompany) {
        if(newOrder!=null){
            if (transportationCompany!=null){
                newOrder.setTransportationCompany(transportationCompany);
                newOrder.setTransportationCompanyNameFull(transportationCompany.getNameFull());
                newOrder.setTransportationCompanyNameShort(transportationCompany.getNameShort());
                newOrder.setTransportationCompanyInn(transportationCompany.getInn());
                newOrder.setTransportationCompanyKpp(transportationCompany.getKpp());
                newOrder.setTransportationCompanyOkpo(transportationCompany.getOkpo());
                newOrder.setTransportationCompanyOgrn(transportationCompany.getOgrn());
                newOrder.setTransportationCompanyOkato(transportationCompany.getOkato());
                newOrder.setTransportationCompanyNameWork(transportationCompany.getNameWork());
                if(transportationCompany.getOrganizationType()!=null){
                    newOrder.setTransportationCompanyOrganizationTypeId(transportationCompany.getOrganizationType().getId());
                    newOrder.setTransportationCompanyOrganizationTypeNameFull(transportationCompany.getOrganizationType().getNameFull());
                    newOrder.setTransportationCompanyOrganizationTypeNameShort(transportationCompany.getOrganizationType().getNameShort());
                }
                if(transportationCompany.getAgencyType()!=null){
                    newOrder.setTransportationCompanyAgencyTypeId(transportationCompany.getAgencyType().getId());
                    newOrder.setTransportationCompanyAgencyType(transportationCompany.getAgencyType().getType());
                    newOrder.setTransportationCompanyAgencyTypeName(transportationCompany.getAgencyType().getName());
                }
            } else {
                resetCopySelectedTransportationCompanyInfo(newOrder);
            }
        }
    }
    private void resetCopySelectedTransportationCompanyInfo(Order newOrder){
        newOrder.setTransportationCompany(null);
        newOrder.setTransportationCompanyNameFull(null);
        newOrder.setTransportationCompanyNameShort(null);
        newOrder.setTransportationCompanyInn(null);
        newOrder.setTransportationCompanyKpp(null);
        newOrder.setTransportationCompanyOkpo(null);
        newOrder.setTransportationCompanyOgrn(null);
        newOrder.setTransportationCompanyOkato(null);
        newOrder.setTransportationCompanyNameWork(null);
        newOrder.setTransportationCompanyOrganizationTypeId(null);
        newOrder.setTransportationCompanyOrganizationTypeNameFull(null);
        newOrder.setTransportationCompanyOrganizationTypeNameShort(null);
        newOrder.setTransportationCompanyAgencyTypeId(null);
        newOrder.setTransportationCompanyAgencyType(null);
        newOrder.setTransportationCompanyAgencyTypeName(null);
    }

    /* This part highly depend to each other, be careful */
    @Override
    public void copySelectedTransportationVehicleTypeInfo(Order newOrder, TransportationVehicleType transportationVehicleType, boolean resetOtherFields) {
        if (newOrder!=null) {
            if(transportationVehicleType!=null){
                newOrder.setTransportationVehicleType(transportationVehicleType);
                newOrder.setTransportationVehicleTypeType(transportationVehicleType.getType());
                newOrder.setTransportationVehicleTypeName(transportationVehicleType.getName());
            }
            resetCopySelectedTransportationVehicleTypeInfo(newOrder, transportationVehicleType==null, resetOtherFields);
        }
    }
    private void resetCopySelectedTransportationVehicleTypeInfo(Order newOrder, boolean resetItself, boolean resetOtherFields){
        if(newOrder!=null){
            if(resetItself){
                newOrder.setTransportationVehicleType(null);
                newOrder.setTransportationVehicleTypeType(null);
                newOrder.setTransportationVehicleTypeName(null);
            }
            if(resetOtherFields)
                resetCopySelectedTransportationCompanyTypeInfo(newOrder, true, true);
        }
    }

    @Override
    public void copySelectedTransportationCompanyTypeInfo(Order newOrder, TransportationCompanyType transportationCompanyType, boolean resetOtherFields) {
        if (newOrder!=null) {
            if(transportationCompanyType!=null){
                newOrder.setTransportationCompanyType(transportationCompanyType);
                newOrder.setTransportationCompanyTypeType(transportationCompanyType.getType());
                newOrder.setTransportationCompanyTypeName(transportationCompanyType.getName());
            }
            resetCopySelectedTransportationCompanyTypeInfo(newOrder, transportationCompanyType==null, resetOtherFields);
        }
    }
    private void resetCopySelectedTransportationCompanyTypeInfo(Order newOrder, boolean resetItself, boolean resetOtherFields){
        if (newOrder!=null){
            if(resetItself){
                newOrder.setTransportationCompanyType(null);
                newOrder.setTransportationCompanyTypeType(null);
                newOrder.setTransportationCompanyTypeName(null);
            }
            if(resetOtherFields)
                resetCopySelectedTransportationHiredVehicleTypeInfo(newOrder, true, true);
        }

    }

    @Override
    public void copySelectedTransportationHiredVehicleTypeInfo(Order newOrder, TransportationHiredVehicleType transportationHiredVehicleType, boolean resetOtherFields) {
        if (newOrder!=null) {
            if(transportationHiredVehicleType!=null){
                newOrder.setTransportationHiredVehicleType(transportationHiredVehicleType);
                newOrder.setTransportationHiredVehicleTypeType(transportationHiredVehicleType.getType());
                newOrder.setTransportationHiredVehicleTypeName(transportationHiredVehicleType.getName());
            }
            resetCopySelectedTransportationHiredVehicleTypeInfo(newOrder, transportationHiredVehicleType==null, resetOtherFields);
        }
    }
    private void resetCopySelectedTransportationHiredVehicleTypeInfo(Order newOrder, boolean resetItself, boolean resetOtherFields){
        if (newOrder!=null){
            if(resetItself){
                newOrder.setTransportationHiredVehicleType(null);
                newOrder.setTransportationHiredVehicleTypeType(null);
                newOrder.setTransportationHiredVehicleTypeName(null);
            }
            if(resetOtherFields){
                resetCopySelectedVehicleInfo(newOrder);
                resetCopySelectedDriverInfo(newOrder);
                resetCopySelectedCarrierInfo(newOrder);
                resetCopySelectedUserInfoForTransportManager(newOrder);
            }
        }
    }

    @Override
    public void copySelectedVehicleInfo(Order newOrder, Vehicle vehicle) {
        if (newOrder!=null) {
            if(vehicle!=null){
                newOrder.setVehicle(vehicle);
                newOrder.setVehicleDescription(vehicle.getDescription());
                /* Copy vehicle Carrier info */
                if(vehicle.getCarrier()!=null){
                    newOrder.setCarrier(vehicle.getCarrier());
                    copySelectedCarrierInfo(newOrder, vehicle.getCarrier());
                }
                /* Copy User info for Transportation Manager*/
                if(vehicle.getLogistician()!=null){
                    newOrder.setTransportManager(vehicle.getLogistician());
                    copySelectedUserInfoForTransportManager(newOrder, vehicle.getLogistician());
                }
                /* Copy vehicle Status info */
                if(vehicle.getStatus()!=null){
                    newOrder.setVehicleStatusId(vehicle.getStatus().getId());
                    newOrder.setVehicleStatusTitle(vehicle.getStatus().getLogisticianStatus().getTitle());
                    newOrder.setVehicleStatusCreated(vehicle.getStatus().getLogisticianStatus().getCreated());
                    newOrder.setVehicleStatusStatusDate(vehicle.getStatus().getLogisticianStatus().getStatusDate());
                    newOrder.setVehicleStatusDescription(vehicle.getStatus().getLogisticianStatus().getDescription());
                }
                /* Copy vehicle Truck info */
                if(vehicle.getTruck()!=null){
                    newOrder.setTruck(vehicle.getTruck());
                    newOrder.setTruckTitle(vehicle.getTruck().getTitle());
                    newOrder.setTruckBrand(vehicle.getTruck().getBrand());
                    newOrder.setTruckModel(vehicle.getTruck().getModel());
                    newOrder.setTruckGovNumber(vehicle.getTruck().getGovNumber());
                }
                /* Copy vehicle Trailer info */
                if(vehicle.getTrailer()!=null){
                    newOrder.setTrailer(vehicle.getTrailer());
                    newOrder.setTrailerTitle(vehicle.getTrailer().getTitle());
                    newOrder.setTrailerBrand(vehicle.getTrailer().getBrand());
                    newOrder.setTrailerModel(vehicle.getTrailer().getModel());
                    newOrder.setTrailerGovNumber(vehicle.getTrailer().getGovNumber());
                    newOrder.setTrailerCalibration(vehicle.getTrailer().getCalibration());
                    newOrder.setTrailerCapacity(vehicle.getTrailer().getCapacity());
                    newOrder.setTrailerBottomLoading(vehicle.getTrailer().getBottomLoading());
                    newOrder.setTrailerBottomLoadingCalibration(vehicle.getTrailer().getBottomLoadingCalibration());
                }
            } else {
                resetCopySelectedVehicleInfo(newOrder);
            }
        }
    }
    private void resetCopySelectedVehicleInfo(Order newOrder){
        newOrder.setVehicle(null);
        /* Reset values of vehicle Carrier info */
        newOrder.setCarrier(null);
        copySelectedCarrierInfo(newOrder, null);
                /* Reset values of vehicle Status info */
        newOrder.setVehicleStatusId(null);
        newOrder.setVehicleStatusTitle(null);
        newOrder.setVehicleStatusCreated(null);
        newOrder.setVehicleStatusStatusDate(null);
        newOrder.setVehicleStatusDescription(null);
                /* Reset values of User info for Transport Manager*/
        newOrder.setTransportManager(null);
        copySelectedUserInfoForTransportManager(newOrder, null);
        if(newOrder.getTransportationCompanyType()!=null && !TransportationCompanyTypeEnum.SELF.getType().equals(newOrder.getTransportationCompanyType().getType())){
            newOrder.setVehicleDescription(null);
                    /* Reset values of vehicle Truck info */
            newOrder.setTruck(null);
            newOrder.setTruckTitle(null);
            newOrder.setTruckBrand(null);
            newOrder.setTruckModel(null);
            newOrder.setTruckGovNumber(null);
                    /* Reset values of vehicle Trailer info */
            newOrder.setTrailer(null);
            newOrder.setTrailerTitle(null);
            newOrder.setTrailerBrand(null);
            newOrder.setTrailerModel(null);
            newOrder.setTrailerGovNumber(null);
            newOrder.setTrailerCalibration(null);
            newOrder.setTrailerCapacity(null);
            newOrder.setTrailerBottomLoading(null);
            newOrder.setTrailerBottomLoadingCalibration(null);
        }
    }

    @Override
    public void copySelectedDriverInfo(Order newOrder, Driver driver) {
        if (newOrder!=null) {
            if(driver!=null){
                newOrder.setDriver(driver);
                newOrder.setDriverTitle(driver.getTitle());
                newOrder.setDriverFirstName(driver.getFirstName());
                newOrder.setDriverLastName(driver.getLastName());
                newOrder.setDriverPatronymic(driver.getPatronymic());
                newOrder.setDriverDateOfBirth(driver.getDateOfBirth());
                newOrder.setDriverDocSerial(driver.getDocSerial());
                newOrder.setDriverDocNumber(driver.getDocNumber());
                newOrder.setDriverDocGivenDate(driver.getDocGivenDate());
                newOrder.setDriverDocGivenFrom(driver.getDocGivenFrom());
                newOrder.setDriverDocCode(driver.getDocCode());
                newOrder.setDriverDocDescription(driver.getDocDescription());
                newOrder.setDriverPhone1(driver.getPhone1());
                newOrder.setDriverPhone2(driver.getPhone2());
                newOrder.setDriverPhoneEmergency(driver.getPhoneEmergency());
            } else {
                resetCopySelectedDriverInfo(newOrder);
            }
        }
    }
    private void resetCopySelectedDriverInfo(Order newOrder){
        newOrder.setDriver(null);
        if(newOrder.getTransportationCompanyType()!=null && !TransportationCompanyTypeEnum.SELF.getType().equals(newOrder.getTransportationCompanyType().getType())) {
            newOrder.setDriverTitle(null);
            newOrder.setDriverFirstName(null);
            newOrder.setDriverLastName(null);
            newOrder.setDriverPatronymic(null);
            newOrder.setDriverDateOfBirth(null);
            newOrder.setDriverDocSerial(null);
            newOrder.setDriverDocNumber(null);
            newOrder.setDriverDocGivenDate(null);
            newOrder.setDriverDocGivenFrom(null);
            newOrder.setDriverDocCode(null);
            newOrder.setDriverDocDescription(null);
            newOrder.setDriverPhone1(null);
            newOrder.setDriverPhone2(null);
            newOrder.setDriverPhoneEmergency(null);
        }
    }

    @Override
    public void copySelectedCarrierInfo(Order newOrder, Contractor carrier) {
        if (newOrder!=null) {
            if(carrier!=null){
                newOrder.setCarrier(carrier);
                newOrder.setCarrierNameFull(carrier.getNameFull());
                newOrder.setCarrierNameShort(carrier.getNameShort());
                newOrder.setCarrierInn(carrier.getInn());
                newOrder.setCarrierKpp(carrier.getKpp());
                newOrder.setCarrierOkpo(carrier.getOkpo());
                newOrder.setCarrierOgrn(carrier.getOgrn());
                newOrder.setCarrierOkato(carrier.getOkato());
                newOrder.setCarrierNameWork(carrier.getNameWork());
                if(carrier.getOrganizationType()!=null){
                    newOrder.setCarrierOrganizationTypeId(carrier.getOrganizationType().getId());
                    newOrder.setCarrierOrganizationTypeNameFull(carrier.getOrganizationType().getNameFull());
                    newOrder.setCarrierOrganizationTypeNameShort(carrier.getOrganizationType().getNameShort());
                }
                if(carrier.getAgencyType()!=null){
                    newOrder.setCarrierAgencyTypeId(carrier.getAgencyType().getId());
                    newOrder.setCarrierAgencyType(carrier.getAgencyType().getType());
                    newOrder.setCarrierAgencyTypeName(carrier.getAgencyType().getName());
                }
            } else {
                resetCopySelectedCarrierInfo(newOrder);
            }
        }
    }
    private void resetCopySelectedCarrierInfo(Order newOrder){
        newOrder.setCarrier(null);
        newOrder.setCarrierNameFull(null);
        newOrder.setCarrierNameShort(null);
        newOrder.setCarrierInn(null);
        newOrder.setCarrierKpp(null);
        newOrder.setCarrierOkpo(null);
        newOrder.setCarrierOgrn(null);
        newOrder.setCarrierOkato(null);
        newOrder.setCarrierNameWork(null);
        newOrder.setCarrierOrganizationTypeId(null);
        newOrder.setCarrierOrganizationTypeNameFull(null);
        newOrder.setCarrierOrganizationTypeNameShort(null);
        newOrder.setCarrierAgencyTypeId(null);
        newOrder.setCarrierAgencyType(null);
        newOrder.setCarrierAgencyTypeName(null);
    }

    @Override
    public void copySelectedUserInfoForTransportManager(Order newOrder, User transportManager) {
        if(newOrder!=null){
            if (transportManager!=null){
                newOrder.setTransportManager(transportManager);
                newOrder.setTransportManagerUsername(transportManager.getUsername());
                newOrder.setTransportManagerFirstName(transportManager.getFirstName());
                newOrder.setTransportManagerLastName(transportManager.getLastName());
                newOrder.setTransportManagerPatronymic(transportManager.getPatronymic());
                newOrder.setTransportManagerTitle(transportManager.getTitle());
            } else {
                resetCopySelectedUserInfoForTransportManager(newOrder);
            }
        }
    }
    private void resetCopySelectedUserInfoForTransportManager(Order newOrder){
        newOrder.setTransportManager(null);
        newOrder.setTransportManagerUsername(null);
        newOrder.setTransportManagerFirstName(null);
        newOrder.setTransportManagerLastName(null);
        newOrder.setTransportManagerPatronymic(null);
        newOrder.setTransportManagerTitle(null);
    }

    @Override
    public void copySelectedCustomerInfo(Order newOrder, Customer customer) {
        if (newOrder!=null) {
            if(customer!=null){
                newOrder.setCustomer(customer);
                newOrder.setCustomerContacts(customer.getContacts());
                newOrder.setCustomerTitle(customer.getTitle());
            } else {
                resetCopySelectedCustomerInfo(newOrder);
            }
        }
    }
    private void resetCopySelectedCustomerInfo(Order newOrder){
        newOrder.setCustomer(null);
        newOrder.setCustomerContacts(null);
        newOrder.setCustomerTitle(null);
    }

    @Override
    public void copySelectedContractorInfo(Order newOrder, Contractor contractor) {
        if (newOrder!=null) {
            if(contractor!=null){
                newOrder.setContractor(contractor);
                newOrder.setContractorNameFull(contractor.getNameFull());
                newOrder.setContractorNameShort(contractor.getNameShort());
                newOrder.setContractorInn(contractor.getInn());
                newOrder.setContractorKpp(contractor.getKpp());
                newOrder.setContractorOkpo(contractor.getOkpo());
                newOrder.setContractorOgrn(contractor.getOgrn());
                newOrder.setContractorOkato(contractor.getOkato());
                newOrder.setContractorNameWork(contractor.getNameWork());
                if(contractor.getOrganizationType()!=null){
                    newOrder.setContractorOrganizationTypeId(contractor.getOrganizationType().getId());
                    newOrder.setContractorOrganizationTypeNameFull(contractor.getOrganizationType().getNameFull());
                    newOrder.setContractorOrganizationTypeNameShort(contractor.getOrganizationType().getNameShort());
                }
                if(contractor.getAgencyType()!=null){
                    newOrder.setContractorAgencyTypeId(contractor.getAgencyType().getId());
                    newOrder.setContractorAgencyType(contractor.getAgencyType().getType());
                    newOrder.setContractorAgencyTypeName(contractor.getAgencyType().getName());
                }
            } else {
                resetCopySelectedContractorInfo(newOrder);
            }
        }
    }
    private void resetCopySelectedContractorInfo(Order newOrder){
        newOrder.setContractor(null);
        newOrder.setContractorNameFull(null);
        newOrder.setContractorNameShort(null);
        newOrder.setContractorInn(null);
        newOrder.setContractorKpp(null);
        newOrder.setContractorOkpo(null);
        newOrder.setContractorOgrn(null);
        newOrder.setContractorOkato(null);
        newOrder.setContractorNameWork(null);
        newOrder.setContractorOrganizationTypeId(null);
        newOrder.setContractorOrganizationTypeNameFull(null);
        newOrder.setContractorOrganizationTypeNameShort(null);
        newOrder.setContractorAgencyTypeId(null);
        newOrder.setContractorAgencyType(null);
        newOrder.setContractorAgencyTypeName(null);
    }

    @Override
    public void copySelectedTransportationContractorInfo(Order newOrder, Contractor transportationContractor) {
        if (newOrder!=null) {
            if(transportationContractor!=null){
                newOrder.setTransportationContractor(transportationContractor);
                newOrder.setTransportationContractorNameFull(transportationContractor.getNameFull());
                newOrder.setTransportationContractorNameShort(transportationContractor.getNameShort());
                newOrder.setTransportationContractorInn(transportationContractor.getInn());
                newOrder.setTransportationContractorKpp(transportationContractor.getKpp());
                newOrder.setTransportationContractorOkpo(transportationContractor.getOkpo());
                newOrder.setTransportationContractorOgrn(transportationContractor.getOgrn());
                newOrder.setTransportationContractorOkato(transportationContractor.getOkato());
                newOrder.setTransportationContractorNameWork(transportationContractor.getNameWork());
                if(transportationContractor.getOrganizationType()!=null){
                    newOrder.setTransportationContractorOrganizationTypeId(transportationContractor.getOrganizationType().getId());
                    newOrder.setTransportationContractorOrganizationTypeNameFull(transportationContractor.getOrganizationType().getNameFull());
                    newOrder.setTransportationContractorOrganizationTypeNameShort(transportationContractor.getOrganizationType().getNameShort());
                }
                if(transportationContractor.getAgencyType()!=null){
                    newOrder.setTransportationContractorAgencyTypeId(transportationContractor.getAgencyType().getId());
                    newOrder.setTransportationContractorAgencyType(transportationContractor.getAgencyType().getType());
                    newOrder.setTransportationContractorAgencyTypeName(transportationContractor.getAgencyType().getName());
                }
            } else {
                resetCopySelectedTransportationContractorInfo(newOrder);
            }
        }
    }
    private void resetCopySelectedTransportationContractorInfo(Order newOrder){
        newOrder.setTransportationContractor(null);
        newOrder.setTransportationContractorNameFull(null);
        newOrder.setTransportationContractorNameShort(null);
        newOrder.setTransportationContractorInn(null);
        newOrder.setTransportationContractorKpp(null);
        newOrder.setTransportationContractorOkpo(null);
        newOrder.setTransportationContractorOgrn(null);
        newOrder.setTransportationContractorOkato(null);
        newOrder.setTransportationContractorNameWork(null);
        newOrder.setTransportationContractorOrganizationTypeId(null);
        newOrder.setTransportationContractorOrganizationTypeNameFull(null);
        newOrder.setTransportationContractorOrganizationTypeNameShort(null);
        newOrder.setTransportationContractorAgencyTypeId(null);
        newOrder.setTransportationContractorAgencyType(null);
        newOrder.setTransportationContractorAgencyTypeName(null);
    }

    @Override
    public void copySelectedAgreementInfo(Order newOrder, Agreement agreement) {
        if(newOrder!=null){
            if(agreement!=null){
                newOrder.setAgreement(agreement);
                newOrder.setAgreementNumber(agreement.getAgreementNumber());
                newOrder.setAgreementCreated(agreement.getCreated());
                newOrder.setAgreementValidFrom(agreement.getValidFrom());
                newOrder.setAgreementValidTo(agreement.getValidTo());
                newOrder.setAgreementDescription(agreement.getDescription());
                if(agreement.getStatus()!=null){
                    newOrder.setAgreementStatusId(agreement.getStatus().getId());
                    newOrder.setAgreementStatusType(agreement.getStatus().getType());
                    newOrder.setAgreementStatusName(agreement.getStatus().getName());
                }
                if(agreement.getType()!=null){
                    newOrder.setAgreementTypeId(agreement.getType().getId());
                    newOrder.setAgreementType(agreement.getType().getType());
                    newOrder.setAgreementTypeName(agreement.getType().getName());
                }
            } else {
                resetCopySelectedAgreementInfo(newOrder);
            }
        }
    }
    private void resetCopySelectedAgreementInfo(Order newOrder){
        newOrder.setAgreement(null);
        newOrder.setAgreementNumber(null);
        newOrder.setAgreementCreated(null);
        newOrder.setAgreementValidFrom(null);
        newOrder.setAgreementValidTo(null);
        newOrder.setAgreementDescription(null);
        newOrder.setAgreementStatusId(null);
        newOrder.setAgreementStatusType(null);
        newOrder.setAgreementStatusName(null);
        newOrder.setAgreementTypeId(null);
        newOrder.setAgreementType(null);
        newOrder.setAgreementTypeName(null);
    }

    @Override
    public void copySelectedTransportationAgreementInfo(Order newOrder, Agreement transportationAgreement) {
        if(newOrder!=null){
            if(transportationAgreement!=null){
                newOrder.setTransportationAgreement(transportationAgreement);
                newOrder.setTransportationAgreementNumber(transportationAgreement.getAgreementNumber());
                newOrder.setTransportationAgreementCreated(transportationAgreement.getCreated());
                newOrder.setTransportationAgreementValidFrom(transportationAgreement.getValidFrom());
                newOrder.setTransportationAgreementValidTo(transportationAgreement.getValidTo());
                newOrder.setTransportationAgreementDescription(transportationAgreement.getDescription());
                if(transportationAgreement.getStatus()!=null){
                    newOrder.setTransportationAgreementStatusId(transportationAgreement.getStatus().getId());
                    newOrder.setTransportationAgreementStatusType(transportationAgreement.getStatus().getType());
                    newOrder.setTransportationAgreementStatusName(transportationAgreement.getStatus().getName());
                }
                if(transportationAgreement.getType()!=null){
                    newOrder.setTransportationAgreementTypeId(transportationAgreement.getType().getId());
                    newOrder.setTransportationAgreementType(transportationAgreement.getType().getType());
                    newOrder.setTransportationAgreementTypeName(transportationAgreement.getType().getName());
                }
            } else {
                resetCopySelectedTransportationAgreementInfo(newOrder);
            }
        }
    }
    private void resetCopySelectedTransportationAgreementInfo(Order newOrder){
        newOrder.setTransportationAgreement(null);
        newOrder.setTransportationAgreementNumber(null);
        newOrder.setTransportationAgreementCreated(null);
        newOrder.setTransportationAgreementValidFrom(null);
        newOrder.setTransportationAgreementValidTo(null);
        newOrder.setTransportationAgreementDescription(null);
        newOrder.setTransportationAgreementStatusId(null);
        newOrder.setTransportationAgreementStatusType(null);
        newOrder.setTransportationAgreementStatusName(null);
        newOrder.setTransportationAgreementTypeId(null);
        newOrder.setTransportationAgreementType(null);
        newOrder.setTransportationAgreementTypeName(null);
    }

    @Override
    public void copySelectedAdditionalAgreementInfo(Order newOrder, Agreement additionalAgreement) {
        if(newOrder!=null){
            if(additionalAgreement!=null){
                newOrder.setAdditionalAgreement(additionalAgreement);
                newOrder.setAdditionalAgreementNumber(additionalAgreement.getAgreementNumber());
                newOrder.setAdditionalAgreementCreated(additionalAgreement.getCreated());
                newOrder.setAdditionalAgreementValidFrom(additionalAgreement.getValidFrom());
                newOrder.setAdditionalAgreementValidTo(additionalAgreement.getValidTo());
                newOrder.setAdditionalAgreementDescription(additionalAgreement.getDescription());
                if(additionalAgreement.getStatus()!=null){
                    newOrder.setAdditionalAgreementStatus(newOrder.getAdditionalAgreementStatus());
                    newOrder.setAdditionalAgreementStatusType(additionalAgreement.getStatus().getType());
                    newOrder.setAdditionalAgreementStatusName(additionalAgreement.getStatus().getName());
                }
                if(additionalAgreement.getType()!=null){
                    newOrder.setAdditionalAgreementTypeId(additionalAgreement.getType().getId());
                    newOrder.setAdditionalAgreementType(additionalAgreement.getType().getType());
                    newOrder.setAdditionalAgreementTypeName(additionalAgreement.getType().getName());
                }
            } else {
                resetCopySelectedAdditionalAgreementInfo(newOrder);
            }
        }
    }
    private void resetCopySelectedAdditionalAgreementInfo(Order newOrder){
        newOrder.setAdditionalAgreement(null);
        newOrder.setAdditionalAgreementCreated(null);
        newOrder.setAdditionalAgreementValidFrom(null);
        newOrder.setAdditionalAgreementValidTo(null);
        newOrder.setAdditionalAgreementDescription(null);
        newOrder.setAdditionalAgreementTypeId(null);
        newOrder.setAdditionalAgreementType(null);
        newOrder.setAdditionalAgreementTypeName(null);
        if(newOrder.getAdditionalAgreementNumber()==null || !newOrder.getAdditionalAgreementNumber().isEmpty()){
            newOrder.setAdditionalAgreementNumber(null);
            newOrder.setAdditionalAgreementStatus(null);
            newOrder.setAdditionalAgreementStatusType(null);
            newOrder.setAdditionalAgreementStatusName(null);
        }
    }

    @Override
    public void copySelectedOrderPaymentTypeInfo(Order newOrder, OrderPaymentType orderPaymentType) {
        if(newOrder!=null){
            if(orderPaymentType!=null){
                newOrder.setOrderPaymentType(orderPaymentType);
                newOrder.setOrderPaymentTypeType(orderPaymentType.getType());
                newOrder.setOrderPaymentTypeName(orderPaymentType.getName());
            } else {
                resetCopySelectedOrderPaymentTypeInfo(newOrder);
            }
        }
    }
    private void resetCopySelectedOrderPaymentTypeInfo(Order newOrder){
        newOrder.setOrderPaymentType(null);
        newOrder.setOrderPaymentTypeType(null);
        newOrder.setOrderPaymentTypeName(null);
    }

    @Override
    public void copySelectedProviderInfo(Order newOrder, Contractor provider) {
        if (newOrder!=null) {
            if(provider!=null){
                newOrder.setProvider(provider);
                newOrder.setProviderNameFull(provider.getNameFull());
                newOrder.setProviderNameShort(provider.getNameShort());
                newOrder.setProviderInn(provider.getInn());
                newOrder.setProviderKpp(provider.getKpp());
                newOrder.setProviderOkpo(provider.getOkpo());
                newOrder.setProviderOgrn(provider.getOgrn());
                newOrder.setProviderOkato(provider.getOkato());
                newOrder.setProviderNameWork(provider.getNameWork());
                if(provider.getOrganizationType()!=null){
                    newOrder.setProviderOrganizationTypeId(provider.getOrganizationType().getId());
                    newOrder.setProviderOrganizationTypeNameFull(provider.getOrganizationType().getNameFull());
                    newOrder.setProviderOrganizationTypeNameShort(provider.getOrganizationType().getNameShort());
                }
                if(provider.getAgencyType()!=null){
                    newOrder.setProviderAgencyTypeId(provider.getAgencyType().getId());
                    newOrder.setProviderAgencyType(provider.getAgencyType().getType());
                    newOrder.setProviderAgencyTypeName(provider.getAgencyType().getName());
                }
            } else {
                resetCopySelectedProviderInfo(newOrder);
            }
        }
    }
    private void resetCopySelectedProviderInfo(Order newOrder){
        newOrder.setProvider(null);
        newOrder.setProviderNameFull(null);
        newOrder.setProviderNameShort(null);
        newOrder.setProviderInn(null);
        newOrder.setProviderKpp(null);
        newOrder.setProviderOkpo(null);
        newOrder.setProviderOgrn(null);
        newOrder.setProviderOkato(null);
        newOrder.setProviderNameWork(null);
        newOrder.setProviderOrganizationTypeId(null);
        newOrder.setProviderOrganizationTypeNameFull(null);
        newOrder.setProviderOrganizationTypeNameShort(null);
        newOrder.setProviderAgencyTypeId(null);
        newOrder.setProviderAgencyType(null);
        newOrder.setProviderAgencyTypeName(null);
    }












}
