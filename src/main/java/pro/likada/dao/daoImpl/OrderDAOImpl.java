package pro.likada.dao.daoImpl;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.likada.bean.controller.LoginController;
import pro.likada.dao.OrderDAO;
import pro.likada.model.Customer;
import pro.likada.model.Order;
import pro.likada.model.TelegramUser;
import pro.likada.util.HibernateUtil;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * Created by Yusupov on 3/10/2017.
 */
@SuppressWarnings("unchecked")
@Named("orderDAO")
@Transactional
public class OrderDAOImpl implements OrderDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderDAOImpl.class);

    @Inject
    private LoginController loginController;


    @Override
    public Order findById(long id) {
        LOGGER.info("Get Order with an ID: {}", id);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Order.class, "order");
        criteria.add(Restrictions.idEq(id));
        Order order = (Order) criteria.uniqueResult();
        session.close();
        return order;
    }

    @Override
    public Order findByTelegramUserAndSubmissionStatus(TelegramUser telegramUser, Boolean submittedAlready) throws HibernateException {
        LOGGER.info("Get Order by TelegramUser: {}", telegramUser);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Order.class, "order");
        criteria.add(Restrictions.eq("telegramUser", telegramUser));
        criteria.add(Restrictions.eq("telegramReceivedAlready", submittedAlready));
        Order order = (Order) criteria.uniqueResult();
        session.close();
        return order;
    }

    @Override
    public  List<Order> findAllOrdersByCustomer(Customer customer) {
        LOGGER.info("Get Order based on customer {}", customer);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Order.class, "order");
        criteria.createAlias("order.customer", "customer", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.eq("customer.id", customer.getId()));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);//To avoid duplicates.
        criteria.addOrder(org.hibernate.criterion.Order.asc("customer.title"));
        criteria.setCacheable(true);
        List<Order> orders = (List<Order>)criteria.list();
        session.close();
        return orders;
    }

    @Override
    public void save(Order order) {
        LOGGER.info("Saving a new Order: {}", order);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(order);
        transaction.commit();
        session.flush();
        session.close();
    }

    @Override
    public void delete(Order order) {
        LOGGER.info("Delete Order:" + order);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(order);
        transaction.commit();
        session.flush();
        session.close();
    }

    @Override
    public void deleteById(long id) {
        LOGGER.info("Delete Order with an id:" + id);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Order order = findById(id);
        session.delete(order);
        transaction.commit();
        session.flush();
        session.close();
    }

    @Override
    public List<Order> findAllOrdersBasedOnSearchStringAndFilterPeriod(String searchString, Date from, Date to) {
        LOGGER.info("Get all Orders");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Order.class, "order").setCacheable(true);

//        criteria.createAlias("customer.customerManagers", "manager", JoinType.LEFT_OUTER_JOIN);
        if(searchString!=null && !searchString.isEmpty())
            criteria.add(Restrictions.or(
                    Restrictions.ilike("order.loadPointOtherName", searchString, MatchMode.ANYWHERE),
                    Restrictions.ilike("order.unloadPointOtherName", searchString, MatchMode.ANYWHERE),
                    Restrictions.ilike("order.customerTitle", searchString, MatchMode.ANYWHERE),
                    Restrictions.ilike("order.contractorNameFull", searchString, MatchMode.ANYWHERE),
                    Restrictions.ilike("order.contractorNameShort", searchString, MatchMode.ANYWHERE),
                    Restrictions.ilike("order.contractorNameWork", searchString, MatchMode.ANYWHERE),
                    Restrictions.ilike("order.transportationContractorNameFull", searchString, MatchMode.ANYWHERE),
                    Restrictions.ilike("order.transportationContractorNameShort", searchString, MatchMode.ANYWHERE),
                    Restrictions.ilike("order.transportationContractorNameWork", searchString, MatchMode.ANYWHERE),
                    Restrictions.ilike("order.companyNameFull", searchString, MatchMode.ANYWHERE),
                    Restrictions.ilike("order.companyNameShort", searchString, MatchMode.ANYWHERE),
                    Restrictions.ilike("order.companyNameWork", searchString, MatchMode.ANYWHERE),
                    Restrictions.ilike("order.transportationCompanyNameFull", searchString, MatchMode.ANYWHERE),
                    Restrictions.ilike("order.transportationCompanyNameShort", searchString, MatchMode.ANYWHERE),
                    Restrictions.ilike("order.transportationCompanyNameWork", searchString, MatchMode.ANYWHERE),
                    Restrictions.ilike("order.carrierNameFull", searchString, MatchMode.ANYWHERE),
                    Restrictions.ilike("order.carrierNameShort", searchString, MatchMode.ANYWHERE),
                    Restrictions.ilike("order.carrierNameWork", searchString, MatchMode.ANYWHERE),
                    Restrictions.ilike("order.providerNameFull", searchString, MatchMode.ANYWHERE),
                    Restrictions.ilike("order.providerNameShort", searchString, MatchMode.ANYWHERE),
                    Restrictions.ilike("order.providerNameWork", searchString, MatchMode.ANYWHERE),
                    Restrictions.ilike("order.productNameFull", searchString, MatchMode.ANYWHERE),
                    Restrictions.ilike("order.productNameShort", searchString, MatchMode.ANYWHERE),
                    Restrictions.ilike("order.productTypeNameFull", searchString, MatchMode.ANYWHERE),
                    Restrictions.ilike("order.productTypeNameWork", searchString, MatchMode.ANYWHERE),
                    Restrictions.ilike("order.shipmentBaseNameFull", searchString, MatchMode.ANYWHERE),
                    Restrictions.ilike("order.shipmentBaseNameShort", searchString, MatchMode.ANYWHERE),
                    Restrictions.ilike("order.truckTitle", searchString, MatchMode.ANYWHERE),
                    Restrictions.ilike("order.truckGovNumber", searchString, MatchMode.ANYWHERE),
                    Restrictions.ilike("order.trailerTitle", searchString, MatchMode.ANYWHERE),
                    Restrictions.ilike("order.trailerGovNumber", searchString, MatchMode.ANYWHERE),
                    Restrictions.ilike("order.driverTitle", searchString, MatchMode.ANYWHERE),
                    Restrictions.ilike("order.driverFirstName", searchString, MatchMode.ANYWHERE),
                    Restrictions.ilike("order.driverLastName", searchString, MatchMode.ANYWHERE),
                    Restrictions.ilike("order.driverPatronymic",searchString, MatchMode.ANYWHERE)));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);//To avoid duplicates.
        criteria.add(Restrictions.eq("telegramReceivedAlready", true));
        criteria.addOrder(org.hibernate.criterion.Order.asc("order.id"));

        // TODO filter out based on the roles of the current user

        if(from!=null && to!=null){
            Disjunction disjunction = Restrictions.disjunction();
            disjunction.add(Restrictions.between("createdDate", from, to));
            disjunction.add(Restrictions.between("closedDate", from, to));
            disjunction.add(Restrictions.between("cancelledDate", from, to));
            criteria.add(disjunction);
        }

        //TODO Apply filter to show related orders of the user

        List<Order> orders = (List<Order>)criteria.list();
        session.close();
        return orders;
    }
}
