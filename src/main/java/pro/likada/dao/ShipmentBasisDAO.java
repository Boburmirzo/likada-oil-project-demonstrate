package pro.likada.dao;

import pro.likada.model.ShipmentBasis;

import java.util.List;

/**
 * Created by bumur on 13.02.2017.
 */
public interface ShipmentBasisDAO {

    ShipmentBasis findById(long id);

    List<ShipmentBasis> getAllShipmentBasis();

    void deleteById(Long id);

    void save(ShipmentBasis shipmentBasis);

    List<ShipmentBasis> getProductTypesOrderByTurn();

    List<ShipmentBasis> findAllShipmentBasisByNameShort(String searchString);

    List<ShipmentBasis> getShipmentBasisByIdInDescendingOrder();
}
