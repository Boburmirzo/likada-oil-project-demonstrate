package pro.likada.dao.daoImpl;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.likada.dao.AddressApartmentTypeDAO;
import pro.likada.model.AddressApartmentType;
import pro.likada.util.HibernateUtil;

import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by bumur on 05.01.2017.
 */
@SuppressWarnings("unchecked")
@Named("addressApartmentTypeDAO")
@Transactional
public class AddressApartmentTypeDAOImpl implements AddressApartmentTypeDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(AddressApartmentTypeDAOImpl.class);

    @Override
    public List<AddressApartmentType> getAllAddressApartmentTypes() {
        LOGGER.info("Get All AddressApartmentTypes");
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<AddressApartmentType> addressApartmentTypes = (List<AddressApartmentType>) session.createCriteria(AddressApartmentType.class).list();
        session.close();
        return addressApartmentTypes;
    }
}
