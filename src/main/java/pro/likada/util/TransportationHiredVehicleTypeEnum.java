package pro.likada.util;

/**
 * Created by Yusupov on 3/14/2017.
 */
public enum TransportationHiredVehicleTypeEnum {

    SEARCH("SEARCH", "Поиск"),
    INTERNAL("INTERNAL", "Наш"),
    EXTERNAL("EXTERNAL", "Внешний");

    private String type;
    private String name;

    TransportationHiredVehicleTypeEnum(String type, String name) {
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
