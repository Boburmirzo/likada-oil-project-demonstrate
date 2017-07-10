package pro.likada.service;

import pro.likada.model.ProductType;

import java.util.List;

/**
 * Created by bumur on 19.02.2017.
 */
public interface ProductTypeService {

    ProductType findById(long id);

    void deleteById(Long id);

    List<ProductType> getAllProductTypes();

    List<ProductType> getProductTypesGroupByNameWork();

    List<ProductType> findAllProductTypesByNameWork(String searchString);

    void save(ProductType productType);

    List<ProductType> getProductTypesByIdInDescendingOrder();
}
