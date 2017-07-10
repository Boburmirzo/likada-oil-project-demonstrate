package pro.likada.service.serviceImpl;

import pro.likada.dao.AgreementDAO;
import pro.likada.model.Agreement;
import pro.likada.model.AgreementStatus;
import pro.likada.model.AgreementType;
import pro.likada.model.Contractor;
import pro.likada.service.AgreementService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Yusupov on 1/25/2017.
 */
@Named("agreementService")
@Transactional
public class AgreementServiceImpl implements AgreementService, Serializable {

    @Inject
    private AgreementDAO agreementDAO;

    @Override
    public Agreement findById(long id) {
        return agreementDAO.findById(id);
    }

    @Override
    public List<Agreement> findByAgreementNumber(String agreementNumber) {
        return agreementDAO.findByAgreementNumber(agreementNumber);
    }

    @Override
    public void save(Agreement agreement) {
        agreementDAO.save(agreement);
    }

    @Override
    public void deleteById(long id) {
        agreementDAO.deleteById(id);
    }

    @Override
    public void deleteByAgreementNumber(String agreementNumber) {
        agreementDAO.deleteByAgreementNumber(agreementNumber);
    }

    @Override
    public List<Agreement> findAllAgreements(String searchString) {
        return agreementDAO.findAllAgreements(searchString);
    }

    @Override
    public List<Agreement> findAllAgreementsBasedOnCompanyAndContractor(Contractor company, Contractor contractor, String agreementType) {
        return agreementDAO.findAllAgreementsBasedOnCompanyAndContractor(company, contractor, agreementType);
    }

    @Override
    public AgreementType findAgreementTypeById(long id) {
        return agreementDAO.findAgreementTypeById(id);
    }

    @Override
    public List<AgreementType> findAllAgreementTypes() {
        return agreementDAO.findAllAgreementTypes();
    }

    @Override
    public AgreementStatus findAgreementStatusById(long id) {
        return agreementDAO.findAgreementStatusById(id);
    }

    @Override
    public List<AgreementStatus> findAllAgreementStatuses() {
        return agreementDAO.findAllAgreementStatuses();
    }
}
