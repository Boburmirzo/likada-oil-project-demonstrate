package pro.likada.bean.controller;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.ReorderEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TreeDragDropEvent;
import org.primefaces.model.TreeNode;
import org.primefaces.model.chart.LineChartModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.likada.bean.backing.ProductBackingBean;
import pro.likada.bean.model.ProductModelBean;
import pro.likada.bot.products.BotConfiguration;
import pro.likada.bot.products.BotSendMessageUtil.MessagePostTelegramUtil;
import pro.likada.bot.products.BotSendMessageUtil.MessageProductPricesUtil;
import pro.likada.model.*;
import pro.likada.service.*;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.FacesEvent;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.*;

/**
 * Created by bumur on 13.02.2017.
 */
@Named
@RequestScoped
public class ProductController implements Serializable{

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    @Inject
    private ProductBackingBean productBackingBean;
    @Inject
    private ProductModelBean productModelBean;
    @Inject
    private ShipmentBasisService shipmentBasisService;
    @Inject
    private ProductPriceService productPriceService;
    @Inject
    private MeasureUnitService measureUnitService;
    @Inject
    private ProductGroupService productGroupService;
    @Inject
    private ProductService productService;
    @Inject
    private LoginController loginController;
    @Inject
    private ProductTypeService productTypeService;

    public TreeNode getRoot(){
        fillProductsGroupNode();
        if (productBackingBean.getSelectedProductNode() == null && productBackingBean.getProductRoot() != null) {
            if (productBackingBean.getProductRoot().getChildCount() > 0) {
                for (int i = 1; i < productBackingBean.getProductRoot().getChildCount(); i++) {
                    if (productBackingBean.getProductRoot().getChildren().get(i).getData() ==productBackingBean.getAllProductGroup()) {
                        productBackingBean.setSelectedProductNode(productBackingBean.getProductRoot().getChildren().get(i));
                        productBackingBean.getSelectedProductNode().setSelected(true);
                    }
                }
                if (productBackingBean.getSelectedProductNode() == null) {
                    productBackingBean.setSelectedProductNode(productBackingBean.getProductRoot().getChildren().get(0)) ;
                    productBackingBean.getSelectedProductNode().setSelected(true);
                }
                refreshProducts();
            }
        }
        return productBackingBean.getProductRoot();
    }

    public List<Product> getProducts(){
        if(productBackingBean.getProducts()==null){
            refreshProducts();
        }
        return productBackingBean.getProducts();
    }

    public List<TreeNode> getNodeList(){
        refreshGroupNodesList();
        return productBackingBean.getProductGroupsNodeList();
    }

    public void refreshGroupNodesList(){
        productBackingBean.setProductGroupsNodeList(productGroupService.getExpandedProductGroupTreeNodeToList(fillProductsGroupNode()));
    }

    public void refreshProducts(){
        if (productBackingBean.getSelectedProductNode().getData() != null) {
            productBackingBean.setProducts(productService.getProductsByGroup(productBackingBean.getSelectedProductNode()));
        }else{
            productBackingBean.getProducts().clear();
        }
    }

    public TreeNode fillProductsGroupNode(){
        if(productBackingBean.getProductRoot()==null){
            initializeProductRoot();
        }
        return productBackingBean.getProductRoot();
    }

    public LineChartModel getProductChartCategoryModel(){
        return initializeProductCategoryModel(productBackingBean.getProductChart());
    }

    public LineChartModel initializeProductCategoryModel(Product product){
        return productService.drawProductLineChart(product);
    }

    public void initializeProductRoot(){
        ProductGroup productGroup= new ProductGroup();
        productGroup.setTitle("Все группы");
        productGroup.setId(new Long(-1));
        productBackingBean.setAllProductGroup(productGroup);

        productBackingBean.setProductRoot(productGroupService.fillTreeNodeValues(productBackingBean.getProductGroups(),productBackingBean.getAllProductGroup()
                ,productBackingBean.getProductRoot()));

    }

