package pro.likada.bean.backing;

import org.primefaces.model.TreeNode;
import pro.likada.model.*;
import pro.likada.service.ProductGroupService;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by bumur on 13.02.2017.
 */
@Named
@ViewScoped
public class ProductBackingBean implements Serializable{

    @Inject
    private ProductGroupService productGroupService;

    private TreeNode productRoot;
    private TreeNode selectedProductNode;
    private List<TreeNode> productGroupsNodeList;

    private int treeNodeLevel;

    private Product selectedProduct;
    private ProductGroup AllProductGroup;
    private List<Product> products;
    private Product productChart;
    private Product editProduct;

    private List<ProductPrice> newProductPrices;
    private ProductPrice selectedProductNewPrice;
    private ProductPrice selectedProductPrice;

    private ProductType popupDialogSelectedProductType;

    private ShipmentBasis selectedProductDefaultShipmentBasis;
    private ShipmentBasis selectedProductShipmentBasis;
    private ShipmentBasis popupDialogSelectedShipmentBase;

    private MeasureUnit measureUnit;

    private Date newPriceData;
    private Boolean newPriceNotify;
    private Boolean productGroupSwitchOrder=false;

    private int	fieldNewPriceTabIndex = 1000;
    private int	fieldButtonPriceTabIndex = 0;
    private int	fieldNewBalanceTabIndex = 1000;
    private int	fieldButtonBalanceTabIndex = 0;

    private String popupDialogSearchStringContractorByNameWork;
    private String popupDialogSearchStringShipmentBaseByNameShort;
    private List<ProductGroup> productGroups;

    @PostConstruct
    public void init(){
        if(productGroups==null){
            productGroups=productGroupService.getAllProductGroups();
        }
        this.newPriceNotify=false;

    }

    public List<ProductGroup> getProductGroups() {
        return productGroups;
    }

    public void setProductGroups(List<ProductGroup> productGroups) {
        this.productGroups = productGroups;
    }

    public TreeNode getProductRoot() {
        return productRoot;
    }

    public void setProductRoot(TreeNode productRoot) {
        this.productRoot = productRoot;
    }

    public Product getSelectedProduct() {
        return selectedProduct;
    }

    public void setSelectedProduct(Product selectedProduct) {
        this.selectedProduct = selectedProduct;
    }

    public List<ProductPrice> getNewProductPrices() {
        return newProductPrices;
    }

    public void setNewProductPrices(List<ProductPrice> newProductPrices) {
        this.newProductPrices = newProductPrices;
    }

    public ProductPrice getSelectedProductNewPrice() {
        return selectedProductNewPrice;
    }

    public void setSelectedProductNewPrice(ProductPrice selectedProductNewPrice) {
        this.selectedProductNewPrice = selectedProductNewPrice;
    }

    public ShipmentBasis getSelectedProductDefaultShipmentBasis() {
        return selectedProductDefaultShipmentBasis;
    }

    public void setSelectedProductDefaultShipmentBasis(ShipmentBasis selectedProductDefaultShipmentBasis) {
        this.selectedProductDefaultShipmentBasis = selectedProductDefaultShipmentBasis;
    }

    public ShipmentBasis getSelectedProductShipmentBasis() {
        return selectedProductShipmentBasis;
    }

    public void setSelectedProductShipmentBasis(ShipmentBasis selectedProductShipmentBasis) {
        this.selectedProductShipmentBasis = selectedProductShipmentBasis;
    }

    public int getFieldNewPriceTabIndex() {
        return fieldNewPriceTabIndex;
    }

    public void setFieldNewPriceTabIndex(int fieldNewPriceTabIndex) {
        this.fieldNewPriceTabIndex = fieldNewPriceTabIndex;
    }

    public int getFieldButtonPriceTabIndex() {
        return fieldButtonPriceTabIndex;
    }

    public void setFieldButtonPriceTabIndex(int fieldButtonPriceTabIndex) {
        this.fieldButtonPriceTabIndex = fieldButtonPriceTabIndex;
    }

    public int getFieldNewBalanceTabIndex() {
        return fieldNewBalanceTabIndex;
    }

