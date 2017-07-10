package pro.likada.util.converter;


import pro.likada.bean.model.AgreementModelBean;
import pro.likada.model.AgreementType;
import pro.likada.service.AgreementService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * Created by Yusupov on 1/31/2017.
 */
@FacesConverter("agreementTypeConverter")
public class AgreementTypeConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        if (value != null && value.trim().length() > 0) {
            FacesContext context = FacesContext.getCurrentInstance();
            AgreementModelBean agreementModelBean = context.getApplication().evaluateExpressionGet(context, "#{agreementModelBean}", AgreementModelBean.class);
            for(AgreementType agreementType: agreementModelBean.getAgreementTypeList())
                if(agreementType.getId().equals(Long.parseLong(value)))
                    return agreementType;
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object object) {
        if(object!=null){
            AgreementType agreementType = (AgreementType) object;
            return agreementType.getId()!=null ? agreementType.getId().toString() : "";
        }
        return "";
    }

}
