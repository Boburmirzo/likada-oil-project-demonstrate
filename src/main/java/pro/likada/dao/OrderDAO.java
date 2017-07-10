package pro.likada.dao;

import org.hibernate.HibernateException;
import pro.likada.model.Customer;
import pro.likada.model.Order;
import pro.likada.model.TelegramUser;

import java.util.Date;
import java.util.List;

/**
 * Created by Yusupov on 3/10/2017.
 */
public interface OrderDAO {

    Order findById(long id);

    Order findByTelegramUserAndSubmissionStatus(TelegramUser telegramUser, Boolean submittedAlready) throws HibernateException;

    List<Order> findAllOrdersByCustomer(Customer customer);

    void save(Order order);

    void delete(Order order);

    void deleteById(long id);

    List<Order> findAllOrdersBasedOnSearchStringAndFilterPeriod(String searchString, Date from, Date to);

}
