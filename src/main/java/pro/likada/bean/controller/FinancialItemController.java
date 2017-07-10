package pro.likada.bean.controller;

import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.likada.bean.backing.ContractorBackingBean;
import pro.likada.bean.backing.FinancialItemBackingBean;
import pro.likada.bean.model.ContractorModelBean;
import pro.likada.bean.model.CustomerModelBean;
import pro.likada.bean.model.FinancialItemModelBean;
import pro.likada.model.Contractor;
import pro.likada.model.FinancialItem;
import pro.likada.model.InPaymentWay;
import pro.likada.service.ContractorService;
import pro.likada.service.FinancialItemService;
import pro.likada.service.InPaymentWayService;
import pro.likada.service.ProductGroupService;
import pro.likada.util.ModelConstantEnum;
import pro.likada.util.TreeNodeUtil;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.FacesEvent;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.*;

/**
 * Created by bumur on 28.02.2017.
 */
@Named
@RequestScoped
public class FinancialItemController implements Serializable{

    private static final Logger LOGGER = LoggerFactory.getLogger(FinancialItemController.class);

    @Inject
    private FinancialItemModelBean financialItemModelBean;
    @Inject
    private FinancialItemBackingBean financialItemBackingBean;
    @Inject
    private FinancialItemService financialItemService;
    @Inject
    private ContractorBackingBean contractorBackingBean;
    @Inject
    private InPaymentWayService inPaymentWayService;
    @Inject
    private LoginController loginController;
    @Inject
    private ContractorModelBean contractorModelBean;
    @Inject
    private CustomerModelBean customerModelBean;
    @Inject
    private ContractorService contractorService;
    @Inject
    private ContractorController contractorController;
    @Inject
    private ProductGroupService productGroupService;

    public List<FinancialItem> getFinancialItems(){
        if(financialItemBackingBean.getFinancialItems()==null){
            refreshFinancialItems();
        }
        return financialItemBackingBean.getFinancialItems();
    }

    public List<FinancialItem> getFinancialItemsForContractorFrom(){
        if(financialItemBackingBean.getFinancialItems()==null){
            refreshFinancialItemsForContractorFrom();
        }
        return financialItemBackingBean.getFinancialItems();
    }

    public List<FinancialItem> getFinancialItemsForCustomerContractors(){
        if(financialItemBackingBean.getFinancialItems()==null){
            refreshFinancialItemsForCustomerContractors();
        }
        return financialItemBackingBean.getFinancialItems();
    }

    public List<Contractor> getContractors(){
        if(financialItemBackingBean.getContractors()==null) {
            LOGGER.info("Unable to load Contractors from bean, load from Customers table for their contractors.");
            refreshContractorsTable();
        }
        return financialItemBackingBean.getContractors();
    }

    public void refreshContractorsTable(){
        LOGGER.info("Refreshing Contractors table, filter according: " + financialItemBackingBean.getSearchStringForContractorToFinancialItemTable());
        financialItemBackingBean.setContractors(contractorService.findAllContractorsLinkedToCustomer(financialItemBackingBean.getSearchStringForContractorToFinancialItemTable(),customerModelBean.getSelectedCustomer().getId()));
    }

    public void refreshFinancialItemsForCustomerContractors() {
        LOGGER.info("Refreshing Financial items of Customer Contractors");
        List<Long> contractorsIdsOfCustomer = new ArrayList<>();
        for(Contractor contractor:customerModelBean.getSelectedCustomer().getContractorsList()){
            contractorsIdsOfCustomer.add(contractor.getId());
        }
        financialItemBackingBean.setFinancialItems(financialItemService.findAllFinancialItemsOfContractorSourceToContractorDestination(financialItemBackingBean.getFinancialItemDateFrom(), financialItemBackingBean.getFinancialItemDateTo(),financialItemBackingBean.getContractorFromForFilter(),
                financialItemBackingBean.getContractorToForFilter(),contractorsIdsOfCustomer));
    }

