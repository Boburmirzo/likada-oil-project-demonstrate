package pro.likada.service.serviceImpl;

import pro.likada.dao.AddressHouseTypeDAO;
import pro.likada.model.AddressHouseType;
import pro.likada.service.AddressHouseTypeService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

/**
 * Created by bumur on 05.01.2017.
 */
@Named("addressHouseTypeService")
@Transactional
public class AddressHouseTypeServiceImpl implements AddressHouseTypeService, Serializable{

    @Inject
    private AddressHouseTypeDAO addressHouseTypeDAO;

    @Override
    public List<AddressHouseType> getAllAddressHouseTypes() {
        return addressHouseTypeDAO.getAllAddressHouseTypes();
    }
}
