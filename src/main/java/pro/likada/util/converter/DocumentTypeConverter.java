package pro.likada.util.converter;

import pro.likada.bean.backing.DocumentBackingBean;
import pro.likada.model.DocumentType;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * Created by bumur on 01.02.2017.
 */
@FacesConverter("documentTypeConverter")
public class DocumentTypeConverter implements Converter{
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        if (value != null && value.trim().length() > 0) {
            FacesContext context = FacesContext.getCurrentInstance();
            DocumentBackingBean documentBackingBean = context.getApplication().evaluateExpressionGet(context, "#{documentBackingBean}", DocumentBackingBean.class);
            int hashCode = Integer.parseInt(value);
            for(DocumentType documentType: documentBackingBean.getDocumentTypes())
                if(documentType.hashCode()==hashCode)
                    return documentType;
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object object) {
        if (object != null)
            return String.valueOf(((DocumentType) object).hashCode());
        return "";
    }
}