    public void resetFinancialItemsOfContractorsOfCustomerTableContractorFrom(){
        financialItemBackingBean.setContractorFromForFilter(null);
        refreshFinancialItemsForCustomerContractors();
    }

    public void resetFinancialItemsOfContractorsOfCustomerTableContractorTo(){
        financialItemBackingBean.setContractorToForFilter(null);
        refreshFinancialItemsForCustomerContractors();
    }

    public void refreshFinancialItemsForContractorFrom() {
        LOGGER.info("Refreshing ContractorFrom Financial item table for, filter according {} to", financialItemBackingBean.getSearchStringForFinancialItemTable());
        financialItemBackingBean.setFinancialItems(financialItemService.findAllFinancialItemsOfContractorSourceToContractorDestination(financialItemBackingBean.getFinancialItemDateFrom(), financialItemBackingBean.getFinancialItemDateTo(), financialItemBackingBean.getContractorFromForFilter(),
                financialItemBackingBean.getContractorToForFilter(),contractorModelBean.getSelectedContractor()));
    }

    public void resetFinancialItemsForContractorFromTable() {
        LOGGER.info("Reset FinancialItems for ContractorFrom table in popup dialog");
        financialItemBackingBean.setSearchStringForFinancialItemTable(null);
        financialItemBackingBean.setSelectedFinancialItemInTable(null);
        refreshFinancialItemsForContractorFrom();
    }

    public void resetFinancialItemsOfContractorsTableContractorFrom(){
        financialItemBackingBean.setContractorFromForFilter(null);
        refreshFinancialItemsForContractorFrom();
    }

    public void resetFinancialItemsOfContractorsTableContractorTo(){
        financialItemBackingBean.setContractorToForFilter(null);
        refreshFinancialItemsForContractorFrom();
    }

    public void refreshFinancialItems(){
        LOGGER.info("Refreshing Financial item table");
        financialItemBackingBean.setFinancialItems(financialItemService.findAllFinancialItemsForSearchString(financialItemBackingBean.getFinancialItemDateFrom(), financialItemBackingBean.getFinancialItemDateTo(), financialItemBackingBean.getContractorFromForFilter(),
                financialItemBackingBean.getContractorToForFilter()));
    }

    public void resetFinancialItemsTableContractorFrom() {
        LOGGER.info("Reset FinancialItems table by ContractorFrom");
        financialItemBackingBean.setContractorFromForFilter(null);
        refreshFinancialItems();
    }

    public List<Contractor> getContractorsInFinancialItemView(String contractorAgencyType){
        if(financialItemBackingBean.getContractorsInFinancialItemView()==null && contractorAgencyType!=null) {
            LOGGER.info("Unable to load Contractors from bean, load from database.");
            refreshContractorsTableInFinancialItemView(contractorAgencyType);
        }
        return financialItemBackingBean.getContractorsInFinancialItemView();
    }

    public void refreshContractorsTableInFinancialItemView(String contractorAgencyType){
        LOGGER.info("Refreshing Contractors table, filter according: " + financialItemBackingBean.getSelectedContractorFromSearchStringForFinancialItemView());
        financialItemBackingBean.setContractorsInFinancialItemView(contractorService.findAllContractors(financialItemBackingBean.getSelectedContractorFromSearchStringForFinancialItemView(), contractorAgencyType));
    }

    public void resetFinancialItemsTableContractorTo() {
        LOGGER.info("Reset FinancialItems table by ContractorTo");
        financialItemBackingBean.setContractorToForFilter(null);
        refreshFinancialItems();
    }

    public void setFinancialItem(FinancialItem financialItem){
        financialItemBackingBean.setSelectedFinancialItemInTable(financialItem);
    }

