package pro.likada.dao.daoImpl;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.likada.dao.AddressBuildingTypeDAO;
import pro.likada.model.AddressBuildingType;
import pro.likada.util.HibernateUtil;

import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by bumur on 05.01.2017.
 */
@SuppressWarnings("unchecked")
@Named("addressBuildingTypeDAO")
@Transactional
public class AddressBuildingTypeDAOImpl implements AddressBuildingTypeDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(AddressBuildingTypeDAOImpl.class);

    @Override
    public List<AddressBuildingType> getAllAddressBuildingTypes() {
        LOGGER.info("Get All AddressBuildingTypes");
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<AddressBuildingType> addressBuildingTypes = (List<AddressBuildingType>) session.createCriteria(AddressBuildingType.class).list();
        session.close();
        return addressBuildingTypes;
    }
}
