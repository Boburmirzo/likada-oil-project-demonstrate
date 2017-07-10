package pro.likada.bean.backing;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.MapModel;
import pro.likada.bean.model.DriveModelBean;
import pro.likada.model.*;
import pro.likada.service.ContractorService;
import pro.likada.service.DriveStateTypeService;
import pro.likada.service.DriveStatusService;
import pro.likada.service.ShipmentBasisService;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * Created by bumur on 27.03.2017.
 */
@Named
@ViewScoped
public class DriveBackingBean implements Serializable{


    @Inject
    private DriveStatusService driveStatusService;
    @Inject
    private ContractorService contractorService;
    @Inject
    private DriveStateTypeService driveStateTypeService;
    @Inject
    private DriveModelBean driveModelBean;
    @Inject
    private ShipmentBasisService shipmentBasisService;


    private List<Drive> drives;
    private Drive selectedDrive;
    private List<DriveStatus> driveStatuses;
    private List<Contractor> contractorsOfCompany;
    private List<Contractor> contractorsOfCarriers;
    private List<Contractor> contractorsOfReceiver;
    private Contractor selectedContractorOfCompany;
    private List<Contractor> filteredContractorsOfCompany;
    private Contractor selectedContractorOfCarriers;
    private List<Contractor> filteredContractorsCarriers;
    private Contractor selectedContractorOfReceiver;
    private List<Contractor> filteredContractorsReceiver;
    private int switchBetweenObjectType;
    private String saveDrive="save_drive";
    private List<DrivePoint> drivePoints;
    private DrivePoint selectedDrivePoint;
    private DrivePoint selectedDrivePointDestination;
    private DriveState selectedDriveState;
    private List<DriveStateType> driveStateTypes;
    private Driver selectedDriverForVehicle;
    private List<Driver> drivers;
    private Order selectedOrder;
    private List<ShipmentBasis> shipmentBases;
    private List<User> users;
    private User responsibleUser;
    private List<User> filteredResponsibleUsers;
    private List<User> filteredCreatorUsers;
    private User creatorUser;
    private User logisticianOfChosenVehicle;
    private ShipmentBasis shipmentBasisForLinkedObject;
    private List<Driver> driverListOfSelectedCar;
    private List<Drive> filteredDriversList;
    private Vehicle vehicleWithDrivers;
    private MapModel driveStagesMap;
    private MapModel driveFromPointMap;
    private MapModel driveFromPointMapOfDestination;
    private MapModel driveFromPointPutMap;
    private MapModel driveFromPointPutMapOfDestination;
    private double lat;
    private double lng;
    private StreamedContent streamedContent;
    private String filePhotoPath;

    @PostConstruct
    public void init(){
        if(driveModelBean.getSelectedDriveFromTableOfDrives()!=null)
            this.selectedDrive=driveModelBean.getSelectedDriveFromTableOfDrives();
        if(shipmentBases==null){
            this.shipmentBases=shipmentBasisService.getAllShipmentBasis();
        }
        this.driveStatuses=driveStatusService.getAllDriveStatuses();
        this.driveStateTypes=driveStateTypeService.getAllDriveStateTypes();
        this.switchBetweenObjectType=1;
        this.driveFromPointPutMap= new DefaultMapModel();
        this.driveFromPointPutMapOfDestination = new DefaultMapModel();
        this.streamedContent = new DefaultStreamedContent();
        this.filePhotoPath = "icons/processing.gif";
    }

    public List<Drive> getDrives() {
        return drives;
    }

    public void setDrives(List<Drive> drives) {
        this.drives = drives;
    }

    public Drive getSelectedDrive() {
        return selectedDrive;
    }

    public void setSelectedDrive(Drive selectedDrive) {
        this.selectedDrive = selectedDrive;
    }

    public List<DriveStatus> getDriveStatuses() {
        return driveStatuses;
    }

    public void setDriveStatuses(List<DriveStatus> driveStatuses) {
        this.driveStatuses = driveStatuses;
    }

    public List<Contractor> getContractorsOfCompany() {
        return contractorsOfCompany;
    }

    public void setContractorsOfCompany(List<Contractor> contractorsOfCompany) {
        this.contractorsOfCompany = contractorsOfCompany;
    }

