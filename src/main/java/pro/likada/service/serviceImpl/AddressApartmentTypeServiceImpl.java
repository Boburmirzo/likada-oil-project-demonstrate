package pro.likada.service.serviceImpl;

import pro.likada.dao.AddressApartmentTypeDAO;
import pro.likada.model.AddressApartmentType;
import pro.likada.service.AddressApartmentTypeService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

/**
 * Created by bumur on 05.01.2017.
 */
@Named("addressApartmentTypeService")
@Transactional
public class AddressApartmentTypeServiceImpl implements AddressApartmentTypeService, Serializable {

    @Inject
    private AddressApartmentTypeDAO addressApartmentTypeDAO;
    @Override
    public List<AddressApartmentType> getAllAddressApartmentTypes() {
        return addressApartmentTypeDAO.getAllAddressApartmentTypes();
    }
}
