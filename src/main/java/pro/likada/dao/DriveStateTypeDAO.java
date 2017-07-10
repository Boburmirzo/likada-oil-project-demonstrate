package pro.likada.dao;

import pro.likada.model.DriveStateType;

import java.util.List;

/**
 * Created by bumur on 28.03.2017.
 */
public interface DriveStateTypeDAO {

    DriveStateType findById(long id);

    void save(DriveStateType driveStateType);

    void deleteById(Long id);

    List<DriveStateType> getAllDriveStateTypes();
}
