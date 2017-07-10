package pro.likada.util.converter;

import pro.likada.model.TransportationCompanyType;
import pro.likada.service.TransportationCompanyTypeService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * Created by Yusupov on 3/17/2017.
 */
@FacesConverter("transportationCompanyTypeConverter")
public class TransportationCompanyTypeConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        if (value != null && value.trim().length() > 0) {
            FacesContext context = FacesContext.getCurrentInstance();
            TransportationCompanyTypeService transportationCompanyTypeService = context.getApplication().evaluateExpressionGet(context, "#{transportationCompanyTypeService}", TransportationCompanyTypeService.class);
            for(TransportationCompanyType transportationCompanyType: transportationCompanyTypeService.getAllTransportationCompanyTypes())
                if(transportationCompanyType.getId().equals(Long.parseLong(value)))
                    return transportationCompanyType;
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object object) {
        if(object!=null){
            TransportationCompanyType transportationCompanyType = (TransportationCompanyType) object;
            return transportationCompanyType.getId()!=null ? transportationCompanyType.getId().toString(): "";
        }
        return "";
    }

}
