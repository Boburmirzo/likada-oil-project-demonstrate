package pro.likada.service;

import org.hibernate.HibernateException;
import pro.likada.model.TelegramBot;
import pro.likada.model.TelegramConversation;
import pro.likada.model.TelegramUser;

import java.util.List;

/**
 * Created by Yusupov on 4/2/2017.
 */
public interface TelegramConversationService {

    TelegramConversation findById(Long id) throws HibernateException;

    TelegramConversation findByChatId(Long chatId) throws HibernateException;

    List<TelegramConversation> findByTelegramUser(TelegramUser telegramUser);

    List<TelegramConversation> findByTelegramBot(TelegramBot telegramBot);

    TelegramConversation findByTelegramUserAndTelegramBot(TelegramUser telegramUser, TelegramBot telegramBot) throws HibernateException;

    List<TelegramConversation> findAll();

    void save(TelegramConversation telegramConversation) throws HibernateException;

    void delete(TelegramConversation telegramConversation) throws HibernateException;

    void deleteById(Long id) throws HibernateException;


}
