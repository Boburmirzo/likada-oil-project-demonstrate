package pro.likada.util.converter;

import pro.likada.model.Vehicle;
import pro.likada.service.VehicleService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * Created by Yusupov on 3/19/2017.
 */
@FacesConverter("vehicleConverter")
public class VehicleConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        if (value != null && value.trim().length() > 0) {
            FacesContext context = FacesContext.getCurrentInstance();
            VehicleService vehicleService = context.getApplication().evaluateExpressionGet(context, "#{vehicleService}", VehicleService.class);
            Vehicle vehicle = vehicleService.findById(Long.parseLong(value));
            return vehicle;
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object object) {
        if(object!=null){
            Vehicle vehicle = (Vehicle) object;
            return vehicle.getId()!=null ? vehicle.getId().toString(): "";
        }
        return "";
    }


}
