package pro.likada.util.converter;

import pro.likada.bean.backing.ContractorBackingBean;
import pro.likada.model.BankAccount;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * Created by Yusupov on 2/2/2017.
 */
@FacesConverter("bankAccountConverter")
public class BankAccountConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        if (value != null && value.trim().length() > 0) {
            FacesContext context = FacesContext.getCurrentInstance();
            ContractorBackingBean contractorBackingBean = context.getApplication().evaluateExpressionGet(context, "#{contractorBackingBean}", ContractorBackingBean.class);
            int hashCode = Integer.parseInt(value);
            for (BankAccount bankAccount : contractorBackingBean.getSelectedContractor().getBankAccounts())
                if (bankAccount.hashCode() == hashCode)
                    return bankAccount;
        }
        return null;
    }

    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null)
            return String.valueOf(((BankAccount) object).hashCode());
        return "";
    }
}
