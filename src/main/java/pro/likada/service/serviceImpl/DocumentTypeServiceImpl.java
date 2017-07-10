package pro.likada.service.serviceImpl;

import pro.likada.dao.DocumentTypeDAO;
import pro.likada.model.DocumentType;
import pro.likada.service.DocumentTypeService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

/**
 * Created by bumur on 29.01.2017.
 */
@Named("documentTypeService")
@Transactional
public class DocumentTypeServiceImpl implements DocumentTypeService,Serializable {

    @Inject
    private DocumentTypeDAO documentTypeDAO;

    @Override
    public DocumentType findById(long id) {
        return documentTypeDAO.findById(id);
    }

    @Override
    public List<DocumentType> getAllDocumentTypes() {
        return documentTypeDAO.getAllDocumentTypes();
    }
}
