package pro.likada.dao;

import pro.likada.model.Drive;

import java.util.List;

/**
 * Created by bumur on 27.03.2017.
 */
public interface DriveDAO {

    Drive findById(long id);

    void deleteById(Long id);

    void save(Drive drive);

    List<Drive> getAllDrives();

    List<Drive> getAllDrivesOfTerminalForCar(Long id);

}
