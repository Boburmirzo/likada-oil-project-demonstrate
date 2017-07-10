package pro.likada.service;

import org.hibernate.HibernateException;
import pro.likada.model.*;

import java.util.List;

/**
 * Created by Yusupov on 3/10/2017.
 */
public interface OrderService {

    Order findById(long id);

    Order findByTelegramUserAndSubmissionStatus(TelegramUser telegramUser, Boolean submittedAlready) throws HibernateException;

    List<Order> findAllOrdersByCustomer(Customer customer);

    void save(Order order);

    void delete(Order order);

    void deleteById(long id);

    List<Order> findAllOrdersBasedOnSearchStringAndFilterPeriod(String searchString, String filterPeriodOneButton);

    void copySelectedOrderStatusTypeInfo(Order newOrder, OrderStatusType orderStatusType);

    void copyCurrentUserInfoForCreatorAndOwner(Order newOrder, User user);

    void copySelectedProductGroupInfo(Order newOrder, ProductGroup productGroup);

    void copySelectedProductInfo(Order newOrder, Product product);

    void copyShipmentBasisInfo(Order newOrder, ShipmentBasis shipmentBasis);

    void copySelectedUserInfoForCreator(Order newOrder, User creator);

    void copySelectedUserInfoForOwner(Order newOrder, User owner);

    void copySelectedCompanyInfo(Order newOrder, Contractor company);

    void copySelectedTransportationCompanyInfo(Order newOrder, Contractor transportationCompany);

    void copySelectedTransportationVehicleTypeInfo(Order newOrder, TransportationVehicleType transportationVehicleType, boolean resetOtherFields);

    void copySelectedTransportationCompanyTypeInfo(Order newOrder, TransportationCompanyType transportationCompanyType, boolean resetOtherFields);

    void copySelectedTransportationHiredVehicleTypeInfo(Order newOrder, TransportationHiredVehicleType transportationHiredVehicleType, boolean resetOtherFields);

    void copySelectedVehicleInfo(Order newOrder, Vehicle vehicle);

    void copySelectedDriverInfo(Order newOrder, Driver driver);

    void copySelectedCarrierInfo(Order newOrder, Contractor carrier);

    void copySelectedUserInfoForTransportManager(Order newOrder, User transportManager);

    void copySelectedCustomerInfo(Order newOrder, Customer customer);

    void copySelectedContractorInfo(Order newOrder, Contractor contractor);

    void copySelectedTransportationContractorInfo(Order newOrder, Contractor transportationContractor);

    void copySelectedAgreementInfo(Order newOrder, Agreement agreement);

    void copySelectedTransportationAgreementInfo(Order newOrder, Agreement transportationAgreement);

    void copySelectedAdditionalAgreementInfo(Order newOrder, Agreement additionalAgreement);

    void copySelectedOrderPaymentTypeInfo(Order newOrder, OrderPaymentType orderPaymentType);

    void copySelectedProviderInfo(Order newOrder, Contractor provider);

}
