package pro.likada.service.serviceImpl;

import org.glassfish.jersey.message.internal.MessageBodyProviderNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.likada.dao.VehicleDAO;
import pro.likada.dto.VehiclePosition;
import pro.likada.model.Vehicle;
import pro.likada.rest.client.autograph.AutographRestClient;
import pro.likada.rest.client.autograph.Impl.AutographRestClientDirect;
import pro.likada.rest.client.autograph.proxy.AutographRestClientProxy;
import pro.likada.rest.client.autograph.proxy.AutographRestClientProxyImpl;
import pro.likada.rest.wrapper.autograph.*;
import pro.likada.service.VehicleService;
import pro.likada.util.VehicleStateEnum;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import javax.ws.rs.ProcessingException;
import java.io.Serializable;
import java.util.*;

/**
 * Created by abuca on 07.03.17.
 */
@Named("vehicleService")
@Transactional
public class VehicleServiceImpl implements VehicleService,Serializable {
    @Inject
    VehicleDAO vehicleDAO;
    @Inject
    @AutographRestClientProxy
    AutographRestClient autographRestClient;

    private static final Logger LOGGER = LoggerFactory.getLogger(VehicleServiceImpl.class);

    @Override
    public Vehicle findById(long id){
        return vehicleDAO.findById(id);
    }

    @Override
    public void save(Vehicle vehicle) {
        vehicleDAO.save(vehicle);
    }

    @Override
    public List<Vehicle> findAllVehicles(String searchString) {
        return vehicleDAO.findAllVehicles(searchString);
    }

    @Override
    public List<Vehicle> refreshPositionData(List<Vehicle> vehicleList){
        if(!autographRestClient.isEnabled()){
            LOGGER.warn("Cannot refresh positions data. AutoGRAPH rest client is disabled.");
            return vehicleList;
        }
        Map<String, ROnlineInfoWrapper> guidToVehicleInfoWrapperMap = autographRestClient.getVehiclesInfo();
        int refreshedVehiclesAmount = 0;
        for(Vehicle vehicle : vehicleList){
            if(guidToVehicleInfoWrapperMap.containsKey(vehicle.getGUID()) && guidToVehicleInfoWrapperMap.get(vehicle.getGUID()) != null){
                ROnlineInfoWrapper vehicleInfo = guidToVehicleInfoWrapperMap.get(vehicle.getGUID());
                vehicle.setLatitude(vehicleInfo.getLastPosition().getLatitude());
                vehicle.setLongitude(vehicleInfo.getLastPosition().getLongitude());
                vehicle.setSpeed(vehicleInfo.getSpeed());
                vehicle.setCourse(vehicleInfo.getCourse());
                vehicle.setLastObservationDate(vehicleInfo.getDate());
                vehicle.setMoveState(VehicleStateEnum.fromInteger(vehicleInfo.getState()));
                refreshedVehiclesAmount++;
            }
        }
        LOGGER.info("Positions of {} vehicles refreshed", refreshedVehiclesAmount);
        if(refreshedVehiclesAmount == 0){
            LOGGER.warn("Cannot find any vehicles position. Try to refresh vehicle GUIDs and repeat request");
            refreshGUIDs();
        }
        return vehicleList;
    }

    @Override
    public List<VehiclePosition> buildTrack(Vehicle vehicle, Date from, Date to){
        if(!autographRestClient.isEnabled()){
            LOGGER.warn("Cannot build track for vehicle {}. AutoGRAPH rest client is disabled.", vehicle);
            return new ArrayList<>();
        }
        List<VehiclePosition> vehiclePositionList;
        try{
            vehiclePositionList = autographRestClient.getTrack(vehicle.getGUID(), from, to).toVehiclePositions();
        }
        catch (ProcessingException ex){
            LOGGER.warn("Cannot build track for vehicle {}. Try to refresh vehicle GUID and repeat request", vehicle);
            refreshGUIDs();
            vehicle = findById(vehicle.getId());
            vehiclePositionList = autographRestClient.getTrack(vehicle.getGUID(), from, to).toVehiclePositions();
        }
        return vehiclePositionList;
    }

    @Override
    public Double buildTrackLength(Vehicle vehicle, Date from, Date to){
        if(!autographRestClient.isEnabled()){
            LOGGER.warn("Cannot build track length for vehicle {}. AutoGRAPH rest client is disabled.", vehicle);
            return null;
        }
        RTripsWrapper driveInfo;
        try{
            driveInfo = autographRestClient.getDrives(vehicle.getGUID(), from, to);
        }
        catch (ProcessingException ex){
            refreshGUIDs();
            LOGGER.warn("Cannot build track length for vehicle {}. Try to refresh vehicle GUID and repeat request", vehicle);
            vehicle = findById(vehicle.getId());
            driveInfo = autographRestClient.getDrives(vehicle.getGUID(), from, to);
        }
        Double distance = 0.0;
        for(RTripWapper trip : driveInfo.getTrips()){
            for(RTripStageWrapper tripStage : trip.getMotionStages()){
                distance += tripStage.getDistances().stream().reduce((aDouble, aDouble2) -> aDouble+aDouble2).get();
            }
        }
        return distance;
    }

    @Override
    public Long findAmountOfVehicleWithDriversConnectedWithVehicle(Vehicle vehicle) {
        return vehicleDAO.findAmountOfVehicleWithDriversConnectedWithVehicle(vehicle);
    }

    @Override
    public void deleteById(long id){
        vehicleDAO.deleteById(id);
    }

    private void refreshGUIDs(){
        if(!autographRestClient.isEnabled()){
            LOGGER.warn("Cannot refresh vehicles GUIDs. AutoGRAPH rest client is disabled.");
            return;
        }
        REnumDevicesWrapper devicesInfoWrapper = autographRestClient.getDevicesInfo();
        Map<Long,String> deviceIDToGUIDMap = new HashMap<>();
        for(RDeviceItemWrapper deviceInfo : devicesInfoWrapper.getDevicesInfo()){
            deviceIDToGUIDMap.put(Long.valueOf(deviceInfo.getSerial()), deviceInfo.getGUID());
        }
        List<Vehicle> vehicleList = vehicleDAO.findAllVehicles(null);
        for(Vehicle vehicle : vehicleList){
            if(deviceIDToGUIDMap.containsKey(vehicle.getAutographDeviceID())){
                vehicle.setGUID(deviceIDToGUIDMap.get(vehicle.getAutographDeviceID()));
                vehicleDAO.save(vehicle);
            }
        }
    }
}