    public void doubleClickSelectedRowInContractorFromChooseForTableInCustomerView(FacesEvent event){
        setContractorFromForSelectedFinancialItem(financialItemBackingBean.getSelectedContractorFromForFinancialItemView());
    }

    public void doubleClickSelectedRowInContractorFromForFilterByContractorFrom(FacesEvent event){
        refreshFinancialItems();
    }

    public void setContractorFromForFilterByContractorFrom(Contractor contractor){
        financialItemBackingBean.setContractorFromForFilter(contractor);
        refreshFinancialItems();
    }

    public void setContractorFromForFilterByContractorFromForCustomerChosenView(Contractor contractor){
        financialItemBackingBean.setContractorFromForFilter(contractor);
        refreshFinancialItemsForCustomerContractors();
    }

    public void setContractorFromForFilterByContractorFromForContractorChosenView(Contractor contractor){
        financialItemBackingBean.setContractorFromForFilter(contractor);
        refreshFinancialItemsForContractorFrom();
    }

    public void doubleClickSelectedRowInContractorToForFilterByContractorFrom(FacesEvent event){
        refreshFinancialItems();
    }

    public void doubleClickSelectedRowInContractorToForFilterByContractorFromWhenContractorChosen(FacesEvent event){
        refreshFinancialItemsForContractorFrom();
    }

    public void doubleClickSelectedRowInContractorToForFilterByContractorFromWhenCustomerChosen(FacesEvent event){
        refreshFinancialItemsForCustomerContractors();
    }

    public void setContractorToForFilterByContractorTo(Contractor contractor){
        financialItemBackingBean.setContractorToForFilter(contractor);
        refreshFinancialItems();
    }

    public void setContractorToForFilterByContractorToCustomerChosenView(Contractor contractor){
        financialItemBackingBean.setContractorToForFilter(contractor);
        refreshFinancialItemsForCustomerContractors();
    }

    public void setContractorToForFilterByContractorToForContractorChosenView(Contractor contractor){
        financialItemBackingBean.setContractorToForFilter(contractor);
        refreshFinancialItemsForContractorFrom();
    }

    public void setContractorFromForSelectedFinancialItem(Contractor contractorFrom) {
        financialItemBackingBean.getSelectedFinancialItemInTable().setContractorFrom(contractorFrom);
        financialItemService.save(financialItemBackingBean.getSelectedFinancialItemInTable());
        financialItemBackingBean.setSelectedContractorFromForFinancialItemView(null);
        financialItemBackingBean.setSelectedContractorFromSearchStringForFinancialItemView(null);
    }

    public void doubleClickSelectedRowInContractorToChooseTable(FacesEvent event){
        setContractorToForSelectedFinancialItem(financialItemBackingBean.getSelectedContractorToForFinancialItemView());
    }

    public void doubleClickSelectedRowInContractorToChooseTableInCustomerView(FacesEvent event){
        setContractorToForSelectedFinancialItem(financialItemBackingBean.getSelectedContractorToForFinancialItemView());
    }

    public void setContractorToForSelectedFinancialItem(Contractor contractorTo) {
        financialItemBackingBean.getSelectedFinancialItemInTable().setContractorTo(contractorTo);
        financialItemService.save(financialItemBackingBean.getSelectedFinancialItemInTable());
        financialItemBackingBean.setSelectedContractorToSearchStringForFinancialItemView(null);
    }

    public void onColumnEditFinancialItem(CellEditEvent event) {
        FinancialItem financialItem=financialItemBackingBean.getFinancialItems().get(event.getRowIndex());
        financialItemService.save(financialItem);
    }

