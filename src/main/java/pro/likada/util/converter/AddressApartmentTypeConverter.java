package pro.likada.util.converter;

import pro.likada.bean.model.ContractorModelBean;
import pro.likada.model.AddressApartmentType;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * Created by Yusupov on 2/2/2017.
 */
@FacesConverter("addressApartmentTypeConverter")
public class AddressApartmentTypeConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        if (value != null && value.trim().length() > 0) {
            FacesContext context = FacesContext.getCurrentInstance();
            ContractorModelBean contractorModelBean = context.getApplication().evaluateExpressionGet(context, "#{contractorModelBean}", ContractorModelBean.class);
            for(AddressApartmentType addressApartmentType: contractorModelBean.getAddressApartmentTypeList())
                if(addressApartmentType.getId().equals(Long.parseLong(value)))
                    return addressApartmentType;
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object object) {
        if(object!=null){
            AddressApartmentType addressApartmentType = (AddressApartmentType) object;
            return addressApartmentType.getId()!=null ? addressApartmentType.getId().toString(): "";
        }
        return "";
    }

}
