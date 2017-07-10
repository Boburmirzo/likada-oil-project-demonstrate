package pro.likada.bean.model;

import pro.likada.model.MeasureUnit;
import pro.likada.model.ProductGroup;
import pro.likada.model.ProductType;
import pro.likada.model.ShipmentBasis;
import pro.likada.service.MeasureUnitService;
import pro.likada.service.ProductGroupService;
import pro.likada.service.ProductTypeService;
import pro.likada.service.ShipmentBasisService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * Created by bumur on 13.02.2017.
 */
@Named
@SessionScoped
public class ProductModelBean implements Serializable{


    @Inject
    private MeasureUnitService measureUnitService;
    @Inject
    private ShipmentBasisService shipmentBasisService;
    @Inject
    private ProductTypeService productTypeService;


    private List<ProductType> productTypes;
    private List<ProductType> popupDialogProductTypes;

    private List<ShipmentBasis> shipmentBases;
    private List<ShipmentBasis> popupDialogShipmentBasis;

    private List<MeasureUnit> measureUnits;
    @PostConstruct
    public void init(){
       if(measureUnits==null){
            this.measureUnits=measureUnitService.getAllMeasureUnits();
        }
        if(shipmentBases==null){
            this.shipmentBases=shipmentBasisService.getAllShipmentBasis();
        }
        if(productTypes==null){
            this.productTypes=productTypeService.findAllProductTypesByNameWork(null);
        }
    }

    public List<MeasureUnit> getMeasureUnits() {
        return measureUnits;
    }

    public void setMeasureUnits(List<MeasureUnit> measureUnits) {
        this.measureUnits = measureUnits;
    }

    public List<ShipmentBasis> getShipmentBases() {
        return shipmentBases;
    }

    public void setShipmentBases(List<ShipmentBasis> shipmentBases) {
        this.shipmentBases = shipmentBases;
    }

    public List<ProductType> getProductTypes() {
        return productTypes;
    }

    public void setProductTypes(List<ProductType> productTypes) {
        this.productTypes = productTypes;
    }

    public List<ProductType> getPopupDialogProductTypes() {
        return popupDialogProductTypes;
    }

    public void setPopupDialogProductTypes(List<ProductType> popupDialogProductTypes) {
        this.popupDialogProductTypes = popupDialogProductTypes;
    }

    public List<ShipmentBasis> getPopupDialogShipmentBasis() {
        return popupDialogShipmentBasis;
    }

    public void setPopupDialogShipmentBasis(List<ShipmentBasis> popupDialogShipmentBasis) {
        this.popupDialogShipmentBasis = popupDialogShipmentBasis;
    }
}
