package pro.likada.util.converter;

import pro.likada.bean.model.CustomerModelBean;
import pro.likada.model.Customer;
import pro.likada.service.CustomerService;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

/**
 * Created by bumur on 07.01.2017.
 */
@FacesConverter("customerConverter")
public class CustomerConverter implements Converter {
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        FacesContext context = FacesContext.getCurrentInstance();
        CustomerService customerService = context.getApplication().evaluateExpressionGet(context, "#{customerService}", CustomerService.class);
        if (value != null && value.trim().length() > 0) {
            try {
                return customerService.findById(Long.valueOf(value));
            } catch (NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid theme."));
            }
        }

        return null;
    }

    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            return String.valueOf(((Customer) object).getId());
        } else {
            return "";
        }
    }
}
