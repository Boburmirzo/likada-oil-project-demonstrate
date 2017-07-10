package pro.likada.service;

import pro.likada.model.DriveStateType;

import java.util.List;

/**
 * Created by bumur on 28.03.2017.
 */
public interface DriveStateTypeService {

    DriveStateType findById(long id);

    void save(DriveStateType driveStateType);

    void deleteById(Long id);

    List<DriveStateType> getAllDriveStateTypes();
}
