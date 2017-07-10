package pro.likada.rest.server;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Created by bumur on 03.04.2017.
 */
public class RestDrive extends ResourceConfig {

    public RestDrive(){
        register(new JacksonFeature());
        register(new ApplicationBinder());
        register(MultiPartFeature.class);
        packages("pro.likada.rest.server.drive.RestService");
    }
}
