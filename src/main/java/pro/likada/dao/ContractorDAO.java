package pro.likada.dao;

import pro.likada.model.Contractor;

import java.util.List;
import java.util.Set;

/**
 * Created by Yusupov on 12/22/2016.
 */
public interface ContractorDAO {

    Contractor findById(long id);

    List<Contractor> findByNameFull(String nameFull);

    List<Contractor> findByNameShort(String nameShort);

    List<Contractor> findByNameWork(String nameWork);

    List<Contractor> findByINN(String inn, String contractorType);

    void save(Contractor contractor);

    void deleteById(long id);

    void deleteByNameFull(String nameFull);

    void deleteByNameShort(String nameShort);

    List<Contractor> findAllContractors(String searchStringTitle, String contractorAgencyType);

    List<Contractor> findAllContractorsNotLinkedToCustomer(String searchStringName, Set<Contractor> contractors);

    List<Contractor> findAllContractorsLinkedToCustomer(String searchStringName, Long customerId);

    List<Contractor> findAllCompaniesToAssignAsClient();

    List<Contractor> findAllCarriersBasedOnOurs(Boolean ours);

    List<Contractor> findAllProviders();

}
