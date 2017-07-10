package pro.likada.dao;

import pro.likada.model.Product;

import java.util.List;

/**
 * Created by bumur on 14.02.2017.
 */
public interface ProductDAO {

    Product findById(long id);

    List<Product> getProductsByGroupIds(List<Long> groupsIds);

    List<Product> getProductsByGroupId(Long groupId);

    void save(Product product);

    void deleteById(Long id);

    List<Product> getAllProducts();
}
