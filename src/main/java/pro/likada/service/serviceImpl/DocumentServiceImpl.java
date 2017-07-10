package pro.likada.service.serviceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.likada.dao.DocumentDAO;
import pro.likada.model.Document;
import pro.likada.service.DocumentService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by bumur on 29.01.2017.
 */
@Named("documentService")
@Transactional
public class DocumentServiceImpl implements DocumentService{

    private static final Logger LOGGER = LoggerFactory.getLogger(DocumentServiceImpl.class);

    @Inject
    private DocumentDAO documentDAO;

    @Override
    public Document findById(long id) {
        return documentDAO.findById(id);
    }

    @Override
    public List<Document> getAllDocuments() {
        return documentDAO.getAllDocuments();
    }

    @Override
    public void save(Document document) {
        documentDAO.save(document);
    }

    @Override
    public void deleteById(long id) {
        documentDAO.deleteById(id);
    }

    @Override
    public List<Document> findByParentTypeId(Long id, String parentType) {
        return documentDAO.findByParentTypeId(id, parentType);
    }
}
