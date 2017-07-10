package pro.likada.service.serviceImpl;

import pro.likada.dao.TransportationVehicleTypeDAO;
import pro.likada.model.TransportationVehicleType;
import pro.likada.service.TransportationVehicleTypeService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Yusupov on 3/17/2017.
 */
@Named("transportationVehicleTypeService")
@Transactional
public class TransportationVehicleTypeServiceImpl implements TransportationVehicleTypeService, Serializable {

    @Inject
    private TransportationVehicleTypeDAO transportationVehicleTypeDAO;

    @Override
    public List<TransportationVehicleType> getAllTransportationVehicleTypes() {
        return transportationVehicleTypeDAO.getAllTransportationVehicleTypes();
    }
}
