package pro.likada.bean.model;

import pro.likada.model.Truck;
import pro.likada.service.TruckService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Created by abuca on 06.03.17.
 */
@Named
@SessionScoped
public class TruckModelBean implements Serializable {
    @Inject
    private TruckService truckService;

    private String sortingField;

    @PostConstruct
    public void init(){
        this.sortingField = "title";
    }


    public String getSortingField() {
        return sortingField;
    }

    public void setSortingField(String sortingField) {
        this.sortingField = sortingField;
    }

    private Truck selectedTruck;

    public Truck getSelectedTruck() {
        return selectedTruck;
    }

    public void setSelectedTruck(Truck selectedTruck) {
        this.selectedTruck = selectedTruck;
    }
}
