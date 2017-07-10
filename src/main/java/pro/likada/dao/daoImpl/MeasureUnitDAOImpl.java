package pro.likada.dao.daoImpl;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.likada.dao.MeasureUnitDAO;
import pro.likada.model.MeasureUnit;
import pro.likada.util.HibernateUtil;

import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by bumur on 13.02.2017.
 */
@SuppressWarnings("unchecked")
@Named("measureUnitDAO")
@Transactional
public class MeasureUnitDAOImpl implements MeasureUnitDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(MeasureUnitDAOImpl.class);

    @Override
    public MeasureUnit findById(long id) {
        LOGGER.info("Get MeasureUnit with an ID: {}", id);
        Session session = HibernateUtil.getSessionFactory().openSession();
        MeasureUnit measureUnit =(MeasureUnit) session.createCriteria(MeasureUnit.class).add(Restrictions.idEq(id)).uniqueResult();
        session.close();
        return measureUnit;
    }

    @Override
    public List<MeasureUnit> getAllMeasureUnits() {
        LOGGER.info("Get All MeasureUnits");
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<MeasureUnit> measureUnits = (List<MeasureUnit>) session.createCriteria(MeasureUnit.class).list();
        session.close();
        return measureUnits;
    }
}
