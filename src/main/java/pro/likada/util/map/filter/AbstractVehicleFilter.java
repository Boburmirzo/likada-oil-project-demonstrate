package pro.likada.util.map.filter;

import pro.likada.model.Vehicle;

/**
 * Created by abuca on 21.03.17.
 */
public abstract class AbstractVehicleFilter extends AbstractMapFilter {

    public AbstractVehicleFilter() {
    }

    public AbstractVehicleFilter(String name) {
        super(name);
    }

    public boolean filter(Vehicle vehicle){
        return true;
    }
}