package pro.likada.service.serviceImpl;

import pro.likada.dao.TransportationCompanyTypeDAO;
import pro.likada.model.TransportationCompanyType;
import pro.likada.service.TransportationCompanyTypeService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Yusupov on 3/17/2017.
 */
@Named("transportationCompanyTypeService")
@Transactional
public class TransportationCompanyTypeServiceImpl implements TransportationCompanyTypeService, Serializable {

    @Inject
    private TransportationCompanyTypeDAO transportationCompanyTypeDAO;

    @Override
    public List<TransportationCompanyType> getAllTransportationCompanyTypes() {
        return transportationCompanyTypeDAO.getAllTransportationCompanyTypes();
    }

}
