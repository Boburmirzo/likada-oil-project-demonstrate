package pro.likada.bean.backing;

import org.primefaces.model.TreeNode;
import pro.likada.bean.model.CustomerModelBean;
import pro.likada.model.Contractor;
import pro.likada.model.FinancialItem;
import pro.likada.model.InPaymentWay;
import pro.likada.service.ContractorService;
import pro.likada.util.TreeNodeUtil;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by bumur on 28.02.2017.
 */
@Named
@ViewScoped
public class FinancialItemBackingBean implements Serializable{

    @Inject
    private ContractorService contractorService;
    @Inject
    private CustomerModelBean customerModelBean;

    private TreeNode contractorGroupsRoot;
    private TreeNode selectedGroupNode;
    private List<TreeNode> contractorGroupsNodeList;

    private FinancialItem selectedFinancialItemInTable;
    private FinancialItem editFinancialItem;
    private List<FinancialItem> financialItems;

    private List<Contractor> contractors;
    private List<Contractor> contractorsOfCustomersForSelectOneMenu;
    private List<Contractor> contractorsInFinancialItemView;
    private Contractor selectedContractorFromForFinancialItemView;
    private Contractor selectedContractorToForFinancialItemView;
    private String selectedContractorFromSearchStringForFinancialItemView;
    private String selectedContractorToSearchStringForFinancialItemView;
    private String searchStringForFinancialItemTable;
    private String searchStringForContractorToFinancialItemTable;
    private Date financialItemDateFrom;
    private Date financialItemDateTo;
    private List<Contractor> filteredContractorsFrom;
    private List<Contractor> filteredContractorsTo;
    private List<Contractor> filteredContractorsToContractorFromChosenView;
    private Contractor contractorFromForFilter;
    private Contractor contractorToForFilter;

    private int switchBetweenContractors;
    private Boolean switchBetweenContractorsName;
    private int showCustomersForChoosenGroup;

    private int selectedRowIndex = -1;

    List<Boolean> columnToggableValues;

    private InPaymentWay selectedInPaymentWay;

    @PostConstruct
    public void init(){
        this.columnToggableValues= Arrays.asList(true,true,true,true,true,true);
        this.switchBetweenContractors=1;
        this.switchBetweenContractorsName=false;
        if(customerModelBean.getSelectedCustomer()!=null)
            this.contractorsOfCustomersForSelectOneMenu=contractorService.findAllContractorsLinkedToCustomer(null,customerModelBean.getSelectedCustomer().getId());
    }

    public String getSearchStringForContractorToFinancialItemTable() {
        return searchStringForContractorToFinancialItemTable;
    }

    public void setSearchStringForContractorToFinancialItemTable(String searchStringForContractorToFinancialItemTable) {
        this.searchStringForContractorToFinancialItemTable = searchStringForContractorToFinancialItemTable;
    }

    public String getSearchStringForFinancialItemTable() {
        return searchStringForFinancialItemTable;
    }

    public void setSearchStringForFinancialItemTable(String searchStringForFinancialItemTable) {
        this.searchStringForFinancialItemTable = searchStringForFinancialItemTable;
    }

    public FinancialItem getSelectedFinancialItemInTable() {
        return selectedFinancialItemInTable;
    }

    public void setSelectedFinancialItemInTable(FinancialItem selectedFinancialItemInTable) {
        this.selectedFinancialItemInTable = selectedFinancialItemInTable;
    }

    public InPaymentWay getSelectedInPaymentWay() {
        return selectedInPaymentWay;
    }

    public void setSelectedInPaymentWay(InPaymentWay selectedInPaymentWay) {
        this.selectedInPaymentWay = selectedInPaymentWay;
    }

    public FinancialItem getEditFinancialItem() {
        return editFinancialItem;
    }

    public void setEditFinancialItem(FinancialItem editFinancialItem) {
        this.editFinancialItem = editFinancialItem;
    }

    public List<FinancialItem> getFinancialItems() {
        return financialItems;
    }

    public void setFinancialItems(List<FinancialItem> financialItems) {
        this.financialItems = financialItems;
    }

    public List<Contractor> getContractors() {
        return contractors;
    }

    public void setContractors(List<Contractor> contractors) {
        this.contractors = contractors;
    }

    public TreeNode getContractorGroupsRoot() {
        return contractorGroupsRoot;
    }

    public void setContractorGroupsRoot(TreeNode contractorGroupsRoot) {
        this.contractorGroupsRoot = contractorGroupsRoot;
    }

    public TreeNode getSelectedGroupNode() {
        return selectedGroupNode;
    }

    public void setSelectedGroupNode(TreeNode selectedGroupNode) {
        this.selectedGroupNode = selectedGroupNode;
    }

    public List<TreeNode> getContractorGroupsNodeList() {
        return contractorGroupsNodeList;
    }

    public void setContractorGroupsNodeList(List<TreeNode> contractorGroupsNodeList) {
        this.contractorGroupsNodeList = contractorGroupsNodeList;
    }

    public String getSelectedGroupNodeDataInString() {
        return ((TreeNodeUtil)selectedGroupNode)!=null ? ((TreeNodeUtil)selectedGroupNode).getKey() : "All";
    }

