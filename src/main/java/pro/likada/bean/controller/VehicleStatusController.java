package pro.likada.bean.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.likada.bean.backing.VehicleStatusBackingBean;
import pro.likada.bean.model.VehicleStatusModelBean;
import pro.likada.model.*;
import pro.likada.service.VehicleMechanicalStatusTypeService;
import pro.likada.service.VehicleService;
import pro.likada.service.VehicleStatusService;
import pro.likada.util.AccessTypeEnum;
import pro.likada.util.ModelConstantEnum;

import javax.enterprise.context.RequestScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.FacesEvent;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by abuca on 14.03.17.
 */
@Named
@RequestScoped
public class VehicleStatusController {
    private static final Logger LOGGER = LoggerFactory.getLogger(VehicleStatusController.class);

    @Inject
    private VehicleStatusModelBean vehicleStatusModelBean;
    @Inject
    private VehicleStatusBackingBean vehicleStatusBackingBean;
    @Inject
    private VehicleStatusService vehicleStatusService;
    @Inject
    private VehicleService vehicleService;
    @Inject
    private LoginController loginController;
    @Inject
    private VehicleMechanicalStatusTypeService vehicleMechanicalStatusTypeService;


    public void refreshVehicleStatusesTable(){
        LOGGER.info("Refreshing VehicleStatuses table");
        List<Vehicle> vehicleList = vehicleService.findAllVehicles(null);
        if(vehicleStatusBackingBean.isShowOnlyVehiclesWhereLogistianIsYou()){
            vehicleList = vehicleList.stream()
                    .filter(vehicle -> vehicle.getLogistician() != null)
                    .filter(vehicle -> vehicle.getLogistician().getTitle().equals(loginController.getCurrentUser().getTitle())).collect(Collectors.toList());
        }
        vehicleStatusBackingBean.setVehicleList(vehicleList);
    }

    public List<Vehicle> getVehicles(){
        if(vehicleStatusBackingBean.getVehicleList()==null) {
            LOGGER.info("Unable to load vehicles statuses from bean, load from database.");
            refreshVehicleStatusesTable();
        }
        return vehicleStatusBackingBean.getVehicleList();
    }

    public boolean isCurrentUserLogistician(){
        return loginController.getCurrentUser().getUserRoles().stream().anyMatch(role -> role.getType().equals(RoleEnum.LOGISTICS.getRoleType()));
    }

    public void switchModeOnlyMyVehicles(FacesEvent facesEvent){
        if(vehicleStatusBackingBean.isShowOnlyVehiclesWhereLogistianIsYou()){
            LOGGER.info("Switch vehicle statuses view mode to \"only my vehicles\"");
        }
        else {
            LOGGER.info("Switch vehicle statuses view mode to \"all vehicles\"");
        }
        vehicleStatusBackingBean.setShowOnlyVehiclesWhereLogistianIsYou(!vehicleStatusBackingBean.isShowOnlyVehiclesWhereLogistianIsYou());
        refreshVehicleStatusesTable();
    }

    public void buttonSaveVehicleStatusLogistician(ActionEvent actionEvent){
        VehicleStatus selectedStatus = vehicleStatusBackingBean.getSelectedStatus();
        LOGGER.info("Save new VehicleLogisticianStatus: {}", selectedStatus);
        if(!loginController.hasAccessTo(ModelConstantEnum.VEHICLE_STATUS, AccessTypeEnum.EDIT)){
            LOGGER.info("Cannot update status for Vehicle: {}, User: {} has not permission",vehicleStatusBackingBean.getSelectedStatus().getVehicle(),loginController.getCurrentUser());
            return;
        }
        selectedStatus.getVehicle().setStatus(selectedStatus);
//        vehicleStatusService.save(selectedStatus);
        vehicleService.save(selectedStatus.getVehicle());
        refreshVehicleStatusesTable();
    }

