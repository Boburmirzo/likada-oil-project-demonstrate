package pro.likada.bean.controller;

import org.primefaces.event.SelectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.likada.bean.backing.DriverBackingBean;
import pro.likada.bean.model.DriverModelBean;
import pro.likada.model.Driver;
import pro.likada.model.Vehicle;
import pro.likada.service.DriverService;
import pro.likada.util.AccessTypeEnum;
import pro.likada.util.ModelConstantEnum;

import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.List;

/**
 * Created by abuca on 13.03.17.
 */
@Named
@RequestScoped
public class DriverController {
    private static final Logger LOGGER = LoggerFactory.getLogger(VehicleController.class);

    @Inject
    private DriverBackingBean driverBackingBean;
    @Inject
    private DriverModelBean driverModelBean;
    @Inject
    private DriverService driverService;
    @Inject
    private LoginController loginController;

    public void refreshDriversTable(){
        LOGGER.info("Refreshing Vehicles table");
        driverBackingBean.setDriverList(driverService.findAllDrivers(null));
    }

    public void deleteSelectedDriver(){
        LOGGER.info("Delete Driver: {}", driverBackingBean.getSelectedDriver());
        if(!loginController.hasAccessTo(ModelConstantEnum.DRIVER, AccessTypeEnum.DELETE)){
            LOGGER.info("Cannot delete Driver: {}, User: {} has not permission",driverBackingBean.getSelectedDriver(),loginController.getCurrentUser());
            return;
        }
        driverService.deleteById(driverBackingBean.getSelectedDriver().getId());
        if(driverBackingBean.getFiltredDriverList() != null) {
            if (driverBackingBean.getFiltredDriverList().contains(driverBackingBean.getSelectedDriver())) {
                driverBackingBean.getFiltredDriverList().remove(driverBackingBean.getSelectedDriver());
            }
        }
        driverBackingBean.setSelectedDriver(null);
    }

    public List<Driver> getDrivers(){
        if(driverBackingBean.getDriverList()==null) {
            LOGGER.info("Unable to load drivers from bean, load from database.");
            refreshDriversTable();
        }
        return driverBackingBean.getDriverList();
    }

    public List<Vehicle> getVehiclesConnectedWithDriver(){
        if(driverBackingBean.getVehiclesConnectedWithDriver() == null){
            driverBackingBean.setVehiclesConnectedWithDriver(driverBackingBean.getSelectedDriver().getVehicleList());
        }
        return driverBackingBean.getVehiclesConnectedWithDriver();
    }

    public void buttonActionSaveDriver(ActionEvent actionEvent){
        LOGGER.info("Save the Driver: {}", driverBackingBean.getSelectedDriver());
        if(driverBackingBean.getSelectedDriver().getId() == null && !loginController.hasAccessTo(ModelConstantEnum.DRIVER, AccessTypeEnum.ADD)){
            LOGGER.info("Cannot save new Driver: {}, User: {} has not permission",driverBackingBean.getSelectedDriver(),loginController.getCurrentUser());
            return;
        }
        if(driverBackingBean.getSelectedDriver().getId() != null && !loginController.hasAccessTo(ModelConstantEnum.DRIVER, AccessTypeEnum.EDIT)){
            LOGGER.info("Cannot edit Driver: {}, User: {} has not permission",driverBackingBean.getSelectedDriver(),loginController.getCurrentUser());
            return;
        }
        driverService.save(driverBackingBean.getSelectedDriver());
        redirectToDriversPage();
    }

    public void buttonActionCancelSaveDriver(){
        LOGGER.info("Cancel saving the Driver {}", driverBackingBean.getSelectedDriver());
        driverBackingBean.setSelectedDriver(null);
        redirectToDriversPage();
    }

    public void buttonActionAddDriver(ActionEvent actionEvent){
        if(!loginController.hasAccessTo(ModelConstantEnum.DRIVER, AccessTypeEnum.ADD)){
            LOGGER.info("Cannot add new Driver, User: {} has not permission",driverBackingBean.getSelectedDriver(),loginController.getCurrentUser());
            return;
        }
        Driver driver = new Driver();
        editDriver(driver);
    }

    public void buttonActionEditSelectedDriver(SelectEvent selectEvent){
        if(!loginController.hasAccessTo(ModelConstantEnum.DRIVER, AccessTypeEnum.EDIT)){
            LOGGER.info("Cannot edit Driver: {}, User: {} has not permission",driverBackingBean.getSelectedDriver(),loginController.getCurrentUser());
            return;
        }
        editDriver(driverBackingBean.getSelectedDriver());
    }

    public void editDriver(Driver driver){
        driverModelBean.setSelectedDriver(driver);
        redirectToDriverEditPage();
    }

    public Boolean isDriverConnectedWithAnyVehicleWithDriver(Driver driver){
        LOGGER.info("Check is Driver connected with any vehicle with driver, Driver: {}", driver);
        return driverService.findAmountOfVehiclesWithDriversConnectedWithDriver(driver) > 0;
    }

    public void redirectToDriversPage(){
        LOGGER.info("Redirecting to Drivers page");
        try{
            refreshDriversTable();
            FacesContext.getCurrentInstance().getExternalContext().redirect("drivers.xhtml");
        }
        catch (IOException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
    }

    public void redirectToDriverEditPage(){
        LOGGER.info("Redirecting to Driver edit page");
        try{
            FacesContext.getCurrentInstance().getExternalContext().redirect("edit_driver.xhtml");
        }
        catch (IOException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
    }

}