    public List<Boolean> getColumnToggableValues() {
        return columnToggableValues;
    }

    public void setColumnToggableValues(List<Boolean> columnToggableValues) {
        this.columnToggableValues = columnToggableValues;
    }

    public Contractor getSelectedContractorFromForFinancialItemView() {
        return selectedContractorFromForFinancialItemView;
    }

    public void setSelectedContractorFromForFinancialItemView(Contractor selectedContractorFromForFinancialItemView) {
        this.selectedContractorFromForFinancialItemView = selectedContractorFromForFinancialItemView;
    }

    public String getSelectedContractorFromSearchStringForFinancialItemView() {
        return selectedContractorFromSearchStringForFinancialItemView;
    }

    public void setSelectedContractorFromSearchStringForFinancialItemView(String selectedContractorFromSearchStringForFinancialItemView) {
        this.selectedContractorFromSearchStringForFinancialItemView = selectedContractorFromSearchStringForFinancialItemView;
    }

    public List<Contractor> getContractorsInFinancialItemView() {
        return contractorsInFinancialItemView;
    }

    public void setContractorsInFinancialItemView(List<Contractor> contractorsInFinancialItemView) {
        this.contractorsInFinancialItemView = contractorsInFinancialItemView;
    }

    public Contractor getSelectedContractorToForFinancialItemView() {
        return selectedContractorToForFinancialItemView;
    }

    public void setSelectedContractorToForFinancialItemView(Contractor selectedContractorToForFinancialItemView) {
        this.selectedContractorToForFinancialItemView = selectedContractorToForFinancialItemView;
    }

    public String getSelectedContractorToSearchStringForFinancialItemView() {
        return selectedContractorToSearchStringForFinancialItemView;
    }

    public void setSelectedContractorToSearchStringForFinancialItemView(String selectedContractorToSearchStringForFinancialItemView) {
        this.selectedContractorToSearchStringForFinancialItemView = selectedContractorToSearchStringForFinancialItemView;
    }

    public int getSwitchBetweenContractors() {
        return switchBetweenContractors;
    }

    public void setSwitchBetweenContractors(int switchBetweenContractors) {
        this.switchBetweenContractors = switchBetweenContractors;
    }

    public Date getFinancialItemDateFrom() {
        return financialItemDateFrom;
    }

    public void setFinancialItemDateFrom(Date financialItemDateFrom) {
        this.financialItemDateFrom = financialItemDateFrom;
    }

    public Date getFinancialItemDateTo() {
        return financialItemDateTo;
    }

    public void setFinancialItemDateTo(Date financialItemDateTo) {
        this.financialItemDateTo = financialItemDateTo;
    }

    public int getShowCustomersForChoosenGroup() {
        return showCustomersForChoosenGroup;
    }

    public void setShowCustomersForChoosenGroup(int showCustomersForChoosenGroup) {
        this.showCustomersForChoosenGroup = showCustomersForChoosenGroup;
    }

    public List<Contractor> getFilteredContractorsFrom() {
        return filteredContractorsFrom;
    }

    public void setFilteredContractorsFrom(List<Contractor> filteredContractorsFrom) {
        this.filteredContractorsFrom = filteredContractorsFrom;
    }

    public List<Contractor> getFilteredContractorsTo() {
        return filteredContractorsTo;
    }

    public void setFilteredContractorsTo(List<Contractor> filteredContractorsTo) {
        this.filteredContractorsTo = filteredContractorsTo;
    }

    public List<Contractor> getFilteredContractorsToContractorFromChosenView() {
        return filteredContractorsToContractorFromChosenView;
    }

    public void setFilteredContractorsToContractorFromChosenView(List<Contractor> filteredContractorsToContractorFromChosenView) {
        this.filteredContractorsToContractorFromChosenView = filteredContractorsToContractorFromChosenView;
    }

    public Contractor getContractorFromForFilter() {
        return contractorFromForFilter;
    }

    public void setContractorFromForFilter(Contractor contractorFromForFilter) {
        this.contractorFromForFilter = contractorFromForFilter;
    }

    public Contractor getContractorToForFilter() {
        return contractorToForFilter;
    }

    public void setContractorToForFilter(Contractor contractorToForFilter) {
        this.contractorToForFilter = contractorToForFilter;
    }

    public Boolean getSwitchBetweenContractorsName() {
        return switchBetweenContractorsName;
    }

    public void setSwitchBetweenContractorsName(Boolean switchBetweenContractorsName) {
        this.switchBetweenContractorsName = switchBetweenContractorsName;
    }

    public List<Contractor> getContractorsOfCustomersForSelectOneMenu() {
        return contractorsOfCustomersForSelectOneMenu;
    }

    public void setContractorsOfCustomersForSelectOneMenu(List<Contractor> contractorsOfCustomersForSelectOneMenu) {
        this.contractorsOfCustomersForSelectOneMenu = contractorsOfCustomersForSelectOneMenu;
    }

    public int getSelectedRowIndex() {
        return selectedRowIndex;
    }

    public void setSelectedRowIndex(int selectedRowIndex) {
        this.selectedRowIndex = selectedRowIndex;
    }
}