    public void setFieldNewBalanceTabIndex(int fieldNewBalanceTabIndex) {
        this.fieldNewBalanceTabIndex = fieldNewBalanceTabIndex;
    }

    public int getFieldButtonBalanceTabIndex() {
        return fieldButtonBalanceTabIndex;
    }

    public void setFieldButtonBalanceTabIndex(int fieldButtonBalanceTabIndex) {
        this.fieldButtonBalanceTabIndex = fieldButtonBalanceTabIndex;
    }

    public MeasureUnit getMeasureUnit() {
        return measureUnit;
    }

    public void setMeasureUnit(MeasureUnit measureUnit) {
        this.measureUnit = measureUnit;
    }

    public ProductPrice getSelectedProductPrice() {
        return selectedProductPrice;
    }

    public void setSelectedProductPrice(ProductPrice selectedProductPrice) {
        this.selectedProductPrice = selectedProductPrice;
    }

    public Date getNewPriceData() {
        return newPriceData;
    }

    public void setNewPriceData(Date newPriceData) {
        this.newPriceData = newPriceData;
    }

    public Boolean getNewPriceNotify() {
        return newPriceNotify;
    }

    public void setNewPriceNotify(Boolean newPriceNotify) {
        this.newPriceNotify = newPriceNotify;
    }

    public ProductGroup getAllProductGroup() {
        return AllProductGroup;
    }

    public void setAllProductGroup(ProductGroup allProductGroup) {
        AllProductGroup = allProductGroup;
    }

    public int getTreeNodeLevel() {
        return treeNodeLevel;
    }

    public void setTreeNodeLevel(int treeNodeLevel) {
        this.treeNodeLevel = treeNodeLevel;
    }

    public TreeNode getSelectedProductNode() {
        return selectedProductNode;
    }

    public void setSelectedProductNode(TreeNode selectedProductNode) {
        this.selectedProductNode = selectedProductNode;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Boolean getProductGroupSwitchOrder() {
        return productGroupSwitchOrder;
    }

    public void setProductGroupSwitchOrder(Boolean productGroupSwitchOrder) {
        this.productGroupSwitchOrder = productGroupSwitchOrder;
    }

    public Product getProductChart() {
        return productChart;
    }

    public void setProductChart(Product productChart) {
        this.productChart = productChart;
    }

    public List<TreeNode> getProductGroupsNodeList() {
        return productGroupsNodeList;
    }

    public void setProductGroupsNodeList(List<TreeNode> productGroupsNodeList) {
        this.productGroupsNodeList = productGroupsNodeList;
    }

    public Product getEditProduct() {
        return editProduct;
    }

    public void setEditProduct(Product editProduct) {
        this.editProduct = editProduct;
    }

    public ProductType getPopupDialogSelectedProductType() {
        return popupDialogSelectedProductType;
    }

    public void setPopupDialogSelectedProductType(ProductType popupDialogSelectedProductType) {
        this.popupDialogSelectedProductType = popupDialogSelectedProductType;
    }

    public String getPopupDialogSearchStringContractorByNameWork() {
        return popupDialogSearchStringContractorByNameWork;
    }

    public void setPopupDialogSearchStringContractorByNameWork(String popupDialogSearchStringContractorByNameWork) {
        this.popupDialogSearchStringContractorByNameWork = popupDialogSearchStringContractorByNameWork;
    }

    public ShipmentBasis getPopupDialogSelectedShipmentBase() {
        return popupDialogSelectedShipmentBase;
    }

    public void setPopupDialogSelectedShipmentBase(ShipmentBasis popupDialogSelectedShipmentBase) {
        this.popupDialogSelectedShipmentBase = popupDialogSelectedShipmentBase;
    }

    public String getPopupDialogSearchStringShipmentBaseByNameShort() {
        return popupDialogSearchStringShipmentBaseByNameShort;
    }

    public void setPopupDialogSearchStringShipmentBaseByNameShort(String popupDialogSearchStringShipmentBaseByNameShort) {
        this.popupDialogSearchStringShipmentBaseByNameShort = popupDialogSearchStringShipmentBaseByNameShort;
    }
}
