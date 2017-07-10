package pro.likada.service;

import pro.likada.model.Document;
import pro.likada.model.DriveState;

import java.util.Date;
import java.util.List;

/**
 * Created by bumur on 28.03.2017.
 */
public interface DriveStateService {

    DriveState findById(long id);

    void deleteById(Long id);

    void save(DriveState driveState);

    List<DriveState> getAllDriveStates();

    Document findDriveStateDocument(DriveState driveState);

}
