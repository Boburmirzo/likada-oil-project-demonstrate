package pro.likada.bean.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.likada.model.Customer;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Created by Yusupov on 12/20/2016.
 */
@Named
@SessionScoped
public class CustomerModelBean implements Serializable{

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerModelBean.class);

    private Customer selectedCustomer;

    /* Constructors for bean (from specification rules) one with no-args, one with all fields*/

    public CustomerModelBean() {
    }

    public Customer getSelectedCustomer() {
        return selectedCustomer;
    }

    public void setSelectedCustomer(Customer selectedCustomer) {
        this.selectedCustomer = selectedCustomer;
    }
}
