package pro.likada.service.serviceImpl;

import pro.likada.dao.DriverDAO;
import pro.likada.model.Driver;
import pro.likada.service.DriverService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

/**
 * Created by abuca on 13.03.17.
 */
@Named("driverService")
@Transactional
public class DriverServiceImpl implements DriverService,Serializable {
    @Inject
    DriverDAO driverDAO;

    @Override
    public Driver findById(long id) {
        return driverDAO.findById(id);
    }

    @Override
    public boolean checkVehicleGovernmentNumber(String actualGovNumber, String checkGovNumber) {
        return actualGovNumber.contains(checkGovNumber);
    }

    @Override
    public void save(Driver driver) {
        driverDAO.save(driver);
    }

    @Override
    public List<Driver> findAllDrivers(String searchString) {
        return driverDAO.findAllDrivers(searchString);
    }

    @Override
    public Long findAmountOfVehiclesWithDriversConnectedWithDriver(Driver driver) {
        return driverDAO.findAmountOfVehiclesWithDriversConnectedWithDriver(driver);
    }

    @Override
    public void deleteById(long id) {
        driverDAO.deleteById(id);
    }
}
