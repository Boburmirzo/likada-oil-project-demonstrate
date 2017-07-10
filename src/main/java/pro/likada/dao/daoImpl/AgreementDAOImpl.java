package pro.likada.dao.daoImpl;

import org.hibernate.*;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.likada.bean.controller.LoginController;
import pro.likada.dao.AgreementDAO;
import pro.likada.model.*;
import pro.likada.util.AgreementTypeEnum;
import pro.likada.util.HibernateUtil;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Yusupov on 1/25/2017.
 */
@SuppressWarnings("unchecked")
@Named("agreementDAO")
@Transactional
public class AgreementDAOImpl implements AgreementDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDAOImpl.class);

    @Inject
    private LoginController loginController;

    @Override
    public Agreement findById(long id) {
        LOGGER.info("Get Agreement with an ID: {}", id);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Agreement agreement = (Agreement) session.createCriteria(Agreement.class).add(Restrictions.idEq(id)).uniqueResult();
        session.close();
        return agreement;
    }

    @Override
    public List<Agreement> findByAgreementNumber(String agreementNumber) {
        LOGGER.info("Get Agreement with number: {}", agreementNumber);
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Agreement> agreements = (List<Agreement>) session.createCriteria(Agreement.class)
                .add(Restrictions.ilike("agreementNumber", agreementNumber, MatchMode.ANYWHERE)).list();
        session.close();
        return agreements;
    }

    @Override
    public void save(Agreement agreement) {
        LOGGER.info("Saving the Agreement: {}", agreement);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(agreement);
        transaction.commit();
        session.flush();
        session.close();
    }

    @Override
    public void deleteById(long id) {
        LOGGER.info("Delete Agreement with an ID:" + id);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(findById(id));
        transaction.commit();
        session.flush();
        session.close();
    }

    @Override
    public void deleteByAgreementNumber(String agreementNumber) {
        LOGGER.info("Delete Agreement with agreement number:" + agreementNumber);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        List<Agreement> agreements = findByAgreementNumber(agreementNumber);
        for (Agreement a: agreements)
            session.delete(a);
        transaction.commit();
        session.flush();
        session.close();
    }

    @Override
    public List<Agreement> findAllAgreements(String searchString) {
        LOGGER.info("Get all Agreements");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Agreement.class, "agreement");
        criteria.createAlias("agreement.client", "client", JoinType.LEFT_OUTER_JOIN);
        criteria.createAlias("agreement.contractor", "contractor", JoinType.LEFT_OUTER_JOIN);
        criteria.createAlias("agreement.status", "status", JoinType.LEFT_OUTER_JOIN);
        criteria.createAlias("contractor.customer", "customer", JoinType.LEFT_OUTER_JOIN);
        criteria.createAlias("customer.customerManagers", "manager", JoinType.LEFT_OUTER_JOIN);
        if(searchString!=null && !searchString.isEmpty())
            criteria.add(Restrictions.or(Restrictions.ilike("agreement.agreementNumber", searchString, MatchMode.ANYWHERE),
                    Restrictions.ilike("agreement.description", searchString, MatchMode.ANYWHERE),
                    Restrictions.ilike("client.nameWork",searchString, MatchMode.ANYWHERE),
                    Restrictions.ilike("client.nameFull",searchString, MatchMode.ANYWHERE),
                    Restrictions.ilike("client.nameShort",searchString, MatchMode.ANYWHERE),
                    Restrictions.ilike("client.inn",searchString, MatchMode.ANYWHERE),
                    Restrictions.ilike("contractor.nameWork",searchString, MatchMode.ANYWHERE),
                    Restrictions.ilike("contractor.nameFull",searchString, MatchMode.ANYWHERE),
                    Restrictions.ilike("contractor.nameShort",searchString, MatchMode.ANYWHERE),
                    Restrictions.ilike("contractor.inn",searchString, MatchMode.ANYWHERE),
                    Restrictions.ilike("manager.title",searchString, MatchMode.ANYWHERE),
                    Restrictions.ilike("manager.firstName",searchString, MatchMode.ANYWHERE),
                    Restrictions.ilike("manager.lastName",searchString, MatchMode.ANYWHERE),
                    Restrictions.ilike("manager.patronymic",searchString, MatchMode.ANYWHERE)));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);//To avoid duplicates.
        criteria.addOrder(Order.asc("status.id").nulls(NullPrecedence.FIRST));
        criteria.addOrder(Order.asc("customer.title"));
        criteria.addOrder(Order.asc("customer.id"));

        // Apply filter to show related customers of the user
        User user = loginController.getCurrentUser();
        List<Long> subordinateUserIds = new LinkedList<>();
        for(User u: user.getSubordinates())
            subordinateUserIds.add(u.getId());
        for (Role r: user.getUserRoles()){
            if(RoleEnum.SALES_MANAGER_ASSISTANT.getRoleType().equals(r.getType())){
                criteria.add(Restrictions.in("manager.id", subordinateUserIds)); // TODO optimize restriction for subordinates
                criteria.add(Restrictions.eq("manager.id", user.getId()));
            }
            if(RoleEnum.SALES_MANAGER.getRoleType().equals(r.getType()))
                criteria.add(Restrictions.eq("manager.id", user.getId()));
        }
        List<Agreement> agreements = (List<Agreement>)criteria.list();
        session.close();
        return agreements;
    }

    @Override
    public List<Agreement> findAllAgreementsBasedOnCompanyAndContractor(Contractor company, Contractor contractor, String agreementType) {
        if(company!=null && contractor!=null){
            LOGGER.info("Get all Agreements based on company {} and contractor {}", company, contractor);
            Session session = HibernateUtil.getSessionFactory().openSession();
            Criteria criteria = session.createCriteria(Agreement.class, "agreement");
            criteria.createAlias("agreement.client", "client", JoinType.LEFT_OUTER_JOIN);
            criteria.createAlias("agreement.contractor", "contractor", JoinType.LEFT_OUTER_JOIN);
            criteria.add(Restrictions.eq("client.id", company.getId()));
            criteria.add(Restrictions.eq("contractor.id", contractor.getId()));
            if(agreementType!=null)
                criteria.add(Restrictions.or(Restrictions.ilike("agreement.type.type", agreementType),
                                            Restrictions.ilike("agreement.type.type", AgreementTypeEnum.SALE_TRANSPORT.getType())));
            criteria.addOrder(Order.asc("agreement.created"));
            criteria.addOrder(Order.asc("agreement.validFrom"));
            List<Agreement> agreements = (List<Agreement>)criteria.setCacheable(true).list();
            session.close();
            return agreements;
        }
        return null;
    }

    @Override
    public AgreementType findAgreementTypeById(long id) {
        LOGGER.info("Get AgreementType with an ID: {}", id);
        Session session = HibernateUtil.getSessionFactory().openSession();
        AgreementType agreementType = (AgreementType) session.createCriteria(AgreementType.class).add(Restrictions.idEq(id)).uniqueResult();
        session.close();
        return agreementType;
    }

    @Override
    public List<AgreementType> findAllAgreementTypes() {
        LOGGER.info("Get all AgreementTypes");
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<AgreementType> agreementTypes = (List<AgreementType>) session.createCriteria(AgreementType.class).list();
        session.close();
        return agreementTypes;
    }

    @Override
    public AgreementStatus findAgreementStatusById(long id) {
        LOGGER.info("Get AgreementStatus with an ID: {}", id);
        Session session = HibernateUtil.getSessionFactory().openSession();
        AgreementStatus agreementStatus = (AgreementStatus) session.createCriteria(AgreementStatus.class).add(Restrictions.idEq(id)).uniqueResult();
        session.close();
        return agreementStatus;
    }

    @Override
    public List<AgreementStatus> findAllAgreementStatuses() {
        LOGGER.info("Get all AgreementStatuses");
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<AgreementStatus> agreementStatuses = (List<AgreementStatus>) session.createCriteria(AgreementStatus.class).list();
        session.close();
        return agreementStatuses;
    }
}
