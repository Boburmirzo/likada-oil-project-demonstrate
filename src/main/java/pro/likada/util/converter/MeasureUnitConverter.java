package pro.likada.util.converter;

import pro.likada.bean.model.ProductModelBean;
import pro.likada.model.MeasureUnit;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * Created by bumur on 23.02.2017.
 */
@FacesConverter("measureUnitConverter")
public class MeasureUnitConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        if (value != null && value.trim().length() > 0) {
            FacesContext context = FacesContext.getCurrentInstance();
            ProductModelBean productModelBean = context.getApplication().evaluateExpressionGet(context, "#{productModelBean}", ProductModelBean.class);
            for(MeasureUnit measureUnit: productModelBean.getMeasureUnits())
                if(measureUnit.getId().equals(Long.parseLong(value)))
                    return measureUnit;
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object object) {
        if(object!=null){
            MeasureUnit measureUnit = (MeasureUnit) object;
            return measureUnit.getId()!=null ? measureUnit.getId().toString() : "";
        }
        return "";
    }

}
