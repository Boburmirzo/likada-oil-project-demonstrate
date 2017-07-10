package pro.likada.bean.controller;

import com.google.gson.Gson;
import org.primefaces.context.RequestContext;
import org.primefaces.event.map.StateChangeEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.primefaces.model.map.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.likada.bean.backing.GlobalMapBackingBean;
import pro.likada.bean.model.GlobalMapModelBean;
import pro.likada.bean.util.GlobalMapStatesEnum;
import pro.likada.dto.MarkerInfo;
import pro.likada.dto.VehiclePosition;
import pro.likada.model.RoleEnum;
import pro.likada.model.ShipmentBasis;
import pro.likada.model.Vehicle;
import pro.likada.model.VehicleMechanicalStatusType;
import pro.likada.service.ShipmentBasisService;
import pro.likada.service.VehicleService;
import pro.likada.util.VehicleStateEnum;
import pro.likada.util.map.filter.AbstractShipmentBasisFilter;
import pro.likada.util.map.filter.AbstractVehicleFilter;

import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by abuca on 21.03.17.
 */
@Named
@RequestScoped
public class GlobalMapController {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalMapController.class);
    @Inject
    private GlobalMapBackingBean globalMapBackingBean;
    @Inject
    private GlobalMapModelBean globalMapModelBean;
    @Inject
    private ShipmentBasisService shipmentBasisService;
    @Inject
    private VehicleService vehicleService;
    @Inject
    private LoginController loginController;

    public void actionRefreshMapData() {
        MapModel mapModel = getMapModel();
        Marker[] newMarkers = new Marker[mapModel.getMarkers().size()];
        for (int i = 0; i < newMarkers.length; i++) {
            newMarkers[i] = mapModel.getMarkers().get(i);
        }
        RequestContext.getCurrentInstance().addCallbackParam("newMarkers", new Gson().toJson(newMarkers));
        LOGGER.info("Map refreshed");
    }

    public void onMapStateChanged(StateChangeEvent event) {
        globalMapBackingBean.setCurrentZoom(event.getZoomLevel());
        globalMapBackingBean.setCurrentLatitude(event.getCenter().getLat());
        globalMapBackingBean.setCurrentLongitude(event.getCenter().getLng());
    }

    public void onMapMarkerSelected() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> params = context.getExternalContext().getRequestParameterMap();
        if (params.containsKey("selectedMarkerType") && params.containsKey("selectedMarkerID")) {
            Long id = Long.parseLong(params.get("selectedMarkerID"));
            String type = params.get("selectedMarkerType");
            if (type.equals("vehicle")) {
                Vehicle vehicle = globalMapBackingBean.getFilteredVehicleList().stream().filter(vehicle1 -> vehicle1.getId().equals(id)).findFirst().orElse(null);
                globalMapBackingBean.setSelectedVehicle(vehicle);
                RequestContext requestContext = RequestContext.getCurrentInstance();
                requestContext.update(":form_build_track");
                requestContext.execute("PF('dlg_vehicle_info').show();");
            }
        }
    }

    public void refreshShipmentBasisData() {
        List<ShipmentBasis> shipmentBasisList = shipmentBasisService.getAllShipmentBasis();
        shipmentBasisList = shipmentBasisList.stream().filter(basis -> basis.getLatitude() != null && basis.getLongitude() != null).collect(Collectors.toList());
        globalMapBackingBean.setShipmentBasisList(shipmentBasisList);
    }

    public boolean isCurrentUserLogistician() {
        return loginController.getCurrentUser().getUserRoles().stream().anyMatch(role -> role.getType().equals(RoleEnum.LOGISTICS.getRoleType()));
    }

    public void buttonBuildTrack() {
        globalMapBackingBean.setCurrentState(GlobalMapStatesEnum.TRACK);
        actionRefreshMapData();
    }

    public void buttonFilter() {
        refreshMapObjectsFiltredLists();
        globalMapBackingBean.setCurrentState(GlobalMapStatesEnum.ONLINE);
        actionRefreshMapData();
    }

    private void refreshMapObjectsFiltredLists() {
        Set<ShipmentBasis> basisSet = new HashSet<>();
        Set<Vehicle> vehicleSet = new HashSet<>();
        if (globalMapBackingBean.getSelectedNodes() == null) {
            refreshFilterTree();
        }
        for (TreeNode node : globalMapBackingBean.getSelectedNodes()) {
            if (node.getData() instanceof AbstractShipmentBasisFilter) {
                AbstractShipmentBasisFilter filter = (AbstractShipmentBasisFilter) node.getData();
                List<ShipmentBasis> filteredList = globalMapBackingBean.getShipmentBasisList().stream().filter(basis -> filter.filter(basis)).collect(Collectors.toList());
                basisSet.addAll(filteredList);
            }
            if (node.getData() instanceof AbstractVehicleFilter) {
                AbstractVehicleFilter filter = (AbstractVehicleFilter) node.getData();
                List<Vehicle> filteredList = globalMapBackingBean.getVehicleList().stream().filter(basis -> filter.filter(basis)).collect(Collectors.toList());
                vehicleSet.addAll(filteredList);
            }
        }
        globalMapBackingBean.setFilteredShipmentBasisList(new ArrayList<>(basisSet));
        globalMapBackingBean.setFilteredVehicleList(new ArrayList<>(vehicleSet));
    }

    private void refreshVehicleData() {
        List<Vehicle> vehicleList = vehicleService.findAllVehicles(null);
        vehicleList = vehicleService.refreshPositionData(vehicleList);
        vehicleList = vehicleList.stream().filter(vehicle -> vehicle.getLatitude() != null && vehicle.getLongitude() != null).collect(Collectors.toList());
        globalMapBackingBean.setVehicleList(vehicleList);
    }

    private void refreshFilterTree() {
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("messages", context.getViewRoot().getLocale());
        TreeNode root = new DefaultTreeNode(null);

        TreeNode bases = new DefaultTreeNode("shipment_base", new AbstractShipmentBasisFilter(resourceBundle.getString("map.shipmentBases")) {
            @Override
            public boolean filter(ShipmentBasis base) {
                return true;
            }
        }, root);
        TreeNode vehicles = new DefaultTreeNode("vehicle", new AbstractVehicleFilter(resourceBundle.getString("map.cars")) {
            @Override
            public boolean filter(Vehicle vehicle) {
                return true;
            }
        }, root);

        List<ShipmentBasis> baseList = getShipmentBasis().stream().sorted((o1, o2) -> o1.getNameShort().compareTo(o2.getNameShort())).collect(Collectors.toList());
        for (ShipmentBasis baseForFilter : baseList) {
            new DefaultTreeNode("shipment_base", new AbstractShipmentBasisFilter(baseForFilter.getNameShort()) {
                @Override
                public boolean filter(ShipmentBasis base) {
                    return base.getNameShort().equals(baseForFilter.getNameShort());
                }
            }, bases);
        }

        TreeNode allVehicles = new DefaultTreeNode("vehicle", new AbstractVehicleFilter(resourceBundle.getString("map.all")) {
            @Override
            public boolean filter(Vehicle vehicle) {
                return true;
            }
        }, vehicles);

        List<Vehicle> vehicleList = getVehicles().stream().sorted((o1, o2) -> o1.getTruck().getGovNumber().compareTo(o2.getTruck().getGovNumber())).collect(Collectors.toList());
        for (Vehicle vehicleForFilter : vehicleList) {
            new DefaultTreeNode("vehicle", new AbstractVehicleFilter(vehicleForFilter.getTruck().getGovNumber()) {
                @Override
                public boolean filter(Vehicle vehicle) {
                    return vehicle.getTruck().getTitle().equals(vehicleForFilter.getTruck().getTitle());
                }
            }, allVehicles);
        }

        if (isCurrentUserLogistician()) {
            new DefaultTreeNode("vehicle", new AbstractVehicleFilter(resourceBundle.getString("map.logisticianCar")) {
                @Override
                public boolean filter(Vehicle vehicle) {
                    return vehicle.getLogistician().equals(loginController.getCurrentUser());
                }
            }, vehicles);
        }

        new DefaultTreeNode("vehicle", new AbstractVehicleFilter(resourceBundle.getString("map.freeCars")) {
            @Override
            public boolean filter(Vehicle vehicle) {
                return true;
            }
        }, vehicles);

        new DefaultTreeNode("vehicle", new AbstractVehicleFilter(resourceBundle.getString("map.inServiceCars")) {
            @Override
            public boolean filter(Vehicle vehicle) {
                if (vehicle.getStatus() == null) {
                    return false;
                }
                if (vehicle.getStatus().getMechanicalStatus() == null) {
                    return false;
                }
                for (VehicleMechanicalStatusType statusType : vehicle.getStatus().getMechanicalStatus().getMechanicalStatuses()) {
                    if (statusType.getType().equals("MAINTENANCE")) {
                        return true;
                    }
                }
                return false;
            }
        }, vehicles);

        new DefaultTreeNode("vehicle", new AbstractVehicleFilter(resourceBundle.getString("map.repairedCars")) {
            @Override
            public boolean filter(Vehicle vehicle) {
                if (vehicle.getStatus() == null) {
                    return false;
                }
                if (vehicle.getStatus().getMechanicalStatus() == null) {
                    return false;
                }
                for (VehicleMechanicalStatusType statusType : vehicle.getStatus().getMechanicalStatus().getMechanicalStatuses()) {
                    if (statusType.getType().equals("REPAIR")) {
                        return true;
                    }
                }
                return false;
            }
        }, vehicles);

        new DefaultTreeNode("vehicle", new AbstractVehicleFilter(resourceBundle.getString("map.brokenCars")) {
            @Override
            public boolean filter(Vehicle vehicle) {
                if (vehicle.getStatus() == null) {
                    return false;
                }
                if (vehicle.getStatus().getMechanicalStatus() == null) {
                    return false;
                }
                for (VehicleMechanicalStatusType statusType : vehicle.getStatus().getMechanicalStatus().getMechanicalStatuses()) {
                    if (statusType.getType().equals("BROKEN")) {
                        return true;
                    }
                }
                return false;
            }
        }, vehicles);

        new DefaultTreeNode("vehicle", new AbstractVehicleFilter(resourceBundle.getString("map.onTheWayCars")) {
            @Override
            public boolean filter(Vehicle vehicle) {
                return vehicle.getMoveState() == VehicleStateEnum.MOVE;
            }
        }, vehicles);

        new DefaultTreeNode("vehicle", new AbstractVehicleFilter(resourceBundle.getString("map.stoppedCars")) {
            @Override
            public boolean filter(Vehicle vehicle) {
                return vehicle.getMoveState() == VehicleStateEnum.PARK;
            }
        }, vehicles);

        globalMapBackingBean.setSelectedNodes(new TreeNode[0]);
        globalMapBackingBean.setRoot(root);
    }

    public List<Vehicle> getVehicles() {
        if (globalMapBackingBean.getVehicleList() == null) {
            LOGGER.info("Unable to load vehicles bases from bean, load from database.");
            refreshVehicleData();
        }
        return globalMapBackingBean.getVehicleList();
    }

    public List<ShipmentBasis> getShipmentBasis() {
        if (globalMapBackingBean.getShipmentBasisList() == null) {
            LOGGER.info("Unable to load shipment bases from bean, load from database.");
            refreshShipmentBasisData();
        }
        return globalMapBackingBean.getShipmentBasisList();
    }

    public MapModel getMapModel() {
        if (globalMapBackingBean.getCurrentState() == GlobalMapStatesEnum.ONLINE) {
            globalMapBackingBean.setGlobalMapModel(getMapModelOnline());
        }
        if (globalMapBackingBean.getCurrentState() == GlobalMapStatesEnum.TRACK) {
            globalMapBackingBean.setGlobalMapModel(getMapModelTrack());
        }
        return globalMapBackingBean.getGlobalMapModel();
    }

    private MapModel getMapModelOnline() {
        LOGGER.info("Create online map model");
        refreshShipmentBasisData();
        refreshVehicleData();
        refreshMapObjectsFiltredLists();

        List<ShipmentBasis> shipmentBasisList = globalMapBackingBean.getFilteredShipmentBasisList();
        List<Vehicle> vehicleList = globalMapBackingBean.getFilteredVehicleList();

        MapModel mapModel = new DefaultMapModel();
        for (ShipmentBasis base : shipmentBasisList) {
            LatLng coords = new LatLng(base.getLatitude(), base.getLongitude());
            if (base.getIcon() == null) {
                mapModel.addOverlay(new Marker(coords, base.getNameShort(), new MarkerInfo("base", base.getId()), getIconPath("bases/default")));
            } else {
                mapModel.addOverlay(new Marker(coords, base.getNameShort(), new MarkerInfo("base", base.getId()), getIconPath("bases/" + base.getIcon())));
            }
        }
        for (Vehicle vehicle : vehicleList) {
            LatLng coords = new LatLng(vehicle.getLatitude(), vehicle.getLongitude());
            if (vehicle.getMoveState() == VehicleStateEnum.MOVE) {
                mapModel.addOverlay(new Marker(coords, vehicle.getTruck().getTitle(), new MarkerInfo("vehicle", vehicle.getId()), getIconPath("vehicle_green")));
            } else {
                mapModel.addOverlay(new Marker(coords, vehicle.getTruck().getTitle(), new MarkerInfo("vehicle", vehicle.getId()), getIconPath("vehicle_red")));
            }
        }
        return mapModel;
    }

    private MapModel getMapModelTrack() {
        LOGGER.info("Create track map model");
        List<VehiclePosition> positionList = vehicleService.buildTrack(globalMapBackingBean.getSelectedVehicle(), globalMapBackingBean.getSelectedTrack().getDateFrom(), globalMapBackingBean.getSelectedTrack().getDateTo());
        Double distance = vehicleService.buildTrackLength(globalMapBackingBean.getSelectedVehicle(), globalMapBackingBean.getSelectedTrack().getDateFrom(), globalMapBackingBean.getSelectedTrack().getDateTo());
        globalMapBackingBean.getSelectedTrack().setVehicle(globalMapBackingBean.getSelectedVehicle());
        globalMapBackingBean.getSelectedTrack().setPositionList(positionList);
        globalMapBackingBean.getSelectedTrack().setDistance(distance);
        MapModel mapModel = new DefaultMapModel();
        Polyline polyline = new Polyline();
        for (VehiclePosition position : positionList) {
            polyline.getPaths().add(new LatLng(position.getLatitude(), position.getLongitude()));
        }
        polyline.setStrokeWeight(2);
        polyline.setStrokeColor("#FF0000");
        polyline.setStrokeOpacity(1.0);
        mapModel.addOverlay(polyline);
        return mapModel;
    }

    private String getIconPath(String iconName) {
        StringBuilder sb = new StringBuilder();
        sb.append(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath())
                .append("/resources/icons/")
                .append(iconName)
                .append(".png");
        return sb.toString();
    }

    public TreeNode getFilterTreeRoot() {
        if (globalMapBackingBean.getRoot() == null) {
            LOGGER.info("Create filters tree for map front.");
            refreshFilterTree();
        }
        return globalMapBackingBean.getRoot();
    }
}


