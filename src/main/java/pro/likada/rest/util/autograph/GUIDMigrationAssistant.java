package pro.likada.rest.util.autograph;

import org.hibernate.Session;
import pro.likada.model.Vehicle;
import pro.likada.rest.client.autograph.AutographRestClient;
import pro.likada.rest.client.autograph.Impl.AutographRestClientImpl;
import pro.likada.rest.wrapper.autograph.RDeviceItemWrapper;
import pro.likada.rest.wrapper.autograph.REnumDevicesWrapper;
import pro.likada.rest.wrapper.autograph.RPropertyWrapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by abuca on 24.03.17.
 */
public class GUIDMigrationAssistant {
    private AutographRestClientImpl autographRestClient = new AutographRestClientImpl();

    public List<Vehicle> migrate(List<Vehicle> vehicleList){
        autographRestClient.init();
        REnumDevicesWrapper devicesInfoWrapper = autographRestClient.getDevicesInfo();
        Map<String,String> govNumberToGUIDMap = new HashMap<>();
        Map<String,Long> govNumberToDevIDMap = new HashMap<>();
        for(RDeviceItemWrapper deviceInfo : devicesInfoWrapper.getDevicesInfo()){
            String govNumber = "";
            for(RPropertyWrapper propertyWrapper : deviceInfo.getProperties()){
                if(propertyWrapper.getName().equals("VehicleRegNumber") && propertyWrapper.getValue().length() == 6){
                    govNumber = convertGovNumber(propertyWrapper.getValue());
                }
            }
            if(govNumber.length() > 0){
                govNumberToGUIDMap.put(govNumber,deviceInfo.getGUID());
                govNumberToDevIDMap.put(govNumber, Long.valueOf(deviceInfo.getSerial()));
            }
        }
        for(Vehicle vehicle : vehicleList){
            if(govNumberToGUIDMap.containsKey(vehicle.getTruck().getGovNumber())){
               // vehicle.setGUID(govNumberToGUIDMap.get(vehicle.getTruck().getGovNumber()));
                vehicle.setAutographDeviceID(govNumberToDevIDMap.get(vehicle.getTruck().getGovNumber()));
            }
        }
        return vehicleList;
    }

    public String convertGovNumber(String govNumber){
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder
                .append(govNumber.charAt(3))
                .append(govNumber.substring(0,3))
                .append(govNumber.substring(4,6))
                .append(" 116")
                .toString()
                .toUpperCase();
    }
}
