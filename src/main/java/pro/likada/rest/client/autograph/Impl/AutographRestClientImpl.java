package pro.likada.rest.client.autograph.Impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.likada.rest.client.autograph.AutographRestClient;
import pro.likada.rest.wrapper.autograph.REnumDevicesWrapper;
import pro.likada.rest.wrapper.autograph.ROnlineInfoWrapper;
import pro.likada.rest.wrapper.autograph.RTrackInfoWrapper;
import pro.likada.rest.wrapper.autograph.RTripsWrapper;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Created by abuca on 21.03.17.
 */
@Named
@ApplicationScoped
@AutographRestClientDirect
public class AutographRestClientImpl implements AutographRestClient {
    private Boolean enabled;
    private ClientRequestFilter authFilter;
    private String resource;
    private String securityToken;
    private String username;
    private String password;
    private String schemaID;
    private DateFormat dateFormater;

    private static final Logger LOGGER = LoggerFactory.getLogger(AutographRestClientImpl.class);

    @PostConstruct
    public void init(){
        ResourceBundle settings = ResourceBundle.getBundle("autograph");
        this.enabled = Boolean.valueOf(settings.getString("enabled"));
        this.resource = settings.getString("resource");
        this.username = settings.getString("username");
        this.password = settings.getString("password");
        this.schemaID = settings.getString("schema");
        this.dateFormater = new SimpleDateFormat("yyyyMMdd-HHmm");

        if(this.enabled){
            this.login();
        }
    }

    public void login(){
        Response response = ClientBuilder
                .newClient()
                .target(resource)
                .path("Login")
                .queryParam("UserName",username)
                .queryParam("Password",password)
                .request(MediaType.TEXT_PLAIN_TYPE)
                .get();

        if(response.getStatus() != 200){
            throw new RuntimeException("Login for AutoGRAPH failed with username: "+username+", HTTP error code "+ response.getStatus());
        }
        setSecurityToken(response.readEntity(String.class));
        this.authFilter = new ClientRequestFilter() {
            @Override
            public void filter(ClientRequestContext requestContext) throws IOException {
                requestContext.getHeaders().add("AG-TOKEN",securityToken);
            }
        };
        getClient().register(authFilter);
    }

    @Override
    public Map<String, ROnlineInfoWrapper> getVehiclesInfo(){
        if(this.enabled){
            Response response = getClient()
                    .target(resource)
                    .path("GetOnlineInfoAll")
                    .queryParam("schemaID", getSchemaID())
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .get();

            if(response.getStatus() != 200){
                throw new RuntimeException("Cannot get vehicles information from AutoGRAPH REST API, HTTP error code "+ response.getStatus());
            }
            Map<String, ROnlineInfoWrapper> vehicleInfo = response.readEntity(new GenericType<Map<String, ROnlineInfoWrapper>>() {});
            return vehicleInfo;
        }
        else {
            return new HashMap<>();
        }
    }

    @Override
    public RTrackInfoWrapper getTrack(String vehicleGuid, Date from, Date to){
        return getTracks(vehicleGuid,from,to,-1)[0];
    }

    @Override
    public RTrackInfoWrapper[] getTrackSplittedByDrives(String vehicleGuid, Date from, Date to){
        return getTracks(vehicleGuid,from,to,0);
    }

    private RTrackInfoWrapper[] getTracks(String vehicleGuid, Date from, Date to, Integer split){
        if(this.enabled){
            Response response = getClient()
                    .target(resource)
                    .path("GetTrack")
                    .queryParam("schemaID", getSchemaID())
                    .queryParam("IDs", vehicleGuid)
                    .queryParam("SD", dateFormater.format(from))
                    .queryParam("ED", dateFormater.format(to))
                    .queryParam("tripSplitterIndex",split)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .get();

            if(response.getStatus() != 200){
                throw new RuntimeException("Cannot get vehicles information from AutoGRAPH REST API, HTTP error code "+ response.getStatus());
            }
            Map<String, RTrackInfoWrapper[]> trackInfo = response.readEntity(new GenericType<Map<String, RTrackInfoWrapper[]>>() {});
            return trackInfo.get(vehicleGuid);
        }
        else {
            return new RTrackInfoWrapper[0];
        }
    }

    @Override
    public RTripsWrapper getDrives(String vehicleGuid, Date from, Date to){
        if(this.enabled){
            Response response = getClient()
                    .target(resource)
                    .path("GetTrips")
                    .queryParam("schemaID", getSchemaID())
                    .queryParam("IDs", vehicleGuid)
                    .queryParam("SD", dateFormater.format(from))
                    .queryParam("ED", dateFormater.format(to))
                    .queryParam("tripSplitterIndex",0)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .get();

            if(response.getStatus() != 200){
                throw new RuntimeException("Cannot get vehicles information from AutoGRAPH REST API, HTTP error code "+ response.getStatus());
            }
            Map<String, RTripsWrapper> drivesInfo = response.readEntity(new GenericType<Map<String, RTripsWrapper>>() {});
            return drivesInfo.get(vehicleGuid);
        }
        else {
            return new RTripsWrapper();
        }
    }

    @Override
    public REnumDevicesWrapper getDevicesInfo(){
        if(this.enabled){
            Response response = getClient()
                    .target(resource)
                    .path("EnumDevices")
                    .queryParam("schemaID", getSchemaID())
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .get();
            if(response.getStatus() != 200){
                throw new RuntimeException("Cannot get devices information from AutoGRAPH REST API, HTTP error code "+ response.getStatus());
            }
            REnumDevicesWrapper devicesInfo = response.readEntity(REnumDevicesWrapper.class);
            return devicesInfo;
        }
        else {
            return new REnumDevicesWrapper();
        }
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    public Client getClient() {
        return ClientBuilder.newClient().register(authFilter);
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getSecurityToken() {
        return securityToken;
    }

    public void setSecurityToken(String securityToken) {
        this.securityToken = securityToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSchemaID() {
        return schemaID;
    }

    public void setSchemaID(String schemaID) {
        this.schemaID = schemaID;
    }
}
