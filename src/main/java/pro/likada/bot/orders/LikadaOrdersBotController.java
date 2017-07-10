package pro.likada.bot.orders;

import com.vdurmont.emoji.EmojiParser;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import pro.likada.bot.orders.utils.LikadaOrdersBotUtils;
import pro.likada.model.*;
import pro.likada.service.*;
import pro.likada.util.OrderStatusTypeEnum;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.text.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Yusupov on 3/31/2017.
 */
@Named
@ApplicationScoped
public class LikadaOrdersBotController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LikadaOrdersBotController.class);

    @Inject
    private LikadaOrdersBotLongPolling likadaOrdersBotLongPolling;
    @Inject
    private TelegramUserService telegramUserService;
    @Inject
    private TelegramBotService telegramBotService;
    @Inject
    private TelegramButtonMenuService telegramButtonMenuService;
    @Inject
    private TelegramConversationService telegramConversationService;
    @Inject
    private ContractorService contractorService;
    @Inject
    private CustomerService customerService;
    @Inject
    private OrderService orderService;
    @Inject
    private ProductGroupService productGroupService;
    @Inject
    private ProductService productService;
    @Inject
    private ProductPriceService productPriceService;
    @Inject
    private OrderStatusTypeService orderStatusTypeService;
    @Inject
    private OrderPaymentTypeService orderPaymentTypeService;
    @Inject
    private ShipmentBasisService shipmentBasisService;

    /* Menu state string */ //TODO they are aready in enum, try to use from enum class
    private static final String MAIN_MENU = "MAIN_MENU";
    private static final String ORGANIZATIONS = "ORGANIZATIONS";
    private static final String CUSTOMERS = "CUSTOMERS";
    private static final String CONTRACTORS = "CONTRACTORS";
    private static final String PRODUCT_CATEGORIES_AND_PRODUCTS = "PRODUCT_CATEGORIES_AND_PRODUCTS";
    private static final String PRODUCT_MARKUP = "PRODUCT_MARKUP";
    private static final String TRANSPORTATION_PRICE = "TRANSPORTATION_PRICE";
    private static final String SHIPMENT_BASIS = "SHIPMENT_BASIS";
    private static final String PRODUCT_COUNT = "PRODUCT_COUNT";
    private static final String UNLOAD_POINT = "UNLOAD_POINT";
    private static final String UNLOAD_CONTACT = "UNLOAD_CONTACT";
    private static final String UNLOAD_DATE_PLAN = "UNLOAD_DATE_PLAN";
    private static final String ORDER_PAYMENT_TYPE = "ORDER_PAYMENT_TYPE";
    private static final String UNLOAD_DESCRIPTION = "UNLOAD_DESCRIPTION";

    /* Menu strings */
    private static final String DEBIT = EmojiParser.parseToUnicode(":dollar: Дебиторка");
    private static final String INCOMING = EmojiParser.parseToUnicode(":truck: Поступления");
    private static final String ORDER = EmojiParser.parseToUnicode(":memo: Заявка");
    private static final String PRODUCTS = "Товары";
    private static final String SUBCATEGORIES = "Подкатегории";
    private static final String BACK = EmojiParser.parseToUnicode(":arrow_left: Назад");
    private static final String FORWARD = EmojiParser.parseToUnicode("Далее :arrow_right:");
    private static final String CANCEL = EmojiParser.parseToUnicode(":x: Отмена");
    private static final String PREVIOUS = EmojiParser.parseToUnicode(":rewind: ");
    private static final String NEXT = EmojiParser.parseToUnicode(" :fast_forward:");

    /* Utils */
    private static final Pattern regexForPrevious = Pattern.compile(PREVIOUS + "\\[(-?\\d+)]");
    private static final Pattern regexForNext = Pattern.compile("\\[(-?\\d+)]" + NEXT);
    private static final Pattern regex = Pattern.compile("\\[(-?\\d+)]");
    private static final Pattern regexSubCategories = Pattern.compile("\\{(-?\\d+)}");
    private static final Pattern regexDate = Pattern.compile("\\((.*?)\\)");
    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yy");
    private static final Locale locale = new Locale("ru","RU");

    private static DecimalFormat currencyFormatter;

    private TelegramBot telegramBot;

    public LikadaOrdersBotController() {
        currencyFormatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        DecimalFormatSymbols symbols = currencyFormatter.getDecimalFormatSymbols();
        symbols.setGroupingSeparator(' ');
        currencyFormatter.setDecimalFormatSymbols(symbols);
    }

    public void handleIncomingMessage(Message message){
        try{
            /* Initialize TelegramUser, TelegramBot and TelegramConversation */
            if(telegramBot == null)
                this.telegramBot = telegramBotService.findByTelegramBotUsername(LikadaOrdersBotUtils.LIK_ORDERS_BOT_USERNAME);
            TelegramUser telegramUser = ((telegramUser = telegramUserService.findByTelegramUserId(message.getFrom().getId())) != null)
                    ? telegramUser : telegramUserService.findByTelegramUsername(message.getFrom().getUserName());
            TelegramConversation telegramConversation = telegramConversationService.findByTelegramUserAndTelegramBot(telegramUser, telegramBot);
            /* Start handling the message */
            SendMessage sendMessageRequest;
            if(telegramUser == null || telegramUser.getOwnerUser() == null){
                sendMessageRequest = sendMessageDefault(message, telegramUser, telegramConversation);
            } else if(telegramConversation!=null)
                switch(telegramConversation.getCurrentMenu().getType()) {
                    case MAIN_MENU:
                        sendMessageRequest = messageOnMainMenu(message, telegramUser, telegramConversation);
                        break;
                    case ORGANIZATIONS:
                        sendMessageRequest = messageOnOrganizationsMenu(message, telegramUser, telegramConversation);
                        break;
                    case CUSTOMERS:
                        sendMessageRequest = messageOnCustomersMenu(message, telegramUser, telegramConversation);
                        break;
                    case CONTRACTORS:
                        sendMessageRequest = messageOnContractorsMenu(message, telegramUser, telegramConversation);
                        break;
                    case PRODUCT_CATEGORIES_AND_PRODUCTS:
                        sendMessageRequest = messageOnProductCategoriesMenu(message, telegramUser, telegramConversation);
                        break;
                    case PRODUCT_MARKUP:
                        sendMessageRequest = messageOnProductMarkupMenu(message, telegramUser, telegramConversation);
                        break;
                    case TRANSPORTATION_PRICE:
                        sendMessageRequest = messageOnTransportationPriceMenu(message, telegramUser, telegramConversation);
                        break;
                    case SHIPMENT_BASIS:
                        sendMessageRequest = messageOnShipmentBasisMenu(message, telegramUser, telegramConversation);
                        break;
                    case PRODUCT_COUNT:
                        sendMessageRequest = messageOnProductCountMenu(message, telegramUser, telegramConversation);
                        break;
                    case UNLOAD_POINT:
                        sendMessageRequest = messageOnUnloadPointMenu(message, telegramUser, telegramConversation);
                        break;
                    case UNLOAD_CONTACT:
                        sendMessageRequest = messageOnUnloadContactMenu(message, telegramUser, telegramConversation);
                        break;
                    case UNLOAD_DATE_PLAN:
                        sendMessageRequest = messageOnUnLoadDateMenu(message, telegramUser, telegramConversation);
                        break;
                    case ORDER_PAYMENT_TYPE:
                        sendMessageRequest = messageOnOrderPaymentTypeMenu(message, telegramUser, telegramConversation);
                        break;
                    case UNLOAD_DESCRIPTION:
                        sendMessageRequest = messageOnUnloadDescriptionMenu(message, telegramUser, telegramConversation);
                        break;
                    default:
                        sendMessageRequest = sendMessageDefault(message, telegramUser, telegramConversation);
                        break;
                }
            else {
                sendMessageRequest = sendMessageDefault(message, telegramUser, null);
            }
            likadaOrdersBotLongPolling.sendMessage(sendMessageRequest);
        } catch (TelegramApiException e) {
            LOGGER.error("Error in Telegram Bot: @{}", LikadaOrdersBotUtils.LIK_ORDERS_BOT_USERNAME, e);
        } catch (HibernateException e) {
            LOGGER.error("Error in Telegram Bot: @{} on Hibernate Query", LikadaOrdersBotUtils.LIK_ORDERS_BOT_USERNAME, e);
        }
    }

    private SendMessage sendHelpMessage(Long chatId, Integer messageId, ReplyKeyboardMarkup replyKeyboardMarkup) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        if (replyKeyboardMarkup != null) {
            sendMessage.setReplyMarkup(replyKeyboardMarkup);
        }
        sendMessage.setText("Ошибка. Введите еще раз или выберите через меню");
        return sendMessage;
    }
    private SendMessage sendPreviousOrNextPageMessage(Long chatId, Integer messageId, String mainText, ReplyKeyboardMarkup replyKeyboardMarkup, Integer startFrom) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        if (replyKeyboardMarkup != null) {
            sendMessage.setReplyMarkup(replyKeyboardMarkup);
        }
        sendMessage.setText(mainText + "\nДля поиска введите текст." + "\nСписок от " + startFrom + " до " + (startFrom - 1 + LikadaOrdersBotUtils.MAX_NUMBER_OF_RESULTS));
        return sendMessage;
    }
    private SendMessage sendHasNoAccessMessage(Long chatId, Integer messageId, ReplyKeyboardMarkup replyKeyboardMarkup) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        if (replyKeyboardMarkup != null) {
            sendMessage.setReplyMarkup(replyKeyboardMarkup);
        }
        sendMessage.setText("Вы еще не зарегистрированы в системе. Попробуйте попозже.");
        return sendMessage;
    }

    private SendMessage onCancelCommand(Message message, ReplyKeyboard replyKeyboard, TelegramUser telegramUser, TelegramConversation telegramConversation) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(replyKeyboard);
        sendMessage.setText("Заявка отменено");
        try {
            telegramConversation.setCurrentMenu(telegramButtonMenuService.getMainMenu());
            telegramConversationService.save(telegramConversation);
            Order order = orderService.findByTelegramUserAndSubmissionStatus(telegramUser, false);
            orderService.delete(order);
        } catch (HibernateException e){
            LOGGER.error("Error in Telegram Bot: @{} on Hibernate Query", LikadaOrdersBotUtils.LIK_ORDERS_BOT_USERNAME, e);
        }
        return sendMessage;
    }
    private SendMessage onBackCommand(Message message, ReplyKeyboard replyKeyboard, TelegramUser telegramUser, TelegramConversation telegramConversation) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(replyKeyboard);
        try {
            TelegramButtonMenu telegramButtonMenuPrevious = telegramButtonMenuService.getPreviousMenu(telegramConversation.getCurrentMenu());
            sendMessage.setText("Назад в меню - '" + telegramButtonMenuPrevious.getName() + "'");
            telegramConversation.setCurrentMenu(telegramButtonMenuPrevious);
            telegramConversationService.save(telegramConversation);
        } catch (HibernateException e){
            LOGGER.error("Error in Telegram Bot: @{} on Hibernate Query", LikadaOrdersBotUtils.LIK_ORDERS_BOT_USERNAME, e);
        }
        return sendMessage;
    }

    private SendMessage sendMessageDefault(Message message, TelegramUser telegramUser, TelegramConversation telegramConversation) {
        ReplyKeyboardMarkup replyKeyboardMarkup = getMainMenuKeyboard();
        if(telegramConversation == null)
            telegramConversation = new TelegramConversation();
        telegramConversation.setChatId(message.getChatId());
        telegramConversation.setTelegramUser(telegramUser);
        telegramConversation.setTelegramBot(telegramBot);
        telegramConversation.setCurrentMenu(telegramButtonMenuService.getMainMenu());
        telegramConversationService.save(telegramConversation);
        if(telegramUser.getOwnerUser()==null){
            return sendHasNoAccessMessage(message.getChatId(), message.getMessageId(), replyKeyboardMarkup);
        }
        return sendHelpMessage(message.getChatId(), message.getMessageId(), replyKeyboardMarkup);
    }
    public ReplyKeyboardMarkup getMainMenuKeyboard() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboad(false);
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add(DEBIT);
        keyboardFirstRow.add(INCOMING);
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        keyboardSecondRow.add(ORDER);
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }

    /* MAIN MENU */
    private SendMessage messageOnMainMenu(Message message, TelegramUser telegramUser, TelegramConversation telegramConversation) {
        SendMessage sendMessageRequest = sendChooseOptionMessage(message.getChatId(), message.getMessageId(), getMainMenuKeyboard());
        if (message.hasText()) {
            if (message.getText().equals(DEBIT)) {
                sendMessageRequest = onDebitMenuSelection(message.getChatId(), message.getMessageId(),getMainMenuKeyboard());
            } else if (message.getText().equals(INCOMING)) {
                sendMessageRequest = onIncomingMenuSelection(message.getChatId(), message.getMessageId(),getMainMenuKeyboard());
            } else if (message.getText().equals(ORDER)) {
                sendMessageRequest = onOrderMenuSelection(message, telegramUser, telegramConversation);
            } else {
                sendMessageRequest = sendHelpMessage(message.getChatId(), message.getMessageId(), getMainMenuKeyboard());
            }
        }
        return sendMessageRequest;
    }
    private SendMessage sendChooseOptionMessage(Long chatId, Integer messageId, ReplyKeyboard replyKeyboard) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId.toString());
        sendMessage.setReplyMarkup(replyKeyboard);
        sendMessage.setText("Вы можете посмотреть историю дебиторской задолженности, новых поступлиний или можете оставить новую заявку");
        return sendMessage;
    }
    private SendMessage onDebitMenuSelection(Long chatId, Integer messageId, ReplyKeyboard replyKeyboard) {
        LOGGER.info("------- Дебиторка ---------");
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId.toString());
        sendMessage.setReplyMarkup(replyKeyboard);
        sendMessage.setText("Нет никаких данных по дебиторке");
        return sendMessage;
    }
    private SendMessage onIncomingMenuSelection(Long chatId, Integer messageId, ReplyKeyboard replyKeyboard) {
        LOGGER.info("------- Поступления ---------");
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId.toString());
        sendMessage.setReplyMarkup(replyKeyboard);
        sendMessage.setText("На данный момент поступлений нет");
        return sendMessage;
    }
    private SendMessage onOrderMenuSelection(Message message, TelegramUser telegramUser, TelegramConversation telegramConversation) {
        LOGGER.info("------- Заявка ---------");
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        ReplyKeyboardMarkup replyKeyboardMarkup = getOrganizationsKeyboards();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        sendMessage.setChatId(message.getChatId());
        sendMessage.setText("Выберите организацию из списка");
        try {
            Order order = orderService.findByTelegramUserAndSubmissionStatus(telegramUser, false);
            orderService.copyCurrentUserInfoForCreatorAndOwner(order, telegramUser.getOwnerUser());
            orderService.save(order);
            telegramConversation.setCurrentMenu(telegramButtonMenuService.getNextMenu(telegramConversation.getCurrentMenu()));
            telegramConversationService.save(telegramConversation);
        } catch (HibernateException e){
            LOGGER.error("Error in Telegram Bot: @{} on Hibernate Query", LikadaOrdersBotUtils.LIK_ORDERS_BOT_USERNAME, e);
        }
        return sendMessage;
    }
    private ReplyKeyboardMarkup getOrganizationsKeyboards() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboad(true);
        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow firstRow = new KeyboardRow();
        firstRow.add(BACK);
        firstRow.add(CANCEL);
        keyboard.add(firstRow);

        for (Contractor contractor : contractorService.findAllCompaniesToAssignAsClient()) {
            KeyboardRow row = new KeyboardRow();
            row.add(contractor.getNameWork());
            keyboard.add(row);
        }
        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }

    /* ORGANIZATIONS MENU */
    private SendMessage messageOnOrganizationsMenu(Message message, TelegramUser telegramUser, TelegramConversation telegramConversation) {
        SendMessage sendMessageRequest = sendHelpMessage(message.getChatId(), message.getMessageId(), getOrganizationsKeyboards());
        if (message.hasText()) {
            if(message.getText().startsWith(CANCEL)){
                sendMessageRequest = onCancelCommand(message, getMainMenuKeyboard(), telegramUser, telegramConversation);
            } else if(message.getText().startsWith(BACK)){
                sendMessageRequest = onBackCommand(message, getMainMenuKeyboard(), telegramUser, telegramConversation);
            } else {
                List<Contractor> contractors = contractorService.findByNameWork(message.getText());
                if(contractors!=null && !contractors.isEmpty()){
                    sendMessageRequest = onOrganizationsMenuSelection(message, contractors, telegramUser, telegramConversation);
                }
            }
        }
        return sendMessageRequest;
    }
    private SendMessage onOrganizationsMenuSelection(Message message, List<Contractor> contractors, TelegramUser telegramUser, TelegramConversation telegramConversation) {
        LOGGER.info("------- Организация: {} ---------", contractors.get(0));
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        ReplyKeyboardMarkup replyKeyboardMarkup = getCustomersKeyboard(telegramUser, 1);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        sendMessage.setChatId(message.getChatId());
        sendMessage.setText("Выберите клиента из списка");
        try {
            Order order = orderService.findByTelegramUserAndSubmissionStatus(telegramUser, false);
            orderService.copySelectedCompanyInfo(order, contractors.get(0)); // if we have duplicates, choose and save the first one only
            orderService.save(order);
            telegramConversation.setCurrentMenu(telegramButtonMenuService.getNextMenu(telegramConversation.getCurrentMenu()));
            telegramConversationService.save(telegramConversation);
        } catch (HibernateException e) {
            LOGGER.error("Error in Telegram Bot: @{} on Hibernate Query", LikadaOrdersBotUtils.LIK_ORDERS_BOT_USERNAME, e);
        }
        return sendMessage;
    }
    private ReplyKeyboardMarkup getCustomersKeyboard(TelegramUser telegramUser, Integer startFrom) {
        LOGGER.info("Getting Customers Keyboard");
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboad(true);
        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow firstRow = new KeyboardRow();
        firstRow.add(BACK);
        firstRow.add(CANCEL);
        keyboard.add(firstRow);

        List<Customer> customers = customerService.findAllCustomersBasedOnUserRole(telegramUser.getOwnerUser(), startFrom, LikadaOrdersBotUtils.MAX_NUMBER_OF_RESULTS);
        for (Customer customer: customers){
            KeyboardRow row = new KeyboardRow();
            row.add(customer.getTitle());
            keyboard.add(row);
        }

        KeyboardRow lastRow = new KeyboardRow();
        if(startFrom != 1){
            lastRow.add(PREVIOUS + "[" + startFrom + "]");
        }
        if(customerService.countCustomersBasedOnUserRole(telegramUser.getOwnerUser()) >= startFrom + LikadaOrdersBotUtils.MAX_NUMBER_OF_RESULTS) {
            lastRow.add("[" + (startFrom + LikadaOrdersBotUtils.MAX_NUMBER_OF_RESULTS) + "]" + NEXT);
        }
        keyboard.add(lastRow);

        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }

    /* CUSTOMERS MENU */
    private SendMessage messageOnCustomersMenu(Message message, TelegramUser telegramUser, TelegramConversation telegramConversation){
        SendMessage sendMessageRequest = sendHelpMessage(message.getChatId(), message.getMessageId(), getCustomersKeyboard(telegramUser, 1));
        if (message.hasText()) {
            if(message.getText().startsWith(CANCEL)){
                sendMessageRequest = onCancelCommand(message, getMainMenuKeyboard(), telegramUser, telegramConversation);
            } else if(message.getText().startsWith(BACK)){
                sendMessageRequest = onBackCommand(message, getOrganizationsKeyboards(), telegramUser, telegramConversation);
            } else {
                Matcher regexMatcher;
                if((regexMatcher = regexForPrevious.matcher(message.getText())).find()){
                    Integer startFrom = Integer.valueOf(regexMatcher.group(1));
                    sendMessageRequest = sendPreviousOrNextPageMessage(
                            message.getChatId(),
                            message.getMessageId(),
                            "Выберите клиента из списка.",
                            getCustomersKeyboard(telegramUser, startFrom - LikadaOrdersBotUtils.MAX_NUMBER_OF_RESULTS),
                            startFrom - LikadaOrdersBotUtils.MAX_NUMBER_OF_RESULTS);
                } else if((regexMatcher = regexForNext.matcher(message.getText())).find()){
                    Integer startFrom = Integer.valueOf(regexMatcher.group(1));
                    sendMessageRequest = sendPreviousOrNextPageMessage(
                            message.getChatId(),
                            message.getMessageId(),
                            "Выберите клиента из списка.",
                            getCustomersKeyboard(telegramUser, startFrom), startFrom);
                } else {
                    List<Customer> customers = customerService.findByTitle(message.getText());
                    if(customers!=null && !customers.isEmpty()){
                        sendMessageRequest = onCustomersMenuSelection(message, customers, telegramUser, telegramConversation);
                    }
                }
            }
        }
        return sendMessageRequest;
    }
    private SendMessage onCustomersMenuSelection(Message message, List<Customer> customers, TelegramUser telegramUser, TelegramConversation telegramConversation) {
        Customer selectedCustomer = customers.get(0);
        LOGGER.info("------- Клиент: {} ---------", selectedCustomer);
        SendMessage sendMessage = new SendMessage();
        ReplyKeyboardMarkup replyKeyboardMarkup;
        if(selectedCustomer.getContractors()!=null && selectedCustomer.getContractors().size()>1){
            sendMessage.setText("Выберите один из Контрагентов клиента.");
            replyKeyboardMarkup = getContractorsKeyboard(selectedCustomer);
        } else {
            sendMessage.setText("Выберите товар или подкатегорию из списка");
            replyKeyboardMarkup = getProductCategoriesKeyboard((long)0);
        }
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        sendMessage.setChatId(message.getChatId());
        try {
            Order order = orderService.findByTelegramUserAndSubmissionStatus(telegramUser, false);
            orderService.copySelectedCustomerInfo(order, selectedCustomer);
            if(selectedCustomer.getContractors()!=null && selectedCustomer.getContractors().size()==1){
                orderService.copySelectedContractorInfo(order, selectedCustomer.getContractorsList().get(0));
                telegramConversation.setCurrentMenu(telegramButtonMenuService.getNextMenu(telegramConversation.getCurrentMenu()));
            } else {
                telegramConversation.setCurrentMenu(telegramButtonMenuService.getContractorsMenu());
            }
            orderService.save(order);
            telegramConversationService.save(telegramConversation);
        } catch (HibernateException e) {
            LOGGER.error("Error in Telegram Bot: @{} on Hibernate Query", LikadaOrdersBotUtils.LIK_ORDERS_BOT_USERNAME, e);
        }
        return sendMessage;
    }
    private ReplyKeyboardMarkup getContractorsKeyboard(Customer selectedCustomer) {
        LOGGER.info("Getting Contractors of Customer {} ", selectedCustomer);
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboad(true);
        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow firstRow = new KeyboardRow();
        firstRow.add(BACK);
        firstRow.add(CANCEL);
        keyboard.add(firstRow);

        List<Contractor> contractors = contractorService.findAllContractorsLinkedToCustomer(null, selectedCustomer.getId());
        for (Contractor contractor: contractors){
            KeyboardRow row = new KeyboardRow();
            row.add(contractor.getNameWork());
            keyboard.add(row);
        }

        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }

    /* CONTRACTORS MENU */
    private SendMessage messageOnContractorsMenu(Message message, TelegramUser telegramUser, TelegramConversation telegramConversation) {
        Order order = orderService.findByTelegramUserAndSubmissionStatus(telegramUser, false);
        SendMessage sendMessageRequest = sendHelpMessage(message.getChatId(), message.getMessageId(), getContractorsKeyboard(order.getCustomer()));
        if (message.hasText()) {
            if(message.getText().startsWith(CANCEL)){
                sendMessageRequest = onCancelCommand(message, getMainMenuKeyboard(), telegramUser, telegramConversation);
            } else if(message.getText().startsWith(BACK)){
                sendMessageRequest = onBackCommand(message, getCustomersKeyboard(telegramUser, 1), telegramUser, telegramConversation);
            } else {
                List<Contractor> contractors = contractorService.findByNameWork(message.getText());
                if(contractors!=null && !contractors.isEmpty()){
                    sendMessageRequest = onContractorsMenuSelection(message, contractors, telegramUser, telegramConversation);
                }
            }
        }
        return sendMessageRequest;
    }
    private SendMessage onContractorsMenuSelection(Message message, List<Contractor> contractors, TelegramUser telegramUser, TelegramConversation telegramConversation) {
        LOGGER.info("------- Контрагент: {} ---------", contractors.get(0));
        SendMessage sendMessage = new SendMessage();
        ReplyKeyboardMarkup replyKeyboardMarkup;
        sendMessage.setText("Выберите товар или подкатегорию из списка");
        replyKeyboardMarkup = getProductCategoriesKeyboard((long)0);
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        sendMessage.setChatId(message.getChatId());
        try {
            Order order = orderService.findByTelegramUserAndSubmissionStatus(telegramUser, false);
            orderService.copySelectedContractorInfo(order, contractors.get(0));
            orderService.save(order);
            telegramConversation.setCurrentMenu(telegramButtonMenuService.getNextMenu(telegramConversation.getCurrentMenu()));
            telegramConversationService.save(telegramConversation);
        } catch (HibernateException e) {
            LOGGER.error("Error in Telegram Bot: @{} on Hibernate Query", LikadaOrdersBotUtils.LIK_ORDERS_BOT_USERNAME, e);
        }
        return sendMessage;
    }
    private ReplyKeyboardMarkup getProductCategoriesKeyboard(long productGroupId) {
        LOGGER.info("Getting Product Categories Keyboard");
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboad(true);
        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow firstRow = new KeyboardRow();
        firstRow.add(BACK);
        firstRow.add(CANCEL);
        keyboard.add(firstRow);

        /* Show products of the category first and then subcategories */
        insertProductsAndCategoriesButtonsIfExistsInCategory(keyboard, productGroupId);
        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }
    private void insertProductsAndCategoriesButtonsIfExistsInCategory(List<KeyboardRow> keyboard, long productGroupId) {
        /* Insert products first */
        List<Product> products = productService.getProductsByGroupId(productGroupId);
        if(products!=null && !products.isEmpty()){
            KeyboardRow productsLabelButton = new KeyboardRow();
            productsLabelButton.add(PRODUCTS);
            keyboard.add(productsLabelButton);
            for(Product product: products){
                KeyboardRow row = new KeyboardRow();
                ProductPrice productPrice = productPriceService.getProductActualPrice(product.getProductPricesList());
                row.add(product.getCode() +
                        " - " + currencyFormatter.format(productPrice.getPrice()) + " \u20BD" +
                        " - " + DateFormat.getDateInstance(DateFormat.SHORT, locale).format(productPrice.getTimeModified()) +
                        " [" + product.getId() + "]");
                keyboard.add(row);
            }
        }
        /* Insert productGroup subcategories */
        List<ProductGroup> productGroups = productGroupService.getProductGroupsByParentId(productGroupId);
        if(productGroups!=null && !productGroups.isEmpty()){
            KeyboardRow subcategoriesLabelButton = new KeyboardRow();
            subcategoriesLabelButton.add(SUBCATEGORIES);
            keyboard.add(subcategoriesLabelButton);
            Iterator<ProductGroup> iterable = productGroups.iterator();
            while (iterable.hasNext()){
                KeyboardRow row = new KeyboardRow();
                ProductGroup productGroup = iterable.next();
                row.add(productGroup.getTitle() + " {" + productGroup.getId() + "}");
                if (iterable.hasNext()){
                    productGroup = iterable.next();
                    row.add(productGroup.getTitle() + " {" + productGroup.getId() + "}");
                }
                keyboard.add(row);
            }
        }

    }

    /* PRODUCT_CATEGORIES_AND_PRODUCTS MENU */
    private SendMessage messageOnProductCategoriesMenu(Message message, TelegramUser telegramUser, TelegramConversation telegramConversation){
        SendMessage sendMessageRequest = sendHelpMessage(message.getChatId(), message.getMessageId(), getProductCategoriesKeyboard((long)0));
        if (message.hasText()) {
            if(message.getText().startsWith(CANCEL)){
                sendMessageRequest = onCancelCommand(message, getMainMenuKeyboard(), telegramUser, telegramConversation);
            } else if(message.getText().startsWith(BACK)){
                sendMessageRequest = onBackCommand(message, getCustomersKeyboard(telegramUser, 1), telegramUser, telegramConversation);
            } else {
                Matcher regexMatcher;
                if ((regexMatcher = regex.matcher(message.getText())).find()){  // If the choice for product
                    Product product = productService.findById(Long.valueOf(regexMatcher.group(1)));
                    if(product!=null){
                        sendMessageRequest = onProductsMenuSelection(message, product, telegramUser, telegramConversation);
                    }
                } else if ((regexMatcher = regexSubCategories.matcher(message.getText())).find()){  // If the choice for subcategory
                    ProductGroup productGroup = productGroupService.findById(Long.valueOf(regexMatcher.group(1)));
                    if(productGroup!=null){
                        sendMessageRequest = onProductCategoriesMenuSelection(message, productGroup);
                    }
                }
            }
        }
        return sendMessageRequest;
    }
    private SendMessage onProductCategoriesMenuSelection(Message message, ProductGroup productGroup) {
        LOGGER.info("------- Категория товара: {} ---------", productGroup);
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        ReplyKeyboardMarkup replyKeyboardMarkup = getProductCategoriesKeyboard(productGroup.getId());
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        sendMessage.setChatId(message.getChatId());
        sendMessage.setText("Выберите товар или подкатегорию из списка");
        return sendMessage;
    }
    private SendMessage onProductsMenuSelection(Message message, Product product, TelegramUser telegramUser, TelegramConversation telegramConversation) {
        LOGGER.info("------- Товар: {} ---------", product);
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        ReplyKeyboardMarkup replyKeyboardMarkup = getBackCancelKeyboard();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        sendMessage.setChatId(message.getChatId());
        sendMessage.setText("Введите наценку за тонну");
        try {
            Order order = orderService.findByTelegramUserAndSubmissionStatus(telegramUser, false);
            orderService.copySelectedProductGroupInfo(order, product.getGroupId());
            orderService.copySelectedProductInfo(order, product);
            orderService.save(order);
            telegramConversation.setCurrentMenu(telegramButtonMenuService.getNextMenu(telegramConversation.getCurrentMenu()));
            telegramConversationService.save(telegramConversation);
        } catch (HibernateException e) {
            LOGGER.error("Error in Telegram Bot: @{} on Hibernate Query", LikadaOrdersBotUtils.LIK_ORDERS_BOT_USERNAME, e);
        }
        return sendMessage;
    }

    /* PRODUCT_COST_MARKUP MENU */
    private SendMessage messageOnProductMarkupMenu(Message message, TelegramUser telegramUser, TelegramConversation telegramConversation){
        SendMessage sendMessageRequest = sendHelpMessage(message.getChatId(), message.getMessageId(), getBackCancelKeyboard());
        try {
            if (message.hasText()) {
                if(message.getText().startsWith(CANCEL)){
                    sendMessageRequest = onCancelCommand(message, getMainMenuKeyboard(), telegramUser, telegramConversation);
                } else if(message.getText().startsWith(BACK)){
                    sendMessageRequest = onBackCommand(message, getProductCategoriesKeyboard((long)0), telegramUser, telegramConversation);
                } else {
                    double count = Double.parseDouble(message.getText());
                    if(count>0){
                        sendMessageRequest = onProductMarkupMenuSelection(message, count, telegramUser, telegramConversation);
                    }
                }
            }
        } catch (NumberFormatException e){
            LOGGER.error("Error in Telegram Bot: @{} on parsing string to double in product count", LikadaOrdersBotUtils.LIK_ORDERS_BOT_USERNAME, e);
        }
        return sendMessageRequest;
    }
    private SendMessage onProductMarkupMenuSelection(Message message, double productMarkup, TelegramUser telegramUser, TelegramConversation telegramConversation){
        LOGGER.info("------- Наценка: {} ---------", productMarkup);
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        ReplyKeyboardMarkup replyKeyboardMarkup = getBackCancelKeyboard();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        sendMessage.setChatId(message.getChatId());
        sendMessage.setText("Введите цену доставки за тонну");
        try {
            Order order = orderService.findByTelegramUserAndSubmissionStatus(telegramUser, false);
            order.setProductPricePlan(order.getProductPrice()+productMarkup);
            orderService.save(order);
            telegramConversation.setCurrentMenu(telegramButtonMenuService.getNextMenu(telegramConversation.getCurrentMenu()));
            telegramConversationService.save(telegramConversation);
        } catch (HibernateException e) {
            LOGGER.error("Error in Telegram Bot: @{} on Hibernate Query", LikadaOrdersBotUtils.LIK_ORDERS_BOT_USERNAME, e);
        }
        return sendMessage;
    }

    /* TRANSPORTATION_COST_MARKUP MENU */
    private SendMessage messageOnTransportationPriceMenu(Message message, TelegramUser telegramUser, TelegramConversation telegramConversation){
        SendMessage sendMessageRequest = sendHelpMessage(message.getChatId(), message.getMessageId(), getBackCancelKeyboard());
        try {
            if (message.hasText()) {
                if(message.getText().startsWith(CANCEL)){
                    sendMessageRequest = onCancelCommand(message, getMainMenuKeyboard(), telegramUser, telegramConversation);
                } else if(message.getText().startsWith(BACK)){
                    sendMessageRequest = onBackCommand(message, getBackCancelKeyboard(), telegramUser, telegramConversation);
                } else {
                    double count = Double.parseDouble(message.getText());
                    if(count>0){
                        sendMessageRequest = onTransportationPriceMenuSelection(message, count, telegramUser, telegramConversation);
                    }
                }
            }
        } catch (NumberFormatException e){
            LOGGER.error("Error in Telegram Bot: @{} on parsing string to double in product count", LikadaOrdersBotUtils.LIK_ORDERS_BOT_USERNAME, e);
        }
        return sendMessageRequest;
    }
    private SendMessage onTransportationPriceMenuSelection(Message message, double transportationPrice, TelegramUser telegramUser, TelegramConversation telegramConversation){
        LOGGER.info("------- Transportation Price: {} ---------", transportationPrice);
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId());
        sendMessage.setText("Введите или выберите пункт загрузки");
        try {
            Order order = orderService.findByTelegramUserAndSubmissionStatus(telegramUser, false);
            order.setTransportPricePlan(transportationPrice);
            orderService.save(order);
            telegramConversation.setCurrentMenu(telegramButtonMenuService.getNextMenu(telegramConversation.getCurrentMenu()));
            telegramConversationService.save(telegramConversation);
            ReplyKeyboardMarkup replyKeyboardMarkup = getShipmentBasisKeyboard(order);
            sendMessage.setReplyMarkup(replyKeyboardMarkup);
        } catch (HibernateException e) {
            LOGGER.error("Error in Telegram Bot: @{} on Hibernate Query", LikadaOrdersBotUtils.LIK_ORDERS_BOT_USERNAME, e);
        }
        return sendMessage;
    }
    private ReplyKeyboardMarkup getShipmentBasisKeyboard(Order order){
        LOGGER.info("Getting ShipmentBasis of Product {} ", order.getProduct());
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboad(true);
        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow firstRow = new KeyboardRow();
        firstRow.add(BACK);
        firstRow.add(CANCEL);
        keyboard.add(firstRow);

        if(order.getProduct().getShipmentBaseId()!=null) {
            KeyboardRow row = new KeyboardRow();
            row.add(order.getProduct().getShipmentBaseId().getNameShort());
            keyboard.add(row);
        }
        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }

    /* SHIPMENT_BASIS MENU */
    private SendMessage messageOnShipmentBasisMenu(Message message, TelegramUser telegramUser, TelegramConversation telegramConversation) {
        SendMessage sendMessageRequest = sendHelpMessage(message.getChatId(), message.getMessageId(), getMainMenuKeyboard());
        if (message.hasText()) {
            if(message.getText().startsWith(CANCEL)){
                sendMessageRequest = onCancelCommand(message, getMainMenuKeyboard(), telegramUser, telegramConversation);
            } else if(message.getText().startsWith(BACK)){
                sendMessageRequest = onBackCommand(message, getProductCategoriesKeyboard((long)0), telegramUser, telegramConversation);
            } else {
                sendMessageRequest = onShipmentBasisSelection(message, telegramUser, telegramConversation);
            }
        }
        return sendMessageRequest;
    }
    private SendMessage onShipmentBasisSelection(Message message, TelegramUser telegramUser, TelegramConversation telegramConversation) {
        LOGGER.info("------- Пункт загрузки ---------");
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        ReplyKeyboardMarkup replyKeyboardMarkup = getProductCountKeyboard();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        sendMessage.setChatId(message.getChatId());
        sendMessage.setText("Выберите или введите количество в тоннах (пример: 25, 50, 75, 100.5)");
        try {
            Order order = orderService.findByTelegramUserAndSubmissionStatus(telegramUser, false);
            List<ShipmentBasis> shipmentBasis = shipmentBasisService.findAllShipmentBasisByNameShort(message.getText());
            if(shipmentBasis!=null && !shipmentBasis.isEmpty()){
                orderService.copyShipmentBasisInfo(order, shipmentBasis.get(0));
            } else {
                order.setShipmentBaseNameShort(message.getText());
            }
            orderService.save(order);
            telegramConversation.setCurrentMenu(telegramButtonMenuService.getNextMenu(telegramConversation.getCurrentMenu()));
            telegramConversationService.save(telegramConversation);
        } catch (HibernateException e) {
            LOGGER.error("Error in Telegram Bot: @{} on Hibernate Query", LikadaOrdersBotUtils.LIK_ORDERS_BOT_USERNAME, e);
        }
        return sendMessage;
    }
    private ReplyKeyboardMarkup getProductCountKeyboard() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboad(true);
        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow firstRow = new KeyboardRow();
        firstRow.add(BACK);
        firstRow.add(CANCEL);
        keyboard.add(firstRow);

        KeyboardRow row = new KeyboardRow();
        row.add("25");
        row.add("50");
        row.add("75");
        keyboard.add(row);

        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }

    /* PRODUCT_COUNT MENU */
    private SendMessage messageOnProductCountMenu(Message message, TelegramUser telegramUser, TelegramConversation telegramConversation) {
        SendMessage sendMessageRequest = sendHelpMessage(message.getChatId(), message.getMessageId(), getProductCountKeyboard());
        try {
            if (message.hasText()) {
                if(message.getText().startsWith(CANCEL)){
                    sendMessageRequest = onCancelCommand(message, getMainMenuKeyboard(), telegramUser, telegramConversation);
                } else if(message.getText().startsWith(BACK)){
                    Order order = orderService.findByTelegramUserAndSubmissionStatus(telegramUser, false);
                    sendMessageRequest = onBackCommand(message, getShipmentBasisKeyboard(order), telegramUser, telegramConversation);
                } else {
                    double count = Double.parseDouble(message.getText());
                    if(count>0){
                        sendMessageRequest = onProductCountSelection(message, count, telegramUser, telegramConversation);
                    }
                }
            }
        } catch (NumberFormatException e){
            LOGGER.error("Error in Telegram Bot: @{} on parsing string to double in product count", LikadaOrdersBotUtils.LIK_ORDERS_BOT_USERNAME, e);
        } catch (HibernateException e) {
            LOGGER.error("Error in Telegram Bot: @{} on Hibernate Query", LikadaOrdersBotUtils.LIK_ORDERS_BOT_USERNAME, e);
        }
        return sendMessageRequest;
    }
    private SendMessage onProductCountSelection(Message message, double count, TelegramUser telegramUser, TelegramConversation telegramConversation) {
        LOGGER.info("------- Количество в тоннах: {} ---------", count);
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        ReplyKeyboardMarkup replyKeyboardMarkup = getUnloadPointKeyboard(telegramUser);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        sendMessage.setChatId(message.getChatId());
        sendMessage.setText("Выберите или введите пункт выгрузки");
        try {
            Order order = orderService.findByTelegramUserAndSubmissionStatus(telegramUser, false);
            order.setProductCountPlan(count);
            orderService.save(order);
            telegramConversation.setCurrentMenu(telegramButtonMenuService.getNextMenu(telegramConversation.getCurrentMenu()));
            telegramConversationService.save(telegramConversation);
        } catch (HibernateException e) {
            LOGGER.error("Error in Telegram Bot: @{} on Hibernate Query", LikadaOrdersBotUtils.LIK_ORDERS_BOT_USERNAME, e);
        }
        return sendMessage;
    }
    private ReplyKeyboardMarkup getUnloadPointKeyboard(TelegramUser telegramUser){
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboad(true);
        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow firstRow = new KeyboardRow();
        firstRow.add(BACK);
        firstRow.add(CANCEL);
        keyboard.add(firstRow);

        try {
            Order order = orderService.findByTelegramUserAndSubmissionStatus(telegramUser, false);
            if(order.getCustomer()!=null){
                addDistinctUnloadPointsOfOrdersToKeyboard(orderService.findAllOrdersByCustomer(order.getCustomer()), keyboard);
            }
        } catch (HibernateException e) {
            LOGGER.error("Error in Telegram Bot: @{} on Hibernate Query", LikadaOrdersBotUtils.LIK_ORDERS_BOT_USERNAME, e);
        }
        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }
    private void addDistinctUnloadPointsOfOrdersToKeyboard(List<Order> orders, List<KeyboardRow> keyboard){
        if(orders!=null && !orders.isEmpty()){
            Set<String> unloadPoints = new HashSet<String>();
            for (Order order: orders){
                if(order.getUnloadPointOtherName()!=null && !order.getUnloadPointOtherName().isEmpty() && !"".equals(order.getUnloadPointOtherName())){
                    if(unloadPoints.add(order.getUnloadPointOtherName())){
                        KeyboardRow orderUnloadPoint = new KeyboardRow();
                        orderUnloadPoint.add(order.getUnloadPointOtherName());
                        keyboard.add(orderUnloadPoint);
                    }
                }
            }
        }
    }

    /* UNLOAD_POINT MENU */
    private SendMessage messageOnUnloadPointMenu(Message message, TelegramUser telegramUser, TelegramConversation telegramConversation) {
        SendMessage sendMessageRequest = sendHelpMessage(message.getChatId(), message.getMessageId(), getUnloadPointKeyboard(telegramUser));
        if (message.hasText()) {
            if(message.getText().startsWith(CANCEL)){
                sendMessageRequest = onCancelCommand(message, getMainMenuKeyboard(), telegramUser, telegramConversation);
            } else if(message.getText().startsWith(BACK)){
                sendMessageRequest = onBackCommand(message, getProductCountKeyboard(), telegramUser, telegramConversation);
            } else {
                sendMessageRequest = onUnloadPointSelection(message, telegramUser, telegramConversation);
            }
        }
        return sendMessageRequest;
    }
    private SendMessage onUnloadPointSelection(Message message, TelegramUser telegramUser, TelegramConversation telegramConversation) {
        LOGGER.info("------- Пункт выгрузки: {} ---------", message.getText());
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        ReplyKeyboardMarkup replyKeyboardMarkup = getUnLoadContactKeyboard(telegramUser);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        sendMessage.setChatId(message.getChatId());
        sendMessage.setText("Введите или выберите контакты по выгрузки");
        try {
            Order order = orderService.findByTelegramUserAndSubmissionStatus(telegramUser, false);
            order.setUnloadPointOtherName(message.getText());
            orderService.save(order);
            telegramConversation.setCurrentMenu(telegramButtonMenuService.getNextMenu(telegramConversation.getCurrentMenu()));
            telegramConversationService.save(telegramConversation);
        } catch (HibernateException e) {
            LOGGER.error("Error in Telegram Bot: @{} on Hibernate Query", LikadaOrdersBotUtils.LIK_ORDERS_BOT_USERNAME, e);
        }
        return sendMessage;
    }
    private ReplyKeyboardMarkup getUnLoadContactKeyboard(TelegramUser telegramUser){
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboad(true);
        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow firstRow = new KeyboardRow();
        firstRow.add(BACK);
        firstRow.add(CANCEL);
        keyboard.add(firstRow);

        try {
            Order order = orderService.findByTelegramUserAndSubmissionStatus(telegramUser, false);
            if(order.getCustomer()!=null){
                addDistinctUnloadContactsOfOrdersToKeyboard(orderService.findAllOrdersByCustomer(order.getCustomer()), keyboard);
            }
        } catch (HibernateException e) {
            LOGGER.error("Error in Telegram Bot: @{} on Hibernate Query", LikadaOrdersBotUtils.LIK_ORDERS_BOT_USERNAME, e);
        }
        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }
    private void addDistinctUnloadContactsOfOrdersToKeyboard(List<Order> orders, List<KeyboardRow> keyboard){
        if(orders!=null && !orders.isEmpty()){
            Set<String> unloadPoints = new HashSet<String>();
            for (Order order: orders){
                if(order.getUnloadContacts()!=null && !order.getUnloadContacts().isEmpty() && !"".equals(order.getUnloadContacts())){
                    if(unloadPoints.add(order.getUnloadContacts())){
                        KeyboardRow orderUnloadPoint = new KeyboardRow();
                        orderUnloadPoint.add(order.getUnloadContacts());
                        keyboard.add(orderUnloadPoint);
                    }
                }
            }
        }
    }

    /* UNLOAD_CONTACT MENU */
    private SendMessage messageOnUnloadContactMenu(Message message, TelegramUser telegramUser, TelegramConversation telegramConversation) {
        SendMessage sendMessageRequest = sendHelpMessage(message.getChatId(), message.getMessageId(), getUnLoadContactKeyboard(telegramUser));
        if (message.hasText()) {
            if(message.getText().startsWith(CANCEL)){
                sendMessageRequest = onCancelCommand(message, getMainMenuKeyboard(), telegramUser, telegramConversation);
            } else if(message.getText().startsWith(BACK)){
                sendMessageRequest = onBackCommand(message, getUnloadPointKeyboard(telegramUser), telegramUser, telegramConversation);
            } else {
                sendMessageRequest = onUnloadContactSelection(message, telegramUser, telegramConversation);
            }
        }
        return sendMessageRequest;
    }
    private SendMessage onUnloadContactSelection(Message message, TelegramUser telegramUser, TelegramConversation telegramConversation) {
        LOGGER.info("------- Контакты выгрузки: {} ---------", message.getText());
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        ReplyKeyboardMarkup replyKeyboardMarkup = getUnLoadDateKeyboard();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        sendMessage.setChatId(message.getChatId());
        sendMessage.setText("Выберите или укажите дату выгрузки (пример: 14.08.17)");
        try {
            Order order = orderService.findByTelegramUserAndSubmissionStatus(telegramUser, false);
            order.setUnloadContacts(message.getText());
            orderService.save(order);
            telegramConversation.setCurrentMenu(telegramButtonMenuService.getNextMenu(telegramConversation.getCurrentMenu()));
            telegramConversationService.save(telegramConversation);
        } catch (HibernateException e) {
            LOGGER.error("Error in Telegram Bot: @{} on Hibernate Query", LikadaOrdersBotUtils.LIK_ORDERS_BOT_USERNAME, e);
        }
        return sendMessage;
    }
    private ReplyKeyboardMarkup getUnLoadDateKeyboard() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboad(true);
        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow firstRow = new KeyboardRow();
        firstRow.add(BACK);
        firstRow.add(CANCEL);
        keyboard.add(firstRow);

        Date todayDate = new Date();
        KeyboardRow todayButton = new KeyboardRow();
        todayButton.add("Сегодня (" + new SimpleDateFormat("dd.MM.yy").format(todayDate) + ")");
        Calendar calendar = Calendar.getInstance();
        KeyboardRow tomorrowButton = new KeyboardRow();
        calendar.setTime(todayDate);
        calendar.add(Calendar.DATE, 1);
        tomorrowButton.add("Завтра (" + new SimpleDateFormat("dd.MM.yy").format(calendar.getTime()) + ")");
        KeyboardRow dayAfterTomorrowButton = new KeyboardRow();
        calendar.add(Calendar.DATE, 1);
        dayAfterTomorrowButton.add("Послезавтра (" + new SimpleDateFormat("dd.MM.yy").format(calendar.getTime()) + ")");
        keyboard.add(tomorrowButton);
        keyboard.add(dayAfterTomorrowButton);
        keyboard.add(todayButton);

        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }

    /* UNLOAD_DATE_PLAN */
    private SendMessage messageOnUnLoadDateMenu(Message message, TelegramUser telegramUser, TelegramConversation telegramConversation) {
        SendMessage sendMessageRequest = sendHelpMessage(message.getChatId(), message.getMessageId(), getUnLoadDateKeyboard());
        if (message.hasText()) {
            if(message.getText().startsWith(CANCEL)){
                sendMessageRequest = onCancelCommand(message, getMainMenuKeyboard(), telegramUser, telegramConversation);
            } else if(message.getText().startsWith(BACK)){
                sendMessageRequest = onBackCommand(message, getUnLoadContactKeyboard(telegramUser), telegramUser, telegramConversation);
            } else {
                String date = null;
                Matcher regexDateMatcher;
                if((regexDateMatcher = regexDate.matcher(message.getText())).find()){
                    date = regexDateMatcher.group(1);
                }
                try {
                    sendMessageRequest = onUnLoadDateSelection(message, formatter.parse(date == null || date.isEmpty() ? message.getText() : date), telegramUser, telegramConversation);
                } catch (ParseException e) {
                    LOGGER.error("Error in Telegram Bot: @{} on parsing the date string", LikadaOrdersBotUtils.LIK_ORDERS_BOT_USERNAME, e);
                }
            }
        }
        return sendMessageRequest;
    }
    private SendMessage onUnLoadDateSelection(Message message, Date unLoadDatePlan, TelegramUser telegramUser, TelegramConversation telegramConversation) {
        LOGGER.info("------- Дата выгрузки: {} ---------", unLoadDatePlan);
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        ReplyKeyboardMarkup replyKeyboardMarkup = getOrderPaymentTypeKeyboard();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        sendMessage.setChatId(message.getChatId());
        sendMessage.setText("Выберите тип оплаты");
        try {
            Order order = orderService.findByTelegramUserAndSubmissionStatus(telegramUser, false);
            order.setUnloadDatePlan(unLoadDatePlan);
            orderService.save(order);
            telegramConversation.setCurrentMenu(telegramButtonMenuService.getNextMenu(telegramConversation.getCurrentMenu()));
            telegramConversationService.save(telegramConversation);
        } catch (HibernateException e) {
            LOGGER.error("Error in Telegram Bot: @{} on Hibernate Query", LikadaOrdersBotUtils.LIK_ORDERS_BOT_USERNAME, e);
        }
        return sendMessage;
    }
    private ReplyKeyboardMarkup getOrderPaymentTypeKeyboard(){
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboad(true);
        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow firstRow = new KeyboardRow();
        firstRow.add(BACK);
        firstRow.add(CANCEL);
        keyboard.add(firstRow);

        for (OrderPaymentType orderPaymentType: orderPaymentTypeService.getAllOrderPaymentTypes()) {
            KeyboardRow row = new KeyboardRow();
            row.add(orderPaymentType.getName());
            keyboard.add(row);
        }
        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }

    /* ORDER_PAYMENT_TYPE MENU */
    private SendMessage messageOnOrderPaymentTypeMenu(Message message, TelegramUser telegramUser, TelegramConversation telegramConversation) {
        SendMessage sendMessageRequest = sendHelpMessage(message.getChatId(), message.getMessageId(), getOrderPaymentTypeKeyboard());
        if (message.hasText()) {
            if(message.getText().startsWith(CANCEL)){
                sendMessageRequest = onCancelCommand(message, getMainMenuKeyboard(), telegramUser, telegramConversation);
            } else if(message.getText().startsWith(BACK)){
                sendMessageRequest = onBackCommand(message, getUnLoadDateKeyboard(), telegramUser, telegramConversation);
            } else {
                OrderPaymentType orderPaymentType = orderPaymentTypeService.getOrderTypeByName(message.getText());
                if(orderPaymentType!=null){
                    sendMessageRequest = onOrderPaymentSelection(message, orderPaymentType, telegramUser, telegramConversation);
                }
            }
        }
        return sendMessageRequest;
    }
    private SendMessage onOrderPaymentSelection(Message message, OrderPaymentType orderPaymentType, TelegramUser telegramUser, TelegramConversation telegramConversation) {
        LOGGER.info("------- Тип оплаты выгрузки: {} ---------", message.getText());
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        ReplyKeyboardMarkup replyKeyboardMarkup = getUnloadDescriptionKeyboard();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        sendMessage.setChatId(message.getChatId());
        sendMessage.setText("Введите примечание или нажмите кнопку " + FORWARD);
        try {
            Order order = orderService.findByTelegramUserAndSubmissionStatus(telegramUser, false);
            orderService.copySelectedOrderPaymentTypeInfo(order, orderPaymentType);
            orderService.save(order);
            telegramConversation.setCurrentMenu(telegramButtonMenuService.getNextMenu(telegramConversation.getCurrentMenu()));
            telegramConversationService.save(telegramConversation);
        } catch (HibernateException e) {
            LOGGER.error("Error in Telegram Bot: @{} on Hibernate Query", LikadaOrdersBotUtils.LIK_ORDERS_BOT_USERNAME, e);
        }
        return sendMessage;
    }
    private ReplyKeyboardMarkup getUnloadDescriptionKeyboard(){
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboad(true);
        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow firstRow = new KeyboardRow();
        firstRow.add(BACK);
        firstRow.add(CANCEL);
        firstRow.add(FORWARD);
        keyboard.add(firstRow);

        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }

    /* UNLOAD_DESCRIPTION MENU */
    private SendMessage messageOnUnloadDescriptionMenu(Message message, TelegramUser telegramUser, TelegramConversation telegramConversation){
        SendMessage sendMessageRequest = sendHelpMessage(message.getChatId(), message.getMessageId(), getMainMenuKeyboard());
        if (message.hasText()) {
            if(message.getText().startsWith(CANCEL)){
                sendMessageRequest = onCancelCommand(message, getMainMenuKeyboard(), telegramUser, telegramConversation);
            } else if(message.getText().startsWith(BACK)){
                sendMessageRequest = onBackCommand(message, getUnLoadDateKeyboard(), telegramUser, telegramConversation);
            } else if(message.getText().startsWith(FORWARD)){
                sendMessageRequest = onUnloadDescriptionSelection(message, telegramUser, telegramConversation, true);
            } else {
                sendMessageRequest = onUnloadDescriptionSelection(message, telegramUser, telegramConversation, false);
            }
        }
        return sendMessageRequest;
    }
    private SendMessage onUnloadDescriptionSelection(Message message, TelegramUser telegramUser, TelegramConversation telegramConversation, boolean skipUnloadDescription) {
        LOGGER.info("------- Примечание к выгрузке: {} ---------", message.getText());
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        ReplyKeyboardMarkup replyKeyboardMarkup = getMainMenuKeyboard();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        sendMessage.setChatId(message.getChatId());
        try {
            Order order = orderService.findByTelegramUserAndSubmissionStatus(telegramUser, false);
            if(!skipUnloadDescription)
                order.setUnloadDescription(message.getText());
            order.setTelegramReceivedAlready(true);
            orderService.copySelectedOrderStatusTypeInfo(order, orderStatusTypeService.findByType(OrderStatusTypeEnum.NEW.getType()));
            orderService.save(order);
            String resultString = "*Ваша заявка отправлена на рассмотрение*\n\n" +
                    "*Номер заявки:* " + order.getId() + "\n" +
                    "*Организация:* " + order.getCompany().getNameWork() + "\n" +
                    "*Клиент:* " + order.getContractor().getNameShort() + " (" + order.getCustomer().getTitle() + ")" + "\n" +
                    "*Продукт:* " + order.getProduct().getCode() + " - " + currencyFormatter.format(order.getProductPrice()) + " \u20BD\n" +
                    "*Количество:* " + (order.getProductCountPlan() == (long)order.getProductCountPlan().doubleValue() ? String.format("%d", (long)order.getProductCountPlan().doubleValue()) : String.format("%s", order.getProductCountPlan())) + " тн.\n" +
                    "*Пункт выгрузки:* " + order.getShipmentBaseNameShort() + "\n" +
                    "*Контакты по выгрузки:* " + order.getUnloadContacts() + "\n" +
                    "*Дата выгрузки:* " + DateFormat.getDateInstance(DateFormat.SHORT, locale).format(order.getUnloadDatePlan()) + "\n" +
                    "*Тип оплаты:* " + order.getOrderPaymentTypeName() + "\n" +
                    "*Наценка:* " + currencyFormatter.format(order.getProductPricePlan()-order.getProductCostPlan()) + " \u20BD/тн.\n" +
                    "*ТЗР:* " + currencyFormatter.format(order.getTransportPricePlan()) + " \u20BD/тн.\n" +
                    "*Цена продажная:* " + currencyFormatter.format(order.getProductPricePlan()) + " \u20BD/тн.\n\n" +
                    "*Итого:* " + currencyFormatter.format(order.getProductCountPlan()*order.getProductPricePlan()) + " \u20BD\n" +
                    "*Итого с доставкой:* " + currencyFormatter.format(order.getProductCountPlan()*(order.getProductPricePlan() + order.getTransportPricePlan())) + " \u20BD\n" +
                    "*Дебиторка:* -" + "\n";
            sendMessage.setText(resultString);
            telegramConversation.setCurrentMenu(telegramButtonMenuService.getNextMenu(telegramConversation.getCurrentMenu()));
            telegramConversationService.save(telegramConversation);
        } catch (HibernateException e) {
            LOGGER.error("Error in Telegram Bot: @{} on Hibernate Query", LikadaOrdersBotUtils.LIK_ORDERS_BOT_USERNAME, e);
        }
        return sendMessage;
    }

    private ReplyKeyboardMarkup getBackCancelKeyboard(){
        LOGGER.info("Getting Back Cancel Keyboard");
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboad(true);
        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow firstRow = new KeyboardRow();
        firstRow.add(BACK);
        firstRow.add(CANCEL);
        keyboard.add(firstRow);
        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }
}
