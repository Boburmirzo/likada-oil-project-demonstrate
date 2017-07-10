package pro.likada.service;

import org.hibernate.HibernateException;
import pro.likada.model.TelegramButtonMenu;

import java.util.List;

/**
 * Created by Yusupov on 4/2/2017.
 */
public interface TelegramButtonMenuService {

    TelegramButtonMenu findById(Long id) throws HibernateException;

    TelegramButtonMenu findByType(String type) throws HibernateException;

    TelegramButtonMenu findByName(String name) throws HibernateException;

    List<TelegramButtonMenu> findAll();

    void save(TelegramButtonMenu telegramButtonMenu) throws HibernateException;

    void delete(TelegramButtonMenu telegramButtonMenu) throws HibernateException;

    void deleteById(Long id) throws HibernateException;

    TelegramButtonMenu getNextMenu(TelegramButtonMenu telegramButtonMenu) throws HibernateException;

    TelegramButtonMenu getPreviousMenu(TelegramButtonMenu telegramButtonMenu) throws HibernateException;

    TelegramButtonMenu getMainMenu();

    TelegramButtonMenu getOrganizationsMenu();

    TelegramButtonMenu getCustomersMenu();

    TelegramButtonMenu getContractorsMenu();

    TelegramButtonMenu getProductCategoriesAndProductsMenu();

    TelegramButtonMenu getProductMarkupMenu();

    TelegramButtonMenu getTransportationPriceMenu();

    TelegramButtonMenu getShipmentBasisMenu();

    TelegramButtonMenu getProductCountMenu();

    TelegramButtonMenu getUnloadPointMenu();

    TelegramButtonMenu getUnloadContactMenu();

    TelegramButtonMenu getUnloadDatePlanMenu();

    TelegramButtonMenu getOrderPaymentTypeMenu();

    TelegramButtonMenu getUnloadDescriptionMenu();

}
