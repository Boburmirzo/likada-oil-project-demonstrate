package pro.likada.util.converter;

import pro.likada.model.Trailer;
import pro.likada.service.TrailerService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * Created by abuca on 10.03.17.
 */
@FacesConverter("trailerConverter")
public class TrailerConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        if (value != null && value.trim().length() > 0) {
            FacesContext context = FacesContext.getCurrentInstance();
            TrailerService trailerService = context.getApplication().evaluateExpressionGet(context, "#{trailerService}", TrailerService.class);
            Trailer trailer = trailerService.findById(Long.parseLong(value));
            return trailer;
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object object) {
        if(object!=null){
            Trailer trailer = (Trailer) object;
            return trailer.getId()!=null ? trailer.getId().toString(): "";
        }
        return "";
    }

}

