package pro.likada.util.converter;

import pro.likada.model.Contractor;
import pro.likada.service.ContractorService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * Created by abuca on 10.03.17.
 */

@FacesConverter("contractorConverter")
public class ContractorConverter implements Converter {
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        if (value != null && value.trim().length() > 0) {
            FacesContext context = FacesContext.getCurrentInstance();
            ContractorService contractorService = context.getApplication().evaluateExpressionGet(context, "#{contractorService}", ContractorService.class);
            return contractorService.findById(Long.parseLong(value));
        }
        else{
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object object) {
        if(object!=null){
            Contractor contractor = (Contractor) object;
            return contractor.getId()!=null ? contractor.getId().toString(): "";
        }
        return "";
    }
}
