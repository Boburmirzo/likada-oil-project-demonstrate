package pro.likada.dao.daoImpl;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.likada.dao.VehicleMechanicalStatusTypeDAO;
import pro.likada.model.VehicleMechanicalStatusType;
import pro.likada.util.HibernateUtil;

import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by abuca on 03.04.17.
 */
@SuppressWarnings("unchecked")
@Named("vehicleMechanicalStatusTypeDAO")
@Transactional
public class VehicleMechanicalStatusTypeDAOImpl implements VehicleMechanicalStatusTypeDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(VehicleMechanicalStatusTypeDAOImpl.class);

    @Override
    public List<VehicleMechanicalStatusType> getAllVehicleMechanicalStatusTypes() {
        LOGGER.info("Get All VehicleMechanicalStatusTypes");
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<VehicleMechanicalStatusType> vehicleMechanicalStatusTypeList = (List<VehicleMechanicalStatusType>) session.createCriteria(VehicleMechanicalStatusType.class).setCacheable(true).list();
        session.close();
        return vehicleMechanicalStatusTypeList;
    }

    @Override
    public VehicleMechanicalStatusType getVehicleStatusTypeByName(String name){
        LOGGER.info("Get VehicleMechanicalStatusType by name: {}",name);
        Session session = HibernateUtil.getSessionFactory().openSession();
        VehicleMechanicalStatusType vehicleMechanicalStatusType = (VehicleMechanicalStatusType) session.
                createCriteria(VehicleMechanicalStatusType.class)
                .add(Restrictions.eq("name",name))
                .setCacheable(true)
                .uniqueResult();
        session.close();
        return vehicleMechanicalStatusType;
    }
}
