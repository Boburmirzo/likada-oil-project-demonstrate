package pro.likada.bot.orders.commands;

import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commands.BotCommand;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.logging.BotLogger;
import pro.likada.bot.orders.LikadaOrdersBotController;
import pro.likada.bot.orders.utils.LikadaOrdersBotUtils;
import pro.likada.model.TelegramBot;
import pro.likada.model.TelegramButtonMenu;
import pro.likada.model.TelegramConversation;
import pro.likada.model.TelegramUser;
import pro.likada.service.TelegramBotService;
import pro.likada.service.TelegramButtonMenuService;
import pro.likada.service.TelegramConversationService;
import pro.likada.service.TelegramUserService;
import pro.likada.util.TelegramButtonMenuEnum;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Created by Yusupov on 3/31/2017.
 */
@Named
@Dependent
public class LikadaOrdersStartCommand extends BotCommand implements Serializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(LikadaOrdersStartCommand.class);

    @Inject
    private TelegramUserService telegramUserService;
    @Inject
    private TelegramBotService telegramBotService;
    @Inject
    private TelegramButtonMenuService telegramButtonMenuService;
    @Inject
    private TelegramConversationService telegramConversationService;
    @Inject
    private LikadaOrdersBotController likadaOrdersBotController;

    public LikadaOrdersStartCommand() {
        super("start", "С помощью этой команды вы можете запустить бот");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        StringBuilder messageBuilder = new StringBuilder();
        try {
            TelegramUser telegramUser = ((telegramUser = telegramUserService.findByTelegramUserId(user.getId())) != null)
                                        ? telegramUser : telegramUserService.findByTelegramUsername(user.getUserName());
            TelegramBot telegramBot = telegramBotService.findByTelegramBotUsername(LikadaOrdersBotUtils.LIK_ORDERS_BOT_USERNAME);
            TelegramButtonMenu telegramButtonMenu = telegramButtonMenuService.findByType(TelegramButtonMenuEnum.MAIN_MENU.getType());
            if (telegramUser == null){
                telegramUser= new TelegramUser();
                messageBuilder.append("Добро пожаловать, ").append(user.getFirstName()).append(" ").append(user.getLastName()).append("!\n\n");
                messageBuilder.append("Для продолжения нажмите нужную вам кнопку.\n\n");
                messageBuilder.append("Вы можете отписатся через команду \n/stop");
            } else {
                messageBuilder.append(user.getFirstName()).append(" ").append(user.getLastName()).append(" ваш логин ").append(user.getUserName()).append(" уже зарегистрирован.");
            }
            telegramUser.setTelegramUserId(user.getId());
            telegramUser.setUsername(user.getUserName());
            telegramUser.setFirstName(user.getFirstName());
            telegramUser.setLastName(user.getLastName());
            telegramUser.setActive(true);
            telegramUserService.save(telegramUser);
            // Update telegram conversation for this user
            TelegramConversation telegramConversation = telegramConversationService.findByChatId(chat.getId());
            if(telegramConversation == null){
                telegramConversation = new TelegramConversation();
                telegramConversation.setChatId(chat.getId());
                telegramConversation.setTelegramUser(telegramUser);
                telegramConversation.setTelegramBot(telegramBot);
                telegramConversation.setCurrentMenu(telegramButtonMenu);
            } else {
                telegramConversation.setTelegramUser(telegramUser);
                telegramConversation.setTelegramBot(telegramBot);
                telegramConversation.setCurrentMenu(telegramButtonMenu);
            }
            telegramConversationService.save(telegramConversation);
            SendMessage answer = new SendMessage();
            answer.setReplyMarkup(likadaOrdersBotController.getMainMenuKeyboard());
            answer.setChatId(chat.getId().toString());
            answer.setText(messageBuilder.toString());
            absSender.sendMessage(answer);
        } catch (HibernateException e) {
            LOGGER.error("Error while performing Hibernate query", e);
        } catch (TelegramApiException e){
            BotLogger.error("Can't send back the reply", "START_COMMAND", e);
        }
    }
}
