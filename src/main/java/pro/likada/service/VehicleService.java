package pro.likada.service;

import pro.likada.dto.VehiclePosition;
import pro.likada.model.Vehicle;

import java.util.Date;
import java.util.List;

/**
 * Created by abuca on 07.03.17.
 */
public interface VehicleService {
    Vehicle findById(long id);

    void save(Vehicle vehicle);

    List<Vehicle> findAllVehicles(String searchString);

    Long findAmountOfVehicleWithDriversConnectedWithVehicle(Vehicle vehicle);

    List<Vehicle> refreshPositionData(List<Vehicle> vehicleList);

    List<VehiclePosition> buildTrack(Vehicle vehicle, Date from, Date to);

    Double buildTrackLength(Vehicle vehicle, Date from, Date to);

    void deleteById(long id);
}
