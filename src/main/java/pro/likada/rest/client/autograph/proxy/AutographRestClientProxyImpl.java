package pro.likada.rest.client.autograph.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.likada.rest.client.autograph.AutographRestClient;
import pro.likada.rest.client.autograph.Impl.AutographRestClientImpl;
import pro.likada.rest.wrapper.autograph.REnumDevicesWrapper;
import pro.likada.rest.wrapper.autograph.ROnlineInfoWrapper;
import pro.likada.rest.wrapper.autograph.RTrackInfoWrapper;
import pro.likada.rest.wrapper.autograph.RTripsWrapper;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.util.*;

/**
 * Created by abuca on 24.03.17.
 */
@Named
@ApplicationScoped
@AutographRestClientProxy
public class AutographRestClientProxyImpl implements AutographRestClient {
    private Boolean enabled;
    private Integer delay;

    private AutographRestClientImpl autographRestClientForThread;
    private AutographRestClientImpl autographRestClient;

    private Map<String, ROnlineInfoWrapper> vehiclesInfo;

    private Thread requesterThread;

    private static final Logger LOGGER = LoggerFactory.getLogger(AutographRestClientProxyImpl.class);

    @PostConstruct
    public void init(){
        ResourceBundle settings = ResourceBundle.getBundle("autograph");
        this.enabled = Boolean.valueOf(settings.getString("enabled"));
        this.delay = Integer.parseInt(settings.getString("request_interval"))*1000;
        this.autographRestClient = new AutographRestClientImpl();
        this.autographRestClient.init();
    }

    public void startRequesterThread(){
        if(requesterThread != null){
            return;
        }
        LOGGER.info("Start requester thread for  AutoGRAPH REST client proxy");
        this.requesterThread = new Thread(){
            @Override
            public void run() {
                try {
                    while (true) {
                        LOGGER.info("Make request for vehicle info");
                        vehiclesInfo = autographRestClientForThread.getVehiclesInfo();
                        Thread.sleep(delay);
                    }
                }
                catch (InterruptedException e){
                    LOGGER.error("InterruptedException in AutoGRAPH REST client proxy", e); //TODO Should it throw an error in case of interrupting?
                }
            }
        };
        requesterThread.start();
    }

    @PreDestroy
    public void stopRequesterThread(){
        if(requesterThread == null){
            return;
        }
        LOGGER.info("Destroy requester thread for  AutoGRAPH REST client proxy");
        requesterThread.interrupt();
    }

    @Override
    public Map<String, ROnlineInfoWrapper> getVehiclesInfo() {
        if(this.enabled) {
            if (requesterThread == null) {
                autographRestClientForThread = new AutographRestClientImpl();
                autographRestClientForThread.init();
                vehiclesInfo = autographRestClientForThread.getVehiclesInfo();
                startRequesterThread();
            }
            return Collections.unmodifiableMap(vehiclesInfo);
        }
        else {
            return new HashMap<>();
        }
    }

    @Override
    public REnumDevicesWrapper getDevicesInfo() {
        if(this.enabled){
            return autographRestClient.getDevicesInfo();
        }
        else {
            return new REnumDevicesWrapper();
        }
    }

    @Override
    public RTrackInfoWrapper getTrack(String vehicleGuid, Date from, Date to) {
        if(this.enabled){
            return autographRestClient.getTrack(vehicleGuid,from,to);
        }
        else {
            return new RTrackInfoWrapper();
        }
    }

    @Override
    public RTrackInfoWrapper[] getTrackSplittedByDrives(String vehicleGuid, Date from, Date to) {
        if(this.enabled){
            return autographRestClient.getTrackSplittedByDrives(vehicleGuid,from,to);
        }
        else {
            return new RTrackInfoWrapper[0];
        }
    }

    @Override
    public RTripsWrapper getDrives(String vehicleGuid, Date from, Date to) {
        if(this.enabled){
            return autographRestClient.getDrives(vehicleGuid,from,to);
        }
        else {
            return new RTripsWrapper();
        }
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
