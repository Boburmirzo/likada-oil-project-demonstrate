package pro.likada.dao;

import pro.likada.model.DriveState;

import java.util.List;

/**
 * Created by bumur on 28.03.2017.
 */
public interface DriveStateDAO {

    DriveState findById(long id);

    void deleteById(Long id);

    void save(DriveState driveState);

    List<DriveState> getAllDriveStates();
}
