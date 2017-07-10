package pro.likada.dao.daoImpl;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.likada.dao.FinancialItemTypeDAO;
import pro.likada.model.FinancialItemType;
import pro.likada.util.HibernateUtil;

import javax.inject.Named;
import javax.transaction.Transactional;

/**
 * Created by bumur on 27.02.2017.
 */
@SuppressWarnings("unchecked")
@Named("financialItemTypeDAO")
@Transactional
public class FinancialItemTypeDAOImpl implements FinancialItemTypeDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(FinancialItemTypeDAOImpl.class);

    @Override
    public FinancialItemType findById(long id) {
        LOGGER.info("Get FinancialItemType with an ID: {}", id);
        Session session = HibernateUtil.getSessionFactory().openSession();
        FinancialItemType financialItemType = (FinancialItemType) session.createCriteria(FinancialItemType.class).add(Restrictions.idEq(id)).uniqueResult();
        session.close();
        return financialItemType;
    }
}
