package pro.likada.service.serviceImpl;

import pro.likada.dao.DriveStateTypeDAO;
import pro.likada.model.DriveStateType;
import pro.likada.service.DriveStateTypeService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

/**
 * Created by bumur on 28.03.2017.
 */
@Named("driveStateTypeService")
@Transactional
public class DriveStateTypeServiceImpl implements DriveStateTypeService, Serializable{

    @Inject
    private DriveStateTypeDAO driveStateTypeDAO;

    @Override
    public DriveStateType findById(long id) {
        return driveStateTypeDAO.findById(id);
    }

    @Override
    public void save(DriveStateType driveStateType) {
        driveStateTypeDAO.save(driveStateType);
    }

    @Override
    public void deleteById(Long id) {
        driveStateTypeDAO.deleteById(id);
    }

    @Override
    public List<DriveStateType> getAllDriveStateTypes() {
        return driveStateTypeDAO.getAllDriveStateTypes();
    }
}
