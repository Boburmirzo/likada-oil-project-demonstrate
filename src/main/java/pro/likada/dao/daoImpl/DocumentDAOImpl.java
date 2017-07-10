package pro.likada.dao.daoImpl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.likada.dao.DocumentDAO;
import pro.likada.model.Document;
import pro.likada.util.HibernateUtil;

import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by bumur on 29.01.2017.
 */
@SuppressWarnings("unchecked")
@Named("documentDAO")
@Transactional
public class DocumentDAOImpl implements DocumentDAO{

    private static final Logger LOGGER = LoggerFactory.getLogger(DocumentDAOImpl.class);

    @Override
    public Document findById(long id) {
        LOGGER.info("Get Document with an ID: {}", id);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Document document = (Document) session.createCriteria(Document.class).add(Restrictions.idEq(id)).uniqueResult();
        session.close();
        return document;
    }

    @Override
    public List<Document> getAllDocuments() {
        LOGGER.info("Get All Documents");
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Document> documents = (List<Document>) session.createCriteria(Document.class).list();
        session.close();
        return documents;
    }

    @Override
    public void save(Document document) {
        LOGGER.info("Saving a new Document: {}", document);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(document);
        transaction.commit();
        session.flush();
        session.close();
    }

    @Override
    public void deleteById(long id) {
        LOGGER.info("Delete Document with an id:" + id);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(findById(id));
        transaction.commit();
        session.flush();
        session.close();
    }

    @Override
    public List<Document> findByParentTypeId(Long id, String parentType) {
        LOGGER.info("Get Document with Parent Type id: {}", id);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Document.class);
        criteria.add(Restrictions.and(Restrictions.eq("parent_id",id),Restrictions.eq("parent_type",parentType)));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List<Document> documents = (List<Document>) criteria.list();
        session.close();
        return documents;
    }
}
