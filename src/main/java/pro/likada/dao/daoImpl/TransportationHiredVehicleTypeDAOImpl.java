package pro.likada.dao.daoImpl;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.likada.dao.TransportationHiredVehicleTypeDAO;
import pro.likada.model.TransportationHiredVehicleType;
import pro.likada.util.HibernateUtil;

import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Yusupov on 3/17/2017.
 */
@SuppressWarnings("unchecked")
@Named("transportationHiredVehicleTypeDAO")
@Transactional
public class TransportationHiredVehicleTypeDAOImpl implements TransportationHiredVehicleTypeDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransportationHiredVehicleTypeDAOImpl.class);

    @Override
    public List<TransportationHiredVehicleType> getAllTransportationHiredVehicleTypes() {
        LOGGER.info("Get All TransportationHiredVehicleTypes");
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<TransportationHiredVehicleType> transportationHiredVehicleTypes = (List<TransportationHiredVehicleType>) session.createCriteria(TransportationHiredVehicleType.class).setCacheable(true).list();
        session.close();
        return transportationHiredVehicleTypes;
    }

}
