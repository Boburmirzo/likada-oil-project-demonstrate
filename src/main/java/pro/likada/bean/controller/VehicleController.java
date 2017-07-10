package pro.likada.bean.controller;

import org.primefaces.event.SelectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.likada.bean.backing.VehicleBackingBean;
import pro.likada.bean.model.VehicleModelBean;
import pro.likada.bean.util.EnumBean;
import pro.likada.model.*;
import pro.likada.service.*;
import pro.likada.util.AccessTypeEnum;
import pro.likada.util.ModelConstantEnum;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.FacesEvent;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by abuca on 07.03.17.
 */
@Named
@RequestScoped
public class VehicleController {
    private static final Logger LOGGER = LoggerFactory.getLogger(VehicleController.class);

    @Inject
    private VehicleBackingBean vehicleBackingBean;
    @Inject
    private VehicleModelBean vehicleModelBean;
    @Inject
    private VehicleService vehicleService;
    @Inject
    private ContractorService contractorService;
    @Inject
    private UserService userService;
    @Inject
    private TruckService truckService;
    @Inject
    private TrailerService trailerService;
    @Inject
    private DriverService driverService;
    @Inject
    private LoginController loginController;

    public void refreshVehiclesTable(){
        LOGGER.info("Refreshing Vehicles table");
        vehicleBackingBean.setVehicleList(vehicleService.findAllVehicles(null));
    }

    public void refreshDriverListForSelectedVehicle(){
        LOGGER.info("Refreshing Drivers list for Vehicle: {}", vehicleBackingBean.getSelectedVehicle());
        Vehicle vehicle;
        if(vehicleBackingBean.getSelectedVehicle().getId() != null) {
            vehicle = vehicleService.findById(vehicleBackingBean.getSelectedVehicle().getId());
        }
        else {
            vehicle = vehicleBackingBean.getSelectedVehicle();
        }
        List<Driver> driverList = driverService.findAllDrivers(null);
        List<Driver> driversConnectedWithSelectedVehicle = driverList.stream()
                .filter(driver -> vehicle.getDriverList().contains(driver))
                .collect(Collectors.toList());
        List<Driver> driversNotConnectedWithSelectedVehicle = driverList.stream()
                .filter(driver -> !vehicle.getDriverList().contains(driver))
                .collect(Collectors.toList());
        vehicleBackingBean.setDriverOfSelectedVehicleList(driversConnectedWithSelectedVehicle);
        vehicleBackingBean.setDriverNotConnectedWithSelectedVehicleList(driversNotConnectedWithSelectedVehicle);
    }

    public void deleteSelectedVehicle() {
        LOGGER.info("Delete Trailer: {}", vehicleBackingBean.getSelectedVehicle());
        vehicleService.deleteById(vehicleBackingBean.getSelectedVehicle().getId());
        if(vehicleBackingBean.getFiltredVehicleList() != null){
            if(vehicleBackingBean.getFiltredVehicleList().contains(vehicleBackingBean.getSelectedVehicle())){
                vehicleBackingBean.getFiltredVehicleList().remove(vehicleBackingBean.getSelectedVehicle());
            }
        }
        vehicleBackingBean.setSelectedVehicle(null);
        refreshVehiclesTable();
    }

    public List<Vehicle> getVehicles(){
        if(vehicleBackingBean.getVehicleList()==null) {
            LOGGER.info("Unable to load vehicles from bean, load from database.");
            refreshVehiclesTable();
        }
        return vehicleBackingBean.getVehicleList();
    }

    public List<Driver> getDriverOfSelectedVehicleList(){
        LOGGER.info("Get drivers list for selected vehicle: {}",vehicleBackingBean.getSelectedVehicle());
        if(vehicleBackingBean.getDriverOfSelectedVehicleList()==null) {
            refreshDriverListForSelectedVehicle();
        }
        return vehicleBackingBean.getDriverOfSelectedVehicleList();
    }

    public List<Driver> getDriverNotConnectedWithSelectedVehicleList(){
        LOGGER.info("Get drivers list not connected with selected vehicle: {}",vehicleBackingBean.getSelectedVehicle());
        if(vehicleBackingBean.getDriverNotConnectedWithSelectedVehicleList()==null) {
            refreshDriverListForSelectedVehicle();
        }
        return vehicleBackingBean.getDriverNotConnectedWithSelectedVehicleList();
    }

    public List<Contractor> loadAllContractors(){
        LOGGER.info("Load all carriers from database.");
        return contractorService.findAllContractors(null, ModelConstantEnum.CARRIER.getModelName());
    }

    public List<User> loadAllLogicians(){
        LOGGER.info("Load all logicians from database.");
        return userService.findByRole(RoleEnum.LOGISTICS.getRoleType());
    }

    public List<User> loadAllTerminals(){
        LOGGER.info("Load all terminals from database.");
        return userService.findByRole(RoleEnum.TERMINAL_VEHICLE.getRoleType());
    }

