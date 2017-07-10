package pro.likada.bean.model;

import pro.likada.model.Drive;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Created by bumur on 27.03.2017.
 */
@Named
@SessionScoped
public class DriveModelBean implements Serializable {

   private Drive selectedDriveFromTableOfDrives;

    public Drive getSelectedDriveFromTableOfDrives() {
        return selectedDriveFromTableOfDrives;
    }

    public void setSelectedDriveFromTableOfDrives(Drive selectedDriveFromTableOfDrives) {
        this.selectedDriveFromTableOfDrives = selectedDriveFromTableOfDrives;
    }
}
