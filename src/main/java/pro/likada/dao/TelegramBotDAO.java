package pro.likada.dao;

import org.hibernate.HibernateException;
import pro.likada.model.TelegramBot;

import java.util.List;

/**
 * Created by Yusupov on 4/2/2017.
 */
public interface TelegramBotDAO {

    TelegramBot findById(Long id) throws HibernateException;

    TelegramBot findByTelegramBotId(Integer telegramBotId) throws HibernateException;

    TelegramBot findByTelegramBotUsername(String botUsername) throws HibernateException;

    List<TelegramBot> findAll();

    void save(TelegramBot telegramBot) throws HibernateException;

    void delete(TelegramBot telegramBot) throws HibernateException;

    void deleteByTelegramBotId(Integer botId) throws HibernateException;

}
