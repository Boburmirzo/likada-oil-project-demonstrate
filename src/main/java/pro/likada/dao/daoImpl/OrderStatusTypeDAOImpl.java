package pro.likada.dao.daoImpl;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.likada.dao.OrderStatusTypeDAO;
import pro.likada.model.OrderStatusType;
import pro.likada.util.HibernateUtil;

import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Yusupov on 3/16/2017.
 */
@SuppressWarnings("unchecked")
@Named("orderStatusTypeDAO")
@Transactional
public class OrderStatusTypeDAOImpl implements OrderStatusTypeDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderStatusTypeDAOImpl.class);

    @Override
    public List<OrderStatusType> getAllOrderStatusTypes() {
        LOGGER.info("Get All OrderStatusTypes");
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<OrderStatusType> orderStatusTypes = (List<OrderStatusType>) session.createCriteria(OrderStatusType.class).setCacheable(true).list();
        session.close();
        return orderStatusTypes;
    }

    @Override
    public OrderStatusType findByType(String type) throws HibernateException {
        LOGGER.info("Get OrderStatusType with type: {}", type);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(OrderStatusType.class);
        criteria.add(Restrictions.eq("type", type));
        criteria.setCacheable(true);
        OrderStatusType orderStatusType = (OrderStatusType) criteria.uniqueResult();
        session.close();
        return orderStatusType;
    }
}
