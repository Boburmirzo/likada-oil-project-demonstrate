package pro.likada.dao;

import org.hibernate.HibernateException;
import pro.likada.model.TelegramUser;
import pro.likada.model.User;

import java.util.List;

/**
 * Created by bumur on 17.03.2017.
 */
public interface TelegramUserDAO {

    TelegramUser findById(Long id) throws HibernateException;

    TelegramUser findByTelegramUserId(Integer telegramUserId) throws HibernateException;

    TelegramUser findByTelegramUsername(String username) throws HibernateException;

    List<TelegramUser> findByOwnerUser(User owner);

    List<TelegramUser> findAll();

    void save(TelegramUser telegramUser) throws HibernateException;

    void delete(TelegramUser telegramUser) throws HibernateException;

    void deleteByTelegramUserId(Long id) throws HibernateException;

}
