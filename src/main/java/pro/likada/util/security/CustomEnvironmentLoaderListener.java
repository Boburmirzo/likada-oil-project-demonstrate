package pro.likada.util.security;

import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.authc.credential.PasswordMatcher;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.web.env.DefaultWebEnvironment;
import org.apache.shiro.web.env.EnvironmentLoaderListener;
import org.apache.shiro.web.env.WebEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.ApiContext;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.logging.BotLogger;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import pro.likada.bot.orders.LikadaOrdersBotLongPolling;
import pro.likada.bot.orders.commands.LikadaOrdersStartCommand;
import pro.likada.bot.orders.commands.LikadaOrdersStopCommand;
import pro.likada.bot.products.commands.HelloCommand;
import pro.likada.bot.products.commands.StartCommand;
import pro.likada.bot.products.commands.StopCommand;
import pro.likada.bot.products.updateHandlers.CommandsHandler;

import javax.inject.Inject;
import javax.servlet.ServletContext;

/**
 * Created by Yusupov on 2/7/2017.
 */
public class CustomEnvironmentLoaderListener extends EnvironmentLoaderListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomEnvironmentLoaderListener.class);

    @Inject
    private JpaRealm jpaRealm;
    @Inject
    private LikadaOrdersBotLongPolling likadaOrdersBotLongPolling;
    @Inject
    private LikadaOrdersStartCommand likadaOrdersStartCommand;
    @Inject
    private LikadaOrdersStopCommand likadaOrdersStopCommand;

    @Inject
    private CommandsHandler likadaProductsBotPolling;
    @Inject
    private HelloCommand likadaProductsHelloCommand;
    @Inject
    private StartCommand likadaProductsStartCommand;
    @Inject
    private StopCommand likadaProductsStopCommand;

    private DefaultBotSession ordersBotSession;
    private DefaultBotSession productsBotSession;

    @Override
    protected WebEnvironment createEnvironment(ServletContext pServletContext) {
        WebEnvironment environment = super.createEnvironment(pServletContext);
        RealmSecurityManager rsm = (RealmSecurityManager) environment.getSecurityManager();
        PasswordService passwordService = new DefaultPasswordService();
        PasswordMatcher passwordMatcher = new PasswordMatcher();
        passwordMatcher.setPasswordService(passwordService);
        jpaRealm.setCredentialsMatcher(passwordMatcher);
        rsm.setRealm(jpaRealm);
        ((DefaultWebEnvironment) environment).setSecurityManager(rsm);
        /* Register Telegram Bots */
       // registerBots();
        return environment;
    }

    @Override
    public void destroyEnvironment(ServletContext servletContext) {
        super.destroyEnvironment(servletContext);
        productsBotSession.stop();
        ordersBotSession.stop();
        LOGGER.info("Bots are stopped");
    }

    private void registerBots(){
        LOGGER.debug("Telegram bots initialisations has been started");
        ApiContextInitializer.init();
        registerLikadaOrdersBot();
        registerLikadaProductsBot();
    }

    private void registerLikadaProductsBot(){
        LOGGER.debug("Initializing Likada Products Bot");
        try {
            likadaProductsBotPolling.clearWebhook();
            likadaProductsBotPolling.register(likadaProductsHelloCommand);
            likadaProductsBotPolling.register(likadaProductsStartCommand);
            likadaProductsBotPolling.register(likadaProductsStopCommand);
            productsBotSession = ApiContext.getInstance(DefaultBotSession.class);
            productsBotSession.setToken(likadaProductsBotPolling.getBotToken());
            productsBotSession.setOptions(likadaProductsBotPolling.getOptions());
            productsBotSession.setCallback(likadaProductsBotPolling);
            productsBotSession.start();
        } catch (TelegramApiException e) {
            BotLogger.error("Initialize", e);
        }
    }

    private void registerLikadaOrdersBot(){
        LOGGER.debug("Initializing Likada Orders Bot");
        try {
            likadaOrdersBotLongPolling.clearWebhook();
            likadaOrdersBotLongPolling.register(likadaOrdersStartCommand);
            likadaOrdersBotLongPolling.register(likadaOrdersStopCommand);
            ordersBotSession = ApiContext.getInstance(DefaultBotSession.class);
            ordersBotSession.setToken(likadaOrdersBotLongPolling.getBotToken());
            ordersBotSession.setOptions(likadaOrdersBotLongPolling.getOptions());
            ordersBotSession.setCallback(likadaOrdersBotLongPolling);
            ordersBotSession.start();
//            TelegramBotsApi likadaOrdersBot = new TelegramBotsApi(LikadaOrdersBotUtils.PATH_TO_CERTIFICATE_STORE, LikadaOrdersBotUtils.CERTIFICATE_STORE_PASSWORD, LikadaOrdersBotUtils.EXTERNAL_WEBHOOK_URL, LikadaOrdersBotUtils.INTERNAL_WEBHOOK_URL, LikadaOrdersBotUtils.PATH_TO_CERTIFICATE_PUBLIC_KEY);
//            likadaOrdersBot.registerBot(new LikadaOrdersBotWebHook());
        } catch (TelegramApiException e) {
            BotLogger.error("Initialize", e);
        }
    }
}