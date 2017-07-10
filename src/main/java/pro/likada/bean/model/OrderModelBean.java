package pro.likada.bean.model;


import pro.likada.model.Order;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Yusupov on 3/10/2017.
 */
@Named
@SessionScoped
public class OrderModelBean implements Serializable {

    private Order selectedOrder;

    /* GETTER/SETTER */

    public Order getSelectedOrder() {
        return selectedOrder;
    }

    public void setSelectedOrder(Order selectedOrder) {
        this.selectedOrder = selectedOrder;
    }

}
