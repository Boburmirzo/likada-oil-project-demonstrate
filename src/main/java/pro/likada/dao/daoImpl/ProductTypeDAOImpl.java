package pro.likada.dao.daoImpl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.likada.dao.ProductTypeDAO;
import pro.likada.model.ProductType;
import pro.likada.util.HibernateUtil;

import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by bumur on 19.02.2017.
 */
@SuppressWarnings("unchecked")
@Named("productTypeDAO")
@Transactional
public class ProductTypeDAOImpl implements ProductTypeDAO{

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductTypeDAOImpl.class);

    @Override
    public ProductType findById(long id) {
        LOGGER.info("Get ProductType with an ID: {}", id);
        Session session = HibernateUtil.getSessionFactory().openSession();
        ProductType productType = (ProductType) session.createCriteria(ProductType.class).add(Restrictions.idEq(id)).uniqueResult();
        session.close();
        return productType;
    }

    @Override
    public void deleteById(Long id) {
        LOGGER.info("Delete ProductType with an id:" + id);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(findById(id));
        transaction.commit();
        session.flush();
        session.close();
    }

    @Override
    public List<ProductType> getAllProductTypes() {
        LOGGER.info("Get All ProductTypes");
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<ProductType> productTypes = (List<ProductType>) session.createCriteria(ProductType.class).list();
        session.close();
        return productTypes;
    }

    @Override
    public List<ProductType> getProductTypesGroupByNameWork() {
        LOGGER.info("Get ProductTypes Order by NameWork");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(ProductType.class);
        criteria.addOrder(Order.asc("nameWork"));
        List<ProductType> productTypes = criteria.list();
        session.close();
        return productTypes;
    }

    @Override
    public List<ProductType> findAllProductTypesByNameWork(String searchString) {
        LOGGER.info("Find ProductTypes by searchString");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(ProductType.class, "productType");
        if(searchString!=null && !searchString.isEmpty()) {
            criteria.add(Restrictions.or(Restrictions.ilike("productType.nameWork", searchString, MatchMode.ANYWHERE),
                    Restrictions.ilike("productType.nameFull", searchString, MatchMode.ANYWHERE)));
        }
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);//To avoid duplicates.
        criteria.addOrder(Order.asc("id"));
        criteria.addOrder(Order.asc("turn"));
        criteria.addOrder(Order.asc("nameWork"));
        List<ProductType> productTypes = (List<ProductType>)criteria.list();
        session.close();
        return productTypes;
    }

    @Override
    public void save(ProductType productType) {
        LOGGER.info("Saving a ProductType: {}", productType);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(productType);
        transaction.commit();
        session.flush();
        session.close();
    }

    @Override
    public List<ProductType> getProductTypesByIdInDescendingOrder() {
        LOGGER.info("Get ProductTypes Order by Id in descending order");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(ProductType.class);
        criteria.addOrder(Order.desc("id"));
        List<ProductType> productTypes = criteria.list();
        session.close();
        return productTypes;
    }
}
