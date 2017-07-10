package pro.likada.bean.backing;

import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import pro.likada.bean.model.AgreementModelBean;
import pro.likada.bean.model.DocumentModelBean;
import pro.likada.model.Agreement;
import pro.likada.model.Document;
import pro.likada.model.DocumentType;
import pro.likada.service.DocumentTypeService;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * Created by bumur on 29.01.2017.
 */

@Named
@ViewScoped
public class DocumentBackingBean implements Serializable{

    @Inject
    private DocumentModelBean documentModelBean;
    @Inject
    private AgreementModelBean agreementModelBean;
    @Inject
    private DocumentTypeService documentTypeService;
    private List<DocumentType> documentTypes;
    private Agreement agreement;
    private Document selectedDocument;
    private Document selectedDocumentAfterEdit;

    private List<Document> documents;
    private List<Document> newDocuments;

    private StreamedContent streamedContent;
    private UploadedFile uploadedFile;

    @PostConstruct
    public void init(){
        if(agreementModelBean.getSelectedAgreement()!=null)
            this.agreement = agreementModelBean.getSelectedAgreement();
        if(documentTypes==null)
            this.documentTypes = documentTypeService.getAllDocumentTypes();
    }

    public Document getSelectedDocument() {
        return selectedDocument;
    }

    public void setSelectedDocument(Document selectedDocument) {
        this.selectedDocument = selectedDocument;
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    public StreamedContent getStreamedContent() {
        return streamedContent;
    }

    public void setStreamedContent(StreamedContent streamedContent) {
        this.streamedContent = streamedContent;
    }

    public Agreement getAgreement() {
        return agreement;
    }

    public void setAgreement(Agreement agreement) {
        this.agreement = agreement;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public List<Document> getNewDocuments() {
        return newDocuments;
    }

    public void setNewDocuments(List<Document> newDocuments) {
        this.newDocuments = newDocuments;
    }

    public List<DocumentType> getDocumentTypes() {
        return documentTypes;
    }

    public void setDocumentTypes(List<DocumentType> documentTypes) {
        this.documentTypes = documentTypes;
    }

    public Document getSelectedDocumentAfterEdit() {
        return selectedDocumentAfterEdit;
    }

    public void setSelectedDocumentAfterEdit(Document selectedDocumentAfterEdit) {
        this.selectedDocumentAfterEdit = selectedDocumentAfterEdit;
    }
}
