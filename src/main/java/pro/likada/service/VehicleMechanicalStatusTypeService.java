package pro.likada.service;

import pro.likada.model.VehicleMechanicalStatusType;

import java.util.List;

/**
 * Created by abuca on 03.04.17.
 */
public interface VehicleMechanicalStatusTypeService {
    List<VehicleMechanicalStatusType> getAllVehicleMechanicalStatusTypes();

    VehicleMechanicalStatusType getVehicleStatusTypeByName(String name);
}
