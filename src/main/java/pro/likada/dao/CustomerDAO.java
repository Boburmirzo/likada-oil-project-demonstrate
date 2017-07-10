package pro.likada.dao;

import pro.likada.model.Customer;
import pro.likada.model.User;

import java.util.List;

/**
 * Created by Yusupov on 12/19/2016.
 */
public interface CustomerDAO {

    Customer findById(long id);

    List<Customer> findByTitle(String title);

    void save(Customer customer);

    void deleteById(long id);

    void deleteByTitle(String title);

    List<Customer> findAllCustomers(String searchStringTitle);

    List<Customer> findAllCustomersBasedOnUserRole(User user, Integer startFrom, Integer maxNumberOfResults);

    Long countCustomersBasedOnUserRole(User user);
}
