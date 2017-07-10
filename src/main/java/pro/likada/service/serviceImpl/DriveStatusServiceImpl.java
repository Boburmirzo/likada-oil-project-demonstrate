package pro.likada.service.serviceImpl;

import pro.likada.dao.DriveStatusDAO;
import pro.likada.model.DriveStatus;
import pro.likada.service.DriveStatusService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

/**
 * Created by bumur on 27.03.2017.
 */
@Named("driveStatusService")
@Transactional
public class DriveStatusServiceImpl implements DriveStatusService, Serializable {

    @Inject
    private DriveStatusDAO driveStatusDAO;

    @Override
    public DriveStatus findById(long id) {
        return driveStatusDAO.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        driveStatusDAO.deleteById(id);
    }

    @Override
    public List<DriveStatus> getAllDriveStatuses() {
        return driveStatusDAO.getAllDriveStatuses();
    }
}
