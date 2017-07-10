package pro.likada.util.converter;

import pro.likada.bean.backing.DriveBackingBean;
import pro.likada.model.DriveState;
import pro.likada.model.DriveStateType;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * Created by bumur on 30.03.2017.
 */
@FacesConverter("driveStateConverter")
public class DriveStateConverter implements Converter{

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        if (value != null && value.trim().length() > 0) {
            FacesContext context = FacesContext.getCurrentInstance();
            DriveBackingBean driveBackingBean = context.getApplication().evaluateExpressionGet(context, "#{driveBackingBean}", DriveBackingBean.class);
            int hashCode = Integer.parseInt(value);
            for(DriveStateType driveStateType: driveBackingBean.getDriveStateTypes())
                if(driveStateType.hashCode()==hashCode)
                    return driveStateType;
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object object) {
        if (object != null)
            return String.valueOf(((DriveStateType) object).hashCode());
        return "";
    }
}
