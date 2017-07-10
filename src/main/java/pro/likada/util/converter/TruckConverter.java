package pro.likada.util.converter;

import pro.likada.model.Truck;
import pro.likada.service.TruckService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * Created by abuca on 10.03.17.
 */
@FacesConverter("truckConverter")
public class TruckConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        if (value != null && value.trim().length() > 0) {
            FacesContext context = FacesContext.getCurrentInstance();
            TruckService truckService = context.getApplication().evaluateExpressionGet(context, "#{truckService}", TruckService.class);
            Truck truck = truckService.findById(Long.parseLong(value));
            return truck;
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object object) {
        if(object!=null){
            Truck truck = (Truck) object;
            return truck.getId()!=null ? truck.getId().toString(): "";
        }
        return "";
    }

}
