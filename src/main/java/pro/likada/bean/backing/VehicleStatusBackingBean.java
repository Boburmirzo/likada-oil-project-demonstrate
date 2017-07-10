package pro.likada.bean.backing;

import pro.likada.bean.model.VehicleStatusModelBean;
import pro.likada.model.Vehicle;
import pro.likada.model.VehicleMechanicalStatusType;
import pro.likada.model.VehicleStatus;
import pro.likada.service.VehicleMechanicalStatusTypeService;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by abuca on 14.03.17.
 */
@Named
@ViewScoped
public class VehicleStatusBackingBean implements Serializable {
    @Inject
    private VehicleStatusModelBean vehicleStatusModelBean;

    private VehicleStatus selectedStatus;
    private List<Vehicle> vehicleList;
    private List<Vehicle> filteredVehicleList;
    private List<String> mechanicalStatusTypeList;
    private List<String> selectedMechanicalStatusTypes; //TODO: rewrite with converter
    private String searchString;
    private boolean showOnlyVehiclesWhereLogistianIsYou;

    @PostConstruct
    public void init(){
        if(vehicleStatusModelBean.getSelectedStatus() != null){
            this.selectedStatus = vehicleStatusModelBean.getSelectedStatus();
        }
        this.selectedMechanicalStatusTypes = new ArrayList<>();
    }

    public VehicleStatus getSelectedStatus() {
        return selectedStatus;
    }

    public void setSelectedStatus(VehicleStatus selectedStatus) {
        this.selectedStatus = selectedStatus;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public List<Vehicle> getVehicleList() {
        return vehicleList;
    }

    public void setVehicleList(List<Vehicle> vehicleList) {
        this.vehicleList = vehicleList;
    }

    public List<Vehicle> getFilteredVehicleList() {
        return filteredVehicleList;
    }

    public void setFilteredVehicleList(List<Vehicle> filteredVehicleList) {
        this.filteredVehicleList = filteredVehicleList;
    }

    public boolean isShowOnlyVehiclesWhereLogistianIsYou() {
        return showOnlyVehiclesWhereLogistianIsYou;
    }

    public void setShowOnlyVehiclesWhereLogistianIsYou(boolean showOnlyVehiclesWhereLogistianIsYou) {
        this.showOnlyVehiclesWhereLogistianIsYou = showOnlyVehiclesWhereLogistianIsYou;
    }

    public List<String> getMechanicalStatusTypeList() {
        return mechanicalStatusTypeList;
    }

    public void setMechanicalStatusTypeList(List<String> mechanicalStatusTypeList) {
        this.mechanicalStatusTypeList = mechanicalStatusTypeList;
    }

    public List<String> getSelectedMechanicalStatusTypes() {
        return selectedMechanicalStatusTypes;
    }

    public void setSelectedMechanicalStatusTypes(List<String> selectedMechanicalStatusTypes) {
        this.selectedMechanicalStatusTypes = selectedMechanicalStatusTypes;
    }
}
