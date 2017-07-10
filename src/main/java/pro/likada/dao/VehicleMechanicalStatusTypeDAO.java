package pro.likada.dao;

import pro.likada.model.VehicleMechanicalStatusType;

import java.util.List;

/**
 * Created by abuca on 03.04.17.
 */
public interface VehicleMechanicalStatusTypeDAO {
    List<VehicleMechanicalStatusType> getAllVehicleMechanicalStatusTypes();

    VehicleMechanicalStatusType getVehicleStatusTypeByName(String name);
}
