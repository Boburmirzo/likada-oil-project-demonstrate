package pro.likada.service.serviceImpl;

import org.hibernate.HibernateException;
import pro.likada.dao.TelegramButtonMenuDAO;
import pro.likada.model.TelegramButtonMenu;
import pro.likada.service.TelegramButtonMenuService;
import pro.likada.util.TelegramButtonMenuEnum;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Yusupov on 4/2/2017.
 */
@Named("telegramButtonMenuService")
@Transactional
public class TelegramButtonMenuServiceImpl implements TelegramButtonMenuService {

    @Inject
    private TelegramButtonMenuDAO telegramButtonMenuDAO;

    @Override
    public TelegramButtonMenu findById(Long id) throws HibernateException {
        return telegramButtonMenuDAO.findById(id);
    }

    @Override
    public TelegramButtonMenu findByType(String type) throws HibernateException {
        return telegramButtonMenuDAO.findByType(type);
    }

    @Override
    public TelegramButtonMenu findByName(String name) throws HibernateException {
        return telegramButtonMenuDAO.findByName(name);
    }

    @Override
    public List<TelegramButtonMenu> findAll() {
        return telegramButtonMenuDAO.findAll();
    }

    @Override
    public void save(TelegramButtonMenu telegramButtonMenu) throws HibernateException {
        telegramButtonMenuDAO.save(telegramButtonMenu);
    }

    @Override
    public void delete(TelegramButtonMenu telegramButtonMenu) throws HibernateException {
        telegramButtonMenuDAO.delete(telegramButtonMenu);
    }

    @Override
    public void deleteById(Long id) throws HibernateException {
        telegramButtonMenuDAO.deleteById(id);
    }

    @Override
    public TelegramButtonMenu getNextMenu(TelegramButtonMenu telegramButtonMenu) throws HibernateException {
        if(TelegramButtonMenuEnum.MAIN_MENU.getType().equals(telegramButtonMenu.getType())){
            return telegramButtonMenuDAO.findByType(TelegramButtonMenuEnum.ORGANIZATIONS.getType());
        } else if (TelegramButtonMenuEnum.ORGANIZATIONS.getType().equals(telegramButtonMenu.getType())){
            return telegramButtonMenuDAO.findByType(TelegramButtonMenuEnum.CUSTOMERS.getType());
        } else if (TelegramButtonMenuEnum.CUSTOMERS.getType().equals(telegramButtonMenu.getType())){
            return telegramButtonMenuDAO.findByType(TelegramButtonMenuEnum.PRODUCT_CATEGORIES_AND_PRODUCTS.getType());
        } else if (TelegramButtonMenuEnum.CONTRACTORS.getType().equals(telegramButtonMenu.getType())){
            return telegramButtonMenuDAO.findByType(TelegramButtonMenuEnum.PRODUCT_CATEGORIES_AND_PRODUCTS.getType());
        } else if (TelegramButtonMenuEnum.PRODUCT_CATEGORIES_AND_PRODUCTS.getType().equals(telegramButtonMenu.getType())){
            return telegramButtonMenuDAO.findByType(TelegramButtonMenuEnum.PRODUCT_MARKUP.getType());
        } else if (TelegramButtonMenuEnum.PRODUCT_MARKUP.getType().equals(telegramButtonMenu.getType())){
            return telegramButtonMenuDAO.findByType(TelegramButtonMenuEnum.TRANSPORTATION_PRICE.getType());
        } else if (TelegramButtonMenuEnum.TRANSPORTATION_PRICE.getType().equals(telegramButtonMenu.getType())){
            return telegramButtonMenuDAO.findByType(TelegramButtonMenuEnum.SHIPMENT_BASIS.getType());
        } else if (TelegramButtonMenuEnum.SHIPMENT_BASIS.getType().equals(telegramButtonMenu.getType())){
            return telegramButtonMenuDAO.findByType(TelegramButtonMenuEnum.PRODUCT_COUNT.getType());
        } else if (TelegramButtonMenuEnum.PRODUCT_COUNT.getType().equals(telegramButtonMenu.getType())){
            return telegramButtonMenuDAO.findByType(TelegramButtonMenuEnum.UNLOAD_POINT.getType());
        } else if (TelegramButtonMenuEnum.UNLOAD_POINT.getType().equals(telegramButtonMenu.getType())){
            return telegramButtonMenuDAO.findByType(TelegramButtonMenuEnum.UNLOAD_CONTACT.getType());
        } else if (TelegramButtonMenuEnum.UNLOAD_CONTACT.getType().equals(telegramButtonMenu.getType())){
            return telegramButtonMenuDAO.findByType(TelegramButtonMenuEnum.UNLOAD_DATE_PLAN.getType());
        } else if (TelegramButtonMenuEnum.UNLOAD_DATE_PLAN.getType().equals(telegramButtonMenu.getType())){
            return telegramButtonMenuDAO.findByType(TelegramButtonMenuEnum.ORDER_PAYMENT_TYPE.getType());
        } else if (TelegramButtonMenuEnum.ORDER_PAYMENT_TYPE.getType().equals(telegramButtonMenu.getType())){
            return telegramButtonMenuDAO.findByType(TelegramButtonMenuEnum.UNLOAD_DESCRIPTION.getType());
        }
        return telegramButtonMenuDAO.findByType(TelegramButtonMenuEnum.MAIN_MENU.getType());
    }

