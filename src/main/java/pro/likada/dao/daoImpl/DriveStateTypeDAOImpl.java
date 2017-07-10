package pro.likada.dao.daoImpl;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.likada.dao.DriveStateTypeDAO;
import pro.likada.model.DriveStateType;
import pro.likada.util.HibernateUtil;

import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by bumur on 28.03.2017.
 */
@SuppressWarnings("unchecked")
@Named("driveStateTypeDAO")
@Transactional
public class DriveStateTypeDAOImpl implements DriveStateTypeDAO{

    private static final Logger LOGGER = LoggerFactory.getLogger(DriveStateTypeDAOImpl.class);

    @Override
    public DriveStateType findById(long id) {
        LOGGER.info("Get DriveStateType with an ID: {}", id);
        Session session = HibernateUtil.getSessionFactory().openSession();
        DriveStateType driveStateType = (DriveStateType) session.createCriteria(DriveStateType.class).add(Restrictions.idEq(id)).uniqueResult();
        session.close();
        return driveStateType;
    }

    @Override
    public void save(DriveStateType driveStateType) {
        LOGGER.info("Saving a new DriveStateType: {}", driveStateType);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(driveStateType);
        transaction.commit();
        session.flush();
        session.close();
    }

    @Override
    public void deleteById(Long id) {
        LOGGER.info("Delete DriveStateType with an id:" + id);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(findById(id));
        transaction.commit();
        session.flush();
        session.close();
    }

    @Override
    public List<DriveStateType> getAllDriveStateTypes() {
        LOGGER.info("Get All DriveStateTypes");
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<DriveStateType> driveStateTypes = (List<DriveStateType>) session.createCriteria(DriveStateType.class).list();
        session.close();
        return driveStateTypes;
    }
}
