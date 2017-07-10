package pro.likada.dao.daoImpl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.likada.bean.controller.LoginController;
import pro.likada.dao.UserDAO;
import pro.likada.model.Role;
import pro.likada.model.RoleEnum;
import pro.likada.model.User;
import pro.likada.util.HibernateUtil;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by Yusupov on 12/14/2016.
 */
@SuppressWarnings("unchecked")
@Named("userDAO")
@Transactional
public class UserDAOImpl implements UserDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDAOImpl.class);

    @Inject
    private LoginController loginController;

    @Override
    public User findById(long id) {
        LOGGER.info("Get all users with an ID: {}", id);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(User.class, "user").setCacheable(true);
        criteria.createAlias("user.userRoles", "role", JoinType.LEFT_OUTER_JOIN);
        criteria.createAlias("user.userPermissions", "permission", JoinType.LEFT_OUTER_JOIN);
        criteria.createAlias("user.subordinates", "subordinates", JoinType.LEFT_OUTER_JOIN);
        criteria.createAlias("user.parents", "parents", JoinType.LEFT_OUTER_JOIN);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);//To avoid duplicates.
        criteria.add(Restrictions.idEq(id));
        User user = (User) criteria.uniqueResult();
        session.close();
        return user;
    }

    @Override
    public User findByUsername(String username) {
        LOGGER.info("Get all users with USERNAME: {}", username);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(User.class, "user");
        criteria.createAlias("user.userRoles", "role", JoinType.LEFT_OUTER_JOIN);
        criteria.createAlias("user.userPermissions", "permission", JoinType.LEFT_OUTER_JOIN);
        criteria.createAlias("user.subordinates", "subordinates", JoinType.LEFT_OUTER_JOIN);
        criteria.createAlias("user.parents", "parents", JoinType.LEFT_OUTER_JOIN);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);//To avoid duplicates.
        criteria.add(Restrictions.eq("user.username", username));
        User user = (User) criteria.uniqueResult();
        session.close();
        return user;
    }

    @Override
    public List<User> findByRole(String roleType){
        LOGGER.info("Get all users with ROLE: {}", roleType);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(User.class, "user");
        criteria.createAlias("user.userRoles", "role", JoinType.LEFT_OUTER_JOIN);
        criteria.createAlias("user.userPermissions", "permission", JoinType.LEFT_OUTER_JOIN);
        criteria.createAlias("user.subordinates", "subordinates", JoinType.LEFT_OUTER_JOIN);
        criteria.createAlias("user.parents", "parents", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.ilike("role.type", roleType));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List<User> users = (List<User>)criteria.list();
        session.close();
        return users;
    }

    @Override
    public List<User> getAllUsers() {
        LOGGER.info("Get all users");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(User.class, "user");
        criteria.createAlias("user.userRoles", "role");
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);//To avoid duplicates.
        criteria.addOrder(Order.asc("user.title"));
        List<User> users = (List<User>)criteria.setCacheable(true).list();
        session.close();
        return users;
    }

    @Override
    public List<User> findAllUsersNotAddedToTable(String searchString, Set<User> customerManagers) {
        LOGGER.info("Get all users according: {}", searchString);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(User.class, "user");
        criteria.createAlias("user.userRoles", "role");

        // Apply filter to show users (for Manager place) of the Customer
        User user = loginController.getCurrentUser();
        List<Long> userChildIds = new LinkedList<>();
        for(User u: user.getSubordinates())
            for (Role r: u.getUserRoles())
                if (RoleEnum.SALES_MANAGER.getRoleType().equals(r.getType()))
                    userChildIds.add(u.getId());

        for (Role r: user.getUserRoles()){
            if(RoleEnum.ADMIN.getRoleType().equals(r.getType())) {
                criteria.add(Restrictions.ilike("role.type", RoleEnum.SALES_MANAGER.getRoleType()));
            }
            if(RoleEnum.SALES_MANAGER.getRoleType().equals(r.getType())) {
                criteria.add(Restrictions.eq("user.id", user.getId()));
            }
            if(RoleEnum.SALES_MANAGER_ASSISTANT.getRoleType().equals(r.getType())) {
                criteria.add(Restrictions.in("user.id", userChildIds)); // TODO optimize restriction for userChilds
            }
        }
        // Add filter to do not to show Users that are already listed as Customer Manager in the table
        if(customerManagers!=null && customerManagers.size()>0){
            List<Long> currentCustomerManagersIds = new LinkedList<>();
            for (User u: customerManagers)
                currentCustomerManagersIds.add(u.getId());
            criteria.add(Restrictions.not(Restrictions.in("id", currentCustomerManagersIds)));
        }

        // If in search field exists text, add restriction to show only users that matches to that string
        if(searchString!=null && !searchString.isEmpty()) {
            criteria.add(Restrictions.or(Restrictions.ilike("user.username", searchString, MatchMode.ANYWHERE),
                    Restrictions.ilike("user.firstName", searchString, MatchMode.ANYWHERE),
                    Restrictions.ilike("user.lastName", searchString, MatchMode.ANYWHERE),
                    Restrictions.ilike("user.patronymic", searchString, MatchMode.ANYWHERE),
                    Restrictions.ilike("user.title", searchString, MatchMode.ANYWHERE),
                    Restrictions.ilike("user.email", searchString, MatchMode.ANYWHERE),
                    Restrictions.ilike("user.phone", searchString, MatchMode.ANYWHERE),
                    Restrictions.ilike("user.mobile", searchString, MatchMode.ANYWHERE)));
        }
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);//To avoid duplicates.
        criteria.addOrder(Order.asc("user.firstName"));
        criteria.addOrder(Order.asc("user.lastName"));
        criteria.addOrder(Order.asc("user.title"));
        List<User> users = (List<User>)criteria.list();
        session.close();
        return users;
    }

    @Override
    public void changePassword(long userId, String newPassword) {
        LOGGER.info("Change password of the user with ID"+userId+", new password: " + newPassword);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        User user = findById(userId);
        user.setPassword(newPassword);
        session.saveOrUpdate(user);
        transaction.commit();
        session.flush();
        session.close();
    }

    @Override
    public void save(User user) {
        LOGGER.info("Saving a new user: " + user);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(user);
        transaction.commit();
        session.flush();
        session.close();
    }

    @Override
    public void deleteByUsername(String username) {
        LOGGER.info("Delete user with username:" + username);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(findByUsername(username));
        transaction.commit();
        session.flush();
        session.close();
    }
}
