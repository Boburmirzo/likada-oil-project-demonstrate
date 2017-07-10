package pro.likada.util;

/**
 * Created by Yusupov on 3/15/2017.
 */
public enum OrderPaymentTypeEnum {

    FACT("FACT", "По факту"),
    PREPAYMENT("PREPAYMENT", "Предоплата"),
    POSTPAYMENT("POSTPAYMENT", "После выгрузки");

    private String type;
    private String name;

    OrderPaymentTypeEnum(String type, String name) {
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
