package pro.likada.dao.daoImpl;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.likada.dao.DocumentTypeDAO;
import pro.likada.model.DocumentType;
import pro.likada.util.HibernateUtil;

import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by bumur on 29.01.2017.
 */
@SuppressWarnings("unchecked")
@Named("documentTypeDAO")
@Transactional
public class DocumentTypeDAOImpl implements DocumentTypeDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(DocumentTypeDAOImpl.class);

    @Override
    public DocumentType findById(long id) {
        LOGGER.info("Get Document Type with an ID: {}", id);
        Session session = HibernateUtil.getSessionFactory().openSession();
        DocumentType documentType = (DocumentType) session.createCriteria(DocumentType.class).add(Restrictions.idEq(id)).uniqueResult();
        session.close();
        return documentType;
    }

    @Override
    public List<DocumentType> getAllDocumentTypes() {
        LOGGER.info("Get All Document Types");
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<DocumentType> documentTypes = (List<DocumentType>) session.createCriteria(DocumentType.class).list();
        session.close();
        return documentTypes;
    }
}
