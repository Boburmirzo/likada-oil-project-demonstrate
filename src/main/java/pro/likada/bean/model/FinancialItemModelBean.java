package pro.likada.bean.model;

import pro.likada.model.FinancialItem;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * Created by bumur on 28.02.2017.
 */
@Named
@SessionScoped
public class FinancialItemModelBean implements Serializable{

    private List<FinancialItem> financialItems;

    public List<FinancialItem> getFinancialItems() {
        return financialItems;
    }

    public void setFinancialItems(List<FinancialItem> financialItems) {
        this.financialItems = financialItems;
    }

}
