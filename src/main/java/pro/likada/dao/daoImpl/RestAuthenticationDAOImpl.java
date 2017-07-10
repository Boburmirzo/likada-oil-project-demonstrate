package pro.likada.dao.daoImpl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.likada.dao.RestAuthenticationDAO;
import pro.likada.model.RestAuthentication;
import pro.likada.util.HibernateUtil;

import javax.inject.Named;
import javax.transaction.Transactional;

/**
 * Created by bumur on 10.04.2017.
 */
@SuppressWarnings("unchecked")
@Named("restAuthenticationDAO")
@Transactional
public class RestAuthenticationDAOImpl implements RestAuthenticationDAO{

    private static final Logger LOGGER = LoggerFactory.getLogger(RestAuthenticationDAO.class);

    @Override
    public RestAuthentication findByRestUserId(Long restUserId) {
        LOGGER.info("Get RestAuthentication by restUserId");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(RestAuthentication.class);
        criteria.add(Restrictions.eq("restUser.id", restUserId));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        criteria.setCacheable(true);
        RestAuthentication restAuthentication = (RestAuthentication) criteria.uniqueResult();
        session.close();
        return restAuthentication;
    }

    @Override
    public RestAuthentication findByToken(String token) {
        LOGGER.info("Get RestAuthentication by token");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(RestAuthentication.class);
        criteria.add(Restrictions.eq("token", token));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        criteria.setCacheable(true);
        RestAuthentication restAuthentication = (RestAuthentication) criteria.uniqueResult();
        session.close();
        return restAuthentication;
    }

    @Override
    public void save(RestAuthentication restAuthentication) {
        LOGGER.info("Saving a RestAuthentication: {}", restAuthentication);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(restAuthentication);
        transaction.commit();
        session.flush();
        session.close();
    }
}
