package pro.likada.dao.daoImpl;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.likada.dao.TelegramButtonMenuDAO;
import pro.likada.model.TelegramButtonMenu;
import pro.likada.util.HibernateUtil;

import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Yusupov on 3/31/2017.
 */
@SuppressWarnings("unchecked")
@Named("telegramButtonMenuDAO")
@Transactional
public class TelegramButtonMenuDAOImpl implements TelegramButtonMenuDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(TelegramButtonMenuDAOImpl.class);

    @Override
    public TelegramButtonMenu findById(Long id) throws HibernateException {
        LOGGER.info("Get TelegramButtonMenu with an ID: {}", id);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(TelegramButtonMenu.class);
        criteria.add(Restrictions.idEq(id));
        criteria.setCacheable(true);
        TelegramButtonMenu telegramButtonMenu = (TelegramButtonMenu) criteria.uniqueResult();
        session.close();
        return telegramButtonMenu;
    }

    @Override
    public TelegramButtonMenu findByType(String type) throws HibernateException {
        LOGGER.info("Get TelegramButtonMenu with type: {}", type);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(TelegramButtonMenu.class);
        criteria.add(Restrictions.eq("type", type));
        criteria.setCacheable(true);
        TelegramButtonMenu telegramButtonMenu = (TelegramButtonMenu) criteria.uniqueResult();
        session.close();
        return telegramButtonMenu;
    }

    @Override
    public TelegramButtonMenu findByName(String name) throws HibernateException {
        LOGGER.info("Get TelegramButtonMenu with name: {}", name);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(TelegramButtonMenu.class);
        criteria.add(Restrictions.eq("name", name));
        criteria.setCacheable(true);
        TelegramButtonMenu telegramButtonMenu = (TelegramButtonMenu) criteria.uniqueResult();
        session.close();
        return telegramButtonMenu;
    }

    @Override
    public List<TelegramButtonMenu> findAll() {
        LOGGER.info("Get all TelegramButtonMenus");
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<TelegramButtonMenu> telegramButtonMenus = (List<TelegramButtonMenu>) session.createCriteria(TelegramButtonMenu.class).setCacheable(true).list();
        session.close();
        return telegramButtonMenus;
    }

    @Override
    public void save(TelegramButtonMenu telegramButtonMenu) throws HibernateException {
        LOGGER.info("Save TelegramButtonMenu: {}", telegramButtonMenu);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(telegramButtonMenu);
        transaction.commit();
        session.flush();
        session.close();
    }

    @Override
    public void delete(TelegramButtonMenu telegramButtonMenu) throws HibernateException {
        LOGGER.info("Delete TelegramButtonMenu: {}", telegramButtonMenu);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(telegramButtonMenu);
        transaction.commit();
        session.flush();
        session.close();
    }

    @Override
    public void deleteById(Long id) throws HibernateException {
        LOGGER.info("Delete TelegramButtonMenu with id: {}", id);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(findById(id));
        transaction.commit();
        session.flush();
        session.close();
    }
}
