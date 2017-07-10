package pro.likada.service.serviceImpl;

import pro.likada.dao.OrderPaymentTypeDAO;
import pro.likada.model.OrderPaymentType;
import pro.likada.service.OrderPaymentTypeService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Yusupov on 3/20/2017.
 */
@Named("orderPaymentTypeService")
@Transactional
public class OrderPaymentTypeServiceImpl implements OrderPaymentTypeService {

    @Inject
    private OrderPaymentTypeDAO orderPaymentTypeDAO;

    @Override
    public List<OrderPaymentType> getAllOrderPaymentTypes() {
        return orderPaymentTypeDAO.getAllOrderPaymentTypes();
    }

    @Override
    public OrderPaymentType getOrderTypeByName(String name) {
        return orderPaymentTypeDAO.getOrderTypeByName(name);
    }
}
