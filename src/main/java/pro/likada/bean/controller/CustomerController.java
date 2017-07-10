package pro.likada.bean.controller;

import org.primefaces.event.SelectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.likada.bean.backing.CustomerBackingBean;
import pro.likada.bean.model.ContractorModelBean;
import pro.likada.bean.model.CustomerModelBean;
import pro.likada.model.*;
import pro.likada.service.*;
import pro.likada.util.AccessTypeEnum;
import pro.likada.util.ModelConstantEnum;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.FacesEvent;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

/**
 * Created by Yusupov on 12/19/2016.
 */
@Named
@RequestScoped
public class CustomerController implements Serializable{

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

    @Inject
    private CustomerService customerService;
    @Inject
    private CustomerBackingBean customerBackingBean;
    @Inject
    private CustomerModelBean customerModelBean;
    @Inject
    private ContractorModelBean contractorModelBean;
    @Inject
    private ContractorService contractorService;
    @Inject
    private UserService userService;
    @Inject
    private LoginController loginController;
    @Inject
    private ContractorController contractorController;
    @Inject
    private ContractorOrganizationTypeService contractorOrganizationTypeService;
    @Inject
    private ContractorAgencyTypeService contractorAgencyTypeService;


    /* Delete selected customer in the table from the database, and update table data (customers) */
    public void deleteSelectedCustomer(){
        if(customerBackingBean.getSelectedCustomer()!=null) {
            if(customerBackingBean.getSelectedCustomer().getContractors()!=null || customerBackingBean.getSelectedCustomer().getContractors().size()>0){
                FacesContext context = FacesContext.getCurrentInstance();
                ResourceBundle resourceBundle = ResourceBundle.getBundle("messages", context.getViewRoot().getLocale());
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,resourceBundle.getString("customer.cantDelete"), resourceBundle.getString("customer.hasContractor")));
            } else {
                LOGGER.info("Delete Customer: {}", customerBackingBean.getSelectedCustomer());
                customerService.deleteById(customerBackingBean.getSelectedCustomer().getId());
                customerBackingBean.setSelectedCustomer(null);
                refreshCustomersTable();
            }
        }
    }

    /* Refresh Customers table data (searchStringCustomer is handled in DAO) */
    public void refreshCustomersTable(){
        LOGGER.info("Refreshing Customers table, filter according: " + customerBackingBean.getSearchStringCustomer());
        customerBackingBean.setCustomersForTable(customerService.findAllCustomers(customerBackingBean.getSearchStringCustomer()));
    }

    /* Get Customers list for table */
    public List<Customer> getCustomers(){
        if(customerBackingBean.getCustomersForTable()==null) {
            LOGGER.info("Unable to load customers from bean, load from database.");
            refreshCustomersTable();
        }
        return customerBackingBean.getCustomersForTable();
    }

    /* Create a new Customer and redirect to edit page */
    public void buttonActionAddCustomer(ActionEvent actionEvent) {
        Customer newCustomer = new Customer();
        newCustomer.setContractors(new HashSet<Contractor>());
        newCustomer.setCustomerManagers(new HashSet<User>());
        newCustomer.setCreator(loginController.getCurrentUser());
        customerModelBean.setSelectedCustomer(newCustomer);
        redirectToEditPage();
    }

    /* Reset search filter data, and refresh the table of Customers */
    public void resetCustomersTableSearchFilter() {
        LOGGER.info("Reset Customers table");
        customerBackingBean.setSearchStringCustomer(null);
        customerBackingBean.setSelectedCustomer(null);
        refreshCustomersTable();
    }

    /* After double click (to edit chosen Customer) redirect to edit page */
    public void doubleClickSelectedRowInTableCustomers(){
        editCustomer(customerBackingBean.getSelectedCustomer());
    }

    /* After pressing buttons for edit, set fields for chosen Customer and redirect to edit page */
    public void editCustomer(Customer customer){
        LOGGER.info("Passing chosen Customer to editing page");
        customerModelBean.setSelectedCustomer(customer);
        redirectToEditPage();
    }

    private void redirectToEditPage(){
        LOGGER.info("Redirecting to Edit Customer page");
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("edit_customer.xhtml");
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
    }

    /** ----  EDIT/CREATE Customer page functionality  ---- **/

    /** Current Customer's Contractors related functionality **/

    /* Get Contractors for popup dialog that are not linked to any Customer for assigning to the currently editing Customer */
    public List<Contractor> getNotLinkedContractorsForPopupDialog(){
        if(customerBackingBean.getPopupDialogContractors() == null){
            LOGGER.info("Unable to load Contractors for popup dialog from bean, load from database.");
            refreshContractorsTableInPopupDialog();
        }
        return customerBackingBean.getPopupDialogContractors();
    }

    public void refreshContractorsTableInPopupDialog(){
        LOGGER.info("Refreshing Contractors table, filter according {} in popup dialog",customerBackingBean.getPopupDialogSearchStringContractorByINN());
        customerBackingBean.setPopupDialogContractors(contractorService.findAllContractorsNotLinkedToCustomer(
                customerBackingBean.getPopupDialogSearchStringContractorByINN(),
                (customerBackingBean.getSelectedCustomer()!=null) ? customerBackingBean.getSelectedCustomer().getContractors() : null));
    }

    public void resetContractorsTableInPopupDialog() {
        LOGGER.info("Reset Contractors table in popup dialog");
        customerBackingBean.setPopupDialogSearchStringContractorByINN(null);
        customerBackingBean.setPopupDialogSelectedContractor(null);
        refreshContractorsTableInPopupDialog();
    }

    public void doubleClickSelectedRowInTableContractorsPopupDialog(FacesEvent event){
        addSelectedContractorFromPopupDialogToCustomer(customerBackingBean.getPopupDialogSelectedContractor());
    }

    public void addSelectedContractorFromPopupDialogToCustomer(Contractor contractor){
        if(!customerBackingBean.getSelectedCustomer().getContractors().contains(contractor)){
            LOGGER.info("The selected contractor {} is new for this customer {}", contractor, customerBackingBean.getSelectedCustomer());
            contractor.setCreator(null);
            customerBackingBean.getSelectedCustomer().getContractors().add(contractor);
            customerBackingBean.setSelectedContractorInEditPage(contractor);
        }
    }

    public void extractSelectedContractor() {
        if (customerBackingBean.getSelectedContractorInEditPage() != null){
            LOGGER.info("Extract contractor {} from the customer {}", customerBackingBean.getSelectedContractorInEditPage(), customerBackingBean.getSelectedCustomer());
            customerBackingBean.getSelectedContractorInEditPage().setCustomer(null);
            customerBackingBean.getSelectedCustomer().getContractors().remove(customerBackingBean.getSelectedContractorInEditPage());
            customerBackingBean.setSelectedContractorInEditPage(null);
        }
    }

    public void deleteSelectedContractor() {
        if (customerBackingBean.getSelectedContractorInEditPage() != null && loginController.hasAccessTo(ModelConstantEnum.CUSTOMER, AccessTypeEnum.DELETE)) {
            LOGGER.info("Delete contractor {} from the customer {}", customerBackingBean.getSelectedContractorInEditPage(), customerBackingBean.getSelectedCustomer());
            if(customerBackingBean.getContractorsRemovedFromCustomer()==null)
                customerBackingBean.setContractorsRemovedFromCustomer(new LinkedList<Contractor>());
            customerBackingBean.getContractorsRemovedFromCustomer().add(customerBackingBean.getSelectedContractorInEditPage());
            customerBackingBean.getSelectedCustomer().getContractors().remove(customerBackingBean.getSelectedContractorInEditPage());
            customerBackingBean.setSelectedContractorInEditPage(null);
        }
    }

    public void buttonActionInsertContractor(ActionEvent actionEvent){
        LOGGER.info("Reset Contractors table in popup dialog");
        customerBackingBean.setPopupDialogSearchStringContractorByINN(null);
        customerBackingBean.setPopupDialogSelectedContractor(null);
        refreshContractorsTableInPopupDialog();
    }

    public void doubleClickSelectedRowInTableContractorsInEditCustomerPage() {
        redirectToEditContractorPage(customerBackingBean.getSelectedContractorInEditPage());
    }

    public void redirectToEditContractorPage(Contractor contractor){
        LOGGER.info("Redirecting to Edit Contractor pages");
        try {
            contractorModelBean.setSelectedContractor(contractor);
            String url = FacesContext.getCurrentInstance().getViewRoot().getViewId();
            FacesContext.getCurrentInstance().getExternalContext().getFlash().put("backUrl", url);
            FacesContext.getCurrentInstance().getExternalContext().redirect("edit_contractor.xhtml");
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
    }

    public void buttonActionPrepareAddNewContractor(ActionEvent actionEvent){
        customerBackingBean.setAlreadyExistContractorsWithINN(null);
        customerBackingBean.setInnStringForNewContractor(null);
    }

    public void buttonActionAddNewContractorByINN(ModelConstantEnum model){
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("messages", context.getViewRoot().getLocale());
        if(customerBackingBean.getInnStringForNewContractor() == null || customerBackingBean.getInnStringForNewContractor().isEmpty())
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, resourceBundle.getString("error"), resourceBundle.getString("contractor.emptyINNNotAllowed")));
        else if (!customerBackingBean.getInnStringForNewContractor().matches("[0-9]{10,}+"))
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, resourceBundle.getString("error"), resourceBundle.getString("contractor.wrongINN")));
        else {
            ContractorAgencyType contractorAgencyType = contractorAgencyTypeService.findByAgencyType(model.getModelName());
            customerBackingBean.setAlreadyExistContractorsWithINN(contractorService.findByINN(customerBackingBean.getInnStringForNewContractor(), contractorAgencyType.getType()));
            if(customerBackingBean.getAlreadyExistContractorsWithINN()!=null && customerBackingBean.getAlreadyExistContractorsWithINN().size()>0)
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, resourceBundle.getString("error"), resourceBundle.getString("contractor.alreadyExistWithINN")));
            else {
                createNewContractorWithINN(customerBackingBean.getInnStringForNewContractor(), contractorAgencyType);
                redirectToEditContractorPage(customerBackingBean.getSelectedContractorInEditPage());
            }
        }
    }

    private void createNewContractorWithINN(String inn, ContractorAgencyType contractorAgencyType){
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("messages", context.getViewRoot().getLocale());
        Contractor contractor = new Contractor();
        contractor.setInn(inn);
        contractor.setNameWork(resourceBundle.getString("contractor.nameWork"));
        contractor.setCustomer(customerBackingBean.getSelectedCustomer());
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
        contractor.setAgencyType(contractorAgencyType);
        customerBackingBean.setSelectedContractorInEditPage(contractor);
    }



    /** Current Customer's Managers related functionality **/

    /* Get Users for popup dialog that are not linked to current Customer for assigning as a manager to the currently editing Customer */
    public List<User> getUsersForPopupDialogToAssignAsManagerToCustomer(){
        if(customerBackingBean.getPopupDialogUsersForManager()==null){
            LOGGER.info("Unable to load Users to assign as Manager to the Customer from backing bean, load from database.");
            refreshUsersForManagersTableInPopupDialog();
        }
        return customerBackingBean.getPopupDialogUsersForManager();
    }

    public void refreshUsersForManagersTableInPopupDialog(){
        LOGGER.info("Refreshing Users table, filter according: " + customerBackingBean.getPopupDialogSearchStringUserForManager());
        customerBackingBean.setPopupDialogUsersForManager(userService.findAllUsersNotAddedToTable(
                customerBackingBean.getPopupDialogSearchStringUserForManager(),
                (customerBackingBean.getSelectedCustomer()!=null) ? customerBackingBean.getSelectedCustomer().getCustomerManagers() : null));
    }

    public void resetUsersForManagerTableInPopupDialog() {
        LOGGER.info("Reset Users table data of manager choice dialog");
        customerBackingBean.setPopupDialogSearchStringUserForManager(null);
        customerBackingBean.setPopupDialogSelectedUserForManager(null);
        refreshUsersForManagersTableInPopupDialog();
    }

    public void doubleClickSelectedRowInTableUserForManager(FacesEvent event){
        addSelectedUserForManagerFromPopupDialogToCustomer(customerBackingBean.getPopupDialogSelectedUserForManager());
    }

    public void addSelectedUserForManagerFromPopupDialogToCustomer(User user){
        if(!customerBackingBean.getSelectedCustomer().getCustomerManagers().contains(user)){
            LOGGER.info("The selected user {} is a new Manager for the customer {}", user, customerBackingBean.getSelectedCustomer());
            customerBackingBean.getSelectedCustomer().getCustomerManagers().add(user);
            customerBackingBean.setSelectedUserForManagerInEditPage(user);
        }
    }

    public void removeSelectedManagerFromUser(ActionEvent actionEvent){
        LOGGER.info("Remove manager {} from the customer {}", customerBackingBean.getSelectedUserForManagerInEditPage(), customerBackingBean.getSelectedCustomer());
        customerBackingBean.getSelectedCustomer().getCustomerManagers().remove(customerBackingBean.getSelectedUserForManagerInEditPage());
        customerBackingBean.setSelectedUserForManagerInEditPage(null);
    }

    public void buttonActionInsertCustomerManager(ActionEvent actionEvent){
        customerBackingBean.setPopupDialogSelectedUserForManager(null);
        customerBackingBean.setPopupDialogSearchStringUserForManager(null);
        refreshUsersForManagersTableInPopupDialog();
    }

    public void buttonActionSaveCustomer(ActionEvent actionEvent){
        LOGGER.info("Save the Customer {}", customerBackingBean.getSelectedCustomer());
        if(customerBackingBean.getSelectedCustomer().getTitle()!=null && customerBackingBean.getSelectedCustomer().getTitle().isEmpty())
            customerBackingBean.getSelectedCustomer().setTitle("Клиент ИД " + customerBackingBean.getSelectedCustomer().getId());
        customerService.save(customerBackingBean.getSelectedCustomer());
        if(customerBackingBean.getContractorsRemovedFromCustomer()!=null){
            for (Contractor c: customerBackingBean.getContractorsRemovedFromCustomer())
                contractorService.deleteById(c.getId());
            customerBackingBean.setContractorsRemovedFromCustomer(null);
        }
        refreshCustomersTable();
        redirectToCustomersPage();
    }

    public void buttonActionCancelSaveCustomer(ActionEvent actionEvent){
        LOGGER.info("Cancel saving the Customer {}", customerBackingBean.getSelectedCustomer());
        refreshCustomersTable();
        redirectToCustomersPage();
    }

    private void redirectToCustomersPage(){
        LOGGER.info("Redirecting to Customers pages");
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("customers.xhtml");
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
    }

}
