package pro.likada.dao.daoImpl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.likada.dao.ProductPriceDAO;
import pro.likada.model.Product;
import pro.likada.model.ProductPrice;
import pro.likada.util.HibernateUtil;

import javax.inject.Named;
import javax.transaction.Transactional;

/**
 * Created by bumur on 21.02.2017.
 */
@SuppressWarnings("unchecked")
@Named("productPriceDAO")
@Transactional
public class ProductPriceDAOImpl implements ProductPriceDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductPriceDAOImpl.class);


    @Override
    public void save(ProductPrice productPrice) {
        LOGGER.info("Saving a ProductPrice: {}", productPrice);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(productPrice);
        transaction.commit();
        session.flush();
        session.close();
    }

}
