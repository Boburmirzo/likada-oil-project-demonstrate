package pro.likada.bot.orders.utils;

/**
 * Created by Yusupov on 3/31/2017.
 */
public class LikadaOrdersBotUtils {

    public static final Integer LIK_ORDERS_BOT_ID = 370148039;
    public static final String LIK_ORDERS_BOT_USERNAME = "lik_orders_bot";
    public static final String LIK_ORDERS_BOT_TOKEN = "370148039:AAGH_y756i2gvK4OHz8m4zPc4w-SuOd4Dn0";
    public static final String PATH_TO_CERTIFICATE_STORE = "./TEMP/localhost.jks"; //self-signed and non-self-signed.

    public static final String CERTIFICATE_STORE_PASSWORD = "2711991z"; //password for your certificate-store
    public static final String PATH_TO_CERTIFICATE_PUBLIC_KEY = "./TEMP/localhost.pem"; //only for self-signed webhooks
    private static final int PORT = 8443;

    public static final String EXTERNAL_WEBHOOK_URL = "https://erp.likada.pro:" + PORT; // https://(xyz.)externaldomain.tld
    public static final String INTERNAL_WEBHOOK_URL = "https://localhost.erp.likada.pro:" + PORT; // https://(xyz.)localip/domain(.tld) the same if no forwarding in the server

    public static final Integer MAX_NUMBER_OF_RESULTS = 50;
}
