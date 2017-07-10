package pro.likada.bot.products.updateHandlers;


import org.telegram.telegrambots.api.methods.send.SendDocument;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.PhotoSize;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.logging.BotLogger;
import pro.likada.bot.products.BotConfiguration;
import pro.likada.bot.products.commands.HelloCommand;
import pro.likada.bot.products.commands.HelpCommand;
import pro.likada.bot.products.commands.StartCommand;
import pro.likada.bot.products.commands.StopCommand;
import pro.likada.dao.daoImpl.TelegramUserDAOImpl;
import pro.likada.model.TelegramUser;

import javax.enterprise.context.Dependent;
import javax.inject.Named;
import java.util.List;

@Named
@Dependent
public class CommandsHandler extends TelegramLongPollingCommandBot {

    public static final String LOGTAG = "COMMANDSHANDLER";
    private static TelegramUserDAOImpl telegramUserDAO= new TelegramUserDAOImpl();
    /**
     * Constructor.
     */
    public CommandsHandler() {
        register(new HelloCommand());
        register(new StartCommand());
        register(new StopCommand());
        HelpCommand helpCommand = new HelpCommand(this);
        register(helpCommand);
        registerDefaultAction((absSender, message) -> {
            SendMessage commandUnknownMessage = new SendMessage();
            commandUnknownMessage.setChatId(message.getChatId());
            commandUnknownMessage.setText("Это команда '" + message.getText() + "' не существует ");
            try {
                absSender.sendMessage(commandUnknownMessage);
            } catch (TelegramApiException e) {
                BotLogger.error(LOGTAG, e);
            }
            helpCommand.execute(absSender, message.getFrom(), message.getChat(), new String[] {});
        });
    }

    @Override
    public void processNonCommandUpdate(Update update) {

        if (update.hasMessage()) {
            Message message = update.getMessage();
            for (TelegramUser telegramUser : telegramUserDAO.findAll()) {
                if(!message.getFrom().getId().equals(telegramUser.getTelegramUserId())) {
                    if (telegramUser.isActive()) {
                        if (message.hasText()) {
                            SendMessage sendMessage = new SendMessage();
                            sendMessage.setChatId(String.valueOf(telegramUser.getTelegramUserId()));
                            String messageBuilder="";
                                messageBuilder = "<b>" + message.getChat().getFirstName() + "</b>" + "\n";
                            messageBuilder = messageBuilder + message.getText();
                            sendMessage.setText(messageBuilder);
                            sendMessage.setParseMode("HTML");
                            try {
                                sendMessage(sendMessage);
                            } catch (TelegramApiException e) {
                                BotLogger.error(LOGTAG, e);
                            }
                        } else if(message.hasDocument()){
                            SendDocument sendDocument = new SendDocument();
                            sendDocument.setChatId(String.valueOf(telegramUser.getTelegramUserId()));
                            sendDocument.setDocument(message.getDocument().getFileId());
                            try {
                                sendDocument(sendDocument);
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }
                        }
                        else if(message.hasPhoto()){
                            List<PhotoSize> photo = message.getPhoto();
                            PhotoSize photoSize = photo.get(0);
                            SendPhoto sendPhoto= new SendPhoto();
                            sendPhoto.setChatId(String.valueOf(telegramUser.getTelegramUserId()));
                            sendPhoto.setPhoto(photoSize.getFileId());
                            sendPhoto.setCaption(message.getChat().getFirstName()+" "+ message.getCaption());
                            try {
                                sendPhoto(sendPhoto);
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                }
            }
        }
    }

    @Override
    public String getBotUsername() {
        return BotConfiguration.LIK_PRODUCTS_BOT_USER;
    }

    @Override
    public String getBotToken() {
        return BotConfiguration.LIK_PRODUCTS_BOT_TOKEN;
    }
}