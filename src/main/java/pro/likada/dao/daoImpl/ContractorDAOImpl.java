package pro.likada.dao.daoImpl;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.likada.bean.controller.LoginController;
import pro.likada.dao.ContractorDAO;
import pro.likada.model.Contractor;
import pro.likada.model.RoleEnum;
import pro.likada.model.User;
import pro.likada.util.AgencyTypeEnum;
import pro.likada.util.HibernateUtil;
import pro.likada.util.ModelConstantEnum;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by Yusupov on 12/22/2016.
 */
@SuppressWarnings("unchecked")
@Named("contractorDAO")
@Transactional
public class ContractorDAOImpl  implements ContractorDAO{

    private static final Logger LOGGER = LoggerFactory.getLogger(ContractorDAOImpl.class);

    @Inject
    private LoginController loginController;

    @Override
    public Contractor findById(long id) {
        LOGGER.info("Get Contractor with an ID: {}", id);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Contractor.class, "contractor");
        criteria.createAlias("contractor.bankAccounts", "bankAccount", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.idEq(id));
        Contractor contractor = (Contractor) criteria.uniqueResult();
        session.close();
        return contractor;
    }

    @Override
    public List<Contractor> findByNameFull(String nameFull) {
        LOGGER.info("Get Contractor with nameFull: {}", nameFull);
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Contractor> contractors = (List<Contractor>) session.createCriteria(Contractor.class)
                .add(Restrictions.ilike("nameFull", nameFull, MatchMode.EXACT)).list();
        if(contractors!=null && !contractors.isEmpty()){
            for (Contractor c: contractors)
                Hibernate.initialize(c.getBankAccounts());
        }
        session.close();
        return contractors;
    }

    @Override
    public List<Contractor> findByNameShort(String nameShort) {
        LOGGER.info("Get Contractor with nameShort: {}", nameShort);
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Contractor> contractors = (List<Contractor>) session.createCriteria(Contractor.class)
                .add(Restrictions.ilike("nameShort", nameShort, MatchMode.EXACT)).list();
        if(contractors!=null && !contractors.isEmpty()){
            for (Contractor c: contractors)
                Hibernate.initialize(c.getBankAccounts());
        }
        session.close();
        return contractors;
    }

    @Override
    public List<Contractor> findByNameWork(String nameWork) {
        LOGGER.info("Get Contractor with nameWork: {}", nameWork);
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Contractor> contractors = (List<Contractor>) session.createCriteria(Contractor.class)
                .add(Restrictions.ilike("nameWork", nameWork, MatchMode.EXACT)).list();
        session.close();
        return contractors;
    }

    @Override
    public List<Contractor> findByINN(String inn, String contractorType) {
        LOGGER.info("Get Contractor with INN: {}", inn);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Contractor.class, "contractor");
        criteria.createAlias("contractor.agencyType", "agencyType", JoinType.LEFT_OUTER_JOIN);
        criteria.createAlias("contractor.bankAccounts", "bankAccount", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.eq("inn", inn));
        criteria.add(Restrictions.ilike("agencyType.type", contractorType));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);//To avoid duplicates.
        List<Contractor> contractors = criteria.list();
        session.close();
        return contractors;
    }

    @Override
    public void save(Contractor contractor) {
        LOGGER.info("Saving a new Contractor: {}", contractor);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(contractor);
        transaction.commit();
        session.flush();
        session.close();
    }

    @Override
    public void deleteById(long id) {
        LOGGER.info("Delete Contractor with an id:" + id);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Contractor contractor = findById(id);
        if(contractor.getCustomer()!=null)
            contractor.getCustomer().getContractors().remove(contractor);
        else
            session.delete(findById(id));
        transaction.commit();
        session.flush();
        session.close();
    }

    @Override
    public void deleteByNameFull(String nameFull) {
        LOGGER.info("Delete Contractor with nameFull:" + nameFull);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        List<Contractor> contractors = findByNameFull(nameFull);
        for (Contractor c: contractors)
            session.delete(c);
        transaction.commit();
        session.flush();
        session.close();
    }

    @Override
    public void deleteByNameShort(String nameShort) {
        LOGGER.info("Delete Contractor with nameShort:" + nameShort);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        List<Contractor> contractors = findByNameFull(nameShort);
        for (Contractor c: contractors)
            session.delete(c);
        transaction.commit();
        session.flush();
        session.close();
    }

    @Override
    public List<Contractor> findAllContractors(String searchStringTitle, String contractorAgencyType) {
        LOGGER.info("Get all Contractors");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Contractor.class, "contractor");
        criteria.createAlias("contractor.customer", "customer", JoinType.LEFT_OUTER_JOIN);
        criteria.createAlias("contractor.creator", "creator", JoinType.LEFT_OUTER_JOIN);
        criteria.createAlias("customer.customerManagers", "manager", JoinType.LEFT_OUTER_JOIN);
        criteria.createAlias("contractor.agencyType", "agencyType", JoinType.LEFT_OUTER_JOIN);
        if(searchStringTitle!=null && !searchStringTitle.isEmpty())
            criteria.add(Restrictions.or(Restrictions.ilike("contractor.nameFull", searchStringTitle, MatchMode.ANYWHERE),
                    Restrictions.ilike("contractor.nameShort", searchStringTitle, MatchMode.ANYWHERE),
                    Restrictions.ilike("contractor.nameWork", searchStringTitle, MatchMode.ANYWHERE),
                    Restrictions.ilike("contractor.inn",searchStringTitle, MatchMode.ANYWHERE),
                    Restrictions.ilike("contractor.ogrn",searchStringTitle, MatchMode.ANYWHERE),
                    Restrictions.ilike("customer.title",searchStringTitle, MatchMode.ANYWHERE)));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);//To avoid duplicates.
        criteria.addOrder(Order.asc("contractor.nameWork"));
        criteria.addOrder(Order.asc("contractor.nameShort"));
        criteria.addOrder(Order.asc("contractor.nameFull"));
        criteria.addOrder(Order.asc("contractor.id"));

        // Apply filter to show related customers of the user
        User user = loginController.getCurrentUser();
        Subject subject = SecurityUtils.getSubject();
        Disjunction disjunction =Restrictions.disjunction();
        // criteria to get Contractor based on creator
        if(subject.hasRole(RoleEnum.SALES_MANAGER.getRoleType())){
            disjunction.add(Restrictions.eq("manager.id", user.getId()));
            disjunction.add(Restrictions.eq("creator.id", user.getId()));
        }
        if(subject.hasRole(RoleEnum.SALES_DIRECTOR.getRoleType()) || subject.hasRole(RoleEnum.SALES_MANAGER_ASSISTANT.getRoleType())){
            disjunction.add(Restrictions.eq("manager.id", user.getId()));
            for(User subordinate: user.getSubordinates())
                disjunction.add(Restrictions.eq("manager.id", subordinate.getId()));
            disjunction.add(Restrictions.eq("creator.id", user.getId()));
        }
        criteria.add(disjunction);
        // Restrict to show only Contractors (not Companies, nor Providers)

            if (contractorAgencyType.equals(ModelConstantEnum.CARRIER.getModelName()) || contractorAgencyType.equals(ModelConstantEnum.COMPANY.getModelName()) || contractorAgencyType.equals(ModelConstantEnum.PROVIDER.getModelName())) {
                criteria.add(Restrictions.ilike("agencyType.type", contractorAgencyType));
            }else if (contractorAgencyType.equals(ModelConstantEnum.CUSTOMER.getModelName())){
                criteria.add(Restrictions.eqProperty("contractor.customer.id","customer.id"));
            }

        List<Contractor> contractors = (List<Contractor>)criteria.list();
        session.close();
        return contractors;
    }

    @Override
    public List<Contractor> findAllContractorsNotLinkedToCustomer(String searchStringName, Set<Contractor> contractors) {
        LOGGER.info("Get all Contractors");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Contractor.class, "contractor");
        criteria.add(Restrictions.isNull("customer"));
        criteria.createAlias("contractor.agencyType", "agencyType", JoinType.LEFT_OUTER_JOIN);
        // Add filter to do not to show the contractors that are listed already in the table of Customer Contractors
        if(contractors!=null && contractors.size()>0){
            List<Long> currentContractorIds = new LinkedList<>();
            for (Contractor c: contractors)
                currentContractorIds.add(c.getId());
            criteria.add(Restrictions.not(Restrictions.in("contractor.id", currentContractorIds)));
        }
        criteria.createAlias("contractor.bankAccounts", "bankAccount", JoinType.LEFT_OUTER_JOIN);
        if(searchStringName!=null && !searchStringName.isEmpty())
            criteria.add(Restrictions.or(Restrictions.ilike("contractor.nameFull", searchStringName, MatchMode.ANYWHERE),
                    Restrictions.ilike("contractor.nameShort", searchStringName, MatchMode.ANYWHERE),
                    Restrictions.ilike("contractor.inn",searchStringName, MatchMode.ANYWHERE),
                    Restrictions.ilike("contractor.ogrn",searchStringName, MatchMode.ANYWHERE),
                    Restrictions.ilike("contractor.phone",searchStringName, MatchMode.ANYWHERE),
                    Restrictions.ilike("contractor.email",searchStringName, MatchMode.ANYWHERE),
                    Restrictions.ilike("contractor.nameWork",searchStringName, MatchMode.ANYWHERE)));
        // Restrict to show only Contractors (not Companies, nor Providers)
        criteria.add(Restrictions.ilike("agencyType.type", AgencyTypeEnum.CONTRACTOR));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);//To avoid duplicates.
        criteria.addOrder(Order.asc("contractor.nameFull"));
        criteria.addOrder(Order.asc("contractor.nameShort"));
        List<Contractor> contractorsList = (List<Contractor>)criteria.list();
        session.close();
        return contractorsList;
    }

    @Override
    public List<Contractor> findAllContractorsLinkedToCustomer(String searchStringName, Long customerId) {
        LOGGER.info("Get All contractors with customers id: {}", customerId);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Contractor.class, "contractor");
        criteria.createAlias("contractor.agencyType", "agencyType", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.eq("customer.id",customerId));
        if(searchStringName!=null && !searchStringName.isEmpty())
            criteria.add(Restrictions.or(Restrictions.ilike("contractor.nameFull", searchStringName, MatchMode.ANYWHERE),
                    Restrictions.ilike("contractor.nameShort", searchStringName, MatchMode.ANYWHERE),
                    Restrictions.ilike("contractor.inn",searchStringName, MatchMode.ANYWHERE),
                    Restrictions.ilike("contractor.ogrn",searchStringName, MatchMode.ANYWHERE),
                    Restrictions.ilike("contractor.phone",searchStringName, MatchMode.ANYWHERE),
                    Restrictions.ilike("contractor.email",searchStringName, MatchMode.ANYWHERE),
                    Restrictions.ilike("contractor.nameWork",searchStringName, MatchMode.ANYWHERE)));
        // Restrict to show only Contractors (not Companies, nor Providers)
        criteria.add(Restrictions.ilike("agencyType.type", AgencyTypeEnum.CONTRACTOR));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List<Contractor> contractors = (List<Contractor>) criteria.list();
        session.close();
        return contractors;
    }

    public List<Contractor> findAllCompaniesToAssignAsClient(){
        LOGGER.info("Get all Companies to assign as Client");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Contractor.class, "contractor");
        criteria.createAlias("contractor.agencyType", "agencyType", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.ilike("agencyType.type", AgencyTypeEnum.COMPANY));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        criteria.addOrder(Order.asc("contractor.nameWork"));
        criteria.addOrder(Order.asc("contractor.nameShort"));
        criteria.addOrder(Order.asc("contractor.nameFull"));
        criteria.addOrder(Order.asc("contractor.id"));
        criteria.setCacheable(true);
        List<Contractor> contractorsList = (List<Contractor>)criteria.list();
        session.close();
        return contractorsList;
    }

    public List<Contractor> findAllCarriersBasedOnOurs(Boolean ours){
        LOGGER.info("Get all Companies that are: {}", ours ? "ours" : "not ours");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Contractor.class, "contractor");
        criteria.add(Restrictions.eq("ours", ours));
        criteria.createAlias("contractor.agencyType", "agencyType", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.ilike("agencyType.type", AgencyTypeEnum.CARRIER));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        criteria.addOrder(Order.asc("contractor.nameWork"));
        criteria.addOrder(Order.asc("contractor.nameShort"));
        criteria.addOrder(Order.asc("contractor.nameFull"));
        criteria.addOrder(Order.asc("contractor.id"));
        List<Contractor> contractorsList = (List<Contractor>)criteria.setCacheable(true).list();
        session.close();
        return contractorsList;
    }

    public List<Contractor> findAllProviders(){
        LOGGER.info("Get all Providers");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Contractor.class, "contractor");
        criteria.createAlias("contractor.agencyType", "agencyType", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.ilike("agencyType.type", AgencyTypeEnum.PROVIDER));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        criteria.addOrder(Order.asc("contractor.nameWork"));
        criteria.addOrder(Order.asc("contractor.nameShort"));
        criteria.addOrder(Order.asc("contractor.nameFull"));
        criteria.addOrder(Order.asc("contractor.id"));
        List<Contractor> providersList = (List<Contractor>)criteria.setCacheable(true).list();
        session.close();
        return providersList;
    }

}
