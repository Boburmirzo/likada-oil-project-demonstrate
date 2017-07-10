package pro.likada.dao.daoImpl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.likada.dao.ProductDAO;
import pro.likada.model.Product;
import pro.likada.util.HibernateUtil;

import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by bumur on 14.02.2017.
 */
@SuppressWarnings("unchecked")
@Named("productDAO")
@Transactional
public class ProductDAOImpl implements ProductDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductDAOImpl.class);

    @Override
    public Product findById(long id) {
        LOGGER.info("Get with an ID: {}", id);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Product product = (Product) session.createCriteria(Product.class).add(Restrictions.idEq(id)).uniqueResult();
        session.close();
        return product;
    }

    @Override
    public List<Product> getProductsByGroupIds(List<Long> groupsId) {
        LOGGER.info("Get All Products By GroupId and Order by parentId, turn and id");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Product.class);
        if (groupsId.size() > 0) {
            criteria.add(Restrictions.in("groupId.id", groupsId));
        } else {
            criteria.add(Restrictions.eq("groupId.id", new Long(0)));
        }
        criteria.addOrder(Order.asc("turn"));
        criteria.addOrder(Order.asc("id"));
        criteria.addOrder(Order.asc("groupId.id"));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List<Product> products = criteria.list();
        session.close();
        return products;
    }

    @Override
    public List<Product> getProductsByGroupId(Long groupId) {
        LOGGER.info("Get All Products By GroupIds and Order by parentId, turn and id");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Product.class);
        criteria.add(Restrictions.eq("groupId.id", groupId));
        criteria.addOrder(Order.asc("turn"));
        criteria.addOrder(Order.asc("id"));
        criteria.addOrder(Order.asc("groupId.id"));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List<Product> products = criteria.list();
        session.close();
        return products;
    }

    @Override
    public void save(Product product) {
        LOGGER.info("Saving a Product: {}", product);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(product);
        transaction.commit();
        session.flush();
        session.close();
    }

    @Override
    public void deleteById(Long id) {
        LOGGER.info("Delete Product with an id:" + id);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(findById(id));
        transaction.commit();
        session.flush();
        session.close();
    }

    @Override
    public List<Product> getAllProducts() {
        LOGGER.info("Get All Products");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Product.class);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List<Product> products = criteria.list();
        session.close();
        return products;
    }

}
