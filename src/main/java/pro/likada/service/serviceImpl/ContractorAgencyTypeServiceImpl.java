package pro.likada.service.serviceImpl;

import pro.likada.dao.ContractorAgencyTypeDAO;
import pro.likada.model.ContractorAgencyType;
import pro.likada.service.ContractorAgencyTypeService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Yusupov on 1/25/2017.
 */
@Named("contractorAgencyTypeService")
@Transactional
public class ContractorAgencyTypeServiceImpl implements ContractorAgencyTypeService {

    @Inject
    private ContractorAgencyTypeDAO contractorAgencyTypeDAO;

    @Override
    public ContractorAgencyType findById(long id) {
        return contractorAgencyTypeDAO.findById(id);
    }

    @Override
    public ContractorAgencyType findByAgencyType(String type) {
        return contractorAgencyTypeDAO.findByAgencyType(type);
    }

    @Override
    public void save(ContractorAgencyType contractorAgencyType) {
        contractorAgencyTypeDAO.save(contractorAgencyType);
    }

    @Override
    public void deleteById(long id) {
        contractorAgencyTypeDAO.deleteById(id);
    }

    @Override
    public void deleteByAgencyType(String type) {
        contractorAgencyTypeDAO.deleteByAgencyType(type);
    }

    @Override
    public List<ContractorAgencyType> findAllContractorAgencyTypes() {
        return contractorAgencyTypeDAO.findAllContractorAgencyTypes();
    }
}
