package pro.likada.service;

import pro.likada.model.OrderPaymentType;

import java.util.List;

/**
 * Created by Yusupov on 3/20/2017.
 */
public interface OrderPaymentTypeService {

    List<OrderPaymentType> getAllOrderPaymentTypes();

    OrderPaymentType getOrderTypeByName(String name);


}
