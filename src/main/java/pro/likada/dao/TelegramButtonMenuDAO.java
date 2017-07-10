package pro.likada.dao;

import org.hibernate.HibernateException;
import pro.likada.model.TelegramButtonMenu;

import java.util.List;

/**
 * Created by Yusupov on 3/31/2017.
 */
public interface TelegramButtonMenuDAO {

    TelegramButtonMenu findById(Long id) throws HibernateException;

    TelegramButtonMenu findByType(String type) throws HibernateException;

    TelegramButtonMenu findByName(String name) throws HibernateException;

    List<TelegramButtonMenu> findAll();

    void save(TelegramButtonMenu telegramButtonMenu) throws HibernateException;

    void delete(TelegramButtonMenu telegramButtonMenu) throws HibernateException;

    void deleteById(Long id) throws HibernateException;
}
