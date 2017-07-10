package pro.likada.util.converter;

import pro.likada.model.User;
import pro.likada.service.UserService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * Created by abuca on 10.03.17.
 */
@FacesConverter("userConverter")
public class UserConverter implements Converter {
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        if (value != null && value.trim().length() > 0) {
            FacesContext context = FacesContext.getCurrentInstance();
            UserService userService = context.getApplication().evaluateExpressionGet(context, "#{userService}", UserService.class);
            return userService.findById(Long.parseLong(value));
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object object) {
        if(object!=null){
            User user = (User) object;
            return user.getId()!=null ? user.getId().toString(): "";
        }
        return "";
    }
}
