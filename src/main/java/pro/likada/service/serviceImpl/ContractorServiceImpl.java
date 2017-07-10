package pro.likada.service.serviceImpl;

import pro.likada.dao.ContractorDAO;
import pro.likada.model.BankAccount;
import pro.likada.model.Contractor;
import pro.likada.model.Requisite;
import pro.likada.service.ContractorService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Created by Yusupov on 12/22/2016.
 */
@Named("contractorService")
@Transactional
public class ContractorServiceImpl implements ContractorService, Serializable {

    @Inject
    private ContractorDAO contractorDAO;

    @Override
    public Contractor findById(long id) {
        return contractorDAO.findById(id);
    }

    @Override
    public List<Contractor> findByNameFull(String nameFull) {
        return contractorDAO.findByNameFull(nameFull);
    }

    @Override
    public List<Contractor> findByNameShort(String nameShort) {
        return contractorDAO.findByNameShort(nameShort);
    }

    @Override
    public List<Contractor> findByNameWork(String nameWork) {
        return contractorDAO.findByNameWork(nameWork);
    }

    @Override
    public List<Contractor> findByINN(String inn, String contractorType) {
        return contractorDAO.findByINN(inn, contractorType);
    }

    @Override
    public List<Contractor> findAllContractors(String searchStringTitle, String contractorAgencyType) {
        return contractorDAO.findAllContractors(searchStringTitle, contractorAgencyType);
    }

    @Override
    public void save(Contractor contractor) {
        contractorDAO.save(contractor);
    }

    @Override
    public void deleteById(long id) {
        contractorDAO.deleteById(id);
    }

    @Override
    public void deleteByNameFull(String nameFull) {
        contractorDAO.deleteByNameFull(nameFull);
    }

    @Override
    public void deleteByNameShort(String nameShort) {
        contractorDAO.deleteByNameShort(nameShort);
    }

    @Override
    public List<Contractor> findAllContractorsNotLinkedToCustomer(String searchStringName, Set<Contractor> contractors) {
        return contractorDAO.findAllContractorsNotLinkedToCustomer(searchStringName, contractors);
    }

    @Override
    public List<Contractor> findAllContractorsLinkedToCustomer(String searchStringName, Long customerId) {
        return contractorDAO.findAllContractorsLinkedToCustomer(searchStringName, customerId);
    }

    @Override
    public List<Contractor> findAllCompaniesToAssignAsClient() {
        return contractorDAO.findAllCompaniesToAssignAsClient();
    }

    @Override
    public List<Contractor> findAllCarriersBasedOnOurs(Boolean ours) {
        return contractorDAO.findAllCarriersBasedOnOurs(ours);
    }

    @Override
    public List<Contractor> findAllProviders() {
        return contractorDAO.findAllProviders();
    }

    @Override
    public Requisite getCurrentRequisite(List<Requisite> requisites) {
        if (requisites != null) {
            if (requisites.size() > 1) {
                Collections.sort(requisites);
                int cnt_rqs = requisites.size();

                for (int i = cnt_rqs - 1; i >= 0; i--) {
                    if (requisites.get(i).getActive()) {
                        return requisites.get(i);
                    } else {
                        if (i == 0) {
                            return requisites.get(cnt_rqs - 1);
                        }
                    }
                }
            } else if (requisites.size() == 1) {
                return requisites.get(0);
            }
        }
        return null;
    }

    @Override
    public BankAccount getDefaultBankAccount(Contractor selectedContractor) {
        if(selectedContractor.getDefaultBankAccount()!=null){
            return selectedContractor.getDefaultBankAccount();
        }
        else {
            List<BankAccount> bankAccounts =selectedContractor.getBankAccountsList();
            int size = bankAccounts.size();
            for (int i = 0; i < size; i++)
                if (bankAccounts.get(i).getActive())
                    return bankAccounts.get(i);
            return bankAccounts.get(size-1);
        }
    }
}
