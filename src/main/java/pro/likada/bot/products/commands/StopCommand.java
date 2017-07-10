package pro.likada.bot.products.commands;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commands.BotCommand;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.logging.BotLogger;
import pro.likada.dao.daoImpl.TelegramUserDAOImpl;
import pro.likada.model.TelegramUser;

import javax.enterprise.context.Dependent;
import javax.inject.Named;

@Named
@Dependent
public class StopCommand extends BotCommand {

    public static final String LOGTAG = "STOPCOMMAND";
    private static TelegramUserDAOImpl telegramUserDAO= new TelegramUserDAOImpl();
    /**
     * Construct
     */
    public StopCommand() {
        super("stop", "С помощью этой команды вы можете остановить уведомления");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {

        if(telegramUserDAO.findByTelegramUserId(user.getId())!=null){
            TelegramUser telegramUser=telegramUserDAO.findByTelegramUserId(user.getId());
            telegramUser.setConversations(null);
            telegramUser.setActive(false);
            telegramUserDAO.save(telegramUser);
        }
            String userName = user.getFirstName();

            SendMessage answer = new SendMessage();
            answer.setChatId(chat.getId().toString());
            answer.setText("Пока " + userName + "\n" + "До встречи!");

            try {
                absSender.sendMessage(answer);
            } catch (TelegramApiException e) {
                BotLogger.error(LOGTAG, e);
            }
        }
    }

