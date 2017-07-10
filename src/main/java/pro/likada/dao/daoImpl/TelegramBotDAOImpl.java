package pro.likada.dao.daoImpl;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.likada.dao.TelegramBotDAO;
import pro.likada.model.TelegramBot;
import pro.likada.util.HibernateUtil;

import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Yusupov on 4/2/2017.
 */
@SuppressWarnings("unchecked")
@Named("telegramBotDAO")
@Transactional
public class TelegramBotDAOImpl implements TelegramBotDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(TelegramBotDAOImpl.class);

    @Override
    public TelegramBot findById(Long id) throws HibernateException {
        LOGGER.info("Get TelegramBot with an ID: {}", id);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(TelegramBot.class);
        criteria.add(Restrictions.idEq(id));
        criteria.setCacheable(true);
        TelegramBot telegramBot = (TelegramBot) criteria.uniqueResult();
        session.close();
        return telegramBot;
    }

    @Override
    public TelegramBot findByTelegramBotId(Integer telegramBotId) throws HibernateException {
        LOGGER.info("Get TelegramBot with TelegramBotID: {}", telegramBotId);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(TelegramBot.class);
        criteria.add(Restrictions.eq("botId", telegramBotId));
        criteria.setCacheable(true);
        TelegramBot telegramBot = (TelegramBot) criteria.uniqueResult();
        session.close();
        return telegramBot;
    }

    @Override
    public TelegramBot findByTelegramBotUsername(String botUsername) throws HibernateException {
        LOGGER.info("Get TelegramBot with Telegram Bot Username: {}", botUsername);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(TelegramBot.class);
        criteria.add(Restrictions.eq("username", botUsername));
        criteria.setCacheable(true);
        TelegramBot telegramBot = (TelegramBot) criteria.uniqueResult();
        session.close();
        return telegramBot;
    }

    @Override
    public List<TelegramBot> findAll() {
        LOGGER.info("Get all TelegramBots");
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<TelegramBot> telegramBots = (List<TelegramBot>) session.createCriteria(TelegramBot.class).setCacheable(true).list();
        session.close();
        return telegramBots;
    }

    @Override
    public void save(TelegramBot telegramBot) throws HibernateException {
        LOGGER.info("Save TelegramBot: {}", telegramBot);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(telegramBot);
        transaction.commit();
        session.flush();
        session.close();
    }

    @Override
    public void delete(TelegramBot telegramBot) throws HibernateException {
        LOGGER.info("Delete TelegramBot: {}", telegramBot);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(telegramBot);
        transaction.commit();
        session.flush();
        session.close();
    }

    @Override
    public void deleteByTelegramBotId(Integer botId) throws HibernateException {
        LOGGER.info("Delete TelegramBot with  botId: {}", botId);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(findByTelegramBotId(botId));
        transaction.commit();
        session.flush();
        session.close();
    }
}
