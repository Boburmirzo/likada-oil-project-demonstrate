package pro.likada.bean.backing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.likada.bean.model.CustomerModelBean;
import pro.likada.model.Contractor;
import pro.likada.model.Customer;
import pro.likada.model.User;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Yusupov on 12/19/2016.
 */
@Named
@ViewScoped
public class CustomerBackingBean implements Serializable{

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerBackingBean.class);

    @Inject
    private CustomerModelBean customerModelBean;

    /* Fields for Customer list table*/
    private Customer selectedCustomer;
    private String searchStringCustomer;

    private Contractor selectedContractorInEditPage;
    private User selectedUserForManagerInEditPage;

    private List<Contractor> alreadyExistContractorsWithINN;
    private String innStringForNewContractor;

    /* Fields for pop-up dialogs in Customer edit page */
    private List<Contractor> popupDialogContractors;
    private Contractor popupDialogSelectedContractor;
    private String popupDialogSearchStringContractorByINN;
    private List<User> popupDialogUsersForManager;
    private User popupDialogSelectedUserForManager;
    private String popupDialogSearchStringUserForManager;

    /* Fields for Customers table */
    private List<Customer> customersForTable;
    private List<Contractor> contractorsRemovedFromCustomer;

    @PostConstruct
    public void init(){
        if(customerModelBean.getSelectedCustomer()!=null)
            this.selectedCustomer = customerModelBean.getSelectedCustomer();
        Contractor newContractor = (Contractor) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("newContractor");
        if(newContractor!=null)
            selectedCustomer.getContractors().add(newContractor);
    }

    public Customer getSelectedCustomer() {
        return selectedCustomer;
    }

    public void setSelectedCustomer(Customer selectedCustomer) {
        this.selectedCustomer = selectedCustomer;
    }

    public String getSearchStringCustomer() {
        return searchStringCustomer;
    }

    public void setSearchStringCustomer(String searchStringCustomer) {
        this.searchStringCustomer = searchStringCustomer;
    }

    public String getPopupDialogSearchStringContractorByINN() {
        return popupDialogSearchStringContractorByINN;
    }

    public void setPopupDialogSearchStringContractorByINN(String popupDialogSearchStringContractorByINN) {
        this.popupDialogSearchStringContractorByINN = popupDialogSearchStringContractorByINN;
    }

    public Contractor getSelectedContractorInEditPage() {
        return selectedContractorInEditPage;
    }

    public void setSelectedContractorInEditPage(Contractor selectedContractorInEditPage) {
        this.selectedContractorInEditPage = selectedContractorInEditPage;
    }

    public User getSelectedUserForManagerInEditPage() {
        return selectedUserForManagerInEditPage;
    }

    public void setSelectedUserForManagerInEditPage(User selectedUserForManagerInEditPage) {
        this.selectedUserForManagerInEditPage = selectedUserForManagerInEditPage;
    }

    public List<Contractor> getPopupDialogContractors() {
        return popupDialogContractors;
    }

    public void setPopupDialogContractors(List<Contractor> popupDialogContractors) {
        this.popupDialogContractors = popupDialogContractors;
    }

    public List<User> getPopupDialogUsersForManager() {
        return popupDialogUsersForManager;
    }

    public void setPopupDialogUsersForManager(List<User> popupDialogUsersForManager) {
        this.popupDialogUsersForManager = popupDialogUsersForManager;
    }

    public Contractor getPopupDialogSelectedContractor() {
        return popupDialogSelectedContractor;
    }

    public void setPopupDialogSelectedContractor(Contractor popupDialogSelectedContractor) {
        this.popupDialogSelectedContractor = popupDialogSelectedContractor;
    }

    public String getPopupDialogSearchStringUserForManager() {
        return popupDialogSearchStringUserForManager;
    }

    public void setPopupDialogSearchStringUserForManager(String popupDialogSearchStringUserForManager) {
        this.popupDialogSearchStringUserForManager = popupDialogSearchStringUserForManager;
    }

    public User getPopupDialogSelectedUserForManager() {
        return popupDialogSelectedUserForManager;
    }

    public void setPopupDialogSelectedUserForManager(User popupDialogSelectedUserForManager) {
        this.popupDialogSelectedUserForManager = popupDialogSelectedUserForManager;
    }

    public List<Customer> getCustomersForTable() {
        return customersForTable;
    }

    public void setCustomersForTable(List<Customer> customersForTable) {
        this.customersForTable = customersForTable;
    }

    public List<Contractor> getAlreadyExistContractorsWithINN() {
        return alreadyExistContractorsWithINN;
    }

    public void setAlreadyExistContractorsWithINN(List<Contractor> alreadyExistContractorsWithINN) {
        this.alreadyExistContractorsWithINN = alreadyExistContractorsWithINN;
    }

    public String getInnStringForNewContractor() {
        return innStringForNewContractor;
    }

    public void setInnStringForNewContractor(String innStringForNewContractor) {
        this.innStringForNewContractor = innStringForNewContractor;
    }

    public List<Contractor> getContractorsRemovedFromCustomer() {
        return contractorsRemovedFromCustomer;
    }

    public void setContractorsRemovedFromCustomer(List<Contractor> contractorsRemovedFromCustomer) {
        this.contractorsRemovedFromCustomer = contractorsRemovedFromCustomer;
    }
}
