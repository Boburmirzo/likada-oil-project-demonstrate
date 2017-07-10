package pro.likada.bot.orders.commands;

import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
//import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commands.BotCommand;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.logging.BotLogger;
import pro.likada.model.TelegramConversation;
import pro.likada.service.TelegramConversationService;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Created by Yusupov on 4/2/2017.
 */
@Named
@Dependent
public class LikadaOrdersStopCommand extends BotCommand implements Serializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(LikadaOrdersStopCommand.class);

    @Inject
    private TelegramConversationService telegramConversationService;

    public LikadaOrdersStopCommand() {
        super("stop", "С помощью этой команды вы можете остановить уведомления");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        try {
            TelegramConversation telegramConversation = telegramConversationService.findByChatId(chat.getId());
            if(telegramConversation!=null){
                telegramConversationService.delete(telegramConversation);
            }
            SendMessage answer = new SendMessage();
            answer.setChatId(chat.getId().toString());
            answer.setText("Пока " + user.getFirstName() + " " + user.getLastName() + "\n" + "До встречи!\n\nВы можете вернутся в любое время набрав команду \n/start");
          /*  ReplyKeyboardRemove replyKeyboardRemove = new ReplyKeyboardRemove();
            replyKeyboardRemove.setSelective(true);
            answer.setReplyMarkup(replyKeyboardRemove);*/
            absSender.sendMessage(answer);
        } catch (HibernateException e) {
            LOGGER.error("Error while performing Hibernate query", e);
        } catch (TelegramApiException e){
            BotLogger.error("Can't send back the reply", "STOP_COMMAND", e);
        }
    }
}
