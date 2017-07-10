package pro.likada.util.converter;

import pro.likada.bean.backing.FinancialItemBackingBean;
import pro.likada.model.Contractor;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * Created by bumur on 16.03.2017.
 */

@FacesConverter("financialItemConverter")
public class FinancialItemConverter implements Converter {
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        if (value != null && value.trim().length() > 0) {
            FacesContext context = FacesContext.getCurrentInstance();
            FinancialItemBackingBean financialItemBackingBean = context.getApplication().evaluateExpressionGet(context, "#{financialItemBackingBean}", FinancialItemBackingBean.class);
            int hashCode = Integer.parseInt(value);
            for(Contractor contractor: financialItemBackingBean.getContractorsOfCustomersForSelectOneMenu())
                if(contractor.hashCode()==hashCode)
                    return contractor;
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object object) {
        if (object != null)
            return String.valueOf(((Contractor) object).hashCode());
        return "";
    }
}