    public void onDragDropTreeProductGroups(TreeDragDropEvent event){
        ProductGroup dragGroup = (ProductGroup) event.getDragNode().getData();
        ProductGroup dropGroup = (ProductGroup) event.getDropNode().getData();
        int drop_index = event.getDropIndex();
        dragGroup.setParentId(dragGroup.getId());
        List<TreeNode> productGroupChildNodes = event.getDropNode().getChildren();
        for (int i = 0; i < productGroupChildNodes.size(); i++) {
            ProductGroup child_group = (ProductGroup) productGroupChildNodes.get(i).getData();
            child_group.setTurn((long) i);
            productGroupService.save(child_group);
        }
        LOGGER.info("DragAndDrop " + event.getSource() + " from " + dropGroup.getTitle() + " to " + dropGroup.getTitle() + " index " + drop_index);
    }

    public ShipmentBasis getShipmentBasisById(Long baseId) {
        if (baseId != null) {
            if (baseId.equals((long) 0)) {
                return productBackingBean.getSelectedProductDefaultShipmentBasis();
            }
            return shipmentBasisService.findById(baseId);
        }
        return null;
    }

    public void setAllOldProductPrices() {
        if (productBackingBean.getNewProductPrices() != null) {
            List<ProductPrice> productPrices =productBackingBean.getNewProductPrices();
            for (ProductPrice newProductPrice: productPrices) {
                setOldPrice(newProductPrice);
            }
            productBackingBean.setNewProductPrices(productPrices);
        }
    }

    public void setOldPrice(ProductPrice price) {
        if (price != null) {
            if (price.getProduct() != null) {
                ProductPrice productPrice=productPriceService.getProductActualPrice(price.getProduct().getProductPricesList());
                if (productPrice != null) {
                    price.setPrice(productPrice.getPrice());
                }
            }
        }
    }

    public void switchReorderEvent() {
        LOGGER.info("Switch Reorder " + productBackingBean.getProductGroupSwitchOrder());
    }

    public int getFieldButtonPriceTabIndex() {
        productBackingBean.setFieldButtonPriceTabIndex(productBackingBean.getFieldButtonPriceTabIndex()+1);
        return productBackingBean.getFieldButtonPriceTabIndex();
    }

    public int getFieldNewPriceTabIndex() {
        productBackingBean.setFieldNewPriceTabIndex(productBackingBean.getFieldNewPriceTabIndex()+1);
        return productBackingBean.getFieldNewPriceTabIndex();
    }

    public void onRowProductsReorder(ReorderEvent reorderEvent){
        LOGGER.info("D&D " + reorderEvent.getSource() + " from " + reorderEvent.getFromIndex() + " to " + reorderEvent.getToIndex());
        for (int i = 0; i < productBackingBean.getProducts().size(); i++) {
            productBackingBean.getProducts().get(i).setTurn((long) i);
            productService.save(productBackingBean.getProducts().get(i));
        }
    }

    public void eventRowDoubleSelect(SelectEvent selectEvent){
        productBackingBean.setEditProduct(productBackingBean.getSelectedProduct());
    }

    public void refreshProductChart(Product product){
        LOGGER.info("Set product for Product chart");
        LOGGER.info(product.toString());
        productBackingBean.setProductChart(product);
    }

    public void changeProductsOfSelectedGroup(){
        refreshProducts();
    }

