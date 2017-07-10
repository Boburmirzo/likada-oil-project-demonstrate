package pro.likada.util.converter;

import pro.likada.model.Driver;
import pro.likada.service.DriverService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * Created by Yusupov on 3/19/2017.
 */
@FacesConverter("driverConverter")
public class DriverConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        if (value != null && value.trim().length() > 0) {
            FacesContext context = FacesContext.getCurrentInstance();
            DriverService driverService = context.getApplication().evaluateExpressionGet(context, "#{driverService}", DriverService.class);
            return driverService.findById(Long.parseLong(value));
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object object) {
        if(object!=null){
            Driver driver = (Driver) object;
            return driver.getId()!=null ? driver.getId().toString(): "";
        }
        return "";
    }

}
