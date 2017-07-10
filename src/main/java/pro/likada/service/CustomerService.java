package pro.likada.service;

import pro.likada.model.Customer;
import pro.likada.model.User;

import java.util.List;

/**
 * Created by Yusupov on 12/19/2016.
 */
public interface CustomerService {

    Customer findById(long id);

    List<Customer> findByTitle(String title);

    void save(Customer customer);

    void deleteById(long id);

    void deleteByTitle(String title);

    List<Customer> findAllCustomers(String searchStringTitle);

    List<Customer> findAllCustomersBasedOnUserRole(User user, Integer currentIndex, Integer maxNumberOfResults);

    Long countCustomersBasedOnUserRole(User user);
}
