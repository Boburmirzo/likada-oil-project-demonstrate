package pro.likada.util;

/**
 * Created by Yusupov on 3/11/2017.
 */
public enum OrderStatusTypeEnum {

    DELETED(100,"DELETED", "Удалена"),
    CANCELED(200, "CANCELED", "Отменён"),
    NEW(300, "NEW", "Новый"),
    ON_CONFIRMATION(400, "ON_CONFIRMATION", "На подтверждении"),
    COMMERCIAL_DIRECTOR_CONFIRMED(420, "COMMERCIAL_DIRECTOR_CONFIRMED", "Подтверждён коммерческим директором"),
    SECURITY_SERVICE_CONFIRMED(440, "SECURITY_SERVICE_CONFIRMED", "Подтверждён СБ"),
    ON_HIRED_TRANSPORT_SELECTION(500, "ON_HIRED_TRANSPORT_SELECTION", "Подбор внешнего транспорта"),
    ON_LOAD_REQUEST(600, "ON_LOAD_REQUEST", "Запрос на загрузку"),
    SENT_TO_LOAD(620, "SENT_TO_LOAD", "Отправлен на погрузку"),
    SHIPPED(700, "SHIPPED", "В пути к клиенту"),
    ON_UNLOAD_REQUEST(800, "ON_UNLOAD_REQUEST", "Запрос на отгрузку"),
    UNLOAD(820, "UNLOAD", "Отгрузка"),
    DELIVERED(900, "DELIVERED", "Доставлен"),
    ON_PURCHASE_REVIEW(1000, "ON_PURCHASE_REVIEW", "Обработка закупом"),
    CLOSED(1100, "CLOSED", "Закрыт"),
    CONDUCTED(1200, "CONDUCTED", "Проведена");

    private Integer order;
    private String type;
    private String name;

    OrderStatusTypeEnum(Integer order, String type, String name) {
        this.order = order;
        this.type = type;
        this.name = name;
    }

    public Integer getOrder() {
        return order;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
