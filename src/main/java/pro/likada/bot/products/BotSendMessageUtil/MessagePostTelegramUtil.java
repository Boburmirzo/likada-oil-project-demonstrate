package pro.likada.bot.products.BotSendMessageUtil;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import pro.likada.dao.daoImpl.TelegramUserDAOImpl;
import pro.likada.model.TelegramUser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class MessagePostTelegramUtil {

    private static final String BASEURL = "https://api.telegram.org/bot";
    private static final String PATH = "sendmessage";
    private static final String CHATID_FIELD = "chat_id";
    private static Integer chatId;
    private static final String TEXT_FIELD = "text";
    private static String text;
    private static final String PARSE_MODE = "parse_mode";
    private static final String HTML = "HTML";

    private static TelegramUserDAOImpl telegramUserDAO= new TelegramUserDAOImpl();

    public void MpTelegramUtilSend(String textToSend, String botToken) {
        text=textToSend;
        final List<TelegramUser> telegramUsers=telegramUserDAO.findAll();
        Thread notifyProcess = new Thread(() -> {
            for (TelegramUser telegramUser : telegramUsers) {
                if (telegramUser.isActive()) {
                    chatId = telegramUser.getTelegramUserId();
                    try {
                        String url = BASEURL + botToken + "/" + PATH;
                        HttpClient client = HttpClientBuilder.create().build();
                        HttpPost httppost = new HttpPost(url);

                        httppost.addHeader("Content-type", "application/x-www-form-urlencoded");
                        httppost.addHeader("charset", "UTF-8");

                        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                        nameValuePairs.add(new BasicNameValuePair(CHATID_FIELD, chatId + ""));

                        nameValuePairs.add(new BasicNameValuePair(TEXT_FIELD, text));
                        nameValuePairs.add(new BasicNameValuePair(PARSE_MODE, HTML));

                        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));

                        HttpResponse response = client.execute(httppost);
                        System.out.println("Response Code : "
                                + response.getStatusLine().getStatusCode());

                        BufferedReader rd = new BufferedReader(
                                new InputStreamReader(response.getEntity().getContent()));

                        StringBuffer result = new StringBuffer();
                        String line = "";
                        while ((line = rd.readLine()) != null) {
                            result.append(line);
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        notifyProcess.start();
    }

}