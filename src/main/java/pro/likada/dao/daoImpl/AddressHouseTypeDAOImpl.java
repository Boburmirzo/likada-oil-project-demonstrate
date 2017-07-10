package pro.likada.dao.daoImpl;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.likada.dao.AddressHouseTypeDAO;
import pro.likada.model.AddressHouseType;
import pro.likada.util.HibernateUtil;

import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by bumur on 05.01.2017.
 */
@SuppressWarnings("unchecked")
@Named("addressHouseTypeDAO")
@Transactional
public class AddressHouseTypeDAOImpl implements AddressHouseTypeDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(AddressHouseTypeDAOImpl.class);

    @Override
    public List<AddressHouseType> getAllAddressHouseTypes() {
        LOGGER.info("Get All AddressBuildingTypes");
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<AddressHouseType> addressHouseTypes = (List<AddressHouseType>) session.createCriteria(AddressHouseType.class).list();
        session.close();
        return addressHouseTypes;
    }
}
