package pro.likada.service.serviceImpl;

import pro.likada.dao.ContractorOrganizationTypeDAO;
import pro.likada.model.ContractorOrganizationType;
import pro.likada.service.ContractorOrganizationTypeService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

/**
 * Created by bumur on 04.01.2017.
 */
@Named("contractorOrganizationTypeService")
@Transactional
public class ContractorOrganizationTypeServiceImpl implements ContractorOrganizationTypeService, Serializable {

    @Inject
    private ContractorOrganizationTypeDAO contractorOrganizationTypeDAO;


    @Override
    public List<ContractorOrganizationType> getAllContractorOrganizationTypes() {
        return contractorOrganizationTypeDAO.getAllContractorOrganizationTypes();
    }
}