    @Override
    public TelegramButtonMenu getPreviousMenu(TelegramButtonMenu telegramButtonMenu) throws HibernateException {
        if (TelegramButtonMenuEnum.ORGANIZATIONS.getType().equals(telegramButtonMenu.getType())){
            return telegramButtonMenuDAO.findByType(TelegramButtonMenuEnum.MAIN_MENU.getType());
        } else if (TelegramButtonMenuEnum.CUSTOMERS.getType().equals(telegramButtonMenu.getType())){
            return telegramButtonMenuDAO.findByType(TelegramButtonMenuEnum.ORGANIZATIONS.getType());
        } else if (TelegramButtonMenuEnum.CONTRACTORS.getType().equals(telegramButtonMenu.getType())){
            return telegramButtonMenuDAO.findByType(TelegramButtonMenuEnum.CUSTOMERS.getType());
        } else if (TelegramButtonMenuEnum.PRODUCT_CATEGORIES_AND_PRODUCTS.getType().equals(telegramButtonMenu.getType())){
            return telegramButtonMenuDAO.findByType(TelegramButtonMenuEnum.CUSTOMERS.getType());
        } else if (TelegramButtonMenuEnum.PRODUCT_MARKUP.getType().equals(telegramButtonMenu.getType())){
            return telegramButtonMenuDAO.findByType(TelegramButtonMenuEnum.PRODUCT_CATEGORIES_AND_PRODUCTS.getType());
        } else if (TelegramButtonMenuEnum.TRANSPORTATION_PRICE.getType().equals(telegramButtonMenu.getType())){
            return telegramButtonMenuDAO.findByType(TelegramButtonMenuEnum.PRODUCT_MARKUP.getType());
        } else if (TelegramButtonMenuEnum.SHIPMENT_BASIS.getType().equals(telegramButtonMenu.getType())){
            return telegramButtonMenuDAO.findByType(TelegramButtonMenuEnum.TRANSPORTATION_PRICE.getType());
        } else if (TelegramButtonMenuEnum.PRODUCT_COUNT.getType().equals(telegramButtonMenu.getType())){
            return telegramButtonMenuDAO.findByType(TelegramButtonMenuEnum.SHIPMENT_BASIS.getType());
        } else if (TelegramButtonMenuEnum.UNLOAD_POINT.getType().equals(telegramButtonMenu.getType())){
            return telegramButtonMenuDAO.findByType(TelegramButtonMenuEnum.PRODUCT_COUNT.getType());
        } else if (TelegramButtonMenuEnum.UNLOAD_CONTACT.getType().equals(telegramButtonMenu.getType())){
            return telegramButtonMenuDAO.findByType(TelegramButtonMenuEnum.UNLOAD_POINT.getType());
        } else if (TelegramButtonMenuEnum.UNLOAD_DATE_PLAN.getType().equals(telegramButtonMenu.getType())){
            return telegramButtonMenuDAO.findByType(TelegramButtonMenuEnum.UNLOAD_CONTACT.getType());
        } else if (TelegramButtonMenuEnum.UNLOAD_CONTACT.getType().equals(telegramButtonMenu.getType())){
            return telegramButtonMenuDAO.findByType(TelegramButtonMenuEnum.ORDER_PAYMENT_TYPE.getType());
        } else if (TelegramButtonMenuEnum.ORDER_PAYMENT_TYPE.getType().equals(telegramButtonMenu.getType())){
            return telegramButtonMenuDAO.findByType(TelegramButtonMenuEnum.UNLOAD_DESCRIPTION.getType());
        }
        return telegramButtonMenuDAO.findByType(TelegramButtonMenuEnum.MAIN_MENU.getType());
    }

