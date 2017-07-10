package pro.likada.dao.daoImpl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.likada.dao.DriverDAO;
import pro.likada.model.Driver;
import pro.likada.util.HibernateUtil;

import javax.inject.Named;
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.List;

/**
 * Created by abuca on 13.03.17.
 */
@Named("driverDAO")
@Transactional
public class DriverDAOImpl implements DriverDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(DriverDAOImpl.class);

    @Override
    public Driver findById(long id) {
        LOGGER.info("Get Truck with an ID: {}", id);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Driver driver = (Driver) session.get(Driver.class, id);
        session.close();
        return driver;
    }

    @Override
    public void save(Driver driver){
        LOGGER.info("Saving the Driver: {}", driver);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(driver);
        transaction.commit();
        session.flush();
        session.close();
    }

    @Override
    public List<Driver> findAllDrivers(String searchString){ //TODO: implement search
        LOGGER.info("Get all Drivers, by search string: {}",searchString);
        LOGGER.info("Get All Drives");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Driver.class);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);//To avoid duplicates.
        List<Driver> drivers = (List<Driver>)criteria.list();
        session.close();
        return drivers;
    }

    @Override
    public Long findAmountOfVehiclesWithDriversConnectedWithDriver(Driver driver){
        LOGGER.info("Get amount of vehicles with drivers, connected with Driver: {}", driver);
        Session session = HibernateUtil.getSessionFactory().openSession();
        if(driver.getId() == null){
            return 0L;
        }
        Long amount = ((BigInteger) session.createSQLQuery("SELECT COUNT(*) FROM vehicles_drivers WHERE driver_id = ?")
                .setParameter(0,driver.getId())
                .uniqueResult()).longValue();
        session.close();
        return amount;
    }

    @Override
    public void deleteById(long id){
        LOGGER.info("Delete Driver with an ID: {}", id);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(findById(id));
        transaction.commit();
        session.flush();
        session.close();
    }
}