    public void buttonAddNewGroup(ActionEvent actionEvent) {
        if (productBackingBean.getSelectedProductNode() != null) {
            ProductGroup parentGroup;
            TreeNode parentNode = productBackingBean.getSelectedProductNode();
            ProductGroup group_root = (ProductGroup) getRoot().getData();
            if (productBackingBean.getSelectedProductNode().getData() == null) {
                parentGroup = group_root;
                parentNode = getRoot();
            } else {
                parentGroup = (ProductGroup) productBackingBean.getSelectedProductNode().getData();

                if ((productBackingBean.getSelectedProductNode().getData()) == productBackingBean.getAllProductGroup()) {
                    parentGroup = group_root;
                    parentNode = getRoot();
                }
            }
            ProductGroup group_last = null;
            if (productBackingBean.getSelectedProductNode().getChildCount() > 0) {
                group_last = (ProductGroup) productBackingBean.getSelectedProductNode().getChildren().get(productBackingBean.getSelectedProductNode().getChildCount() - 1).getData();
            }
            if (parentGroup.getId() != null) {
                ProductGroup newProductGroup = new ProductGroup();
                newProductGroup.setTitle("Новая группа");
                newProductGroup.setParentId(parentGroup.getId());
                if (group_last != null) {
                    if (group_last.getTurn() != null) {
                        newProductGroup.setTurn(group_last.getTurn() + 1);
                    }
                }
                if (newProductGroup.getTurn() == null) {
                    newProductGroup.setTurn(new Long(1));
                }
                TreeNode newNode = productGroupService.addNewGroupToTreeNode(parentNode, newProductGroup);
                productBackingBean.getSelectedProductNode().setExpanded(true);
                productBackingBean.getSelectedProductNode().setSelected(false);
//                productBackingBean.setSelectedProductNode(newNode);
                newNode.setSelected(true);

                productGroupService.save(newProductGroup);
            }
            LOGGER.info("ADD GROUP " + parentGroup.getId() + " " + productBackingBean.getSelectedProductNode().getData());
            List<TreeNode> numberOfChilds = productBackingBean.getSelectedProductNode().getChildren();
            for (int i = 0; i < numberOfChilds.size(); i++) {
                ProductGroup groupChild = (ProductGroup) numberOfChilds.get(i).getData();
                groupChild.setTurn((long) i);
                productGroupService.save(groupChild);
            }
        }
    }

    public void buttonActionRemoveGroup(ActionEvent actionEvent){
        if (productBackingBean.getSelectedProductNode() != null) {
            if (productBackingBean.getSelectedProductNode().getData() != null) {
                ProductGroup productGroup = (ProductGroup) productBackingBean.getSelectedProductNode().getData();

                if (productGroup != productBackingBean.getAllProductGroup()) {
                    removeGroup((ProductGroup) productBackingBean.getSelectedProductNode().getData());

                    TreeNode nodeParent = productBackingBean.getSelectedProductNode().getParent();
                    nodeParent.getChildren().remove(productBackingBean.getSelectedProductNode());

                    productBackingBean.setSelectedProductNode(nodeParent);
                    nodeParent.setExpanded(true);
                    nodeParent.setSelected(true);
                }
            }
        }
    }

    public void removeGroup(ProductGroup productGroup){
        if (productGroup != null) {
            if (productGroup != productBackingBean.getAllProductGroup()) {
                List<ProductGroup> productGroups = productGroupService.getProductGroupsByParentId(productGroup.getId());
                for (ProductGroup removedProductGroup:productGroups) {
                    removeGroup(removedProductGroup);
                }
                List<Product> groupProducts=productService.getProductsByGroupId(productGroup.getId());
                for (Product groupProduct:groupProducts) {
                    if(groupProduct!=null) {
                        productService.deleteById(groupProduct.getId());
                        LOGGER.info("REMOVE " + groupProduct.getNameShort());
                    }
                }
                for(Product product:productService.getAllProducts()){
                    if(product.getGroupId().getId().equals(productGroup.getId())){
                        product.setGroupId(productBackingBean.getProductGroups().get(0));
                        productService.save(product);
                    }
                }
                LOGGER.info("REMOVE " + productGroup.getTitle());
                productGroupService.deleteById(productGroup.getId());
            }
        }
    }

    public void buttonActionSaveEditGroup(ActionEvent actionEvent){
        if (productBackingBean.getSelectedProductNode() != null) {
            if (productBackingBean.getSelectedProductNode().getData() != null) {
                productGroupService.save((ProductGroup) productBackingBean.getSelectedProductNode().getData());
            }
        }
    }

    public void editProduct(Product product){
        productBackingBean.setSelectedProduct(product);
        productBackingBean.setEditProduct(productBackingBean.getSelectedProduct());
    }

