package pro.likada.bean.model;

import pro.likada.model.VehicleStatus;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Created by abuca on 14.03.17.
 */
@Named
@SessionScoped
public class VehicleStatusModelBean implements Serializable {
    private VehicleStatus selectedStatus;

    private String sortingField;

    @PostConstruct
    public void init(){
        this.sortingField = "status.statusDate";
    }

    public String getSortingField() {
        return sortingField;
    }

    public void setSortingField(String sortingField) {
        this.sortingField = sortingField;
    }

    public VehicleStatus getSelectedStatus() {
        return selectedStatus;
    }

    public void setSelectedStatus(VehicleStatus selectedStatus) {
        this.selectedStatus = selectedStatus;
    }
}
