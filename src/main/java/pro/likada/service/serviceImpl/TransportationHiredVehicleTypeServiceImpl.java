package pro.likada.service.serviceImpl;

import pro.likada.dao.TransportationHiredVehicleTypeDAO;
import pro.likada.model.TransportationHiredVehicleType;
import pro.likada.service.TransportationHiredVehicleTypeService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Yusupov on 3/17/2017.
 */
@Named("transportationHiredVehicleTypeService")
@Transactional
public class TransportationHiredVehicleTypeServiceImpl implements TransportationHiredVehicleTypeService, Serializable {

    @Inject
    private TransportationHiredVehicleTypeDAO transportationHiredVehicleTypeDAO;


    @Override
    public List<TransportationHiredVehicleType> getAllTransportationHiredVehicleTypes() {
        return transportationHiredVehicleTypeDAO.getAllTransportationHiredVehicleTypes();
    }

}
