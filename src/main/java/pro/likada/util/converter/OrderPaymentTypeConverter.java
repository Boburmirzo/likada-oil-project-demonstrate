package pro.likada.util.converter;

import pro.likada.model.OrderPaymentType;
import pro.likada.service.OrderPaymentTypeService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * Created by Yusupov on 3/20/2017.
 */
@FacesConverter("orderPaymentTypeConverter")
public class OrderPaymentTypeConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        if (value != null && value.trim().length() > 0) {
            FacesContext context = FacesContext.getCurrentInstance();
            OrderPaymentTypeService orderPaymentTypeService = context.getApplication().evaluateExpressionGet(context, "#{orderPaymentTypeService}", OrderPaymentTypeService.class);
            for(OrderPaymentType orderPaymentType: orderPaymentTypeService.getAllOrderPaymentTypes())
                if(orderPaymentType.getId().equals(Long.parseLong(value)))
                    return orderPaymentType;
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object object) {
        if(object!=null){
            OrderPaymentType orderPaymentType = (OrderPaymentType) object;
            return orderPaymentType.getId()!=null ? orderPaymentType.getId().toString(): "";
        }
        return "";
    }

}
