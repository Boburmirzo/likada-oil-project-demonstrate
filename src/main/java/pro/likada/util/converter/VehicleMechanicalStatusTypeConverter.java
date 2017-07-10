package pro.likada.util.converter;

import pro.likada.model.VehicleMechanicalStatusType;
import pro.likada.service.VehicleMechanicalStatusTypeService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * Created by abuca on 03.04.17.
 */
@FacesConverter("vehicleMechanicalStatusTypeConverter")
public class VehicleMechanicalStatusTypeConverter implements Converter {
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        if (value != null && value.trim().length() > 0) {
            FacesContext context = FacesContext.getCurrentInstance();
            VehicleMechanicalStatusTypeService orderStatusTypeService = context.getApplication().evaluateExpressionGet(context, "#{vehicleMechanicalStatusTypeService}", VehicleMechanicalStatusTypeService.class);
            for(VehicleMechanicalStatusType vehicleMechanicalStatusType: orderStatusTypeService.getAllVehicleMechanicalStatusTypes()){
                if(vehicleMechanicalStatusType.getId().equals(Long.parseLong(value))) {
                    return vehicleMechanicalStatusType;
                }
            }

        }
        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object object) {
        if(object!=null){
            VehicleMechanicalStatusType vehicleMechanicalStatusType = (VehicleMechanicalStatusType) object;
            return vehicleMechanicalStatusType.getId()!=null ? vehicleMechanicalStatusType.getId().toString(): "";
        }
        return "";
    }
}
