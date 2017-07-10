package pro.likada.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ResourceBundle;

/**
 * Created by bumur on 02.02.2017.
 */
public class FileIOSystemUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileIOSystemUtil.class);

    private String fileDirectory;
    private String fileDirectoryForDatabase;
    private boolean flagForCheckingSource = false;
    public void createDirectoryIfNotExists(String parentTypeForPath, String parentIdForPath) {
        if(!flagForCheckingSource) {
            FacesContext context = FacesContext.getCurrentInstance();
            ResourceBundle resourceBundle = ResourceBundle.getBundle("messages", context.getViewRoot().getLocale());
            fileDirectory = resourceBundle.getString("document.pathName") + parentTypeForPath + "\\" + parentIdForPath + "\\";
        }else
            fileDirectory = "C:\\files_archive\\" + parentTypeForPath + "\\" + parentIdForPath + "\\";
        fileDirectoryForDatabase = parentTypeForPath + "\\" + parentIdForPath + "\\";
        File directory = new File(fileDirectory);
        if (!directory.exists()) {
            LOGGER.info("Directory is doesn't exists!");
            directory.mkdirs();
        }
    }

    public boolean saveNewFile(InputStream fileInputStream, String fileName) throws Exception {
        File fileForSaving = new File(fileDirectory + fileName);
        OutputStream out = new FileOutputStream(fileForSaving);
        if (out != null) {
            byte buf[] = new byte[1024];
            int len;
            while ((len = fileInputStream.read(buf)) > 0)
                out.write(buf, 0, len);
            fileInputStream.close();
            out.close();
            return true;
        }
        return false;
    }

    public boolean deleteExistsFile(String deletedFilePath, String deletedFile){
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("messages", context.getViewRoot().getLocale());
        File deletedFileFind= new File(resourceBundle.getString("document.pathName")+deletedFilePath+deletedFile);
        if(deletedFileFind.delete()){
            LOGGER.info(deletedFileFind.getName() + " is deleted!");
            return true;
        }else{
            LOGGER.info("Delete operation is failed.");
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, resourceBundle.getString("error"),  resourceBundle.getString("document.pathToDirectoryIsNotExists")));
        }
        return false;
    }

    public boolean renameFileName(String renamedFilePath, String renamedFileNewValue, String renamedFileOldValue, String fileExtension){
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("messages", context.getViewRoot().getLocale());
        File oldFile = new File(resourceBundle.getString("document.pathName")+renamedFilePath+renamedFileOldValue+"."+fileExtension);
        File newFile = new File(resourceBundle.getString("document.pathName")+renamedFilePath+renamedFileNewValue+"."+fileExtension);
        if(oldFile.renameTo(newFile)) {
            LOGGER.info("File is renamed");
            return true;
        } else {
            LOGGER.info("File renaming is failed");
            return false;
        }
    }

    public String splitDriveIdFromFileName(String fileName){
        String[] fileNameSplits = fileName.split("_");
        return fileNameSplits[fileNameSplits.length - 1];
    }

    public String splitFileExtension(String fileName){
        String[] fileNameSplits = fileName.split("\\.");
        return fileNameSplits[fileNameSplits.length - 1];
    }

    public String removeFileExtension(String fileName){
        if (fileName == null) return null;
        int pos = fileName.lastIndexOf(".");
        if (pos == -1) return fileName;
        return fileName.substring(0, pos);
    }

    public String getFileDirectoryForDatabase() {
        return fileDirectoryForDatabase;
    }

    public void setFileDirectoryForDatabase(String fileDirectoryForDatabase) {
        this.fileDirectoryForDatabase = fileDirectoryForDatabase;
    }

    public boolean isFlagForCheckingSource() {
        return flagForCheckingSource;
    }

    public void setFlagForCheckingSource(boolean flagForCheckingSource) {
        this.flagForCheckingSource = flagForCheckingSource;
    }
}
