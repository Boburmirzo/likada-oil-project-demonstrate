package pro.likada.dto;

import pro.likada.model.Vehicle;

import java.util.Date;
import java.util.List;

/**
 * Created by abuca on 31.03.17.
 */
public class TrackInfo {
    private Vehicle vehicle;
    private Date dateFrom;
    private Date dateTo;
    private List<VehiclePosition> positionList;
    private Double distance;

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public List<VehiclePosition> getPositionList() {
        return positionList;
    }

    public void setPositionList(List<VehiclePosition> positionList) {
        this.positionList = positionList;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }
}
