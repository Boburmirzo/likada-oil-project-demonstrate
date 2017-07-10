package pro.likada.service.serviceImpl;

import org.hibernate.HibernateException;
import pro.likada.dao.TelegramBotDAO;
import pro.likada.model.TelegramBot;
import pro.likada.service.TelegramBotService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Yusupov on 4/2/2017.
 */
@Named("telegramBotService")
@Transactional
public class TelegramBotServiceImpl implements TelegramBotService {

    @Inject
    private TelegramBotDAO telegramBotDAO;

    @Override
    public TelegramBot findById(Long id) throws HibernateException {
        return telegramBotDAO.findById(id);
    }

    @Override
    public TelegramBot findByTelegramBotId(Integer telegramBotId) throws HibernateException {
        return telegramBotDAO.findByTelegramBotId(telegramBotId);
    }

    @Override
    public TelegramBot findByTelegramBotUsername(String botUsername) throws HibernateException {
        return telegramBotDAO.findByTelegramBotUsername(botUsername);
    }

    @Override
    public List<TelegramBot> findAll() {
        return telegramBotDAO.findAll();
    }

    @Override
    public void save(TelegramBot telegramBot) throws HibernateException {
        telegramBotDAO.save(telegramBot);
    }

    @Override
    public void delete(TelegramBot telegramBot) throws HibernateException {
        telegramBotDAO.delete(telegramBot);
    }

    @Override
    public void deleteByTelegramBotId(Integer botId) throws HibernateException {
        telegramBotDAO.deleteByTelegramBotId(botId);
    }
}
