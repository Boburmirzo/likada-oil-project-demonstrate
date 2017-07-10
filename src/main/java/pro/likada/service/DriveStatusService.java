package pro.likada.service;

import pro.likada.model.DriveStatus;

import java.util.List;

/**
 * Created by bumur on 27.03.2017.
 */
public interface DriveStatusService {

    DriveStatus findById(long id);

    void deleteById(Long id);

    List<DriveStatus> getAllDriveStatuses();
}

