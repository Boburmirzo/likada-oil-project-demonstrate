package pro.likada.bean.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.likada.bean.backing.OrderBackingBean;
import pro.likada.bean.model.OrderModelBean;
import pro.likada.model.*;
import pro.likada.service.*;
import pro.likada.util.*;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Yusupov on 3/10/2017.
 */
@Named
@RequestScoped
public class OrderController implements Serializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    @Inject
    private OrderBackingBean orderBackingBean;
    @Inject
    private OrderModelBean orderModelBean;
    @Inject
    private OrderService orderService;
    @Inject
    private OrderStatusTypeService orderStatusTypeService;
    @Inject
    private UserService userService;
    @Inject
    private ContractorService contractorService;
    @Inject
    private TransportationVehicleTypeService transportationVehicleTypeService;
    @Inject
    private TransportationCompanyTypeService transportationCompanyTypeService;
    @Inject
    private TransportationHiredVehicleTypeService transportationHiredVehicleTypeService;
    @Inject
    private VehicleService vehicleService;
    @Inject
    private DriverService driverService;
    @Inject
    private AgreementService agreementService;
    @Inject
    private OrderPaymentTypeService orderPaymentTypeService;


    /* Delete selected order in the table from the database, and update table data (orders) */
    public void deleteSelectedOrder(){
        if(orderBackingBean.getSelectedOrder()!=null) {
            LOGGER.info("Delete Order: {}", orderBackingBean.getSelectedOrder());
            orderService.delete(orderBackingBean.getSelectedOrder());
            orderBackingBean.setSelectedOrder(null);
            refreshOrdersTable();

        }
    }

    /* Refresh Orders table data (searchString is handled in DAO) */
    public void refreshOrdersTable(){
        LOGGER.info("Refreshing Orders table, filter according: " + orderBackingBean.getSearchStringInOrderTable());
        orderBackingBean.setOrdersForTable(
                orderService.findAllOrdersBasedOnSearchStringAndFilterPeriod(
                        orderBackingBean.getSearchStringInOrderTable(),
                        orderBackingBean.getSelectedFilterPeriodOneButton()
                )
        );
    }

    /* Reset search filter data, and refresh the table of Orders */
    public void resetOrdersTableSearchFilter() {
        LOGGER.info("Reset Orders table");
        orderBackingBean.setSearchStringInOrderTable(null);
        orderBackingBean.setSelectedOrder(null);
        refreshOrdersTable();
    }

    /* Create a new Order and redirect to edit page */
    public void buttonActionAddOrder(ActionEvent actionEvent) {
        Order newOrder = new Order();
        orderService.copyCurrentUserInfoForCreatorAndOwner(newOrder, null);
        newOrder.setCreatedDate(Calendar.getInstance().getTime());
        newOrder.setNumberOfVehicles(1);
        orderModelBean.setSelectedOrder(newOrder);
        redirectToEditPage();
    }

    /* Get Orders list for table */
    public List<Order> getOrders(){
        if(orderBackingBean.getOrdersForTable()==null) {
            LOGGER.info("Unable to load orders from bean, load from database.");
            refreshOrdersTable();
        }
        return orderBackingBean.getOrdersForTable();
    }

    /* After double click (to edit chosen Order) redirect to edit page */
    public void doubleClickSelectedRowInTableOrders(){
        editOrder(orderBackingBean.getSelectedOrder());
    }

    /* After pressing buttons for edit, set fields for chosen Order and redirect to edit page */
    public void editOrder(Order order){
        LOGGER.info("Passing chosen Order to editing page");
        orderModelBean.setSelectedOrder(order);
        redirectToEditPage();
    }

    /* Addtional methods */
    public String getTransportInfoForTableOrders(Order order){  // TODO try to find out other way of implementation of this
        String transport = "";
        if(TransportationVehicleTypeEnum.RAILWAY.getType().equals(order.getTransportationVehicleTypeType())){
            transport += TransportationVehicleTypeEnum.RAILWAY.getName() + " ";
        } else if (TransportationVehicleTypeEnum.AUTO.getType().equals(order.getTransportationVehicleTypeType())){
            if(TransportationCompanyTypeEnum.HIRED.getType().equals(order.getTransportationCompanyTypeType())){
                transport += TransportationCompanyTypeEnum.HIRED.getName() + " ";
                if(TransportationHiredVehicleTypeEnum.INTERNAL.getType().equals(order.getTransportationHiredVehicleTypeType())){
                    transport += TransportationHiredVehicleTypeEnum.INTERNAL.getName() + " ";
                } else if (TransportationHiredVehicleTypeEnum.EXTERNAL.getType().equals(order.getTransportationHiredVehicleTypeType())){
                    transport += TransportationHiredVehicleTypeEnum.EXTERNAL.getName() + " ";
                } else {
                    transport += TransportationHiredVehicleTypeEnum.SEARCH.getName() + " ";
                }
            } else if(TransportationCompanyTypeEnum.SELF.getType().equals(order.getTransportationCompanyTypeType())){
                transport += TransportationCompanyTypeEnum.SELF.getName() + " ";
            } else if(TransportationCompanyTypeEnum.TRANSPORT.getType().equals(order.getTransportationCompanyTypeType())){
                transport += TransportationCompanyTypeEnum.TRANSPORT.getName() + " ";
            } else {
                transport += "Компания транспортировки не указан";
            }
        } else {
            transport = "Не указан";
        }
        return transport;
    }

    private void redirectToEditPage(){
        LOGGER.info("Redirecting to Edit Order page");
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("edit_order.xhtml");
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
    }

    /** ----  EDIT/CREATE Order page functionality  ---- **/
    public void buttonActionSaveOrder(ActionEvent actionEvent){
        LOGGER.info("Save the Order");
        /* Encapsulate everything */
        if(orderBackingBean.getSelectedOrder()!=null){
            orderService.copySelectedOrderStatusTypeInfo(orderBackingBean.getSelectedOrder(), orderBackingBean.getSelectedOrder().getOrderStatusType());
            orderService.copySelectedProductGroupInfo(orderBackingBean.getSelectedOrder(), orderBackingBean.getSelectedOrder().getProductGroup());
            orderService.copySelectedProductInfo(orderBackingBean.getSelectedOrder(), orderBackingBean.getSelectedOrder().getProduct());
            orderService.copyShipmentBasisInfo(orderBackingBean.getSelectedOrder(), orderBackingBean.getSelectedOrder().getShipmentBase());
            orderService.copySelectedUserInfoForCreator(orderBackingBean.getSelectedOrder(), orderBackingBean.getSelectedOrder().getCreator());
            orderService.copySelectedUserInfoForOwner(orderBackingBean.getSelectedOrder(), orderBackingBean.getSelectedOrder().getOwner());
            orderService.copySelectedCompanyInfo(orderBackingBean.getSelectedOrder(), orderBackingBean.getSelectedOrder().getCompany());
            orderService.copySelectedTransportationCompanyInfo(orderBackingBean.getSelectedOrder(), orderBackingBean.getSelectedOrder().getTransportationCompany());
            orderService.copySelectedTransportationVehicleTypeInfo(orderBackingBean.getSelectedOrder(), orderBackingBean.getSelectedOrder().getTransportationVehicleType(), false);
            orderService.copySelectedTransportationCompanyTypeInfo(orderBackingBean.getSelectedOrder(), orderBackingBean.getSelectedOrder().getTransportationCompanyType(), false);
            orderService.copySelectedTransportationHiredVehicleTypeInfo(orderBackingBean.getSelectedOrder(), orderBackingBean.getSelectedOrder().getTransportationHiredVehicleType(), false);
            orderService.copySelectedVehicleInfo(orderBackingBean.getSelectedOrder(), orderBackingBean.getSelectedOrder().getVehicle());
            orderService.copySelectedDriverInfo(orderBackingBean.getSelectedOrder(), orderBackingBean.getSelectedOrder().getDriver());
            orderService.copySelectedDriverInfo(orderBackingBean.getSelectedOrder(), orderBackingBean.getSelectedOrder().getDriver());
            orderService.copySelectedCarrierInfo(orderBackingBean.getSelectedOrder(), orderBackingBean.getSelectedOrder().getCarrier());
            orderService.copySelectedUserInfoForTransportManager(orderBackingBean.getSelectedOrder(), orderBackingBean.getSelectedOrder().getTransportManager());
            orderService.copySelectedCustomerInfo(orderBackingBean.getSelectedOrder(), orderBackingBean.getSelectedOrder().getCustomer());
            orderService.copySelectedContractorInfo(orderBackingBean.getSelectedOrder(), orderBackingBean.getSelectedOrder().getContractor());
            orderService.copySelectedTransportationContractorInfo(orderBackingBean.getSelectedOrder(), orderBackingBean.getSelectedOrder().getTransportationContractor());
            orderService.copySelectedAgreementInfo(orderBackingBean.getSelectedOrder(), orderBackingBean.getSelectedOrder().getAgreement());
            orderService.copySelectedTransportationAgreementInfo(orderBackingBean.getSelectedOrder(), orderBackingBean.getSelectedOrder().getTransportationAgreement());
            orderService.copySelectedAdditionalAgreementInfo(orderBackingBean.getSelectedOrder(), orderBackingBean.getSelectedOrder().getAdditionalAgreement());
            orderService.copySelectedOrderPaymentTypeInfo(orderBackingBean.getSelectedOrder(), orderBackingBean.getSelectedOrder().getOrderPaymentType());
            orderService.copySelectedProviderInfo(orderBackingBean.getSelectedOrder(), orderBackingBean.getSelectedOrder().getProvider());
        }
        orderService.save(orderBackingBean.getSelectedOrder());
        refreshOrdersTable();
        redirectToOrdersPage();
    }

    public void buttonActionCancelSaveOrder(ActionEvent actionEvent){
        LOGGER.info("Cancel saving the Order");
        refreshOrdersTable();
        redirectToOrdersPage();
    }

    private void redirectToOrdersPage(){
        LOGGER.info("Redirecting to Orders pages");
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("orders.xhtml");
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
    }

    public List<OrderStatusType> getOrderStatusTypeList(){
        return orderStatusTypeService.getAllOrderStatusTypes();
    }

    public void orderStatusTypeChangeListener(){
        orderService.copySelectedOrderStatusTypeInfo(orderBackingBean.getSelectedOrder(), orderBackingBean.getSelectedOrder().getOrderStatusType());
    }

    public void productChangeListener(Product product){
        orderService.copySelectedProductInfo(orderBackingBean.getSelectedOrder(), product);
    }

    public List<User> getUsersForCreator(){
        return userService.getAllUsers();
    }

    public void creatorChangeListener(){
        orderService.copySelectedUserInfoForCreator(orderBackingBean.getSelectedOrder(), orderBackingBean.getSelectedOrder().getCreator());
    }

    public List<User> getUsersForOwner(){
        return userService.getAllUsers();
    }

    public void ownerChangeListener(){
        orderService.copySelectedUserInfoForOwner(orderBackingBean.getSelectedOrder(), orderBackingBean.getSelectedOrder().getOwner());
    }

    public List<Contractor> getAllCompanies(){
        return contractorService.findAllCompaniesToAssignAsClient();
    }

    public void companyChangeListener(){
        orderService.copySelectedCompanyInfo(orderBackingBean.getSelectedOrder(), orderBackingBean.getSelectedOrder().getCompany());
    }

    public List<Contractor> getAllTransportationCompanies(){
        return contractorService.findAllCompaniesToAssignAsClient();
    }

    public void transportationCompanyChangeListener(){
        orderService.copySelectedTransportationCompanyInfo(orderBackingBean.getSelectedOrder(), orderBackingBean.getSelectedOrder().getTransportationCompany());
    }

    public List<TransportationVehicleType> getAllTransportationVehicleTypes(){
        return transportationVehicleTypeService.getAllTransportationVehicleTypes();
    }

    public void transportationVehicleTypeChangeListener(){
        orderService.copySelectedTransportationVehicleTypeInfo(orderBackingBean.getSelectedOrder(), orderBackingBean.getSelectedOrder().getTransportationVehicleType(), true);
    }

    public List<TransportationCompanyType> getAllTransportationCompanyTypes(){
        return transportationCompanyTypeService.getAllTransportationCompanyTypes();
    }

    public void transportationCompanyTypeChangeListener(){
        orderService.copySelectedTransportationCompanyTypeInfo(orderBackingBean.getSelectedOrder(), orderBackingBean.getSelectedOrder().getTransportationCompanyType(), true);
    }

    public List<TransportationHiredVehicleType> getAllTransportationHiredVehicleTypes(){
        return transportationHiredVehicleTypeService.getAllTransportationHiredVehicleTypes();
    }

    public void transportationHiredVehicleTypeChangeListener(){
        orderService.copySelectedTransportationHiredVehicleTypeInfo(orderBackingBean.getSelectedOrder(), orderBackingBean.getSelectedOrder().getTransportationHiredVehicleType(), true);
    }

    public List<Vehicle> getAllVehicles(){
        return vehicleService.findAllVehicles(null);
    }

    public void vehicleChangeListener(){
        orderService.copySelectedVehicleInfo(orderBackingBean.getSelectedOrder(), orderBackingBean.getSelectedOrder().getVehicle());
    }

    public List<Driver> getAllDrivers(){
        return driverService.findAllDrivers(null);
    }

    public void driverChangeListener(){
        orderService.copySelectedDriverInfo(orderBackingBean.getSelectedOrder(), orderBackingBean.getSelectedOrder().getDriver());
    }

    public List<Contractor> getAllCarriers(){
        if(orderBackingBean.getSelectedOrder()!=null && orderBackingBean.getSelectedOrder().getTransportationHiredVehicleType()!=null
                && TransportationHiredVehicleTypeEnum.EXTERNAL.getType().equals(orderBackingBean.getSelectedOrder().getTransportationHiredVehicleType().getType()))
            return contractorService.findAllCarriersBasedOnOurs(false);
        return contractorService.findAllCarriersBasedOnOurs(true);
    }

    public void carrierChangeListener(){
        orderService.copySelectedCarrierInfo(orderBackingBean.getSelectedOrder(), orderBackingBean.getSelectedOrder().getCarrier());
    }

    public List<User> getAllUsersForTransportManager(){
        return userService.findByRole(RoleEnum.LOGISTICS.getRoleType());
    }

    public void transportManagerChangeListener(){
        orderService.copySelectedUserInfoForTransportManager(orderBackingBean.getSelectedOrder(), orderBackingBean.getSelectedOrder().getTransportManager());
    }

    public List<Contractor> getAllContractorsOfSelectedCustomer(){
        if(orderBackingBean.getSelectedOrder()!=null && orderBackingBean.getSelectedOrder().getCustomer()!=null)
            return orderBackingBean.getSelectedOrder().getCustomer().getContractorsList();
        return null;
    }

    public void contractorChangeListener(){
        orderService.copySelectedContractorInfo(orderBackingBean.getSelectedOrder(), orderBackingBean.getSelectedOrder().getContractor());
    }

    public void transportationContractorChangeListener(){
        orderService.copySelectedTransportationContractorInfo(orderBackingBean.getSelectedOrder(), orderBackingBean.getSelectedOrder().getTransportationContractor());
    }

    public List<Agreement> getAllAgreementsOfTypeSale(){
        if(orderBackingBean.getSelectedOrder()!=null && orderBackingBean.getSelectedOrder().getCompany()!=null && orderBackingBean.getSelectedOrder().getContractor()!=null)
            return agreementService.findAllAgreementsBasedOnCompanyAndContractor(orderBackingBean.getSelectedOrder().getCompany(), orderBackingBean.getSelectedOrder().getContractor(), AgreementTypeEnum.SALE.getType());
        return null;
    }

    public void agreementChangeListener(){
        orderService.copySelectedAgreementInfo(orderBackingBean.getSelectedOrder(), orderBackingBean.getSelectedOrder().getAgreement());
    }

    public List<Agreement> getAllAgreementsOfTypeTransport(){
        if(orderBackingBean.getSelectedOrder()!=null && orderBackingBean.getSelectedOrder().getCompany()!=null && orderBackingBean.getSelectedOrder().getContractor()!=null)
            return agreementService.findAllAgreementsBasedOnCompanyAndContractor(orderBackingBean.getSelectedOrder().getCompany(), orderBackingBean.getSelectedOrder().getContractor(), AgreementTypeEnum.TRANSPORT.getType());
        return null;
    }

    public void transportationAgreementChangeListener(){
        orderService.copySelectedTransportationAgreementInfo(orderBackingBean.getSelectedOrder(), orderBackingBean.getSelectedOrder().getTransportationAgreement());
    }

    public List<AgreementStatus> getAllAgreementStatuses(){
        return agreementService.findAllAgreementStatuses();
    }

    public void additionalAgreementChangeListener(){
        orderService.copySelectedAdditionalAgreementInfo(orderBackingBean.getSelectedOrder(), orderBackingBean.getSelectedOrder().getAdditionalAgreement());
    }

    public List<OrderPaymentType> getAllOrderPaymentTypes(){
        return orderPaymentTypeService.getAllOrderPaymentTypes();
    }

    public void orderPaymentTypeChangeListener(){
        orderService.copySelectedOrderPaymentTypeInfo(orderBackingBean.getSelectedOrder(), orderBackingBean.getSelectedOrder().getOrderPaymentType());
    }

    public List<Contractor> getAllProviders(){
        return contractorService.findAllProviders();
    }

    public void providerChangeListener(){
        orderService.copySelectedProviderInfo(orderBackingBean.getSelectedOrder(), orderBackingBean.getSelectedOrder().getProvider());
    }

}
