package pro.likada.dao.daoImpl;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.*;
import org.hibernate.sql.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.likada.bean.controller.LoginController;
import pro.likada.dao.CustomerDAO;
import pro.likada.dao.UserDAO;
import pro.likada.model.Customer;
import pro.likada.model.Role;
import pro.likada.model.RoleEnum;
import pro.likada.model.User;
import pro.likada.util.HibernateUtil;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Yusupov on 12/19/2016.
 */
@SuppressWarnings("unchecked")
@Named("customerDAO")
@Transactional
public class CustomerDAOImpl implements CustomerDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDAOImpl.class);

    @Inject
    private LoginController loginController;

    @Override
    public Customer findById(long id) {
        LOGGER.info("Get Customer with an ID: {}", id);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Customer customer = (Customer) session.createCriteria(Customer.class).add(Restrictions.idEq(id)).uniqueResult();
        if(customer!=null){
            Hibernate.initialize(customer.getCustomerManagers());
        }
        session.close();
        return customer;
    }

    @Override
    public List<Customer> findByTitle(String title) {
        LOGGER.info("Get Customers with title: {}", title);
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Customer> customers = (List<Customer>) session.createCriteria(Customer.class)
                .add(Restrictions.ilike("title", title, MatchMode.EXACT)).list();
        session.close();
        return customers;
    }

    @Override
    public void save(Customer customer) {
        LOGGER.info("Saving the Customer: {}", customer);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(customer);
        transaction.commit();
        session.flush();
        session.close();
    }

    @Override
    public void deleteById(long id) {
        LOGGER.info("Delete Customer with an id:" + id);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(findById(id));
        transaction.commit();
        session.flush();
        session.close();
    }

    @Override
    public void deleteByTitle(String title) {
        LOGGER.info("Delete Customers with title:" + title);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        List<Customer> customers = findByTitle(title);
        for (Customer c: customers)
            session.delete(c);
        transaction.commit();
        session.flush();
        session.close();
    }

    @Override
    public List<Customer> findAllCustomers(String searchStringTitle) {
        LOGGER.info("Get all Customers");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Customer.class, "customer");
        criteria.createAlias("customer.customerManagers", "manager", JoinType.LEFT_OUTER_JOIN);
        if(searchStringTitle!=null && !searchStringTitle.isEmpty())
            criteria.add(Restrictions.or(Restrictions.ilike("customer.title", searchStringTitle, MatchMode.ANYWHERE),
                    Restrictions.ilike("manager.username", searchStringTitle, MatchMode.ANYWHERE),
                    Restrictions.ilike("manager.firstName",searchStringTitle, MatchMode.ANYWHERE),
                    Restrictions.ilike("manager.lastName",searchStringTitle, MatchMode.ANYWHERE),
                    Restrictions.ilike("manager.patronymic",searchStringTitle, MatchMode.ANYWHERE),
                    Restrictions.ilike("manager.title",searchStringTitle, MatchMode.ANYWHERE)));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);//To avoid duplicates.
        criteria.addOrder(Order.asc("customer.title"));
        criteria.addOrder(Order.asc("customer.id"));

        // Apply filter to show related customers of the user
        User user = loginController.getCurrentUser();
        Subject subject = SecurityUtils.getSubject();
        Disjunction disjunction =Restrictions.disjunction();
        if(subject.hasRole(RoleEnum.SALES_MANAGER.getRoleType()))
            disjunction.add(Restrictions.eq("manager.id", user.getId()));
        if(subject.hasRole(RoleEnum.SALES_DIRECTOR.getRoleType()) || subject.hasRole(RoleEnum.SALES_MANAGER_ASSISTANT.getRoleType()))
            for(User subordinate: user.getSubordinates())
                disjunction.add(Restrictions.eq("manager.id", subordinate.getId()));
        criteria.add(disjunction);
        List<Customer> customers = (List<Customer>)criteria.list();
        session.close();
        return customers;
    }

    @Override
    public List<Customer> findAllCustomersBasedOnUserRole(User user, Integer startFrom, Integer maxNumberOfResults) {
        LOGGER.info("Get all Customers from {} to {} based on {}", startFrom-1, startFrom*maxNumberOfResults, user);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Customer.class, "customer");
        criteria.createAlias("customer.customerManagers", "manager", JoinType.LEFT_OUTER_JOIN);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);//To avoid duplicates.
        criteria.addOrder(Order.asc("customer.title"));
        criteria.addOrder(Order.asc("customer.id"));
        // Apply filter to show related customers of the user
        criteria.add(addRestrictionBasedOnUserRole(user));
        criteria.setFirstResult(startFrom-1);
        criteria.setMaxResults(maxNumberOfResults);
        criteria.setCacheable(true);
        List<Customer> customers = (List<Customer>)criteria.list();
        session.close();
        return customers;
    }

    @Override
    public Long countCustomersBasedOnUserRole(User user) {
        LOGGER.info("Count all Customers based on {}", user);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Customer.class, "customer");
        criteria.createAlias("customer.customerManagers", "manager", JoinType.LEFT_OUTER_JOIN);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);//To avoid duplicates.
        // Apply filter to show related customers of the user
        criteria.add(addRestrictionBasedOnUserRole(user));
        criteria.setProjection(Projections.rowCount());
        return (Long) criteria.uniqueResult();
    }

    private Disjunction addRestrictionBasedOnUserRole(User user){
        Disjunction disjunction = Restrictions.disjunction();
        for (Role role: user.getUserRoles()){
            if(RoleEnum.SALES_MANAGER.getRoleType().equals(role.getType()))
                disjunction.add(Restrictions.eq("manager.id", user.getId()));
            if(RoleEnum.SALES_DIRECTOR.getRoleType().equals(role.getType()) || RoleEnum.SALES_MANAGER_ASSISTANT.getRoleType().equals(role.getType()))
                for(User subordinate: user.getSubordinates())
                    disjunction.add(Restrictions.eq("manager.id", subordinate.getId()));
        }
        return disjunction;
    }
}
