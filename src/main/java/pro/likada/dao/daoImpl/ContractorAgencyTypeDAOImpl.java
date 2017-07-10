package pro.likada.dao.daoImpl;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.likada.dao.ContractorAgencyTypeDAO;
import pro.likada.model.ContractorAgencyType;
import pro.likada.util.HibernateUtil;

import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Yusupov on 1/25/2017.
 */
@SuppressWarnings("unchecked")
@Named("contractorAgencyTypeDAO")
@Transactional
public class ContractorAgencyTypeDAOImpl implements ContractorAgencyTypeDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDAOImpl.class);

    @Override
    public ContractorAgencyType findById(long id) {
        LOGGER.info("Get ContractorAgencyType with an ID: {}", id);
        Session session = HibernateUtil.getSessionFactory().openSession();
        ContractorAgencyType agreement = (ContractorAgencyType) session.createCriteria(ContractorAgencyType.class).add(Restrictions.idEq(id)).uniqueResult();
        session.close();
        return agreement;
    }

    @Override
    public ContractorAgencyType findByAgencyType(String type) {
        LOGGER.info("Get ContractorAgencyType with type: {}", type);
        Session session = HibernateUtil.getSessionFactory().openSession();
        ContractorAgencyType contractorAgencyType = (ContractorAgencyType) session.createCriteria(ContractorAgencyType.class)
                .add(Restrictions.ilike("type", type)).uniqueResult();
        session.close();
        return contractorAgencyType;
    }

    @Override
    public void save(ContractorAgencyType contractorAgencyType) {
        LOGGER.info("Saving the ContractorAgencyType: {}", contractorAgencyType);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(contractorAgencyType);
        transaction.commit();
        session.flush();
        session.close();
    }

    @Override
    public void deleteById(long id) {
        LOGGER.info("Delete ContractorAgencyType with an ID:" + id);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(findById(id));
        transaction.commit();
        session.flush();
        session.close();
    }

    @Override
    public void deleteByAgencyType(String type) {
        LOGGER.info("Delete ContractorAgencyType with type:" + type);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(findByAgencyType(type));
        transaction.commit();
        session.flush();
        session.close();
    }

    @Override
    public List<ContractorAgencyType> findAllContractorAgencyTypes() {
        LOGGER.info("Get all ContractorAgencyTypes");
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<ContractorAgencyType> contractorAgencyTypes = (List<ContractorAgencyType>) session.createCriteria(ContractorAgencyType.class).list();
        session.close();
        return contractorAgencyTypes;
    }
}
