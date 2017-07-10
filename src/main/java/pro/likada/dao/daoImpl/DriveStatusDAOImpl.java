package pro.likada.dao.daoImpl;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.likada.dao.DriveStatusDAO;
import pro.likada.model.DriveStatus;
import pro.likada.util.HibernateUtil;

import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by bumur on 27.03.2017.
 */
@SuppressWarnings("unchecked")
@Named("driveStatusDAO")
@Transactional
public class DriveStatusDAOImpl implements DriveStatusDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(DriveStatusDAOImpl.class);


    @Override
    public DriveStatus findById(long id) {
        LOGGER.info("Get DriveStatus with an ID: {}", id);
        Session session = HibernateUtil.getSessionFactory().openSession();
        DriveStatus driveStatus = (DriveStatus) session.createCriteria(DriveStatus.class).add(Restrictions.idEq(id)).uniqueResult();
        session.close();
        return driveStatus;
    }

    @Override
    public void deleteById(Long id) {
        LOGGER.info("Delete DriveStatus with an id:" + id);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(findById(id));
        transaction.commit();
        session.flush();
        session.close();
    }

    @Override
    public List<DriveStatus> getAllDriveStatuses() {
        LOGGER.info("Get All DriveStatuses");
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<DriveStatus> driveStatuses = (List<DriveStatus>) session.createCriteria(DriveStatus.class).list();
        session.close();
        return driveStatuses;
    }
}
