package pro.likada.util;

/**
 * Created by Yusupov on 3/14/2017.
 */
public enum  TransportationVehicleTypeEnum {

    AUTO("AUTO", "Авто"),
    RAILWAY("RAILWAY", "Ж/д");

    private String type;
    private String name;

    TransportationVehicleTypeEnum(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
