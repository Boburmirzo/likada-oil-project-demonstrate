package pro.likada.dao.daoImpl;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.likada.dao.RequisiteDAO;
import pro.likada.model.Requisite;
import pro.likada.util.HibernateUtil;

import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by bumur on 27.12.2016.
 */
@SuppressWarnings("unchecked")
@Named("requisiteDAO")
@Transactional
public class RequisiteDAOImpl implements RequisiteDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequisiteDAOImpl.class);

    @Override
    public Requisite findById(long id) {
        LOGGER.info("Get Requisite with an ID: {}", id);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Requisite requisite =(Requisite) session.createCriteria(Requisite.class).add(Restrictions.idEq(id)).uniqueResult();
        session.close();
        return requisite;
    }

    @Override
    public List<Requisite> findByContractorId(long id) {
        LOGGER.info("Get BankAccount with bankNumber: {}", id);
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Requisite> requisites = (List<Requisite>) session.createCriteria(Requisite.class)
                .add(Restrictions.eq("contractor_id", id)).list();
        session.close();
        return requisites;

    }
}
