package pro.likada.dao;

import pro.likada.model.ProductGroup;

import java.util.List;

/**
 * Created by bumur on 14.02.2017.
 */
public interface ProductGroupDAO {

    ProductGroup findById(long id);

    void save(ProductGroup productGroup);

    void deleteById(Long id);

    List<ProductGroup> getProductGroupsByParentId(Long id);

    List<ProductGroup> getAllProductGroups();

    Long getMinValueOfProductParentId();

    Long getMinValueOfProductId();
}
