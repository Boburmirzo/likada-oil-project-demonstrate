package pro.likada.util;

/**
 * Created by Yusupov on 1/3/2017.
 */
public enum ModelConstantEnum {

    CUSTOMER("customer"),
    CONTRACTOR("contractor"),
    AGREEMENT("agreement"),
    PRODUCT("product"),
    PRODUCT_TYPE("product_type"),
    ORDER("order"),
    BASIS("bases"),
    CARRIER("carrier"),
    PROVIDER("provider"),
    COMPANY("company"),
    TRUCK("truck"),
    TRAILER("trailer"),
    FINANCIAL_ITEM("financial_item"),
    VEHICLE("vehicle"),
    VEHICLE_STATUS("vehicle_status"),
    DRIVER("driver");

    private String modelName;

    ModelConstantEnum(String modelName) {
        this.modelName = modelName;
    }

    public String getModelName() {
        return modelName;
    }
}
