package pro.likada.util.converter;

import pro.likada.bean.backing.DriveBackingBean;
import pro.likada.model.ShipmentBasis;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

/**
 * Created by bumur on 29.03.2017.
 */
@FacesConverter("shipmentBaseConverter")
public class ShipmentBaseConverter implements Converter{

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        if (value != null && value.trim().length() > 0) {
            FacesContext context = FacesContext.getCurrentInstance();
            DriveBackingBean driveBackingBean = context.getApplication().evaluateExpressionGet(context, "#{driveBackingBean}", DriveBackingBean.class);
            int hashCode = Integer.parseInt(value);
            for(ShipmentBasis shipmentBasis: driveBackingBean.getShipmentBases())
                if(shipmentBasis.hashCode()==hashCode)
                    return shipmentBasis;
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object object) {
        if (object != null)
            return String.valueOf(((ShipmentBasis) object).hashCode());
        return "";
    }
}
