package pro.likada.util;

/**
 * Created by Yusupov on 3/31/2017.
 */
public enum TelegramButtonMenuEnum {

    /* Likada Orders bot */
    MAIN_MENU("MAIN_MENU", "Главное меню"),
    ORGANIZATIONS("ORGANIZATIONS", "Список организаций"),
    CUSTOMERS("CUSTOMERS", "Клиенты"),
    CONTRACTORS("CONTRACTORS", "Контрагенты"),
    PRODUCT_CATEGORIES_AND_PRODUCTS("PRODUCT_CATEGORIES_AND_PRODUCTS", "Товары и категории товаров"),
    PRODUCT_MARKUP("PRODUCT_MARKUP", "Наценка"),
    TRANSPORTATION_PRICE("TRANSPORTATION_PRICE", "Стоимость доставки"),
    SHIPMENT_BASIS("SHIPMENT_BASIS", "Пункт загрузки"),
    PRODUCT_COUNT("PRODUCT_COUNT", "Обьем товара в тоннах"),
    UNLOAD_POINT("UNLOAD_POINT", "Пункт выгрузки"),
    UNLOAD_CONTACT("UNLOAD_CONTACT", "Контакты по выгрузки"),
    UNLOAD_DATE_PLAN("UNLOAD_DATE_PLAN", "Дата выгрузки"),
    ORDER_PAYMENT_TYPE("ORDER_PAYMENT_TYPE", "Тип оплаты"),
    UNLOAD_DESCRIPTION("UNLOAD_DESCRIPTION", "Примечание к выгрузке");

    private String type;
    private String name;

    TelegramButtonMenuEnum(String type, String name) {
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
