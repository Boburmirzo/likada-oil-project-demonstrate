package pro.likada.dao;

import pro.likada.model.Vehicle;

import java.util.List;

/**
 * Created by abuca on 07.03.17.
 */
public interface VehicleDAO {
    Vehicle findById(long id);

    void save(Vehicle vehicle);

    List<Vehicle> findAllVehicles(String searchString);

    Long findAmountOfVehicleWithDriversConnectedWithVehicle(Vehicle vehicle);

    void deleteById(long id);
}
