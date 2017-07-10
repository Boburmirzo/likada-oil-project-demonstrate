package pro.likada.service;

import org.hibernate.HibernateException;
import pro.likada.model.OrderStatusType;

import java.util.List;

/**
 * Created by Yusupov on 3/16/2017.
 */
public interface OrderStatusTypeService {

    List<OrderStatusType> getAllOrderStatusTypes();

    OrderStatusType findByType(String type) throws HibernateException;

}
