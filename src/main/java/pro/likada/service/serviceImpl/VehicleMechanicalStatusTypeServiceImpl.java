package pro.likada.service.serviceImpl;

import pro.likada.dao.VehicleMechanicalStatusTypeDAO;
import pro.likada.model.VehicleMechanicalStatusType;
import pro.likada.service.VehicleMechanicalStatusTypeService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by abuca on 03.04.17.
 */
@Named("vehicleMechanicalStatusTypeService")
@Transactional
public class VehicleMechanicalStatusTypeServiceImpl implements VehicleMechanicalStatusTypeService {
    @Inject
    private VehicleMechanicalStatusTypeDAO vehicleMechanicalStatusTypeDAO;

    @Override
    public List<VehicleMechanicalStatusType> getAllVehicleMechanicalStatusTypes() {
        return vehicleMechanicalStatusTypeDAO.getAllVehicleMechanicalStatusTypes();
    }

    @Override
    public VehicleMechanicalStatusType getVehicleStatusTypeByName(String name) {
        return vehicleMechanicalStatusTypeDAO.getVehicleStatusTypeByName(name);
    }
}
