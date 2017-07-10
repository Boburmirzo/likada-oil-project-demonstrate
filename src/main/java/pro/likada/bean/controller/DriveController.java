package pro.likada.bean.controller;

import org.primefaces.event.CellEditEvent;
import org.primefaces.event.map.MarkerDragEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.map.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.likada.bean.backing.CustomerBackingBean;
import pro.likada.bean.backing.DriveBackingBean;
import pro.likada.bean.backing.OrderBackingBean;
import pro.likada.bean.backing.VehicleBackingBean;
import pro.likada.bean.model.DriveModelBean;
import pro.likada.dto.VehiclePosition;
import pro.likada.model.*;
import pro.likada.rest.server.fmc.FMCMessagePost;
import pro.likada.service.*;
import pro.likada.util.ModelConstantEnum;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.FacesEvent;
import javax.faces.event.ValueChangeEvent;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Named;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;
import java.util.List;

/**
 * Created by bumur on 27.03.2017.
 */
@Named
@RequestScoped
public class DriveController implements Serializable{

    private static final Logger LOGGER = LoggerFactory.getLogger(DriveController.class);

    @Inject
    private DriveBackingBean driveBackingBean;
    @Inject
    private ContractorService contractorService;
    @Inject
    private VehicleBackingBean vehicleBackingBean;
    @Inject
    private CustomerBackingBean customerBackingBean;
    @Inject
    private OrderBackingBean orderBackingBean;
    @Inject
    private DriveStateService  driveStateService;
    @Inject
    private DriveService driveService;
    @Inject
    private DriverService driverService;
    @Inject
    private DriveModelBean driveModelBean;
    @Inject
    private LoginController loginController;
    @Inject
    private UserService userService;


    public List<Drive> getDrives(){
        if(driveBackingBean.getDrives()==null){
            driveBackingBean.setDrives(driveService.getAllDrives());
        }
        return driveBackingBean.getDrives();
    }

    public void setDriveForDistanceUpdate(Drive drive){
        driveBackingBean.setSelectedDrive(drive);
    }

    public void editDrive(Drive drive){
        LOGGER.info("Passing chosen Customer to editing page");
        driveModelBean.setSelectedDriveFromTableOfDrives(drive);
        redirectToEditPage();
    }