    public void buttonActionAddNewFinancialItem(ActionEvent actionEvent){
        FinancialItem newFinancialItem= new FinancialItem();
        newFinancialItem.setDate(new Date(System.currentTimeMillis()));
        newFinancialItem.setCreated(new Date(System.currentTimeMillis()));
        if(contractorModelBean.getSelectedContractor()!=null) {
            newFinancialItem.setContractorFrom(contractorModelBean.getSelectedContractor());
        }else if (financialItemBackingBean.getContractorsOfCustomersForSelectOneMenu()!=null){
            newFinancialItem.setContractorFrom(financialItemBackingBean.getContractorsOfCustomersForSelectOneMenu().get(0));
        }
        newFinancialItem.setCreatorUserId(loginController.getCurrentUser().getId());
        newFinancialItem.setCreatorUserTitle(loginController.getCurrentUser().getUsername());
        Set<InPaymentWay> inPaymentWays = new HashSet<>();
        newFinancialItem.setInPaymentWays(inPaymentWays);
        financialItemBackingBean.getFinancialItems().add(newFinancialItem);
        financialItemBackingBean.setSelectedFinancialItemInTable(newFinancialItem);
        financialItemService.save(newFinancialItem);
        financialItemBackingBean.setSwitchBetweenContractors(1);
    }

    public void buttonActionRemove(ActionEvent actionEvent){
        if(financialItemBackingBean.getSelectedFinancialItemInTable()!=null){
            financialItemBackingBean.getFinancialItems().remove(financialItemBackingBean.getSelectedFinancialItemInTable());
            financialItemService.deleteById(financialItemBackingBean.getSelectedFinancialItemInTable().getId());
        }
    }

    public void onCellEditInPaymentWay(CellEditEvent event) {
        InPaymentWay inPaymentWay=financialItemBackingBean.getEditFinancialItem().getInPaymentWayList().get(event.getRowIndex());
        inPaymentWayService.save(inPaymentWay);
    }

    public void buttonActionNewInPaymentWay(ActionEvent actionEvent){
        financialItemBackingBean.getSelectedInPaymentWay().setFinancialItem(financialItemBackingBean.getEditFinancialItem());
        financialItemBackingBean.getEditFinancialItem().getInPaymentWays().add( financialItemBackingBean.getSelectedInPaymentWay());
        financialItemBackingBean.setSelectedInPaymentWay( financialItemBackingBean.getSelectedInPaymentWay());
        financialItemService.save(financialItemBackingBean.getEditFinancialItem());
    }

    public void buttonActionRemoveFinancialItem(ActionEvent actionEvent){
        LOGGER.info("Delete selected InPayment from the list");
        if(financialItemBackingBean.getSelectedInPaymentWay()!=null){
            financialItemBackingBean.getEditFinancialItem().getInPaymentWays().remove(financialItemBackingBean.getSelectedInPaymentWay());
            financialItemService.save(financialItemBackingBean.getEditFinancialItem());
        }
    }

    public void addNewInPayment(ActionEvent actionEvent){
        InPaymentWay newInPaymentWay = new InPaymentWay();
        newInPaymentWay.setObjectType(3);
        financialItemBackingBean.setSelectedInPaymentWay(newInPaymentWay);
    }

    public void test(ToggleEvent toggleEvent){
        FinancialItem financialItem=(FinancialItem)toggleEvent.getData();
        financialItemBackingBean.setEditFinancialItem(financialItem);
    }

    public TreeNode getContractorGroupsRoot() {
        fillGroupsWithContractors();
        if (financialItemBackingBean.getSelectedGroupNode() == null && financialItemBackingBean.getContractorGroupsRoot() != null) {
            if (financialItemBackingBean.getContractorGroupsRoot().getChildCount() > 0) {
                for (int i = 1; i < financialItemBackingBean.getContractorGroupsRoot().getChildCount(); i++) {
                    if (financialItemBackingBean.getContractorGroupsRoot().getChildren().get(i).getData().equals("All")) {
                        financialItemBackingBean.setSelectedGroupNode(financialItemBackingBean.getContractorGroupsRoot().getChildren().get(i));
                        financialItemBackingBean.getSelectedGroupNode().setSelected(true);
                    }
                }
                if (financialItemBackingBean.getSelectedGroupNode() == null) {
                    financialItemBackingBean.setSelectedGroupNode(financialItemBackingBean.getContractorGroupsRoot().getChildren().get(0)) ;
                    financialItemBackingBean.getSelectedGroupNode().setSelected(true);
                }
                refreshContractorsOfSelectedGroup();
            }
        }
        return financialItemBackingBean.getContractorGroupsRoot();
    }

