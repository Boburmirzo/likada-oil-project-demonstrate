package pro.likada.util.converter;

import pro.likada.model.TransportationVehicleType;
import pro.likada.service.TransportationVehicleTypeService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * Created by Yusupov on 3/17/2017.
 */
@FacesConverter("transportationVehicleTypeConverter")
public class TransportationVehicleTypeConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        if (value != null && value.trim().length() > 0) {
            FacesContext context = FacesContext.getCurrentInstance();
            TransportationVehicleTypeService transportationVehicleTypeService = context.getApplication().evaluateExpressionGet(context, "#{transportationVehicleTypeService}", TransportationVehicleTypeService.class);
            for(TransportationVehicleType transportationVehicleType: transportationVehicleTypeService.getAllTransportationVehicleTypes())
                if(transportationVehicleType.getId().equals(Long.parseLong(value)))
                    return transportationVehicleType;
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object object) {
        if(object!=null){
            TransportationVehicleType transportationVehicleType = (TransportationVehicleType) object;
            return transportationVehicleType.getId()!=null ? transportationVehicleType.getId().toString(): "";
        }
        return "";
    }

}
