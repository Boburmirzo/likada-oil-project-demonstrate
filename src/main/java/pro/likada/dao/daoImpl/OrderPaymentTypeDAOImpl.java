package pro.likada.dao.daoImpl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.likada.dao.OrderPaymentTypeDAO;
import pro.likada.model.OrderPaymentType;
import pro.likada.util.HibernateUtil;

import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Yusupov on 3/20/2017.
 */
@SuppressWarnings("unchecked")
@Named("orderPaymentTypeDAO")
@Transactional
public class OrderPaymentTypeDAOImpl implements OrderPaymentTypeDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderPaymentTypeDAOImpl.class);

    @Override
    public List<OrderPaymentType> getAllOrderPaymentTypes() {
        LOGGER.info("Get All OrderPaymentTypes");
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<OrderPaymentType> orderPaymentTypes = (List<OrderPaymentType>) session.createCriteria(OrderPaymentType.class).setCacheable(true).list();
        session.close();
        return orderPaymentTypes;
    }

    @Override
    public OrderPaymentType getOrderTypeByName(String name) {
        LOGGER.info("Get an Order with name: {}", name);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(OrderPaymentType.class);
        criteria.add(Restrictions.eq("name", name));
        OrderPaymentType orderPaymentType = (OrderPaymentType) criteria.uniqueResult();
        session.close();
        return orderPaymentType;
    }
}
