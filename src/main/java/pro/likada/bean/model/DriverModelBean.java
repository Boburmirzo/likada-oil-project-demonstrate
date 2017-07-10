package pro.likada.bean.model;

import pro.likada.model.Driver;
import pro.likada.service.DriverService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Created by abuca on 13.03.17.
 */
@Named
@SessionScoped
public class DriverModelBean implements Serializable{
    @Inject
    private DriverService driverService;

    private String sortingField;

    @PostConstruct
    public void init(){
        this.sortingField = "title";
    }

    private Driver selectedDriver;

    public Driver getSelectedDriver() {
        return selectedDriver;
    }

    public void setSelectedDriver(Driver selectedDriver) {
        this.selectedDriver = selectedDriver;
    }

    public String getSortingField() {
        return sortingField;
    }

    public void setSortingField(String sortingField) {
        this.sortingField = sortingField;
    }
}