    public void buttonActionNewPrice(ActionEvent actionEvent){
        LOGGER.info(productBackingBean.getEditProduct().toString());
        if (productBackingBean.getEditProduct() != null) {
            ProductPrice price = new ProductPrice();
            price.setProduct(productBackingBean.getEditProduct());
            price.setTimeModified(new Date(System.currentTimeMillis()));

            price.setCreator_user_id(loginController.getCurrentUser());

            productBackingBean.getEditProduct().getProductPrices().add(price);
            productBackingBean.setSelectedProductPrice(price);

            DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent(":form_good_edit:good_prices");
            dataTable.reset();
        }
    }

    public void buttonActionRemovePrice(ActionEvent actionEvent){
        if (productBackingBean.getSelectedProductPrice() != null) {
            productBackingBean.getEditProduct().getProductPrices().remove(productBackingBean.getSelectedProductPrice());
        }
    }

    public void buttonActionSaveEditProduct(ActionEvent actionEvent) {
        if (productBackingBean.getEditProduct() != null) {
            if (productBackingBean.getEditProduct().isNewState()) {
                productBackingBean.getEditProduct().setNewState(false);
            }
            productService.save(productBackingBean.getEditProduct());
        }
        refreshProducts();
    }

    public void buttonActionCancelEditProduct(ActionEvent actionEvent) {
        if (productBackingBean.getEditProduct() != null) {
            if (productBackingBean.getEditProduct().isNewState()) {
                productService.deleteById(productBackingBean.getEditProduct().getId());
            }
        }
        refreshProducts();
    }

    public void buttonActionBackEditProduct(ActionEvent actionEvent) {
        refreshProducts();
    }

    public void buttonActionNewProduct(ActionEvent actionEvent) {
        LOGGER.info(productBackingBean.getSelectedProductNode().toString());
        if (productBackingBean.getSelectedProductNode().getData() != null) {
            ProductGroup productGroup= (ProductGroup) productBackingBean.getSelectedProductNode().getData();
            Product newProduct = new Product();
            newProduct.setContractPrice(false);
            newProduct.setGroupId(productGroup);
            newProduct.setType(new Long(1));
            newProduct.setActive(true);
            newProduct.setNewState(true);
            newProduct.setMeasureUnit(productModelBean.getMeasureUnits().get(0));
            newProduct.setProductType(productModelBean.getProductTypes().get(0));
            newProduct.setShipmentBaseId(productModelBean.getShipmentBases().get(0));
            ProductPrice productPrice= new ProductPrice();
            productPrice.setProduct(newProduct);
            productPrice.setTimeModified(new Date(System.currentTimeMillis()));
            productPrice.setCreator_user_id(loginController.getCurrentUser());
            Set<ProductPrice> newProductPrices = new HashSet<>();
            newProductPrices.add(productPrice);
            newProduct.setProductPrices(newProductPrices);
            productBackingBean.setEditProduct(newProduct);
            refreshProducts();
        }
    }

    public void buttonActionRemoveProduct(ActionEvent actionEvent) {
        if (productBackingBean.getSelectedProduct() != null) {
            productService.deleteById(productBackingBean.getSelectedProduct().getId());
        }
        productBackingBean.setSelectedProduct(null);
        refreshProducts();
    }

    public List<ProductType> getProductTypes(){
        if(productModelBean.getPopupDialogProductTypes()==null) {
            refreshProductTypesTableInPopupDialog();
        }
        return productModelBean.getPopupDialogProductTypes();
    }

    public void onRowEditProductType(RowEditEvent event) {
        if (event.getObject() instanceof ProductType) {
            ProductType productType = (ProductType) event.getObject();
            productTypeService.save(productType);
        }
    }

    public void onRowCancelProductType(RowEditEvent event) {
    }

    public void refreshProductTypesTableInPopupDialog(){
        LOGGER.info("Refreshing ProductTypes table in popup dialog, filter according {} in popup dialog", productBackingBean.getPopupDialogSearchStringContractorByNameWork());
        productModelBean.setPopupDialogProductTypes(productTypeService.findAllProductTypesByNameWork(productBackingBean.getPopupDialogSearchStringContractorByNameWork()));
    }

