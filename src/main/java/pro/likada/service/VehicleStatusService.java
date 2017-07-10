package pro.likada.service;

import pro.likada.model.Vehicle;
import pro.likada.model.VehicleStatus;

import java.util.List;

/**
 * Created by abuca on 14.03.17.
 */
public interface VehicleStatusService {
    VehicleStatus findById(long id);

    void save(VehicleStatus vehicleStatus);

    List<VehicleStatus> findAllVehicleStatuses(String searchString);

    void deleteById(long id);
}
