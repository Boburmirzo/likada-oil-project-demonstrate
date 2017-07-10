package pro.likada.bean.backing;

import pro.likada.bean.model.DriverModelBean;
import pro.likada.model.Driver;
import pro.likada.model.Vehicle;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * Created by abuca on 13.03.17.
 */
@Named
@ViewScoped
public class DriverBackingBean implements Serializable {
    @Inject
    private DriverModelBean driverModelBean;

    private Driver selectedDriver;
    private List<Driver> driverList;
    private List<Driver> filtredDriverList;
    private List<Vehicle> vehiclesConnectedWithDriver;
    private String searchString;

    @PostConstruct
    public void init(){
        if(driverModelBean.getSelectedDriver() != null){
            this.selectedDriver = driverModelBean.getSelectedDriver();
        }
    }

    public Driver getSelectedDriver() {
        return selectedDriver;
    }

    public void setSelectedDriver(Driver selectedDriver) {
        this.selectedDriver = selectedDriver;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public List<Vehicle> getVehiclesConnectedWithDriver() {
        return vehiclesConnectedWithDriver;
    }

    public void setVehiclesConnectedWithDriver(List<Vehicle> vehiclesConnectedWithDriver) {
        this.vehiclesConnectedWithDriver = vehiclesConnectedWithDriver;
    }

    public List<Driver> getDriverList() {
        return driverList;
    }

    public void setDriverList(List<Driver> driverList) {
        this.driverList = driverList;
    }

    public List<Driver> getFiltredDriverList() {
        return filtredDriverList;
    }

    public void setFiltredDriverList(List<Driver> filtredDriverList) {
        this.filtredDriverList = filtredDriverList;
    }
}
