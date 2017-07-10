package pro.likada.dao.daoImpl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.likada.dao.DriveStateDAO;
import pro.likada.model.DriveState;
import pro.likada.util.HibernateUtil;

import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by bumur on 28.03.2017.
 */
@SuppressWarnings("unchecked")
@Named("driveStateDAO")
@Transactional
public class DriveStateDAOImpl implements DriveStateDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(DriveStateDAOImpl.class);


    @Override
    public DriveState findById(long id) {
        LOGGER.info("Get DriveState with an ID: {}", id);
        Session session = HibernateUtil.getSessionFactory().openSession();
        DriveState driveState = (DriveState) session.createCriteria(DriveState.class).add(Restrictions.idEq(id)).uniqueResult();
        session.close();
        return driveState;
    }

    @Override
    public void deleteById(Long id) {
        LOGGER.info("Delete DriveState with an id:" + id);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(findById(id));
        transaction.commit();
        session.flush();
        session.close();
    }

    @Override
    public void save(DriveState driveState) {
        LOGGER.info("Saving a new DriveState: {}", driveState);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(driveState);
        transaction.commit();
        session.flush();
        session.close();
    }

    @Override
    public List<DriveState> getAllDriveStates() {
        LOGGER.info("Get All DriveStates");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria=session.createCriteria(DriveState.class);
        criteria.addOrder(Order.desc("modifiedDate"));
        List<DriveState> driveStates = (List<DriveState>)criteria.list();
        session.close();
        return driveStates;
    }
}
