package pro.likada.util.converter;

import org.primefaces.model.TreeNode;
import pro.likada.bean.backing.FinancialItemBackingBean;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import java.util.List;

/**
 * Created by bumur on 06.03.2017.
 */
@FacesConverter("contractorGroupConverter")
public class ContractorGroupConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
                FacesContext context = FacesContext.getCurrentInstance();
                FinancialItemBackingBean financialItemBackingBean = context.getApplication().evaluateExpressionGet(context, "#{financialItemBackingBean}", FinancialItemBackingBean.class);
                List<TreeNode> list = financialItemBackingBean.getContractorGroupsNodeList();
                if (list != null) {
                    for (int i = 0; i < list.size(); i++) {
                        String gg = (String) list.get(i).getData();
                        if (value.equals(gg)) {
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

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        if (o != null) {
            return String.valueOf( ((TreeNode) o).getData());
        } else {
            return "";
        }
    }
}
