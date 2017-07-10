package pro.likada.service;

import pro.likada.model.Document;

import java.util.List;

/**
 * Created by bumur on 29.01.2017.
 */
public interface DocumentService {

    Document findById(long id);

    List<Document> getAllDocuments();

    void save(Document document);

    void deleteById(long id);

    List<Document> findByParentTypeId(Long id, String parentType);
}
