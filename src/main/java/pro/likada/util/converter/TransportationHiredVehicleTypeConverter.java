package pro.likada.util.converter;

import pro.likada.model.TransportationHiredVehicleType;
import pro.likada.service.TransportationHiredVehicleTypeService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * Created by Yusupov on 3/17/2017.
 */
@FacesConverter("transportationHiredVehicleTypeConverter")
public class TransportationHiredVehicleTypeConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        if (value != null && value.trim().length() > 0) {
            FacesContext context = FacesContext.getCurrentInstance();
            TransportationHiredVehicleTypeService transportationHiredVehicleTypeService = context.getApplication().evaluateExpressionGet(context, "#{transportationHiredVehicleTypeService}", TransportationHiredVehicleTypeService.class);
            for(TransportationHiredVehicleType transportationHiredVehicleType: transportationHiredVehicleTypeService.getAllTransportationHiredVehicleTypes())
                if(transportationHiredVehicleType.getId().equals(Long.parseLong(value)))
                    return transportationHiredVehicleType;
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object object) {
        if(object!=null){
            TransportationHiredVehicleType transportationHiredVehicleType = (TransportationHiredVehicleType) object;
            return transportationHiredVehicleType.getId()!=null ? transportationHiredVehicleType.getId().toString(): "";
        }
        return "";
    }

}
