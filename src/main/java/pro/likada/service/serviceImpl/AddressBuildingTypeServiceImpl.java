package pro.likada.service.serviceImpl;

import pro.likada.dao.AddressBuildingTypeDAO;
import pro.likada.model.AddressBuildingType;
import pro.likada.service.AddressBuildingTypeService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

/**
 * Created by bumur on 05.01.2017.
 */
@Named("addressBuildingTypeService")
@Transactional
public class AddressBuildingTypeServiceImpl implements AddressBuildingTypeService, Serializable {

    @Inject
    private AddressBuildingTypeDAO addressBuildingTypeDAO;

    @Override
    public List<AddressBuildingType> getAllAddressBuildingTypes() {
        return addressBuildingTypeDAO.getAllAddressBuildingTypes();
    }
}
