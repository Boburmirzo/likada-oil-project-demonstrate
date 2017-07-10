package pro.likada.bean.backing;

import pro.likada.bean.model.OrderModelBean;
import pro.likada.model.Order;
import pro.likada.util.Constants;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Yusupov on 3/10/2017.
 */
@Named
@ViewScoped
public class OrderBackingBean implements Serializable {

    @Inject
    private OrderModelBean orderModelBean;

    /* Fields for Orders table view */
    private Order selectedOrder;
    private String searchStringInOrderTable;
    private String selectedFilterPeriodOneButton;
    private List<Order> ordersForTable;

    @PostConstruct
    public void init(){
        if(orderModelBean.getSelectedOrder()!=null)
            this.selectedOrder = orderModelBean.getSelectedOrder();
        if(selectedFilterPeriodOneButton==null)
            selectedFilterPeriodOneButton = Constants.FILTER_PERIOD_FOR_TODAY;
    }

    /* GETTER/SETTER */

    public Order getSelectedOrder() {
        return selectedOrder;
    }

    public void setSelectedOrder(Order selectedOrder) {
        this.selectedOrder = selectedOrder;
    }

    public String getSearchStringInOrderTable() {
        return searchStringInOrderTable;
    }

    public void setSearchStringInOrderTable(String searchStringInOrderTable) {
        this.searchStringInOrderTable = searchStringInOrderTable;
    }

    public String getSelectedFilterPeriodOneButton() {
        return selectedFilterPeriodOneButton;
    }

    public void setSelectedFilterPeriodOneButton(String selectedFilterPeriodOneButton) {
        this.selectedFilterPeriodOneButton = selectedFilterPeriodOneButton;
    }

    public List<Order> getOrdersForTable() {
        return ordersForTable;
    }

    public void setOrdersForTable(List<Order> ordersForTable) {
        this.ordersForTable = ordersForTable;
    }
}
