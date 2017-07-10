package pro.likada.bean.controller;

import org.primefaces.event.CellEditEvent;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.likada.bean.backing.DocumentBackingBean;
import pro.likada.bean.model.DocumentModelBean;
import pro.likada.model.Document;
import pro.likada.service.DocumentService;
import pro.likada.service.DocumentTypeService;
import pro.likada.util.FileIOSystemUtil;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URLEncoder;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by bumur on 29.01.2017.
 */
@Named
@RequestScoped
public class DocumentController implements Serializable{

    private static final Logger LOGGER = LoggerFactory.getLogger(DocumentController.class);

    @Inject
    private DocumentService documentService;
    @Inject
    private DocumentTypeService documentTypeService;
    @Inject
    private DocumentBackingBean documentBackingBean;
    @Inject
    private DocumentModelBean documentModelBean;
    @Inject
    private LoginController loginController;


    public List<Document> getDocuments(){
        LOGGER.info("Get all Documents");
        if(documentBackingBean.getDocuments()==null) {
            documentBackingBean.setDocuments(documentService.findByParentTypeId(documentBackingBean.getAgreement().getId(),documentBackingBean.getAgreement().getClass().getSimpleName()));
        }
        return documentBackingBean.getDocuments();
    }

    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("messages", context.getViewRoot().getLocale());
        Document document=documentBackingBean.getDocuments().get(event.getRowIndex());
        if(event.getColumn().getHeaderText().equals(resourceBundle.getString("document.name"))){
            FileIOSystemUtil fileIOSystemUtil= new FileIOSystemUtil();
            fileIOSystemUtil.renameFileName(document.getDocument_path(),document.getDocument_name(), (String)oldValue, document.getDocument_extension());
        }
        documentService.save(document);
        if(newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, resourceBundle.getString("document.cellIsChanged"),
                    resourceBundle.getString("document.cellOldValue")+" "+ oldValue + resourceBundle.getString("document.cellNewValue")+" " + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

    }

    public void buttonActionAddDocument(ActionEvent actionEvent){
        Document newDocument= new Document();
        documentBackingBean.setSelectedDocument(newDocument);
    }

    public void fileDownloadView(Document document) throws Exception {
        LOGGER.info("Download file*******************", document.getDocument_name());
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("messages", context.getViewRoot().getLocale());
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        LOGGER.info(document.getDocument_path());
        File file = new File(resourceBundle.getString("document.pathName")+
                document.getDocument_path()+document.getDocument_name()+"."+document.getDocument_extension());
        InputStream input = new FileInputStream(file);
        documentBackingBean.setStreamedContent(new DefaultStreamedContent(input, externalContext.getMimeType(URLEncoder.encode(file.getName(), "UTF-8")), URLEncoder.encode(file.getName(), "UTF-8")));
    }

    public void handleFileUpload() throws Exception{
        buttonActionAddFileToTheDocumentList();
        String fileName = documentBackingBean.getUploadedFile().getFileName();
        long fileSize = documentBackingBean.getUploadedFile().getSize();
        FileIOSystemUtil fileIOSystemUtil = new FileIOSystemUtil();
        LOGGER.info(fileIOSystemUtil.removeFileExtension(fileName));
        documentBackingBean.getSelectedDocument().setDocument_name(fileIOSystemUtil.removeFileExtension(fileName));
        documentBackingBean.getSelectedDocument().setDocument_size(fileSize);
        LOGGER.info(fileIOSystemUtil.splitFileExtension(fileName));
        documentBackingBean.getSelectedDocument().setDocument_extension(fileIOSystemUtil.splitFileExtension(fileName));
        saveNewDocumentToPath();
    }

    public void buttonActionAddFileToTheDocumentList(){
        LOGGER.info("Fill Document fields");
        documentBackingBean.getSelectedDocument().setParent_id(documentBackingBean.getAgreement().getId());
        documentBackingBean.getSelectedDocument().setParent_type(documentBackingBean.getAgreement().getClass().getSimpleName());
        documentBackingBean.getSelectedDocument().setCreator_user_id(loginController.getCurrentUser());
        documentBackingBean.getSelectedDocument().setDocument_type(documentBackingBean.getDocumentTypes().get(1));
    }

    public void buttonActionDeleteDocument(){
        FileIOSystemUtil fileIOSystemUtil= new FileIOSystemUtil();
        fileIOSystemUtil.deleteExistsFile(documentBackingBean.getSelectedDocument().getDocument_path(),
                documentBackingBean.getSelectedDocument().getDocument_name()+"."+documentBackingBean.getSelectedDocument().getDocument_extension());
        documentBackingBean.getDocuments().remove(documentBackingBean.getSelectedDocument());
        documentService.deleteById(documentBackingBean.getSelectedDocument().getId());

    }

    public void saveNewDocumentToPath() throws Exception{
        FileIOSystemUtil fileIOSystemUtil= new FileIOSystemUtil();
        fileIOSystemUtil.createDirectoryIfNotExists(documentBackingBean.getAgreement().getClass().getSimpleName(),documentBackingBean.getSelectedDocument().getParent_id().toString());
        if(fileIOSystemUtil.saveNewFile(documentBackingBean.getUploadedFile().getInputstream(),documentBackingBean.getUploadedFile().getFileName())){
            documentBackingBean.getSelectedDocument().setDocument_path(fileIOSystemUtil.getFileDirectoryForDatabase());
            documentBackingBean.getDocuments().add(documentBackingBean.getSelectedDocument());
            documentService.save(documentBackingBean.getSelectedDocument());
        }else{
            FacesContext context = FacesContext.getCurrentInstance();
            ResourceBundle resourceBundle = ResourceBundle.getBundle("messages", context.getViewRoot().getLocale());
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, resourceBundle.getString("error"),  resourceBundle.getString("document.pathToDirectoryIsNotExists")));
        }
    }

    public void uploadedFileListener(FileUploadEvent fileUploadEvent){
        documentBackingBean.setUploadedFile(fileUploadEvent.getFile());
    }

}