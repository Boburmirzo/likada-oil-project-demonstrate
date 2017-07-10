package pro.likada.rest.server.wrapper.drive;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by bumur on 03.04.2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DriveWrapper {
    @JsonProperty("ID")
    private String id;
    @JsonProperty("SERIAL")
    private String serial;
    @JsonProperty("STATUS")
    private String status;
    @JsonProperty("CURRENT_STATE")
    private DriveStateHistoryWrapper currentState;
    @JsonProperty("NEXT_STATE")
    private DriveStateNextWrapper nextState;
    @JsonProperty("STOP_STATE")
    private StopStateWrapper stopState;
    @JsonProperty("CONTINUE_STATE")
    private ContinueStateWrapper continueState;
    @JsonProperty("CANCEL_STATE")
    private CancelStateWrapper cancelState;
    @JsonProperty("SLEEP_STATE")
    private SleepStateWrapper sleepState;
    @JsonProperty("PRODUCT")
    private String product;
    @JsonProperty("VEHICLE")
    private String vehicle;
    @JsonProperty("LOGISTICIAN_NAME_SHORT")
    private String logisticianNameShort;
    @JsonProperty("LOGISTICIAN_NAME_FULL")
    private String logisticianNameFull;
    @JsonProperty("LOGISTICIAN_PHONE")
    private String logisticianPhone;
    @JsonProperty("DRIVER")
    private String driver;
    @JsonProperty("SOURCE")
    private SourceWrapper[] sourceWrappers;
    @JsonProperty("DESTINATION")
    private DestinationWrapper[] destinationWrappers;
    @JsonProperty("DESCRIPTION")
    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DriveStateHistoryWrapper getCurrentState() {
        return currentState;
    }

    public void setCurrentState(DriveStateHistoryWrapper currentState) {
        this.currentState = currentState;
    }

    public DriveStateNextWrapper getNextState() {
        return nextState;
    }

    public void setNextState(DriveStateNextWrapper nextState) {
        this.nextState = nextState;
    }

    public StopStateWrapper getStopState() {
        return stopState;
    }

    public void setStopState(StopStateWrapper stopState) {
        this.stopState = stopState;
    }

    public ContinueStateWrapper getContinueState() {
        return continueState;
    }

    public void setContinueState(ContinueStateWrapper continueState) {
        this.continueState = continueState;
    }

    public CancelStateWrapper getCancelState() {
        return cancelState;
    }

    public void setCancelState(CancelStateWrapper cancelState) {
        this.cancelState = cancelState;
    }

    public SleepStateWrapper getSleepState() {
        return sleepState;
    }

    public void setSleepState(SleepStateWrapper sleepState) {
        this.sleepState = sleepState;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public String getLogisticianNameShort() {
        return logisticianNameShort;
    }

    public void setLogisticianNameShort(String logisticianNameShort) {
        this.logisticianNameShort = logisticianNameShort;
    }

    public String getLogisticianNameFull() {
        return logisticianNameFull;
    }

    public void setLogisticianNameFull(String logisticianNameFull) {
        this.logisticianNameFull = logisticianNameFull;
    }

    public String getLogisticianPhone() {
        return logisticianPhone;
    }

    public void setLogisticianPhone(String logisticianPhone) {
        this.logisticianPhone = logisticianPhone;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public SourceWrapper[] getSourceWrappers() {
        return sourceWrappers;
    }

    public void setSourceWrappers(SourceWrapper[] sourceWrappers) {
        this.sourceWrappers = sourceWrappers;
    }

    public DestinationWrapper[] getDestinationWrappers() {
        return destinationWrappers;
    }

    public void setDestinationWrappers(DestinationWrapper[] destinationWrappers) {
        this.destinationWrappers = destinationWrappers;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
