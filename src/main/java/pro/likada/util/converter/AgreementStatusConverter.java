package pro.likada.util.converter;

import pro.likada.bean.model.AgreementModelBean;
import pro.likada.model.AgreementStatus;
import pro.likada.service.AgreementService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * Created by Yusupov on 1/31/2017.
 */
@FacesConverter("agreementStatusConverter")
public class AgreementStatusConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        if (value != null && value.trim().length() > 0) {
            FacesContext context = FacesContext.getCurrentInstance();
            AgreementModelBean agreementModelBean = context.getApplication().evaluateExpressionGet(context, "#{agreementModelBean}", AgreementModelBean.class);
            for(AgreementStatus agreementStatus: agreementModelBean.getAgreementStatusList())
                if(agreementStatus.getId().equals(Long.parseLong(value)))
                    return agreementStatus;
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object object) {
        if(object!=null){
            AgreementStatus agreementStatus = (AgreementStatus) object;
            return agreementStatus.getId()!=null ? agreementStatus.getId().toString(): "";
        }
        return "";
    }

}
