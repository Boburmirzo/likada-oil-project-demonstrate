package pro.likada.util.converter;

import pro.likada.bean.model.ContractorModelBean;
import pro.likada.model.ContractorOrganizationType;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * Created by innopolis on 24.01.2017.
 */
@FacesConverter("organizationTypeConverter")
public class OrganizationTypeConverter implements Converter{

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        if (value != null && value.trim().length() > 0) {
            FacesContext context = FacesContext.getCurrentInstance();
            ContractorModelBean contractorModelBean = context.getApplication().evaluateExpressionGet(context, "#{contractorModelBean}", ContractorModelBean.class);
            for(ContractorOrganizationType contractorOrganizationType: contractorModelBean.getContractorOrganizationTypeList())
                if(contractorOrganizationType.getId().equals(Long.parseLong(value)))
                    return contractorOrganizationType;
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object object) {
        if(object!=null){
            ContractorOrganizationType contractorOrganizationType = (ContractorOrganizationType) object;
            return contractorOrganizationType.getId()!=null ? contractorOrganizationType.getId().toString(): "";
        }
        return "";
    }
}