    public void resetProductTypesTableInPopupDialog() {
        LOGGER.info("Reset ProductTypes table in popup dialog");
        productBackingBean.setPopupDialogSearchStringContractorByNameWork(null);
        productBackingBean.setPopupDialogSelectedProductType(null);
        refreshProductTypesTableInPopupDialog();
    }

    public void doubleClickSelectedRowInTableProductTypesPopupDialog(FacesEvent event){
        setProductTypeForSelectedProduct(productBackingBean.getPopupDialogSelectedProductType());
    }

    public void setProductTypeForSelectedProduct(ProductType productType) {
        productBackingBean.getEditProduct().setProductType(productType);
        productBackingBean.setPopupDialogSearchStringContractorByNameWork(null);
        productBackingBean.setPopupDialogSelectedProductType(null);
    }

    public void buttonActionNewProductType(ActionEvent actionEvent) {
        ProductType newProductType = new ProductType();
        newProductType.setTurn((long) productModelBean.getProductTypes().size());
        newProductType.setNameWork("Новый вид товара");
        productTypeService.save(newProductType);
        productBackingBean.setPopupDialogSelectedProductType(newProductType);
        productModelBean.setPopupDialogProductTypes(productTypeService.getProductTypesByIdInDescendingOrder());
    }

    public void buttonActionRemoveProductType(ActionEvent actionEvent) {
        if (productBackingBean.getPopupDialogSelectedProductType() != null) {
            for(Product product:productService.getAllProducts()){
                if(product.getProductType().getId().equals(productBackingBean.getPopupDialogSelectedProductType().getId())){
                    product.setProductType(productModelBean.getProductTypes().get(0));
                    productService.save(product);
                }
            }
            productTypeService.deleteById(productBackingBean.getPopupDialogSelectedProductType().getId());
            productModelBean.setPopupDialogProductTypes(productTypeService.getProductTypesByIdInDescendingOrder());
        }
    }

    public void setSelectedProductType(ProductType productType){
        productBackingBean.setPopupDialogSelectedProductType(productType);
    }

    public List<ShipmentBasis> getProductShipmentBases(){
        if(productModelBean.getPopupDialogShipmentBasis()==null){
            refreshShipmentBasisTableInPopupDialog();
        }
        return productModelBean.getPopupDialogShipmentBasis();
    }

    public void onRowEditShipmentBase(RowEditEvent event) {
        if (event.getObject() instanceof ShipmentBasis) {
            ShipmentBasis shipmentBasis = (ShipmentBasis) event.getObject();
            shipmentBasisService.save(shipmentBasis);
        }
    }

    public void onRowCancelShipmentBase(RowEditEvent event) {
    }

    public void refreshShipmentBasisTableInPopupDialog(){
        LOGGER.info("Refreshing ShipmentBasis table in popup dialog, filter according {} in popup dialog", productBackingBean.getPopupDialogSearchStringShipmentBaseByNameShort());
        productModelBean.setPopupDialogShipmentBasis(shipmentBasisService.findAllShipmentBasisByNameShort(productBackingBean.getPopupDialogSearchStringShipmentBaseByNameShort()));
    }

    public void resetShipmentBasisTableInPopupDialog() {
        LOGGER.info("Reset ShipmentBasis table in popup dialog");
        productBackingBean.setPopupDialogSearchStringShipmentBaseByNameShort(null);
        productBackingBean.setPopupDialogSelectedShipmentBase(null);
        refreshShipmentBasisTableInPopupDialog();
    }

    public void doubleClickSelectedRowInTableShipmentBasisPopupDialog(FacesEvent event){
        setShipmentBaseForSelectedProduct(productBackingBean.getPopupDialogSelectedShipmentBase());
    }

