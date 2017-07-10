package pro.likada.service;

import org.primefaces.model.TreeNode;
import org.primefaces.model.chart.LineChartModel;
import pro.likada.model.Product;

import java.util.List;

/**
 * Created by bumur on 14.02.2017.
 */
public interface ProductService {

    Product findById(long id);

    List<Product> getProductsByGroup(TreeNode selectedProductNode);

    List<Product> getProductsByGroupId(Long groupId);

    void deleteById(Long id);

    void compareChilds(TreeNode node, List<Long> list);

    LineChartModel drawProductLineChart(Product product);

    void save(Product product);

    List<Product> getAllProducts();
}
