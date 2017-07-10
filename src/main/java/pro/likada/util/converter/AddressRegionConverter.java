package pro.likada.util.converter;

import pro.likada.bean.model.ContractorModelBean;
import pro.likada.model.AddressRegion;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * Created by Yusupov on 2/2/2017.
 */
@FacesConverter("addressRegionConverter")
public class AddressRegionConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        if (value != null && value.trim().length() > 0) {
            FacesContext context = FacesContext.getCurrentInstance();
            ContractorModelBean contractorModelBean = context.getApplication().evaluateExpressionGet(context, "#{contractorModelBean}", ContractorModelBean.class);
            for(AddressRegion addressRegion: contractorModelBean.getAddressRegionList())
                if(addressRegion.getId().equals(Long.parseLong(value)))
                    return addressRegion;
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object object) {
        if(object!=null){
            AddressRegion addressRegion = (AddressRegion) object;
            return addressRegion.getId()!=null ? addressRegion.getId().toString(): "";
        }
        return "";
    }
}
