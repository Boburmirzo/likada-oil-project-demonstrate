package pro.likada.service.serviceImpl;

import pro.likada.dao.TruckDAO;
import pro.likada.model.Truck;
import pro.likada.service.TruckService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

/**
 * Created by abuca on 06.03.17.
 */
@Named("truckService")
@Transactional
public class TruckServiceImpl implements TruckService, Serializable {
    @Inject
    TruckDAO truckDAO;

    @Override
    public Truck findById(long id){
        return truckDAO.findById(id);
    }

    @Override
    public void save(Truck truck) {
        truckDAO.save(truck);
    }

    @Override
    public List<Truck> findAllTrucks(String searchString) {
        return truckDAO.findAllTrucks(searchString);
    }

    @Override
    public void deleteById(long id){
        truckDAO.deleteById(id);
    }

    @Override
    public Long findAmountOfVehiclesConnectedWithTruck(Truck truck) {
        return truckDAO.findAmountOfVehiclesConnectedWithTruck(truck);
    }
}
