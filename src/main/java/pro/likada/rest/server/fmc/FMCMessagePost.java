package pro.likada.rest.server.fmc;

/**
 * Created by bumur on 27.04.2017.
 */

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.likada.dao.daoImpl.RestAuthenticationDAOImpl;
import pro.likada.model.RestAuthentication;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class FMCMessagePost {
    private static final String GOOGLE_API="https://fcm.googleapis.com/fcm/send";
    private static final String SERVER_KEY="key=AAAA-JtNPaw:APA91bGrS-yqjS7VOJY7Z9UlquAwQZYbVUW6aQicoOPXZs5ugNIdOzNVvdb8x5SP4t0hqMAQnpVEwUW5NCEn1vMWwqAZ01NQTPi2lXYjuqfTals75hjku38Zo7xk1P4gcZR559tgRSWp";
    private static RestAuthenticationDAOImpl restAuthenticationDAO= new RestAuthenticationDAOImpl();
    private static final Logger LOGGER = LoggerFactory.getLogger(FMCMessagePost.class);

    public void doPost(Long terminalId) {
        RestAuthentication restAuthentication = restAuthenticationDAO.findByRestUserId(terminalId);
        String gcmToken="";
        if(restAuthentication.getGcmToken().isEmpty())
            return;
        gcmToken=restAuthentication.getGcmToken();

        // Generating a JSONObject for the content of the message
        JSONObject message = new JSONObject();
        message.put("message", "OK");
        JSONObject protocol = new JSONObject();
        protocol.put("to", gcmToken);
        protocol.put("data", message);

        // Send Protocol
        try {
            HttpClient httpClient = HttpClientBuilder.create().build();

            HttpPost request = new HttpPost(GOOGLE_API);
            request.addHeader("content-type", "application/json");
            request.addHeader("charset", "UTF-8");
            request.addHeader("Authorization", SERVER_KEY);

            StringEntity params = new StringEntity(protocol.toString());
            request.setEntity(params);
            System.out.println(params);

            HttpResponse response = httpClient.execute(request);
            System.out.println("Response Code : "
                    + response.getStatusLine().getStatusCode());

            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));

            StringBuffer result = new StringBuffer();
            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