    public void buttonSaveVehicleStatusMechanical(ActionEvent actionEvent){
        VehicleStatus selectedStatus = vehicleStatusBackingBean.getSelectedStatus();
        LOGGER.info("Save new VehicleMechanicalStatus: {}", selectedStatus);
        if(!loginController.hasAccessTo(ModelConstantEnum.VEHICLE_STATUS, AccessTypeEnum.EDIT)){
            LOGGER.info("Cannot update status for Vehicle: {}, User: {} has not permission",vehicleStatusBackingBean.getSelectedStatus().getVehicle(),loginController.getCurrentUser());
            return;
        }
        Set<VehicleMechanicalStatusType> selectedTypes = new HashSet<>();
        vehicleStatusBackingBean.getSelectedMechanicalStatusTypes()
                .stream()
                .map(name -> vehicleMechanicalStatusTypeService.getVehicleStatusTypeByName(name))
                .forEach(vehicleMechanicalStatusType -> selectedTypes.add(vehicleMechanicalStatusType));
        selectedStatus.getMechanicalStatus().setMechanicalStatuses(selectedTypes);
        selectedStatus.getVehicle().setStatus(selectedStatus);
//        vehicleStatusService.save(selectedStatus);
        vehicleService.save(selectedStatus.getVehicle());
        refreshVehicleStatusesTable();
    }

    public List<VehicleMechanicalStatusType> getVehicleMechanicalStatysTypes(){
        return vehicleMechanicalStatusTypeService.getAllVehicleMechanicalStatusTypes();
    }

    public void buttonCreateNewLogisticianStatus(Vehicle vehicle){
        if(!loginController.hasAccessTo(ModelConstantEnum.VEHICLE_STATUS, AccessTypeEnum.EDIT)){
            LOGGER.info("Cannot update status for Vehicle: {}, User: {} has not permission",vehicle,loginController.getCurrentUser());
            return;
        }
        VehicleStatus status;
        if(vehicle.getStatus() != null){
            LOGGER.info("Create new VehicleLogisticianStatus, based on: {}", vehicle.getStatus());
            status = new VehicleStatus(vehicle.getStatus());
            if(vehicle.getStatus().getLogisticianStatus() != null) {
                status.setLogisticianStatus(new VehicleLogisticianStatus(status.getLogisticianStatus()));
            }
            else{
                status.setLogisticianStatus(new VehicleLogisticianStatus());
            }
        }
        else {
            LOGGER.info("Create new VehicleLogisticianStatus");
            status = new VehicleStatus();
            status.setVehicle(vehicle);
            status.setLogisticianStatus(new VehicleLogisticianStatus());
        }
        status.getLogisticianStatus().setCreator(loginController.getCurrentUser());
        status.getLogisticianStatus().setCreated(new Date(System.currentTimeMillis()));
        vehicleStatusBackingBean.setSelectedStatus(status);
    }

    public void buttonCreateNewMechanicalStatus(Vehicle vehicle){
        if(!loginController.hasAccessTo(ModelConstantEnum.VEHICLE_STATUS, AccessTypeEnum.EDIT)){
            LOGGER.info("Cannot update status for Vehicle: {}, User: {} has not permission",vehicle,loginController.getCurrentUser());
            return;
        }
        VehicleStatus status;
        if(vehicle.getStatus() != null){
            LOGGER.info("Create new VehicleMechanicalStatus, based on: {}", vehicle.getStatus());
            status = new VehicleStatus(vehicle.getStatus());
            if(vehicle.getStatus().getMechanicalStatus()!=null) {
                status.setMechanicalStatus(new VehicleMechanicalStatus(status.getMechanicalStatus()));
                if(status.getMechanicalStatus().getMechanicalStatuses().size() > 0){
                    vehicleStatusBackingBean.setSelectedMechanicalStatusTypes(
                            status.getMechanicalStatus()
                                    .getMechanicalStatuses()
                                    .stream()
                                    .map(vehicleMechanicalStatusType -> vehicleMechanicalStatusType.getName())
                                    .collect(Collectors.toList()));
                }
            }
            else{
                status.setMechanicalStatus(new VehicleMechanicalStatus());
            }
        }
        else {
            LOGGER.info("Create new VehicleMechanicalStatus");
            status = new VehicleStatus();
            status.setVehicle(vehicle);
            status.setMechanicalStatus(new VehicleMechanicalStatus());
        }

        status.getMechanicalStatus().setCreator(loginController.getCurrentUser());
        status.getMechanicalStatus().setCreated(new Date(System.currentTimeMillis()));
        vehicleStatusBackingBean.setSelectedStatus(status);
        vehicleStatusBackingBean.setMechanicalStatusTypeList(
                getVehicleMechanicalStatysTypes()
                        .stream()
                        .map(vehicleMechanicalStatusType -> vehicleMechanicalStatusType.getName())
                        .collect(Collectors.toList())
        );
    }
}
