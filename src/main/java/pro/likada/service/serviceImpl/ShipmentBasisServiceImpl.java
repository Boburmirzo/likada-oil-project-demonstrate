package pro.likada.service.serviceImpl;

import pro.likada.dao.ShipmentBasisDAO;
import pro.likada.model.ShipmentBasis;
import pro.likada.service.ShipmentBasisService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

/**
 * Created by bumur on 13.02.2017.
 */
@Named("shipmentBasisService")
@Transactional
public class ShipmentBasisServiceImpl implements ShipmentBasisService, Serializable {

    @Inject
    private ShipmentBasisDAO shipmentBasisDAO;

    @Override
    public ShipmentBasis findById(long id) {
        return shipmentBasisDAO.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        shipmentBasisDAO.deleteById(id);
    }

    @Override
    public void save(ShipmentBasis shipmentBasis) {
        shipmentBasisDAO.save(shipmentBasis);
    }

    @Override
    public List<ShipmentBasis> getAllShipmentBasis() {
        return shipmentBasisDAO.getAllShipmentBasis();
    }

    @Override
    public List<ShipmentBasis> getProductTypesOrderByTurn() {
        return shipmentBasisDAO.getProductTypesOrderByTurn();
    }

    @Override
    public List<ShipmentBasis> findAllShipmentBasisByNameShort(String searchString) {
        return shipmentBasisDAO.findAllShipmentBasisByNameShort(searchString);
    }

    @Override
    public List<ShipmentBasis> getShipmentBasisByIdInDescendingOrder() {
        return shipmentBasisDAO.getShipmentBasisByIdInDescendingOrder();
    }
}
