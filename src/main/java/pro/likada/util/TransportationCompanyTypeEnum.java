package pro.likada.util;

/**
 * Created by Yusupov on 3/14/2017.
 */
public enum TransportationCompanyTypeEnum {

    HIRED("HIRED", "Наёмный"),
    SELF("SELF", "Самовывоз"),
    TRANSPORT("TRANSPORT", "ТЗР");

    private String type;
    private String name;

    TransportationCompanyTypeEnum(String type, String name) {
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
