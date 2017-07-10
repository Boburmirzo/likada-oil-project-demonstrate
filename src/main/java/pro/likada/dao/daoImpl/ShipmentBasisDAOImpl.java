package pro.likada.dao.daoImpl;

import org.hibernate.Criteria;
import org.hibernate.NullPrecedence;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.likada.dao.ShipmentBasisDAO;
import pro.likada.model.ShipmentBasis;
import pro.likada.util.HibernateUtil;

import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by bumur on 13.02.2017.
 */
@SuppressWarnings("unchecked")
@Named("shipmentBasisDAO")
@Transactional
public class ShipmentBasisDAOImpl implements ShipmentBasisDAO{

    private static final Logger LOGGER = LoggerFactory.getLogger(ShipmentBasisDAOImpl.class);

    @Override
    public ShipmentBasis findById(long id) {
        LOGGER.info("Get ShipmentBasis with an ID: {}", id);
        Session session = HibernateUtil.getSessionFactory().openSession();
        ShipmentBasis shipmentBasis =(ShipmentBasis) session.createCriteria(ShipmentBasis.class).add(Restrictions.idEq(id)).uniqueResult();
        session.close();
        return shipmentBasis;
    }

    @Override
    public List<ShipmentBasis> getAllShipmentBasis() {
        LOGGER.info("Get All ShipmentBasis");
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<ShipmentBasis> shipmentBases = (List<ShipmentBasis>) session.createCriteria(ShipmentBasis.class).list();
        session.close();
        return shipmentBases;
    }

    @Override
    public void deleteById(Long id) {
        LOGGER.info("Delete ShipmentBase with an id:" + id);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(findById(id));
        transaction.commit();
        session.flush();
        session.close();
    }

    @Override
    public void save(ShipmentBasis shipmentBasis) {
        LOGGER.info("Saving a ShipmentBase: {}", shipmentBasis);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(shipmentBasis);
        transaction.commit();
        session.flush();
        session.close();
    }

    @Override
    public List<ShipmentBasis> getProductTypesOrderByTurn() {
        LOGGER.info("Get ShipmentBasis Order by Turn");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(ShipmentBasis.class);
        criteria.addOrder(Order.asc("turn"));
        criteria.addOrder(Order.asc("nameShort"));
        criteria.addOrder(Order.asc("id"));
        List<ShipmentBasis> shipmentBases = criteria.list();
        session.close();
        return shipmentBases;
    }

    @Override
    public List<ShipmentBasis> findAllShipmentBasisByNameShort(String searchString) {
        LOGGER.info("Find ShipmentBasis by searchString");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(ShipmentBasis.class, "shipment_basis");
        if(searchString!=null && !searchString.isEmpty())
            criteria.add(Restrictions.ilike("shipment_basis.nameShort", searchString, MatchMode.EXACT));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);//To avoid duplicates.
        criteria.addOrder(Order.asc("nameShort"));
        criteria.setCacheable(true);
        List<ShipmentBasis> shipmentBases = (List<ShipmentBasis>)criteria.list();
        session.close();
        return shipmentBases;
    }

    @Override
    public List<ShipmentBasis> getShipmentBasisByIdInDescendingOrder() {
        LOGGER.info("Get ShipmentBasis Order by Id in descending order");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(ShipmentBasis.class);
        criteria.addOrder(Order.desc("id"));
        List<ShipmentBasis> shipmentBases = criteria.list();
        session.close();
        return shipmentBases;
    }
}
