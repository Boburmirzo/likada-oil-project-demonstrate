package pro.likada.bean.backing;

import pro.likada.bean.model.TruckModelBean;
import pro.likada.model.Truck;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * Created by abuca on 06.03.17.
 */
@Named
@ViewScoped
public class TruckBackingBean implements Serializable {
    @Inject
    private TruckModelBean truckModelBean;

    private Truck selectedTruck;
    private List<Truck> truckList;
    private List<Truck> filtredTruckList;

    private String searchString;


    @PostConstruct
    public void init(){
        if(truckModelBean.getSelectedTruck() != null){
            this.selectedTruck = truckModelBean.getSelectedTruck();
        }
    }

    public Truck getSelectedTruck() {
        return selectedTruck;
    }

    public void setSelectedTruck(Truck selectedTruck) {
        this.selectedTruck = selectedTruck;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public List<Truck> getTruckList() {
        return truckList;
    }

    public void setTruckList(List<Truck> truckList) {
        this.truckList = truckList;
    }

    public List<Truck> getFiltredTruckList() {
        return filtredTruckList;
    }

    public void setFiltredTruckList(List<Truck> filtredTruckList) {
        this.filtredTruckList = filtredTruckList;
    }
}
