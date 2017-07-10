package pro.likada.dao;

import pro.likada.model.ProductType;

import java.util.List;

/**
 * Created by bumur on 19.02.2017.
 */
public interface ProductTypeDAO {

    ProductType findById(long id);

    void deleteById(Long id);

    List<ProductType> getAllProductTypes();

    List<ProductType> getProductTypesGroupByNameWork();

    List<ProductType> findAllProductTypesByNameWork(String searchString);

    void save(ProductType productType);

    List<ProductType> getProductTypesByIdInDescendingOrder();
}
