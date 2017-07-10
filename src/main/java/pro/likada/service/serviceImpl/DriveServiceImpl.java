package pro.likada.service.serviceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.likada.dao.DriveDAO;
import pro.likada.dto.VehiclePosition;
import pro.likada.model.Drive;
import pro.likada.model.DriveState;
import pro.likada.model.Vehicle;
import pro.likada.rest.client.autograph.AutographRestClient;
import pro.likada.rest.client.autograph.proxy.AutographRestClientProxy;
import pro.likada.service.DriveService;
import pro.likada.service.VehicleService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by bumur on 27.03.2017.
 */
@Named("driveService")
@Transactional
public class DriveServiceImpl implements DriveService, Serializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(DriveServiceImpl.class);

    @Inject
    private VehicleService vehicleService;

    @Inject
    private DriveDAO driveDAO;

    @Override
    public Drive findById(long id) {
        return driveDAO.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        driveDAO.deleteById(id);
    }

    @Override
    public void save(Drive drive) {
        driveDAO.save(drive);
    }

    @Override
    public List<Drive> getAllDrives() {
        return driveDAO.getAllDrives();
    }

    @Override
    public List<Drive> getAllDrivesOfTerminalForCar(Long id) {
        return driveDAO.getAllDrivesOfTerminalForCar(id);
    }

    @Override
    public String getCurrentStateOfDrive(List<DriveState> driveStates) {
        if(driveStates!=null && driveStates.size()>0){
            DriveState driveState = driveStates.get(0);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd HH:mm");
            String dateAndTime=formatter.format(driveState.getModifiedDate());
            if(driveState.getResponsible()!=null) {
                return driveState.getDriveStateType().getName() + " " + dateAndTime + " " + driveState.getResponsible().getTitle();
            }
            return driveState.getDriveStateType().getName() + " " + dateAndTime;
        }
        return "";
    }

    @Override
    public DriveState getCurrentStateOfDriveObject(List<DriveState> driveStates) {
        if(driveStates!=null && driveStates.size()>0){
            return driveStates.get(0);
        }
        return null;
    }

    @Override
    public Drive refreshDistanceData(Drive drive) {
        if(drive.getDriveStatesList() == null || drive.getDriveStatesList().size() == 0 || drive.getVehicle() == null){
            return drive;
        }
        List<DriveState> driveStateList = drive.getDriveStatesList();
        driveStateList.sort((o1, o2) -> o2.compareTo(o1));
        Iterator<DriveState> driveStateIterator = driveStateList.iterator();
        DriveState currentState = driveStateIterator.next();
        currentState.setDistance(0.0);
        DriveState nextState;
        while (driveStateIterator.hasNext()){
            nextState = driveStateIterator.next();
            Double distance = vehicleService.buildTrackLength(drive.getVehicle(),
                    currentState.getModifiedDate(),
                    nextState.getModifiedDate());
            nextState.setDistance(distance);
            currentState = nextState;
        }
        drive.setDriveStates(new HashSet<>(driveStateList));
        return drive;
    }

    public List<VehiclePosition> buildTracksForDriveStages(Drive drive){
        if(drive.getDriveStatesList() == null || drive.getDriveStatesList().size() == 0 || drive.getVehicle() == null){
            return new ArrayList<>();
        }
        List<DriveState> driveStateList = drive.getDriveStatesList();
        driveStateList.sort((o1, o2) -> o2.compareTo(o1));
        Date startDate = driveStateList.get(0).getModifiedDate();
        Date endDate = driveStateList.get(driveStateList.size()-1).getModifiedDate();
        return vehicleService.buildTrack(drive.getVehicle(),startDate,endDate);
    }
}
