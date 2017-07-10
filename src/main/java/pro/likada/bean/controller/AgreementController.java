package pro.likada.bean.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.likada.bean.backing.AgreementBackingBean;
import pro.likada.bean.model.AgreementModelBean;
import pro.likada.model.Agreement;
import pro.likada.model.Contractor;
import pro.likada.service.AgreementService;
import pro.likada.service.ContractorService;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.FacesEvent;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Yusupov on 1/25/2017.
 */
@Named
@RequestScoped
public class AgreementController implements Serializable{

    private static final Logger LOGGER = LoggerFactory.getLogger(AgreementController.class);

    @Inject
    private AgreementBackingBean agreementBackingBean;
    @Inject
    private AgreementModelBean agreementModelBean;
    @Inject
    private AgreementService agreementService;
    @Inject
    private ContractorService contractorService;


    /* Delete selected Agreement in the table from the database, and update table data (Agreements) */
    public void buttonActionNewAgreement(){
        LOGGER.info("Create a new Agreement, redirecting to edit Agreement page");
        Agreement newAgreement = new Agreement();
        Date currentDate = new Date();
        newAgreement.setCreated(currentDate);
        newAgreement.setValidFrom(currentDate);
        newAgreement.setValidTo(currentDate);
        agreementModelBean.setSelectedAgreement(newAgreement);
        redirectToEditPage();
    }

    /* Delete selected Agreement in the table from the database, and update table data (Agreements) */
    public void deleteSelectedAgreement(){
        if(agreementBackingBean.getSelectedAgreement().getStatus()!=null){
            FacesContext context = FacesContext.getCurrentInstance();
            ResourceBundle resourceBundle = ResourceBundle.getBundle("messages", context.getViewRoot().getLocale());
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,resourceBundle.getString("agreement.cantDelete"), resourceBundle.getString("agreement.hasStatus")));
        } else {
            LOGGER.info("Delete Agreement: {}", agreementBackingBean.getSelectedAgreement());
            agreementService.deleteById(agreementBackingBean.getSelectedAgreement().getId());
            agreementModelBean.getAgreementsForTable().remove(agreementBackingBean.getSelectedAgreement());
            agreementBackingBean.setSelectedAgreement(null);
        }
    }

    /* Refresh Agreements table data (searchString is handled in DAO) */
    public void refreshAgreementsTable(){
        LOGGER.info("Refreshing Agreements table, filter according: " + agreementBackingBean.getSearchStringInAgreementTable());
        agreementModelBean.setAgreementsForTable(agreementService.findAllAgreements(agreementBackingBean.getSearchStringInAgreementTable()));
    }

    public void resetSearchStringInAgreementTable(){
        LOGGER.info("Reset Agreements table");
        agreementBackingBean.setSearchStringInAgreementTable(null);
        agreementBackingBean.setSelectedAgreement(null);
        refreshAgreementsTable();
    }

    /* Get Agreements list for table */
    public List<Agreement> getAgreements(){
        if(agreementModelBean.getAgreementsForTable()==null) {
            LOGGER.info("Unable to load Agreements from bean, load from database.");
            refreshAgreementsTable();
        }
        return agreementModelBean.getAgreementsForTable();
    }

    public void doubleClickSelectedRowInTableAgreements(){
        editAgreement(agreementBackingBean.getSelectedAgreement());
    }

    public void editAgreement(Agreement agreement){
        LOGGER.info("Passing chosen Agreement to editing page");
        agreementModelBean.setSelectedAgreement(agreement);
        redirectToEditPage();
    }

    private void redirectToEditPage(){
        LOGGER.info("Redirecting to Edit Agreement page");
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("edit_agreement.xhtml");
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
    }

    /** ----  EDIT/CREATE Agreement page functionality  ---- **/

    public void refreshContractorsTableInPopupDialog(String contractorAgencyType){
        LOGGER.info("Refreshing Contractors table in popup dialog, filter according {} in popup dialog", agreementBackingBean.getPopupDialogSearchStringContractorByINN());
        agreementModelBean.setPopupDialogContractors(contractorService.findAllContractors(agreementBackingBean.getPopupDialogSearchStringContractorByINN(), contractorAgencyType));
    }

    public void resetContractorsTableInPopupDialog(String contractorAgencyType) {
        LOGGER.info("Reset Contractors table in popup dialog");
        agreementBackingBean.setPopupDialogSearchStringContractorByINN(null);
        agreementBackingBean.setPopupDialogSelectedContractor(null);
        refreshContractorsTableInPopupDialog(contractorAgencyType);
    }

    /* Get Contractors for popup dialog for assigning to the currently editing Agreements */
    public List<Contractor> getContractors(String contractorAgencyType){
        if(agreementModelBean.getPopupDialogContractors()==null) {
            LOGGER.info("Unable to load contractors from bean for popup dialog, load from database.");
            refreshContractorsTableInPopupDialog(contractorAgencyType);
        }
        return agreementModelBean.getPopupDialogContractors();
    }

    public void doubleClickSelectedRowInTableContractorsPopupDialog(FacesEvent event){
        setContractorForSelectedAgreement(agreementBackingBean.getPopupDialogSelectedContractor());
    }

    public void setContractorForSelectedAgreement(Contractor contractorForSelectedAgreement) {
        agreementBackingBean.getSelectedAgreement().setContractor(contractorForSelectedAgreement);
        agreementBackingBean.setPopupDialogSelectedContractor(null);
        agreementBackingBean.setPopupDialogSearchStringContractorByINN(null);
    }

    public void buttonActionSaveAgreement(ActionEvent actionEvent){
        LOGGER.info("Save the Agreement {}", agreementBackingBean.getSelectedAgreement());
        agreementService.save(agreementBackingBean.getSelectedAgreement());
        refreshAgreementsTable();
        redirectToAgreementsPage();
    }

    public void buttonActionCancelSaveAgreement(ActionEvent actionEvent){
        LOGGER.info("Cancel saving the Agreement {}", agreementBackingBean.getSelectedAgreement());
        refreshAgreementsTable();
        redirectToAgreementsPage();
    }

    private void redirectToAgreementsPage(){
        LOGGER.info("Redirecting to Agreements pages");
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("agreements.xhtml");
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
    }
}