    public void setShipmentBaseForSelectedProduct(ShipmentBasis shipmentBasis) {
        productBackingBean.getEditProduct().setShipmentBaseId(shipmentBasis);
        productBackingBean.setPopupDialogSearchStringShipmentBaseByNameShort(null);
        productBackingBean.setPopupDialogSelectedShipmentBase(null);
    }

    public void buttonActionNewShipmentBase(ActionEvent actionEvent) {
        ShipmentBasis shipmentBasis = new ShipmentBasis();
        shipmentBasis.setTurn((long) productModelBean.getShipmentBases().size());
        shipmentBasis.setNameShort("Новая база отгрузки");
        shipmentBasisService.save(shipmentBasis);
        productBackingBean.setPopupDialogSelectedShipmentBase(shipmentBasis);
        productModelBean.setPopupDialogShipmentBasis(shipmentBasisService.getShipmentBasisByIdInDescendingOrder());
    }

    public void buttonActionRemoveShipmentBase(ActionEvent actionEvent) {
        if (productBackingBean.getPopupDialogSelectedShipmentBase() != null) {
            for(Product product:productService.getAllProducts()){
                if(product.getShipmentBaseId().getId().equals(productBackingBean.getPopupDialogSelectedShipmentBase().getId())){
                    product.setShipmentBaseId(productModelBean.getShipmentBases().get(0));
                    productService.save(product);
                }
            }
            shipmentBasisService.deleteById(productBackingBean.getPopupDialogSelectedShipmentBase().getId());
            productModelBean.setPopupDialogShipmentBasis(shipmentBasisService.getShipmentBasisByIdInDescendingOrder());
        }
    }

    public void setSelectedShipmentBase(ShipmentBasis shipmentBase){
        productBackingBean.setPopupDialogSelectedShipmentBase(shipmentBase);
    }

    public void deleteNewProductPrice(ProductPrice price) {
        if (price != null && productBackingBean.getNewProductPrices() != null) {
            productBackingBean.getNewProductPrices().remove(price);
        }
    }

    public void buttonActionSaveNewProductPrices(ActionEvent actionEvent) {
        if (productBackingBean.getNewProductPrices() != null) {
            MessageProductPricesUtil messageProductPricesUtil = new MessageProductPricesUtil();
            String messageToSend= messageProductPricesUtil.createHTMLMessageToTelegram(productBackingBean.getNewProductPrices(), productBackingBean.getProductGroups());
            for (ProductPrice productPrice:productBackingBean.getNewProductPrices()) {
                productPrice.setTimeModified(productBackingBean.getNewPriceData());
                productPrice.getProduct().getProductPrices().add(productPrice);
                productPriceService.save(productPrice);
            }
            if(productBackingBean.getNewPriceNotify()){
                MessagePostTelegramUtil messagePostTelegramUtil = new MessagePostTelegramUtil();
                messagePostTelegramUtil.MpTelegramUtilSend(messageToSend, BotConfiguration.LIK_PRODUCTS_BOT_TOKEN);
            }
        }

        LOGGER.info("ACTION SAVE NEW PRICES " + productBackingBean.getNewPriceNotify() + " " + productBackingBean.getNewPriceData());
    }

    public void buttonActionCancelSaveNewPrices(ActionEvent actionEvent) {
        LOGGER.info("ACTION CANCEL SAVE NEW PRICES");
    }

    public void buttonActionNewPrices(ActionEvent actionEvent) {
        productBackingBean.setNewProductPrices(new ArrayList<>());

        if (getProducts() != null) {
            productBackingBean.setNewPriceNotify(true);
            productBackingBean.setNewPriceData(new Date(System.currentTimeMillis()));
            for (Product product:getProducts()) {
                if (product.isActive()) {
                    ProductPrice newPrice = new ProductPrice();
                    newPrice.setProduct(product);
                    newPrice.setTimeModified(productBackingBean.getNewPriceData());
                    newPrice.setCreator_user_id(loginController.getCurrentUser());
                    productBackingBean.getNewProductPrices().add(newPrice);
                }
            }
            setAllOldProductPrices();
        }

    }
}

