package pro.likada.util;

/**
 * Created by Yusupov on 1/3/2017.
 */
public enum  AccessTypeEnum {

    VIEW("view"),
    ADD("add"),
    EDIT("edit"),
    DELETE("delete");

    private String accessName;

    AccessTypeEnum(String access) {
        this.accessName = access;
    }

    public String getAccessName() {
        return accessName;
    }
}
