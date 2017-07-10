package pro.likada.bean.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.likada.bean.backing.ContractorBackingBean;
import pro.likada.bean.model.ContractorModelBean;
import pro.likada.model.BankAccount;
import pro.likada.model.Contractor;
import pro.likada.model.ContractorAgencyType;
import pro.likada.model.Requisite;
import pro.likada.service.ContractorAgencyTypeService;
import pro.likada.service.ContractorOrganizationTypeService;
import pro.likada.service.ContractorService;
import pro.likada.util.ModelConstantEnum;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

/**
 * Created by bumur on 28.12.2016.
 */
@Named
@RequestScoped
public class ContractorController implements Serializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContractorController.class);

    @Inject
    private ContractorBackingBean contractorBackingBean;
    @Inject
    private ContractorModelBean contractorModelBean;

    @Inject
    private ContractorService contractorService;
    @Inject
    private ContractorOrganizationTypeService contractorOrganizationTypeService;
    @Inject
    private ContractorAgencyTypeService contractorAgencyTypeService;
    @Inject
    private LoginController loginController;

    /* Delete selected Agreement in the table from the database, and update table data (Agreements) */
    public void deleteSelectedContractor(){
        LOGGER.info("Delete Contractor: {}", contractorBackingBean.getSelectedContractor());
        contractorService.deleteById(contractorBackingBean.getSelectedContractor().getId());
        contractorModelBean.getContractorsForTable().remove(contractorBackingBean.getSelectedContractor());
        contractorBackingBean.setSelectedContractor(null);
    }

    public void buttonActionPrepareAddNewContractor(ActionEvent actionEvent){
        contractorBackingBean.setAlreadyExistContractorsWithINN(null);
        contractorBackingBean.setNewContractorByINNString(null);
    }

    /* Add a new Contractor based on a new INN */
    public void buttonActionAddNewContractorByINN(ModelConstantEnum model){
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("messages", context.getViewRoot().getLocale());
        if(contractorBackingBean.getNewContractorByINNString() == null || contractorBackingBean.getNewContractorByINNString().isEmpty())
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, resourceBundle.getString("error"), resourceBundle.getString("contractor.emptyINNNotAllowed")));
        else if (!contractorBackingBean.getNewContractorByINNString().matches("[0-9]{10,}+"))
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, resourceBundle.getString("error"), resourceBundle.getString("contractor.wrongINN")));
        else {
            ContractorAgencyType contractorAgencyType = contractorAgencyTypeService.findByAgencyType(model.getModelName());
            contractorBackingBean.setAlreadyExistContractorsWithINN(contractorService.findByINN(contractorBackingBean.getNewContractorByINNString(),contractorAgencyType.getType()));
            if(contractorBackingBean.getAlreadyExistContractorsWithINN()!=null && contractorBackingBean.getAlreadyExistContractorsWithINN().size()>0)
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, resourceBundle.getString("error"), resourceBundle.getString("contractor.alreadyExistWithINN")));
            else {
                createNewContractorWithINN(contractorBackingBean.getNewContractorByINNString(), contractorAgencyType);
                redirectToEditContractorPage();
            }
        }
    }
    private void createNewContractorWithINN(String inn, ContractorAgencyType contractorAgencyType){
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("messages", context.getViewRoot().getLocale());
        Contractor contractor = new Contractor();
        contractor.setInn(inn);
        contractor.setNameWork(resourceBundle.getString("contractor.nameWork"));
        contractor.setOrganizationType(contractorOrganizationTypeService.getAllContractorOrganizationTypes().get(0));
        BankAccount newBankAccount = new BankAccount();
        contractor.setDefaultBankAccount(newBankAccount);
        Set<BankAccount> newBankAccounts = new HashSet<>();
        newBankAccounts.add(newBankAccount);
        newBankAccount.setContractor(contractor);
        contractor.setBankAccounts(newBankAccounts);
        Requisite newRequisite = new Requisite();
        newRequisite.setActive(true);
        newRequisite.setValidFrom(new Date());
        Set<Requisite> newRequisites = new HashSet<>();
        newRequisites.add(newRequisite);
        newRequisite.setContractor(contractor);
        contractor.setRequisites(newRequisites);
        contractor.setCreator(loginController.getCurrentUser());
        contractor.setAgencyType(contractorAgencyType);
        contractorModelBean.setSelectedContractor(contractor);
    }

    public void refreshContractorsTable(String contractorAgencyType){
        LOGGER.info("Refreshing Contractors table, filter according: " + contractorBackingBean.getSearchStringInContractorsTable());
        contractorModelBean.setContractorsForTable(contractorService.findAllContractors(contractorBackingBean.getSearchStringInContractorsTable(), contractorAgencyType));
    }

    public void resetContractorsTableData(String contractorAgencyType) {
        LOGGER.info("Reset Contractors table");
        contractorBackingBean.setSearchStringInContractorsTable(null);
        contractorBackingBean.setSelectedContractor(null);
        refreshContractorsTable(contractorAgencyType);
    }

    /* Get Contractors list for table */
    public List<Contractor> getContractors(String contractorAgencyType){
        if(contractorModelBean.getContractorsForTable()==null) {
            LOGGER.info("Unable to load Contractors from bean, load from database.");
            refreshContractorsTable(contractorAgencyType);
        } else if (contractorModelBean.getContractorsForTable().size()>0 &&
                contractorModelBean.getContractorsForTable().get(0).getAgencyType().getType().toLowerCase().compareTo(contractorAgencyType)!=0)
            refreshContractorsTable(contractorAgencyType);
        return contractorModelBean.getContractorsForTable();
    }

    public void doubleClickSelectedRowInTableContractors(){
        editContractor(contractorBackingBean.getSelectedContractor());
    }

    public void editContractor(Contractor contractor){
        LOGGER.info("Passing chosen Contractor to editing page");
        contractorModelBean.setSelectedContractor(contractor);
        redirectToEditContractorPage();
    }

    public void redirectToEditContractorPage() {
        LOGGER.info("Redirecting to Edit Contractor page");
        try {
            String url = FacesContext.getCurrentInstance().getViewRoot().getViewId();
            FacesContext.getCurrentInstance().getExternalContext().getFlash().put("backUrl", url);
            FacesContext.getCurrentInstance().getExternalContext().redirect("edit_contractor.xhtml");
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
    }

    /** ----  EDIT/CREATE Contractor page functionality  ---- **/


    public void updateRequisite(){
        contractorBackingBean.setEditSelectedRequisite(contractorBackingBean.getSelectedRequisite());
    }

    public void updateBankAccount(){
        contractorBackingBean.setEditSelectedBankAccount(contractorBackingBean.getSelectedBankAccount());
    }

    public void buttonActionRemoveRequisite(ActionEvent actionEvent) {
        LOGGER.info("Remove selected Requisite from Contractor");
        if(contractorBackingBean.getSelectedContractor().getRequisites().size() <= 1){
            ResourceBundle resourceBundle = ResourceBundle.getBundle("messages", FacesContext.getCurrentInstance().getViewRoot().getLocale());
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, resourceBundle.getString("error"),  resourceBundle.getString("bankAccount.cannotDeleteLastRequisite")));
        }else {
            contractorBackingBean.getSelectedContractor().getRequisites().remove(contractorBackingBean.getSelectedRequisite());
            contractorBackingBean.setSelectedRequisite(contractorService.getCurrentRequisite(contractorBackingBean.getSelectedContractor().getRequisitesList()));
            contractorBackingBean.setEditSelectedRequisite(contractorBackingBean.getSelectedRequisite());
        }
    }

    public void buttonActionRemoveBankAccount(ActionEvent actionEvent) {
        LOGGER.info("Remove selected BankAccount from Contractor");
        if(contractorBackingBean.getSelectedContractor().getBankAccounts().size() <= 1){
            ResourceBundle resourceBundle = ResourceBundle.getBundle("messages", FacesContext.getCurrentInstance().getViewRoot().getLocale());
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, resourceBundle.getString("error"),  resourceBundle.getString("bankAccount.cannotDeleteLastBankAccount")));
        }else {
            contractorBackingBean.getSelectedContractor().getBankAccounts().remove(contractorBackingBean.getSelectedBankAccount());
            if (contractorBackingBean.getSelectedBankAccount().equals(contractorBackingBean.getSelectedContractor().getDefaultBankAccount())) {
                LOGGER.info("Remove default BankAccount from Contractor");
                contractorBackingBean.getSelectedContractor().setDefaultBankAccount(null);
            }
            contractorBackingBean.setSelectedBankAccount(contractorService.getDefaultBankAccount(contractorBackingBean.getSelectedContractor()));
            contractorBackingBean.setEditSelectedBankAccount(contractorBackingBean.getSelectedBankAccount());
            contractorBackingBean.getSelectedContractor().setDefaultBankAccount(contractorBackingBean.getSelectedBankAccount());
        }
    }

    public void buttonActionNewRequisite(ActionEvent actionEvent) {
        LOGGER.info("Adding a new Requisite");
        Requisite newRequisite = new Requisite();
        newRequisite.setActive(true);
        newRequisite.setValidFrom(new Date());
        newRequisite.setAddressLegalEqualsFact(false);
        newRequisite.setContractor(contractorBackingBean.getSelectedContractor());
        contractorBackingBean.getSelectedContractor().getRequisites().add(newRequisite);
        contractorBackingBean.setSelectedRequisite(newRequisite);
        contractorBackingBean.setEditSelectedRequisite(newRequisite);
    }

    public void buttonActionNewBankAccount(ActionEvent actionEvent) {
        LOGGER.info("Adding a new BankAccount");
        BankAccount newBankAccount = new BankAccount();
        newBankAccount.setActive(true);
        newBankAccount.setContractor(contractorBackingBean.getSelectedContractor());
        contractorBackingBean.getSelectedContractor().getBankAccounts().add(newBankAccount);
        contractorBackingBean.setSelectedBankAccount(newBankAccount);
        contractorBackingBean.setEditSelectedBankAccount(newBankAccount);
    }

    public void buttonActionSetDefaultBankAccount(ActionEvent actionEvent) {
        LOGGER.info("Set default BankAccount for Contractor");
        contractorBackingBean.getSelectedContractor().setDefaultBankAccount(contractorBackingBean.getSelectedBankAccount());
    }

    public String bankAccountNumberValidation(String bankNumber){
        if((bankNumber==null) || bankNumber.isEmpty()){
            FacesContext context = FacesContext.getCurrentInstance();
            ResourceBundle resourceBundle = ResourceBundle.getBundle("messages", context.getViewRoot().getLocale());
            return resourceBundle.getString("bankAccount.bankNumberIsEmpty.autoComplete");
        }
        return bankNumber;
    }

    public void buttonActionSaveContractor(ActionEvent actionEvent) {
        LOGGER.info("Save a new {}", contractorBackingBean.getSelectedContractor());
        if(contractorModelBean.getSelectedContractor().getId()==null && !contractorBackingBean.getBackUrl().isEmpty() && contractorBackingBean.getBackUrl().equals("/edit_customer.xhtml"))
            FacesContext.getCurrentInstance().getExternalContext().getFlash().put("newContractor", contractorBackingBean.getSelectedContractor());
        else{
            contractorService.save(contractorBackingBean.getSelectedContractor());
            refreshContractorsTable(contractorBackingBean.getSelectedContractor().getAgencyType().getType());
        }
        returnToPreviousPage();
    }

    public void buttonActionCancelContractor(ActionEvent actionEvent) {
        LOGGER.info("Cancel saving {} ", contractorBackingBean.getSelectedContractor());
        refreshContractorsTable(contractorBackingBean.getSelectedContractor().getAgencyType().getType());
        returnToPreviousPage();
    }

    public void returnToPreviousPage(){
        LOGGER.info("Redirecting to Previous page");
        try {
            if (contractorBackingBean.getBackUrl()!=null && !contractorBackingBean.getBackUrl().isEmpty())
                FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()+contractorBackingBean.getBackUrl());
            else
                FacesContext.getCurrentInstance().getExternalContext().redirect("contractors.xhtml");
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
    }

    public void editFinancialItem(Contractor contractor){
        LOGGER.info("Passing chosen Contractor to financial items view page");
        contractorModelBean.setSelectedContractor(contractor);
        redirectToEditFinancialItemsPage();
    }

    public void redirectToEditFinancialItemsPage() {
        LOGGER.info("Redirecting to FinancialItems page");
        try {
            String url = FacesContext.getCurrentInstance().getViewRoot().getViewId();
            FacesContext.getCurrentInstance().getExternalContext().getFlash().put("backUrl", url);
            FacesContext.getCurrentInstance().getExternalContext().redirect("contractor_financial_items.xhtml");
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
    }

}
