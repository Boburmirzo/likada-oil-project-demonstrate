package pro.likada.rest.client.autograph;

import pro.likada.rest.wrapper.autograph.REnumDevicesWrapper;
import pro.likada.rest.wrapper.autograph.ROnlineInfoWrapper;
import pro.likada.rest.wrapper.autograph.RTrackInfoWrapper;
import pro.likada.rest.wrapper.autograph.RTripsWrapper;

import java.util.Date;
import java.util.Map;

/**
 * Created by abuca on 24.03.17.
 */
public interface AutographRestClient {
    Map<String, ROnlineInfoWrapper> getVehiclesInfo();

    REnumDevicesWrapper getDevicesInfo();

    RTrackInfoWrapper getTrack(String vehicleGuid, Date from, Date to);

    RTrackInfoWrapper[] getTrackSplittedByDrives(String vehicleGuid, Date from, Date to);

    RTripsWrapper getDrives(String vehicleGuid, Date from, Date to);

    boolean isEnabled();
}
