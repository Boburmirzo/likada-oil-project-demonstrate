package pro.likada.bean.backing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.likada.bean.model.AgreementModelBean;
import pro.likada.model.Agreement;
import pro.likada.model.Contractor;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Yusupov on 1/25/2017.
 */
@Named
@ViewScoped
public class AgreementBackingBean implements Serializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(AgreementBackingBean.class);

    @Inject
    private AgreementModelBean agreementModelBean;

    private Agreement selectedAgreement;

    private String searchStringInAgreementTable;

    /* Fields for pop-up dialogs in Agreement edit page */
    private Contractor popupDialogSelectedContractor;
    private String popupDialogSearchStringContractorByINN;

    @PostConstruct
    public void init(){
        if(agreementModelBean.getSelectedAgreement()!=null)
            this.selectedAgreement = agreementModelBean.getSelectedAgreement();
    }

    public Agreement getSelectedAgreement() {
        return selectedAgreement;
    }

    public void setSelectedAgreement(Agreement selectedAgreement) {
        this.selectedAgreement = selectedAgreement;
    }

    public String getSearchStringInAgreementTable() {
        return searchStringInAgreementTable;
    }

    public void setSearchStringInAgreementTable(String searchStringInAgreementTable) {
        this.searchStringInAgreementTable = searchStringInAgreementTable;
    }

    public Contractor getPopupDialogSelectedContractor() {
        return popupDialogSelectedContractor;
    }

    public void setPopupDialogSelectedContractor(Contractor popupDialogSelectedContractor) {
        this.popupDialogSelectedContractor = popupDialogSelectedContractor;
    }

    public String getPopupDialogSearchStringContractorByINN() {
        return popupDialogSearchStringContractorByINN;
    }

    public void setPopupDialogSearchStringContractorByINN(String popupDialogSearchStringContractorByINN) {
        this.popupDialogSearchStringContractorByINN = popupDialogSearchStringContractorByINN;
    }
}
