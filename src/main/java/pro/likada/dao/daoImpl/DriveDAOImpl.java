package pro.likada.dao.daoImpl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.likada.dao.DriveDAO;
import pro.likada.model.Drive;
import pro.likada.util.HibernateUtil;

import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by bumur on 27.03.2017.
 */
@SuppressWarnings("unchecked")
@Named("driveDAO")
@Transactional
public class DriveDAOImpl implements DriveDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(DriveDAOImpl.class);


    @Override
    public Drive findById(long id) {
        LOGGER.info("Get Drive with an ID: {}", id);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Drive drive = (Drive) session.createCriteria(Drive.class).add(Restrictions.idEq(id)).uniqueResult();
        session.close();
        return drive;
    }

    @Override
    public void deleteById(Long id) {
        LOGGER.info("Delete Drive with an id:" + id);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(findById(id));
        transaction.commit();
        session.flush();
        session.close();
    }

    @Override
    public void save(Drive drive) {
        LOGGER.info("Saving a new Drive: {}", drive);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(drive);
        transaction.commit();
        session.flush();
        session.close();
    }

    @Override
    public List<Drive> getAllDrives() {
        LOGGER.info("Get All Drives");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Drive.class);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);//To avoid duplicates.
        List<Drive> drives = (List<Drive>)criteria.list();
        session.close();
        return drives;
    }

    @Override
    public List<Drive> getAllDrivesOfTerminalForCar(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Drive.class, "drive");
        criteria.createAlias("drive.vehicle", "vehicle", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.eq("vehicle.terminalVehicleID.id", id));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return (List<Drive>)criteria.list();
    }
}