    private void redirectToEditPage(){
        LOGGER.info("Redirecting to Edit Drive page");
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("edit_drive.xhtml");
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Driver> getAllDriversForVehicle(){
        List<Driver> allDrivers = driverService.findAllDrivers(null);
        if (driveBackingBean.getDriverListOfSelectedCar() == null) {
            if (driveBackingBean.getSelectedDrive().getVehicle() != null) {
                driveBackingBean.setDriverListOfSelectedCar(driveBackingBean.getSelectedDrive().getVehicle().getDriverList());
            } else {
                driveBackingBean.setDrivers(allDrivers);
            }
        }
        driveBackingBean.setDrivers(driveBackingBean.getDriverListOfSelectedCar());
        for (Driver driver : allDrivers) {
            if (!driveBackingBean.getDrivers().contains(driver)) {
                driveBackingBean.getDrivers().add(driver);
            }
        }
        return driveBackingBean.getDrivers();
    }

    public void buttonActionAddNewDrive(ActionEvent actionEvent){
        Drive newDrive = new Drive();
        newDrive.setCreatedUserid(loginController.getCurrentUser());
        newDrive.setDriveStates(new HashSet<DriveState>());
        newDrive.setDrivePointsSource(new HashSet<DrivePoint>());
        newDrive.setDrivePointsDestination(new HashSet<DrivePoint>());
        newDrive.setOrders(new HashSet<Order>());
        newDrive.setCreated(new Date(System.currentTimeMillis()));
        driveModelBean.setSelectedDriveFromTableOfDrives(newDrive);
        redirectToEditPage();
    }

    public void buttonActionAddNewDriverPointForSource(ActionEvent actionEvent){
        DrivePoint newDrivePoint = new DrivePoint();
        newDrivePoint.setTypeOfObject(1);
        driveBackingBean.setSelectedDrivePoint(newDrivePoint);
    }

    public void buttonActionAddNewDriverPointForDestination(ActionEvent actionEvent){
        DrivePoint newDrivePoint = new DrivePoint();
        newDrivePoint.setTypeOfObject(1);
        driveBackingBean.setSelectedDrivePointDestination(newDrivePoint);
    }

    public void changeFieldsAccordingToShipmentBase(ShipmentBasis shipmentBasis){
        driveBackingBean.getSelectedDrivePoint().setAddress(shipmentBasis.getAddress());
        driveBackingBean.getSelectedDrivePoint().setCoordinates(String.valueOf(shipmentBasis.getLatitude()));
        driveBackingBean.getSelectedDrivePoint().setDescription(shipmentBasis.getDescription());
    }

    public void addNewDrivePoint(ActionEvent actionEvent){
        driveBackingBean.getSelectedDrivePoint().setDriveSource(driveBackingBean.getSelectedDrive());
        driveBackingBean.getSelectedDrive().getDrivePointsSource().add(driveBackingBean.getSelectedDrivePoint());
    }

    public void buttonActionRemoveDrivePoint(ActionEvent actionEvent){
        driveBackingBean.getSelectedDrive().getDrivePointsSource().remove(driveBackingBean.getSelectedDrivePoint());
    }

    public void addNewDrivePointDestination(ActionEvent actionEvent){
        driveBackingBean.getSelectedDrivePointDestination().setDriveDestination(driveBackingBean.getSelectedDrive());
        driveBackingBean.getSelectedDrive().getDrivePointsDestination().add(driveBackingBean.getSelectedDrivePointDestination());
    }

    public void buttonActionRemoveDrivePointDestination(ActionEvent actionEvent){
        driveBackingBean.getSelectedDrive().getDrivePointsDestination().remove(driveBackingBean.getSelectedDrivePointDestination());
    }

    public void buttonActionRemoveDrive(ActionEvent actionEvent){
        driveService.deleteById(driveBackingBean.getSelectedDrive().getId());
        driveBackingBean.getDrives().remove(driveBackingBean.getSelectedDrive());
    }

    public void editDrivePointDestination(DrivePoint drivePoint){
        if (drivePoint.getCoordinates() != null && !drivePoint.getCoordinates().isEmpty() && !drivePoint.getCoordinates().equals("null")) {
            String[] splitStr = drivePoint.getCoordinates().split("\\s+");
            double langtitude = Double.parseDouble(splitStr[0]);
            double longtitude = Double.parseDouble(splitStr[1]);
            MapModel mapModel = new DefaultMapModel();
            Marker marker = new Marker(new LatLng(langtitude, longtitude), drivePoint.getLinkToObject());
            marker.setDraggable(true);
            mapModel.addOverlay(marker);
            driveBackingBean.setDriveFromPointPutMapOfDestination(mapModel);
        }
        driveBackingBean.setSelectedDrivePointDestination(drivePoint);
    }

    public void editDrivePoint(DrivePoint drivePoint){
        if (drivePoint.getCoordinates() != null && !drivePoint.getCoordinates().isEmpty() && !drivePoint.getCoordinates().equals("null")) {
            String[] splitStr = drivePoint.getCoordinates().split("\\s+");
            double langtitude = Double.parseDouble(splitStr[0]);
            double longtitude = Double.parseDouble(splitStr[1]);
            MapModel mapModel = new DefaultMapModel();
            Marker marker = new Marker(new LatLng(langtitude, longtitude), drivePoint.getLinkToObject());
            marker.setDraggable(true);
            mapModel.addOverlay(marker);
            driveBackingBean.setDriveFromPointPutMap(mapModel);
        }
        driveBackingBean.setSelectedDrivePoint(drivePoint);
    }

    public void doubleClickSelectedRowForChooseContractorOfCompany(FacesEvent event){
        setContractorOfCompany(driveBackingBean.getSelectedContractorOfCompany());
    }

    public void setContractorOfCompany(Contractor contractor){
        driveBackingBean.getSelectedDrive().setCompany(contractor);
    }

    public List<Contractor> getContractorsOfCompany(){
        if(driveBackingBean.getContractorsOfCompany()==null) {
            driveBackingBean.setContractorsOfCompany(contractorService.findAllContractors(null, ModelConstantEnum.COMPANY.getModelName()));
        }
        return driveBackingBean.getContractorsOfCompany();
    }

    public void doubleClickSelectedRowForChooseContractorOfCarrier(FacesEvent event){
        setContractorOfCarrier(driveBackingBean.getSelectedContractorOfCarriers());
    }

    public void setContractorOfCarrier(Contractor contractor){
        driveBackingBean.getSelectedDrive().setCarrier(contractor);
    }

    public List<Contractor> getContractorsOfCarrier(){
        if(driveBackingBean.getContractorsOfCarriers()==null) {
            driveBackingBean.setContractorsOfCarriers(contractorService.findAllContractors(null, ModelConstantEnum.CARRIER.getModelName()));
        }
        return driveBackingBean.getContractorsOfCarriers();
    }

    public void doubleClickSelectedRowForChooseContractorOfCustomer(FacesEvent event){
        setContractorOfCustomer(driveBackingBean.getSelectedContractorOfReceiver());
    }

    public void setContractorOfCustomer(Contractor contractor){
        driveBackingBean.getSelectedDrive().setReceiver(contractor);
    }

    public void editResponsible(DriveState driveState){
        driveBackingBean.setSelectedDriveState(driveState);
    }

    public void doubleClickSelectedRowForChooseResponsible(FacesEvent event){
        setContractorOfResponsible(driveBackingBean.getResponsibleUser());
    }

    public void setContractorOfResponsible(User user){
        driveBackingBean.getSelectedDriveState().setResponsible(user);
    }

    public void doubleClickSelectedRowForChooseCreator(FacesEvent event){
        setContractorOfCreator(driveBackingBean.getCreatorUser());
    }

    public void setContractorOfCreator(User user){
        driveBackingBean.getSelectedDrive().setCreatedUserid(user);
    }

    public List<Contractor> getContractorsOfCustomer(){
        if(driveBackingBean.getContractorsOfReceiver()==null) {
            driveBackingBean.setContractorsOfReceiver(contractorService.findAllContractors(null, ModelConstantEnum.CUSTOMER.getModelName()));
        }
        return driveBackingBean.getContractorsOfReceiver();
    }

    public void doubleClickSelectedRowForChooseCar(FacesEvent event){
        setVehicle(vehicleBackingBean.getSelectedVehicle());
    }

    public void setVehicle(Vehicle vehicle){
        driveBackingBean.getSelectedDrive().setVehicle(vehicle);
        driveBackingBean.getSelectedDrive().setLogistician(vehicle.getLogistician());
        driveBackingBean.setLogisticianOfChosenVehicle(vehicle.getLogistician());
        driveBackingBean.setDriverListOfSelectedCar(vehicle.getDriverList());
        driveBackingBean.setVehicleWithDrivers(vehicle);
    }

    public void doubleClickSelectedRowForChooseDriver(FacesEvent event){
        setDriver(driveBackingBean.getSelectedDriverForVehicle());
    }

    public void setDriver(Driver driver){
        driveBackingBean.getSelectedDrive().setDriver(driver);
    }

    public void doubleClickSelectedRowForOrder(FacesEvent event){
        setOrder(orderBackingBean.getSelectedOrder());
    }

    public void deleteSelectedDriveOrder(ActionEvent actionEvent){
        driveBackingBean.getSelectedDrive().getOrders().remove(driveBackingBean.getSelectedOrder());
    }

    public void setOrder(Order order){
        driveBackingBean.getSelectedDrive().getOrders().add(order);
    }

    public void onRowEditDriveState(CellEditEvent event) {
        driveService.save(driveBackingBean.getSelectedDrive());
    }

    public void buttonActionAddNewDriveState(ActionEvent actionEvent){
        DriveState newDriveState = new DriveState();
        newDriveState.setModifiedDate(new Date(System.currentTimeMillis()));
        newDriveState.setDriveStateType(driveBackingBean.getDriveStateTypes().get(2));
        newDriveState.setDrive(driveBackingBean.getSelectedDrive());
        newDriveState.setResponsible(loginController.getCurrentUser());
        driveBackingBean.getSelectedDrive().getDriveStates().add(newDriveState);
    }

    public void buttonActionRemoveDriveState(ActionEvent actionEvent){
        driveBackingBean.getSelectedDrive().getDriveStates().remove(driveBackingBean.getSelectedDriveState());
    }

    public void buttonActionSaveDrive(){
        driveService.save(driveBackingBean.getSelectedDrive());
        FMCMessagePost fmcMessagePost = new FMCMessagePost();
        fmcMessagePost.doPost(driveBackingBean.getSelectedDrive().getVehicle().getTerminalVehicleID().getId());
        refreshDrivesTable();
        redirectToDrivesPage();
    }

    public void buttonActionCancelSaveDrive(ActionEvent actionEvent){
        LOGGER.info("Cancel saving the Drive {}", driveBackingBean.getSelectedDrive());
        refreshDrivesTable();
        redirectToDrivesPage();
    }

    private void refreshDrivesTable() {
        driveBackingBean.setDrives(driveService.getAllDrives());
    }

    private void redirectToDrivesPage(){
        LOGGER.info("Redirecting to Drive pages");
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("drive.xhtml");
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
    }

    public void changeInputValuesToShipmentBaseData(ValueChangeEvent valueChangeEvent){
        ShipmentBasis shipmentBasis = (ShipmentBasis) valueChangeEvent.getNewValue();
        driveBackingBean.getSelectedDrivePoint().setLinkToObject(shipmentBasis.getNameShort());
        driveBackingBean.getSelectedDrivePoint().setAddress(shipmentBasis.getAddress());
        driveBackingBean.getSelectedDrivePoint().setCoordinates(String.valueOf(shipmentBasis.getLatitude())+" "+String.valueOf(shipmentBasis.getLongitude()));
        driveBackingBean.getSelectedDrivePoint().setDescription(shipmentBasis.getDescription());
    }


    public void changeInputValuesToShipmentBaseDataForDestination(ValueChangeEvent valueChangeEvent){
        ShipmentBasis shipmentBasis = (ShipmentBasis) valueChangeEvent.getNewValue();
        if(shipmentBasis!=null) {
            driveBackingBean.getSelectedDrivePointDestination().setLinkToObject(shipmentBasis.getNameShort());
            driveBackingBean.getSelectedDrivePointDestination().setAddress(shipmentBasis.getAddress());
            driveBackingBean.getSelectedDrivePointDestination().setCoordinates(String.valueOf(shipmentBasis.getLatitude()) + " " + String.valueOf(shipmentBasis.getLongitude()));
            driveBackingBean.getSelectedDrivePointDestination().setDescription(shipmentBasis.getDescription());
        }
    }

    public List<User> getUsers(){
        if(driveBackingBean.getUsers()==null){
            driveBackingBean.setUsers(userService.getAllUsers());
        }
        return driveBackingBean.getUsers();
    }

    public void refreshStatesDistance(){
        if(driveBackingBean.getSelectedDrive() == null){
            return;
        }
        Drive refreshedDrive = driveService.refreshDistanceData(driveBackingBean.getSelectedDrive());
        driveBackingBean.setSelectedDrive(refreshedDrive);
    }

    public void buildMapForStages(){
        if(driveBackingBean.getSelectedDrive() == null){
            driveBackingBean.setDriveStagesMap(new DefaultMapModel());
            return;
        }
        List<VehiclePosition> positionList = driveService.buildTracksForDriveStages(driveBackingBean.getSelectedDrive());
        if(positionList.size() == 0){
            driveBackingBean.setDriveStagesMap(new DefaultMapModel());
            return;
        }
        List<DriveState> driveStateList = new ArrayList<>(driveBackingBean.getSelectedDrive().getDriveStatesList());
        driveStateList.sort((o1, o2) -> o2.compareTo(o1));
        MapModel mapModel = new DefaultMapModel();
        Polyline polyline = new Polyline();
        polyline.setStrokeWeight(2);
        polyline.setStrokeColor("#FF0000");
        polyline.setStrokeOpacity(1.0);
        LatLng coords = new LatLng(positionList.get(0).getLatitude(),positionList.get(0).getLongitude());
        for(VehiclePosition position : positionList){
            coords = new LatLng(position.getLatitude(),position.getLongitude());
            polyline.getPaths().add(coords);
            if(driveStateList.size() > 0 && position.getDate().compareTo(driveStateList.get(0).getModifiedDate()) >= 0){
                mapModel.addOverlay(new Marker(coords, driveStateList.get(0).getDriveStateType().getName()));
                driveStateList.remove(0);
            }
        }
        if(driveStateList.size() > 0 && driveStateList.get(0).getDriveStateType().getType().equals("FINISHED")){
            mapModel.addOverlay(new Marker(coords, driveStateList.get(0).getDriveStateType().getName()));
            driveStateList.remove(0);
        }
        mapModel.addOverlay(polyline);
        driveBackingBean.setDriveStagesMap(mapModel);
    }

    public void showDrivePointCoordinates(DrivePoint drivePoint){
        if(drivePoint!=null) {
            if (drivePoint.getCoordinates() != null && !drivePoint.getCoordinates().isEmpty() && !drivePoint.getCoordinates().contains("null")) {
                String[] splitStr = drivePoint.getCoordinates().split("\\s+");
                Double langtitude = Double.parseDouble(splitStr[0]);
                Double longtitude = Double.parseDouble(splitStr[1]);
                LOGGER.info(langtitude.toString());
                LOGGER.info(longtitude.toString());
                MapModel mapModel = new DefaultMapModel();
                LatLng coordinate = new LatLng(langtitude, longtitude);
                mapModel.addOverlay(new Marker(coordinate, drivePoint.getLinkToObject()));
                driveBackingBean.setDriveFromPointMap(mapModel);
            }
        }
    }

    public void showDrivePointCoordinatesOfDestination(DrivePoint drivePointTo){
        if(drivePointTo!=null) {
            if (drivePointTo.getCoordinates() != null && !drivePointTo.getCoordinates().isEmpty() && !drivePointTo.getCoordinates().contains("null")) {
                String[] splitStr = drivePointTo.getCoordinates().split("\\s+");
                Double langtitude = Double.parseDouble(splitStr[0]);
                Double longtitude = Double.parseDouble(splitStr[1]);
                LOGGER.info(langtitude.toString());
                LOGGER.info(longtitude.toString());
                MapModel mapModel = new DefaultMapModel();
                LatLng coordinate = new LatLng(langtitude, longtitude);
                mapModel.addOverlay(new Marker(coordinate, drivePointTo.getLinkToObject()));
                driveBackingBean.setDriveFromPointMapOfDestination(mapModel);
            }
        }
    }

    public void addMarkerForSourcePoint(){
        driveBackingBean.getSelectedDrivePoint().setCoordinates(String.valueOf(driveBackingBean.getLat())+" "+String.valueOf(driveBackingBean.getLng()));
        LOGGER.info(driveBackingBean.getSelectedDrivePoint().getCoordinates());
        Marker marker = new Marker(new LatLng(driveBackingBean.getLat(), driveBackingBean.getLng()), driveBackingBean.getSelectedDrivePoint().getLinkToObject());
        marker.setDraggable(true);
        driveBackingBean.getDriveFromPointPutMap().addOverlay(marker);
    }

    public void addMarkerForSourcePointDestination(){
        driveBackingBean.getSelectedDrivePointDestination().setCoordinates(String.valueOf(driveBackingBean.getLat())+" "+String.valueOf(driveBackingBean.getLng()));
        LOGGER.info(driveBackingBean.getSelectedDrivePointDestination().getCoordinates());
        Marker marker = new Marker(new LatLng(driveBackingBean.getLat(), driveBackingBean.getLng()), driveBackingBean.getSelectedDrivePointDestination().getLinkToObject());
        marker.setDraggable(true);
        driveBackingBean.getDriveFromPointPutMapOfDestination().addOverlay(marker);
    }

    public void onMarkerOfSourPointDrag(MarkerDragEvent event){
        Marker marker = event.getMarker();
        driveBackingBean.getSelectedDrivePoint().setCoordinates(String.valueOf(marker.getLatlng().getLat())+" "+String.valueOf(marker.getLatlng().getLng()));
    }

    public void onMarkerOfSourPointDragDestinationPoint(MarkerDragEvent event){
        Marker marker = event.getMarker();
        driveBackingBean.getSelectedDrivePointDestination().setCoordinates(String.valueOf(marker.getLatlng().getLat())+" "+String.valueOf(marker.getLatlng().getLng()));
    }

    public void fileDownloadViewForState(Document document) throws Exception {
//        LOGGER.info("Download file sent by DriveState", document.getDocument_name());
//        FacesContext context = FacesContext.getCurrentInstance();
//        ResourceBundle resourceBundle = ResourceBundle.getBundle("messages", context.getViewRoot().getLocale());
//        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
//        LOGGER.info(document.getDocument_path());
//        File file = new File(resourceBundle.getString("document.pathName")+
//                document.getDocument_path()+document.getDocument_name()+"."+document.getDocument_extension());
//        //driveBackingBean.setFilePhotoPath(file.getAbsolutePath());
//        InputStream input = new FileInputStream(file);
//        LOGGER.info(file.getCanonicalPath());
//        ByteArrayOutputStream bos = new ByteArrayOutputStream();
//        BufferedImage img = ImageIO.read(input);
//        int w = img.getWidth(null);
//        int h = img.getHeight(null);
//
//        // image is scaled two times at run time
//        int scale = 2;
//
//        BufferedImage bi = new BufferedImage(w * scale, h * scale,
//                BufferedImage.TYPE_INT_ARGB);
//        Graphics g = bi.getGraphics();
//
//        g.drawImage(img, 100, 100, w * scale, h * scale, null);
//
//        ImageIO.write(bi, "png", bos);
//
//        driveBackingBean.setStreamedContent( new DefaultStreamedContent(new ByteArrayInputStream(
//                bos.toByteArray()), "image/png"));
//        InputStream input = new FileInputStream(file);
//        driveBackingBean.setStreamedContent(new DefaultStreamedContent(input, externalContext.getMimeType(URLEncoder.encode(file.getName(), "UTF-8")), URLEncoder.encode(file.getName(), "UTF-8")));
        LOGGER.info("Download file sfsd*******************", document.getDocument_name());
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("messages", context.getViewRoot().getLocale());
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        LOGGER.info(document.getDocument_path());
        File file = new File(resourceBundle.getString("document.pathName")+
                document.getDocument_path()+document.getDocument_name()+"."+document.getDocument_extension());
        InputStream input = new FileInputStream(file);
        driveBackingBean.setStreamedContent(new DefaultStreamedContent(input, externalContext.getMimeType(URLEncoder.encode(file.getName(), "UTF-8")), URLEncoder.encode(file.getName(), "UTF-8")));
    }
}
