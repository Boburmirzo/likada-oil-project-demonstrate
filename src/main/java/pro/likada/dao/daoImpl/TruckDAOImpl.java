package pro.likada.dao.daoImpl;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.likada.dao.TruckDAO;
import pro.likada.model.Truck;
import pro.likada.model.Vehicle;
import pro.likada.util.HibernateUtil;

import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by abuca on 06.03.17.
 */
@Named("truckDAO")
@Transactional
public class TruckDAOImpl implements TruckDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(TruckDAOImpl.class);

    @Override
    public Truck findById(long id) {
        LOGGER.info("Get Truck with an ID: {}", id);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Truck truck = (Truck) session.get(Truck.class, id);
        Hibernate.initialize(truck.getVehicles());
        session.close();
        return truck;
    }

    @Override
    public void save(Truck truck) {
        LOGGER.info("Saving the Truck: {}", truck);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(truck);
        transaction.commit();
        session.flush();
        session.close();
    }

    @Override
    public List<Truck> findAllTrucks(String searchString) {
        LOGGER.info("Get all Trucks, by search string: {}",searchString);
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Truck> trucks = session.createCriteria(Truck.class).setCacheable(true).list();
        session.close();
        return trucks;
    }

    @Override
    public Long findAmountOfVehiclesConnectedWithTruck(Truck truck) {
        LOGGER.info("Get amount of vehicles, connected with Truck: {}", truck);
        Session session = HibernateUtil.getSessionFactory().openSession();
        if(truck.getId() == null){
            return 0L;
        }
        Long amount = (Long) session.createCriteria(Vehicle.class)
                .add(Restrictions.eq("truck", truck))
                .setProjection(Projections.rowCount())
                .setCacheable(true)
                .uniqueResult();
        session.close();
        return amount;
    }

    @Override
    public void deleteById(long id) {
        LOGGER.info("Delete Truck with an ID: {}", id);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(findById(id));
        transaction.commit();
        session.flush();
        session.close();
    }
}


