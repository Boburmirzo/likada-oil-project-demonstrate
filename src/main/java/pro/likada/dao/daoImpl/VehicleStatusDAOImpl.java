package pro.likada.dao.daoImpl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.likada.dao.VehicleStatusDAO;
import pro.likada.model.VehicleLogisticianStatus;
import pro.likada.model.VehicleStatus;
import pro.likada.util.HibernateUtil;

import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by abuca on 14.03.17.
 */
@Named("vehicleStatusDAO")
@Transactional
public class VehicleStatusDAOImpl implements VehicleStatusDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(VehicleStatusDAOImpl.class);

    @Override
    public VehicleStatus findById(long id){
        LOGGER.info("Get vehicle status with an ID: {}",id);
        Session session = HibernateUtil.getSessionFactory().openSession();
        VehicleStatus vehicleStatus = (VehicleStatus) session.get(VehicleStatus.class,id);
        session.close();
        return vehicleStatus;
    }

    @Override
    public void save(VehicleStatus vehicleStatus){
        LOGGER.info("Saving the VehicleStatus: {}",vehicleStatus);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        if(vehicleStatus.getLogisticianStatus() != null) {
            session.saveOrUpdate(vehicleStatus.getLogisticianStatus());
        }
        if(vehicleStatus.getMechanicalStatus() != null) {
            session.saveOrUpdate(vehicleStatus.getMechanicalStatus());
        }
        session.saveOrUpdate(vehicleStatus);
        transaction.commit();
        session.flush();
        session.close();
    }


    @Override
    public List<VehicleStatus> findAllVehicleStatuses(String searchString) {
        LOGGER.info("Get all VehicleStatuses, by search string: {}",searchString);
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<VehicleStatus> vehicleStatuses = session
                .createCriteria(VehicleStatus.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .setCacheable(true)
                .list();
        session.close();
        return vehicleStatuses;
    }

    @Override
    public void deleteById(long id){
        LOGGER.info("Delete VehicleStatus with and id: {}",id);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(findById(id));
        transaction.commit();
        session.flush();
        session.close();
    }
}
