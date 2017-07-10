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
public class StartCommand extends BotCommand {

    public static final String LOGTAG = "STARTCOMMAND";
    private static TelegramUserDAOImpl telegramUserDAO= new TelegramUserDAOImpl();
    public StartCommand() {
        super("start", "С помощью этой команды вы можете запустить бот");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        StringBuilder messageBuilder = new StringBuilder();
        String userName = user.getFirstName();

        if(telegramUserDAO.findByTelegramUserId(user.getId())==null){
            TelegramUser telegramUser= new TelegramUser();
            telegramUser.setTelegramUserId(user.getId());
            telegramUser.setUsername(user.getUserName());
            telegramUser.setFirstName(user.getFirstName());
            telegramUser.setLastName(user.getLastName());
            telegramUserDAO.save(telegramUser);
            messageBuilder.append("Добро пожаловать! ").append(userName).append("\n\n");
            messageBuilder.append("Чтобы получить уведомления запускайте команду Hello").append("\n");
            messageBuilder.append("Потом можно будет их отключить по команде Stop");
        }else {
            messageBuilder.append(userName).append(" ваш логин уже зарегистрирован");
        }

        SendMessage answer = new SendMessage();
        answer.setChatId(chat.getId().toString());
        answer.setText(messageBuilder.toString());

        try {
            absSender.sendMessage(answer);
        } catch (TelegramApiException e) {
            BotLogger.error(LOGTAG, e);
        }
    }
}