package pro.likada.service;

import pro.likada.model.Agreement;
import pro.likada.model.AgreementStatus;
import pro.likada.model.AgreementType;
import pro.likada.model.Contractor;

import java.util.List;

/**
 * Created by Yusupov on 1/25/2017.
 */
public interface AgreementService {

    Agreement findById(long id);

    List<Agreement> findByAgreementNumber(String agreementNumber);

    void save(Agreement agreement);

    void deleteById(long id);

    void deleteByAgreementNumber(String agreementNumber);

    List<Agreement> findAllAgreements(String searchString);

    List<Agreement> findAllAgreementsBasedOnCompanyAndContractor(Contractor company, Contractor contractor, String agreementType);

    AgreementType findAgreementTypeById(long id);

    List<AgreementType> findAllAgreementTypes();

    AgreementStatus findAgreementStatusById(long id);

    List<AgreementStatus> findAllAgreementStatuses();

}
