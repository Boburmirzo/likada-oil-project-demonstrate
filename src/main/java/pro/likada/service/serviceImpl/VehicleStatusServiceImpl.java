package pro.likada.service.serviceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.likada.dao.VehicleStatusDAO;
import pro.likada.model.Vehicle;
import pro.likada.model.VehicleStatus;
import pro.likada.service.VehicleStatusService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by abuca on 14.03.17.
 */
@Named("vehicleStatusService")
@Transactional
public class VehicleStatusServiceImpl implements VehicleStatusService {

    private static final Logger LOGGER = LoggerFactory.getLogger(VehicleStatusServiceImpl.class);

    @Inject
    public VehicleStatusDAO vehicleStatusDAO;

    @Override
    public VehicleStatus findById(long id) {
        return vehicleStatusDAO.findById(id);
    }

    @Override
    public void save(VehicleStatus vehicleStatus) {
        vehicleStatusDAO.save(vehicleStatus);
    }

    @Override
    public List<VehicleStatus> findAllVehicleStatuses(String searchString) {
        return vehicleStatusDAO.findAllVehicleStatuses(searchString);
    }

    @Override
    public void deleteById(long id) {
        vehicleStatusDAO.deleteById(id);
    }
}
