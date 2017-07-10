package pro.likada.service.serviceImpl;

import pro.likada.dao.DocumentDAO;
import pro.likada.dao.DriveStateDAO;
import pro.likada.model.Document;
import pro.likada.model.DriveState;
import pro.likada.service.DriveStateService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by bumur on 28.03.2017.
 */
@Named("driveStateService")
@Transactional
public class DriveStateServiceImpl implements DriveStateService {

    @Inject
    private DriveStateDAO driveStateDAO;
    @Inject
    private DocumentDAO documentDAO;

    @Override
    public DriveState findById(long id) {
        return driveStateDAO.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        driveStateDAO.deleteById(id);
    }

    @Override
    public void save(DriveState driveState) {
        driveStateDAO.save(driveState);
    }

    @Override
    public List<DriveState> getAllDriveStates() {
        return driveStateDAO.getAllDriveStates();
    }

    @Override
    public Document findDriveStateDocument(DriveState driveState) {
        List<Document> document = null;
        document = documentDAO.findByParentTypeId(driveState.getId(),DriveState.class.getSimpleName());
        if(document.size()>0)
            return document.get(0);
        else
            return null;
    }

}
