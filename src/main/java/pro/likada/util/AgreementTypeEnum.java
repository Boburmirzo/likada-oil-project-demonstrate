package pro.likada.util;

/**
 * Created by Yusupov on 3/20/2017.
 */
public enum AgreementTypeEnum {

    SALE("SALE", "Продажа"),
    TRANSPORT("TRANSPORT", "Транспорт"),
    SALE_TRANSPORT("SALE_TRANSPORT", "Продажа + Транспорт");

    private String type;
    private String name;

    AgreementTypeEnum(String type, String name) {
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
