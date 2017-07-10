package pro.likada.service;

import pro.likada.dto.VehiclePosition;
import pro.likada.model.Drive;
import pro.likada.model.DrivePoint;
import pro.likada.model.DriveState;

import java.util.List;

/**
 * Created by bumur on 27.03.2017.
 */
public interface DriveService {

    Drive findById(long id);

    void deleteById(Long id);

    void save(Drive drive);

    List<Drive> getAllDrives();

    List<Drive> getAllDrivesOfTerminalForCar(Long id);

    String getCurrentStateOfDrive(List<DriveState> driveStates);

    DriveState getCurrentStateOfDriveObject(List<DriveState> driveStates);

    Drive refreshDistanceData(Drive drive);

    List<VehiclePosition> buildTracksForDriveStages(Drive drive);
}
