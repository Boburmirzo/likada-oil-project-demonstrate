package pro.likada.dao.daoImpl;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.likada.dao.TransportationCompanyTypeDAO;
import pro.likada.model.TransportationCompanyType;
import pro.likada.util.HibernateUtil;

import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Yusupov on 3/17/2017.
 */
@SuppressWarnings("unchecked")
@Named("transportationCompanyTypeDAO")
@Transactional
public class TransportationCompanyTypeDAOImpl implements TransportationCompanyTypeDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransportationCompanyTypeDAOImpl.class);

    @Override
    public List<TransportationCompanyType> getAllTransportationCompanyTypes() {
        LOGGER.info("Get All TransportationCompanyTypes");
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<TransportationCompanyType> transportationCompanyTypes = (List<TransportationCompanyType>) session.createCriteria(TransportationCompanyType.class).setCacheable(true).list();
        session.close();
        return transportationCompanyTypes;
    }
}
