package pro.likada.service;

import pro.likada.model.DocumentType;

import java.util.List;

/**
 * Created by bumur on 29.01.2017.
 */
public interface DocumentTypeService {

    DocumentType findById(long id);

    List<DocumentType> getAllDocumentTypes();
}
