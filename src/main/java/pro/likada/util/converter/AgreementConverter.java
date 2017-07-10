package pro.likada.util.converter;

import pro.likada.model.Agreement;
import pro.likada.service.AgreementService;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

/**
 * Created by Yusupov on 3/20/2017.
 */
@FacesConverter("agreementConverter")
public class AgreementConverter implements Converter {

    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        FacesContext context = FacesContext.getCurrentInstance();
        AgreementService agreementService = context.getApplication().evaluateExpressionGet(context, "#{agreementService}", AgreementService.class);
        if (value != null && value.trim().length() > 0) {
            try {
                return agreementService.findById(Long.valueOf(value));
            } catch (NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid theme."));
            }
        }

        return null;
    }

    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            return String.valueOf(((Agreement) object).getId());
        } else {
            return "";
        }
    }


}
