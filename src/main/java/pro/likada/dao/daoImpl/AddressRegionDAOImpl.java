package pro.likada.dao.daoImpl;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.likada.dao.AddressRegionDAO;
import pro.likada.model.AddressRegion;
import pro.likada.util.HibernateUtil;

import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by bumur on 05.01.2017.
 */
@SuppressWarnings("unchecked")
@Named("addressRegionDAO")
@Transactional
public class AddressRegionDAOImpl implements AddressRegionDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(AddressRegionDAOImpl.class);

    @Override
    public List<AddressRegion> getAddressRegions() {
        LOGGER.info("Get All AddressRegionTypes");
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<AddressRegion> addressRegions = (List<AddressRegion>) session.createCriteria(AddressRegion.class).list();
        session.close();
        return addressRegions;
    }
}
