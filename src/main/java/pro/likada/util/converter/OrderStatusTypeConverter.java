package pro.likada.util.converter;

import pro.likada.model.OrderStatusType;
import pro.likada.service.OrderStatusTypeService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * Created by Yusupov on 3/16/2017.
 */
@FacesConverter("orderStatusTypeConverter")
public class OrderStatusTypeConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        if (value != null && value.trim().length() > 0) {
            FacesContext context = FacesContext.getCurrentInstance();
            OrderStatusTypeService orderStatusTypeService = context.getApplication().evaluateExpressionGet(context, "#{orderStatusTypeService}", OrderStatusTypeService.class);
            for(OrderStatusType orderStatusType: orderStatusTypeService.getAllOrderStatusTypes())
                if(orderStatusType.getId().equals(Long.parseLong(value)))
                    return orderStatusType;
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object object) {
        if(object!=null){
            OrderStatusType orderStatusType = (OrderStatusType) object;
            return orderStatusType.getId()!=null ? orderStatusType.getId().toString(): "";
        }
        return "";
    }
}
