package pro.likada.bot.products.BotSendMessageUtil;

import pro.likada.model.ProductGroup;
import pro.likada.model.ProductPrice;

import java.util.List;

/**
 * Created by bumur on 21.03.2017.
 */
public class MessageProductPricesUtil {

    private String checkProductGroupRepeatingString="";

    public String createHTMLMessageToTelegram(List<ProductPrice> productPrices, List<ProductGroup> productGroups) {
        String productPricesUpdate="";
        for (ProductPrice productPrice : productPrices) {
            String productGroupsTreeForTextView = findProductGroupTree(productGroups, productPrice);
            if(!productGroupsTreeForTextView.isEmpty()) {
                productPricesUpdate =productPricesUpdate+"\n"+ "<b>" + productGroupsTreeForTextView +
                        "</b>"+ "\n";
            }
            ProductPrice productOldPrice = getProductActualPrice(productPrice.getProduct().getProductPricesList());
            if (productOldPrice != null) {
                Double differenceBetweenPrice = productPrice.getPrice() - productOldPrice.getPrice();
                if (differenceBetweenPrice > 0) {
                    productPricesUpdate = productPricesUpdate + productPrice.getProduct().getCode() + " " + productOldPrice.getPrice().intValue() +
                            " (" + "<b>" + "+" + differenceBetweenPrice.intValue() + "</b>" + ")"+"\n";
                } else if (differenceBetweenPrice<0){
                    productPricesUpdate = productPricesUpdate + productPrice.getProduct().getCode() + " " + productOldPrice.getPrice().intValue() +
                            " (" + "<b>" + differenceBetweenPrice.intValue() + "</b>" + ")"+"\n";
                }else {
                    productPricesUpdate = productPricesUpdate + productPrice.getProduct().getCode() + " " + productOldPrice.getPrice().intValue()+"\n";
                }
            }
        }
        return productPricesUpdate;
    }

    private String findProductGroupTree(List<ProductGroup> productGroups, ProductPrice productPrice){
        if(!checkProductGroupRepeatingString.equals(productPrice.getProduct().getGroupId().toString())) {
            checkProductGroupRepeatingString=productPrice.getProduct().getGroupId().toString();
            String productGroupsTreeForTextView = productPrice.getProduct().getGroupId().getTitle();
            if (productPrice.getProduct().getGroupId().getParentId()==0) {
                return productGroupsTreeForTextView;
            }else {
                Long nextLevel= productPrice.getProduct().getGroupId().getParentId();
                while (nextLevel!=0) {
                    for (ProductGroup productGroupInner : productGroups) {
                        if (productGroupInner.getId().equals(nextLevel)) {
                            productGroupsTreeForTextView = productGroupInner.getTitle() + " - " + productGroupsTreeForTextView;
                            nextLevel = productGroupInner.getParentId();
                        }
                    }
                }
            }

            return productGroupsTreeForTextView;
        }
        return "";
    }

    private ProductPrice getProductActualPrice(List<ProductPrice> productPrices) {
        for (ProductPrice productPrice:productPrices) {
            if (productPrice != null) {
                if (productPrice.getPrice() != null && productPrice.getTimeModified() != null) {
                    return productPrice;
                }
            }
        }
        return null;
    }
}
