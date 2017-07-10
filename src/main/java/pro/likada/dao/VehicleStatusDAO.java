package pro.likada.dao;

import pro.likada.model.VehicleStatus;

import java.util.List;

/**
 * Created by abuca on 14.03.17.
 */
public interface VehicleStatusDAO {
    VehicleStatus findById(long id);

    void save(VehicleStatus vehicleStatus);

    List<VehicleStatus> findAllVehicleStatuses(String searchString);

    void deleteById(long id);
}
