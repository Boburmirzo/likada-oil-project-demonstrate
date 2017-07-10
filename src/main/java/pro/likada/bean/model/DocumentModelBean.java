package pro.likada.bean.model;

import org.primefaces.model.UploadedFile;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Created by bumur on 01.02.2017.
 */
@Named
@SessionScoped
public class DocumentModelBean implements Serializable{


    private UploadedFile file;


    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

}
