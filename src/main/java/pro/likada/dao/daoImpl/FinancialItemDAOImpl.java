package pro.likada.dao.daoImpl;

import org.hibernate.Criteria;
import org.hibernate.NullPrecedence;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.likada.dao.FinancialItemDAO;
import pro.likada.model.Contractor;
import pro.likada.model.FinancialItem;
import pro.likada.util.HibernateUtil;

import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * Created by bumur on 27.02.2017.
 */
@SuppressWarnings("unchecked")
@Named("financialItemDAO")
@Transactional
public class FinancialItemDAOImpl implements FinancialItemDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(FinancialItemDAOImpl.class);

    @Override
    public FinancialItem findById(long id) {
        LOGGER.info("Get FinancialItem with an ID: {}", id);
        Session session = HibernateUtil.getSessionFactory().openSession();
        FinancialItem financialItem = (FinancialItem) session.createCriteria(FinancialItem.class).add(Restrictions.idEq(id)).uniqueResult();
        session.close();
        return financialItem;
    }

    @Override
    public void deleteById(Long id) {
        LOGGER.info("Delete FinancialItem with an id:" + id);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(findById(id));
        transaction.commit();
        session.flush();
        session.close();
    }

    @Override
    public void save(FinancialItem financialItem) {
        LOGGER.info("Saving a FinancialItem: {}", financialItem);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(financialItem);
        transaction.commit();
        session.flush();
        session.close();
    }

    @Override
    public List<FinancialItem> getAllFinancialItems() {
        return null;
    }

    @Override
    public List<FinancialItem> findAllFinancialItemsForSearchString(Date dateFrom, Date dateTo, Contractor contractorFrom, Contractor contractorTo) {
        LOGGER.info("Find FinancialItems by searchString");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(FinancialItem.class, "financial_item");
        criteria.createAlias("financial_item.contractorFrom", "contractorFrom", JoinType.LEFT_OUTER_JOIN);
        criteria.createAlias("financial_item.contractorTo", "contractorTo", JoinType.LEFT_OUTER_JOIN);
        if(dateFrom!=null && dateTo==null)
            criteria.add(Restrictions.ge("financial_item.date", dateFrom));
        if(dateFrom==null && dateTo!=null)
            criteria.add(Restrictions.lt("financial_item.date", dateTo));
        if(dateFrom!=null && dateTo!=null) {
            criteria.add(Restrictions.ge("financial_item.date", dateFrom));
            criteria.add(Restrictions.lt("financial_item.date", dateTo));
        }
        if(contractorFrom!=null && contractorTo==null)
            criteria.add(Restrictions.ilike("contractorFrom.nameShort", contractorFrom.getNameShort(), MatchMode.ANYWHERE));
        if(contractorTo!=null && contractorFrom==null)
            criteria.add(Restrictions.ilike("contractorTo.nameShort", contractorTo.getNameShort(), MatchMode.ANYWHERE));
        if(contractorTo!=null && contractorFrom!=null)
            criteria.add(Restrictions.and(Restrictions.ilike("contractorFrom.nameShort", contractorFrom.getNameShort(), MatchMode.ANYWHERE),
                    Restrictions.ilike("contractorTo.nameShort", contractorTo.getNameShort(), MatchMode.ANYWHERE)));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);//To avoid duplicates.
        criteria.addOrder(Order.asc("date"));
        criteria.addOrder(Order.asc("id"));
        List<FinancialItem> financialItems = (List<FinancialItem>)criteria.list();
        session.close();
        return financialItems;
    }

    @Override
    public List<FinancialItem> findAllFinancialItemsOfContractorFromForSearchString(Date dateFrom, Date dateTo,String search, Long contractorFromId) {
        LOGGER.info("Find FinancialItems for ContractorFrom by searchString");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(FinancialItem.class, "financial_item");
        criteria.createAlias("financial_item.contractorTo", "contractorTo", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.or(Restrictions.eq("contractorFrom.id",contractorFromId),Restrictions.eq("contractorTo.id",contractorFromId)));
        if(dateFrom!=null && dateTo==null)
            criteria.add(Restrictions.or(Restrictions.ge("date", dateFrom),Restrictions.lt("date", dateFrom)));
        if(dateFrom==null && dateTo!=null)
            criteria.add(Restrictions.le("date", dateTo));
        if(dateFrom!=null && dateTo!=null) {
            criteria.add(Restrictions.or(Restrictions.ge("date", dateFrom),Restrictions.lt("date", dateFrom)));
            criteria.add(Restrictions.le("date", dateTo));
        }
        if(search!=null && !search.isEmpty())
            criteria.add(Restrictions.ilike("contractorTo.nameShort", search, MatchMode.ANYWHERE));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);//To avoid duplicates.
        criteria.addOrder(Order.asc("id").nulls(NullPrecedence.FIRST));
        criteria.addOrder(Order.asc("date"));

        List<FinancialItem> financialItems = (List<FinancialItem>)criteria.list();
        session.close();
        return financialItems;
    }

    @Override
    public List<FinancialItem> findAllFinancialItemsOfContractorSourceToContractorDestination(Date dateFrom, Date dateTo, Long contractorFromId, Long contractorToId) {
        LOGGER.info("Find FinancialItems from ContractorFrom To ContractorTo by their ID");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(FinancialItem.class);
        if(dateFrom!=null && dateTo==null)
            criteria.add(Restrictions.or(Restrictions.ge("date", dateFrom),Restrictions.lt("date", dateFrom)));
        if(dateFrom==null && dateTo!=null)
            criteria.add(Restrictions.lt("date", dateTo));
        if(dateFrom!=null && dateTo!=null) {
            criteria.add(Restrictions.or(Restrictions.ge("date", dateFrom),Restrictions.lt("date", dateFrom)));
            criteria.add(Restrictions.lt("date", dateTo));
        }
        criteria.add(Restrictions.eq("contractorFrom.id",contractorFromId));
        criteria.add(Restrictions.eq("contractorTo.id",contractorToId));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);//To avoid duplicates.
        criteria.addOrder(Order.asc("date"));
        criteria.addOrder(Order.asc("id"));
        List<FinancialItem> financialItems = (List<FinancialItem>)criteria.list();
        session.close();
        return financialItems;
    }

    @Override
    public List<FinancialItem> findAllFinancialItemsOfContractorSourceToContractorDestination(Date dateFrom, Date dateTo, Contractor contractorFrom, Contractor contractorTo, List<Long> contractorIds) {
        LOGGER.info("Find FinancialItems from ContractorFrom To ContractorTo by their list of IDs");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(FinancialItem.class, "financial_item");
        criteria.createAlias("financial_item.contractorFrom", "contractorFrom", JoinType.LEFT_OUTER_JOIN);
        criteria.createAlias("financial_item.contractorTo", "contractorTo", JoinType.LEFT_OUTER_JOIN);
        if(contractorIds!=null && !contractorIds.isEmpty())
            criteria.add(Restrictions.or(Restrictions.in("contractorFrom.id", contractorIds),Restrictions.in("contractorTo.id", contractorIds)));
        if(dateFrom!=null && dateTo==null) {
            if(contractorTo!=null && contractorFrom!=null) {
                criteria.add(Restrictions.or(Restrictions.ge("date", dateFrom), Restrictions.lt("date", dateFrom)));
            }else{
                criteria.add(Restrictions.ge("date", dateFrom));
            }
        }
        if(dateFrom==null && dateTo!=null)
            criteria.add(Restrictions.lt("date", dateTo));
        if(dateFrom!=null && dateTo!=null) {
            criteria.add(Restrictions.or(Restrictions.ge("date", dateFrom),Restrictions.lt("date", dateFrom)));
            criteria.add(Restrictions.lt("date", dateTo));
        }
        if(contractorFrom!=null && contractorTo==null)
            criteria.add(Restrictions.ilike("contractorFrom.nameShort", contractorFrom.getNameShort(), MatchMode.ANYWHERE));
        if(contractorTo!=null && contractorFrom==null)
            criteria.add(Restrictions.ilike("contractorTo.nameShort", contractorTo.getNameShort(), MatchMode.ANYWHERE));
        if(contractorTo!=null && contractorFrom!=null)
            criteria.add(Restrictions.and(Restrictions.ilike("contractorFrom.nameShort", contractorFrom.getNameShort(), MatchMode.ANYWHERE),
                    Restrictions.ilike("contractorTo.nameShort", contractorTo.getNameShort(), MatchMode.ANYWHERE)));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);//To avoid duplicates.
        criteria.addOrder(Order.asc("date"));
        criteria.addOrder(Order.asc("id"));
        List<FinancialItem> financialItems = (List<FinancialItem>)criteria.list();
        session.close();
        return financialItems;
    }

    @Override
    public List<FinancialItem> findAllFinancialItemsOfContractorSourceToContractorDestination(Date dateFrom, Date dateTo, Contractor contractorFrom, Contractor contractorTo, Contractor choosenContractor) {
        LOGGER.info("Find FinancialItems from ContractorFrom To ContractorTo by chosen Contractor");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(FinancialItem.class, "financial_item");
        criteria.createAlias("financial_item.contractorFrom", "contractorFrom", JoinType.LEFT_OUTER_JOIN);
        criteria.createAlias("financial_item.contractorTo", "contractorTo", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.or(Restrictions.eq("contractorFrom.id",choosenContractor.getId()),Restrictions.eq("contractorTo.id",choosenContractor.getId())));
        if(dateFrom!=null && dateTo==null) {
            if(contractorTo!=null && contractorFrom!=null) {
                criteria.add(Restrictions.or(Restrictions.ge("date", dateFrom), Restrictions.lt("date", dateFrom)));
            }else{
                criteria.add(Restrictions.ge("date", dateFrom));
            }
        }
        if(dateFrom==null && dateTo!=null)
            criteria.add(Restrictions.lt("date", dateTo));
        if(dateFrom!=null && dateTo!=null) {
            criteria.add(Restrictions.or(Restrictions.ge("date", dateFrom),Restrictions.lt("date", dateFrom)));
            criteria.add(Restrictions.lt("date", dateTo));
        }
        if(contractorFrom!=null && contractorTo==null)
            criteria.add(Restrictions.ilike("contractorFrom.nameShort", contractorFrom.getNameShort(), MatchMode.ANYWHERE));
        if(contractorTo!=null && contractorFrom==null)
            criteria.add(Restrictions.ilike("contractorTo.nameShort", contractorTo.getNameShort(), MatchMode.ANYWHERE));
        if(contractorTo!=null && contractorFrom!=null)
            criteria.add(Restrictions.and(Restrictions.ilike("contractorFrom.nameShort", contractorFrom.getNameShort(), MatchMode.ANYWHERE),
                    Restrictions.ilike("contractorTo.nameShort", contractorTo.getNameShort(), MatchMode.ANYWHERE)));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);//To avoid duplicates.
        criteria.addOrder(Order.asc("date"));
        criteria.addOrder(Order.asc("id"));
        List<FinancialItem> financialItems = (List<FinancialItem>)criteria.list();
        session.close();
        return financialItems;
    }

    @Override
    public List<FinancialItem> findAllFinancialItemsOfContractorsOfCustomers(Date dateFrom, Date dateTo,List<Long> contractorIds,Contractor contractorTo) {
        LOGGER.info("Get All FinancialItems By contractorsId");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(FinancialItem.class);
        criteria.add(Restrictions.or(Restrictions.in("contractorFrom.id", contractorIds),Restrictions.in("contractorTo.id", contractorIds)));
        if(dateFrom!=null && dateTo==null)
            criteria.add(Restrictions.ge("date", dateFrom));
        if(dateFrom==null && dateTo!=null)
            criteria.add(Restrictions.lt("date", dateTo));
        if(dateFrom!=null && dateTo!=null) {
            criteria.add(Restrictions.ge("date", dateFrom));
            criteria.add(Restrictions.lt("date", dateTo));
        }
        if(contractorTo!=null){
            criteria.add(Restrictions.ilike("contractorTo.nameShort", contractorTo.getNameShort(), MatchMode.ANYWHERE));
        }
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        criteria.addOrder(Order.asc("date"));
        criteria.addOrder(Order.asc("id"));
        List<FinancialItem> financialItems = criteria.list();
        session.close();
        return financialItems;
    }

}