    public void changeContractorsOfSelectedGroup(){
        refreshContractorsOfSelectedGroup();
    }

    public List<TreeNode> getNodeList(){
        refreshGroupNodesList();
        return financialItemBackingBean.getContractorGroupsNodeList();
    }

    private void refreshGroupNodesList(){
        financialItemBackingBean.setContractorGroupsNodeList(productGroupService.getExpandedProductGroupTreeNodeToList(fillGroupsWithContractors()));
    }

    private void refreshContractorsOfSelectedGroup() {
        LOGGER.info("refresh Contractors Of Selected Group");
        if (financialItemBackingBean.getSelectedGroupNode().getData() != null) {
            LOGGER.info(financialItemBackingBean.getSelectedGroupNodeDataInString());
            if((ModelConstantEnum.CUSTOMER.getModelName().equals(financialItemBackingBean.getSelectedGroupNodeDataInString())) ||
                    (financialItemBackingBean.getSelectedGroupNodeDataInString().equals("All"))){
                financialItemBackingBean.setShowCustomersForChoosenGroup(1);
            }else{
                financialItemBackingBean.setShowCustomersForChoosenGroup(0);
            }
            refreshContractorsTableInFinancialItemView(financialItemBackingBean.getSelectedGroupNodeDataInString());
        }
    }

    private TreeNode fillGroupsWithContractors(){
        if(financialItemBackingBean.getContractorGroupsRoot()==null){
            initializeContractorGroupsRoot();
        }
        return financialItemBackingBean.getContractorGroupsRoot();
    }

    private void initializeContractorGroupsRoot() {
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("messages", context.getViewRoot().getLocale());
        List<TreeNode> nodes = new ArrayList<TreeNode>();
        TreeNode root = new TreeNodeUtil("Все", null);
        nodes.add(root);
        TreeNodeUtil slutNode = null;
        for (String s:fillContractorGroupsWithValues()){
            if(ModelConstantEnum.CUSTOMER.getModelName().equals(s)){
                slutNode = new TreeNodeUtil(resourceBundle.getString("customer"), root);
                slutNode.setExpanded(true);
                slutNode.setKey(ModelConstantEnum.CUSTOMER.getModelName());
            }else if(ModelConstantEnum.COMPANY.getModelName().equals(s)){
                slutNode = new TreeNodeUtil(resourceBundle.getString("companies"), root);
                slutNode.setExpanded(true);
                slutNode.setKey(ModelConstantEnum.COMPANY.getModelName());
            }else if(ModelConstantEnum.PROVIDER.getModelName().equals(s)){
                slutNode = new TreeNodeUtil(resourceBundle.getString("providers"), root);
                slutNode.setExpanded(true);
                slutNode.setKey(ModelConstantEnum.PROVIDER.getModelName());
            }else if(ModelConstantEnum.CARRIER.getModelName().equals(s)){
                slutNode = new TreeNodeUtil(resourceBundle.getString("carriers"), root);
                slutNode.setExpanded(true);
                slutNode.setKey(ModelConstantEnum.CARRIER.getModelName());
            }else if(s.equals("All")){
                slutNode = new TreeNodeUtil("Все", root);
                slutNode.setExpanded(true);
                slutNode.setKey("All");
            }
            nodes.add(slutNode);
        }
        financialItemBackingBean.setContractorGroupsRoot(root);
    }

