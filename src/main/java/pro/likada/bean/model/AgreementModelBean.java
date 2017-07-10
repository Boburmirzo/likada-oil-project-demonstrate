package pro.likada.bean.model;

import pro.likada.model.Agreement;
import pro.likada.model.AgreementStatus;
import pro.likada.model.AgreementType;
import pro.likada.model.Contractor;
import pro.likada.service.AgreementService;
import pro.likada.service.ContractorService;
import pro.likada.util.ModelConstantEnum;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Yusupov on 1/25/2017.
 */
@Named
@SessionScoped
public class AgreementModelBean implements Serializable {

    @Inject
    private AgreementService agreementService;
    @Inject
    private ContractorService contractorService;

    private Agreement selectedAgreement;

    /* Fields for Agreement table */
    private List<Agreement> agreementsForTable;

    /* Fields for pop-up dialogs in Agreement edit page */
    private List<Contractor> popupDialogContractors;

    /* Lists for agreement edit page */
    private List<Contractor> contractorsListToAssignAsClient;
    private List<AgreementType> agreementTypeList;
    private List<AgreementStatus> agreementStatusList;

    @PostConstruct
    public void init(){
        if(agreementsForTable==null)
            this.agreementsForTable = agreementService.findAllAgreements(null);
        if(popupDialogContractors==null)
            this.popupDialogContractors = contractorService.findAllContractors(null, ModelConstantEnum.CONTRACTOR.getModelName());
        if(contractorsListToAssignAsClient==null)
            this.contractorsListToAssignAsClient = contractorService.findAllCompaniesToAssignAsClient();
        if(agreementTypeList==null)
            this.agreementTypeList = agreementService.findAllAgreementTypes();
        if(agreementStatusList==null)
            this.agreementStatusList = agreementService.findAllAgreementStatuses();
    }

    public Agreement getSelectedAgreement() {
        return selectedAgreement;
    }

    public void setSelectedAgreement(Agreement selectedAgreement) {
        this.selectedAgreement = selectedAgreement;
    }

    public List<Agreement> getAgreementsForTable() {
        return agreementsForTable;
    }

    public void setAgreementsForTable(List<Agreement> agreementsForTable) {
        this.agreementsForTable = agreementsForTable;
    }

    public List<Contractor> getPopupDialogContractors() {
        return popupDialogContractors;
    }

    public void setPopupDialogContractors(List<Contractor> popupDialogContractors) {
        this.popupDialogContractors = popupDialogContractors;
    }

    public List<Contractor> getContractorsListToAssignAsClient() {
        return contractorsListToAssignAsClient;
    }

    public void setContractorsListToAssignAsClient(List<Contractor> contractorsListToAssignAsClient) {
        this.contractorsListToAssignAsClient = contractorsListToAssignAsClient;
    }

    public List<AgreementType> getAgreementTypeList() {
        return agreementTypeList;
    }

    public void setAgreementTypeList(List<AgreementType> agreementTypeList) {
        this.agreementTypeList = agreementTypeList;
    }

    public List<AgreementStatus> getAgreementStatusList() {
        return agreementStatusList;
    }

    public void setAgreementStatusList(List<AgreementStatus> agreementStatusList) {
        this.agreementStatusList = agreementStatusList;
    }
}
