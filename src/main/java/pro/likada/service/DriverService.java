package pro.likada.service;

import pro.likada.model.Driver;

import java.util.List;

/**
 * Created by abuca on 13.03.17.
 */
public interface DriverService {
    Driver findById(long id);

    boolean checkVehicleGovernmentNumber(String actualGovNumber, String checkGovNumber);

    void save(Driver driver);

    List<Driver> findAllDrivers(String searchString);

    Long findAmountOfVehiclesWithDriversConnectedWithDriver(Driver driver);

    void deleteById(long id);
}
