package pro.likada.util.converter;

import pro.likada.bean.backing.DriveBackingBean;
import pro.likada.model.DriveStatus;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * Created by bumur on 29.03.2017.
 */
@FacesConverter("driveStatusConverter")
public class DriveStatusConverter implements Converter{

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        if (value != null && value.trim().length() > 0) {
            FacesContext context = FacesContext.getCurrentInstance();
            DriveBackingBean driveBackingBean = context.getApplication().evaluateExpressionGet(context, "#{driveBackingBean}", DriveBackingBean.class);
            int hashCode = Integer.parseInt(value);
            for(DriveStatus driveStatus: driveBackingBean.getDriveStatuses())
                if(driveStatus.hashCode()==hashCode)
                    return driveStatus;
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object object) {
        if (object != null)
            return String.valueOf(((DriveStatus) object).hashCode());
        return "";
    }
}
