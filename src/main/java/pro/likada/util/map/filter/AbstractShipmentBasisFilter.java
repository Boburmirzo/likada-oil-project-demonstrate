package pro.likada.util.map.filter;

import pro.likada.model.ShipmentBasis;

/**
 * Created by abuca on 21.03.17.
 */
public abstract class AbstractShipmentBasisFilter extends AbstractMapFilter {

    public AbstractShipmentBasisFilter() {
    }

    public AbstractShipmentBasisFilter(String name) {
        super(name);
    }

    public boolean filter(ShipmentBasis base){
        return true;
    }
}