    public List<Contractor> getContractorsOfCarriers() {
        return contractorsOfCarriers;
    }

    public void setContractorsOfCarriers(List<Contractor> contractorsOfCarriers) {
        this.contractorsOfCarriers = contractorsOfCarriers;
    }

    public List<Contractor> getContractorsOfReceiver() {
        return contractorsOfReceiver;
    }

    public void setContractorsOfReceiver(List<Contractor> contractorsOfReceiver) {
        this.contractorsOfReceiver = contractorsOfReceiver;
    }

    public Contractor getSelectedContractorOfCompany() {
        return selectedContractorOfCompany;
    }

    public void setSelectedContractorOfCompany(Contractor selectedContractorOfCompany) {
        this.selectedContractorOfCompany = selectedContractorOfCompany;
    }

    public Contractor getSelectedContractorOfCarriers() {
        return selectedContractorOfCarriers;
    }

    public void setSelectedContractorOfCarriers(Contractor selectedContractorOfCarriers) {
        this.selectedContractorOfCarriers = selectedContractorOfCarriers;
    }

    public Contractor getSelectedContractorOfReceiver() {
        return selectedContractorOfReceiver;
    }

    public void setSelectedContractorOfReceiver(Contractor selectedContractorOfReceiver) {
        this.selectedContractorOfReceiver = selectedContractorOfReceiver;
    }

    public List<DrivePoint> getDrivePoints() {
        return drivePoints;
    }

    public void setDrivePoints(List<DrivePoint> drivePoints) {
        this.drivePoints = drivePoints;
    }

    public DrivePoint getSelectedDrivePoint() {
        return selectedDrivePoint;
    }

    public void setSelectedDrivePoint(DrivePoint selectedDrivePoint) {
        this.selectedDrivePoint = selectedDrivePoint;
    }

    public int getSwitchBetweenObjectType() {
        return switchBetweenObjectType;
    }

    public void setSwitchBetweenObjectType(int switchBetweenObjectType) {
        this.switchBetweenObjectType = switchBetweenObjectType;
    }

    public Order getSelectedOrder() {
        return selectedOrder;
    }

    public void setSelectedOrder(Order selectedOrder) {
        this.selectedOrder = selectedOrder;
    }

    public DriveState getSelectedDriveState() {
        return selectedDriveState;
    }

    public void setSelectedDriveState(DriveState selectedDriveState) {
        this.selectedDriveState = selectedDriveState;
    }

    public List<DriveStateType> getDriveStateTypes() {
        return driveStateTypes;
    }

    public void setDriveStateTypes(List<DriveStateType> driveStateTypes) {
        this.driveStateTypes = driveStateTypes;
    }

    public Driver getSelectedDriverForVehicle() {
        return selectedDriverForVehicle;
    }

    public void setSelectedDriverForVehicle(Driver selectedDriverForVehicle) {
        this.selectedDriverForVehicle = selectedDriverForVehicle;
    }