    @Override
    public TelegramButtonMenu getMainMenu() {
        return telegramButtonMenuDAO.findByType(TelegramButtonMenuEnum.MAIN_MENU.getType());
    }

    @Override
    public TelegramButtonMenu getOrganizationsMenu() {
        return telegramButtonMenuDAO.findByType(TelegramButtonMenuEnum.ORGANIZATIONS.getType());
    }

    @Override
    public TelegramButtonMenu getCustomersMenu() {
        return telegramButtonMenuDAO.findByType(TelegramButtonMenuEnum.CUSTOMERS.getType());
    }

    @Override
    public TelegramButtonMenu getContractorsMenu() {
        return telegramButtonMenuDAO.findByType(TelegramButtonMenuEnum.CONTRACTORS.getType());
    }

    @Override
    public TelegramButtonMenu getProductCategoriesAndProductsMenu() {
        return telegramButtonMenuDAO.findByType(TelegramButtonMenuEnum.PRODUCT_CATEGORIES_AND_PRODUCTS.getType());
    }

    @Override
    public TelegramButtonMenu getProductMarkupMenu() {
        return telegramButtonMenuDAO.findByType(TelegramButtonMenuEnum.PRODUCT_MARKUP.getType());
    }

    @Override
    public TelegramButtonMenu getTransportationPriceMenu() {
        return telegramButtonMenuDAO.findByType(TelegramButtonMenuEnum.TRANSPORTATION_PRICE.getType());
    }

    @Override
    public TelegramButtonMenu getShipmentBasisMenu() {
        return telegramButtonMenuDAO.findByType(TelegramButtonMenuEnum.SHIPMENT_BASIS.getType());
    }

    @Override
    public TelegramButtonMenu getProductCountMenu() {
        return telegramButtonMenuDAO.findByType(TelegramButtonMenuEnum.PRODUCT_COUNT.getType());
    }

    @Override
    public TelegramButtonMenu getUnloadPointMenu() {
        return telegramButtonMenuDAO.findByType(TelegramButtonMenuEnum.UNLOAD_POINT.getType());
    }

    @Override
    public TelegramButtonMenu getUnloadContactMenu() {
        return telegramButtonMenuDAO.findByType(TelegramButtonMenuEnum.UNLOAD_CONTACT.getType());
    }

    @Override
    public TelegramButtonMenu getUnloadDatePlanMenu() {
        return telegramButtonMenuDAO.findByType(TelegramButtonMenuEnum.UNLOAD_DATE_PLAN.getType());
    }

    @Override
    public TelegramButtonMenu getOrderPaymentTypeMenu() {
        return telegramButtonMenuDAO.findByType(TelegramButtonMenuEnum.ORDER_PAYMENT_TYPE.getType());
    }

    @Override
    public TelegramButtonMenu getUnloadDescriptionMenu() {
        return telegramButtonMenuDAO.findByType(TelegramButtonMenuEnum.UNLOAD_DESCRIPTION.getType());
    }
}
