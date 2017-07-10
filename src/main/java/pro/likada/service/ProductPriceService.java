package pro.likada.service;

import pro.likada.model.Product;
import pro.likada.model.ProductPrice;

import java.util.List;

/**
 * Created by bumur on 13.02.2017.
 */
public interface ProductPriceService {

    void save(ProductPrice productPrice);

    ProductPrice getProductActualPrice(List<ProductPrice> productPrices);

    int getPriceDirection(Product product, ProductPrice productPrice);

    double getPriceDifference(Product product,ProductPrice productPrice);
}
