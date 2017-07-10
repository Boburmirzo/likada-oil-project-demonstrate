package pro.likada.service.serviceImpl;

import org.hibernate.HibernateException;
import pro.likada.dao.TelegramConversationDAO;
import pro.likada.model.TelegramBot;
import pro.likada.model.TelegramConversation;
import pro.likada.model.TelegramUser;
import pro.likada.service.TelegramConversationService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Yusupov on 4/2/2017.
 */
@Named("telegramConversationService")
@Transactional
public class TelegramConversationServiceImpl implements TelegramConversationService {

    @Inject
    private TelegramConversationDAO telegramConversationDAO;

    @Override
    public TelegramConversation findById(Long id) throws HibernateException {
        return telegramConversationDAO.findById(id);
    }

    @Override
    public TelegramConversation findByChatId(Long chatId) throws HibernateException {
        return telegramConversationDAO.findByChatId(chatId);
    }

    @Override
    public List<TelegramConversation> findByTelegramUser(TelegramUser telegramUser) {
        return telegramConversationDAO.findByTelegramUser(telegramUser);
    }

    @Override
    public List<TelegramConversation> findByTelegramBot(TelegramBot telegramBot) {
        return telegramConversationDAO.findByTelegramBot(telegramBot);
    }

    @Override
    public TelegramConversation findByTelegramUserAndTelegramBot(TelegramUser telegramUser, TelegramBot telegramBot) throws HibernateException {
        return telegramConversationDAO.findByTelegramUserAndTelegramBot(telegramUser, telegramBot);
    }

    @Override
    public List<TelegramConversation> findAll() {
        return telegramConversationDAO.findAll();
    }

    @Override
    public void save(TelegramConversation telegramConversation) throws HibernateException {
        telegramConversationDAO.save(telegramConversation);
    }

    @Override
    public void delete(TelegramConversation telegramConversation) throws HibernateException {
        telegramConversationDAO.delete(telegramConversation);
    }

    @Override
    public void deleteById(Long id) throws HibernateException {
        telegramConversationDAO.deleteById(id);
    }
}
