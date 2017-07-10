package pro.likada.bean.backing;

import pro.likada.bean.model.VehicleModelBean;
import pro.likada.model.Driver;
import pro.likada.model.Vehicle;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * Created by abuca on 07.03.17.
 */
@Named
@ViewScoped
public class VehicleBackingBean implements Serializable {
    @Inject
    private VehicleModelBean vehicleModelBean;

    private Vehicle selectedVehicle;
    private Driver selectedDriverForVehicleInEditPage;
    private Driver selectedDriverForPopupDialog;
    private String searchString;

    private List<Vehicle> vehicleList;
    private List<Vehicle> filtredVehicleList;
    private List<Driver> driverOfSelectedVehicleList;
    private List<Driver> driverNotConnectedWithSelectedVehicleList;
    private List<Driver> filtredDriverNotConnectedWithSelectedVehicleList;

    @PostConstruct
    public void init(){
        if(vehicleModelBean.getSelectedVehicle() != null){
            this.selectedVehicle = vehicleModelBean.getSelectedVehicle();
        }
    }

    public Vehicle getSelectedVehicle() {
        return selectedVehicle;
    }

    public void setSelectedVehicle(Vehicle selectedVehicle) {
        this.selectedVehicle = selectedVehicle;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public Driver getSelectedDriverForVehicleInEditPage() {
        return selectedDriverForVehicleInEditPage;
    }

    public void setSelectedDriverForVehicleInEditPage(Driver selectedDriverForVehicleInEditPage) {
        this.selectedDriverForVehicleInEditPage = selectedDriverForVehicleInEditPage;
    }

    public Driver getSelectedDriverForPopupDialog() {
        return selectedDriverForPopupDialog;
    }

    public void setSelectedDriverForPopupDialog(Driver selectedDriverForPopupDialog) {
        this.selectedDriverForPopupDialog = selectedDriverForPopupDialog;
    }

    public List<Vehicle> getVehicleList() {
        return vehicleList;
    }

    public void setVehicleList(List<Vehicle> vehicleList) {
        this.vehicleList = vehicleList;
    }

    public List<Driver> getDriverOfSelectedVehicleList() {
        return driverOfSelectedVehicleList;
    }

    public void setDriverOfSelectedVehicleList(List<Driver> driverOfSelectedVehicleList) {
        this.driverOfSelectedVehicleList = driverOfSelectedVehicleList;
    }

    public List<Driver> getDriverNotConnectedWithSelectedVehicleList() {
        return driverNotConnectedWithSelectedVehicleList;
    }

    public void setDriverNotConnectedWithSelectedVehicleList(List<Driver> driverNotConnectedWithSelectedVehicleList) {
        this.driverNotConnectedWithSelectedVehicleList = driverNotConnectedWithSelectedVehicleList;
    }

    public List<Driver> getFiltredDriverNotConnectedWithSelectedVehicleList() {
        return filtredDriverNotConnectedWithSelectedVehicleList;
    }

    public void setFiltredDriverNotConnectedWithSelectedVehicleList(List<Driver> filtredDriverNotConnectedWithSelectedVehicleList) {
        this.filtredDriverNotConnectedWithSelectedVehicleList = filtredDriverNotConnectedWithSelectedVehicleList;
    }

    public List<Vehicle> getFiltredVehicleList() {
        return filtredVehicleList;
    }

    public void setFiltredVehicleList(List<Vehicle> filtredVehicleList) {
        this.filtredVehicleList = filtredVehicleList;
    }
}
