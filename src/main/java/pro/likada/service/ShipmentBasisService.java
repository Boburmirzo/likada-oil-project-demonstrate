package pro.likada.service;

import pro.likada.model.ShipmentBasis;

import java.util.List;

/**
 * Created by bumur on 13.02.2017.
 */
public interface ShipmentBasisService {

    ShipmentBasis findById(long id);

    void deleteById(Long id);

    void save(ShipmentBasis shipmentBasis);

    List<ShipmentBasis> getAllShipmentBasis();

    List<ShipmentBasis> getProductTypesOrderByTurn();

    List<ShipmentBasis> findAllShipmentBasisByNameShort(String searchString);

    List<ShipmentBasis> getShipmentBasisByIdInDescendingOrder();
}
