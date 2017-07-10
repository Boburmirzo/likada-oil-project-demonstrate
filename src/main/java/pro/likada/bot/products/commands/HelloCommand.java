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
public class HelloCommand extends BotCommand {

    private static final String LOGTAG = "HELLOCOMMAND";
    private static TelegramUserDAOImpl telegramUserDAO= new TelegramUserDAOImpl();

    public HelloCommand() {
        super("hello", "Отправьте команду чтобы получить уведомления об изменении цены");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {



        String userName = chat.getFirstName();
        if (userName == null || userName.isEmpty()) {
            userName = user.getFirstName();
        }

        if(telegramUserDAO.findByTelegramUserId(user.getId())!=null && !telegramUserDAO.findByTelegramUserId(user.getId()).isActive()){
           TelegramUser telegramUser=telegramUserDAO.findByTelegramUserId(user.getId());
           telegramUser.setActive(true);
           telegramUser.setConversations(null);
           telegramUserDAO.save(telegramUser);
        }

        StringBuilder messageTextBuilder = new StringBuilder(userName).append(" ваш профиль активный и можете получить информацию об изменении цены");
        if (arguments != null && arguments.length > 0) {
            messageTextBuilder.append("\n");
            messageTextBuilder.append("Ваш логин зарегистрирован:\n");
            messageTextBuilder.append(String.join(" ", arguments));
        }

        SendMessage answer = new SendMessage();
        answer.setChatId(chat.getId().toString());
        answer.setText(messageTextBuilder.toString());

        try {
            absSender.sendMessage(answer);
        } catch (TelegramApiException e) {
            BotLogger.error(LOGTAG, e);
        }
    }
}