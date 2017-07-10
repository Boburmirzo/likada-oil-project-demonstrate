package pro.likada.dao.daoImpl;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.likada.dao.TelegramConversationDAO;
import pro.likada.model.TelegramBot;
import pro.likada.model.TelegramConversation;
import pro.likada.model.TelegramUser;
import pro.likada.util.HibernateUtil;

import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Yusupov on 4/2/2017.
 */
@SuppressWarnings("unchecked")
@Named("telegramConversationDAO")
@Transactional
public class TelegramConversationDAOImpl implements TelegramConversationDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(TelegramConversationDAOImpl.class);

    @Override
    public TelegramConversation findById(Long id) throws HibernateException {
        LOGGER.info("Get TelegramConversation with an ID: {}", id);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(TelegramConversation.class);
        criteria.add(Restrictions.idEq(id));
        criteria.setCacheable(true);
        TelegramConversation telegramConversation = (TelegramConversation) criteria.uniqueResult();
        session.close();
        return telegramConversation;
    }

    @Override
    public TelegramConversation findByChatId(Long chatId) throws HibernateException {
        LOGGER.info("Get TelegramConversation with an chat ID: {}", chatId);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(TelegramConversation.class);
        criteria.add(Restrictions.eq("chatId", chatId));
        criteria.setCacheable(true);
        TelegramConversation telegramConversation = (TelegramConversation) criteria.uniqueResult();
        session.close();
        return telegramConversation;
    }

    @Override
    public List<TelegramConversation> findByTelegramUser(TelegramUser telegramUser) {
        LOGGER.info("Get all TelegramConversations by telegramUser: {}", telegramUser);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(TelegramConversation.class);
        criteria.add(Restrictions.eq("telegramUser", telegramUser));
        criteria.setCacheable(true);
        List<TelegramConversation> telegramConversations = (List<TelegramConversation>) criteria.list();
        session.close();
        return telegramConversations;
    }

    @Override
    public List<TelegramConversation> findByTelegramBot(TelegramBot telegramBot) {
        LOGGER.info("Get all TelegramConversations by telegramBot: {}", telegramBot);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(TelegramConversation.class);
        criteria.add(Restrictions.eq("telegramBot", telegramBot));
        criteria.setCacheable(true);
        List<TelegramConversation> telegramConversations = (List<TelegramConversation>) criteria.list();
        session.close();
        return telegramConversations;
    }

    @Override
    public TelegramConversation findByTelegramUserAndTelegramBot(TelegramUser telegramUser, TelegramBot telegramBot) throws HibernateException {
        LOGGER.info("Get TelegramConversation by telegramUser: {} and telegramBot: {}", telegramUser, telegramBot);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(TelegramConversation.class);
        criteria.add(Restrictions.eq("telegramUser", telegramUser));
        criteria.add(Restrictions.eq("telegramBot", telegramBot));
        criteria.setCacheable(true);
        TelegramConversation telegramConversation = (TelegramConversation) criteria.uniqueResult();
        session.close();
        return telegramConversation;
    }

    @Override
    public List<TelegramConversation> findAll() {
        LOGGER.info("Get all TelegramConversations");
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<TelegramConversation> telegramConversations = (List<TelegramConversation>) session.createCriteria(TelegramConversation.class).setCacheable(true).list();
        session.close();
        return telegramConversations;
    }

    @Override
    public void save(TelegramConversation telegramConversation) throws HibernateException {
        LOGGER.info("Save TelegramConversation: {}", telegramConversation);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(telegramConversation);
        transaction.commit();
        session.flush();
        session.close();
    }

    @Override
    public void delete(TelegramConversation telegramConversation) throws HibernateException {
        LOGGER.info("Delete TelegramConversation: {}", telegramConversation);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(telegramConversation);
        transaction.commit();
        session.flush();
        session.close();
    }

    @Override
    public void deleteById(Long id) throws HibernateException {
        LOGGER.info("Delete TelegramConversation with id: {}", id);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(findById(id));
        transaction.commit();
        session.flush();
        session.close();
    }
}
