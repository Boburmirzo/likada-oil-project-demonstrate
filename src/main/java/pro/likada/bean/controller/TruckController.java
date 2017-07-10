package pro.likada.bean.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.likada.bean.backing.TruckBackingBean;
import pro.likada.bean.model.TruckModelBean;
import pro.likada.model.Truck;
import pro.likada.service.TruckService;
import pro.likada.util.AccessTypeEnum;
import pro.likada.util.ModelConstantEnum;

import javax.enterprise.context.RequestScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * Created by abuca on 06.03.17.
 */
@Named
@RequestScoped
public class TruckController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TruckController.class);

    @Inject
    private TruckBackingBean truckBackingBean;
    @Inject
    private TruckModelBean truckModelBean;
    @Inject
    private TruckService truckService;
    @Inject
    private LoginController loginController;

    public void refreshTrucksTable(){
        LOGGER.info("Refreshing Trucks table");
        truckBackingBean.setTruckList(truckService.findAllTrucks(null));
    }

    public void makeNewSelectedTruck(ActionEvent actionEvent){
        LOGGER.info("Set selected truck to new");
        if(!loginController.hasAccessTo(ModelConstantEnum.TRUCK, AccessTypeEnum.ADD)){
            LOGGER.info("Cannot add new Truck, User: {} has not permission",loginController.getCurrentUser());
            return;
        }
        Truck truck = new Truck();
        truckBackingBean.setSelectedTruck(truck);
    }

    public void selectTruck(Truck truck){
        LOGGER.info("Truck {} is selected now", truck);
        truckBackingBean.setSelectedTruck(truck);
    }

    public void deleteSelectedTruck(){
        LOGGER.info("Delete truck: {} ", truckBackingBean.getSelectedTruck());
        if(!loginController.hasAccessTo(ModelConstantEnum.TRUCK, AccessTypeEnum.DELETE)){
            LOGGER.info("Cannot delete Truck: {}, User: {} has not permission",truckBackingBean.getSelectedTruck(),loginController.getCurrentUser());
            return;
        }
        truckService.deleteById(truckBackingBean.getSelectedTruck().getId());
        if(truckBackingBean.getFiltredTruckList() != null){
            if(truckBackingBean.getFiltredTruckList().contains(truckBackingBean.getSelectedTruck())){
                truckBackingBean.getFiltredTruckList().remove(truckBackingBean.getSelectedTruck());
            }
        }
        truckBackingBean.setSelectedTruck(null);
        refreshTrucksTable();
    }

    public void buttonActionSaveTruck(ActionEvent actionEvent){
        LOGGER.info("Save the truck: {}", truckBackingBean.getSelectedTruck());
        if(truckBackingBean.getSelectedTruck().getId()== null && !loginController.hasAccessTo(ModelConstantEnum.TRUCK, AccessTypeEnum.ADD)){
            LOGGER.info("Cannot save new Truck: {}, User: {} has not permission",truckBackingBean.getSelectedTruck(),loginController.getCurrentUser());
            return;
        }
        if(truckBackingBean.getSelectedTruck().getId()!= null && !loginController.hasAccessTo(ModelConstantEnum.TRUCK, AccessTypeEnum.EDIT)){
            LOGGER.info("Cannot edit Truck: {}, User: {} has not permission",truckBackingBean.getSelectedTruck(),loginController.getCurrentUser());
            return;
        }
        truckService.save(truckBackingBean.getSelectedTruck());
        refreshTrucksTable();
    }

    public List<Truck> getTrucks(){
        if(truckBackingBean.getTruckList()==null) {
            LOGGER.info("Unable to load trucks from bean, load from database.");
            refreshTrucksTable();
        }
        return truckBackingBean.getTruckList();
    }

    public Boolean isTruckConnectedWithAnyVehicle(Truck truck){
        LOGGER.info("Check is Truck connected with any vehicle, Truck {}", truck);
        return truckService.findAmountOfVehiclesConnectedWithTruck(truck) > 0;
    }
}
