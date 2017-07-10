package pro.likada.bean.backing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.likada.bean.model.ContractorModelBean;
import pro.likada.model.*;
import pro.likada.service.ContractorService;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * Created by bumur on 28.12.2016.
 */
@Named
@ViewScoped
public class ContractorBackingBean implements Serializable{

    @Inject
    private ContractorModelBean contractorModelBean;
    @Inject
    private ContractorService contractorService;

    /* Fields for Contractors table */
    private Contractor selectedContractor;
    private String searchStringInContractorsTable;

    /* Create a new Contractor button dialog fields */
    private List<Contractor> alreadyExistContractorsWithINN;
    private String newContractorByINNString;

    /* Edit Contractor page related fields*/
    private Requisite selectedRequisite;
    private BankAccount selectedBankAccount;

    private Requisite editSelectedRequisite;
    private BankAccount editSelectedBankAccount;

    /* Return url */
    private String backUrl;

    @PostConstruct
    public void init(){
        String backUrl = (String) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("backUrl");
        if(backUrl!=null && !backUrl.isEmpty()){
            this.backUrl = backUrl;
        }
        if(contractorModelBean.getSelectedContractor()!=null){
            this.selectedContractor = contractorModelBean.getSelectedContractor();
            this.selectedRequisite = contractorService.getCurrentRequisite(selectedContractor.getRequisitesList());
            this.selectedBankAccount = contractorService.getDefaultBankAccount(selectedContractor);
            this.editSelectedRequisite = selectedRequisite;
            this.editSelectedBankAccount = selectedBankAccount;
        }
    }

    public Contractor getSelectedContractor() {
        return selectedContractor;
    }

    public void setSelectedContractor(Contractor selectedContractor) {
        this.selectedContractor = selectedContractor;
    }

    public String getSearchStringInContractorsTable() {
        return searchStringInContractorsTable;
    }

    public void setSearchStringInContractorsTable(String searchStringInContractorsTable) {
        this.searchStringInContractorsTable = searchStringInContractorsTable;
    }

    public List<Contractor> getAlreadyExistContractorsWithINN() {
        return alreadyExistContractorsWithINN;
    }

    public void setAlreadyExistContractorsWithINN(List<Contractor> alreadyExistContractorsWithINN) {
        this.alreadyExistContractorsWithINN = alreadyExistContractorsWithINN;
    }

    public String getNewContractorByINNString() {
        return newContractorByINNString;
    }

    public void setNewContractorByINNString(String newContractorByINNString) {
        this.newContractorByINNString = newContractorByINNString;
    }

    public Requisite getSelectedRequisite() {
        return selectedRequisite;
    }

    public void setSelectedRequisite(Requisite selectedRequisite) {
        this.selectedRequisite = selectedRequisite;
    }

    public BankAccount getSelectedBankAccount() {
        return selectedBankAccount;
    }

    public void setSelectedBankAccount(BankAccount selectedBankAccount) {
        this.selectedBankAccount = selectedBankAccount;
    }

    public String getBackUrl() {
        return backUrl;
    }

    public void setBackUrl(String backUrl) {
        this.backUrl = backUrl;
    }

    public Requisite getEditSelectedRequisite() {
        return editSelectedRequisite;
    }

    public void setEditSelectedRequisite(Requisite editSelectedRequisite) {
        this.editSelectedRequisite = editSelectedRequisite;
    }

    public BankAccount getEditSelectedBankAccount() {
        return editSelectedBankAccount;
    }

    public void setEditSelectedBankAccount(BankAccount editSelectedBankAccount) {
        this.editSelectedBankAccount = editSelectedBankAccount;
    }
}