package pro.likada.service.serviceImpl;

import org.hibernate.HibernateException;
import pro.likada.dao.TelegramUserDAO;
import pro.likada.model.TelegramUser;
import pro.likada.model.User;
import pro.likada.service.TelegramUserService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by bumur on 17.03.2017.
 */
@Named("telegramUserService")
@Transactional
public class TelegramUserServiceImpl implements TelegramUserService {

    @Inject
    private TelegramUserDAO telegramUserDAO;

    @Override
    public TelegramUser findById(Long id) throws HibernateException {
        return telegramUserDAO.findById(id);
    }

    @Override
    public TelegramUser findByTelegramUserId(Integer telegramUserId) throws HibernateException {
        return telegramUserDAO.findByTelegramUserId(telegramUserId);
    }

    @Override
    public TelegramUser findByTelegramUsername(String username) throws HibernateException {
        return telegramUserDAO.findByTelegramUsername(username);
    }

    @Override
    public List<TelegramUser> findByOwnerUser(User owner) {
        return telegramUserDAO.findByOwnerUser(owner);
    }

    @Override
    public List<TelegramUser> findAll() {
        return telegramUserDAO.findAll();
    }

    @Override
    public void save(TelegramUser telegramUser) throws HibernateException {
        telegramUserDAO.save(telegramUser);
    }

    @Override
    public void delete(TelegramUser telegramUser) throws HibernateException {
        telegramUserDAO.delete(telegramUser);
    }

    @Override
    public void deleteByTelegramUserId(Long id) throws HibernateException {
        telegramUserDAO.deleteByTelegramUserId(id);
    }
}
