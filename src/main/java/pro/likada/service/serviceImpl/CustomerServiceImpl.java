package pro.likada.service.serviceImpl;

import pro.likada.dao.CustomerDAO;
import pro.likada.model.Customer;
import pro.likada.model.User;
import pro.likada.service.CustomerService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Yusupov on 12/19/2016.
 */
@Named("customerService")
@Transactional
public class CustomerServiceImpl implements CustomerService {

    @Inject
    private CustomerDAO customerDAO;

    @Override
    public Customer findById(long id) {
        return customerDAO.findById(id);
    }

    @Override
    public List<Customer> findByTitle(String title) {
        return customerDAO.findByTitle(title);
    }

    @Override
    public void save(Customer customer) {
        customerDAO.save(customer);
    }

    @Override
    public void deleteById(long id) {
        customerDAO.deleteById(id);
    }

    @Override
    public void deleteByTitle(String title) {
        customerDAO.deleteByTitle(title);
    }

    @Override
    public List<Customer> findAllCustomers(String searchStringTitle) {
        return customerDAO.findAllCustomers(searchStringTitle);
    }

    @Override
    public List<Customer> findAllCustomersBasedOnUserRole(User user, Integer startFrom, Integer maxNumberOfResults) {
        return customerDAO.findAllCustomersBasedOnUserRole(user, startFrom, maxNumberOfResults);
    }

    @Override
    public Long countCustomersBasedOnUserRole(User user) {
        return customerDAO.countCustomersBasedOnUserRole(user);
    }

}
