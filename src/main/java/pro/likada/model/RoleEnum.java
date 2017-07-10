package pro.likada.model;

import java.io.Serializable;

/**
 * Created by Yusupov on 12/14/2016.
 */
public enum RoleEnum implements Serializable  {

    SALES_MANAGER("ROLE_SALES_MANAGER", "Менеджер продаж"),
    LOGISTICS_MANAGER("ROLE_LOGISTICS_MANAGER", "Менеджер Логист"),
    SALES_MANAGER_ASSISTANT("ROLE_SALES_MANAGER_ASSISTANT", "Помощник Менеджера продаж"),
    ADMIN("ROLE_ADMIN", "Админ"),
    MODERATOR("ROLE_MODERATOR", "Модератор"),
    OBSERVER("ROLE_OBSERVER", "Наблюдатель"),
    SALES_DIRECTOR("ROLE_SALES_DIRECTOR", "Руководитель продаж"),
    LOGISTICS_DIRECTOR("ROLE_LOGISTICS_DIRECTOR", "Руководитель логистики"),
    LOGISTICS("ROLE_LOGISTICS", "Логист"),
    BUYER("ROLE_BUYER", "Закуп"),
    LOADING_MANAGER("ROLE_LOADING_MANAGER", "Менеджер по загрузке"),
    SHIPMENT_OPERATOR("ROLE_SHIPMENT_OPERATOR", "Оператор отгрузки"),
    HIRED_TRANSPORT_OPERATOR("ROLE_HIRED_TRANSPORT_OPERATOR", "Оператор наёмного транспорта"),
    SECURITY_WORK_APPLICATION("ROLE_SECURITY_WORK_APPLICATION", "СБ Заявка в работу"),
    SECURITY_UNLOADING("ROLE_SECURITY_UNLOADING", "СБ На выгрузку"),
    YURIST("ROLE_YURIST", "Юрист"),
    TERMINAL_VEHICLE("ROLE_TERMINAL_VEHICLE", "Терминал ТС");

    private String roleType;
    private String roleName;

    RoleEnum(String roleType, String roleName){
        this.roleType = roleType;
        this.roleName = roleName;
    }

    public String getRoleType(){
        return roleType;
    }
    public String getRoleName(){
        return roleName;
    }

}
