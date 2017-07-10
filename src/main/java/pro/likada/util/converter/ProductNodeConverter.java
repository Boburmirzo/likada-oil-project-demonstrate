package pro.likada.util.converter;

import org.primefaces.model.TreeNode;
import pro.likada.bean.backing.ProductBackingBean;
import pro.likada.model.ProductGroup;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import java.util.List;

/**
 * Created by bumur on 15.02.2017.
 */
@FacesConverter("productNodeConverter")
public class ProductNodeConverter implements Converter{

    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
                FacesContext context = FacesContext.getCurrentInstance();
                ProductBackingBean productBackingBean = context.getApplication().evaluateExpressionGet(context, "#{productBackingBean}", ProductBackingBean.class);
                List<TreeNode> list = productBackingBean.getProductGroupsNodeList();
                Long productGroupId = Long.parseLong(value);
                if (list != null) {
                    for (int i = 0; i < list.size(); i++) {
                        ProductGroup gg = (ProductGroup) list.get(i).getData();
                        if (productGroupId.equals(gg.getId())) {
                            return list.get(i);
                        }
                    }
                }
            } catch (NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, " Error", "Not a valid theme."));
            }
        }

        return null;
    }

    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            return String.valueOf(((ProductGroup) ((TreeNode) object).getData()).getId());
        } else {
            return "";
        }
    }
}
