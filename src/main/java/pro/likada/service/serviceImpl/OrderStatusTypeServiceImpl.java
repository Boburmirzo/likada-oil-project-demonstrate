package pro.likada.service.serviceImpl;

import org.hibernate.HibernateException;
import pro.likada.dao.OrderStatusTypeDAO;
import pro.likada.model.OrderStatusType;
import pro.likada.service.OrderStatusTypeService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Yusupov on 3/16/2017.
 */
@Named("orderStatusTypeService")
@Transactional
public class OrderStatusTypeServiceImpl implements OrderStatusTypeService, Serializable {

    @Inject
    private OrderStatusTypeDAO orderStatusTypeDAO;

    @Override
    public List<OrderStatusType> getAllOrderStatusTypes() {
        return orderStatusTypeDAO.getAllOrderStatusTypes();
    }

    @Override
    public OrderStatusType findByType(String type) throws HibernateException {
        return orderStatusTypeDAO.findByType(type);
    }
}
