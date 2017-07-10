package pro.likada.dao.daoImpl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.likada.dao.InPaymentWayDAO;
import pro.likada.model.InPaymentWay;
import pro.likada.util.HibernateUtil;

import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by bumur on 27.02.2017.
 */
@SuppressWarnings("unchecked")
@Named("inPaymentTypeDAO")
@Transactional
public class InPaymentWayDAOImpl implements InPaymentWayDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(InPaymentWayDAOImpl.class);

    @Override
    public InPaymentWay findById(long id) {
        LOGGER.info("Get InPaymentWay with an ID: {}", id);
        Session session = HibernateUtil.getSessionFactory().openSession();
        InPaymentWay inPaymentType = (InPaymentWay) session.createCriteria(InPaymentWay.class).add(Restrictions.idEq(id)).uniqueResult();
        session.close();
        return inPaymentType;
    }

    @Override
    public void deleteById(Long id) {
        LOGGER.info("Delete InPaymentWay with an id:" + id);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(findById(id));
        transaction.commit();
        session.flush();
        session.close();
    }

    @Override
    public void save(InPaymentWay inPaymentWay) {
        LOGGER.info("Saving a InPaymentWay: {}", inPaymentWay);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(inPaymentWay);
        transaction.commit();
        session.flush();
        session.close();
    }

    @Override
    public List<InPaymentWay> getAllInpaymentWaysByFinancialItemId(Long id) {
        LOGGER.info("Get InPaymentWays with FinancialItem id: {}", id);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(InPaymentWay.class);
        criteria.add(Restrictions.eq("financialItem.id",id));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List<InPaymentWay> inPaymentWays = (List<InPaymentWay>) criteria.list();
        session.close();
        return inPaymentWays;
    }
}
