package pro.likada.dao.daoImpl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.likada.dao.ProductGroupDAO;
import pro.likada.model.ProductGroup;
import pro.likada.util.HibernateUtil;

import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by bumur on 14.02.2017.
 */
@SuppressWarnings("unchecked")
@Named("productGroupDAO")
@Transactional
public class ProductGroupDAOImpl implements ProductGroupDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductGroupDAOImpl.class);

    @Override
    public ProductGroup findById(long id) {
        LOGGER.info("Get ProductGroup with an ID: {}", id);
        Session session = HibernateUtil.getSessionFactory().openSession();
        ProductGroup productGroup = (ProductGroup) session.createCriteria(ProductGroup.class).add(Restrictions.idEq(id)).uniqueResult();
        session.close();
        return productGroup;
    }

    @Override
    public void save(ProductGroup productGroup) {
        LOGGER.info("Saving a ProductGroup: {}", productGroup);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(productGroup);
        transaction.commit();
        session.flush();
        session.close();
    }

    @Override
    public void deleteById(Long id) {
        LOGGER.info("Delete ProductGroup with an id:" + id);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(findById(id));
        transaction.commit();
        session.flush();
        session.close();
    }

    @Override
    public List<ProductGroup> getProductGroupsByParentId(Long id) {
        LOGGER.info("Get All Products By GroupId");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(ProductGroup.class);
        criteria.add(Restrictions.eq("parentId", id));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        criteria.setCacheable(true);
        List<ProductGroup> productGroups = criteria.list();
        session.close();
        return productGroups;
    }

    @Override
    public List<ProductGroup> getAllProductGroups() {
        LOGGER.info("Get All ProductGroups Order by parentId, turn and id");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(ProductGroup.class);
        criteria.addOrder(Order.asc("parentId"));
        criteria.addOrder(Order.asc("turn"));
        criteria.addOrder(Order.asc("id"));
        criteria.setCacheable(true);
        List<ProductGroup> productGroups = criteria.list();
        session.close();
        return productGroups;

    }

    @Override
    public Long getMinValueOfProductParentId() {
        LOGGER.info("Get Min value of Product Parent Id");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(ProductGroup.class);
        criteria.setProjection(Projections.min("parentId"));
        Long minProductParentId=(Long) criteria.uniqueResult();
        session.close();
        return minProductParentId;
    }

    @Override
    public Long getMinValueOfProductId() {
        LOGGER.info("Get Min value of Product Id");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(ProductGroup.class);
        criteria.setProjection(Projections.min("id"));
        Long minProductId=(Long) criteria.uniqueResult();
        session.close();
        return minProductId;
    }
}
