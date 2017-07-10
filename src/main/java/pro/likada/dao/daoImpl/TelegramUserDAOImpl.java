package pro.likada.dao.daoImpl;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.likada.dao.TelegramUserDAO;
import pro.likada.model.TelegramUser;
import pro.likada.model.User;
import pro.likada.util.HibernateUtil;

import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by bumur on 17.03.2017.
 */
@SuppressWarnings("unchecked")
@Named("telegramUserDAO")
@Transactional
public class TelegramUserDAOImpl implements TelegramUserDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(TelegramUserDAOImpl.class);

    @Override
    public TelegramUser findById(Long id) throws HibernateException {
        LOGGER.info("Get TelegramUser with an ID: {}", id);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(TelegramUser.class);
        criteria.add(Restrictions.idEq(id));
        criteria.setCacheable(true);
        TelegramUser telegramUser = (TelegramUser) criteria.uniqueResult();
        session.close();
        return telegramUser;
    }

    @Override
    public TelegramUser findByTelegramUserId(Integer telegramUserId) throws HibernateException {
        LOGGER.info("Get TelegramUser with an Telegram User ID: {}", telegramUserId);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(TelegramUser.class);
        criteria.add(Restrictions.eq("telegramUserId", telegramUserId));
        criteria.setCacheable(true);
        TelegramUser telegramUser = (TelegramUser) criteria.uniqueResult();
        session.close();
        return telegramUser;
    }

    @Override
    public TelegramUser findByTelegramUsername(String username) throws HibernateException {
        LOGGER.info("Get TelegramUser with an Telegram Username: {}", username);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(TelegramUser.class);
        criteria.add(Restrictions.eq("username", username));
        criteria.setCacheable(true);
        TelegramUser telegramUser = (TelegramUser) criteria.uniqueResult();
        session.close();
        return telegramUser;
    }

    @Override
    public List<TelegramUser> findByOwnerUser(User owner) {
        LOGGER.info("Get TelegramUser of owner: {}", owner);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(TelegramUser.class);
        criteria.add(Restrictions.eq("ownerUser", owner));
        criteria.setCacheable(true);
        List<TelegramUser> telegramUsers = (List<TelegramUser>) criteria.list();
        session.close();
        return telegramUsers;
    }

    @Override
    public List<TelegramUser> findAll() {
        LOGGER.info("Get all TelegramUsers");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(TelegramUser.class);
        criteria.setCacheable(true);
        criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        List<TelegramUser> telegramUsers = (List<TelegramUser>)criteria.list();
        session.close();
        return telegramUsers;
    }

    @Override
    public void save(TelegramUser telegramUser) throws HibernateException {
        LOGGER.info("Save TelegramUser: {}", telegramUser);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(telegramUser);
        transaction.commit();
        session.flush();
        session.close();
    }

    @Override
    public void delete(TelegramUser telegramUser) throws HibernateException {
        LOGGER.info("Delete TelegramUser: {}", telegramUser);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(telegramUser);
        transaction.commit();
        session.flush();
        session.close();
    }

    @Override
    public void deleteByTelegramUserId(Long id) throws HibernateException{
        LOGGER.info("Delete TelegramUser by id: {}", id);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(findById(id));
        transaction.commit();
        session.flush();
        session.close();
    }


}
