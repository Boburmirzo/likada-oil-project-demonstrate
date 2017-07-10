package pro.likada.util.converter;

import pro.likada.bean.backing.DocumentBackingBean;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import java.text.DecimalFormat;

/**
 * Created by bumur on 01.02.2017.
 */
@FacesConverter("fileSizeConverter")
public class FileSizeConverter implements Converter {
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        return s;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        FacesContext context = FacesContext.getCurrentInstance();
        DocumentBackingBean documentBackingBean = context.getApplication().evaluateExpressionGet(context, "#{documentBackingBean}", DocumentBackingBean.class);
        Long filesize=(Long)o;
        if(filesize <= 0) return "0";
        final String[] units = new String[] { "B", "kB", "MB", "GB", "TB" };
        int digitGroups = (int) (Math.log10(filesize)/Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(filesize/Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }
}
