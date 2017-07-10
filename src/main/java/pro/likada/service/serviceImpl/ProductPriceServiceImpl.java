package pro.likada.service.serviceImpl;

import javafx.print.Collation;
import pro.likada.dao.ProductPriceDAO;
import pro.likada.model.Product;
import pro.likada.model.ProductPrice;
import pro.likada.service.ProductPriceService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by bumur on 13.02.2017.
 */
@Named("productPriceService")
@Transactional
public class ProductPriceServiceImpl implements ProductPriceService {

    @Inject
    private ProductPriceDAO productPriceDAO;

    @Override
    public void save(ProductPrice productPrice) {
        productPriceDAO.save(productPrice);
    }

    @Override
    public ProductPrice getProductActualPrice(List<ProductPrice> productPrices) {
        Date currentDate=new Date(System.currentTimeMillis());
        Collections.sort(productPrices);
        for (ProductPrice productPrice:productPrices) {
            if (productPrice != null) {
                if (productPrice.getPrice() != null && productPrice.getTimeModified() != null) {
                    if(productPrice.getTimeModified().before(currentDate))
                    return productPrice;
                }
            }
        }
        return null;
    }

    @Override
    public int getPriceDirection(Product product, ProductPrice productPrice) {
        if (product != null && productPrice != null) {
            if (productPrice.getPrice() != null) {
                int ig = product.getProductPricesList().indexOf(productPrice);
                double pc = productPrice.getPrice();
                if (ig == (product.getProductPricesList().size() - 1)) {
                    return 0;
                } else {
                    for (int i = ig + 1; i < product.getProductPricesList().size(); i++) {
                        if (product.getProductPricesList().get(i) != null) {
                            if (product.getProductPricesList().get(i).getPrice() != null) {
                                double pp = product.getProductPricesList().get(i).getPrice();

                                if (pc > pp) {
                                    return 1;
                                } else if (pc < pp) {
                                    return -1;
                                } else {
                                    return 0;
                                }
                            }
                        }
                    }
                }
            }
        }
        return 0;
    }

    @Override
    public double getPriceDifference(Product product, ProductPrice productPrice) {
        if (product != null && productPrice != null) {
            if (productPrice.getPrice() != null) {
                int ig = product.getProductPricesList().indexOf(productPrice);
                if (ig == (product.getProductPricesList().size() - 1)) {
                    return 0;
                } else {
                    for (int i = ig + 1; i < product.getProductPricesList().size(); i++) {
                        if (product.getProductPricesList().get(i) != null) {
                            if (product.getProductPricesList().get(i).getPrice() != null) {

                                return (productPrice.getPrice() - product.getProductPricesList().get(i).getPrice());
                            }
                        }
                    }
                }
            }
        }

        return 0;
    }
}
