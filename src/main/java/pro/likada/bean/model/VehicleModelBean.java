package pro.likada.bean.model;

import pro.likada.model.Vehicle;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Created by abuca on 07.03.17.
 */
@Named
@SessionScoped
public class VehicleModelBean implements Serializable {
    private Vehicle selectedVehicle;

    private String sortingField;

    @PostConstruct
    public void init(){
        this.sortingField = "truck.title";
    }


    public String getSortingField() {
        return sortingField;
    }

    public void setSortingField(String sortingField) {
        this.sortingField = sortingField;
    }

    public Vehicle getSelectedVehicle() {
        return selectedVehicle;
    }

    public void setSelectedVehicle(Vehicle selectedVehicle) {
        this.selectedVehicle = selectedVehicle;
    }
}
