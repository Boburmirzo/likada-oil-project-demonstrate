package pro.likada.dao.daoImpl;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.likada.dao.TrailerDAO;
import pro.likada.model.Trailer;
import pro.likada.model.Vehicle;
import pro.likada.util.HibernateUtil;

import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by abuca on 07.03.17.
 */
@Named("trailerDAO")
@Transactional
public class TrailerDAOImpl implements TrailerDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(TrailerDAOImpl.class);

    @Override
    public Trailer findById(long id){
        LOGGER.info("Get Trailer with ID: {}",id);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Trailer trailer = (Trailer) session.get(Trailer.class, id);
        Hibernate.initialize(trailer.getVehicles());
        session.close();
        return trailer;
    }

    @Override
    public void save(Trailer trailer){
        LOGGER.info("Saving the Trailer: {}", trailer);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(trailer);
        transaction.commit();
        session.flush();
        session.close();
    }

    @Override
    public List<Trailer> findAllTrailers(String searchString) {
        LOGGER.info("Get all Trailers, by search string: {}",searchString);
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Trailer> trailers = session.createCriteria(Trailer.class).setCacheable(true).list();
        session.close();
        return trailers;
    }

    @Override
    public Long findAmountOfVehiclesConnectedWithTrailer(Trailer trailer){
        LOGGER.info("Get amount of vehicles connected with Trailer: {}", trailer);
        Session session = HibernateUtil.getSessionFactory().openSession();
        if(trailer.getId() == null){
            return 0L;
        }
        Long amount = (Long) session.createCriteria(Vehicle.class).
                                add(Restrictions.eq("trailer",trailer)).
                                setProjection(Projections.rowCount()).
                                setCacheable(true).
                                uniqueResult();
        session.close();
        return amount;
    }

    @Override
    public void deleteById(long id){
        LOGGER.info("Delete Trailer with an ID:" + id);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(findById(id));
        transaction.commit();
        session.flush();
        session.close();
    }
}
