package pro.likada.service;

import pro.likada.model.Truck;

import java.util.List;

/**
 * Created by abuca on 06.03.17.
 */
public interface TruckService {
    Truck findById(long id);

    void save(Truck truck);

    List<Truck> findAllTrucks(String searchString);

    void deleteById(long id);

    Long findAmountOfVehiclesConnectedWithTruck(Truck truck);
}
