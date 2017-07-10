package pro.likada.bot.products;

/**
 * Created by bumur on 17.03.2017.
 */
public class BotBuildVariables {

    public static final Boolean debug = true;
    public static final Boolean useWebHook = false;
    public static final int PORT = 8443;
    public static final String EXTERNALWEBHOOKURL = "https://PetrolCRMSystem:"+PORT;
    public static final String INTERNALWEBHOOKURL = "https://localhost:"+PORT; // https://(xyz.)localip/domain(.tld)
    public static final String pathToCertificateStore = "E:\\LikadaProjects\\wildfly-10.1.0.Final\\wildfly-10.1.0.Final\\standalone\\configuration\\my.jks";
    public static final String pathToCertificatePublicKey = "E:\\LikadaProjects\\wildfly-10.1.0.Final\\wildfly-10.1.0.Final\\standalone\\configuration\\likada_products_prices.pem"; //only for self-signed webhooks
    public static final String certificateStorePassword = "secret"; //password for your certificate-store
}