    private List<String> fillContractorGroupsWithValues(){
        List<String> contractorGroups= new ArrayList<>();
        contractorGroups.add("All");
        contractorGroups.add(ModelConstantEnum.CUSTOMER.getModelName());
        contractorGroups.add(ModelConstantEnum.COMPANY.getModelName());
        contractorGroups.add(ModelConstantEnum.PROVIDER.getModelName());
        contractorGroups.add(ModelConstantEnum.CARRIER.getModelName());
        return contractorGroups;
    }

    public void updateFinancialItemForContractorChoose(FinancialItem financialItem){
        financialItemBackingBean.setSelectedFinancialItemInTable(financialItem);
    }

    public void switchFromAllContractorsViewToContractorsOfCustomersView(AjaxBehaviorEvent event){
        LOGGER.info("Switch From All Contractors View To Contractors Of Customers View");
        if(financialItemBackingBean.getSwitchBetweenContractors()==1){
            financialItemBackingBean.setSwitchBetweenContractors(0);
        }else {
            financialItemBackingBean.setSwitchBetweenContractors(1);
        }
        UIComponent component = event.getComponent();
        FinancialItem financialItem = (FinancialItem) component.getAttributes().get("financial_item");
        if(financialItem.getContractorTo()!=null) {
            String financialItemContractorFromShortName = financialItem.getContractorFrom().getNameShort();
            String financialItemContractorToShortName = financialItem.getContractorTo().getNameShort();
            financialItem.getContractorFrom().setNameShort(financialItemContractorToShortName);
            financialItem.getContractorTo().setNameShort(financialItemContractorFromShortName);
            String updateContractorFrom = component.getClientId().replace(":changeFromContractorToContractor", ":inputContractorFromSwitchContractorFrom");
            RequestContext.getCurrentInstance().update(updateContractorFrom);
            String updateContractorTo = component.getClientId().replace(":changeFromContractorToContractor", ":inputContractorToSwitchContractorTo");
            RequestContext.getCurrentInstance().update(updateContractorTo);
            String panelChangeContractorFromView = component.getClientId().replace(":changeFromContractorToContractor", ":panelChangeContractorFromView");
            RequestContext.getCurrentInstance().update(panelChangeContractorFromView);
            String panelChangeContractorToView = component.getClientId().replace(":changeFromContractorToContractor", ":panelChangeContractorToView");
            RequestContext.getCurrentInstance().update(panelChangeContractorToView);
        }
    }

    public int getSwitchParameter(){
        if(financialItemBackingBean.getSwitchBetweenContractors()==1) {
            return 1;
        }else if (financialItemBackingBean.getSwitchBetweenContractors()==0){
            return 0;
        }
        return 1;
    }

    public int showCustomerFieldForAllAndCustomerGroup(){
        if(financialItemBackingBean.getShowCustomersForChoosenGroup()==1){
            return 1;
        }else if(financialItemBackingBean.getShowCustomersForChoosenGroup()==0){
            return 0;
        }
        return 0;
    }

    public void onDateSelect(SelectEvent event){
        refreshFinancialItems();
    }

    public void onDateSelectForContractorFromChoosenView(SelectEvent selectEvent){
        refreshFinancialItemsForContractorFrom();
    }

    public void onDateSelectForContractorFromCustomerChoosenView(SelectEvent selectEvent){
        refreshFinancialItemsForCustomerContractors();
    }

    public void makeFinancialItemActive(){
        if(financialItemBackingBean.getSelectedFinancialItemInTable()!=null) {
            financialItemBackingBean.getSelectedFinancialItemInTable().setActive(true);
            financialItemService.save(financialItemBackingBean.getSelectedFinancialItemInTable());
        }
    }

    public void makeFinancialItemNotActive(){
        if(financialItemBackingBean.getSelectedFinancialItemInTable()!=null) {
            financialItemBackingBean.getSelectedFinancialItemInTable().setActive(false);
            financialItemService.save(financialItemBackingBean.getSelectedFinancialItemInTable());
        }
    }
}
