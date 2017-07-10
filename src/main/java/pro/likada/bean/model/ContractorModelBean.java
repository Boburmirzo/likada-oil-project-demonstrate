package pro.likada.bean.model;

import pro.likada.model.*;
import pro.likada.service.*;
import pro.likada.util.ModelConstantEnum;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * Created by bumur on 28.12.2016.
 */
@Named
@SessionScoped
public class ContractorModelBean implements Serializable{

    @Inject
    private ContractorService contractorService;
    @Inject
    private ContractorOrganizationTypeService contractorOrganizationTypeService;
    @Inject
    private AddressRegionService addressRegionService;
    @Inject
    private AddressHouseTypeService addressHouseTypeService;
    @Inject
    private AddressBuildingTypeService addressBuildingTypeService;
    @Inject
    private AddressApartmentTypeService addressApartmentTypeService;

    private List<Contractor> contractorsForTable;

    /* Fields for edit contractor */
    private Contractor selectedContractor;

    private List<ContractorOrganizationType> contractorOrganizationTypeList;
    private List<AddressRegion> addressRegionList;
    private List<AddressHouseType> addressHouseTypeList;
    private List<AddressBuildingType> addressBuildingTypeList;
    private List<AddressApartmentType> addressApartmentTypeList;


    @PostConstruct
    public void init(){
        if(contractorsForTable==null)
            contractorService.findAllContractors(null, ModelConstantEnum.CONTRACTOR.getModelName());
        if(contractorOrganizationTypeList==null)
            this.contractorOrganizationTypeList = contractorOrganizationTypeService.getAllContractorOrganizationTypes();
        if(addressRegionList==null)
            this.addressRegionList = addressRegionService.getAddressRegions();
        if(addressHouseTypeList==null)
            this.addressHouseTypeList = addressHouseTypeService.getAllAddressHouseTypes();
        if(addressBuildingTypeList==null)
            this.addressBuildingTypeList = addressBuildingTypeService.getAllAddressBuildingTypes();
        if(addressApartmentTypeList==null)
            this.addressApartmentTypeList = addressApartmentTypeService.getAllAddressApartmentTypes();
    }

    public List<Contractor> getContractorsForTable() {
        return contractorsForTable;
    }

    public void setContractorsForTable(List<Contractor> contractorsForTable) {
        this.contractorsForTable = contractorsForTable;
    }

    public Contractor getSelectedContractor() {
        return selectedContractor;
    }

    public void setSelectedContractor(Contractor selectedContractor) {
        this.selectedContractor = selectedContractor;
    }

    public List<ContractorOrganizationType> getContractorOrganizationTypeList() {
        return contractorOrganizationTypeList;
    }

    public void setContractorOrganizationTypeList(List<ContractorOrganizationType> contractorOrganizationTypeList) {
        this.contractorOrganizationTypeList = contractorOrganizationTypeList;
    }

    public List<AddressRegion> getAddressRegionList() {
        return addressRegionList;
    }

    public void setAddressRegionList(List<AddressRegion> addressRegionList) {
        this.addressRegionList = addressRegionList;
    }

    public List<AddressHouseType> getAddressHouseTypeList() {
        return addressHouseTypeList;
    }

    public void setAddressHouseTypeList(List<AddressHouseType> addressHouseTypeList) {
        this.addressHouseTypeList = addressHouseTypeList;
    }

    public List<AddressBuildingType> getAddressBuildingTypeList() {
        return addressBuildingTypeList;
    }

    public void setAddressBuildingTypeList(List<AddressBuildingType> addressBuildingTypeList) {
        this.addressBuildingTypeList = addressBuildingTypeList;
    }

    public List<AddressApartmentType> getAddressApartmentTypeList() {
        return addressApartmentTypeList;
    }

    public void setAddressApartmentTypeList(List<AddressApartmentType> addressApartmentTypeList) {
        this.addressApartmentTypeList = addressApartmentTypeList;
    }
}
