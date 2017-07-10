package pro.likada.util.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.likada.bean.model.AgreementModelBean;
import pro.likada.model.Contractor;
import pro.likada.service.ContractorService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * Created by Yusupov on 1/31/2017.
 */
@FacesConverter("clientConverter")
public class ClientConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        if (value != null && value.trim().length() > 0) {
            FacesContext context = FacesContext.getCurrentInstance();
            AgreementModelBean agreementModelBean = context.getApplication().evaluateExpressionGet(context, "#{agreementModelBean}", AgreementModelBean.class);
            for (Contractor client: agreementModelBean.getContractorsListToAssignAsClient())
                if(client.getId().equals(Long.parseLong(value)))
                    return client;
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object object) {
        if(object!=null){
            Contractor client = (Contractor)object;
            return client.getId()!=null ? client.getId().toString(): "";
        }
        return "";
    }
}
