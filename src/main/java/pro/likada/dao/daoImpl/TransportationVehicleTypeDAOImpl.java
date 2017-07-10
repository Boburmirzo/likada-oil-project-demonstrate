package pro.likada.dao.daoImpl;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.likada.dao.TransportationVehicleTypeDAO;
import pro.likada.model.TransportationVehicleType;
import pro.likada.util.HibernateUtil;

import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Yusupov on 3/17/2017.
 */
@SuppressWarnings("unchecked")
@Named("transportationVehicleTypeDAO")
@Transactional
public class TransportationVehicleTypeDAOImpl implements TransportationVehicleTypeDAO{

    private static final Logger LOGGER = LoggerFactory.getLogger(TransportationVehicleTypeDAOImpl.class);

    @Override
    public List<TransportationVehicleType> getAllTransportationVehicleTypes() {
        LOGGER.info("Get All TransportationVehicleTypes");
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<TransportationVehicleType> transportationVehicleTypes = (List<TransportationVehicleType>) session.createCriteria(TransportationVehicleType.class).setCacheable(true).list();
        session.close();
        return transportationVehicleTypes;
    }
}
