package pro.likada.bean.backing;

import com.google.gson.Gson;
import org.primefaces.context.RequestContext;
import org.primefaces.model.TreeNode;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.likada.bean.controller.GlobalMapController;
import pro.likada.bean.util.GlobalMapStatesEnum;
import pro.likada.dto.TrackInfo;
import pro.likada.model.ShipmentBasis;
import pro.likada.model.Vehicle;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.function.BooleanSupplier;

/**
 * Created by abuca on 21.03.17.
 */
@Named
@ViewScoped
public class GlobalMapBackingBean implements Serializable {
    private GlobalMapStatesEnum currentState;

    private Double currentLatitude;
    private Double currentLongitude;
    private Integer currentZoom;

    private List<ShipmentBasis> shipmentBasisList;
    private List<Vehicle> vehicleList;

    private List<ShipmentBasis> filteredShipmentBasisList;
    private List<Vehicle> filteredVehicleList;

    private Vehicle selectedVehicle;
    private TrackInfo selectedTrack;

    private TreeNode root;
    private TreeNode[] selectedNodes;

    private MapModel globalMapModel;

    public List<ShipmentBasis> getShipmentBasisList() {
        return shipmentBasisList;
    }

    @PostConstruct
    public void init(){
        this.currentLatitude = 55.791589;
        this.currentLongitude = 49.115525;
        this.currentZoom = 10;
        this.currentState = GlobalMapStatesEnum.ONLINE;
        this.selectedTrack = new TrackInfo();
    }

    public Boolean isOnline(){
        return currentState == GlobalMapStatesEnum.ONLINE;
    }

    public void setShipmentBasisList(List<ShipmentBasis> shipmentBasisList) {
        this.shipmentBasisList = shipmentBasisList;
    }

    public List<Vehicle> getVehicleList() {
        return vehicleList;
    }

    public void setVehicleList(List<Vehicle> vehicleList) {
        this.vehicleList = vehicleList;
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public TreeNode[] getSelectedNodes() {
        return selectedNodes;
    }

    public void setSelectedNodes(TreeNode[] selectedNodes) {
        this.selectedNodes = selectedNodes;
    }

    public List<ShipmentBasis> getFilteredShipmentBasisList() {
        return filteredShipmentBasisList;
    }

    public void setFilteredShipmentBasisList(List<ShipmentBasis> filteredShipmentBasisList) {
        this.filteredShipmentBasisList = filteredShipmentBasisList;
    }

    public List<Vehicle> getFilteredVehicleList() {
        return filteredVehicleList;
    }

    public void setFilteredVehicleList(List<Vehicle> filteredVehicleList) {
        this.filteredVehicleList = filteredVehicleList;
    }

    public Double getCurrentLatitude() {
        return currentLatitude;
    }

    public void setCurrentLatitude(Double currentLatitude) {
        this.currentLatitude = currentLatitude;
    }

    public Double getCurrentLongitude() {
        return currentLongitude;
    }

    public void setCurrentLongitude(Double currentLongitude) {
        this.currentLongitude = currentLongitude;
    }

    public Integer getCurrentZoom() {
        return currentZoom;
    }

    public void setCurrentZoom(Integer currentZoom) {
        this.currentZoom = currentZoom;
    }

    public MapModel getGlobalMapModel() {
        return globalMapModel;
    }

    public void setGlobalMapModel(MapModel globalMapModel) {
        this.globalMapModel = globalMapModel;
    }

    public GlobalMapStatesEnum getCurrentState() {
        return currentState;
    }

    public void setCurrentState(GlobalMapStatesEnum currentState) {
        this.currentState = currentState;
    }

    public Vehicle getSelectedVehicle() {
        return selectedVehicle;
    }

    public void setSelectedVehicle(Vehicle selectedVehicle) {
        this.selectedVehicle = selectedVehicle;
    }

    public TrackInfo getSelectedTrack() {
        return selectedTrack;
    }

    public void setSelectedTrack(TrackInfo selectedTrack) {
        this.selectedTrack = selectedTrack;
    }
}
