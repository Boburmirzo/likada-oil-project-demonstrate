package pro.likada.bot.products.updateHandlers;

import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import pro.likada.bot.products.BotConfiguration;


public class WebHookExampleHandler extends TelegramWebhookBot {
    @Override
    public BotApiMethod onWebhookUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(update.getMessage().getChatId().toString());
            sendMessage.setText("Well, all information looks like noise until you break the code.");
            return sendMessage;
        }
        return null;
    }

    @Override
    public String getBotUsername() {
        return BotConfiguration.LIK_PRODUCTS_BOT_USER;
    }

    @Override
    public String getBotToken() {
        return BotConfiguration.LIK_PRODUCTS_BOT_TOKEN;
    }

    @Override
    public String getBotPath() {
        return BotConfiguration.LIK_PRODUCTS_BOT_USER; //arbitrary path to deliver updates on, username is an example.
    }
}