    public void buttonActionSaveVehicle(ActionEvent actionEvent){
        LOGGER.info("Save the Vehicle: {}", vehicleBackingBean.getSelectedVehicle());
        if(vehicleBackingBean.getSelectedVehicle().getId()==null && !loginController.hasAccessTo(ModelConstantEnum.VEHICLE, AccessTypeEnum.ADD)){
            LOGGER.info("Cannot save new Vehicle: {}, User: {} has not permission",vehicleBackingBean.getSelectedVehicle(),loginController.getCurrentUser());
            return;
        }
        if(vehicleBackingBean.getSelectedVehicle().getId()!=null && !loginController.hasAccessTo(ModelConstantEnum.VEHICLE, AccessTypeEnum.EDIT)){
            LOGGER.info("Cannot edit Vehicle: {}, User: {} has not permission",vehicleBackingBean.getSelectedVehicle(),loginController.getCurrentUser());
            return;
        }
        vehicleService.save(vehicleBackingBean.getSelectedVehicle());
        redirectToVehiclesPage();
    }

    public void buttonActionCancelSaveVehicle(ActionEvent actionEvent){
        LOGGER.info("Cancel saving the Vehicle {}",  vehicleBackingBean.getSelectedVehicle());
        vehicleBackingBean.setSelectedVehicle(null);
        redirectToVehiclesPage();
    }

    public void buttonActionAddDriver(FacesEvent event){
        LOGGER.info("Add Driver: {}, to Vehicle: {}",vehicleBackingBean.getSelectedDriverForPopupDialog(),vehicleBackingBean.getSelectedVehicle());
        if(!loginController.hasAccessTo(ModelConstantEnum.VEHICLE, AccessTypeEnum.EDIT)){
            LOGGER.info("Cannot edit Vehicle: {}, User: {} has not permission",vehicleBackingBean.getSelectedVehicle(),loginController.getCurrentUser());
            return;
        }
        Driver driver = vehicleBackingBean.getSelectedDriverForPopupDialog();
        Vehicle vehicle = vehicleService.findById(vehicleBackingBean.getSelectedVehicle().getId());
        vehicle.getDrivers().add(driver);
        vehicleService.save(vehicle);
        vehicleBackingBean.setSelectedDriverForPopupDialog(null);
        refreshDriverListForSelectedVehicle();
    }

    public void buttonActionRemoveDriver(FacesEvent event){
        LOGGER.info("Remove Driver: {}, from Vehicle: {}",vehicleBackingBean.getSelectedDriverForVehicleInEditPage(),vehicleBackingBean.getSelectedVehicle());
        if(!loginController.hasAccessTo(ModelConstantEnum.VEHICLE, AccessTypeEnum.EDIT)){
            LOGGER.info("Cannot edit Vehicle: {}, User: {} has not permission",vehicleBackingBean.getSelectedVehicle(),loginController.getCurrentUser());
            return;
        }
        Driver driver = vehicleBackingBean.getSelectedDriverForVehicleInEditPage();
        Vehicle vehicle = vehicleService.findById(vehicleBackingBean.getSelectedVehicle().getId());
        vehicle.getDrivers().remove(driver);
        vehicleService.save(vehicle);
        vehicleBackingBean.setSelectedDriverForVehicleInEditPage(null);
        refreshDriverListForSelectedVehicle();
    }

    public void buttonActionAddVehicle(ActionEvent actionEvent) {
        LOGGER.info("Create new Vehicle");
        if(!loginController.hasAccessTo(ModelConstantEnum.VEHICLE, AccessTypeEnum.ADD)){
            LOGGER.info("Cannot save new Vehicle: {}, User: {} has not permission",vehicleBackingBean.getSelectedVehicle(),loginController.getCurrentUser());
            return;
        }
        Vehicle vehicle = new Vehicle();
        vehicle.setStatuses(new HashSet<>());
        vehicle.setDrivers(new HashSet<>());
        editVehicle(vehicle);
    }

    public void buttonActionEditSelectedVehicle(SelectEvent selectEvent) {
        if(!loginController.hasAccessTo(ModelConstantEnum.VEHICLE, AccessTypeEnum.EDIT)){
            LOGGER.info("Cannot edit Vehicle: {}, User: {} has not permission",vehicleBackingBean.getSelectedVehicle(),loginController.getCurrentUser());
            return;
        }
        editVehicle(vehicleBackingBean.getSelectedVehicle());
    }

    public void editVehicle(Vehicle vehicle){
        LOGGER.info("Start editing of selected Vehicle: {}", vehicleBackingBean.getSelectedVehicle());
        vehicleBackingBean.setSelectedVehicle(vehicle);
        vehicleModelBean.setSelectedVehicle(vehicle);
        refreshDriverListForSelectedVehicle();
        redirectToVehicleEditPage();
    }

    public List<Truck> loadAllTrucks(){
        LOGGER.info("Load all trucks from database.");
        return truckService.findAllTrucks(null);
    }

    public List<Trailer> loadAllTrailers(){
        LOGGER.info("Load all trailers from database.");
        return trailerService.findAllTrailers(null);
    }

    public Boolean isVehicleConnectedWithAnyVehicleWithDriver(Vehicle vehicle){
        LOGGER.info("Check is Vehicle connected with any vehicle with driver, Vehicle: {}", vehicle);
        return vehicleService.findAmountOfVehicleWithDriversConnectedWithVehicle(vehicle) > 0;
    }

    private void redirectToVehiclesPage(){
        LOGGER.info("Redirecting to Vehicles page");
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("vehicles.xhtml");
        }
        catch (IOException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
    }

    private void redirectToVehicleEditPage(){
        LOGGER.info("Redirecting to Vehicle edit page");
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("edit_vehicle.xhtml");
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
    }
}
