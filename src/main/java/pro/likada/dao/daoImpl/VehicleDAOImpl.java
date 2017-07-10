package pro.likada.dao.daoImpl;

import org.hibernate.*;
import org.hibernate.criterion.Order;
import org.hibernate.sql.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.likada.dao.VehicleDAO;
import pro.likada.model.Driver;
import pro.likada.model.Vehicle;
import pro.likada.rest.util.autograph.GUIDMigrationAssistant;
import pro.likada.util.HibernateUtil;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by abuca on 07.03.17.
 */
@Named("vehicleDAO")
@Transactional
public class VehicleDAOImpl implements VehicleDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(VehicleDAOImpl.class);

    @Override
    public Vehicle findById(long id){
        LOGGER.info("Get vehicle with an ID: {}", id);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Vehicle vehicle = (Vehicle) session.get(Vehicle.class,id);
        Hibernate.initialize(vehicle.getDrivers());
        for(Driver driver : vehicle.getDrivers()){
            Hibernate.initialize(driver.getVehicles());
        }
        session.close();
        return vehicle;
    }

    @Override
    public void save(Vehicle vehicle){
        LOGGER.info("Saving the Vehicle: {}", vehicle);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(vehicle);
        transaction.commit();
        session.flush();
        session.close();
    }

    @Override
    public List<Vehicle> findAllVehicles(String searchString) {
        LOGGER.info("Get all Vehicles, by search string: {}",searchString);
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Vehicle> vehicles = session.createCriteria(Vehicle.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .setCacheable(true)
                .list();
        for(Vehicle vehicle : vehicles){
            Hibernate.initialize(vehicle.getDrivers());
            for(Driver driver : vehicle.getDrivers()){
                Hibernate.initialize(driver.getVehicles());
            }
        }
        session.close();
        return vehicles;
    }

    public Long findAmountOfVehicleWithDriversConnectedWithVehicle(Vehicle vehicle){
        LOGGER.info("Get amount of drivers connected with Vehicle: {}", vehicle);
        Session session = HibernateUtil.getSessionFactory().openSession();
        if(vehicle.getId() == null){
            return 0L;
        }
        Long amount = ((BigInteger) session.createSQLQuery("SELECT COUNT(*) FROM vehicles_drivers WHERE vehicle_id = ?")
                .setParameter(0,vehicle.getId())
                .uniqueResult()).longValue();
        session.close();
        return amount;
    }

    @Override
    public void deleteById(long id){
        LOGGER.info("Delete Vehicle with an ID: {}",id);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(findById(id));
        transaction.commit();
        session.flush();
        session.close();
    }
}