    public List<Driver> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<Driver> drivers) {
        this.drivers = drivers;
    }

    public List<ShipmentBasis> getShipmentBases() {
        return shipmentBases;
    }

    public void setShipmentBases(List<ShipmentBasis> shipmentBases) {
        this.shipmentBases = shipmentBases;
    }

    public DrivePoint getSelectedDrivePointDestination() {
        return selectedDrivePointDestination;
    }

    public void setSelectedDrivePointDestination(DrivePoint selectedDrivePointDestination) {
        this.selectedDrivePointDestination = selectedDrivePointDestination;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public User getResponsibleUser() {
        return responsibleUser;
    }

    public void setResponsibleUser(User responsibleUser) {
        this.responsibleUser = responsibleUser;
    }

    public User getCreatorUser() {
        return creatorUser;
    }

    public void setCreatorUser(User creatorUser) {
        this.creatorUser = creatorUser;
    }

    public List<User> getFilteredResponsibleUsers() {
        return filteredResponsibleUsers;
    }

    public void setFilteredResponsibleUsers(List<User> filteredResponsibleUsers) {
        this.filteredResponsibleUsers = filteredResponsibleUsers;
    }

    public List<User> getFilteredCreatorUsers() {
        return filteredCreatorUsers;
    }

    public void setFilteredCreatorUsers(List<User> filteredCreatorUsers) {
        this.filteredCreatorUsers = filteredCreatorUsers;
    }

    public List<Contractor> getFilteredContractorsOfCompany() {
        return filteredContractorsOfCompany;
    }

    public void setFilteredContractorsOfCompany(List<Contractor> filteredContractorsOfCompany) {
        this.filteredContractorsOfCompany = filteredContractorsOfCompany;
    }

    public List<Contractor> getFilteredContractorsCarriers() {
        return filteredContractorsCarriers;
    }

    public void setFilteredContractorsCarriers(List<Contractor> filteredContractorsCarriers) {
        this.filteredContractorsCarriers = filteredContractorsCarriers;
    }

    public List<Contractor> getFilteredContractorsReceiver() {
        return filteredContractorsReceiver;
    }

    public void setFilteredContractorsReceiver(List<Contractor> filteredContractorsReceiver) {
        this.filteredContractorsReceiver = filteredContractorsReceiver;
    }

    public String getSaveDrive() {
        return saveDrive;
    }

    public void setSaveDrive(String saveDrive) {
        this.saveDrive = saveDrive;
    }

    public ShipmentBasis getShipmentBasisForLinkedObject() {
        return shipmentBasisForLinkedObject;
    }

    public void setShipmentBasisForLinkedObject(ShipmentBasis shipmentBasisForLinkedObject) {
        this.shipmentBasisForLinkedObject = shipmentBasisForLinkedObject;
    }

    public List<Driver> getDriverListOfSelectedCar() {
        return driverListOfSelectedCar;
    }

    public void setDriverListOfSelectedCar(List<Driver> driverListOfSelectedCar) {
        this.driverListOfSelectedCar = driverListOfSelectedCar;
    }

    public List<Drive> getFilteredDriversList() {
        return filteredDriversList;
    }

    public void setFilteredDriversList(List<Drive> filteredDriversList) {
        this.filteredDriversList = filteredDriversList;
    }

    public Vehicle getVehicleWithDrivers() {
        return vehicleWithDrivers;
    }

    public void setVehicleWithDrivers(Vehicle vehicleWithDrivers) {
        this.vehicleWithDrivers = vehicleWithDrivers;
    }

    public MapModel getDriveStagesMap() {
        return driveStagesMap;
    }

    public void setDriveStagesMap(MapModel driveStagesMap) {
        this.driveStagesMap = driveStagesMap;
    }

    public MapModel getDriveFromPointMap() {
        return driveFromPointMap;
    }

    public void setDriveFromPointMap(MapModel driveFromPointMap) {
        this.driveFromPointMap = driveFromPointMap;
    }

    public MapModel getDriveFromPointMapOfDestination() {
        return driveFromPointMapOfDestination;
    }

    public void setDriveFromPointMapOfDestination(MapModel driveFromPointMapOfDestination) {
        this.driveFromPointMapOfDestination = driveFromPointMapOfDestination;
    }

    public MapModel getDriveFromPointPutMap() {
        return driveFromPointPutMap;
    }

    public void setDriveFromPointPutMap(MapModel driveFromPointPutMap) {
        this.driveFromPointPutMap = driveFromPointPutMap;
    }

    public void setDriveFromPointPutMapOfDestination(MapModel driveFromPointPutMapOfDestination) {
        this.driveFromPointPutMapOfDestination = driveFromPointPutMapOfDestination;
    }

    public MapModel getDriveFromPointPutMapOfDestination() {
        return driveFromPointPutMapOfDestination;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public User getLogisticianOfChosenVehicle() {
        return logisticianOfChosenVehicle;
    }

    public void setLogisticianOfChosenVehicle(User logisticianOfChosenVehicle) {
        this.logisticianOfChosenVehicle = logisticianOfChosenVehicle;
    }

    public StreamedContent getStreamedContent() {
        return streamedContent;
    }

    public void setStreamedContent(StreamedContent streamedContent) {
        this.streamedContent = streamedContent;
    }

    public String getFilePhotoPath() {
        return filePhotoPath;
    }

    public void setFilePhotoPath(String filePhotoPath) {
        this.filePhotoPath = filePhotoPath;
    }
}
