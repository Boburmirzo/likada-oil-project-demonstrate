package pro.likada.dao;

import pro.likada.model.Driver;

import java.util.List;

/**
 * Created by abuca on 13.03.17.
 */
public interface DriverDAO {
    Driver findById(long id);

    void save(Driver driver);

    List<Driver> findAllDrivers(String searchString);

    Long findAmountOfVehiclesWithDriversConnectedWithDriver(Driver driver);

    void deleteById(long id);
}
