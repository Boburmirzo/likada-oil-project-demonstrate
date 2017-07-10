package pro.likada.service;

import pro.likada.model.ContractorAgencyType;

import java.util.List;

/**
 * Created by Yusupov on 1/25/2017.
 */
public interface ContractorAgencyTypeService {

    ContractorAgencyType findById(long id);

    ContractorAgencyType findByAgencyType(String type);

    void save(ContractorAgencyType contractorAgencyType);

    void deleteById(long id);

    void deleteByAgencyType(String type);

    List<ContractorAgencyType> findAllContractorAgencyTypes();

}
