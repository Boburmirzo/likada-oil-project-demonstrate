package pro.likada.bot.orders;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingCommandBot;
import pro.likada.bot.orders.utils.LikadaOrdersBotUtils;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Yusupov on 3/31/2017.
 */
@Named
@Dependent
public class LikadaOrdersBotLongPolling extends TelegramLongPollingCommandBot {

    private static final Logger LOGGER = LoggerFactory.getLogger(LikadaOrdersBotLongPolling.class);

    @Inject
    private LikadaOrdersBotController likadaOrdersBotController;

    @Override
    public String getBotUsername() {
        return LikadaOrdersBotUtils.LIK_ORDERS_BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return LikadaOrdersBotUtils.LIK_ORDERS_BOT_TOKEN;
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        LOGGER.info("NonCommandUpdate received from {}, with text: {}", update.getMessage().getFrom().getUserName(), update.getMessage().getText());
        if(update.hasMessage() && update.getMessage().hasText()){
            likadaOrdersBotController.handleIncomingMessage(update.getMessage());
        }
    }


}
